
pipeline {
    agent any

    stages {

        stage('Build') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Tests') {
            parallel {

                stage('Unit Tests') {
                    steps {
                        sh 'mvn test'
                    }
                }

                stage('Integration Tests') {
                    steps {
                        dir('integration-workspace') {
                            checkout scm
                            sh 'mvn clean test-compile failsafe:integration-test failsafe:verify -Pintegration-tests'
                        }
                    }
                }
            }
        }

        stage('Quality Analysis') {
            steps {
                sh 'mvn clean verify -Pintegration-tests'
                sh 'mvn org.owasp:dependency-check-maven:check -DfailBuildOnCVSS=0'
            }
        }

        stage('Manual Approval') {
            when {
                branch 'main'
            }
            steps {
                input message: 'Autorizar deploy para staging?'
            }
        }

        stage('Deploy to Staging') {
            when {
                branch 'main'
            }
            steps {
                sh 'docker build -t myapp:${GIT_COMMIT} .'
                sh 'kubectl apply -f k8s/deployment.yaml'
                sh 'kubectl rollout status deployment/myapp --timeout=120s'
            }

            post {
                failure {
                    sh 'kubectl rollout undo deployment/myapp'
                }
            }
        }
    }

    post {
        always {
            junit allowEmptyResults: true,
                  testResults: '**/target/surefire-reports/*.xml, **/target/failsafe-reports/*.xml'
        }

        failure {
            echo 'Pipeline executado com falha!'
            slackSend color: 'danger',
                      message: "Falha no pipeline ${env.JOB_NAME} #${env.BUILD_NUMBER}"
        }

        success {
            echo 'Pipeline executado com sucesso!'
        }
    }
}

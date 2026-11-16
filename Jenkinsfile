
pipeline {
    agent any

    stages {

        stage('Build') {
            steps {
                sh 'mvn clean package'
            }
        }

        stage('Test') {
            steps {
                sh 'mvn test'
                junit 'target/surefire-reports/*.xml'
            }
        }

        stage('Quality Analysis') {
            steps {
                sh 'mvn checkstyle:check'
                sh 'mvn pmd:check'
                sh 'mvn jacoco:report'
            }
        }

        stage('Deploy to Staging') {
            when {
                branch 'main'
            }

            steps {
                sh 'echo "Preparando deploy para staging"'
            }
        }
    }

    post {
        failure {
            echo 'Build Failed! Iniciando procedimento de rollback.'

            sh '''
                echo "Rollback solicitado."
                echo "A restauração real depende da configuração do ambiente."
            '''
        }

        success {
            echo 'Pipeline executado com sucesso!'
        }
    }
}

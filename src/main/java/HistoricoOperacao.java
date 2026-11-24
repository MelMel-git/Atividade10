public class HistoricoOperacao {


        private String operacao;
        private double resultado;

        public HistoricoOperacao(String operacao, double resultado) {
            this.operacao = operacao;
            this.resultado = resultado;
        }

        public String getOperacao() {
            return operacao;
        }

        public double getResultado() {
            return resultado;
        }
    }



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

    public class HistoricoRepository {

        private final String url;
        private final String usuario;
        private final String senha;

        public HistoricoRepository(String url, String usuario, String senha) {
            this.url = url;
            this.usuario = usuario;
            this.senha = senha;
        }

        private Connection conectar() throws SQLException {
            return DriverManager.getConnection(url, usuario, senha);
        }

        public void criarTabela() throws SQLException {
            String sql = """
                CREATE TABLE IF NOT EXISTS historico (
                    id SERIAL PRIMARY KEY,
                    operacao VARCHAR(100) NOT NULL,
                    resultado DOUBLE PRECISION NOT NULL
                )
                """;

            try (Connection conexao = conectar();
                 Statement comando = conexao.createStatement()) {
                comando.execute(sql);
            }
        }

        public void salvar(HistoricoOperacao historico) throws SQLException {
            String sql = """
                INSERT INTO historico (operacao, resultado)
                VALUES (?, ?)
                """;

            try (Connection conexao = conectar();
                 PreparedStatement comando = conexao.prepareStatement(sql)) {

                comando.setString(1, historico.getOperacao());
                comando.setDouble(2, historico.getResultado());

                comando.executeUpdate();
            }
        }

        public List<HistoricoOperacao> listarTodos() throws SQLException {
            List<HistoricoOperacao> historicos = new ArrayList<>();

            String sql = """
                SELECT operacao, resultado
                FROM historico
                ORDER BY id
                """;

            try (Connection conexao = conectar();
                 Statement comando = conexao.createStatement();
                 ResultSet resultado = comando.executeQuery(sql)) {

                while (resultado.next()) {
                    historicos.add(new HistoricoOperacao(
                            resultado.getString("operacao"),
                            resultado.getDouble("resultado")
                    ));
                }
            }

            return historicos;
        }
    }



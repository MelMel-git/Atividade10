
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Testcontainers
public class HistoricoIntegrationTest {

    @Container
    static PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>("postgres:16-alpine")
                    .withDatabaseName("calculadora")
                    .withUsername("usuario")
                    .withPassword("senha");

    @Test
    void deveSalvarEConsultarOperacao() throws Exception {

        HistoricoRepository repository = new HistoricoRepository(
                postgres.getJdbcUrl(),
                postgres.getUsername(),
                postgres.getPassword()
        );

        repository.criarTabela();

        HistoricoOperacao operacao =
                new HistoricoOperacao("10 + 5", 15);

        repository.salvar(operacao);

        var historicos = repository.listarTodos();

        assertEquals(1, historicos.size());
        assertEquals("10 + 5", historicos.get(0).getOperacao());
        assertEquals(15.0, historicos.get(0).getResultado());
    }
}

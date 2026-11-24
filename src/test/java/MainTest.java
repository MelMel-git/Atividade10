import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class MainTest {

    @Test
    public void testarSoma() {
        Assertions.assertEquals(15, Main.somar(10, 5));
    }

    @Test
    public void testarSubtracao() {
        Assertions.assertEquals(5, Main.subtrair(10, 5));
    }

    @Test
    public void testarMultiplicacao() {
        Assertions.assertEquals(50, Main.multiplicar(10, 5));
    }

    @Test
    public void testarDivisao() {
        Assertions.assertEquals(2.0, Main.dividir(10, 5));
    }

    @Test
    public void testarDivisaoPorZero() {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> Main.dividir(10, 0)
        );
    }

    @Test
    public void testarProgramaPrincipal() {
        Main.main(new String[]{});
    }
}

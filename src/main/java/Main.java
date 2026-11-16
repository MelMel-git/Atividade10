
public class Main {

    public static int somar(int a, int b) {
        return a + b;
    }

    public static int subtrair(int a, int b) {
        return a - b;
    }

    public static int multiplicar(int a, int b) {
        return a * b;
    }

    public static double dividir(double a, double b) {
        if (b == 0) {
            throw new IllegalArgumentException(
                    "Não é possível dividir por zero."
            );
        }

        return a / b;
    }

    public static void main(String[] args) {
        System.out.println("Resultado: " + somar(10, 5));
    }
}

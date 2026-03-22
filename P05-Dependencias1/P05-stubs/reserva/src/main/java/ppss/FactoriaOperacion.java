package ppss;

public class FactoriaOperacion {
    private static IOperacionB0 operacion = null;

    public static IOperacionB0 create() {
        if (operacion != null) {
            return operacion;
        } else {
            return new Operacion();
        }
    }

    public static void setOperacion(IOperacionB0 op) {
        operacion = op;
    }
}

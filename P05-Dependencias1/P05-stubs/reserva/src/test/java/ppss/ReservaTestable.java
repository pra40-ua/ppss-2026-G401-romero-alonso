package ppss;

public class ReservaTestable extends Reserva {
    private boolean permiso = true;
    private OperacionStub operacion;

    public void setPermiso(boolean permiso) {
        this.permiso = permiso;
    }

    public ReservaTestable(OperacionStub operacion) {
        this.operacion = operacion;
        FactoriaOperacion.setOperacion(operacion);
    }

    @Override
    public boolean compruebaPermisos(String login, String password, Usuario usuario) {
        return permiso;
    }

}

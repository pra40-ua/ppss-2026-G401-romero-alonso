package ppss;

public class MatriculaAlumnoTestable extends MatriculaAlumno{
    private OperacionStub operacionStub;

    public MatriculaAlumnoTestable(OperacionStub operacionStub) {
        this.operacionStub = operacionStub;
    }

    @Override
    protected Operacion getOperacion() {
        return operacionStub;
    }
}

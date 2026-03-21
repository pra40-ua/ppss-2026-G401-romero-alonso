package ppss;

import java.util.HashMap;
import java.util.Map;

public class OperacionStub extends Operacion {
    private Map<String, Exception> excepciones = new HashMap<>();

    public void setException(String asignatura, Exception excepcion) {
        excepciones.put(asignatura, excepcion);
    }

    @Override
    public void compruebaMatricula(String dni, String asignatura)
            throws AsignaturaCursadaException, AsignaturaIncorrectaException {
        Exception ex = excepciones.get(asignatura);
        if (ex instanceof AsignaturaIncorrectaException) {
            throw (AsignaturaIncorrectaException) ex;
        } else if (ex instanceof AsignaturaCursadaException) {
            throw (AsignaturaCursadaException) ex;
        }
    }
}

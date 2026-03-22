package ppss;

import ppss.excepciones.IsbnInvalidoException;
import ppss.excepciones.JDBCException;
import ppss.excepciones.SocioInvalidoException;
import java.util.Map;
import java.util.HashMap;

public class OperacionStub implements IOperacionB0 {
    private Map<String, Exception> excepcionIsbn = new HashMap<>();
    private Map<String, Exception> excepcionSocio = new HashMap<>();
    private Map<String, Exception> excepcionConexion = new HashMap<>();

    public void setExceptionIsbn(String isbn, Exception e) {
        excepcionIsbn.put(isbn, e);
    }

    public void setExceptionSocio(String socio, Exception e) {
        excepcionSocio.put(socio, e);
    }

    public void setExceptionConexion(String isbn, Exception e) {
        excepcionConexion.put(isbn, e);
    }
    
    @Override
    public void operacionReserva(String socio, String isbn)
            throws IsbnInvalidoException, JDBCException, SocioInvalidoException {
        Exception eSocio = excepcionSocio.get(socio);
        Exception eIsbn = excepcionIsbn.get(isbn);
        Exception eConexion = excepcionConexion.get(isbn);
        if (eSocio != null) {
            throw (SocioInvalidoException) eSocio;
        } if (eIsbn != null) {
            throw (IsbnInvalidoException) eIsbn;
        } if (eConexion != null) {
            throw (JDBCException) eConexion;
        }

    }
}

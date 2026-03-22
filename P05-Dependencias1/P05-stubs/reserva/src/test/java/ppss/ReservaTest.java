package ppss;

import org.junit.jupiter.api.Test;
import ppss.excepciones.IsbnInvalidoException;
import ppss.excepciones.JDBCException;
import ppss.excepciones.ReservaException;
import ppss.excepciones.SocioInvalidoException;

import static org.junit.jupiter.api.Assertions.*;

public class ReservaTest {

    @Test
    public void C1_realizaReserva_should_return_ReservaException1_when_ErrorPermisos() {
        //Arrange
        String login = "xxxx";
        String password = "xxxx";
        String socio = "Luis";
        String [] isbns = {"11111"};
        OperacionStub stub = new OperacionStub();
        ReservaTestable  sut = new ReservaTestable(stub);

        sut.setPermiso(false);

        //Act
        ReservaException ex = assertThrows(ReservaException.class,
                () -> sut.realizaReserva(login,password, socio, isbns));

        //Assert
        assertEquals("ERROR de permisos; ", ex.getMessage());
    }

    @Test
    public void  C2_realizaReserva_should_return_NoException_when_all_fine() {
        //Arrange
        String login = "ppss";
        String password = "ppss";
        String socio = "Luis";
        String [] isbns = {"11111","22222"};
        OperacionStub stub = new OperacionStub();
        ReservaTestable  sut = new ReservaTestable(stub);


        //Act & Assert
        assertDoesNotThrow(() -> sut.realizaReserva(login, password, socio, isbns));
    }

    @Test
    public void C3_realizaReserva_should_return_ReservaException2_when_ISBNinvalido() {
        //Arrange
        String login = "ppss";
        String password = "ppss";
        String socio = "Luis";
        String [] isbns = {"11111", "33333", "44444"};
        OperacionStub stub = new OperacionStub();
        ReservaTestable  sut = new ReservaTestable(stub);

        IsbnInvalidoException ex = new IsbnInvalidoException("error");
        stub.setExceptionIsbn("33333", ex);
        stub.setExceptionIsbn("44444", ex);

        //Act
        ReservaException resultado = assertThrows(ReservaException.class,
                () -> sut.realizaReserva(login, password, socio, isbns));

        //Assert
        assertEquals("ISBN invalido:33333; ISBN invalido:44444; ", resultado.getMessage());
    }

    @Test
    public void C4_realizaReserva_should_return_ReservaException3_when_SocioInvalido() {
        //Arrange
        String login = "ppss";
        String password = "ppss";
        String socio = "Pepe";
        String [] isbns = {"11111"};
        OperacionStub stub = new OperacionStub();
        ReservaTestable  sut = new ReservaTestable(stub);

        SocioInvalidoException ex = new SocioInvalidoException("error");
        stub.setExceptionSocio(socio, ex);

        //Act
        ReservaException resultado = assertThrows(ReservaException.class,
                () -> sut.realizaReserva(login, password, socio, isbns));

        //Assert
        assertEquals("SOCIO invalido; ", resultado.getMessage());
    }

    @Test
    public void C5_realizaReserva_should_return_ReservaException4_when_ConexionInvalida() {
        //Arrange
        String login = "ppss";
        String password = "ppss";
        String socio = "Luis";
        String [] isbns = {"11111","22222"};
        OperacionStub stub = new OperacionStub();
        ReservaTestable sut = new ReservaTestable(stub);

        JDBCException ex = new JDBCException("error");
        stub.setExceptionConexion("22222", ex);

        //Act
        ReservaException resultado = assertThrows(ReservaException.class,
                () -> sut.realizaReserva(login, password, socio, isbns));

        //Assert
        assertEquals("CONEXION invalida; ", resultado.getMessage());
    }

}

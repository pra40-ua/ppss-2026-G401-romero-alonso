package ppss;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AlquilaCochesTest {

    @Test
    public void C1_calculaPrecio_should_return_75_when_all_days_are_false() throws MensajeException {
        // Arrange
        TipoCoche tipo = TipoCoche.TURISMO;
        LocalDate fecha = LocalDate.of(2024, 05, 18);
        int ndias = 10;
        CalendarioStub calendarioStub = new CalendarioStub();
        ServicioStub servicioStub = new ServicioStub();
        AlquilaCochesTestable sut = new AlquilaCochesTestable(calendarioStub, servicioStub);

        //Act
        Ticket resultado = sut.calculaPrecio(tipo, fecha, ndias);

        //Assert
        assertEquals(75.0f, resultado.getPrecio_final());
    }

    @Test
    public void C2_calculaPrecio_should_return_62_5_when_two_days_true() throws MensajeException {
        //Arrange
        TipoCoche tipo = TipoCoche.CARAVANA;
        LocalDate fecha = LocalDate.of(2024, 06, 19);
        int ndias = 7;
        CalendarioStub calendarioStub = new CalendarioStub();
        ServicioStub servicioStub = new ServicioStub();
        AlquilaCochesTestable sut = new AlquilaCochesTestable(calendarioStub, servicioStub);

        calendarioStub.setFestivos(LocalDate.of(2024, 06, 20), true);
        calendarioStub.setFestivos(LocalDate.of(2024, 06, 24), true);

        //Act
        Ticket resultado = sut.calculaPrecio(tipo, fecha, ndias);

        //Assert
        assertEquals(62.5f, resultado.getPrecio_final());
    }

    @Test
    public void C3_calculaPrecio_should_return_error_when_three_days_false() throws MensajeException {
        //Arrange
        TipoCoche tipo = TipoCoche.TURISMO;
        LocalDate fecha = LocalDate.of(2024, 04, 17);
        int ndias = 8;
        CalendarioStub calendarioStub = new CalendarioStub();
        ServicioStub servicioStub = new ServicioStub();
        AlquilaCochesTestable sut = new AlquilaCochesTestable(calendarioStub, servicioStub);

        calendarioStub.setException(LocalDate.of(2024, 04, 18), new CalendarioException("error"));
        calendarioStub.setException(LocalDate.of(2024, 04, 21), new CalendarioException("error"));
        calendarioStub.setException(LocalDate.of(2024, 04, 22), new CalendarioException("error"));

        //Act
        MensajeException ex = assertThrows(MensajeException.class, ()-> sut.calculaPrecio(tipo, fecha, ndias));

        //Assert
        assertEquals("Error en dia: 2024-04-18; Error en dia: 2024-04-21; Error en dia: 2024-04-22; ", ex.getMessage());


    }
}

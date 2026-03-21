package ppss;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.Arrays;

public class MatriculaAlumnoTest {

    @Test
    void C1_validaAsignaturas_should_returnJustificante_when_mixedAsignaturas()
            throws Exception {
        //Arrange
        OperacionStub stub = new OperacionStub();
        stub.setException("ZZ", new AsignaturaIncorrectaException("no existe"));
        stub.setException("YYY", new AsignaturaIncorrectaException("no existe"));
        stub.setException("P1", new AsignaturaCursadaException("ya cursada"));

        MatriculaAlumnoTestable sut = new MatriculaAlumnoTestable(stub);

        String[] asignaturas = {"MD", "ZZ", "FBD", "YYY", "P1"};

        ArrayList<String> validasEsperadas = new ArrayList<>(Arrays.asList("MD", "FBD"));
        ArrayList<String> erroresEsperados = new ArrayList<>(Arrays.asList(
                "Asignatura ZZ no existe",
                "Asignatura YYY no existe",
                "Asignatura P1 ya cursada"
        ));

        //Act
        JustificanteMatricula resultado = sut.validaAsignaturas("00000000T", asignaturas);

        //Assert
        assertAll(
                () -> assertEquals("00000000T", resultado.getDni()),
                () -> assertEquals(validasEsperadas, resultado.getAsignaturas()),
                () -> assertEquals(erroresEsperados, resultado.getErrores())
        );
    }

    @Test
    void C2_validaAsignaturas_should_returnJustificante_when_allAsignaturasValidas() {
        //Arrange
        OperacionStub stub = new OperacionStub();

        MatriculaAlumnoTestable sut = new MatriculaAlumnoTestable(stub);

        String[] asignaturas = {"PPSS", "ADA", "P3"};

        ArrayList<String> validasEsperadas = new ArrayList<>(Arrays.asList("PPSS", "ADA", "P3"));
        ArrayList<String> erroresEsperados = new ArrayList<>(Arrays.asList());

        //Act
        JustificanteMatricula resultado = sut.validaAsignaturas("00000000T", asignaturas);

        //Assert
        assertAll(
                () -> assertEquals("00000000T", resultado.getDni()),
                () -> assertEquals(validasEsperadas, resultado.getAsignaturas()),
                () -> assertEquals(erroresEsperados, resultado.getErrores())
        );
    }
}

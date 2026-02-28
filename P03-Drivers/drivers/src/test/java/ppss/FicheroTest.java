package ppss;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests asociados a la clase Fichero")
public class FicheroTest {

    FicheroTexto fichero;

    @BeforeEach
    void setUp() {
        fichero = new FicheroTexto();
    }

    @Tag("no_parametrizado")
    @Test
    void C1_contarCaracteres_should_return_Exception_when_file_does_not_exist(){
        // Arrange
        String name = "ficheroC1.txt";

        // Act + Assert
        FicheroException exception = assertThrows(FicheroException.class, () -> fichero.contarCaracteres(name));
        assertEquals("ficheroC1.txt (No existe el archivo o el directorio)", exception.getMessage());
    }

    @Tag("no_parametrizado")
    @Test
    void C2_contarCaracteres_should_return_4_when_file_has_4_chars(){
        // Arrange
        String name = "src/test/resources/ficheroCorrecto.txt";
        int resultadoEsperado = 4;

        // Act
        int resultado = assertDoesNotThrow(() -> fichero.contarCaracteres(name));

        // Assert
        assertEquals(resultadoEsperado, resultado);
    }

    @Tag("excluido")
    @Test
    void C3_contarCaracteres_should_return_Exception_when_file_cannot_be_read(){
        Assertions.fail();
    }

    @Tag("excluido")
    @Test
    void C4_contarCaracteres_should_return_Exception_when_file_cannot_be_closed(){
        Assertions.fail();
    }

}

package ppss;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;

@DisplayName("Tests asociados a la clase Cine")
public class CineTest {

    Cine cine;

    @BeforeEach
    void setUp() {
        cine = new Cine();
    }

    @Tag("no_parametrizado")
    @Test
    void C1_reservaButacas_should_return_Exception_when_fila_empty_and_want_3(){
        // Arrange
        boolean[] asientos = {};
        int solicitados = 3;

        // Act + Assert
        assertThrows(ButacasException.class, () -> cine.reservaButacas(asientos,solicitados));
    }

    @Tag("no_parametrizado")
    @Test
    void C2_reservaButacas_should_return_false_when_fila_empty_and_want_zero(){
        // Arrange
        boolean[] asientos = {};
        boolean[] asientosEsperados = {};
        int solicitados = 0;

        // Act
        boolean resultado = assertDoesNotThrow(() -> cine.reservaButacas(asientos,solicitados));

        // Assert
        assertAll(
                () -> assertFalse(resultado),
                () -> assertArrayEquals(asientosEsperados,asientos)
        );
    }

    @Tag("no_parametrizado")
    @Test
    void C3_reservaButacas_should_return_true_when_fila_has_3_seats_free_and_want_2(){
        // Arrange
        boolean[] asientos = {false, false, false, true, true};
        boolean[] asientosEsperados = {true, true, false, true, true};
        int solicitados = 2;

        // Act
        boolean resultado = assertDoesNotThrow(() -> cine.reservaButacas(asientos,solicitados));

        // Assert
        assertAll(
                () -> assertTrue(resultado),
                () -> assertArrayEquals(asientosEsperados,asientos)
        );
    }

    @Tag("no_parametrizado")
    @Test
    void C4_reservaButacas_should_return_false_when_no_free_seats_and_want_1(){
        // Arrange
        boolean[] asientos = {true, true, true};
        boolean[] asientosEsperados = {true, true, true};
        int solicitados = 1;

        // Act
        boolean resultado = assertDoesNotThrow(() -> cine.reservaButacas(asientos,solicitados));

        // Assert
        assertAll(
                () -> assertFalse(resultado),
                () -> assertArrayEquals(asientosEsperados,asientos)
        );
    }

    static Stream<Arguments> datosC5(){
        return Stream.of(
                Arguments.of("should be false when we want 0 and fila has no seats",
                        new boolean[]{}, new boolean[]{}, 0, false),
                Arguments.of("should be true when we want 2 and there are 2 free seats",
                        new boolean[]{false, false, false, true, true},
                        new boolean[]{true, true, false, true, true}, 2, true),
                Arguments.of("should be false when we want 1 and all seats are already reserved",
                        new boolean[]{true, true, true},
                        new boolean[]{true, true, true}, 1, false)
        );
    }

    @Tag("parametrizado")
    @DisplayName("reservaButacas_")
    @ParameterizedTest(name = "{index} {0}")
    @MethodSource("datosC5")
    void C5_reservaButacas(String mensaje, boolean[] asientos, boolean[] asientosEsperados, int solicitados, boolean esperado){
        // Act
        boolean resultado = assertDoesNotThrow(() -> cine.reservaButacas(asientos, solicitados));

        // Assert
        assertAll(
                () -> assertEquals(esperado, resultado, mensaje),
                () -> assertArrayEquals(asientosEsperados, asientos, mensaje)
        );
    }

    static Stream<Arguments> datosC6(){
        return Stream.of(
                Arguments.of("should be return true when we want 1 and there is 1 free seat",
                        new boolean[]{false, false, false}, new boolean[]{true, false, false}, 1, true),
                Arguments.of("should be return false when we want 1 and seats is already reserved",
                        new boolean[]{true}, new boolean[]{true}, 1, false)
        );
    }

    @Tag("parametrizado")
    @Tag("tablaB")
    @ParameterizedTest(name = "{index} {0}")
    @DisplayName("reservaButacas_")
    @MethodSource("datosC6")
    void C6_reservaButacas(String mensaje, boolean[] asientos, boolean[] asientosEsperados, int solicitados, boolean esperado){
        // Act
        boolean resultado = assertDoesNotThrow(() -> cine.reservaButacas(asientos, solicitados));

        // Assert
        assertAll(
                () -> assertEquals(esperado, resultado, mensaje),
                () -> assertArrayEquals(asientosEsperados, asientos, mensaje)
        );
    }


}

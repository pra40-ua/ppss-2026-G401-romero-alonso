package llamadas;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GestorLlamadasTest {

    @Test
    void C1_calculoConsumo_should_return_147_when_HoraEsDiurna(){
        //Arrange
        CalendarioStub stub = new CalendarioStub(15);
        GestorLlamadasTestable sut = new GestorLlamadasTestable(stub);

        //Act
        double resultado = sut.calculaConsumo(10);

        //Assert
        assertEquals(147, resultado);
    }

    @Test
    void C2_calculoConsumo_should_return_65_when_HoraEsNocturna(){
        //Arrange
        CalendarioStub stub = new CalendarioStub(23);
        GestorLlamadasTestable sut = new GestorLlamadasTestable(stub);

        //Act
        double resultado = sut.calculaConsumo(10);

        //Arrange
        assertEquals(65, resultado);
    }
}

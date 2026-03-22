package ppss;

import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PremioMockTest {
    IMocksControl ctrl;
    ClienteWebService clienteMock;
    Premio sut;

    @BeforeEach
    public void setUp() {
        ctrl = EasyMock.createStrictControl();
        clienteMock = ctrl.mock(ClienteWebService.class);
        sut = EasyMock.partialMockBuilder(Premio.class)
                .addMockedMethod("generaNumero")
                .mock(ctrl);
        sut.cliente = clienteMock;
    }

    @Test
    public void C1_compruebaPremio_should_return_Premiado_when_generado_es_0_07() {
        //Arrange
        EasyMock.expect(sut.generaNumero()).andReturn(0.07f);
        assertDoesNotThrow(
                () -> EasyMock.expect(clienteMock.obtenerPremio()).andReturn("entrada final Champions"));
        ctrl.replay();

        //Act
        String resultado = assertDoesNotThrow(() -> sut.compruebaPremio());

        //Assert
        assertEquals("Premiado con entrada final Champions", resultado);
        ctrl.verify();
    }

    @Test
    public void C2_compruebaPremio_should_return_NoObtenidoPremio_when_generado_es_0_05() {
        //Arrange
        EasyMock.expect(sut.generaNumero()).andReturn(0.05f);
        assertDoesNotThrow(
                () -> EasyMock.expect(clienteMock.obtenerPremio())
                        .andThrow(new ClienteWebServiceException("error")));
        ctrl.replay();

        //Act
        String resultado = assertDoesNotThrow(() -> sut.compruebaPremio());

        //Assert
        assertEquals("No se ha podido obtener el premio", resultado);
        ctrl.verify();
    }

    @Test
    public void C3_compruebaPremio_should_return_SinPremio_when_generado_es_0_48() {
        //Arrange
        EasyMock.expect(sut.generaNumero()).andReturn(0.48f);
        ctrl.replay();

        //Act
        String resultado = assertDoesNotThrow(() -> sut.compruebaPremio());

        //Assert
        assertEquals("Sin premio", resultado);
        ctrl.verify();
    }
}

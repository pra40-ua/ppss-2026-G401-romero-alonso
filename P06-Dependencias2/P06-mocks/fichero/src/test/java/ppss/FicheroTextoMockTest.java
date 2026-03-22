package ppss;

import java.io.FileReader;
import java.io.IOException;

import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class FicheroTextoMockTest {
    IMocksControl control;
    FileReader fileReaderMock;
    FicheroTexto sut;

    @BeforeEach
    public void setUp(){
        control = EasyMock.createStrictControl();
        fileReaderMock = control.mock(FileReader.class);
        sut = EasyMock.partialMockBuilder(FicheroTexto.class)
                .addMockedMethod("getFichero")
                .mock(control);
    }

    @Test
    public void C1_contarCaracteres_should_return_ErrorAlLeerArchivo_when_ficheroC1() {
        //Arrange
        assertDoesNotThrow(() ->
                EasyMock.expect(sut.getFichero("src/test/resources/ficheroC1.txt"))
                .andReturn(fileReaderMock));
        assertDoesNotThrow(() ->
        EasyMock.expect(fileReaderMock.read()).andReturn((int) 'a').andReturn((int) 'b')
                .andThrow(new IOException()));

        control.replay();

        //Act
        FicheroException ex = assertThrows(FicheroException.class,
                () -> sut.contarCaracteres("src/test/resources/ficheroC1.txt"));

        //Assert
        assertEquals("src/test/resources/ficheroC1.txt (Error al leer el archivo)",ex.getMessage());

        control.verify();
    }

    @Test
    public void C2_contarCaracteres_should_return_ErrorAlCerrarArchivo_when_ficheroC2() {
        //Arrange
        assertDoesNotThrow(() ->
                EasyMock.expect(sut.getFichero("src/test/resources/ficheroC2.txt"))
                        .andReturn(fileReaderMock));
        assertDoesNotThrow(() ->
                EasyMock.expect(fileReaderMock.read()).andReturn((int) 'a').andReturn((int) 'b')
                        .andReturn((int) 'c').andReturn(-1));
        assertDoesNotThrow(() -> {fileReaderMock.close();
                EasyMock.expectLastCall().andThrow(new IOException());
        });

        control.replay();

        //Act
        FicheroException ex = assertThrows(FicheroException.class,
                () -> sut.contarCaracteres("src/test/resources/ficheroC2.txt"));

        //Assert
        assertEquals("src/test/resources/ficheroC2.txt (Error al cerrar el archivo)",ex.getMessage());

        control.verify();
    }

}

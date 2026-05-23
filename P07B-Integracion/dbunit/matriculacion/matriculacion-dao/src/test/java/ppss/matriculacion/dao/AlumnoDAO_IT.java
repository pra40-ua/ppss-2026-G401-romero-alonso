package ppss.matriculacion.dao;

import org.dbunit.Assertion;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.util.fileloader.FlatXmlDataFileLoader;
import ppss.matriculacion.to.AlumnoTO;
import java.time.LocalDate;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.sql.SQLException;

public class AlumnoDAO_IT {

    private IAlumnoDAO alumnoDAO;
    private JdbcDatabaseTester databaseTester;
    private IDatabaseConnection connection;

    @BeforeEach
    public void setUp() throws Exception {
        String cadena_conexionDB = "jdbc:mysql://localhost:3306/matriculacion?useSSL=false&allowPublicKeyRetrieval=true";
        String claseDriver = "com.mysql.jdbc.Driver";
        databaseTester = new MiJdbcDatabaseTester(claseDriver, cadena_conexionDB, "ppss_user", "ppss-2025");

        //obtenemos la conexión a la BD
        connection = databaseTester.getConnection();

        //inicializamos el dataset para inicializar la BD
        IDataSet dataSet = new FlatXmlDataFileLoader().load("/tabla-inicial.xml");
        //inyectamos el dataset
        databaseTester.setDataSet(dataSet);

        //inicializamos la BD con el dataset inicial
        databaseTester.onSetup();

        alumnoDAO = new FactoriaDAO().getAlumnoDAO();

    }

    @Test
    public void testA1() throws Exception {
        AlumnoTO alumno = new AlumnoTO();
        alumno.setNif("33333333C");
        alumno.setNombre("Elena Aguirre Juarez");
        alumno.setFechaNacimiento(LocalDate.of(1985, 02, 22));

        //invocamos a la sut
        Assertions.assertDoesNotThrow(() -> alumnoDAO.addAlumno(alumno));

        //recuperamos los datos de la BD después de invocar a la  SUT
        IDataSet databaseDataSet = connection.createDataSet();
        ITable actualTable = databaseDataSet.getTable("alumnos");

        //creamos el dataset con el resultado esperado
        IDataSet expectedDataSet = new FlatXmlDataFileLoader().load("/tabla-esperada-testA1.xml");
        ITable expectedTable = expectedDataSet.getTable("alumnos");

        Assertion.assertEquals(expectedTable, actualTable);
    }

    @Test
    public void testA2() throws Exception {
        AlumnoTO alumno = new AlumnoTO();
        alumno.setNif("11111111A");
        alumno.setNombre("Alfonso Ramirez Ruiz");
        alumno.setFechaNacimiento(LocalDate.of(1982, 02, 22));

        //invocamos a la sut
        Exception ex = assertThrows(DAOException.class, () -> alumnoDAO.addAlumno(alumno));

        //Comparamos el resultado
        Assertions.assertTrue(ex.getMessage().contains("Error al conectar con BD"));
    }

    @Test
    public void testA3() throws Exception {
        AlumnoTO alumno = new AlumnoTO();
        alumno.setNif("44444444D");
        alumno.setFechaNacimiento(LocalDate.of(1982, 02, 22));

        //invocamos a la sut
        Exception ex = assertThrows(DAOException.class, () -> alumnoDAO.addAlumno(alumno));

        //Comparamos el resultado
        Assertions.assertTrue(ex.getMessage().contains("Error al conectar con BD"));
    }

    @Test
    public void testA4() throws Exception {
        AlumnoTO alumno = new AlumnoTO();

        //invocamos a la sut
        Exception ex = assertThrows(DAOException.class, () -> alumnoDAO.addAlumno(alumno));

        //Comparamos el resultado
        Assertions.assertTrue(ex.getMessage().contains("Alumno nulo"));
    }

    @Test
    public void testA5() throws Exception {
        AlumnoTO alumno = new AlumnoTO();
        alumno.setNombre("Pedro Garcia Lopez");
        alumno.setFechaNacimiento(LocalDate.of(1982, 02, 22));

        //invocamos a la sut
        Exception ex = assertThrows(DAOException.class, () -> alumnoDAO.addAlumno(alumno));

        //Comparamos el resultado
        Assertions.assertTrue(ex.getMessage().contains("Error al conectar con BD"));
    }

    @Test
    public void testB1() throws Exception {
        String nif = "11111111A";

        //invocamos a la sut
        Assertions.assertDoesNotThrow(() -> alumnoDAO.delAlumno(nif));

        //recuperamos los datos de la BD
        IDataSet databaseDataSet = connection.createDataSet();
        ITable actualTable = databaseDataSet.getTable("alumnos");

        //creamos el  dataset con el resultado esperado
        IDataSet expectedDataSet = new FlatXmlDataFileLoader().load("/tabla-esperada-testB1.xml");
        ITable expectedTable = expectedDataSet.getTable("alumnos");

        Assertion.assertEquals(expectedTable, actualTable);
    }

    @Test
    public void testB2() throws Exception {
        String nif = "33333333C";

        //invocamos a la sut
        Exception ex = assertThrows(DAOException.class, () -> alumnoDAO.delAlumno(nif));

        //Comparamos el resultado
        Assertions.assertTrue(ex.getMessage().contains("No se ha borrado ningún alumno"));
    }
}

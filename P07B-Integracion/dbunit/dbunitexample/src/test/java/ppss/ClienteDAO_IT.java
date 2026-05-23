 package ppss;

import org.dbunit.Assertion;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.util.fileloader.FlatXmlDataFileLoader;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.sql.SQLException;

 /* IMPORTANTE:
     Dado que prácticamente todos los métodos de dBUnit lanzan una excepción,
     vamos a usar "throws Esception" en los métodos, para que el código quede más
     legible sin necesidad de usar un try..catch o envolver cada sentencia dbUnit
     con un assertDoesNotThrow()
     Es decir, que vamos a primar la legibilidad de los tests.
     Si la SUT puede lanza una excepción, SIEMPRE usaremos assertDoesNotThrow para
     invocar a la sut cuando no esperemos que se lance dicha excepción (independientemente de que hayamos propagado las excepciones provocadas por dbunit).
 */
public class ClienteDAO_IT {
  
  private ClienteDAO clienteDAO; //SUT
  private IDatabaseTester databaseTester;
  private IDatabaseConnection connection;

  @BeforeEach
  public void setUp() throws Exception {

    String cadena_conexionDB = "jdbc:mysql://localhost:3306/DBUNIT?useSSL=false&allowPublicKeyRetrieval=true";
    databaseTester = new JdbcDatabaseTester("com.mysql.cj.jdbc.Driver",
            "jdbc:mysql://localhost:3306/DBUNIT?useSSL=false&allowPublicKeyRetrieval=true", "ppss_user", "ppss-2025");
    connection = databaseTester.getConnection();

    clienteDAO = new ClienteDAO();
  }

  @Test
  public void D1_insert_should_add_John_to_cliente_when_John_does_not_exist() throws Exception {
    Cliente cliente = new Cliente(1,"John", "Smith");
    cliente.setDireccion("1 Main Street");
    cliente.setCiudad("Anycity");

    //inicializamos la BD
    IDataSet dataSet = new FlatXmlDataFileLoader().load("/cliente-init.xml");
    databaseTester.setDataSet(dataSet);
    databaseTester.onSetup();
    
     //invocamos a la sut
    Assertions.assertDoesNotThrow(()->clienteDAO.insert(cliente));

    //recuperamos los datos de la BD después de invocar al SUT
    IDataSet databaseDataSet = connection.createDataSet();
    ITable actualTable = databaseDataSet.getTable("cliente"); 

    //creamos el dataset con el resultado esperado
    IDataSet expectedDataSet = new FlatXmlDataFileLoader().load("/cliente-esperado.xml");
    ITable expectedTable = expectedDataSet.getTable("cliente");

    Assertion.assertEquals(expectedTable, actualTable);

   }

  @Test
  public void D2_delete_should_remove_John_from_cliente_when_John_is_in_table() throws Exception {
    Cliente cliente =  new Cliente(1,"John", "Smith");
    cliente.setDireccion("1 Main Street");
    cliente.setCiudad("Anycity");

    //inicializamos la BD
    IDataSet dataSet = new FlatXmlDataFileLoader().load("/cliente-esperado.xml");
    databaseTester.setDataSet(dataSet);
    databaseTester.onSetup();

    //invocamos a la SUT
    Assertions.assertDoesNotThrow(()->clienteDAO.delete(cliente));

    //recuperamos los datos de la BD después de invocar al SUT
    IDataSet databaseDataSet = connection.createDataSet();
    ITable actualTable = databaseDataSet.getTable("cliente");
    
    //creamos el dataset con el resultado esperado
    IDataSet expectedDataSet = new FlatXmlDataFileLoader().load("/cliente-init.xml");
    ITable expectedTable = expectedDataSet.getTable("cliente");

    Assertion.assertEquals(expectedTable, actualTable);
  }

  @Test
  public void D3_should_try_insert_a_client_who_alredy_exists() throws Exception {
    Cliente cliente = new Cliente(1,"John", "Smith");
    cliente.setDireccion("1 Main Street");
    cliente.setCiudad("Anycity");

    //inicializamos la  BD
    IDataSet dataSet = new FlatXmlDataFileLoader().load("/cliente-dos-registros.xml");
    databaseTester.setDataSet(dataSet);
    databaseTester.onSetup();

    //invocamos a la sut
    Exception ex = Assertions.assertThrows(SQLException.class, ()-> clienteDAO.insert(cliente));

    //comparamos mensaje de la excepción
    Assertions.assertTrue(ex.getMessage().contains("Duplicate entry"));
  }

  @Test
   public void D4_should_try_to_delete_a_non_existing_client() throws Exception {
    Cliente cliente = new Cliente(3,"Miguel", "Smith");
    cliente.setDireccion("3 Main Street");
    cliente.setCiudad("Cadiz");

    //inicializamos la BD
    IDataSet dataSet = new FlatXmlDataFileLoader().load("/cliente-dos-registros.xml");
    databaseTester.setDataSet(dataSet);
    databaseTester.onSetup();

    //invocamos a la sut
    Exception ex = Assertions.assertThrows(SQLException.class, ()-> clienteDAO.delete(cliente));

    //comparamos el mensaje con la excepción
    Assertions.assertTrue(ex.getMessage().contains("Delete failed"));
  }

  @Test
   public void D5_should_update_an_existing_client() throws Exception {
    Cliente cliente = new Cliente(1, "John", "Smith");
    cliente.setDireccion("Other Street");
    cliente.setCiudad("NewCity");

    //inicializamos la BD
    IDataSet dataSet = new FlatXmlDataFileLoader().load("/cliente-esperado.xml");
    databaseTester.setDataSet(dataSet);
    databaseTester.onSetup();

    //invocamos al sut
    Assertions.assertDoesNotThrow(() -> clienteDAO.update(cliente));

    //recuperamos la BD después de invocar al Sut
   IDataSet databaseDataSet = connection.createDataSet();
   ITable actualTable = databaseDataSet.getTable("cliente");

   //creamos el dataset con el resultado esperado
    IDataSet expectedDataSet = new FlatXmlDataFileLoader().load("/cliente-esperado-update.xml");
    ITable exppectedTable = expectedDataSet.getTable("cliente");

    Assertion.assertEquals(exppectedTable, actualTable);
  }

   @Test
   public void D6_should_retrieve_client_with_id_equal_1() throws Exception {
     Cliente expectedCliente = new Cliente(1, "John", "Smith");
     expectedCliente.setDireccion("1 Main Street");
     expectedCliente.setCiudad("Anycity");

     //inicializamos la BD
     IDataSet dataSet = new FlatXmlDataFileLoader().load("/cliente-esperado.xml");
     databaseTester.setDataSet(dataSet);
     databaseTester.onSetup();

     //invocamos al sut
     Cliente cliente = Assertions.assertDoesNotThrow(() -> clienteDAO.retrieve(1));

     assertAll(
             () -> assertEquals(expectedCliente.getId(), cliente.getId()),
             () -> assertEquals(expectedCliente.getNombre(), cliente.getNombre()),
             () -> assertEquals(expectedCliente.getApellido(), cliente.getApellido()),
             () -> assertEquals(expectedCliente.getDireccion(), cliente.getDireccion()),
             () -> assertEquals(expectedCliente.getCiudad(), cliente.getCiudad())
     );
  }

}
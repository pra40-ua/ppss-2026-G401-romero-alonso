package ejercicio1.sinPageObject;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class TestLogin {

    WebDriver driver;

    @BeforeEach
    void setUp(){
        ChromeOptions co = new ChromeOptions();
        co.setExperimentalOption("excludeSwitches", Arrays.asList("enable-automation"));
        driver = new ChromeDriver(co);
        driver.get("https://jpetstore.aspectran.com");
    }

    @AfterEach
    void tearDown(){
        driver.quit();
    }

    @Test
    void R1_loginOK(){

        // Comprobamos el titulo
        String titulo = driver.getTitle();
        assertEquals("JPetStore Demo", titulo);

        // Hacemos click
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement signInLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Sign In")));
        signInLink.click();

        // Verificar título
        WebElement tituloFormulario = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h5.card-title")));
        assertEquals("Please enter your username and password.", tituloFormulario.getText());

        // Rellenar datos
        WebElement usernameInput = driver.findElement(By.id("username"));
        WebElement passwordInput = driver.findElement(By.id("password"));

        usernameInput.clear();
        usernameInput.sendKeys("j2ee");
        passwordInput.clear();
        passwordInput.sendKeys("j2ee");

        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button.btn-primary")));
        loginButton.click();

        // Guardamos mensaje de bienvenida
        WebElement mensajeBienvenida = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.text-end.pb-2")));
        String textoBienvenida = mensajeBienvenida.getText().trim();

        // Navegar My Account
        WebElement dropdownButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("dropdownMenuButton")));
        dropdownButton.click();

        WebElement myAccountLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("My Account")));
        myAccountLink.click();

        // Verificar formulario
        WebElement formulario = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h3[text()='User Information']")));
        assertEquals("User Information", formulario.getText());

        // Leer formulario
        WebElement nomUsuario = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("firstName")));
        String nombreReal = nomUsuario.getDomProperty("value");

        // Verificar texto completo
        assertEquals("Welcome " + nombreReal + "!", textoBienvenida);
    }

    @Test
    void R2_loginFail(){

        // Verificar título
        String titulo = driver.getTitle();
        assertEquals("JPetStore Demo", titulo);

        // Click en singIn
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement singin = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Sign In")));
        singin.click();

        // Verificar titulo formulario
        WebElement tituloFormulario = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h5.card-title")));
        assertEquals("Please enter your username and password.", tituloFormulario.getText());

        // Registro
        WebElement usernameInput = driver.findElement(By.id("username"));
        WebElement passwordInput = driver.findElement(By.id("password"));

        passwordInput.clear();
        passwordInput.sendKeys("abcd");

        WebElement login = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button.btn-primary")));
        login.click();

        // Verificar mensaje de error
        WebElement mensajeError = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.alert-danger")));
        assertEquals("Invalid username or password. Signon failed.", mensajeError.getText().trim());

    }

}

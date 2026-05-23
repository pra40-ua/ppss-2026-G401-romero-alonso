package ejercicio2.conPO;

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

public class TestLogin2 {

    WebDriver driver;
    HomePage home;

    @BeforeEach
    public void setUp() {
        ChromeOptions co = new ChromeOptions();
        co.setExperimentalOption("excludeSwitches", Arrays.asList("enable-automation"));
        driver = new ChromeDriver(co);
        driver.get("https://jpetstore.aspectran.com");
        home = new HomePage(driver);
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    @Test
    void R3_loginOK(){

        assertEquals("JPetStore Demo", home.getTitle());

        LoginPage loginPage = home.goToSignIn();

        assertEquals("Please enter your username and password.", loginPage.getFormTitle());

        home = loginPage.loginSuccessful("j2ee","j2ee");

        String welcome = home.getWelcomeMessage();

        MyAccountPage account = home.goToMyAccount();

        assertEquals("User Information", account.getFormTitle());

        String nombre = account.getFirstName();

        assertEquals("Welcome " + nombre +"!", welcome);

    }

    @Test
    void R4_loginFail(){

        assertEquals("JPetStore Demo", home.getTitle());

        LoginPage loginPage = home.goToSignIn();

        assertEquals("Please enter your username and password.", loginPage.getFormTitle());

        LoginPage loginPage2 = loginPage.loginFailed("abdc");

        assertEquals("Invalid username or password. Signon failed.", loginPage2.getErrorMessage());

    }

}

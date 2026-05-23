package ejercicio2.conPO;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage {

    WebDriver driver;
    WebDriverWait wait;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public String getTitle() {
        return driver.getTitle();
    }

    public LoginPage goToSignIn() {
        WebElement signInLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Sign In")));
        signInLink.click();
        return new LoginPage(driver);
    }

    public String getWelcomeMessage() {
        WebElement  mensaje = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.text-end.pb-2")));
        return mensaje.getText().trim();
    }

    public MyAccountPage goToMyAccount() {
        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(By.id("dropdownMenuButton")));
        dropdown.click();
        WebElement myAccountLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("My Account")));
        myAccountLink.click();
        return new MyAccountPage(driver);
    }
}

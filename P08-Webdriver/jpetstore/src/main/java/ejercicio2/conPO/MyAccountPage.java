package ejercicio2.conPO;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MyAccountPage {

    private WebDriver driver;
    private WebDriverWait wait;

    public MyAccountPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public String getFormTitle() {
        WebElement form = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h3[text()='User Information']")));
        return form.getText().trim();
    }

    public String getFirstName() {
        WebElement usuario = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("firstName")));
        return usuario.getDomProperty("value");
    }



}

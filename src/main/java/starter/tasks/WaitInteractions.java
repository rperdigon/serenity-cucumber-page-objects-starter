package starter.tasks;

import net.thucydides.core.pages.PageObject;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import starter.pages.factory.PageFactory;

import java.time.Duration;

public class WaitInteractions extends PageObject {

    public WebElement waitElementIsPresent(WebElement element) {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10), Duration.ofSeconds(1));
        return wait.until(ExpectedConditions.presenceOfElementLocated(PageFactory.getWebElementSelector(element)));
    }

    public WebElement waitElementVisible(WebElement element) {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10), Duration.ofSeconds(1));
        return wait.until(ExpectedConditions.visibilityOf((element)));
    }
}

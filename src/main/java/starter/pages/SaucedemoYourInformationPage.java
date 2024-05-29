package starter.pages;

import org.openqa.selenium.By;

import java.util.Map;

import static starter.Constants.YOURINFORMATION;

public class SaucedemoYourInformationPage extends AbstractPage {
    @Override
    public Map mapSelectors() {
        mapSelectors.put(YOURINFORMATION, By.cssSelector("div.checkout_info"));
        mapSelectors.put("first name", By.id("first-name"));
        mapSelectors.put("last name", By.id("last-name"));
        mapSelectors.put("zip/postal code", By.id("postal-code"));
        mapSelectors.put("continue", By.id("continue"));
        return mapSelectors;
    }
}

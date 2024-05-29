package starter.pages;

import org.openqa.selenium.By;

import java.util.Map;

import static starter.Constants.*;

public class SaucedemoLoginPage extends AbstractPage {
    @Override
    public Map mapSelectors() {
        mapSelectors.put(LOGIN, By.id("login-button"));
        mapSelectors.put("user", By.id("user-name"));
        mapSelectors.put("pass", By.id("password"));
        return mapSelectors;
    }
}

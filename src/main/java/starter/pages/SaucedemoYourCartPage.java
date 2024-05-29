package starter.pages;

import org.openqa.selenium.By;
import static starter.Constants.*;
import java.util.Map;

public class SaucedemoYourCartPage extends AbstractPage {
    @Override
    public Map mapSelectors() {
        mapSelectors.put(YOURCART, By.id("cart_contents_container"));
        mapSelectors.put("checkout", By.id("checkout"));
        return mapSelectors;
    }
}

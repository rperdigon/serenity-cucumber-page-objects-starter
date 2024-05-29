package starter.pages;

import org.openqa.selenium.By;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractPage {
    public Map<String, By> mapSelectors = new HashMap<String, By>();

    public abstract Map<String, By> mapSelectors();

    public By getSelector(String selector) {
        return mapSelectors().get(selector.toLowerCase());
    }
}

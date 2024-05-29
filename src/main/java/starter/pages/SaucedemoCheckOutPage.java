package starter.pages;

import org.openqa.selenium.By;

import java.util.Map;

import static starter.Constants.CHECKOUT;

public class SaucedemoCheckOutPage extends AbstractPage {
    @Override
    public Map mapSelectors() {
        mapSelectors.put(CHECKOUT, By.cssSelector("div.cart_item_label"));
        mapSelectors.put("finish", By.id("finish"));
        mapSelectors.put("sauce labs backpack", By.cssSelector("div.inventory_item_name"));
        return mapSelectors;
    }
}

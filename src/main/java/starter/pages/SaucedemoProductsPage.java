package starter.pages;

import org.openqa.selenium.By;

import java.util.Map;

import static starter.Constants.PRODUCTS;

public class SaucedemoProductsPage extends AbstractPage {
    @Override
    public Map mapSelectors() {
        mapSelectors.put(PRODUCTS, By.cssSelector("div.inventory_item"));
        mapSelectors.put("title item", By.cssSelector("div.inventory_item"));
        mapSelectors.put("add to cart", By.cssSelector("button.btn"));
        mapSelectors.put("cart icon", By.cssSelector("#shopping_cart_container > a"));
        return mapSelectors;
    }

}

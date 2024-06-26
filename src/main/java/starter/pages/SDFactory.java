package starter.pages;

import net.serenitybdd.core.pages.PageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import java.util.HashMap;
import java.util.Map;

import static starter.Constants.*;

public class SDFactory extends PageObject {

    /**
     * Map associating page names with their corresponding instances.
     */
    public static Map<String, AbstractPage> map = new HashMap<String, AbstractPage>() {{
        put(LOGIN, PageFactory.initElements(getDriverStatic(), SaucedemoLoginPage.class));
        put(PRODUCTS, PageFactory.initElements(getDriverStatic(), SaucedemoProductsPage.class));
        put(YOURCART, PageFactory.initElements(getDriverStatic(), SaucedemoYourCartPage.class));
        put(YOURINFORMATION, PageFactory.initElements(getDriverStatic(), SaucedemoYourInformationPage.class));
        put(CHECKOUT, PageFactory.initElements(getDriverStatic(), SaucedemoCheckOutPage.class));
    }};

    /**
     * The currently selected page.
     */
    public static AbstractPage currentPage;

    /**
     * Sets the current page based on the provided page name.
     * @param page Name of the page to set as the current page.
     */
    public static void setCurrentPage(String page) {
        currentPage = map.get(page);
    }

    /**
     * Gets a static instance of the WebDriver.
     * @return Static instance of WebDriver.
     */
    public static WebDriver getDriverStatic() {
        return new SDFactory().getDriver();
    }

    /**
     * Gets a By object from a WebElement using the information from its toString().
     * @param element WebElement from which to obtain the By selector.
     * @return By object corresponding to the WebElement's selector.
     * @throws IllegalArgumentException If the toString() format is not recognized.
     */
    public static By getWebElementSelector(WebElement element) {
        // Get the string representation of the WebElement
        String elementToString = element.toString();
        String[] parts = elementToString.split(" -> ");

        if (parts.length > 1) {
            // Extract information about the WebElement's selector
            String selectorInfo = parts[1].replace("]", "");
            String[] selectorParts = selectorInfo.split(": ");

            if (selectorParts.length == 2) {
                // Get selector type and value, and return the corresponding By object
                String selectorType = selectorParts[0].trim();
                String selectorValue = selectorParts[1].trim();

                switch (selectorType) {
                    case "id":
                        return By.id(selectorValue);
                    case "css selector":
                        return By.cssSelector(selectorValue);
                    case "class name":
                        return By.className(selectorValue);
                    case "name":
                        return By.name(selectorValue);
                    case "link text":
                        return By.linkText(selectorValue);
                    case "partial link text":
                        return By.partialLinkText(selectorValue);
                    case "tag name":
                        return By.tagName(selectorValue);
                    case "xpath":
                        return By.xpath(selectorValue);
                    default:
                        throw new IllegalArgumentException("Unsupported selector type: " + selectorType);
                }
            }
        }

        // If the format is not recognized, throw an exception
        throw new IllegalArgumentException("Unrecognized format of WebElement.toString()");
    }
}

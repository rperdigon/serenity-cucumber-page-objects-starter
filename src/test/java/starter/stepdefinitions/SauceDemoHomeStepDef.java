package starter.stepdefinitions;

import io.cucumber.java.en.*;
import starter.tasks.ElementInteraction;
import starter.tasks.ElementVisibilityVerifier;

public class SauceDemoHomeStepDef {
    /** Item List actions */

    /**
     * Clicks on a specific element in a list based on the title of the element.
     *
     * @param elementClick The element to click on.
     * @param listElement  The list containing the elements.
     * @param titleElement The title of the element to be clicked.
     */
    @When("click {string} on item in the list {string} whose title is {string}")
    public void clickOnItemInTheListWhoseTitleIs(String elementClick, String listElement, String titleElement) {
        // Call a method to click on the element in the list with the specified title
        ElementInteraction.clickOnElementInListWithTitleContaining(elementClick, listElement, titleElement);
    }

    /**
     * Card actions
     */
    private static final String CART_ICON = "cart icon";

    /**
     * Verify the visibility of the cart number and check that it matches the given number of products in the cart
     *
     * @param numberOfProductInCart the number of products expected to be in the cart
     */
    @Then("we check that the cart has {string} product")
    public void weCheckThatTheCartHasProduct(String numberOfProductInCart) {
        // Verify the visibility of the cart number
        ElementVisibilityVerifier.verifyElementIsVisible(CART_ICON);
        // Check that the cart number matches the given number of products in the cart
        ElementVisibilityVerifier.verifyElementNumberIs(numberOfProductInCart, CART_ICON);
    }

    /**
     * Verifies the existence of a product.
     *
     * @param product The name of the product to verify.
     */
    @Then("verify that the product {string} exists")
    public void verifyThatTheProductExists(String product) {
        // Verify that the product element is visible
        ElementVisibilityVerifier.verifyElementIsVisible(product);

        // Verify that the product element has the correct name
        ElementVisibilityVerifier.verifyElementNameIs(product);
    }
}

package starter.tasks;

import net.serenitybdd.screenplay.GivenWhenThen;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.matchers.WebElementStateMatchers;

import static net.serenitybdd.screenplay.questions.Text.*;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isNotVisible;
import static net.serenitybdd.screenplay.questions.WebElementQuestion.*;
import static org.hamcrest.Matchers.equalTo;
import static starter.pages.factory.PageFactory.*;

public class ElementVisibilityVerifier {
    /**
     * Verifies if the given web element is visible
     *
     * @param element The element to be verified
     */
    public static void verifyElementIsVisible(String element) {
        // Use the actor in the spotlight to check if the element is visible
        OnStage.theActorInTheSpotlight().should(
                "check that the element is visible: " + element,
                GivenWhenThen.seeThat(
                        // Get the web element using the selector for the current page
                        the(getCurrentPage().getSelector(element)),
                        WebElementStateMatchers.isVisible()
                ));
    }

    /**
     * Verifies that the given element is not visible on the current page.
     *
     * @param element the element to be verified
     */
    public static void verifyElementIsNotVisible(String element) {
        // Use the actor in the spotlight to check if the element is not visible
        OnStage.theActorInTheSpotlight().should(
                // Provide a description for the verification
                "check that the element is not visible: " + element,
                // Use WebElementQuestion to locate the element and WebElementStateMatchers to check its visibility
                GivenWhenThen.seeThat(
                        the(getCurrentPage().getSelector(element)),
                        isNotVisible()
                ));
    }

    /**
     * Verify if the element number matches the expected number.
     *
     * @param number  the expected number
     * @param element the element to verify
     */
    public static void verifyElementNumberIs(String number, String element) {
        // Use the Actor to verify the element number
        OnStage.theActorInTheSpotlight().should(
                "check that the element number is: " + number,
                GivenWhenThen.seeThat(
                        of(getCurrentPage().getSelector(element)).asString(),
                        equalTo(number)
                )
        );
    }

    /**
     * Verify if the element name matches the expected name.
     *
     * @param element the element to verify
     */
    public static void verifyElementNameIs(String element) {
        // Use the Actor to verify the element name
        OnStage.theActorInTheSpotlight().should(
                "check that the element name: " + element,
                GivenWhenThen.seeThat(
                        of(getCurrentPage().getSelector(element)).asString(),
                        equalTo(element)
                )
        );
    }
}

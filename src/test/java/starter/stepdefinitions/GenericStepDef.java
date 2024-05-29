package starter.stepdefinitions;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.Before;
import io.cucumber.java.en.*;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;

import static starter.tasks.ElementInteraction.*;
import static starter.tasks.IsLoad.*;
import static starter.tasks.NavigateTo.*;
import static starter.tasks.SendTextTo.*;

public class GenericStepDef {
    //Actor for scenario
    String actor = "user not logged";

    /**
     * Before step for scenario uses
     */
    @Before
    public void setTheStage() {
        OnStage.setTheStage(new OnlineCast());
    }

    @Given("go to web {string}")
    public void goToWeb(String url) {
        theWebSite(url, actor);
    }

    @Then("check to {string} has loaded")
    public void checkToHasLoaded(String page) {
        isLoadPage(page.toLowerCase());
    }

    @When("click in {string}")
    public void clickIn(String element) {
        clickOnTarget(element);
    }

    @When("send text {string} to element {string}")
    public void sendTextToElement(String text, String element) {
        input(text, element);
    }

    @When("send text to:")
    public void sentTextToTable(DataTable table) {
        table(table);
    }
}

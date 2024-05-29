package starter.tasks;

import io.cucumber.datatable.DataTable;
import net.serenitybdd.screenplay.actions.Clear;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.actors.OnStage;
import starter.pages.SDFactory;

import java.util.List;

public class SendTextTo {
    /**
     * Input the given text into the specified element.
     *
     * @param text    the text to input
     * @param element the element to input the text into
     */
    public static void input(String text, String element) {
        // Clear the specified element
        OnStage.theActorInTheSpotlight().attemptsTo(
                Clear.field(SDFactory.currentPage.getSelector(element)),
                // Enter the given text into the specified element
                Enter.theValue(text).into(SDFactory.currentPage.getSelector(element))
        );
    }

    /**
     * Iterates through the rows of a DataTable and calls the input method with the elements of each row.
     *
     * @param table the DataTable to iterate through
     */
    public static void table(DataTable table) {
        for (List<String> row : table.asLists(String.class)) {
            input(row.get(1), row.get(0));
        }
    }
}

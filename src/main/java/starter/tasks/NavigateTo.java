package starter.tasks;

import net.serenitybdd.screenplay.actions.Open;
import net.serenitybdd.screenplay.actors.OnStage;

public class NavigateTo {
    public static void theWebSite(String url, String actor) {
        OnStage.theActorCalled(actor).attemptsTo(Open.url(url));
    }
}

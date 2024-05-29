package starter.tasks;


import net.serenitybdd.core.Serenity;
import net.thucydides.core.pages.PageObject;
import starter.pages.SDFactory;

public class IsLoad extends PageObject {
    public static void isLoadPage(String pageName) {
        SDFactory.setCurrentPage(pageName);
        Serenity.recordReportData().withTitle("visible page: ").andContents(pageName);
        ElementVisibilityVerifier.verifyElementIsVisible(pageName);
    }
}

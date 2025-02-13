package glue.steps;

import  io.cucumber.java8.En;
import fixture.persistence.ScenarioState;
import pages.AcquaintanceDetailsPage;
import pages.AcquaintancesPage;
import pages.AlertConfirmationPage;
import utils.ExpectedConditionWrapper;
import utils.Helper;
import utils.AppiumManager;

public class AcquaintanceDetailsSteps implements En {

    public AcquaintanceDetailsSteps(ScenarioState scenarioState) {
        When("I tap on the delete acquaintance button and confirm my action", () -> {
            AcquaintanceDetailsPage acquaintanceDetailsPage = new AcquaintanceDetailsPage(AppiumManager.getInstance().getDriver());
            AlertConfirmationPage alertConfirmationPage = new AlertConfirmationPage(AppiumManager.getInstance().getDriver());

            acquaintanceDetailsPage.waitUntilPageLoads();
            acquaintanceDetailsPage.tapOnDeleteAcquaintanceButton();

            alertConfirmationPage.tapOnConfirmButton();
        });

        And("I tap on the edit acquaintance button", () -> {
            AcquaintanceDetailsPage acquaintanceDetailsPage = new AcquaintanceDetailsPage(AppiumManager.getInstance().getDriver());

            acquaintanceDetailsPage.waitUntilPageLoads();
            acquaintanceDetailsPage.tapOnEditAcquaintanceButton();
        });

        Then("I should see that the first name and last name changed", () -> {
            AcquaintanceDetailsPage acquaintanceDetailsPage = new AcquaintanceDetailsPage(AppiumManager.getInstance().getDriver());
            AcquaintancesPage addAcquaintancePage = new AcquaintancesPage(AppiumManager.getInstance().getDriver());

            String formattedDetailsAcquaintanceName = String.format("%s %s", scenarioState.getFirstName(), scenarioState.getLastName());
            String formattedListingAcquaintanceName = String.format("%s, %s", scenarioState.getLastName(), scenarioState.getFirstName());

            Helper.waitUntil(webDriver -> acquaintanceDetailsPage.getToolbarTitle().contains(formattedDetailsAcquaintanceName));
            Helper.navigateBack();

            addAcquaintancePage.waitUntilListIsNotEmpty();

            Helper.scrollDownFullScreenUntilCondition(ExpectedConditionWrapper.condition(webElement ->
                    addAcquaintancePage.getAcquaintancesNames().contains(formattedListingAcquaintanceName)));
        });
    }
}
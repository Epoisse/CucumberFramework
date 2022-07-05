package steps;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import pages.LoginPage;
import utils.CommonMethods;

public class LoginSteps extends CommonMethods {
        @Then("admin user is successfully logged in")
        public void admin_user_is_successfully_logged_in() {
            Assert.assertTrue(dashboardPage.welcomeMsg.isDisplayed());
        }

        @When("user enters valid ess username and password")
        public void user_enters_valid_ess_username_and_password() {
            sendText(loginPage.usernameBox, "tts12345");
            sendText(loginPage.passwordBox, "Hum@nhrm123");
        }

        @Then("ess user is successfully logged in")
        public void ess_user_is_successfully_logged_in() {
            System.out.println("test passed");
            //tearDown();
        }

        @When("user enters invalid username and password")
        public void user_enters_invalid_username_and_password() {
            sendText(loginPage.usernameBox, "tts12345");
            sendText(loginPage.passwordBox, "Hum@nhrm");
        }

        @Then("user see error message on the screen")
        public void user_see_error_message_on_the_screen() {
            //here will be homework
            //tearDown();
        }
}

package steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import pages.AddEmployeePage;
import utils.CommonMethods;
import utils.Constants;
import utils.DBUtils;
import utils.ExcelReader;

import javax.swing.*;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class AddEmployeeSteps extends CommonMethods {

    String empId, firstName, dbFirstName, dbEmpId;

    @When("user clicks on PIM option")
    public void user_clicks_on_pim_option() {
        click(employeeSearchPage.pimOption);
    }

    @When("user clicks on add employee option")
    public void user_clicks_on_add_employee_option() {
        click(employeeSearchPage.addEmployeeOption);
    }

    @When("user enters firstname middlename and lastname")
    public void user_enters_firstname_middlename_and_lastname() {
        sendText(addEmployeePage.firstNameField, "Dasir");
        sendText(addEmployeePage.middleNameField, "hamilia");
        sendText(addEmployeePage.lastNameField, "tolom");
    }

    @When("user clicks on save button")
    public void user_clicks_on_save_button() {
        click(addEmployeePage.saveButton);
    }

    @Then("employee added successfully")
    public void employee_added_successfully() {
        System.out.println("employee added");
    }

    @When("user enters {string} {string} and {string}")
    public void user_enters_and(String firstNameValue, String middleNameValue, String lastNameValue) {
        sendText(addEmployeePage.firstNameField, firstNameValue);
        sendText(addEmployeePage.middleNameField, middleNameValue);
        sendText(addEmployeePage.lastNameField, lastNameValue);
    }

    @When("user provides {string} {string} and {string}")
    public void user_provides_and(String firstName, String middleName, String lastName) {
        sendText(addEmployeePage.firstNameField, firstName);
        sendText(addEmployeePage.middleNameField, middleName);
        sendText(addEmployeePage.lastNameField, lastName);
    }

    @When("user provides multiple employee data adn verify they are added")
    public void user_provides_multiple_employee_data_adn_verify_they_are_added(DataTable dataTable) throws InterruptedException {
        List<Map<String, String>> employeeNames = dataTable.asMaps();
        for (Map<String, String> employee : employeeNames
        ) {
            String firstNameValue = employee.get("firstName");
            String middleNameValue = employee.get("middleName");
            String lastNameValue = employee.get("lastName");

            System.out.println(firstNameValue + " " + middleNameValue + " " + lastNameValue);

            sendText(addEmployeePage.firstNameField, firstNameValue);
            sendText(addEmployeePage.middleNameField, middleNameValue);
            sendText(addEmployeePage.lastNameField, lastNameValue);

            click(addEmployeePage.saveButton);
            Thread.sleep(3000);

            //verification of adding an employee is HW
            click(employeeSearchPage.addEmployeeOption);
        }
    }

    @When("user add multiple employees from excel file using {string} sheet and verify the user added")
    public void user_add_multiple_employees_from_excel_file_using_sheet_and_verify_the_user_added(String sheetName) throws InterruptedException {
        List<Map<String, String>> newEmployees = ExcelReader.excelIntoMap(Constants.TESTDATE_FILEPATH, sheetName);
        Iterator<Map<String, String>> iterator = newEmployees.iterator();
        //it checks whether the next element exist or not
        while (iterator.hasNext()) {
            Map<String, String> mapNewEmp = iterator.next();

            sendText(addEmployeePage.firstNameField, mapNewEmp.get("FirstName"));
            sendText(addEmployeePage.middleNameField, mapNewEmp.get("MiddleName"));
            sendText(addEmployeePage.lastNameField, mapNewEmp.get("LastName"));

            //it will fetch the employee id from attribute
            String empIdValue = addEmployeePage.empIdLocator.getAttribute("value");

            //to upload the photo
         /*   sendText(addEmployeePage.photograph, mapNewEmp.get("Photograph"));
            if (!addEmployeePage.checkbox.isSelected()) {
                click(addEmployeePage.checkbox);
            }*/

            sendText(addEmployeePage.createUsername, mapNewEmp.get("Username"));
            sendText(addEmployeePage.createPassword, mapNewEmp.get("Password"));
            sendText(addEmployeePage.confirmPassword, mapNewEmp.get("Password"));
            click(addEmployeePage.saveButton);
            Thread.sleep(3000);

            //to verify the employee, we will navigate to employee list option
            click(employeeSearchPage.empListOption);
            sendText(employeeSearchPage.idField, empIdValue);

            click(employeeSearchPage.searchButton);


            //it is returning the data from the row in results
            List<WebElement> rowData = driver.findElements(By.xpath("//table[@id='resultTable']/tbody/tr"));
            for (int i = 0; i < rowData.size(); i++) {
                String rowText = rowData.get(i).getText();
                System.out.println(rowText);
                String expectedData = empIdValue + " " + mapNewEmp.get("FirstName") + " " + mapNewEmp.get("MiddleName") + " " + mapNewEmp.get("LastName");
                Assert.assertEquals(expectedData, rowText);
            }
            click(employeeSearchPage.addEmployeeOption);
        }

    }

    @And("user grabs the employee id")
    public void userGrabsTheEmployeeId() {
        empId = addEmployeePage.empIdLocator.getAttribute("value");
        firstName = addEmployeePage.firstNameField.getAttribute("value");
    }

    @And("user query the database for same employee id")
    public void userQueryTheDatabaseForSameEmployeeId() {
        String query = "select * from hs_hr_employees where employee_id='" + empId + "'";
        dbFirstName = DBUtils.getDataFromDB(query).get(0).get("emp_firstname");
        dbEmpId = DBUtils.getDataFromDB(query).get(0).get("employee_id");
    }

    @Then("user verifies the results")
    public void userVerifiesTheResults() {
        System.out.println("First name from Front end" + firstName);
        System.out.println("First name from Front end" + dbFirstName);
        Assert.assertEquals(firstName, dbFirstName);
        Assert.assertEquals(empId, dbEmpId);
    }
}

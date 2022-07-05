package API;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.Request;
import org.junit.runners.MethodSorters;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class HardCodedExamples {

    String baseURI = RestAssured.baseURI = "http://hrm.syntaxtechs.net/syntaxapi/api";
    String token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE2NTU2NDA3MDUsImlzcyI6ImxvY2FsaG9zdCIsImV4cCI6MTY1NTY4MzkwNSwidXNlcklkIjoiMzc4NiJ9.WPT50QvnwTdiAw0dwH5P_WskOqKBaO_OtV9MVe4N8KY";
    static String employeeId;

    @Test
    public void acreateEmployee() {
        RequestSpecification request = given().header("Content-Type", "application/json").
                header("Authorization", token).body("{\n" +
                        "  \"emp_firstname\": \"Donald\",\n" +
                        "  \"emp_lastname\": \"Biden\",\n" +
                        "  \"emp_middle_name\": \"Jr\",\n" +
                        "  \"emp_gender\": \"M\",\n" +
                        "  \"emp_birthday\": \"1983-06-11\",\n" +
                        "  \"emp_status\": \"Probation\",\n" +
                        "  \"emp_job_title\": \"President of the United States\"\n" +
                        "}");

        Response response = request.when().post("/createEmployee.php");

        response.prettyPrint();
        response.then().assertThat().statusCode(201);

        // Hamcrest matchers
        response.then().assertThat().body("Message", Matchers.equalTo("Employee Created"));
        response.then().assertThat().body("Employee.emp_firstname", equalTo("Donald"));

        //using jsonPath(), to specify the key in the body so that it returns the value against it
        employeeId = response.jsonPath().getString("Employee.employee_id");
    }

    @Test
    public void bgetCreatedEmployee() {
        RequestSpecification request = given().header("Content-Type", "application/json").
                header("Authorization", token).queryParam("employee_id", employeeId);

        Response response = request.when().get("/getOneEmployee.php");
        response.prettyPrint();

        response.then().assertThat().statusCode(200);

        String tempId = response.jsonPath().getString("employee.employee_id");
        System.out.println(tempId);
        Assert.assertEquals(tempId, employeeId);
    }

    public void cupdateEmployee() {
        RequestSpecification request = given().header("Content-Type", "application/json").
                header("Autorization", token).body(
                        "{\n" +
                                "  \"employee_id\": \" "+ employeeId + " \",\n" +
                                "  \"emp_firstname\": \"edward\",\n" +
                                "  \"emp_lastname\": \"sisi\",\n" +
                                "  \"emp_middle_name\": \"MS1\",\n" +
                                "  \"emp_gender\": \"M\",\n" +
                                "  \"emp_birthday\": \"1995-06-12\",\n" +
                                "  \"emp_status\": \"confirmed\",\n" +
                                "  \"emp_job_title\": \"Manager\"\n" +
                                "}");

        Response response = request.when().put("/updateEmployee.php");
        response.prettyPrint();
        response.then().assertThat().statusCode(200);
    }

    @Test
    public void dGetUpdatedEmployee() {
        RequestSpecification request = given().header("Content-Type", "application/json").
                header("Authorization", token).queryParam("employee_id", employeeId);

        Response response = request.when().get("/getOneEmployee.php");
        response.then().assertThat().statusCode(200);
        response.prettyPrint();
    }

    @Test
    public void eGetAllEmployees() {
        RequestSpecification request = given().header("Authorization", token).header("Content-Type", "application/json");

        Response response = request.when().get("/getAllEmployees.php");

        //it returns string of response
        String allEmployees = response.prettyPrint();

        //jsonPath() vs jsonPath
        //jsonPath is a class that contains method for converting the values into json object
        //jsonPath() is a mehtod belongs to jsonPath class

        JsonPath js = new JsonPath(allEmployees);

        //retrieving the total number of employees
        int count = js.getInt("Employees.size()");
        System.out.println(count);

        //to print only employee id of all the employees
        for (int i = 0; i < count; i++) {
            String empID = js.getString("Employees[" + i + "].employee_id");
            System.out.println(empID);
        }
    }

}

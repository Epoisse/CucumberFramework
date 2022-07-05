package utils;

import com.google.gson.JsonObject;
import org.json.JSONObject;

public class APIPayloadConstants {

    public static String createEmployeePayload() {
        return "{\n" +
                "  \"emp_firstname\":   \"Donald\",\n" +
                "  \"emp_lastname\":    \"Biden\",\n" +
                "  \"emp_middle_name\": \"Jr\",\n" +
                "  \"emp_gender\":      \"M\",\n" +
                "  \"emp_birthday\":    \"1983-06-11\",\n" +
                "  \"emp_status\":      \"Probation\",\n" +
                "  \"emp_job_title\":   \"President of the United States\"\n" +
                "}";
    }

    public static String createEmployeePayLoadViaJson() {
        JSONObject obj = new JSONObject();
        obj.put("emp_firstname", "Donald");
        obj.put("emp_lastname", "Biden");
        obj.put("emp_middle_name", "Jr");
        obj.put("emp_gender", "M");
        obj.put("emp_birthday", "1983-06-11");
        obj.put("emp_status", "Probation");
        obj.put("emp_job_title", "President of the United States");
        return obj.toString();
    }

    public static String createEmployeeDynamic(String firstName, String lastName, String middleName, String gender,
                                               String dob, String status, String jobTitle) {
        JSONObject obj = new JSONObject();
        obj.put("emp_firstname", firstName);
        obj.put("emp_lastname", lastName);
        obj.put("emp_middle_name", middleName);
        obj.put("emp_gender", gender);
        obj.put("emp_birthday", dob);
        obj.put("emp_status", status);
        obj.put("emp_job_title", jobTitle);
        return obj.toString();
    }

}

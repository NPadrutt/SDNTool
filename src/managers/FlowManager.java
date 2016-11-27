package managers;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import Utilities.Settings;
import org.apache.commons.codec.binary.Base64;
import org.codehaus.jettison.json.JSONObject;

public class FlowManager {

    // REST NBI Hydrogen for FlowGrogrammerNorthbound. More details at
    // http://opendaylight.nbi.sdngeeks.com/
    private static final String FLOW_PROGRAMMER_REST_API = "/restconf/config/opendaylight-inventory:nodes/node/";

    // HTTP statuses for checking the call output
    private static final int NO_CONTENT = 204;
    private static final int CREATED = 201;
    private static final int OK = 200;

    public static boolean installFlow(String switchUrl, String nodeId, String flowId,
            JSONObject postData) {

        HttpURLConnection connection = null;
        int callStatus = 0;

        // Creating the actual URL to call
        String baseURL = switchUrl + FLOW_PROGRAMMER_REST_API + nodeId
                + "/table/0/flow/" + flowId;

        try {

            // Create URL = base URL + container
            URL url = new URL(baseURL);

            // Create authentication string and encode it to Base64
            String authStr = Settings.USERNAME + ":" + Settings.PASSWORD;
            String encodedAuthStr = Base64.encodeBase64String(authStr
                    .getBytes());

            // Create Http connection
            connection = (HttpURLConnection) url.openConnection();

            // Set connection properties
            connection.setRequestMethod("PUT");
            connection.setRequestProperty("Authorization", "Basic "
                    + encodedAuthStr);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setUseCaches(false);
            connection.setDoInput(true);
            connection.setDoOutput(true);

            // Set Post Data
            OutputStream os = connection.getOutputStream();
            os.write(postData.toString().getBytes());
            os.close();

            // Getting the response code
            callStatus = connection.getResponseCode();

        } catch (Exception e) {
            System.err.println("Unexpected error while flow installation.. "
                    + e.getMessage());
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

        if (callStatus == CREATED || callStatus == 200) {
            System.out.println("Flow installed Successfully");
            return true;
        } else {
            System.err.println("Failed to install flow.. " + callStatus);
            return false;
        }
    }

    public static boolean installMeter(String switchUrl, String nodeId, String meterId,
            JSONObject postData) {

        HttpURLConnection connection = null;
        int callStatus = 0;

        // Creating the actual URL to call
        String baseURL = switchUrl + FLOW_PROGRAMMER_REST_API + nodeId
                + "/meter/" + meterId;

        try {

            // Create URL = base URL + container
            URL url = new URL(baseURL);

            // Create authentication string and encode it to Base64
            String authStr = Settings.USERNAME + ":" + Settings.PASSWORD;
            String encodedAuthStr = Base64.encodeBase64String(authStr
                    .getBytes());

            // Create Http connection
            connection = (HttpURLConnection) url.openConnection();

            // Set connection properties
            connection.setRequestMethod("PUT");
            connection.setRequestProperty("Authorization", "Basic "
                    + encodedAuthStr);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setUseCaches(false);
            connection.setDoInput(true);
            connection.setDoOutput(true);

            // Set Post Data
            OutputStream os = connection.getOutputStream();
            os.write(postData.toString().getBytes());
            os.close();

            // Getting the response code
            callStatus = connection.getResponseCode();

        } catch (Exception e) {
            System.err.println("Unexpected error while meter installation.. "
                    + e.getMessage());
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

        if (callStatus == CREATED || callStatus == 200) {
            System.out.println("Meter installed Successfully");
            return true;
        } else {
            System.err.println("Failed to install meter.. " + callStatus);
            return false;
        }
    }

    public static boolean deleteMeter(String switchUrl, String nodeId, String meterId) {

        HttpURLConnection connection = null;
        int callStatus = 0;

        String baseURL = switchUrl + FLOW_PROGRAMMER_REST_API + nodeId
                + "/meter/" + meterId;

        try {

            // Create URL = base URL + container
            URL url = new URL(baseURL);

            // Create authentication string and encode it to Base64
            String authStr = Settings.USERNAME + ":" + Settings.PASSWORD;
            String encodedAuthStr = Base64.encodeBase64String(authStr
                    .getBytes());

            // Create Http connection
            connection = (HttpURLConnection) url.openConnection();

            // Set connection properties
            connection.setRequestMethod("DELETE");
            connection.setRequestProperty("Authorization", "Basic "
                    + encodedAuthStr);
            connection.setRequestProperty("Content-Type", "application/json");

            callStatus = connection.getResponseCode();

        } catch (Exception e) {
            System.err.println("Unexpected error while meter deletion.."
                    + e.getMessage());
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

        if (callStatus == NO_CONTENT || callStatus == 200) {
            System.out.println("Meter deleted Successfully");
            return true;
        } else {
            System.err.println("Failed to delete the meter..." + callStatus);
            return false;
        }
    }

    public static boolean deleteFlow(String switchUrl, String nodeId, String flowId) {

        HttpURLConnection connection = null;
        int callStatus = 0;

        String baseURL = switchUrl + FLOW_PROGRAMMER_REST_API + nodeId
                + "/table/0/flow/" + flowId;
        try {

            // Create URL = base URL + container
            URL url = new URL(baseURL);

            // Create authentication string and encode it to Base64
            String authStr = Settings.USERNAME + ":" + Settings.PASSWORD;
            String encodedAuthStr = Base64.encodeBase64String(authStr
                    .getBytes());

            // Create Http connection
            connection = (HttpURLConnection) url.openConnection();

            // Set connection properties
            connection.setRequestMethod("DELETE");
            connection.setRequestProperty("Authorization", "Basic "
                    + encodedAuthStr);
            connection.setRequestProperty("Content-Type", "application/json");

            callStatus = connection.getResponseCode();

        } catch (Exception e) {
            System.err.println("Unexpected error while flow deletion.."
                    + e.getMessage());
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

        if (callStatus == NO_CONTENT || callStatus == 200) {
            System.out.println("Flow deleted Successfully");
            return true;
        } else {
            System.err.println("Failed to delete the flow..." + callStatus);
            return false;
        }
    }

    public static boolean deleteAllFlows(String switchUrl, String nodeId) {

        HttpURLConnection connection = null;
        int callStatus = 0;

        String baseURL = switchUrl + FLOW_PROGRAMMER_REST_API + nodeId
                + "/table/0/";
        try {

            // Create URL = base URL + container
            URL url = new URL(baseURL);

            // Create authentication string and encode it to Base64
            String authStr = Settings.USERNAME + ":" + Settings.PASSWORD;
            String encodedAuthStr = Base64.encodeBase64String(authStr
                    .getBytes());

            // Create Http connection
            connection = (HttpURLConnection) url.openConnection();

            // Set connection properties
            connection.setRequestMethod("DELETE");
            connection.setRequestProperty("Authorization", "Basic "
                    + encodedAuthStr);
            connection.setRequestProperty("Content-Type", "application/json");

            callStatus = connection.getResponseCode();

        } catch (Exception e) {
            System.err.println("Unexpected error while flow deletion.."
                    + e.getMessage());
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

        if (callStatus == NO_CONTENT || callStatus == 200) {
            System.out.println("All Flows deleted Successfully");
            return true;
        } else {
            System.err.println("Failed to delete the flows..." + callStatus);
            return false;
        }
    }

}

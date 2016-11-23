package manager;

/**
 * Created by nino on 23.11.2016.
 */
public class FlowManager {

    private static final String FLOW_PROGRAMMER_REST_API = "/restconf/config/opendaylight-inventory:nodes/node/";

    public FlowManager(String path){

    }

    public static boolean setFlowMeter(String nodeId, String destIP, String outputPort, String flowId, String meterId) throws JSONException {

        JSONObject flow = new JSONObject();
        JSONObject postData = new JSONObject();
        JSONObject match = new JSONObject();
        JSONObject ethernetmatch = new JSONObject();
        JSONObject ethernettype = new JSONObject();
        JSONObject instructions = new JSONObject();
        JSONObject instructionApplyAction = new JSONObject();
        JSONObject instructionMeter = new JSONObject();
        JSONObject meter = new JSONObject();
        JSONObject applyactions = new JSONObject();
        JSONObject action = new JSONObject();
        JSONObject outputaction = new JSONObject();

        ethernettype.put("type", 2048);
        ethernetmatch.put("ethernet-type", ethernettype);
        match.put("ethernet-match", ethernetmatch);
        match.put("ipv4-destination", destIP);

        outputaction.put("output-node-connector", outputPort);
        action.put("output-action", outputaction);
        action.put("order", 0);

        applyactions.put("action", new JSONArray().put(action));

        JSONArray instruction = new JSONArray();

        instructionApplyAction.put("order", 0);
        instructionApplyAction.put("apply-actions", applyactions);

        meter.put("meter-id", meterId);
        instructionMeter.put("order", 1);
        instructionMeter.put("meter", meter);

        instruction.put(instructionApplyAction);
        instruction.put(instructionMeter);

        applyactions.put("action", action);

        instructions.put("instruction", instruction);

        flow.put("id", flowId);
        flow.put("flow-name", "flow1");
        flow.put("priority", 502);
        flow.put("table_id", 0);
        flow.put("hard-timeout", 0);
        flow.put("idle-timeout", 0);
        flow.put("cookie", 0);
        flow.put("strict", true);
        flow.put("barrier", false);
        flow.put("match", match);
        flow.put("instructions", instructions);
        postData.put("flow", flow);

        return FlowManager.installFlow(nodeId, flowId, postData);

    }


    public void addMeter(double bandwidth){

    }
}

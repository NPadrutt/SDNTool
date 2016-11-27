package Utilities;

import managers.FlowManager;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public class FlowActions {

    public static boolean setFlowMeter(String url, String nodeId, String destIP, String outputPort, String flowId, String meterId)
            throws JSONException {

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

        return FlowManager.installFlow(url, nodeId, flowId, postData);

    }

    public static boolean addMeter(String url, String nodeId, String meterId, String droprate) throws JSONException {

        JSONObject postData = new JSONObject();
        JSONObject meter = new JSONObject();

        JSONObject meterbandheaders = new JSONObject();
        JSONObject meterbandheader = new JSONObject();
        JSONObject meterbandtypes = new JSONObject();

        meterbandheader.put("band-id", 1);
        meterbandheader.put("drop-rate", droprate);
        meterbandheader.put("drop-burst-size", droprate);

        meterbandtypes.put("flags", "ofpmbt-drop");
        meterbandheader.put("meter-band-types", meterbandtypes);
        meterbandheaders.put("meter-band-header", meterbandheader);

        meter.put("meter-id", meterId);
        meter.put("meter-name", "testMeter");
        meter.put("flags", "meter-kbps meter-burst meter-stats");
        meter.put("meter-band-headers", meterbandheaders);

        postData.put("meter", meter);

        return FlowManager.installMeter(url, nodeId, meterId, postData);
    }

    public static boolean deleteFlow(String url, String nodeId, String flowId) {

        return FlowManager.deleteFlow(url, nodeId, flowId);
    }

    public static boolean deleteAllFlows(String url, String nodeId) {
        return FlowManager.deleteAllFlows(url, nodeId);
    }

    public static boolean deleteMeter(String url, String nodeId, String meterId) {

        return FlowManager.deleteMeter(url, nodeId, meterId);
    }
}

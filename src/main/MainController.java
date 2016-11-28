package main;

import Utilities.FlowActions;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import org.codehaus.jettison.json.JSONException;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private Button activateFlowButton;

    @FXML
    private Slider bandwidthSlider;

    @FXML
    private TextField sourceAddressText;

    @FXML
    private TextField portText;

    @FXML
    private TextField pc1AddressText;

    @FXML
    private TextField pc2AddressText;

    @FXML
    private TextField nodeIdText;

    @FXML
    private TextField outPortText;

    private boolean isFlowActive;
    private String urlSwitch;

    @Override // This method is called by the FXMLLoader when initialization is complete
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {

        activateFlowButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                urlSwitch = sourceAddressText.getText() + ":" + portText.getText();

                if(isFlowActive){
                    FlowActions.deleteAllFlows(urlSwitch, nodeIdText.getText());
                    activateFlowButton.setText("Flow Aktivieren");
                } else{
                    try {
                        // Install Flow in both directions
                        FlowActions.addFlowDestIP(urlSwitch, nodeIdText.getText(), pc1AddressText.getText(), outPortText.getText(), "99");
                        FlowActions.addFlowDestIP(urlSwitch, nodeIdText.getText(), pc1AddressText.getText(), outPortText.getText(), "99");

                        // Set Meter on PC 1
                        FlowActions.setFlowMeter(urlSwitch, nodeIdText.getText(), pc1AddressText.getText(), outPortText.getText(), "99", "1");
                        // Add Meter on PC1
                        FlowActions.addMeter(urlSwitch, nodeIdText.getText(), "25", String.valueOf(bandwidthSlider.getValue()));
                    } catch(JSONException ex){
                        new Alert(Alert.AlertType.ERROR, ex.getMessage()).showAndWait();
                    }
                    activateFlowButton.setText("Flow Deaktivieren");
                }
                isFlowActive = !isFlowActive;
            }
        });

        bandwidthSlider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                                Number oldVal, Number newVal) {

                if(!isFlowActive) return;

                if(!bandwidthSlider.isValueChanging()) {
                    FlowActions.deleteMeter(urlSwitch, nodeIdText.getText(), "25");
                    try {
                        FlowActions.addMeter(urlSwitch, nodeIdText.getText(), "25", newVal.toString());
                    } catch (JSONException ex) {
                        System.out.println(ex);
                    }
                }
            }
        });
    }
}

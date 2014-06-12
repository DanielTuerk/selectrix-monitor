package net.wbz.selectrixmonitor;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import net.wbz.selectrix4java.api.device.DeviceAccessException;

public class Controller {

    @FXML
    private TextField txtDevice;
    @FXML
    private TextField txtCommand;
    @FXML
    private ScrollPane mainPane;
    @FXML
    private ToggleButton btnRailVoltage;

    public void connect(ActionEvent actionEvent) {
        mainPane.setContent(Main.monitorFlowPane);

        String deviceId = txtDevice.getText();
        if (!"".equals(deviceId)) {
            Selecttrix.getInstance().connect(deviceId);
        }
    }

    public void send(ActionEvent actionEvent) {
        String line = txtCommand.getText();
        String[] parts = line.split(" ");
        Selecttrix.getInstance().sendDebug(Integer.parseInt(parts[0]),
                (byte) Integer.parseInt(parts[1]), (byte) Integer.parseInt(parts[2]));
    }

    public void railVoltage(ActionEvent actionEvent) {
        try {
            Selecttrix.getInstance().getDeviceManager().getConnectedDevice().setRailVoltage(btnRailVoltage.isSelected());
        } catch (DeviceAccessException e) {
            e.printStackTrace();
        }
    }
}

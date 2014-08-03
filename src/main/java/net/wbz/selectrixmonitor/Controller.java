package net.wbz.selectrixmonitor;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
		System.out.println("Pressed ..");
		mainPane.setContent(Main.monitorFlowPane);
		String deviceId = txtDevice.getText();
		if (!"".equals(deviceId)) {
			Selectrix.getInstance().connect(deviceId);
		}
	}

	@FXML
	public void connect2(KeyEvent actionEvent) {
		if (actionEvent.getCode() == KeyCode.ENTER) {
			mainPane.setContent(Main.monitorFlowPane);
			String deviceId = txtDevice.getText();
			if (!"".equals(deviceId)) {
				Selectrix.getInstance().connect(deviceId);
			}
		}
	}

	public void send(ActionEvent actionEvent) {
		String line = txtCommand.getText();
		String[] parts = line.split(" ");
		Selectrix.getInstance().sendDebug(Integer.parseInt(parts[0]),
				(byte) Integer.parseInt(parts[1]),
				(byte) Integer.parseInt(parts[2]));
	}

	public void send2(KeyEvent actionEvent) {
		if (actionEvent.getCode() == KeyCode.ENTER) {
			String line = txtCommand.getText();
			String[] parts = line.split(" ");
			Selectrix.getInstance().sendDebug(Integer.parseInt(parts[0]),
					(byte) Integer.parseInt(parts[1]),
					(byte) Integer.parseInt(parts[2]));
		}
	}

	public void railVoltage(ActionEvent actionEvent) {
		try {
			Selectrix.getInstance().getDeviceManager().getConnectedDevice()
					.setRailVoltage(btnRailVoltage.isSelected());
		} catch (DeviceAccessException e) {
			e.printStackTrace();
		}
	}
}

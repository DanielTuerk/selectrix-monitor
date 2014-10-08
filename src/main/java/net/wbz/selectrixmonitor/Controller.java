package net.wbz.selectrixmonitor;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import net.wbz.selectrix4java.api.device.DeviceAccessException;

/**
 * The Controller  focuses for all the changes in
 * Selectrix Monitor 
 * Controller sets to listen to changes and responds to user`s
 * activities
 * 
 * @author Daniel Türk (daniel.tuerk@w-b-z.com)
 * 
 */
public class Controller {
	@FXML
	private TextField txtDevice;
	@FXML
	private TextField txtCommand;
	@FXML
	private VBox mainPane;
	@FXML
	private ToggleButton btnRailVoltage;

	/**
	 * an event represents an action such as connection of device
	 * 
	 * @param actionEvent
	 */
	public void connect(ActionEvent actionEvent) {
		System.out.println("Pressed ..");
		mainPane.getChildren().add(Main.monitorFlowPane);
		String deviceId = txtDevice.getText();
		if (!deviceId.isEmpty()) {
			Selectrix.getInstance().connect(deviceId);
		}
	}

	/**
	 * event indicates that a keystroke is occurred and responds changes
	 * 
	 * @param actionEvent
	 */
	public void connectEntered(KeyEvent actionEvent) {
		if (actionEvent.getCode() == KeyCode.ENTER) {
			mainPane.getChildren().add(Main.monitorFlowPane);
			String deviceId = txtDevice.getText();
			if (deviceId.isEmpty()) {
				Selectrix.getInstance().connect(deviceId);
			}
		}
	}

	public TextField getTxtDevice() {
		return txtDevice;
	}

	public void setTxtDevice(TextField txtDevice) {
		this.txtDevice = txtDevice;
	}

	public TextField getTxtCommand() {
		return txtCommand;
	}

	public void setTxtCommand(TextField txtCommand) {
		this.txtCommand = txtCommand;
	}

	public VBox getMainPane() {
		return mainPane;
	}

	public void setMainPane(VBox mainPane) {
		this.mainPane = mainPane;
	}

	public ToggleButton getBtnRailVoltage() {
		return btnRailVoltage;
	}

	public void setBtnRailVoltage(ToggleButton btnRailVoltage) {
		this.btnRailVoltage = btnRailVoltage;
	}

	/**
	 * an event represents an action such as sending a data
	 * @param actionEvent
	 */
	public void send(ActionEvent actionEvent) {

		String line = txtCommand.getText();
		String[] parts = line.split(" ");
		Selectrix.getInstance().sendDebug(Integer.parseInt(parts[0]),
				(byte) Integer.parseInt(parts[1]),
				(byte) Integer.parseInt(parts[2]));

	}

	/**
	 * Event indicates that a keystroke occurred and responds changes
	 * @param actionEvent
	 */
	public void sendEntered(KeyEvent actionEvent) {
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
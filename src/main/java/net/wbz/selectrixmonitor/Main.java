package net.wbz.selectrixmonitor;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;

import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.stage.Stage;
import net.wbz.selectrix4java.api.device.Device;
import net.wbz.selectrix4java.api.device.DeviceAccessException;
import net.wbz.selectrix4java.api.device.DeviceConnectionListener;

/**
 * This javafx Class extends Application 
 * the class represents DeviceConnectionListener interface
 * @author Daniel Türk
 * 
 */
public class Main extends Application implements DeviceConnectionListener {
	// TODO: ugly
	public static MonitorFlowPane monitorFlowPane = new  MonitorFlowPane();

	/** 
	 * @see javafx.application.Application#start(javafx.stage.Stage)
	 * @param primaryStage onto application scene can be settled
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		Selectrix.getInstance().getDeviceManager()
				.addDeviceConnectionListener(this);   
		Parent root = FXMLLoader.load(getClass().getClassLoader().getResource(
				"fxml/sample.fxml"));

		primaryStage.setTitle("Selectrix Monitor");
		primaryStage.setScene(new Scene(root, 450, 700));
		primaryStage.setResizable(true);
		monitorFlowPane.prefWidthProperty().bind(primaryStage.widthProperty());
		monitorFlowPane.prefHeightProperty().bind(primaryStage.heightProperty());
		primaryStage.show();

	}

	/**
	 * launch a stand alone application
	 */
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void connected(Device device) {
		try {
			Device connectedDevice = Selectrix.getInstance().getDeviceManager()
					.getConnectedDevice();
			connectedDevice.getBusDataDispatcher().registerConsumer(
					monitorFlowPane.getConsumer());

			monitorFlowPane.initData(0, connectedDevice.getBusDataDispatcher()
					.getData(0));
			monitorFlowPane.initData(1, connectedDevice.getBusDataDispatcher()
					.getData(1));

		} catch (DeviceAccessException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void disconnected(Device device) {
		try {
			Selectrix.getInstance().getDeviceManager().getConnectedDevice()
					.getBusDataDispatcher()
					.unregisterConsumer(monitorFlowPane.getConsumer());
		} catch (DeviceAccessException e) {
			e.printStackTrace();
		}
	}
}

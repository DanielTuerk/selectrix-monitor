package net.wbz.selectrixmonitor;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;
import net.wbz.selectrix4java.api.device.Device;
import net.wbz.selectrix4java.api.device.DeviceAccessException;
import net.wbz.selectrix4java.api.device.DeviceConnectionListener;

public class Main extends Application implements DeviceConnectionListener {

    //TODO
    public static MonitorFlowPane monitorFlowPane = new MonitorFlowPane();

    @Override
    public void start(Stage primaryStage) throws Exception {
        Selectrix.getInstance().getDeviceManager().addDeviceConnectionListener(this);

        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/sample.fxml"));
        primaryStage.setTitle("Selectrix Monitor");
        primaryStage.setScene(new Scene(root, 1100, 800));
        monitorFlowPane.prefWidthProperty().bind(primaryStage.widthProperty());
        monitorFlowPane.prefHeightProperty().bind(primaryStage.heightProperty());
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void connected(Device device) {
        try {
            Device connectedDevice = Selectrix.getInstance().getDeviceManager().getConnectedDevice();
            connectedDevice.getBusDataDispatcher().registerConsumer(monitorFlowPane.getConsumer());
            monitorFlowPane.initData(0, connectedDevice.getBusDataDispatcher().getData(0));
            monitorFlowPane.initData(1, connectedDevice.getBusDataDispatcher().getData(1));
        } catch (DeviceAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void disconnected(Device device) {
        try {
            Selectrix.getInstance().getDeviceManager().getConnectedDevice().getBusDataDispatcher().unregisterConsumer(monitorFlowPane.getConsumer());
        } catch (DeviceAccessException e) {
            e.printStackTrace();
        }
    }
}

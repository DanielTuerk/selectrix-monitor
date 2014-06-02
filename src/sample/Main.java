package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import net.wbz.selecttrix4java.api.bus.AllBusDataConsumer;
import net.wbz.selecttrix4java.api.device.Device;
import net.wbz.selecttrix4java.api.device.DeviceAccessException;
import net.wbz.selecttrix4java.api.device.DeviceConnectionListener;

public class Main extends Application implements DeviceConnectionListener {

    //TODO
    public static MonitorFlowPane monitorFlowPane=new MonitorFlowPane();
    @Override
    public void start(Stage primaryStage) throws Exception {
        Selecttrix.getInstance().getDeviceManager().addDeviceConnectionListener(this);

        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Selecttrix Monitor");
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
            Selecttrix.getInstance().getDeviceManager().getConnectedDevice().getBusDataDispatcher().registerConsumer(monitorFlowPane.getConsumer());
        } catch (DeviceAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void disconnected(Device device) {
        try {
            Selecttrix.getInstance().getDeviceManager().getConnectedDevice().getBusDataDispatcher().unregisterConsumer(monitorFlowPane.getConsumer());
        } catch (DeviceAccessException e) {
            e.printStackTrace();
        }
    }
}

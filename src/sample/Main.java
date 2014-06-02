package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import net.wbz.selecttrix4java.api.bus.AllBusDataConsumer;
import net.wbz.selecttrix4java.api.device.Device;
import net.wbz.selecttrix4java.api.device.DeviceConnectionListener;

public class Main extends Application implements DeviceConnectionListener {

    //TODO
    public static MonitorFlowPane monitorFlowPane=new MonitorFlowPane();
    @Override
    public void start(Stage primaryStage) throws Exception {
        Selecttrix.getInstance().getDevice().addDeviceConnectionListener(this);

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
        Selecttrix.getInstance().getDevice().getBusDataDispatcher().registerConsumer(monitorFlowPane.getConsumer());
    }

    @Override
    public void disconnected(Device device) {
        Selecttrix.getInstance().getDevice().getBusDataDispatcher().unregisterConsumer(monitorFlowPane.getConsumer());
    }
}

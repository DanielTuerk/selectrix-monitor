package sample;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Daniel Tuerk (daniel.tuerk@w-b-z.com)
 */
public class BusAddressItemPanel extends HBox {

    private final int bus;
    private final int address;


    private Map<Integer, Label> dataLabels = new HashMap<>();

    public BusAddressItemPanel(int bus, int address) {
        this.bus = bus;

        this.address = address;
        setStyle("-fx-background-color: antiquewhite; -fx-padding: 5");
        Label element = new Label(String.valueOf(address));
        element.setStyle("-fx-background-color: burlywood;");
        getChildren().add(element);
        for (int i = 1; i < 9; i++) {
            Label lblData = new Label("0");
            getChildren().add(lblData);
            dataLabels.put(i, lblData);
        }
    }

    public void updateData(final int data) {
        // UI updaten
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                // entsprechende UI Komponente updaten
                BigInteger wrappedData = BigInteger.valueOf(data);
                for (int i = 1; i < 9; i++) {
                    dataLabels.get(i).setText(wrappedData.testBit(i) ? "1" : "0");
                }
            }
        });

    }
}

package net.wbz.selectrixmonitor;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.HBox;

import java.math.BigInteger;
import java.util.*;

/**
 * This is the implementation of TitledPane which is a panel that has a title
 * and can be opened and closed. TitledPane is with Bus Addresses and its number
 * on the top and set of buttons-8 bites of Information on the bottom
 *
 * @author Daniel Tuerk (daniel.tuerk@w-b-z.com)
 */
public class BusAddressItemPanel extends TitledPane {

    private int bus;
    private int address;

    private final Map<Integer, Button> dataButtons = new HashMap<>();

    private final List<Button> buttonThreads = Collections.synchronizedList(new ArrayList<Button>());

    /**
     * constructs a TitledPane with title and content- a horizontal box
     *
     * @param bus     number of bus
     *                (0-1)
     * @param address of the bus(0-112)
     */
    public BusAddressItemPanel(final int bus, final int address) {

        this.bus = bus;
        this.address = address;
        setText("Address : " + address);
        HBox boxAddressDataLeft = new HBox();
        boxAddressDataLeft.setAlignment(Pos.BASELINE_LEFT);
        boxAddressDataLeft
                .setStyle("-fx-font-size:10;-fx-background-color:#a6b5c9,"
                        + "linear-gradient(#303842 0%,#3e5577 20%,#375074 100%),"
                        + "linear-gradient(#768aa5 0%,#849cbb 5%,#5877a2 50%,#486a9a 51%, #4a6c9b 100%)");
        HBox boxAddressDataRight = new HBox();
        boxAddressDataRight.setAlignment(Pos.BASELINE_CENTER);
        boxAddressDataRight
                .setStyle("-fx-font-size:10;-fx-background-color:#a6b5c9,"
                        + "linear-gradient(#303842 0%,#3e5577 20%,#375074 100%),"
                        + "linear-gradient(#768aa5 0%,#849cbb 5%,#5877a2 50%,#486a9a 51%, #4a6c9b 100%)");
        // main horizontal box to create one united item panel
        HBox mainHorizontalBox = new HBox();
        // create  buttons for each bit for address
        for (int i = 7; i >= 0; i--) {
            final Button btnData = new Button("0");
            btnData.setStyle("-fx-color: #c3c4c4, "
                    + "linear-gradient(#d6d6d6 50%, white 50%),"
                    + "radial-gradient(center 50% -40%,radius 50%, #e6e6e6 45%,rgba(230,230,230,0), 50%);"
                    + "-fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 3, 0.0 ,0 , 1)");
            dataButtons.put(i, btnData);
            // add action to  change the state of bits
            final int temp = i + 1;
            btnData.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent arg0) {
                    if (btnData.getText().equals("0")) {
                        Selectrix.getInstance().changedValue(bus,
                                (byte) address, temp, true);

                    } else {
                        Selectrix.getInstance().changedValue(bus,
                                (byte) address, temp, false);
                    }
                }
            });
            // add first 4 buttons to the left and others to the  right
            if (i > 3) {
                boxAddressDataLeft.getChildren().add(btnData);
            } else {
                boxAddressDataRight.getChildren().add(btnData);
            }
        }

        mainHorizontalBox.getChildren().addAll(boxAddressDataLeft,
                boxAddressDataRight);
        mainHorizontalBox.setSpacing(5);
        setContent(mainHorizontalBox);
    }

    public int getBus() {
        return bus;
    }

    public int getAddress() {
        return address;
    }

    /**
     * start the thread to set color for changed value of buttons
     * and delay for 3 seconds and sets standard color
     *
     * @param btnData the changed data of a button or buttons
     */
    public synchronized void setFlashColor(final Button btnData) {
        if (!buttonThreads.contains(btnData)) {
            buttonThreads.add(btnData);
            new Thread(new Runnable() {

                @Override
                public void run() {
                    btnData.setStyle("-fx-color: purple");
                    try {
                        Thread.sleep(1000);

                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                    btnData.setStyle("-fx-color: #c3c4c4, "
                            + "linear-gradient(#d6d6d6 50%, white 50%),"
                            + "radial-gradient(center 50% -40%,radius 50%, #e6e6e6 45%,rgba(230,230,230,0), 50%);"
                            + "-fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 3, 0.0 ,0 , 1)");

                    buttonThreads.remove(btnData);
                }
            }).start();
        }
    }

    /**
     * Updates the definite components of GUI, nodes (the set of button) of
     * Titled Pane
     *
     * @param data the actual data
     */
    public synchronized void updateData(final int data) {

        Platform.runLater(new Runnable() {

            // specify the running behavior of thread
            @Override
            public void run() {
                // entsprechende UI Komponente updaten
                final BigInteger wrappedData = BigInteger.valueOf(data);

                for (int i = 0; i < 8; i++) {
                    String dataString = dataButtons.get(i).getText();
                    dataButtons.get(i).setText(
                            wrappedData.testBit(i) ? "1" : "0");

                    if (!dataString.equals(dataButtons.get(i).getText())) {
                        setFlashColor(dataButtons.get(i));

                    }

                }
            }
        });

    }
}

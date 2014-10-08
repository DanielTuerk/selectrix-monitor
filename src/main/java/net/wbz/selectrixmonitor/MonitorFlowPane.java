package net.wbz.selectrixmonitor;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.*;
import net.wbz.selectrix4java.api.bus.AllBusDataConsumer;
import net.wbz.selectrix4java.api.bus.BusDataConsumer;

import java.util.HashMap;
import java.util.Map;

/**
 * The class MonitorFlowPane is main container for components in it The
 * MonitorFlowPane lays out its children HBox with the Bus and its number and
 * HBox containing ScrollPane of 2 buses,vertically
 *
 * @author Daniel Tuerk (daniel.tuerk@w-b-z.com)
 */
public class MonitorFlowPane extends VBox
{

  private final Map<Integer, Map<Integer, BusAddressItemPanel>> addressItemMapping = new HashMap<>();

  public final Map<Integer, Map<Integer, BusAddressItemPanel>> getAddressItemMapping()
  {
    return addressItemMapping;
  }

  /**
   * constructs a layout with spacing=0
   */
  public MonitorFlowPane()
  {
    Map<Integer, BusAddressItemPanel> addressItemPanelMapForLeftBus = new HashMap<>();
    Map<Integer, BusAddressItemPanel> addressItemPanelMapForRightBus = new HashMap<>();
    HBox scrollPaneBox = new HBox();
    ScrollPane scrollpane = new ScrollPane();
    scrollpane.setVbarPolicy(ScrollBarPolicy.ALWAYS);
    scrollpane.setFitToHeight(true);
    scrollpane.setFitToWidth(true);
    TilePane tilePaneLeft = new TilePane();
    tilePaneLeft.setHgap(6);
    tilePaneLeft.setVgap(6);
    tilePaneLeft.setPadding(new Insets(12));
    TilePane tilePaneRight = new TilePane();
    tilePaneRight.setHgap(6);
    tilePaneRight.setVgap(6);
    tilePaneRight.setPadding(new Insets(12));
    tilePaneLeft
        .setStyle("-fx-background-color:#a6b5c9,"
            + "linear-gradient(#303842 0%,#3e5577 20%,#375074 100%),"
            + "linear-gradient(#768aa5 0%,#849cbb 5%,#5877a2 50%,#486a9a 51%, #4a6c9b 100%)");
    tilePaneRight
        .setStyle("-fx-background-color:#a6b5c9,"
            + "linear-gradient(#303842 0%,#3e5577 20%,#375074 100%),"
            + "linear-gradient(#768aa5 0%,#849cbb 5%,#5877a2 50%,#486a9a 51%, #4a6c9b 100%)");

    GridPane gridPaneLabel = new GridPane();
    // specifying the metrics to control the size of columns by adding
    // СolumnConstraints
    ColumnConstraints columnleft = new ColumnConstraints();
    ColumnConstraints columnright = new ColumnConstraints();
    // the size to be specified as a percentage of GridPane available space
    columnright.setPercentWidth(50);
    columnleft.setPercentWidth(50);
    gridPaneLabel.getColumnConstraints().addAll(columnleft, columnright);
    // creates 2 containers for bus 0 and 1
    for (int busNr = 0; busNr < 2; busNr++)
    {
      for (int addressNr = 0; addressNr < 113; addressNr++)
      {
        if (busNr < 1)
        {
          gridPaneLabel.add(new Label("Bus: " + busNr), 0, 1);
          gridPaneLabel
              .setStyle("-fx-background-color:#a6b5c9,"
                  + "linear-gradient(#303842 0%,#3e5577 20%,#375074 100%),"
                  + "linear-gradient(#768aa5 0%,#849cbb 5%,#5877a2 50%,#486a9a 51%, #4a6c9b 100%)");
          BusAddressItemPanel busItemPanelNull = new BusAddressItemPanel(
              busNr, addressNr);
          addressItemPanelMapForLeftBus.put(addressNr,
              busItemPanelNull);
          tilePaneLeft.getChildren().add(busItemPanelNull);

          busItemPanelNull
              .setStyle("-fx-background-color:#a6b5c9,"
                  + "linear-gradient(#303842 0%,#3e5577 20%,#375074 100%),"
                  + "linear-gradient(#768aa5 0%,#849cbb 5%,#5877a2 50%,#486a9a 51%, #4a6c9b 100%)");

          addressItemMapping
              .put(busNr, addressItemPanelMapForLeftBus);
        }
        else
        {
          gridPaneLabel.add(new Label("Bus: " + busNr), 1, 1);
          gridPaneLabel
              .setStyle("-fx-background-color:#a6b5c9,"
                  + "linear-gradient(#303842 0%,#3e5577 20%,#375074 100%),"
                  + "linear-gradient(#768aa5 0%,#849cbb 5%,#5877a2 50%,#486a9a 51%, #4a6c9b 100%)");
          BusAddressItemPanel busItemPanelFirst = new BusAddressItemPanel(
              busNr, addressNr);
          addressItemPanelMapForRightBus.put(addressNr,
              busItemPanelFirst);

          tilePaneRight.getChildren().add(busItemPanelFirst);

          busItemPanelFirst
              .setStyle("-fx-background-color:#a6b5c9,"
                  + "linear-gradient(#303842 0%,#3e5577 20%,#375074 100%),"
                  + "linear-gradient(#768aa5 0%,#849cbb 5%,#5877a2 50%,#486a9a 51%, #4a6c9b 100%)");

          addressItemMapping.put(busNr,
              addressItemPanelMapForRightBus);
        }
      }

    }
    setVgrow(scrollpane, Priority.ALWAYS);
    HBox.setHgrow(tilePaneLeft, Priority.ALWAYS);
    HBox.setHgrow(tilePaneRight, Priority.ALWAYS);
    getChildren().addAll(gridPaneLabel, scrollpane);
    scrollPaneBox.getChildren().addAll(tilePaneLeft, tilePaneRight);
    scrollpane.setContent(scrollPaneBox);
  }

  /**
   * initializes data once
   *
   * @param bus
   * @param data
   */
  public void initData(int bus, byte[] data)
  {
    for (int address = 0; address < data.length; address++)
    {
      addressItemMapping.get(bus).get(address).updateData(data[address]);

    }
  }

  public BusDataConsumer getConsumer()
  {
    return new AllBusDataConsumer()
    {

      @Override
      public void valueChanged(int bus, int address, int oldValue, int newValue)
      {
        addressItemMapping.get(bus).get(address).updateData(newValue);
      }
    };
  }

}

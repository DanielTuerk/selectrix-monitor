package sample;

import net.wbz.selecttrix4java.SerialDevice;
import net.wbz.selecttrix4java.api.device.DeviceAccessException;

/**
 * @author Daniel Tuerk (daniel.tuerk@w-b-z.com)
 */
public class Selecttrix {

    private final SerialDevice device;
    private final static Selecttrix instance = new Selecttrix();

    private Selecttrix() {
        device = new SerialDevice(deviceId, baudRate);
    }

    public static Selecttrix getInstance() {
        return instance;
    }

    public void connect(String deviceId) {
        device.connect(deviceId, SerialDevice.DEFAULT_BAUD_RATE_FCC);
    }

    public SerialDevice getDevice() {
        return device;
    }

    public void sendDebug(int bus, byte address, byte data) {
        try {
            device.getBusAddress(bus,address).sendData(data);
        } catch (DeviceAccessException e) {
            e.printStackTrace();
        }
    }


}

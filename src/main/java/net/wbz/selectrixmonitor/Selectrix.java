package net.wbz.selectrixmonitor;

import net.wbz.selectrix4java.SerialDevice;
import net.wbz.selectrix4java.api.device.DeviceAccessException;
import net.wbz.selectrix4java.manager.DeviceManager;

/**
 * @author Daniel Tuerk (daniel.tuerk@w-b-z.com)
 */
public class Selectrix {

    private DeviceManager deviceManager = new DeviceManager();
    private final static Selectrix instance = new Selectrix();

    private Selectrix() {
        new ConsoleListenerOutput().start(deviceManager);
    }

    public static Selectrix getInstance() {
        return instance;
    }

    public void connect(String deviceId) {

        if(deviceManager.getDeviceById(deviceId)== null) {
            if("test".equals(deviceId)) {
                deviceManager.registerDevice(DeviceManager.DEVICE_TYPE.TEST, "test",0);

            }
            else {
                deviceManager.registerDevice(DeviceManager.DEVICE_TYPE.SERIAL, deviceId, SerialDevice.DEFAULT_BAUD_RATE_FCC);
            }
        }

        try {
            deviceManager.getDeviceById(deviceId).connect();
        } catch (DeviceAccessException e) {
            e.printStackTrace();
        }
    }

    public DeviceManager getDeviceManager() {
        return deviceManager;
    }

    public void sendDebug(int bus, byte address, byte data) {
        try {
            deviceManager.getConnectedDevice().getBusAddress(bus,address).sendData(data);
        } catch (DeviceAccessException e) {
            e.printStackTrace();
        }
    }


}

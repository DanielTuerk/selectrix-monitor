package net.wbz.selectrixmonitor;


import net.wbz.selectrix4java.bus.BusAddress;
import net.wbz.selectrix4java.device.Device;
import net.wbz.selectrix4java.device.DeviceAccessException;
import net.wbz.selectrix4java.device.DeviceConnectionListener;
import net.wbz.selectrix4java.device.DeviceManager;
import net.wbz.selectrix4java.device.serial.SerialDevice;

/**
 * @author Daniel Tuerk (daniel.tuerk@w-b-z.com)
 */
public class Selectrix {
    private DeviceManager deviceManager = new DeviceManager();

    private final static Selectrix instance = new Selectrix();

    private Device device;

    private void createDevice(String deviceId) {
        device = deviceManager.registerDevice(DeviceManager.DEVICE_TYPE.TEST,
                deviceId, SerialDevice.DEFAULT_BAUD_RATE_FCC);
    }

    public static Selectrix getInstance() {
        return instance;
    }

    public Device getDevice() {
        return device;
    }

    private Selectrix() {

//		createDevice("device#1");

        new ConsoleListenerOutput().start(deviceManager);
        DeviceManager deviceManager = new DeviceManager();
        deviceManager
                .addDeviceConnectionListener(new DeviceConnectionListener() {

                    @Override
                    public void disconnected(Device device) {
                        System.out.println(" did not connect");

                    }

                    @Override
                    public void connected(Device device) {
                        System.out.println("connected");

                    }
                });
//		try {
//			device.connect();
//		} catch (DeviceAccessException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

    }

    public void connect(String deviceId) {

        if (deviceManager.getDeviceById(deviceId) == null) {
            if ("test".equals(deviceId)) {
                deviceManager.registerDevice(DeviceManager.DEVICE_TYPE.TEST,
                        "test", 0);

            } else {
                deviceManager.registerDevice(DeviceManager.DEVICE_TYPE.SERIAL,
                        deviceId, SerialDevice.DEFAULT_BAUD_RATE_FCC);
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
            deviceManager.getConnectedDevice().getBusAddress(bus, address)
                    .sendData(data);
        } catch (DeviceAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * examines the state of bit if it`s true turns the definite bit on,
     * otherwise turns the definite bit off
     *
     * @param bus      the definite bus
     * @param address  the definite address
     * @param bitNr    the changed definite button by its number
     * @param bitState the actual state of a button(on/off)
     */

    public void changedValue(int bus, byte address, int bitNr, boolean bitState) {
        try {

            BusAddress busAddress = deviceManager.getConnectedDevice().getBusAddress(bus, address);
            if (bitState) {
                busAddress.setBit(bitNr);
            } else {
                busAddress.clearBit(bitNr);
            }
            busAddress.send();
        } catch (DeviceAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
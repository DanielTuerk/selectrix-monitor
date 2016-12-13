package net.wbz.selectrixmonitor;


import net.wbz.selectrix4java.block.BlockListener;
import net.wbz.selectrix4java.block.BlockModule;
import net.wbz.selectrix4java.device.Device;
import net.wbz.selectrix4java.device.DeviceAccessException;
import net.wbz.selectrix4java.device.DeviceConnectionListener;
import net.wbz.selectrix4java.device.DeviceManager;
import net.wbz.selectrix4java.train.TrainDataListener;
import net.wbz.selectrix4java.train.TrainModule;

/**
 * Only simple console handler to print state changes.
 *
 * @author Daniel Tuerk (daniel.tuerk@w-b-z.com)
 */
public class ConsoleListenerOutput {

    public void start(DeviceManager deviceManager) {

        /*
         * Device
         */
        deviceManager.addDeviceConnectionListener(new DeviceConnectionListener() {
            @Override
            public void connected(Device device) {

                out(device.getClass().getSimpleName() + " connected");

                // Block
                try {
                    BlockModule blockModule = device.getBlockModule((byte) 10);
                    blockModule.addBlockListener(new BlockListener() {
                        @Override
                        public void blockOccupied(int blockNr) {
                            out("blockOccupied " + blockNr);
                        }

                        @Override
                        public void blockFreed(int blockNr) {
                            out("blockFreed " + blockNr);
                        }
                    });
                } catch (DeviceAccessException e) {
                    e.printStackTrace();
                }

                // Train
                try {
                    final byte trainAddress = 3;
                    TrainModule trainModule = device.getTrainModule(trainAddress);
                    trainModule.addTrainDataListener(new TrainDataListener() {
                        @Override
                        public void drivingLevelChanged(int level) {
                            out("drivingLevelChanged " + level + " (" + trainAddress + ")");
                        }

                        @Override
                        public void drivingDirectionChanged(TrainModule.DRIVING_DIRECTION direction) {
                            out("drivingDirectionChanged " + direction + " (" + trainAddress + ")");
                        }

                        @Override
                        public void functionStateChanged(byte address, int functionBit, boolean state) {
                            out("functionStateChanged " + address + "," + functionBit + "," + state + "(" + trainAddress + ")");
                        }

                        @Override
                        public void lightStateChanged(boolean on) {
                            out("lightStateChanged " + on + " (" + trainAddress + ")");
                        }

                        @Override
                        public void hornStateChanged(boolean on) {
                            out("hornStateChanged " + on + " (" + trainAddress + ")");
                        }
                    });
                } catch (DeviceAccessException e) {
                    e.printStackTrace();
                }

//                device.getBusDataDispatcher().registerConsumer(new AllBusDataConsumer() {
//                  @Override
//                  public void valueChanged(int bus, int address, int oldValue, int newValue)
//                  {
//                    out(String.format("Consumer::valueChanged - bus %d, address %d, oldValue %d newValue %d", bus, address, oldValue, newValue));
//                  }
//                });
            }

            @Override
            public void disconnected(Device device) {
                out(device + " disconnected");
            }
        });

    }

    public static void out(String msg) {
        System.out.println(msg);
    }
}

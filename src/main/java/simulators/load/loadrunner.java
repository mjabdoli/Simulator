package simulators.load;

import models.DeviceType;
import simulators.Sensor;
import util.HttpOperations;

public class loadrunner extends Sensor {
    public loadrunner(int deviceId, int profileId, String deviceName, DeviceType deviceType) {
        super(deviceId, profileId, deviceName, deviceType);
    }

    public static void main(String[] args) {

    }

    @Override
    public void start() {

    }

    @Override
    public String send() {
        String cr= prepareCommand();
        String ret= null;
        try {
            System.out.println(cr);
            ret = new HttpOperations().executePost(baseDataServer + getProfileId(),cr);
            System.out.println(cr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    @Override
    public void receive() {

    }

    @Override
    public void heartbeat() {

    }
}

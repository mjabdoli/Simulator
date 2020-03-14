package simulators;

import models.DeviceType;
import util.HttpOperations;

import java.util.HashMap;

public class HumiditySensor extends Sensor {


    public HumiditySensor(int deviceId, int profileId, String deviceName, DeviceType deviceType) {
        super(deviceId, profileId, deviceName,deviceType);
    }

    @Override
    public void start() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    receive();
                    try {
                        Thread.sleep(getReceiveDataPeriod());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
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
        try {
            String ret=new HttpOperations().executePost(baseRuleServer,"",
                    new HashMap<String,String>(){{put("did",String.valueOf(getDeviceId()));}});
            System.out.println(getDeviceName() +  ", HumiditySensor.receive : " + ret );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void heartbeat() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while(true) {
                        String ret = new HttpOperations().executePost(baseHeartbeatServer+"/" + getDeviceId(), "", new HashMap<String, String>());
                        System.out.println("Heartbeat info : " +getDeviceName() +  " : " + ret);
                        Thread.sleep(secondBase *30);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}

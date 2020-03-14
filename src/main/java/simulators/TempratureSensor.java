package simulators;

import models.DeviceType;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import util.HttpOperations;

import java.util.HashMap;

public class TempratureSensor extends Sensor {
    public TempratureSensor(int deviceId, int profileId, String deviceName, DeviceType deviceType) {
        super(deviceId, profileId, deviceName, deviceType);
    }

    @Override
    public void start() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    send();
                    try {
                        Thread.sleep(100000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private long gettemp() throws Exception {
        String url ="https://api.openweathermap.org/data/2.5/weather?q=Tehran&appid=821715b084a768b147676b59e381fe74";

        String ret= new HttpOperations().executePost(url, "");
        JSONObject jsonObject=new JSONObject();
        JSONParser jsonParser=new JSONParser();
        jsonObject=(JSONObject) jsonParser.parse(ret);
        JSONObject obj2= (JSONObject) jsonObject.get("main");
        long temp=Math.round(Float.parseFloat(obj2.get("temp").toString()) - 273.15);
        System.out.println(temp);
        return temp;
    }

    @Override
    public String send() {
        long temp=0;
        try {
            temp=gettemp();
        } catch (Exception e) {
            e.printStackTrace();
        }
        setBodyValue("t",String.valueOf(temp));
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
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while(true) {
                        String ret = new HttpOperations().executePost(baseHeartbeatServer+"/" + getDeviceId(), "", new HashMap<String, String>());
                        System.out.println("Heartbeat info : " +getDeviceName() +  " : " + ret);
                        Thread.sleep(secondBase *100);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }
}

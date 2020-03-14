package simulators;

import models.DeviceType;
import org.json.simple.JSONObject;
import util.PublicVariables;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public abstract class Sensor {
    //"http:localhost:8080/input/flat/{profileid}"
    //144.76.189.84
    private int sendDataPeriod =60000;
    private int receiveDataPeriod =20000;
    protected int secondBase =1000;
    //116.202.78.234
    //public static String webServerAddress="http://localhost:8081";
    public static String baseDataServer = PublicVariables.baseurl +"/input/flat/";
    public static String baseRuleServer =PublicVariables.baseurl+"/input/myrules";
    public static String baseHeartbeatServer=PublicVariables.baseurl+"/heartbeat/notify";
    /*public static String bodyValue ="{\"p\":\"802647\",\"d\":\"2\",\"t\":\"880855808484\",\"v\":{\"long\":\"70\"," +
            "\"lat\":\"60\"," +
            "\"c\":\"x\"}}";*/
    private DeviceType deviceType=DeviceType.HTTP;
    private int deviceId;
    private int profileId;
    private String deviceName;
    private HashMap<String,String> parameters=new HashMap<>();
    public Sensor(int deviceId,int profileId,String deviceName,DeviceType deviceType)
    {
        this.deviceId=deviceId;
        this.profileId=profileId;
        this.deviceName=deviceName;
        this.deviceType=deviceType;
    }

    public boolean setBodyValue(String key,String value)
    {
        if (parameters.containsKey(key)) {
            parameters.put(key, value);
            return true;
        }
        return false;
    }

    public Sensor addField(String fieldName)
    {
        parameters.put(fieldName,"");
        return this;
    }

    protected String prepareCommand()
    {
        JSONObject object=new JSONObject();
        object.put("p",profileId);
        object.put("d",deviceId);
        object.put("t",new Date().getTime());
        JSONObject valueobject=new JSONObject();
        for (Map.Entry<String,String> entry:parameters.entrySet())
        {
            if (!entry.getValue().equals(""))
            valueobject.put(entry.getKey(),entry.getValue());
        }
        object.put("v",valueobject);
        return object.toJSONString();
    }
    public abstract void start();
    public abstract String send();
    public abstract void receive();
    public abstract void heartbeat();


    public int getSendDataPeriod() {
        return sendDataPeriod;
    }

    public Sensor setSendDataPeriod(int sendDataPeriod) {
        this.sendDataPeriod = sendDataPeriod;
        return this;
    }

    public int getReceiveDataPeriod() {
        return receiveDataPeriod;
    }

    public Sensor setReceiveDataPeriod(int receiveDataPeriod) {
        this.receiveDataPeriod = receiveDataPeriod;
        return this;
    }

    public DeviceType getDeviceType() {
        return deviceType;
    }

    public Sensor setDeviceType(DeviceType deviceType) {
        this.deviceType = deviceType;
        return this;
    }

    public int getDeviceId() {
        return deviceId;
    }

    public Sensor setDeviceId(int deviceId) {
        this.deviceId = deviceId;
        return this;
    }

    public int getProfileId() {
        return profileId;
    }

    public Sensor setProfileId(int profileId) {
        this.profileId = profileId;
        return this;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public Sensor setDeviceName(String deviceName) {
        this.deviceName = deviceName;
        return this;
    }

}

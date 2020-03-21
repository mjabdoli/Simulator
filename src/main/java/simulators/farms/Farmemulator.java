package simulators.farms;

import util.HttpOperations;
import util.PublicVariables;
import java.util.HashMap;
import java.util.Random;

public class Farmemulator {
    public static void main(String[] args) throws Exception {
        //Change first_patch
        //Commit and push

        /*String ddd="http://localhost:8080/device/1";
        String ret=new HttpOperations().executePost(ddd,"",
                new HashMap<String,String>(){{put("profid","1");}});
        System.out.println(ret);*/
        //String ddd="http://localhost:8080/rules/list/1";

        String ret ="{\"val\":[{\"name\":\"Report Water To Admin\",\"statements\":[{\"fid\":3,\"fname\":\"s\",\"stype\":\"FLOAT\",\"lastfield\":\"\",\"operator\":\"EQUALS\",\"operand\":0}],\"ruleid\":\"155ce309-fc9a-4d34-ad4a-6c97f06899dc\",\"waittime\":2,\"profid\":1,\"actions\":[{\"para\":\"r\",\"name\":\"Report to Admin\",\"id\":\"9cec066b-18f0-4768-874b-5154a0365dab\",\"value\":\"@s\",\"did\":4}],\"policy\":\"AND\"},{\"name\":\"OpenWater\",\"statements\":[{\"fid\":1,\"fname\":\"m\",\"stype\":\"FLOAT\",\"lastfield\":\"30\",\"operator\":\"LESS\",\"operand\":20.0}],\"ruleid\":\"814ab5c5-91a8-44a7-9263-f213775b103f\",\"waittime\":2,\"profid\":1,\"actions\":[{\"para\":\"c\",\"name\":\"Force open water\",\"id\":\"3662af3c-1d33-472d-888a-84e4de8abb3e\",\"value\":\"1\",\"did\":3},{\"para\":\"r\",\"name\":\"Report from humidity1 to admin\",\"id\":\"4eff465a-2b1b-4c34-8a6b-156b2dc0c5b2\",\"value\":\"@m\",\"did\":4}],\"policy\":\"AND\"},{\"name\":\"Closeafter60Second\",\"statements\":[{\"fid\":1,\"fname\":\"m\",\"stype\":\"FLOAT\",\"lastfield\":\"30\",\"operator\":\"LESS\",\"operand\":20.0}],\"ruleid\":\"c739139f-8724-45bb-9ce7-ec5dbc7d7f3a\",\"waittime\":2,\"profid\":1,\"actions\":[{\"para\":\"c\",\"name\":\"Force Close water after 60 sec\",\"id\":\"38b9f141-3547-4212-8c91-447c7f989495\",\"value\":\"0\",\"did\":3},{\"para\":\"r\",\"name\":\"Report from Closing after 60 sec to admin\",\"id\":\"d9cc5038-99dc-43bf-ab6d-5d01ce2327b7\",\"value\":\"Close Water\",\"did\":4}],\"policy\":\"AND\"},{\"name\":\"CloseWater\",\"statements\":[{\"fid\":1,\"fname\":\"m\",\"stype\":\"FLOAT\",\"lastfield\":\"30\",\"operator\":\"GREATER\",\"operand\":90.0}],\"ruleid\":\"ce1157a2-dafd-4ba8-84a2-2444fc633aca\",\"waittime\":2,\"profid\":1,\"actions\":[{\"para\":\"c\",\"name\":\"Force Close water\",\"id\":\"1743bf84-0c3d-41a6-901c-8a027413cad4\",\"value\":\"0\",\"did\":3},{\"para\":\"r\",\"name\":\"Report from humidity1 to admin\",\"id\":\"5c8a40b3-64ca-4932-bfae-50efd9cbdb89\",\"value\":\"@m\",\"did\":4}],\"policy\":\"AND\"}]}";
        //new HttpOperations().executePost(ddd);
        System.out.println(ret);

        Farms farms=new Farms();
        //new Farmemulator().createRule(ret);
        new Thread(new Runnable() {
            int i =40 ;
            Random rnd=new Random();
            boolean flg=true;
            @Override
            public void run() {

                while(true) {
                    int d=rnd.nextInt(10);
                    if (i<=0)
                        flg=true;
                    if(i>=100)
                        flg=false;
                    if (flg)
                        i += d;
                    else
                        i -= d;
                    i=i<0? 0:i;
                    i=i>100?100:i;

                    farms.moisture1.setBodyValue("m",String.valueOf(i));
                    farms.moisture1.send();
                    //farms.temp1.start();
                    try {
                        profileHeartbeatStatus();
                        profileDisconnectedDevice();
                        Thread.sleep(100000);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }).start();

        /*farms.water1.setBodyValue("s", "Open");
        farms.water1.send();
        Thread.sleep(10000);
        farms.water1.setBodyValue("s", "Close");
        farms.water1.send();
        Thread.sleep(25000);*/
       /* farms.moisture1.setBodyValue("m","95");
        farms.moisture1.send();
        Thread.sleep(15000);*//*
        farms.moisture1.setBodyValue("m","8");
        farms.moisture1.send();*/
        farms.temp1.start();
        farms.temp1.heartbeat();
        farms.moisture1.heartbeat();
        farms.water1.heartbeat();


    }

    public static void profileHeartbeatStatus()
    {
        String ret= "";
        try {
            ret = new HttpOperations().executePost(PublicVariables.baseurl +"/heartbeat/profile/1","",
                    new HashMap<String,String>(){{}});
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Profile Heartbeat info " +   ret );
    }

    public static void profileDisconnectedDevice()
    {
        String ret= "";
        try {
            ret = new HttpOperations().executePost( PublicVariables.baseurl+"/heartbeat/disconnect/1","",
                    new HashMap<String,String>(){{}});
            //System.out.println( ret);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Profile disconnected device info " +   ret );
    }


}

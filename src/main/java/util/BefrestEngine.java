package util;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.net.util.Base64;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class BefrestEngine {
    /*public static void main(String[] args) {
        BefrestEngine engine= new BefrestEngine();
        String a=BefrestEngine.generateSubscriptionAuth("test",2);
        String transmitterobject=new TransmitterObject(UUID.randomUUID().toString(),"action1",new Random().nextInt(100),
                "Device"+String.valueOf(new Random().nextInt(10)),new Random().nextInt(100),
                "Device"+String.valueOf(new Random().nextInt(10)),"parameter"+String.valueOf(new Random().nextInt(10)),"value"+String.valueOf(new Random().nextInt(100)), Duration.ZERO,1).buildJsonString();
        String auth=engine.generatePublishAuth("test");
        System.out.println(engine.publish("4",auth,transmitterobject));
    }*/
    protected static Logger logger = LogManager.getLogger(BefrestEngine.class);
    protected static final long UID = 11388;
    private static final String API_KEY = "981AD273D67D8FDA45B6A18F01BAF63A";
    private static final String SHARED_KEY = "abcdefghijklmnopqrstuvwxyz1234567890";
    protected static final String SUBSCRIBECHANNELENDPOINT = "https://gw.bef.rest/xapi/1/subscribe/%d/%s/%d";
    protected static final String CHANNELSTATUSENDPOINT = "https://api.bef.rest/xapi/1/channel-status/%d/%s";
    protected static final String ENDPOINT = "https://api.bef.rest/xapi/1/publish/%d/%s";
    protected static final String PUBLICENDPOINT = "https://api.bef.rest/xapi/1/multi-publish/%d";
    protected static final String TOPICENDPOINT="https://api.bef.rest/xapi/1/t-publish/%d/%s";

    public static String generateSubscriptionAuth(String chid, int sdkVersion) {
        return generateAuthToken(String.format("/xapi/1/subscribe/%d/%s/%d", UID, chid, sdkVersion));
    }

    public  String generatePublishAuth(String chid) {

        return generateAuthToken(String.format("/xapi/1/publish/%d/%s", UID, chid));
    }

    public  String generateTopicPublishAuth(String chid) {

        return generateAuthToken(String.format("/xapi/1/t-publish/%d/%s", UID, chid));
    }

    private static String generateAuthToken(String addr) {
        try {
            String initialPayload = String.format("%s,%s", API_KEY, addr);
            byte[] md5 = md5(initialPayload);
            String base64 = base64Encode(md5);

            String payload = String.format("%s,%s", SHARED_KEY, base64);
            md5 = md5(payload);
            return  base64Encode(md5).toLowerCase();
        } catch (Exception e) {
            // Log the occurred exception
            return null;
        }
    }

    private static byte[] md5(String input) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        messageDigest.update(input.getBytes());
        return messageDigest.digest();
    }

    private static String base64Encode(byte[] input) {
        return new String(Base64.encodeBase64(input))
                .replaceAll("\\+", "-")
                .replaceAll("=", "")
                .replaceAll("/", "_");
    }

    public String publish( String chid, String auth, String message) {
        HttpPost post = new HttpPost(String.format(ENDPOINT, UID, chid));
        post.setEntity(new StringEntity(message, "UTF8"));
        post.setHeader("X-BF-AUTH", auth);
        StringBuilder responseBuilder = new StringBuilder();

        try (CloseableHttpClient httpClient = HttpClients.custom().build()) {
            try (CloseableHttpResponse response = httpClient.execute(post)) {
                InputStream stream = response.getEntity().getContent();
                int content;
                int statusCode = response.getStatusLine().getStatusCode();
                System.out.println(statusCode);

                if (statusCode == HttpStatus.SC_OK)
                    while ((content = stream.read()) != -1)
                        responseBuilder.append((char) content);

                return responseBuilder.toString();
            }
        } catch (Exception e) {
            // Handle the occurred exception
            logger.error(e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public String publishToTopic( String prifileId, String auth, String message) {
        HttpPost post = new HttpPost(String.format(TOPICENDPOINT, UID, prifileId));
        post.setHeader("X-BF-AUTH", auth);
        post.setEntity(new StringEntity(message, "UTF8"));
        StringBuilder responseBuilder = new StringBuilder();

        try (CloseableHttpClient httpClient = HttpClients.custom().build()) {
            try (CloseableHttpResponse response = httpClient.execute(post)) {
                InputStream stream = response.getEntity().getContent();
                int content;
                int statusCode = response.getStatusLine().getStatusCode();
                System.out.println(statusCode);
                //if (statusCode == HttpStatus.SC_OK)
                    while ((content = stream.read()) != -1)
                        responseBuilder.append((char) content);
                    logger.info(responseBuilder.toString());
                return responseBuilder.toString();
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public String subscribechannel(String deviceId)
    {
        int version=2;
        String auth =generateSubscriptionAuth(deviceId,version);
        String url=String.format(SUBSCRIBECHANNELENDPOINT, UID, deviceId,version);
        System.out.println(url);
        HttpGet post = new HttpGet(url);
        //post.setEntity(new String
        // Entity(message, "UTF8"));
        post.setHeader("X-BF-AUTH", auth);
        post.setHeader("X-BF-TOPICS",deviceId);

        StringBuilder responseBuilder = new StringBuilder();

        try (CloseableHttpClient httpClient = HttpClients.custom().build()) {
            try (CloseableHttpResponse response = httpClient.execute(post)) {
                InputStream stream = response.getEntity().getContent();
                int content;
                int statusCode = response.getStatusLine().getStatusCode();
                System.out.println(statusCode);

                //if (statusCode == HttpStatus.SC_OK)
                    while ((content = stream.read()) != -1)
                        responseBuilder.append((char) content);

                System.out.println("----------------> " + responseBuilder.toString());
                return responseBuilder.toString();
            }
        } catch (Exception e) {
            // Handle the occurred exception
            return null;
        }

    }

    /*public String  chaneelStatus(String deviceId)
    {

    }*/
}

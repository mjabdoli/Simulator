package util;

import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Map;

public class HttpOperations {
    public String executePost(String targetURL) throws Exception {
        return executePost(targetURL,"");
    }

    public String executePost(String targetURL, String urlParameters) throws Exception {
       return executePost(targetURL,urlParameters,new HashMap<String,String>());
  /*      HttpURLConnection connection = null;
        StringBuilder response = new StringBuilder();
        try {
            //Create connection
            URL url = new URL(targetURL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type",
                    "text/html");
            connection.setRequestProperty("Content-Length",
                    Integer.toString(urlParameters.getBytes().length));
            connection.setRequestProperty("Content-Language", "en-US");
            connection.setUseCaches(false);
            connection.setDoOutput(true);
            //Send request
            DataOutputStream wr = new DataOutputStream(
                    connection.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.close();
            //Get Response
            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            // or StringBuffer if Java version 5+
            String line;
            while ((line = rd.readLine()) != null) {

                if (response.length() > 0)
                    response.append('\r');
                response.append(line);

            }
            rd.close();
            return response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (connection != null) {
                connection.disconnect();
                return response.toString();
            }
        }*/
    }

    public String executePost(String url, String postdata, HashMap<String,String> cookies) throws Exception {

        String address=url;
        HttpURLConnection connection = null;

        try {
            System.out.println("f01");
            connection = (HttpURLConnection) new URL(address/*"http://cdn.tabnak.ir/files/fa/news/1396/7/21/785164_939.jpg"*/).openConnection();
            final String COOKIES_HEADER = "Set-Cookie";

            CookieManager cookieManager = CreateCookieManager(cookies);


            //connection.setRequestProperty("Cookie",);
            String ds="";
            for (int i = 0; i <cookieManager.getCookieStore().getCookies().size() ; i++) {
                if (!ds.equals(""))
                    ds+=";";
                ds+=cookieManager.getCookieStore().getCookies().get(i).getName() + "=" + cookieManager.getCookieStore().getCookies().get(i).getValue();
            }


            //String ds=String.join(";", cookieManager.getCookieStore().getCookies());

            //connection.set
            connection.setRequestProperty("Cookie",ds);
            //String encoded= Base64.getEncoder().encodeToString(("admin:nimda").getBytes(StandardCharsets.UTF_8));
            String encoded= "admin:nimda";
            //connection.setRequestProperty("Authorization","Basic "+encoded);

            /*byte[] postDataBytes=null;
            if (!postdata.equals(""))
            {

                postDataBytes= postdata.getBytes();
                connection.setRequestProperty("Content-Length", Integer.toString(postDataBytes.length));

            }*/



            /////////////////////////////
            System.out.println("f1");
            connection.setRequestMethod("POST");
            System.out.println(address);
            connection.setRequestProperty("Content-Type","text/html");
            connection.setRequestProperty("Content-Length",Integer.toString(postdata.getBytes().length));
            connection.setRequestProperty("Content-Language", "en-US");
            connection.setUseCaches(false);
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.connect();
            if (!postdata.equals("")) {
                DataOutputStream wr = new DataOutputStream(
                        connection.getOutputStream());
                wr.writeBytes(postdata);
                wr.close();
            }

            InputStream input = new BufferedInputStream(connection.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            StringBuilder result = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
            input.close();
            return result.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private CookieManager CreateCookieManager(HashMap<String,String> cookielist) {
        //BaseDataInfo.setUserid("714966167405100");



        //String userid =BaseDataInfo.getUserid();
        //String nickname = "zhosseini";
        //BaseDataInfo.setNickname("zhosseini");
        CookieManager msCookieManager = new CookieManager();
        //msCookieManager.getCookieStore().add(null, new HttpCookie("userid", userid));

        for (Map.Entry<String,String> ss: cookielist.entrySet())
            try {
                msCookieManager.getCookieStore().add(null, new HttpCookie(ss.getKey(), URLEncoder.encode(ss.getValue(), "UTF-8")));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        return msCookieManager;
    }

}

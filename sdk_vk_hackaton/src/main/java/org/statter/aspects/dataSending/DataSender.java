package org.statter.aspects.dataSending;

import com.google.gson.Gson;
import com.sun.org.apache.bcel.internal.generic.ACONST_NULL;

import javax.net.ssl.*;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

public class DataSender {

    private final String host = "https://192.168.140.10:5001/api/v1/analyzer/register";
    private final int port = 5001;

    private static  DataSender instance;
    public  static  DataSender getInstance(){

        if(instance == null){
            instance = new DataSender();
        }

        return  instance;
    }

    public  void SendData(NetworkData data){
        try {

            System.out.println("[send data 1]");

            TrustManager[] trustAllCerts = new TrustManager[] {
                    new X509TrustManager() {
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return null;
                        }

                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {
                        }
                    }
            };

            System.out.println("[send data 2]");

            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustAllCerts, new SecureRandom());

            System.out.println("[send data 3]");

            HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());

            System.out.println("[send data 4]");

// Создаем объект URL и открываем соединение
            URL url = new URL(host);
            HttpsURLConnection con = (HttpsURLConnection) url.openConnection();

            System.out.println("[send data 5]");

            con.setSSLSocketFactory(new NoOpSSLSocketFactory());
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");

            System.out.println("[send data 5]");

            HostnameVerifier allHostsValid = (hostname, session) -> true;

            System.out.println("[send data 6]");

            con.setHostnameVerifier(allHostsValid);

            Gson gson = new Gson();

            String json = gson.toJson(data);

            System.out.println("[ui] " + json);
            con.setDoOutput(true);

            DataOutputStream outputStream = new DataOutputStream(con.getOutputStream());

            outputStream.writeBytes(json);
            outputStream.flush();
            outputStream.close();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));
            StringBuilder responseBuilder = new StringBuilder();
            String responseLine = null;
            while ((responseLine = bufferedReader.readLine()) != null) {
                responseBuilder.append(responseLine.trim());
            }

            String response = responseBuilder.toString();
            System.out.println(response);

// Читаем ответ сервера
//            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
//            String inputLine;
//            StringBuffer content = new StringBuffer();
//            while ((inputLine = in.readLine()) != null) {
//                content.append(inputLine);
//            }
//            in.close();

// Выводим ответ сервера на экран
            //System.out.println(content.toString());
        } catch (Exception e) {
            System.out.println("ААА БЛЯ ВСЕ ЕБНУЛОСЬ");
            System.out.println(e);
        }
    }
}

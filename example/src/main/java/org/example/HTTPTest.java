package org.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HTTPTest {
    public HTTPTest(){

    }

    public static void Execute(){
        try {
// Создаем объект URL и открываем соединение
            URL url = new URL("https://www.google.com/");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

// Читаем ответ сервера
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();

// Выводим ответ сервера на экран
            //System.out.println(content.toString());
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    }


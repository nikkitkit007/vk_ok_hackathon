package org.example;

import org.json.JSONArray;
import org.json.JSONObject;

public class JNIActivity {

    /*protected void executeScript() {

        String result = nativeFunction();
        if (result == null) {
            return;
        }
        result = result.dropWhile(it -> it != '{');

        JSONObject textJson = new JSONObject(result);
        String act = textJson.getString("activity");
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TextView textView = findViewById(R.id.result);
                textView.setText(act);
            }
        });
    }

    public native String nativeFunction();

    static {
        System.loadLibrary("jnisocket");
    }*/
}
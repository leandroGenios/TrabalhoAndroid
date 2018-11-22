package br.com.trabalhoandroid.utils;

import android.os.AsyncTask;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.trabalhoandroid.models.Cliente;

public class TaskConnection extends AsyncTask<Object, Object, Object> {
    @Override
    protected Object doInBackground(Object... strings) {
        String url = "http://172.30.246.172:8080/ServidorRest/rest/" + strings[1];
        HttpHelper http = new HttpHelper();
        if(strings[0].equals(Constants.GET)){
            try {
                List<Cliente> list = new ArrayList<Cliente>();
                String json = http.doGet(url);
                System.out.println(json);

                return json;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(strings[0].equals(Constants.POST)){
            try {
                List<Cliente> list = new ArrayList<Cliente>();
                String json = http.doPost(url, (JSONObject) strings[2], "UTF-8");
                System.out.println(json);

                return json;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}

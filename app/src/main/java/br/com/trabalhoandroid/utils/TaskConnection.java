package br.com.trabalhoandroid.utils;

import android.os.AsyncTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.trabalhoandroid.models.Cliente;

public class TaskConnection extends AsyncTask<Object, Object, Object> {
    @Override
    protected Object doInBackground(Object... strings) {
        String url = "http://192.168.1.3:8080/ServidorRest/rest/" + strings[1];
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
                String json = http.doPost(url, (byte[]) null, "UTF-8");
                System.out.println(json);

                return json;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}

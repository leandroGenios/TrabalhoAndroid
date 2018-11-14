package br.com.trabalhoandroid.utils;

import android.os.AsyncTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.trabalhoandroid.models.Cliente;

public class TaskConnection extends AsyncTask<String, String, List<Cliente>> {
    @Override
    protected List<Cliente> doInBackground(String... strings) {
        String url = "http://172.30.246.172:8080/ServidorRest/rest/" + strings[1];

        HttpHelper http = new HttpHelper();
        try {
            if(strings[0].equals(Constants.GET)){
                String json = http.doGet(url);
                System.out.println(json);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<Cliente> list = new ArrayList<Cliente>();
        list.add(new Cliente());
        return list;
    }
}

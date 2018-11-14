package br.com.trabalhoandroid.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Network {

    public static String getJson(String url){
        String retorno = "";
        try {
            URL api = new URL(url);
            int codResposta;
            HttpURLConnection conexao;
            InputStream input;

            conexao = (HttpURLConnection) api.openConnection();
            conexao.setRequestMethod("GET");
            conexao.setReadTimeout(15000);
            conexao.setConnectTimeout(15000);
            conexao.connect();

            codResposta = conexao.getResponseCode();
            if(codResposta < HttpURLConnection.HTTP_BAD_REQUEST){
                input = conexao.getInputStream();
            }else{
                input = conexao.getErrorStream();
            }

            retorno = converterInputStreamToString(input);
            input.close();
            conexao.disconnect();
        } catch (MalformedURLException e) {
            // error url
            e.printStackTrace();
        } catch (IOException e) {
            // error conection
            e.printStackTrace();
        }
        System.out.println(retorno);
        return retorno;
    }

    private static String converterInputStreamToString(InputStream input){
        StringBuffer buffer = new StringBuffer();
        try{
            BufferedReader br;
            String linha;

            br = new BufferedReader(new InputStreamReader(input));
            while((linha = br.readLine())!=null){
                buffer.append(linha);
            }

            br.close();
        }catch(IOException e){
            e.printStackTrace();
        }

        return buffer.toString();
    }
}

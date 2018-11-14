package br.com.trabalhoandroid.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import br.com.trabalhoandroid.adapter.ClienteListAdapter;
import br.com.trabalhoandroid.trabalhoandroid.R;
import br.com.trabalhoandroid.utils.Network;

public class ClientesActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clientes);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ListView listClientes = (ListView) findViewById(R.id.list_clientes);
        listClientes.setAdapter(new ClienteListAdapter(this));
        listClientes.setOnItemClickListener(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(onClickClientes());

        //Network.getJson("http://192.168.1.5:8080/ServidorRest/rest/clientes");

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                // Create URL
                URL githubEndpoint = null;
                try {
                    githubEndpoint = new URL("https://api.github.com/");
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

                // Create connection
                HttpsURLConnection myConnection = (HttpsURLConnection) githubEndpoint.openConnection();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_cliente) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    private View.OnClickListener onClickClientes(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CadastroClienteActivity.class);
                startActivity(intent);
            }
        };
    }
}

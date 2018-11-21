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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import br.com.trabalhoandroid.adapter.ClienteListAdapter;
import br.com.trabalhoandroid.models.Cliente;
import br.com.trabalhoandroid.trabalhoandroid.R;
import br.com.trabalhoandroid.utils.Constants;
import br.com.trabalhoandroid.utils.TaskConnection;

public class ClientesActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clientes);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ListView listClientes = (ListView) findViewById(R.id.list_clientes);
        listClientes.setAdapter(new ClienteListAdapter(this, getClientes()));
        listClientes.setOnItemClickListener(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(onClickClientes());

    }

    private List<Cliente> getClientes() {
        TaskConnection t = new TaskConnection();
        String[] params = new String[2];
        params[0] = Constants.GET;
        params[1] = "clientes";

        String json = null;
        t.execute(params);
        try {
            json = (String) t.get();
            System.out.println("teste  ---" + json);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        List<Cliente> list = new ArrayList<>();
        if(json != null){
            JSONArray array = null;
            try {
                array = new JSONArray(json);
                for (int i = 0; i < array.length(); i++) {
                    JSONObject jsonObj = array.getJSONObject(i);
                    Cliente c = new Cliente();
                    c.setId(jsonObj.getInt("id"));
                    c.setCpf(jsonObj.getString("cpf"));
                    c.setNome(jsonObj.getString("nome"));
                    c.setSobrenome(jsonObj.getString("sobrenome"));
                    list.add(c);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return list;
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

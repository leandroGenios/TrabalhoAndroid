package br.com.trabalhoandroid.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import br.com.trabalhoandroid.adapter.PedidoListAdapter;
import br.com.trabalhoandroid.models.Cliente;
import br.com.trabalhoandroid.models.Pedido;
import br.com.trabalhoandroid.trabalhoandroid.R;
import br.com.trabalhoandroid.utils.Constants;
import br.com.trabalhoandroid.utils.MaskWatcher;
import br.com.trabalhoandroid.utils.TaskConnection;

public class PedidosActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{
    private Context context;
    private ListView listPedidos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedidos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        context = this;

        listPedidos = (ListView) findViewById(R.id.list_pedidos);
        listPedidos.setAdapter(new PedidoListAdapter(this, getPedidos()));
        listPedidos.setOnItemClickListener(this);

        final EditText edtCpf = findViewById(R.id.edtCpf);
        edtCpf.addTextChangedListener(MaskWatcher.buildCpf());

        Button btnPesquisar = findViewById(R.id.btnPesquisar);
        btnPesquisar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TaskConnection t = new TaskConnection();
                String[] params = new String[2];
                params[0] = Constants.GET;
                params[1] = "pedidos/cliente/" + edtCpf.getText().toString();

                String json = null;
                t.execute(params);
                try {
                    json = (String) t.get();
                    Type listType = new TypeToken<ArrayList<Pedido>>(){}.getType();
                    List<Pedido> list = new Gson().fromJson(json, listType);
                    listPedidos.setAdapter(new PedidoListAdapter(context, list));
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(onClickPedido());
    }

    private List<Pedido> getPedidos() {
        TaskConnection t = new TaskConnection();
        String[] params = new String[2];
        params[0] = Constants.GET;
        params[1] = "pedidos";

        String json = null;
        t.execute(params);
        try {
            json = (String) t.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        List<Pedido> list = new ArrayList<>();
        if(json != null){
            JSONArray array = null;
            try {
                array = new JSONArray(json);
                for (int i = 0; i < array.length(); i++) {
                    JSONObject jsonObj = array.getJSONObject(i);
                    Pedido p = new Pedido();
                    p.setId(jsonObj.getInt("id"));

                    String pattern = "yyyy-MM-dd";
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                    Date date = simpleDateFormat.parse(jsonObj.getString("data"));
                    p.setData(date);

                    JSONObject jsonCliente = jsonObj.getJSONObject("cliente");
                    Cliente c = new Cliente();
                    c.setNome(jsonCliente.getString("nome"));
                    c.setSobrenome((jsonCliente.getString("sobrenome")));
                    c.setCpf(jsonCliente.getString("cpf"));
                    c.setId(jsonCliente.getInt("id"));
                    p.setCliente(c);
                    list.add(p);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getApplicationContext(), ItensPedidoActivity.class);
        intent.putExtra("pedido", ((Pedido) parent.getAdapter().getItem(position)).getId());
        startActivity(intent);
    }

    private View.OnClickListener onClickPedido(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(), CadastroItensActivity.class);
            startActivity(intent);
            finish();
            }
        };
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent intent;
        switch (id) {
            case R.id.action_cliente:
                intent = new Intent(getApplicationContext(), ClientesActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.action_produto:
                intent = new Intent(getApplicationContext(), ProdutoActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.action_pedido:
                intent = new Intent(getApplicationContext(), PedidosActivity.class);
                startActivity(intent);
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

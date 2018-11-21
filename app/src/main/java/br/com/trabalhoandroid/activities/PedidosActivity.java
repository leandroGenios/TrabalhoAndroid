package br.com.trabalhoandroid.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import br.com.trabalhoandroid.adapter.PedidoListAdapter;
import br.com.trabalhoandroid.adapter.ProdutoListAdapter;
import br.com.trabalhoandroid.models.Cliente;
import br.com.trabalhoandroid.models.Pedido;
import br.com.trabalhoandroid.models.Produto;
import br.com.trabalhoandroid.trabalhoandroid.R;
import br.com.trabalhoandroid.utils.Constants;
import br.com.trabalhoandroid.utils.TaskConnection;

public class PedidosActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedidos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ListView listPedidos = (ListView) findViewById(R.id.list_pedidos);
        listPedidos.setAdapter(new PedidoListAdapter(this, getPedidos()));
        listPedidos.setOnItemClickListener(this);

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
        startActivity(intent);
    }

    private View.OnClickListener onClickPedido(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(), CadastroPedidoActivity.class);
            startActivity(intent);
            }
        };
    }
}

package br.com.trabalhoandroid.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import br.com.trabalhoandroid.adapter.ClienteSpinnerAdapter;
import br.com.trabalhoandroid.adapter.ItensPedidoListAdapter;
import br.com.trabalhoandroid.adapter.ProdutoSpinnerAdapter;
import br.com.trabalhoandroid.models.Cliente;
import br.com.trabalhoandroid.models.ItemDoPedido;
import br.com.trabalhoandroid.models.Pedido;
import br.com.trabalhoandroid.models.Produto;
import br.com.trabalhoandroid.trabalhoandroid.R;
import br.com.trabalhoandroid.utils.Constants;
import br.com.trabalhoandroid.utils.TaskConnection;

public class CadastroItensActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private ProdutoSpinnerAdapter adapter;
    private ClienteSpinnerAdapter adapterCliente;
    private ListView listItens;
    private ItensPedidoListAdapter listAdapter;
    private ItemDoPedido item;
    private Cliente cliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_itens);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        List<Cliente> clientes = getClientes();
        adapterCliente = new ClienteSpinnerAdapter(this, android.R.layout.simple_spinner_item, clientes);
        Spinner spinnerCliente = (Spinner) findViewById(R.id.spinner_cliente);
        spinnerCliente.setAdapter(adapterCliente);
        spinnerCliente.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cliente = adapterCliente.getItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        List<Produto> produtos = getProdutos();
        adapter = new ProdutoSpinnerAdapter(this,
                android.R.layout.simple_spinner_item,
                produtos);
        Spinner spinnerProdutos = (Spinner) findViewById(R.id.spinner);
        spinnerProdutos.setAdapter(adapter);

        spinnerProdutos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Produto p = adapter.getItem(position);
                item = new ItemDoPedido();
                item.setProduto(p);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        final EditText edtQuantidade = findViewById(R.id.edtQuantidade);
        listItens = findViewById(R.id.list_itens);
        listAdapter = new ItensPedidoListAdapter(this, new ArrayList<ItemDoPedido>());
        listItens.setAdapter(listAdapter);
        listItens.setOnItemClickListener(this);

        Button btnIncluir = findViewById(R.id.btnAlterar);
        btnIncluir.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                ItemDoPedido i = new ItemDoPedido();
                i.setProduto(item.getProduto());
                i.setQuantidade(Integer.parseInt(edtQuantidade.getText().toString()));
                listAdapter.addResults(i);
            }
        });

        Button btnConcluir = findViewById(R.id.btnConcluir);
        btnConcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TaskConnection t = new TaskConnection();
                Object[] params = new Object[3];
                params[0] = Constants.POST;
                params[1] = "pedidos";

                Pedido p = new Pedido();
                p.setCliente(cliente);
                p.setItens(listAdapter.getList());
                String gson = new Gson().toJson(p);
                try {
                    params[2] = new JSONObject(gson);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                t.execute(params);

                try {
                    String json = (String) t.get();
                    if(json != null){
                        Pedido pedido = new Gson().fromJson(json, Pedido.class);
                        Intent intent = new Intent(getApplicationContext(), ItensPedidoActivity.class);
                        intent.putExtra("pedido", pedido.getId());
                        startActivity(intent);
                        finish();
                    }
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private List<Produto> getProdutos() {
        TaskConnection t = new TaskConnection();
        String[] params = new String[2];
        params[0] = Constants.GET;
        params[1] = "produtos";

        String json = null;
        t.execute(params);
        try {
            json = (String) t.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        List<Produto> list = new ArrayList<>();
        if(json != null){
            JSONArray array = null;
            try {
                array = new JSONArray(json);
                for (int i = 0; i < array.length(); i++) {
                    list.add(new Gson().fromJson(array.getJSONObject(i).toString(), Produto.class));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return list;
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
                    list.add(new Gson().fromJson(array.getJSONObject(i).toString(), Cliente.class));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

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

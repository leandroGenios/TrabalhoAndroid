package br.com.trabalhoandroid.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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

import br.com.trabalhoandroid.adapter.ProdutoListAdapter;
import br.com.trabalhoandroid.models.Produto;
import br.com.trabalhoandroid.trabalhoandroid.R;
import br.com.trabalhoandroid.utils.Constants;
import br.com.trabalhoandroid.utils.TaskConnection;

public class ProdutoActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{
    private ListView listProdutos;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produto);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        context = this;

        listProdutos = (ListView) findViewById(R.id.list_produtos);
        listProdutos.setAdapter(new ProdutoListAdapter(this, android.R.layout.simple_spinner_item, getProdutos()));
        listProdutos.setOnItemClickListener(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(onClickProduto());
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
                    JSONObject jsonObj = array.getJSONObject(i);
                    Produto p = new Produto();
                    p.setId(jsonObj.getInt("id"));
                    p.setDescricao(jsonObj.getString("descricao"));
                    list.add(p);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        exibeDialogo((Produto) parent.getAdapter().getItem(position));
    }

    private View.OnClickListener onClickProduto(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(), CadastroProdutoActivity.class);
            startActivity(intent);
            finish();
            }
        };
    }

    private void exibeDialogo(final Produto produto) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Atenção");
        builder.setMessage("Qual a ação desejada?");
        builder.setPositiveButton("Excluir", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                exibeDialogoDelete(produto);
            }
        });
        builder.setNegativeButton("Alterar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                Intent intent = new Intent(getApplicationContext(), AlteraProdutoActivity.class);
                intent.putExtra("produto.id", produto.getId());
                startActivity(intent);
                finish();
            }
        });

        builder.setNeutralButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        AlertDialog alerta = builder.create();
        alerta.show();
    }

    private void exibeDialogoDelete(final Produto produto) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Atenção");
        builder.setMessage("Deseja realmente excluir o produto " + produto.getDescricao() + "?");
        builder.setPositiveButton("Excluir", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                TaskConnection t = new TaskConnection();
                String[] params = new String[2];
                params[0] = Constants.DELETE;
                params[1] = "produtos/" + produto.getId();

                String json = null;
                t.execute(params);
                try {
                    json = (String) t.get();
                    if(json.split("1451").length > 1){
                        exibeMensagem("O produto selecionado não pode ser excluido porque tem pedidos cadastrados.");
                    }else{
                        listProdutos.setAdapter(new ProdutoListAdapter(context, android.R.layout.simple_spinner_item, getProdutos()));
                    }
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
            }
        });
        AlertDialog alerta = builder.create();
        alerta.show();
    }

    private void exibeMensagem(String mensagem){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Atenção");
        builder.setMessage(mensagem);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
            }
        });
        AlertDialog alerta = builder.create();
        alerta.show();
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

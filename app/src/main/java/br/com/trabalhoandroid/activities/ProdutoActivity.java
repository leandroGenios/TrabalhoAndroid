package br.com.trabalhoandroid.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
import br.com.trabalhoandroid.models.Cliente;
import br.com.trabalhoandroid.models.Produto;
import br.com.trabalhoandroid.trabalhoandroid.R;
import br.com.trabalhoandroid.utils.Constants;
import br.com.trabalhoandroid.utils.TaskConnection;

public class ProdutoActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produto);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ListView listProdutos = (ListView) findViewById(R.id.list_produtos);
        listProdutos.setAdapter(new ProdutoListAdapter(this, getProdutos()));
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

    }

    private View.OnClickListener onClickProduto(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CadastroProdutoActivity.class);
                startActivity(intent);
            }
        };
    }
}

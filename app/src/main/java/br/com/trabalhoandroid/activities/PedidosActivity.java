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

import br.com.trabalhoandroid.adapter.PedidoListAdapter;
import br.com.trabalhoandroid.adapter.ProdutoListAdapter;
import br.com.trabalhoandroid.trabalhoandroid.R;

public class PedidosActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedidos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ListView listPedidos = (ListView) findViewById(R.id.list_pedidos);
        listPedidos.setAdapter(new PedidoListAdapter(this));
        listPedidos.setOnItemClickListener(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(onClickPedido());
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

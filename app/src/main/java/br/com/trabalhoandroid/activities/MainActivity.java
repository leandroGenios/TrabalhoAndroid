package br.com.trabalhoandroid.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import br.com.trabalhoandroid.trabalhoandroid.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button btnClientes = (Button) findViewById(R.id.btn_clientes);
        btnClientes.setOnClickListener(onClickClientes());

        Button btnProdutos = (Button) findViewById(R.id.btn_produtos);
        btnProdutos.setOnClickListener(onClickProdutos());

        Button btnPedido = (Button) findViewById(R.id.btn_pedidos);
        btnPedido.setOnClickListener(onClickPedidos());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private View.OnClickListener onClickClientes(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ClientesActivity.class);
                startActivity(intent);
            }
        };
    }

    private View.OnClickListener onClickProdutos(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ProdutoActivity.class);
                startActivity(intent);
            }
        };
    }

    private View.OnClickListener onClickPedidos(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PedidosActivity.class);
                startActivity(intent);
            }
        };
    }
}

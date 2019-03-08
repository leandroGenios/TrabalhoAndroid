package br.com.trabalhoandroid.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.concurrent.ExecutionException;

import br.com.trabalhoandroid.adapter.ItensPedidoListAdapter;
import br.com.trabalhoandroid.models.Pedido;
import br.com.trabalhoandroid.trabalhoandroid.R;
import br.com.trabalhoandroid.utils.Constants;
import br.com.trabalhoandroid.utils.TaskConnection;

public class ItensPedidoActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itens_pedido);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = this.getIntent();
        int id = intent.getIntExtra("pedido", 0);

        Pedido p = getPedido(id);
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String strDate = dateFormat.format(p.getData());
        setTitle("Itens pedido " + strDate + " - " + p.getCliente().getNome());

        ListView listItens = (ListView) findViewById(R.id.list_itens);
        listItens.setAdapter(new ItensPedidoListAdapter(this, p.getItens()));
        listItens.setOnItemClickListener(this);


    }

    public  Pedido getPedido(int id){
        TaskConnection t = new TaskConnection();
        String[] params = new String[2];
        params[0] = Constants.GET;
        params[1] = "pedidos/" + id;

        String json = null;
        t.execute(params);
        try {
            json = (String) t.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Pedido p = null;
        if(json != null){
            System.out.println(json);
            p = new Gson().fromJson(json,Pedido.class);
        }
        return p;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    private View.OnClickListener onClickItem(){
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

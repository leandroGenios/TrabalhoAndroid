package br.com.trabalhoandroid.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

import br.com.trabalhoandroid.models.Cliente;
import br.com.trabalhoandroid.trabalhoandroid.R;
import br.com.trabalhoandroid.utils.Constants;
import br.com.trabalhoandroid.utils.MaskWatcher;
import br.com.trabalhoandroid.utils.TaskConnection;

public class AlteraClienteActivity extends AppCompatActivity {
    private EditText edtCpf;
    private EditText edtNome;
    private EditText edtSobrenome;
    private Button btnAlterar;
    private Cliente cliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_altera_cliente);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        edtCpf = findViewById(R.id.edtCpf);
        edtCpf.addTextChangedListener(MaskWatcher.buildCpf());
        edtNome = findViewById(R.id.edtNome);
        edtSobrenome = findViewById(R.id.edtSobrenome);
        btnAlterar = findViewById(R.id.btnAlterar);

        cliente = new Cliente();
        Intent intent = this.getIntent();
        cliente.setId(intent.getIntExtra("cliente.id", 0));
        getCliente(cliente.getId());
        edtCpf.setText(cliente.getCpf());
        edtNome.setText(cliente.getNome());
        edtSobrenome.setText(cliente.getSobrenome());


        btnAlterar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TaskConnection t = new TaskConnection();
                Object[] params = new Object[3];
                params[0] = Constants.PUT;
                params[1] = "clientes";

                cliente.setCpf(edtCpf.getText().toString());
                cliente.setNome(edtNome.getText().toString());
                cliente.setSobrenome(edtSobrenome.getText().toString());

                String gson = new Gson().toJson(cliente);
                try {
                    params[2] = new JSONObject(gson);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                t.execute(params);

                String json = null;
                try {
                    json = (String) t.get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if(!json.equals("true")){
                    exibeMensagem(json);
                }else{
                    Intent intent = new Intent(getApplicationContext(), ClientesActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    private void getCliente(int id){
        TaskConnection t = new TaskConnection();
        String[] params = new String[2];
        params[0] = Constants.GET;
        params[1] = "clientes/" + id;

        String json = null;
        t.execute(params);
        try {
            json = (String) t.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if(json != null){
            cliente = new Gson().fromJson(json,Cliente.class);
        }
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
}

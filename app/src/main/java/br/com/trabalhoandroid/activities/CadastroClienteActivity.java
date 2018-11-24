package br.com.trabalhoandroid.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import br.com.trabalhoandroid.models.Cliente;
import br.com.trabalhoandroid.trabalhoandroid.R;
import br.com.trabalhoandroid.utils.Constants;
import br.com.trabalhoandroid.utils.TaskConnection;
import retrofit2.Retrofit;

public class CadastroClienteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_cliente);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button btnIncluir = (Button) findViewById(R.id.btnIncluir);

        btnIncluir.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                TaskConnection t = new TaskConnection();
                Object[] params = new Object[3];
                params[0] = Constants.POST;
                params[1] = "clientes";

                Cliente c = new Cliente();
                c.setCpf("111.111.111-11");
                c.setNome("Leandro");
                c.setSobrenome("Soares");

                String gson = new Gson().toJson(c);
                try {
                    params[2] = new JSONObject(gson);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                ;

                t.execute(params);
            }
        });
    }

}

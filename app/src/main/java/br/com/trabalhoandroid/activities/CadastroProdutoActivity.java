package br.com.trabalhoandroid.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import br.com.trabalhoandroid.models.Produto;
import br.com.trabalhoandroid.trabalhoandroid.R;
import br.com.trabalhoandroid.utils.Constants;
import br.com.trabalhoandroid.utils.TaskConnection;

public class CadastroProdutoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_produto);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button btnIncluir = (Button) findViewById(R.id.btnIncluirProduto);
        final EditText edtDescricao = (EditText) findViewById(R.id.edtDescricao);

        btnIncluir.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                TaskConnection t = new TaskConnection();
                Object[] params = new Object[3];
                params[0] = Constants.POST;
                params[1] = "produtos";

                Produto p = new Produto();
                p.setDescricao(edtDescricao.getText().toString());

                String gson = new Gson().toJson(p);
                try {
                    params[2] = new JSONObject(gson);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                t.execute(params);
            }
        });
    }

}

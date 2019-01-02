package br.com.trabalhoandroid.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.trabalhoandroid.models.Cliente;

public class ClienteListAdapter extends ArrayAdapter<Cliente> {
    private String[] list;
    private Context context;
    private List<Cliente> clientes;

    public ClienteListAdapter(Context context, int textViewResourceId,
                              List<Cliente> clientes) {
        super(context, textViewResourceId, clientes);
        this.context = context;
        this.list = new String[clientes.size()];
        this.clientes = clientes;
        int index = 0;

        for ( Cliente c: clientes) {
            this.list[index] = c.getCpf() +" "+ c.getNome() +" "+ c.getSobrenome();
            index++;
        }
    }

    @Override
    public int getCount() {
        return list.length;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String cliente = list[position];
        TextView t = new TextView(context);
        float dip = 50;
        float densidade = context.getResources().getDisplayMetrics().density;

        int px = (int) (dip * densidade + 0.5f)/2;
        t.setPadding(px,px,px,px);
        t.setText(cliente);
        return t;
    }
}

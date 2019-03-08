package br.com.trabalhoandroid.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.trabalhoandroid.models.Cliente;
import br.com.trabalhoandroid.models.Produto;

public class ClienteSpinnerAdapter extends ArrayAdapter<Cliente> {

    private Context context;
    private List<Cliente> clientes;

    public ClienteSpinnerAdapter(Context context, int textViewResourceId,
                                 List<Cliente> clientes) {
        super(context, textViewResourceId, clientes);
        this.context = context;
        this.clientes = clientes;
    }

    @Override
    public int getCount(){
        return clientes.size();
    }

    @Override
    public Cliente getItem(int position){
        return clientes.get(position);
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView label = (TextView) super.getView(position, convertView, parent);
        label.setTextColor(Color.BLACK);
        label.setText(clientes.get(position).getNome() + " " + clientes.get(position).getSobrenome());
        return label;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        TextView label = (TextView) super.getDropDownView(position, convertView, parent);
        label.setTextColor(Color.BLACK);
        label.setText(clientes.get(position).getNome() + " " + clientes.get(position).getSobrenome());
        return label;
    }
}

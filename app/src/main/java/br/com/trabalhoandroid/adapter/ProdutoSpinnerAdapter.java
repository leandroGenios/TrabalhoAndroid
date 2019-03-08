package br.com.trabalhoandroid.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.trabalhoandroid.models.Produto;

public class ProdutoSpinnerAdapter extends ArrayAdapter<Produto> {

    private Context context;
    private List<Produto> produtos;

    public ProdutoSpinnerAdapter(Context context, int textViewResourceId,
                                 List<Produto> produtos) {
        super(context, textViewResourceId, produtos);
        this.context = context;
        this.produtos = produtos;
    }

    @Override
    public int getCount(){
        return produtos.size();
    }

    @Override
    public Produto getItem(int position){
        return produtos.get(position);
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView label = (TextView) super.getView(position, convertView, parent);
        label.setTextColor(Color.BLACK);
        label.setText(produtos.get(position).getDescricao());
        return label;
    }

    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        TextView label = (TextView) super.getDropDownView(position, convertView, parent);
        label.setTextColor(Color.BLACK);
        label.setText(produtos.get(position).getDescricao());
        return label;
    }
}

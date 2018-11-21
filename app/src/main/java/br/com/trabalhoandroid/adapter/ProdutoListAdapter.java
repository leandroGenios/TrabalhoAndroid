package br.com.trabalhoandroid.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.trabalhoandroid.models.Cliente;
import br.com.trabalhoandroid.models.Produto;

public class ProdutoListAdapter extends BaseAdapter {
    private String[] list;
    private Context context;

    public ProdutoListAdapter(Context context, List<Produto> produtos){
        super();
        this.context = context;
        this.list = new String[produtos.size()];
        int index = 0;
        for ( Produto p: produtos) {
            this.list[index] = p.getDescricao();
            index++;
        }
    }

    @Override
    public int getCount() {
        return list.length;
    }

    @Override
    public Object getItem(int position) {
        return list[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String produto = list[position];
        TextView t = new TextView(context);
        float dip = 50;
        float densidade = context.getResources().getDisplayMetrics().density;

        int px = (int) (dip * densidade + 0.5f);
        t.setHeight(px);
        t.setText(produto);
        return t;
    }
}

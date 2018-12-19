package br.com.trabalhoandroid.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import br.com.trabalhoandroid.models.ItemDoPedido;

public class ItensPedidoListAdapter extends BaseAdapter {
    private String[] list;
    private Context context;

    public ItensPedidoListAdapter(Context context, List<ItemDoPedido> itens){
        super();
        this.context = context;
        this.list = new String[itens.size()];
        int index = 0;

        for ( ItemDoPedido i: itens) {
            this.list[index] = i.getQuantidade() +" "+ i.getProduto().getDescricao();
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
        String cliente = list[position];
        TextView t = new TextView(context);
        float dip = 50;
        float densidade = context.getResources().getDisplayMetrics().density;

        int px = (int) (dip * densidade + 0.5f);
        t.setHeight(px);
        t.setText(cliente);
        return t;
    }
}

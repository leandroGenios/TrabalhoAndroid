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
    private List<ItemDoPedido> list;
    private Context context;

    public ItensPedidoListAdapter(Context context, List<ItemDoPedido> itens){
        super();
        this.context = context;
        this.list = itens;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemDoPedido item = list.get(position);
        TextView t = new TextView(context);
        t.setText(item.getQuantidade() + "   " + item.getProduto().getDescricao());
        return t;
    }

    public void addResults(ItemDoPedido item) {
        list.add(item);
        notifyDataSetChanged();
    }

    public List<ItemDoPedido> getList(){
        return list;
    }
}

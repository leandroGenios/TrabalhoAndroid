package br.com.trabalhoandroid.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ItensPedidoListAdapter extends BaseAdapter {
    private String[] list = new String[]{"nome sobrenome\ncpf", "teste1", "teste1", "teste1", "teste1", "teste1", "teste1", "teste1", "teste1", "teste1", "teste1", "teste1", "teste1", "teste1", "teste1", "teste1", "teste1", "teste2"};
    private Context context;

    public ItensPedidoListAdapter(Context context){
        super();
        this.context = context;
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

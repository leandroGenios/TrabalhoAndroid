package br.com.trabalhoandroid.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import br.com.trabalhoandroid.models.Pedido;
import br.com.trabalhoandroid.models.Produto;

public class PedidoListAdapter extends BaseAdapter {
    private String[] list;
    private Context context;
    private List<Pedido> pedidos;

    public PedidoListAdapter(Context context, List<Pedido> pedidos){
        super();
        this.context = context;
        this.list = new String[pedidos.size()];
        this.pedidos = pedidos;
        int index = 0;

        for ( Pedido p: pedidos) {
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            String strDate = dateFormat.format(p.getData());
            this.list[index] = p.getCliente().getCpf() +" "+ p.getCliente().getNome() +" "+ p.getCliente().getSobrenome() +"\n"+ strDate;
            index++;
        }
    }

    @Override
    public int getCount() {
        return list.length;
    }

    @Override
    public Object getItem(int position) {
        return pedidos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String pedido = list[position];
        TextView t = new TextView(context);
        float dip = 50;
        float densidade = context.getResources().getDisplayMetrics().density;

        int px = (int) (dip * densidade + 0.5f)/2;
        t.setPadding(px,px,px,px);
        t.setText(pedido);
        return t;
    }
}

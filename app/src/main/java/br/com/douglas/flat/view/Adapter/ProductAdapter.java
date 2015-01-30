package br.com.douglas.flat.view.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.douglas.flat.R;
import br.com.douglas.flat.helper.NumberHelper;
import br.com.douglas.flat.model.Product;

/**
 * Created by douglas on 29/01/15.
 */
public class ProductAdapter extends ArrayAdapter<Product> {

    public ProductAdapter(Context context, List<Product> items) {
        super(context, R.layout.sale_adapter, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v == null) {

            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.adapter_product, null);

        }

        Product p = getItem(position);

        if (p != null) {
            TextView tt0 = (TextView) v.findViewById(R.id.txt_description);
            TextView tt3 = (TextView) v.findViewById(R.id.txt_value);

            if(tt0 != null){
                tt0.setText(p.getDescription());
            }

            if (tt3 != null) {

                tt3.setText("R$ " + NumberHelper.parseString(p.getValue()));
            }
        }

        return v;
    }
}

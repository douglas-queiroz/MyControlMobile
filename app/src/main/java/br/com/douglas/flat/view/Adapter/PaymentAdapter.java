package br.com.douglas.flat.view.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.douglas.flat.R;
import br.com.douglas.flat.helper.DateHelper;
import br.com.douglas.flat.helper.NumberHelper;
import br.com.douglas.flat.model.Payment;

/**
 * Created by douglas on 26/01/15.
 */
public class PaymentAdapter extends ArrayAdapter<Payment> {

    public PaymentAdapter(Context context, List<Payment> items) {
        super(context, R.layout.sale_adapter, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v == null) {

            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.sale_adapter, null);

        }

        Payment p = getItem(position);

        if (p != null) {
            TextView tt0 = (TextView) v.findViewById(R.id.txt_client);
            TextView tt1 = (TextView) v.findViewById(R.id.categoryId);
            TextView tt3 = (TextView) v.findViewById(R.id.description);

            if(tt0 != null){
                tt0.setText(p.getClient().getName());
            }

            if (tt1 != null) {

                tt1.setText(DateHelper.getStringBr(p.getDate()));
            }
            if (tt3 != null) {

                tt3.setText("R$ " + NumberHelper.parseString(p.getValue()));
            }
        }

        return v;
    }
}

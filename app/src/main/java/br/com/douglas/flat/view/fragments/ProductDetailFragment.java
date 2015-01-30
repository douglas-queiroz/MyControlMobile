package br.com.douglas.flat.view.fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import br.com.douglas.flat.R;
import br.com.douglas.flat.helper.NumberHelper;
import br.com.douglas.flat.model.Product;

public class ProductDetailFragment extends Fragment {
    public static final String ARG_PRODUCT = "product";

    private Product mProduct;
    private TextView txtValue;
    private TextView txtDescription;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mProduct = (Product) getArguments().getSerializable(ARG_PRODUCT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_detail, container, false);

        txtDescription = (TextView) view.findViewById(R.id.txt_description);
        txtValue = (TextView) view.findViewById(R.id.txt_value);

        txtDescription.setText(mProduct.getDescription());
        txtValue.setText("R$ " + NumberHelper.parseString(mProduct.getValue()));

        return view;
    }
}

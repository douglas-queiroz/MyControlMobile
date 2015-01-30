package br.com.douglas.flat.view.fragments;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import br.com.douglas.flat.R;
import br.com.douglas.flat.helper.NumberHelper;
import br.com.douglas.flat.model.Product;
import br.com.douglas.flat.service.ProductService;
import br.com.douglas.flat.view.activity.ProductActivity;

public class ProductDetailFragment extends Fragment {
    public static final String ARG_PRODUCT = "product";

    private Product mProduct;
    private ProductService service;
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

        service = new ProductService(getActivity());

        return view;
    }

    private void loadInformation(){
        txtDescription.setText(mProduct.getDescription());
        txtValue.setText("R$ " + NumberHelper.parseString(mProduct.getValue()));
    }

    @Override
    public void onResume() {
        mProduct = service.get(mProduct.getId());

        loadInformation();
        super.onResume();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_client_detail, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_edit) {
            Intent intent = new Intent(getActivity(), ProductActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable(ProductActivity.ARG_PRODUCT, mProduct);
            intent.putExtras(bundle);

            startActivity(intent);
            return true;
        }else if(id == R.id.action_delete){
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());
            builder.setMessage("Deseja realemnte excluir o produto de " + mProduct.getDescription() + "?")
                    .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            service.delete(mProduct);
                            getActivity().onBackPressed();
                        }
                    })
                    .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User cancelled the dialog
                        }
                    })
                    .setTitle("Atenção");
            // Create the AlertDialog object and return it
            builder.create().show();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

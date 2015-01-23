package br.com.douglas.flat;

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
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Date;

import br.com.douglas.flat.helper.DateHelper;
import br.com.douglas.flat.helper.NumberHelper;
import br.com.douglas.flat.model.Sale;
import br.com.douglas.flat.model.SaleItem;
import br.com.douglas.flat.service.SaleItemService;
import br.com.douglas.flat.service.SaleService;
import br.com.douglas.flat.view.activity.ClientActivity;
import br.com.douglas.flat.view.activity.SaleActivity;

public class SaleDetailFragment extends Fragment {

    public static final String ARG_SALE = "sale";

    private Sale mSale;
    private SaleService mService;
    private SaleItemService saleItemService;

    private TextView mTxtClient;
    private TextView mTxtDate;
    private TextView mTxtTotal;
    private LinearLayout mDescriptionLayout;
    private LinearLayout mValuesLayout;
    private LinearLayout mItensLayout;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mSale= (Sale) getArguments().getSerializable(ARG_SALE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_sale_detail, container, false);

        mTxtClient = (TextView) v.findViewById(R.id.txt_client);
        mTxtDate = (TextView) v.findViewById(R.id.txt_date);
        mTxtTotal = (TextView) v.findViewById(R.id.txt_total);
        mDescriptionLayout = (LinearLayout) v.findViewById(R.id.description_layout);
        mValuesLayout = (LinearLayout) v.findViewById(R.id.values_layout);
        mItensLayout = (LinearLayout) v.findViewById(R.id.itens_layout);

        saleItemService = new SaleItemService(getActivity());
        mService = new SaleService(getActivity());
        mSale.setSaleItems(saleItemService.get(mSale));

        loadInformations();
        loadItens();

        return v;
    }

    private void loadInformations(){
        mTxtClient.setText(mSale.getClient().getName());
        mTxtDate.setText(DateHelper.getStringBr(mSale.getDate()));
        mTxtTotal.setText("R$ " + NumberHelper.parseString(mSale.getTotal()));
    }

    private void loadItens(){
        for (int i = 0; i < mSale.getSaleItems().size(); i++) {
            SaleItem item = mSale.getSaleItems().get(i);


            TextView txtProduct = new TextView(getActivity());
            txtProduct.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            txtProduct.setText(item.getProduct().getDescription());
            txtProduct.setTextSize(20);
            mItensLayout.addView(txtProduct);

            LinearLayout linearLayout = new LinearLayout(getActivity());
            linearLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);

            TextView txtPrice = new TextView(getActivity());
            txtPrice.setLayoutParams(new TableLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));;
            txtPrice.setText("R$ " + NumberHelper.parseString(item.getValue()));
            txtPrice.setTextSize(20);
            linearLayout.addView(txtPrice);

            TextView txtAmount = new TextView(getActivity());
            txtAmount.setLayoutParams(new TableLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));;
            txtAmount.setText(item.getAmount() + "x");
            txtAmount.setTextSize(20);
            linearLayout.addView(txtAmount);

            TextView txtSubtotal = new TextView(getActivity());
            txtSubtotal.setLayoutParams(new TableLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));;
            double subTotal = item.getAmount() * item.getValue();
            txtSubtotal.setText("R$ " + NumberHelper.parseString(subTotal));
            txtSubtotal.setTextSize(20);
            linearLayout.addView(txtSubtotal);

            mItensLayout.addView(linearLayout);
        }
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
            Intent intent = new Intent(getActivity(), SaleActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable(SaleActivity.ARG_Sale, mSale);
            intent.putExtras(bundle);

            startActivity(intent);
            return true;
        }else if(id == R.id.action_delete){
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());
            builder.setMessage("Deseja realemnte excluir a venda de " + mSale.getClient() + "?")
                    .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            mService.delete(mSale);
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

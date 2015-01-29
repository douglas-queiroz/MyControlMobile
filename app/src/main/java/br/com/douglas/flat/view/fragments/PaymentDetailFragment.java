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
import br.com.douglas.flat.helper.DateHelper;
import br.com.douglas.flat.helper.NumberHelper;
import br.com.douglas.flat.model.Payment;
import br.com.douglas.flat.service.PaymentService;
import br.com.douglas.flat.view.activity.PaymentActivity;

public class PaymentDetailFragment extends Fragment {

    public static final String ARG_PAYMENT = "payment";

    private Payment mPayment;

    private PaymentService service;

    private TextView txtClient;
    private TextView txtDate;
    private TextView txtValue;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPayment = (Payment) getArguments().getSerializable(ARG_PAYMENT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_payment_detail, container, false);

        txtClient = (TextView) view.findViewById(R.id.txt_client);
        txtDate = (TextView) view.findViewById(R.id.txt_date);
        txtValue = (TextView) view.findViewById(R.id.txt_value);

        service = new PaymentService(getActivity());

        loadInformations();

        return view;
    }

    private void loadInformations(){
        txtClient.setText(mPayment.getClient().toString());
        txtDate.setText(DateHelper.getStringBr(mPayment.getDate()));
        txtValue.setText("R$ " + NumberHelper.parseString(mPayment.getValue()));
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
            Intent intent = new Intent(getActivity(), PaymentActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable(PaymentActivity.ARG_PAYMENT, mPayment);
            intent.putExtras(bundle);

            startActivity(intent);
            return true;
        }else if(id == R.id.action_delete){
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());
            builder.setMessage("Deseja realemnte excluir o pagamento do cliente " + mPayment.getClient() + "?")
                    .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            service.delete(mPayment);
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

    @Override
    public void onResume() {
        mPayment = service.get(mPayment.getId());
        loadInformations();

        super.onResume();
    }
}

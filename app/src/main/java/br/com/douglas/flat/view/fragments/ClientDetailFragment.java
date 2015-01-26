package br.com.douglas.flat.view.fragments;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import br.com.douglas.flat.PaymentListFragment;
import br.com.douglas.flat.R;
import br.com.douglas.flat.helper.NumberHelper;
import br.com.douglas.flat.model.Client;
import br.com.douglas.flat.model.Contact;
import br.com.douglas.flat.model.SaleItem;
import br.com.douglas.flat.service.ClientService;
import br.com.douglas.flat.service.ContactService;
import br.com.douglas.flat.view.activity.ClientActivity;
import br.com.douglas.flat.view.activity.MainActivity;
import br.com.douglas.flat.view.activity.PaymentActivity;
import br.com.douglas.flat.view.activity.SaleActivity;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ClientDetailFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ClientDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ClientDetailFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public static final String ARG_CLIENT = "client";

    private Client mClient;
    private ClientService service;
    private ContactService contactService;
    private TextView txtName;
    private TextView txtSaldo;
    private Button btnCreateSale;
    private LinearLayout descriptionLayout;
    private LinearLayout valuesLayout;
    private MainActivity context;
    private Button btnDisplaySales;
    private Button btnCreatePayment;

    private OnFragmentInteractionListener mListener;
    private Button btnDisplayPayments;

    public static ClientDetailFragment newInstance(Client client) {
        ClientDetailFragment fragment = new ClientDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_CLIENT, client);
        fragment.setArguments(args);
        return fragment;
    }

    public ClientDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mClient = (Client) getArguments().getSerializable(ARG_CLIENT);
        }

        context = (MainActivity) getActivity();
    }

    public void loadInformations(){
        txtName.setText(mClient.getName());
        txtSaldo.setText(NumberHelper.parseString(mClient.getSale()));
    }

    private void loadContacts(){
        for (int i = 0; i < mClient.getContacts().size(); i++) {
            Contact contact = mClient.getContacts().get(i);

            TextView txtDescription = new TextView(context);
            txtDescription.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            txtDescription.setText("Fone:");
            txtDescription.setTextSize(20);
            descriptionLayout.addView(txtDescription);

            TextView txtContact = new TextView(context);
            txtContact.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            txtContact.setText(contact.getNumber());
            txtContact.setTextSize(20);
            valuesLayout.addView(txtContact);

        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_client_detail, container, false);
        service = new ClientService(context);
        contactService = new ContactService(context);
        mClient.setContacts(contactService.get(mClient));

        txtName = (TextView) v.findViewById(R.id.txtName);
        txtSaldo = (TextView) v.findViewById(R.id.txt_saldo);
        descriptionLayout = (LinearLayout) v.findViewById(R.id.description_layout);
        valuesLayout = (LinearLayout) v.findViewById(R.id.values_layout);
        btnCreateSale = (Button) v.findViewById(R.id.btn_create_sale);
        btnDisplaySales = (Button) v.findViewById(R.id.btn_display_sales);
        btnCreatePayment = (Button) v.findViewById(R.id.btn_create_payment);
        btnDisplayPayments = (Button) v.findViewById(R.id.btn_display_payments);

        loadInformations();
        loadContacts();

        btnCreateSale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SaleActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(ARG_CLIENT, mClient);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });

        btnDisplaySales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new SaleItemFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable(SaleItemFragment.ARG_CLIENT, mClient);
                fragment.setArguments(bundle);
                context.startFragment(fragment);
            }
        });

        btnCreatePayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PaymentActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(PaymentActivity.ARG_CLIENT, mClient);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });

        btnDisplayPayments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new PaymentListFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable(PaymentListFragment.ARG_CLIENT, mClient);
                fragment.setArguments(bundle);
                context.startFragment(fragment);
            }
        });

        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
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
            Intent intent = new Intent(context, ClientActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("client", mClient);
            intent.putExtras(bundle);

            startActivity(intent);
            return true;
        }else if(id == R.id.action_delete){
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
            builder.setMessage("Deseja realemnte excluir " + mClient + "?")
                    .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            service.delete(mClient);
                            context.onBackPressed();
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
        mClient = service.get(mClient.getId());
        loadInformations();
        loadContacts();
        super.onResume();
    }
}

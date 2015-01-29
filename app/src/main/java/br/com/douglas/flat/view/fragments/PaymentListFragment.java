package br.com.douglas.flat.view.fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListAdapter;

import java.util.List;

import br.com.douglas.flat.R;
import br.com.douglas.flat.model.Client;
import br.com.douglas.flat.model.Payment;
import br.com.douglas.flat.service.PaymentService;
import br.com.douglas.flat.view.Adapter.PaymentAdapter;
import br.com.douglas.flat.view.Adapter.SaleAdapter;
import br.com.douglas.flat.view.activity.MainActivity;

public class PaymentListFragment extends Fragment implements AdapterView.OnItemClickListener {

    public static final String ARG_CLIENT = "client";

    private PaymentAdapter mAdapter;

    private Client mClient;
    private List<Payment> payments;
    private PaymentService paymentService;
    private AbsListView mListView;
    private MainActivity mContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mClient = (Client) getArguments().getSerializable(ARG_CLIENT);
        }

        paymentService = new PaymentService(getActivity());

        if(mClient == null){
            payments = paymentService.get();
        }else{
            payments = paymentService.get(mClient);
        }

        mAdapter = new PaymentAdapter(getActivity(), payments);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_payment_list, container, false);

        mListView = (AbsListView) v.findViewById(android.R.id.list);
        ((AdapterView<ListAdapter>) mListView).setAdapter(mAdapter);

        // Set OnItemClickListener so we can be notified on item clicks
        mListView.setOnItemClickListener(this);
        mContext = (MainActivity) this.getActivity();

        return v;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Fragment fragment = new PaymentDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(PaymentDetailFragment.ARG_PAYMENT, payments.get(position));
        fragment.setArguments(bundle);

        mContext.startFragment(fragment);
    }
}

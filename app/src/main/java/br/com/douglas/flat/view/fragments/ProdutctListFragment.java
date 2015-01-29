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
import br.com.douglas.flat.model.Product;
import br.com.douglas.flat.service.ProductService;
import br.com.douglas.flat.view.Adapter.ProductAdapter;
import br.com.douglas.flat.view.activity.MainActivity;

public class ProdutctListFragment extends Fragment implements AdapterView.OnItemClickListener {

    private ProductAdapter mAdapter;
    private ProductService service;
    private List<Product> productList;
    private AbsListView mListView;
    private MainActivity mContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        service = new ProductService(getActivity());
        productList = service.get();
        mAdapter = new ProductAdapter(getActivity(), productList);
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

    }
}

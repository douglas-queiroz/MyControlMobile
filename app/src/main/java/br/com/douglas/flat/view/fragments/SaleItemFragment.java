package br.com.douglas.flat.view.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.douglas.flat.R;

import br.com.douglas.flat.SaleDetailFragment;
import br.com.douglas.flat.model.Client;
import br.com.douglas.flat.model.Sale;
import br.com.douglas.flat.service.SaleService;
import br.com.douglas.flat.view.Adapter.SaleAdapter;
import br.com.douglas.flat.view.activity.MainActivity;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
public class SaleItemFragment extends Fragment implements AbsListView.OnItemClickListener {

    private OnFragmentInteractionListener mListener;
    private SaleService saleService;
    private List<Sale> saleList;
    private Client client;
    public static final String ARG_CLIENT = "client";
    public static final String ARG_SECTION_NUMBER = "section_number";
    public MainActivity mContext;

    /**
     * The fragment's ListView/GridView.
     */
    private AbsListView mListView;
    private SaleAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            client = (Client) getArguments().getSerializable("client");
        }

        saleService = new SaleService(getActivity());
        if(client ==null){
            saleList = saleService.get();
        }else{
            saleList = saleService.get(client);
        }
        mAdapter = new SaleAdapter(getActivity(), saleList);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_saleitem, container, false);

        // Set the adapter
        mListView = (AbsListView) view.findViewById(android.R.id.list);
        ((AdapterView<ListAdapter>) mListView).setAdapter(mAdapter);

        // Set OnItemClickListener so we can be notified on item clicks
        mListView.setOnItemClickListener(this);
        mContext = (MainActivity) this.getActivity();

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Fragment fragment = new SaleDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(SaleDetailFragment.ARG_SALE, saleList.get(position));
        fragment.setArguments(bundle);

        mContext.startFragment(fragment);
    }

    /**
     * The default content for this Fragment has a TextView that is shown when
     * the list is empty. If you would like to change the text, call this method
     * to supply the text it should use.
     */
    public void setEmptyText(CharSequence emptyText) {
        View emptyView = mListView.getEmptyView();

        if (emptyView instanceof TextView) {
            ((TextView) emptyView).setText(emptyText);
        }
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
        public void onFragmentInteraction(String id);
    }

}

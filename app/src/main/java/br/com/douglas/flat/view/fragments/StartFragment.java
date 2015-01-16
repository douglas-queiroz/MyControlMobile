package br.com.douglas.flat.view.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import br.com.douglas.flat.R;
import br.com.douglas.flat.model.Client;
import br.com.douglas.flat.service.ClientService;
import br.com.douglas.flat.view.activity.MainActivity;

/**
 * Created by douglas on 16/01/15.
 */
public class StartFragment extends Fragment{
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    private Button addCliente;
    private ClientService clientService;

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static StartFragment newInstance(int sectionNumber) {
        StartFragment fragment = new StartFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public StartFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
    }
}

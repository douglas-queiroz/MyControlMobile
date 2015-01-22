package br.com.douglas.flat.view.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.douglas.flat.R;
import br.com.douglas.flat.model.Client;
import br.com.douglas.flat.model.Contact;
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
    public static final String ARG_SECTION_NUMBER = "section_number";
    private Button btnImport;
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
        btnImport = (Button) rootView.findViewById(R.id.btn_import);
        btnImport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getContact();
            }
        });

        clientService = new ClientService(this.getActivity());

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
    }

    private void getContact(){
        final List<Client> clients = new ArrayList<Client>();

        final ProgressDialog progDailog = ProgressDialog.show(this.getActivity(), "Importando contatos",
                "Por favor aguarde!", true);
        final Activity activity = this.getActivity();

        new Thread(){
            public void run(){
                Cursor c = activity.getContentResolver().query(ContactsContract.Contacts.CONTENT_URI,
                        null, null, null, null);
                if (c.moveToFirst())
                {
                    do {
                        String id = c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts._ID));

                        String hasPhone = c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

                        if (hasPhone.equalsIgnoreCase("1")) {
                            Cursor phones = activity.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id, null, null);
                            if(phones.moveToFirst()){
                                do {
                                    String cNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                                    String nameContact = c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME));

                                    Client client = null;

                                    if (!clients.isEmpty() && clients.get(clients.size()-1).getName().equals(nameContact)){
                                        client = clients.get(clients.size() -1);
                                    }else{
                                        client = new Client();
                                    }

                                    client.setName(nameContact);
                                    Contact contact = new Contact();
                                    contact.setNumber(cNumber);
                                    contact.setClient(client);
                                    client.getContacts().add(contact);

                                    clients.add(client);
                                }while(phones.moveToNext());
                            }

                        }
                    }while (c.moveToNext());

                }

                clientService.save(clients);

                progDailog.dismiss();
            }
        }.start();
    }
}
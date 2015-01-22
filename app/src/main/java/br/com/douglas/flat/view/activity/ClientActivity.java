package br.com.douglas.flat.view.activity;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.douglas.flat.R;
import br.com.douglas.flat.model.Client;
import br.com.douglas.flat.model.Contact;
import br.com.douglas.flat.service.ClientService;
import br.com.douglas.flat.service.ContactService;

public class ClientActivity extends ActionBarActivity {

    private ClientService clientService;
    private ContactService contactService;
    private EditText edtName;
    private LinearLayout layoutContact;
    private Button btnAddContact;
    private List<EditText> edtContacts = new ArrayList<EditText>();
    private Client client;
    private Context context;
    private List<Integer> contactsDeleted = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);
        context = this;
        edtName = (EditText) findViewById(R.id.edtName);
        layoutContact = (LinearLayout) findViewById(R.id.layout_edt_contact);

        clientService = new ClientService(this);
        contactService = new ContactService(this);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            client = (Client) bundle.getSerializable("client");
            client.setContacts(contactService.get(client));
            edtName.setText(client.getName());
        }

        btnAddContact = (Button) findViewById(R.id.btn_add_contact);
        btnAddContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addContact(null, client.getContacts().size());
            }
        });

        loadContacts();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_client, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            save();
            return true;
        }

        onBackPressed();

        return super.onOptionsItemSelected(item);
    }

    private void loadContacts(){
        if(client != null && !client.getContacts().isEmpty()){
            for (int i = 0; i < client.getContacts().size(); i++) {
                addContact(client.getContacts().get(i).getNumber(), i);
            }
        }else{
            addContact(null, 0);
        }
    }

    private void addContact(String number, final int index){

        final LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);

        EditText edtContact = new EditText(context);
        edtContact.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.WRAP_CONTENT, 1f));
        edtContact.setHint("Fone");
        if(number != null)
            edtContact.setText(number);
        linearLayout.addView(edtContact);
        edtContacts.add(edtContact);

        Button btnDelete = new Button(this);
        btnDelete.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.WRAP_CONTENT, 4f));
        btnDelete.setText("X");
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contactsDeleted.add(index);
                layoutContact.removeView(linearLayout);
            }
        });
        linearLayout.addView(btnDelete);

        layoutContact.addView(linearLayout);
    }

    private void save(){
        if(client == null) {
            client = new Client();
        }
        client.setName(edtName.getText().toString());

        for (int i = 0; i < edtContacts.size(); i++) {
            EditText edtContact = edtContacts.get(i);

            if(client.getContacts().size() > i){
                client.getContacts().get(i).setNumber(edtContact.getText().toString());
            }else{
                Contact contact = new Contact();
                contact.setClient(client);
                contact.setNumber(edtContact.getText().toString());
                client.getContacts().add(contact);
            }
        }

        for (int i = 0; i < contactsDeleted.size(); i++) {
            contactService.delete(client.getContacts().get(contactsDeleted.get(i)));
            client.getContacts().remove(contactsDeleted.get(i));
        }

        clientService.save(client);

        Toast.makeText(this, "Cliente salvo com sucesso!", Toast.LENGTH_SHORT).show();
        onBackPressed();
    }
}

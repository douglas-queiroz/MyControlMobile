package br.com.douglas.flat.view.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import br.com.douglas.flat.R;
import br.com.douglas.flat.model.Client;
import br.com.douglas.flat.model.Contact;
import br.com.douglas.flat.service.ClientService;
import br.com.douglas.flat.service.ContactService;

public class ClientDetailActivity extends ActionBarActivity {

    private ClientService service;
    private ContactService contactService;
    private Client client;
    private TextView txtName;
    private Button btnCreateSale;
    private LinearLayout descriptionLayout;
    private LinearLayout valuesLayout;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_detail);
        context = this;

        client = (Client) getIntent().getExtras().getSerializable("client");
        txtName = (TextView) findViewById(R.id.txtName);
        descriptionLayout = (LinearLayout) findViewById(R.id.description_layout);
        valuesLayout = (LinearLayout) findViewById(R.id.values_layout);
        btnCreateSale = (Button) findViewById(R.id.btn_create_sale);

        service = new ClientService(this);
        contactService = new ContactService(this);
        client.setContacts(contactService.get(client));

        btnCreateSale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SaleActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("client", client);
                intent.putExtras(bundle);

                context.startActivity(intent);
            }
        });

        txtName.setText(client.getName());
        for (int i = 0; i < client.getContacts().size(); i++) {
            Contact contact = client.getContacts().get(i);

            TextView txtDescription = new TextView(this);
            txtDescription.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            txtDescription.setText("Fone:");
            txtDescription.setTextSize(20);
            descriptionLayout.addView(txtDescription);

            TextView txtContact = new TextView(this);
            txtContact.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            txtContact.setText(contact.getNumber());
            txtContact.setTextSize(20);
            valuesLayout.addView(txtContact);

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_client_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_edit) {
            Intent intent = new Intent(this, ClientActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("client", client);
            intent.putExtras(bundle);

            finish();

            startActivity(intent);
            return true;
        }else if(id == R.id.action_delete){
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
            builder.setMessage("Deseja realemnte excluir " + client + "?")
                    .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            service.delete(client);
                            onBackPressed();
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

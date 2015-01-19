package br.com.douglas.flat.view.activity;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import br.com.douglas.flat.R;
import br.com.douglas.flat.model.Client;
import br.com.douglas.flat.service.ClientService;

public class ClientActivity extends ActionBarActivity {

    private ClientService clientService;
    private EditText edtName;
    private EditText edtPhone;
    private EditText edtAddress;
    private Client client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);
        edtName = (EditText) findViewById(R.id.edtName);
        edtPhone = (EditText) findViewById(R.id.edtPhone);
        edtAddress = (EditText) findViewById(R.id.edtAddress);

        clientService = new ClientService(this);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            client = (Client) bundle.getSerializable("client");

            edtName.setText(client.getName());
            edtPhone.setText(client.getPhone());
            edtAddress.setText(client.getAddress());
        }
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

    public void save(){
        if(client == null) {
            client = new Client();
        }
        client.setName(edtName.getText().toString());
        client.setPhone(edtPhone.getText().toString());
        client.setAddress(edtAddress.getText().toString());

        clientService.save(client);

        Toast.makeText(this, "Cliente salvo com sucesso!", Toast.LENGTH_SHORT).show();
        onBackPressed();
    }
}

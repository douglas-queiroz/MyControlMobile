package br.com.douglas.flat.view.activity;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import br.com.douglas.flat.R;
import br.com.douglas.flat.helper.DateHelper;
import br.com.douglas.flat.model.Client;
import br.com.douglas.flat.model.Sale;


public class SaleActivity extends ActionBarActivity {

    private Sale sale = new Sale();
    private TextView txtClient;
    private TextView txtDate;
    private TextView txtTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale);
        Client client = (Client) getIntent().getExtras().getSerializable("client");
        sale.setClient(client);

        loadComponets();
        loadInformations();
    }

    private void loadComponets(){
        txtClient = (TextView) findViewById(R.id.txt_name);
        txtDate = (TextView) findViewById(R.id.txt_date);
        txtTotal = (TextView) findViewById(R.id.txt_total);
    }

    private void loadInformations(){
        txtClient.setText(sale.getClient().getName());
        txtDate.setText(DateHelper.getStringBr(sale.getDate()));
        txtTotal.setText("0,00");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sale, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

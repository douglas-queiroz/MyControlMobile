package br.com.douglas.flat.view.activity;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.util.Date;

import br.com.douglas.flat.R;
import br.com.douglas.flat.helper.DateHelper;
import br.com.douglas.flat.helper.NumberHelper;
import br.com.douglas.flat.model.Client;
import br.com.douglas.flat.model.Payment;
import br.com.douglas.flat.service.PaymentService;

public class PaymentActivity extends ActionBarActivity {

    public static final String ARG_CLIENT = "client";
    public static final String ARG_PAYMENT = "payment";

    private EditText edtValue;
    private EditText edtDate;

    private Payment payment;
    private PaymentService service;
    private TextView txtClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        Bundle bundle = getIntent().getExtras();

        payment = (Payment) bundle.getSerializable(ARG_PAYMENT);
        if(payment == null){
            payment = new Payment();
            Client client = (Client) bundle.getSerializable(ARG_CLIENT);
            payment.setClient(client);
        }

        service = new PaymentService(this);

        loadInformations();
    }

    private void loadInformations(){
        txtClient = (TextView) findViewById(R.id.txt_client);
        edtDate = (EditText) findViewById(R.id.edt_date);
        edtValue = (EditText) findViewById(R.id.edt_value);

        txtClient.setText(payment.getClient().toString());
        edtDate.setText(DateHelper.getStringBr(payment.getDate()));
        if (payment.getId() != 0){
            edtValue.setText(NumberHelper.parseString(payment.getValue()));
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_payment, menu);
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
            Toast.makeText(this, "Pagamento salvo com sucesso!", Toast.LENGTH_SHORT).show();
            onBackPressed();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void save(){
        try {
            payment.setDate(DateHelper.getDateBr(edtDate.getText().toString()));
            payment.setValue(NumberHelper.parseDouble(edtValue.getText().toString()));

            service.save(payment);
        }catch (ParseException e){
            Toast.makeText(this, "Digite valores válidos!", Toast.LENGTH_SHORT).show();
        }
    }
}

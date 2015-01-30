package br.com.douglas.flat.view.activity;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.text.ParseException;

import br.com.douglas.flat.R;
import br.com.douglas.flat.helper.NumberHelper;
import br.com.douglas.flat.model.Product;
import br.com.douglas.flat.service.ProductService;

public class ProductActivity extends ActionBarActivity {

    public static final String ARG_PRODUCT = "product";

    private ProductService service;
    private Product mProduct;

    private EditText edtDescription;
    private EditText edtValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        service = new ProductService(this);

        edtDescription = (EditText) findViewById(R.id.edt_description);
        edtValue = (EditText) findViewById(R.id.edt_value);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            mProduct = (Product) bundle.getSerializable(ARG_PRODUCT);

            edtDescription.setText(mProduct.getDescription());
            edtValue.setText(NumberHelper.parseString(mProduct.getValue()));
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
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void save(){
        try {
            mProduct.setDescription(edtDescription.getText().toString());
            mProduct.setValue(NumberHelper.parseDouble(edtValue.getText().toString()));

            service.save(mProduct);
        } catch (ParseException e) {
            e.printStackTrace();
            Toast.makeText(this, "Digite valores validos", Toast.LENGTH_SHORT);
        }
    }
}

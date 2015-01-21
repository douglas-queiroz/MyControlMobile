package br.com.douglas.flat.view.activity;

import android.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
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
    private LinearLayout layoutItens;
    private Button btnAddItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale);
        Client client = (Client) getIntent().getExtras().getSerializable("client");
        sale.setClient(client);

        loadComponets();
        loadInformations();

        btnAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItem();
            }
        });
    }

    private void loadComponets(){
        txtClient = (TextView) findViewById(R.id.txt_name);
        txtDate = (TextView) findViewById(R.id.txt_date);
        txtTotal = (TextView) findViewById(R.id.txt_total);
        layoutItens = (LinearLayout) findViewById(R.id.layout_itens);
        btnAddItem = (Button) findViewById(R.id.btn_add_item);
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

    String[] arr = { "MS SQL SERVER", "MySQL", "Oracle" };

    private void addItem(){

        ArrayAdapter adapter = new ArrayAdapter
                (this,android.R.layout.select_dialog_item, arr);

        final AutoCompleteTextView edtProdut = new AutoCompleteTextView(this);
        edtProdut.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        edtProdut.setHint("Produto");
        edtProdut.setThreshold(1);
        edtProdut.setAdapter(adapter);
        layoutItens.addView(edtProdut);

        final LinearLayout layout = new LinearLayout(this);
        layout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        layout.setOrientation(layout.HORIZONTAL);

        final EditText edtAmount = new EditText(this);
        edtAmount.setLayoutParams(new TableLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));
        edtAmount.setHint("Quantidade");
        layout.addView(edtAmount);

        final EditText edtSubtotal = new EditText(this);
        edtSubtotal.setLayoutParams(new TableLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));
        edtSubtotal.setHint("Subtotal");
        layout.addView(edtSubtotal);

        edtAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                edtSubtotal.setText(s);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        edtProdut.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                edtAmount.setText("1");
                edtSubtotal.setText("R$ 10,00");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        Button btnRemove = new Button(this);
        btnRemove.setLayoutParams(new TableLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1.5f));
        btnRemove.setText("X");
        btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeItem(edtProdut, layout);
            }
        });
        layout.addView(btnRemove);

        layoutItens.addView(layout);
    }

    private void removeItem(AutoCompleteTextView edtProdut, LinearLayout layoutAmount){
        layoutItens.removeView(edtProdut);
        layoutItens.removeView(layoutAmount);
    }
}

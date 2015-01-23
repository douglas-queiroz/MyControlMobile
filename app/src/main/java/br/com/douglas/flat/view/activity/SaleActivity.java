package br.com.douglas.flat.view.activity;

import android.app.ActionBar;
import android.app.ProgressDialog;
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
import android.widget.Toast;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;

import br.com.douglas.flat.R;
import br.com.douglas.flat.helper.DateHelper;
import br.com.douglas.flat.helper.NumberHelper;
import br.com.douglas.flat.model.Client;
import br.com.douglas.flat.model.Product;
import br.com.douglas.flat.model.Sale;
import br.com.douglas.flat.model.SaleItem;
import br.com.douglas.flat.service.ProductService;
import br.com.douglas.flat.service.SaleItemService;
import br.com.douglas.flat.service.SaleService;


public class SaleActivity extends ActionBarActivity {

    public static final String ARG_Sale = "sale";

    private Sale sale;
    private SaleService service;
    private ProductService productService;
    private SaleItemService saleItemService;

    private TextView txtClient;
    private TextView txtDate;
    private TextView txtTotal;
    private LinearLayout layoutItens;
    private Button btnAddItem;
    private int amountItens = 0;
    private HashMap<String, Product> products = new HashMap<String, Product>();
    private String[] strProducts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale);
        service = new SaleService(this);
        saleItemService = new SaleItemService(this);
        productService = new ProductService(this);

        Bundle args = getIntent().getExtras();
        sale = (Sale) args.getSerializable(ARG_Sale);
        if(sale == null){
            sale = new Sale();
            Client client = (Client) args.getSerializable("client");
            sale.setClient(client);
        }

        loadComponets();
        loadInformations();

        btnAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItem(null);
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

        List<Product> productList = productService.get();
        strProducts = new String[productList.size()];
        for (int i = 0; i < productList.size(); i++) {
            Product product = productList.get(i);
            products.put(product.getDescription(), product);
            strProducts[i] = product.getDescription();
        }

        if(sale.getId() != 0){
            for (int i = 0; i < sale.getSaleItems().size(); i++) {
                SaleItem item = sale.getSaleItems().get(i);
                addItem(item);
            }
        }
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
            save();
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void addItem(SaleItem item){

        ArrayAdapter adapter = new ArrayAdapter
                (this,android.R.layout.select_dialog_item, strProducts);

        final AutoCompleteTextView edtProdut = new AutoCompleteTextView(this);
        edtProdut.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        edtProdut.setHint("Produto");
        edtProdut.setThreshold(1);
        edtProdut.setAdapter(adapter);
        layoutItens.addView(edtProdut);

        final LinearLayout layoutAmount = new LinearLayout(this);
        layoutAmount.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        layoutAmount.setOrientation(layoutAmount.HORIZONTAL);

        final EditText edtAmount = new EditText(this);
        edtAmount.setLayoutParams(new TableLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));
        edtAmount.setHint("Quantidade");
        layoutAmount.addView(edtAmount);

        final EditText edtValue = new EditText(this);
        edtValue.setLayoutParams(new TableLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));
        edtValue.setHint("Valor");
        layoutAmount.addView(edtValue);

        final LinearLayout layoutSubtotal = new LinearLayout(this);
        layoutSubtotal.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        layoutSubtotal.setOrientation(layoutSubtotal.HORIZONTAL);
        layoutSubtotal.setPadding(0, 0, 0, 20);

        final EditText edtSubtotal = new EditText(this);
        edtSubtotal.setLayoutParams(new TableLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));
        edtSubtotal.setHint("Subtotal");
        edtSubtotal.setEnabled(false);
        layoutSubtotal.addView(edtSubtotal);

        Button btnRemove = new Button(this);
        btnRemove.setLayoutParams(new TableLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1.5f));
        btnRemove.setText("X");
        btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeItem(edtProdut, layoutAmount, layoutSubtotal);
            }
        });
        layoutSubtotal.addView(btnRemove);

        layoutItens.addView(layoutAmount);
        layoutItens.addView(layoutSubtotal);

        edtAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateSubTotals(edtAmount, edtValue, edtSubtotal);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        edtValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateSubTotals(edtAmount, edtValue, edtSubtotal);
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
                String description = s.toString();
                Product product = products.get(description);
                if(product != null){
                    edtValue.setText(NumberHelper.parseString(product.getValue()));
                    edtAmount.setText("1");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        if(item != null){
            edtProdut.setText(item.getProduct().getDescription());
            edtValue.setText(NumberHelper.parseString(item.getValue()));
            edtAmount.setText(NumberHelper.parseString(item.getAmount()));
            double subTotal = item.getValue() * item.getAmount();
            edtSubtotal.setText(NumberHelper.parseString(subTotal));
        }

        amountItens ++;
    }

    private void removeItem(AutoCompleteTextView edtProdut, LinearLayout layoutAmount, LinearLayout layoutSubtotal){
        layoutItens.removeView(edtProdut);
        layoutItens.removeView(layoutSubtotal);
        layoutItens.removeView(layoutAmount);
        amountItens --;

        updateTotal();
    }

    private void updateSubTotals(EditText edtAmount, EditText edtPrice, EditText edtSubTotal){
        String strAmount = edtAmount.getText().toString();
        String strPrice = edtPrice.getText().toString();

        if(!strAmount.isEmpty() || !strPrice.isEmpty()){
            try {
                double amount = NumberHelper.parseDouble(strAmount);
                double price = NumberHelper.parseDouble(strPrice);
                double subTotal = amount * price;
                edtSubTotal.setText(NumberHelper.parseString(subTotal));
                updateTotal();
            }catch (Exception e){
                Toast.makeText(this, "Digite um valor válido!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void updateTotal(){
        try {
            double total = 0;
            for (int i = 0; i < amountItens; i++) {
                LinearLayout layoutSubTotal = (LinearLayout) layoutItens.getChildAt(((i+1) * 3) - 1);
                EditText edtSub = (EditText) layoutSubTotal.getChildAt(0);
                double sub = 0;
                    sub = NumberHelper.parseDouble(edtSub.getText().toString());
                total += sub;
            }

            txtTotal.setText(NumberHelper.parseString(total));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void save(){
        try {
            sale.setTotal(NumberHelper.parseDouble(txtTotal.getText().toString()));
            for (int i = 0; i < amountItens; i++) {
                int indexFirst = i * 3;
                int indexSecond = indexFirst + 1;
                int indexThrid = indexFirst + 2;

                EditText edtProduct = (EditText) layoutItens.getChildAt(indexFirst);

                LinearLayout layoutAmount = (LinearLayout) layoutItens.getChildAt(indexSecond);
                EditText edtAmoun = (EditText) layoutAmount.getChildAt(0);
                EditText edtPrice = (EditText) layoutAmount.getChildAt(1);

                LinearLayout layoutSubTotal = (LinearLayout) layoutItens.getChildAt(indexThrid);
                EditText edtSub = (EditText) layoutSubTotal.getChildAt(0);


                Product product = products.get(edtProduct.getText().toString());
                if(product == null) {
                    product = new Product();
                    product.setDescription(edtProduct.getText().toString());
                    product.setValue(NumberHelper.parseDouble(edtPrice.getText().toString()));
                }

                if(sale.getSaleItems().size() > i){
                    SaleItem item = sale.getSaleItems().get(i);
                    item.setSale(sale);
                    item.setProduct(product);
                    item.setAmount(NumberHelper.parseDouble(edtAmoun.getText().toString()));
                    item.setValue(product.getValue());
                }else{
                    SaleItem item = new SaleItem();
                    item.setSale(sale);
                    item.setProduct(product);
                    item.setAmount(NumberHelper.parseDouble(edtAmoun.getText().toString()));
                    item.setValue(product.getValue());

                    sale.getSaleItems().add(item);
                }


            }

            while(sale.getSaleItems().size() > amountItens){
                int lastItem = sale.getSaleItems().size() - 1;
                SaleItem item = sale.getSaleItems().get(lastItem);
                saleItemService.delete(item);
                sale.getSaleItems().remove(lastItem);
            }

            service.save(sale);

            Toast.makeText(this, "Venda salva com sucesso!", Toast.LENGTH_SHORT).show();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}

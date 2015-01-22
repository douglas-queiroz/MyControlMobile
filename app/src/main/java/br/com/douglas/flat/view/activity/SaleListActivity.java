package br.com.douglas.flat.view.activity;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import java.util.List;

import br.com.douglas.flat.R;
import br.com.douglas.flat.helper.DateHelper;
import br.com.douglas.flat.model.Client;
import br.com.douglas.flat.model.Sale;
import br.com.douglas.flat.service.SaleService;
import br.com.douglas.flat.view.Adapter.SaleAdapter;


public class SaleListActivity extends ActionBarActivity {

    private SaleService service;
    private AbsListView mListView;
    private SaleAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale_list);
        Client client = (Client) getIntent().getExtras().getSerializable("client");
        mListView = (AbsListView) findViewById(R.id.list_sales);
        service = new SaleService(this);
        loadSales(client);
    }

    private void loadSales(Client client){
        List<Sale> sales = service.get(client);

        mAdapter = new SaleAdapter(this, sales);

        // Set the adapter
        mListView = (AbsListView) findViewById(R.id.list_sales);
        ((AdapterView<ListAdapter>) mListView).setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sale_list, menu);
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

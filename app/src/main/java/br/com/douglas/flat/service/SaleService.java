package br.com.douglas.flat.service;

import android.content.Context;

import java.util.List;

import br.com.douglas.flat.dao.AbstractDAO;
import br.com.douglas.flat.dao.SaleDAO;
import br.com.douglas.flat.model.Client;
import br.com.douglas.flat.model.Product;
import br.com.douglas.flat.model.Sale;
import br.com.douglas.flat.model.SaleItem;

/**
 * Created by douglas on 20/01/15.
 */
public class SaleService extends AbstractService<Sale> {

    private SaleDAO dao;
    private SaleItemService saleItemService;
    private ClientService clientService;

    public SaleService(Context context){
        dao= new SaleDAO(context);
        saleItemService = new SaleItemService(context);
        clientService = new ClientService(context);
    }

    public List<Sale> get(Client client) {
        return dao.get(client);
    }

    @Override
    public long save(Sale object) {
        long id = super.save(object);
        object.setId((int) id);
        for (int i = 0; i < object.getSaleItems().size(); i++) {
            saleItemService.save(object.getSaleItems().get(i));
        }
        Client client = object.getClient();
        client.setSale(client.getSale() - object.getTotal());
        clientService.save(client);

        return id;
    }

    @Override
    public List<Sale> get() {
        List<Sale> sales = super.get();
        for (int i = 0; i < sales.size(); i++) {
            Sale sale = sales.get(i);
            sale.setClient(clientService.get(sale.getClient().getId()));
        }

        return sales;
    }

    @Override
    public void delete(Sale object) {
        super.delete(object);
        for (int i = 0; i < object.getSaleItems().size(); i++) {
            saleItemService.delete(object.getSaleItems().get(i));
        }

        Client client = object.getClient();
        double sale = client.getSale();
        client.setSale(sale + object.getTotal());
        clientService.save(client);
    }

    @Override
    public AbstractDAO getDao() {
        return dao;
    }
}

package br.com.douglas.flat.service;

import android.content.Context;

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
    public AbstractDAO getDao() {
        return dao;
    }
}

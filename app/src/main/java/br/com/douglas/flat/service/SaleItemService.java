package br.com.douglas.flat.service;

import android.content.Context;

import br.com.douglas.flat.dao.AbstractDAO;
import br.com.douglas.flat.dao.SaleItemDAO;
import br.com.douglas.flat.model.Product;
import br.com.douglas.flat.model.SaleItem;

/**
 * Created by douglas on 20/01/15.
 */
public class SaleItemService extends AbstractService<SaleItem> {

    private SaleItemDAO dao;
    private ProductService productService;

    public SaleItemService(Context context){
        dao = new SaleItemDAO(context);
        productService = new ProductService(context);
    }

    @Override
    public long save(SaleItem object) {
        long id = super.save(object);
        if(object.getProduct().getId() == 0){
            productService.save(object.getProduct());
        }
        return id;
    }

    @Override
    public AbstractDAO getDao() {
        return dao;
    }
}

package br.com.douglas.flat.service;

import android.content.Context;

import br.com.douglas.flat.dao.AbstractDAO;
import br.com.douglas.flat.dao.ProductDAO;
import br.com.douglas.flat.model.Product;

/**
 * Created by douglas on 20/01/15.
 */
public class ProductService extends AbstractService<Product>{

    private ProductDAO dao;

    public ProductService(Context context){
        dao = new ProductDAO(context);
    }

    @Override
    public AbstractDAO getDao() {
        return dao;
    }
}

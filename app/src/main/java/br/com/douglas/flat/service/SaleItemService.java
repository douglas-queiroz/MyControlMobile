package br.com.douglas.flat.service;

import android.content.Context;

import br.com.douglas.flat.dao.AbstractDAO;
import br.com.douglas.flat.dao.SaleItemDAO;
import br.com.douglas.flat.model.SaleItem;

/**
 * Created by douglas on 20/01/15.
 */
public class SaleItemService extends AbstractService<SaleItem> {

    private SaleItemDAO dao;

    public SaleItemService(Context context){
        dao = new SaleItemDAO(context);
    }

    @Override
    public AbstractDAO getDao() {
        return dao;
    }
}

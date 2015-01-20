package br.com.douglas.flat.service;

import android.content.Context;

import br.com.douglas.flat.dao.AbstractDAO;
import br.com.douglas.flat.dao.SaleDAO;
import br.com.douglas.flat.model.Sale;

/**
 * Created by douglas on 20/01/15.
 */
public class SaleService extends AbstractService<Sale> {

    private SaleDAO dao;

    public SaleService(Context context){
        dao= new SaleDAO(context);
    }

    @Override
    public AbstractDAO getDao() {
        return dao;
    }
}

package br.com.douglas.flat.service;

import android.content.Context;

import java.util.List;

import br.com.douglas.flat.dao.AbstractDAO;
import br.com.douglas.flat.dao.SaleItemDAO;
import br.com.douglas.flat.model.Product;
import br.com.douglas.flat.model.Sale;
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
        if(object.getProduct().getId() == 0){
            int id = (int) productService.save(object.getProduct());
            object.getProduct().setId(id);
        }

        return super.save(object);
    }

    @Override
    public AbstractDAO getDao() {
        return dao;
    }

    public List<SaleItem> get(Sale sale) {
        List<SaleItem> itens = dao.get(sale);
        for (int i = 0; i < itens.size(); i++) {
            SaleItem item = itens.get(i);
            item.setProduct(productService.get(item.getProduct().getId()));
        }
        
        return itens;
    }
}

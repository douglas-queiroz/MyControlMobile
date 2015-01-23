package br.com.douglas.flat.service;

import android.content.Context;

import br.com.douglas.flat.dao.AbstractDAO;
import br.com.douglas.flat.dao.PaymentDAO;
import br.com.douglas.flat.model.Payment;

/**
 * Created by douglas on 23/01/15.
 */
public class PaymentService extends AbstractService<Payment> {

    private PaymentDAO dao;

    public PaymentService(Context context){
        dao = new PaymentDAO(context);
    }


    @Override
    public AbstractDAO getDao() {
        return dao;
    }
}

package br.com.douglas.flat.service;

import android.content.Context;

import br.com.douglas.flat.dao.AbstractDAO;
import br.com.douglas.flat.dao.PaymentDAO;
import br.com.douglas.flat.model.Client;
import br.com.douglas.flat.model.Payment;

/**
 * Created by douglas on 23/01/15.
 */
public class PaymentService extends AbstractService<Payment> {

    private PaymentDAO dao;
    private ClientService clientService;

    public PaymentService(Context context){
        dao = new PaymentDAO(context);
        clientService = new ClientService(context);
    }

    @Override
    public long save(Payment object) {
        long id = super.save(object);
        Client client = object.getClient();
        double sale = client.getSale();
        client.setSale(sale + object.getValue());
        clientService.save(client);

        return id;
    }

    @Override
    public AbstractDAO getDao() {
        return dao;
    }
}

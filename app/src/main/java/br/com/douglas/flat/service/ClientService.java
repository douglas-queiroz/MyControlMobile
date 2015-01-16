package br.com.douglas.flat.service;

import android.content.Context;

import br.com.douglas.flat.dao.AbstractDAO;
import br.com.douglas.flat.dao.ClientDAO;
import br.com.douglas.flat.model.Client;

/**
 * Created by douglas on 16/01/15.
 */
public class ClientService extends AbstractService<Client>{

    private ClientDAO dao;

    public ClientService(Context context){
        dao = new ClientDAO(context);
    }

    @Override
    public ClientDAO getDao() {
        return dao;
    }
}

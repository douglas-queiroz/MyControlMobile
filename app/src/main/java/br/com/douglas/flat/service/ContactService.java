package br.com.douglas.flat.service;

import android.content.Context;

import java.util.List;

import br.com.douglas.flat.dao.AbstractDAO;
import br.com.douglas.flat.dao.ContactDAO;
import br.com.douglas.flat.model.Client;
import br.com.douglas.flat.model.Contact;

/**
 * Created by Douglas on 19/01/2015.
 */
public class ContactService extends AbstractService<Contact> {

    private ContactDAO dao;

    public ContactService(Context context){
        dao = new ContactDAO(context);
    }

    public List<Contact> get(Client client){
        return dao.get(client);
    }

    @Override
    public AbstractDAO getDao() {
        return dao;
    }
}
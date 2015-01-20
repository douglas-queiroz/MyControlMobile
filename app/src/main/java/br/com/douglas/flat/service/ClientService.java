package br.com.douglas.flat.service;

import android.content.Context;

import java.util.List;

import br.com.douglas.flat.dao.AbstractDAO;
import br.com.douglas.flat.dao.ClientDAO;
import br.com.douglas.flat.model.Client;
import br.com.douglas.flat.model.Contact;

/**
 * Created by douglas on 16/01/15.
 */
public class ClientService extends AbstractService<Client>{

    private ClientDAO dao;
    private ContactService contactService;

    public ClientService(Context context){
        dao = new ClientDAO(context);
        contactService = new ContactService(context);
    }

    @Override
    public ClientDAO getDao() {
        return dao;
    }

    public void save(List<Client> clients){
        for (int i = 0; i < clients.size(); i++) {
            Client client = clients.get(i);
            if(dao.get(client.getName()) == null) {
                this.save(client);
            }
        }
    }

    @Override
    public long save(Client client) {
        client.setId((int) super.save(client));

        for (int j = 0; j < client.getContacts().size(); j++) {
            Contact contact = client.getContacts().get(j);
            contactService.save(contact);
        }
        return client.getId();
    }
}

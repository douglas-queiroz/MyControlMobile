package br.com.douglas.flat.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import br.com.douglas.flat.model.Client;
import br.com.douglas.flat.model.Contact;

/**
 * Created by douglas on 16/01/15.
 */
public class ClientDAO extends AbstractDAO<Client> {

    public ClientDAO(Context context){
        super(context);
    }

    @Override
    protected ContentValues convertToContent(Client object) {
        ContentValues values = new ContentValues();
        values.put(Client.COLUMN_NAME, object.getName());
        values.put(Client.COLUMN_ADDRESS, object.getAddress());
        values.put(Client.COLUMN_PHONE, object.getPhone());
        values.put(Client.COLUMN_SALE, object.getSale());

        return values;
    }

    @Override
    protected String getTable() {
        return Client.TABLE;
    }

    @Override
    protected String[] getColumns() {
        String[] columns = {Client.COLUMN_ID, Client.COLUMN_NAME, Client.COLUMN_ADDRESS, Client.COLUMN_PHONE, Client.COLUMN_SALE};

        return columns;
    }

    @Override
    protected Client convertToObject(Cursor cursor) {
        Client client = new Client();

        int column_id;

        column_id = cursor.getColumnIndex(Client.COLUMN_ID);
        client.setId(cursor.getInt(column_id));

        column_id = cursor.getColumnIndex(Client.COLUMN_NAME);
        client.setName(cursor.getString(column_id));

        column_id = cursor.getColumnIndex(Client.COLUMN_ADDRESS);
        client.setAddress(cursor.getString(column_id));

        column_id = cursor.getColumnIndex(Client.COLUMN_PHONE);
        client.setPhone(cursor.getString(column_id));

        column_id = cursor.getColumnIndex(Client.COLUMN_SALE);
        client.setSale(cursor.getDouble(column_id));

        return client;
    }

    public Client get(String name){
        Client client = null;
        String selection = Client.COLUMN_NAME + " LIKE ?";
        String[] selectionArgs = { String.valueOf(name) };
        Cursor c = db.query(getTable(), getColumns(), selection, selectionArgs, null, null, null);
        if(c.moveToFirst()) {
            client = convertToObject(c);
        }
        c.close();

        return client;
    }


    public List<Client> get(){
        Cursor c = db.query(getTable(), getColumns(), null, null, null, null, Client.COLUMN_NAME);
        c.moveToFirst();

        List<Client> objectLis = new ArrayList<Client>();

        if (c.moveToFirst()) {
            do {
                Client object = convertToObject(c);
                objectLis.add(object);
            } while (c.moveToNext());
        }
        return objectLis;
    }
}

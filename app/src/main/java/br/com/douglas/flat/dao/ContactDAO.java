package br.com.douglas.flat.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import br.com.douglas.flat.model.Client;
import br.com.douglas.flat.model.Contact;

/**
 * Created by Douglas on 19/01/2015.
 */
public class ContactDAO extends AbstractDAO<Contact> {

    public ContactDAO(Context context) {
        super(context);
    }

    @Override
    protected ContentValues convertToContent(Contact object) {
        ContentValues values = new ContentValues();
        values.put(Contact.COLUMN_CLIENT, object.getClient().getId());
        values.put(Contact.COLUMN_NUMBER, object.getNumber());

        return values;
    }

    @Override
    protected Contact convertToObject(Cursor cursor) {
        Contact contact = new Contact();
        Client client = new Client();

        int columnId = cursor.getColumnIndex(Contact.COLUMN_CLIENT);
        client.setId(cursor.getInt(columnId));

        columnId = cursor.getColumnIndex(Contact.COLUMN_ID);
        contact.setId(cursor.getInt(columnId));

        columnId = cursor.getColumnIndex(Contact.COLUMN_NUMBER);
        contact.setNumber(cursor.getString(columnId));
        contact.setClient(client);

        return contact;
    }

    @Override
    protected String getTable() {
        return Contact.TABLE;
    }

    @Override
    protected String[] getColumns() {
        return new String[]{Contact.COLUMN_ID, Contact.COLUMN_NUMBER, Contact.COLUMN_CLIENT};
    }

    public List<Contact> get(Client client) {
        String selection = Contact.COLUMN_CLIENT + " LIKE ?";
        String[] selectionArgs = { String.valueOf(client.getId()) };
        Cursor c = db.query(getTable(), getColumns(), selection, selectionArgs, null, null, null);

        List<Contact> contacts = new ArrayList<Contact>();
        if(c.moveToFirst()){
            do {
                Contact contact = convertToObject(c);
                contacts.add(contact);
            }while(c.moveToNext());
        }
        c.close();

        return contacts;
    }
}

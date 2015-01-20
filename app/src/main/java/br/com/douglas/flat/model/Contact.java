package br.com.douglas.flat.model;

/**
 * Created by Douglas on 19/01/2015.
 */
public class Contact extends AbstractModel {

    private static final long serialVersionUID = -1308562566681545399L;

    public static final String TABLE = "contacts";
    public static final String COLUMN_CLIENT = "client_id";
    public static final String COLUMN_NUMBER = "number";
    public static final String SQL_CREATE = "CREATE TABLE " + TABLE + "( "
            + COLUMN_ID + " INTEGER PRIMARY KEY,"
            + COLUMN_CLIENT + " INTEGER,"
            + COLUMN_NUMBER + " TEXT)";
    public static final String SQL_DELETE = "DROP TABLE IF EXISTS " + TABLE;

    private Client client;
    private String number;

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}

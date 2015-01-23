package br.com.douglas.flat.model;

import java.util.Date;

/**
 * Created by douglas on 23/01/15.
 */
public class Payment extends AbstractModel {

    private static final long serialVersionUID = -4252681487021507666L;

    public static final String TABLE = "payment";
    public static final String COLUMN_CLIENT = "client_id";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_VALUE = "value";
    public static final String SQL_CREATE = "CREATE TABLE " + TABLE + "( "
            + COLUMN_ID + " INTEGER PRIMARY KEY,"
            + COLUMN_CLIENT + " INTEGER,"
            + COLUMN_DATE + " TEXT,"
            + COLUMN_VALUE + " DECIMAL(10, 2))";
    public static final String SQL_DELETE = "DROP TABLE IF EXISTS " + TABLE;

    private Client client = new Client();
    private Date date = new Date();
    private double value;

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}

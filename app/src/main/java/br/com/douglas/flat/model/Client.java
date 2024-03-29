package br.com.douglas.flat.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by douglas on 16/01/15.
 */
public class Client extends AbstractModel {

    private static final long serialVersionUID = -7210088324642280786L;

    public static final String TABLE = "clients";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_PHONE = "phone";
    public static final String COLUMN_ADDRESS = "address";
    public static final String COLUMN_SALE = "sale";
    public static final String SQL_CREATE = "CREATE TABLE " + TABLE + "( "
            + COLUMN_ID + " INTEGER PRIMARY KEY,"
            + COLUMN_NAME + " TEXT,"
            + COLUMN_PHONE + " TEXT,"
            + COLUMN_ADDRESS + " TEXT,"
            + COLUMN_SALE + " DOUBLE)";
    public static final String SQL_DELETE = "DROP TABLE IF EXISTS " + TABLE;

    private String name;
    private String phone;
    private String address;
    private double sale;
    private List<Contact> contacts = new ArrayList<Contact>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getSale() {
        return sale;
    }

    public void setSale(double sale) {
        this.sale = sale;
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

    @Override
    public String toString() {
        return this.name;
    }
}

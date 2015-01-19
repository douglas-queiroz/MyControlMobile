package br.com.douglas.flat.model;

import java.io.Serializable;

/**
 * Created by douglas on 16/01/15.
 */
public class Client extends AbstractModel implements Serializable {

    private static final long serialVersionUID = 1L;
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

    @Override
    public String toString() {
        return this.name;
    }
}

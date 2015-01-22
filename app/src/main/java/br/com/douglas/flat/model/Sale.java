package br.com.douglas.flat.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.douglas.flat.helper.DateHelper;

/**
 * Created by douglas on 20/01/15.
 */
public class Sale extends AbstractModel {

    private static final long serialVersionUID = -3615441807018043077L;

    public static final String TABLE = "sales";
    public static final String COLUMN_CLIENT_ID = "client";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_TOTAL = "total";
    public static final String COLUMN_OBS = "obs";
    public static final String SQL_CREATE = "CREATE TABLE " + TABLE + "( "
            + COLUMN_ID + " INTEGER PRIMARY KEY,"
            + COLUMN_CLIENT_ID + " INTEGER,"
            + COLUMN_DATE + " TEXT,"
            + COLUMN_TOTAL + " INTEGER,"
            + COLUMN_OBS + " DECIMAL(10, 2))";
    public static final String SQL_DELETE = "DROP TABLE IF EXISTS " + TABLE;

    private List<SaleItem> saleItems = new ArrayList<SaleItem>();
    private Client client = new Client();
    private Date date = new Date();
    private double total;
    private String obs;

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public List<SaleItem> getSaleItems() {
        return saleItems;
    }

    public void setSaleItems(List<SaleItem> saleItems) {
        this.saleItems = saleItems;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    @Override
    public String toString() {
        return DateHelper.getStringBr(this.date);
    }
}

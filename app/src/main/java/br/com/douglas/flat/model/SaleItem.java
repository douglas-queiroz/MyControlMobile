package br.com.douglas.flat.model;

/**
 * Created by douglas on 20/01/15.
 */
public class SaleItem extends AbstractModel {

    private static final long serialVersionUID = 8591390917631059512L;

    public static final String TABLE = "sale_itens";
    public static final String COLUMN_SALE_ID = "sale_id";
    public static final String COLUMN_PRODUCT_ID = "product_id";
    public static final String COLUMN_AMOUNT = "amount";
    public static final String COLUMN_VALUE = "value";
    public static final String SQL_CREATE = "CREATE TABLE " + TABLE + "( "
            + COLUMN_ID + " INTEGER PRIMARY KEY,"
            + COLUMN_SALE_ID + " INTEGER,"
            + COLUMN_PRODUCT_ID + " INTEGER,"
            + COLUMN_AMOUNT + " DECIMAL(10, 2),"
            + COLUMN_VALUE + " DECIMAL(10, 2))";
    public static final String SQL_DELETE = "DROP TABLE IF EXISTS " + TABLE;

    private Sale sale = new Sale();
    private Product product = new Product();
    private double amount;
    private double value;

    public Sale getSale() {
        return sale;
    }

    public void setSale(Sale sale) {
        this.sale = sale;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}

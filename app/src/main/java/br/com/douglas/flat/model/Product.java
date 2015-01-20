package br.com.douglas.flat.model;

/**
 * Created by douglas on 20/01/15.
 */
public class Product extends AbstractModel {

    private static final long serialVersionUID = -6422694305342598677L;

    public static final String TABLE = "products";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_VALUE = "value";
    public static final String SQL_CREATE = "CREATE TABLE " + TABLE + "( "
            + COLUMN_ID + " INTEGER PRIMARY KEY,"
            + COLUMN_DESCRIPTION + " TEXT,"
            + COLUMN_VALUE + " DECIMAL(10, 2))";
    public static final String SQL_DELETE = "DROP TABLE IF EXISTS " + TABLE;

    private String description;
    private double value;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}

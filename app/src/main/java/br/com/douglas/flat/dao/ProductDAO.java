package br.com.douglas.flat.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import br.com.douglas.flat.model.Product;

/**
 * Created by douglas on 20/01/15.
 */
public class ProductDAO extends AbstractDAO<Product> {

    public ProductDAO(Context context) {
        super(context);
    }

    public Product get(String description){
        String selection = Product.COLUMN_DESCRIPTION + " LIKE ?";
        String[] selectionArgs = { String.valueOf(description) };
        Cursor c = db.query(getTable(), getColumns(), selection, selectionArgs, null, null, null);

        Product product = null;
        if(c.moveToFirst()){
            product = convertToObject(c);
        }

        return product;
    }

    @Override
    protected ContentValues convertToContent(Product object) {
        ContentValues values = new ContentValues();
        values.put(Product.COLUMN_DESCRIPTION, object.getDescription());
        values.put(Product.COLUMN_VALUE, object.getValue());

        return values;
    }

    @Override
    protected Product convertToObject(Cursor cursor) {
        Product product = new Product();

        int columnId = cursor.getColumnIndex(Product.COLUMN_ID);
        product.setId(cursor.getInt(columnId));

        columnId = cursor.getColumnIndex(Product.COLUMN_DESCRIPTION);
        product.setDescription(cursor.getString(columnId));

        columnId = cursor.getColumnIndex(Product.COLUMN_VALUE);
        product.setValue(cursor.getDouble(columnId));

        return product;
    }

    @Override
    protected String getTable() {
        return Product.TABLE;
    }

    @Override
    protected String[] getColumns() {
        return new String[]{Product.COLUMN_ID, Product.COLUMN_DESCRIPTION, Product.COLUMN_VALUE};
    }
}

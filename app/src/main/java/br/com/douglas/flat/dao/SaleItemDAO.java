package br.com.douglas.flat.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import br.com.douglas.flat.model.SaleItem;

/**
 * Created by douglas on 20/01/15.
 */
public class SaleItemDAO extends AbstractDAO<SaleItem> {

    public SaleItemDAO(Context context) {
        super(context);
    }

    @Override
    protected ContentValues convertToContent(SaleItem object) {
        ContentValues values = new ContentValues();
        values.put(SaleItem.COLUMN_SALE_ID, object.getSale().getId());
        values.put(SaleItem.COLUMN_PRODUCT_ID, object.getProduct().getId());
        values.put(SaleItem.COLUMN_VALUE, object.getValue());
        values.put(SaleItem.COLUMN_AMOUNT, object.getAmount());

        return values;
    }

    @Override
    protected SaleItem convertToObject(Cursor cursor) {
        SaleItem saleItem = new SaleItem();

        int columnId = cursor.getColumnIndex(SaleItem.COLUMN_ID);
        saleItem.setId(cursor.getInt(columnId));

        columnId = cursor.getColumnIndex(SaleItem.COLUMN_SALE_ID);
        saleItem.getSale().setId(cursor.getInt(columnId));

        columnId = cursor.getColumnIndex(SaleItem.COLUMN_PRODUCT_ID);
        saleItem.getProduct().setId(cursor.getInt(columnId));

        columnId = cursor.getColumnIndex(SaleItem.COLUMN_AMOUNT);
        saleItem.setAmount(cursor.getDouble(columnId));

        columnId = cursor.getColumnIndex(SaleItem.COLUMN_VALUE);
        saleItem.setValue(cursor.getDouble(columnId));

        return saleItem;
    }

    @Override
    protected String getTable() {
        return SaleItem.TABLE;
    }

    @Override
    protected String[] getColumns() {
        return new String[]{SaleItem.COLUMN_ID, SaleItem.COLUMN_SALE_ID, SaleItem.COLUMN_PRODUCT_ID, SaleItem.COLUMN_AMOUNT, SaleItem.COLUMN_VALUE};
    }
}

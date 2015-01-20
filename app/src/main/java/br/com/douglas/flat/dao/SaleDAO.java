package br.com.douglas.flat.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.text.ParseException;
import java.util.Date;

import br.com.douglas.flat.helper.DateHelper;
import br.com.douglas.flat.model.Sale;

/**
 * Created by douglas on 20/01/15.
 */
public class SaleDAO extends AbstractDAO<Sale> {

    public SaleDAO(Context context) {
        super(context);
    }

    @Override
    protected ContentValues convertToContent(Sale object) {
        ContentValues values = new ContentValues();
        values.put(Sale.COLUMN_DATE, DateHelper.getString(object.getDate()));
        values.put(Sale.COLUMN_TOTAL, object.getTotal());
        values.put(Sale.COLUMN_OBS, object.getObs());

        return values;
    }

    @Override
    protected Sale convertToObject(Cursor cursor) {
        try {
            Sale sale = new Sale();

            int columnId = cursor.getColumnIndex(Sale.COLUMN_ID);
            sale.setId(cursor.getInt(columnId));

            columnId = cursor.getColumnIndex(Sale.COLUMN_DATE);
            sale.setDate(DateHelper.getDate(cursor.getString(columnId)));

            columnId = cursor.getColumnIndex(Sale.COLUMN_TOTAL);
            sale.setTotal(cursor.getDouble(columnId));

            columnId = cursor.getColumnIndex(Sale.COLUMN_OBS);
            sale.setObs(cursor.getString(columnId));

            return sale;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected String getTable() {
        return Sale.TABLE;
    }

    @Override
    protected String[] getColumns() {
        return new String[]{Sale.COLUMN_ID, Sale.COLUMN_DATE, Sale.COLUMN_TOTAL, Sale.COLUMN_OBS};
    }
}

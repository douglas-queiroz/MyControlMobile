package br.com.douglas.flat.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import br.com.douglas.flat.helper.DateHelper;
import br.com.douglas.flat.model.Client;
import br.com.douglas.flat.model.Payment;

/**
 * Created by douglas on 23/01/15.
 */
public class PaymentDAO extends AbstractDAO<Payment> {

    public PaymentDAO(Context context) {
        super(context);
    }

    @Override
    protected ContentValues convertToContent(Payment object) {
        ContentValues values = new ContentValues();
        values.put(Payment.COLUMN_CLIENT, object.getClient().getId());
        values.put(Payment.COLUMN_DATE, DateHelper.getString(object.getDate()));
        values.put(Payment.COLUMN_VALUE, object.getValue());

        return values;
    }

    @Override
    protected Payment convertToObject(Cursor cursor) {
        try {
            Payment payment = new Payment();

            int columnID = cursor.getColumnIndex(Payment.COLUMN_ID);
            payment.setId(cursor.getInt(columnID));

            columnID = cursor.getColumnIndex(Payment.COLUMN_CLIENT);
            payment.getClient().setId(cursor.getInt(columnID));

            columnID = cursor.getColumnIndex(Payment.COLUMN_DATE);
            payment.setDate(DateHelper.getDate(cursor.getString(columnID)));

            columnID = cursor.getColumnIndex(Payment.COLUMN_VALUE);
            payment.setValue(cursor.getDouble(columnID));

            return payment;
        } catch (ParseException e) {
            e.printStackTrace();

            return null;
        }
    }

    @Override
    protected String getTable() {
        return Payment.TABLE;
    }

    @Override
    protected String[] getColumns() {
        return new String[]{Payment.COLUMN_ID, Payment.COLUMN_CLIENT, Payment.COLUMN_DATE, Payment.COLUMN_VALUE};
    }

    public List<Payment> get(Client client) {
        String selection = Payment.COLUMN_CLIENT + " LIKE ?";
        String[] selectionArgs = { String.valueOf(client.getId()) };
        Cursor c = db.query(getTable(), getColumns(), selection, selectionArgs, null, null, null);

        List<Payment> payments = new ArrayList<Payment>();
        if(c.moveToFirst()){
            do {
                Payment payment = convertToObject(c);
                payment.setClient(client);
                payments.add(payment);
            }while (c.moveToNext());
        }

        return payments;
    }
}

package br.com.douglas.flat.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.douglas.flat.helper.FlatDBHelper;
import br.com.douglas.flat.model.AbstractModel;
import br.com.douglas.flat.model.Client;

/**
 * Created by douglas on 16/01/15.
 */
public abstract class AbstractDAO<T extends AbstractModel> {

    private FlatDBHelper mDBHelper;
    protected SQLiteDatabase db;

    public AbstractDAO(Context context){
        mDBHelper = new FlatDBHelper(context);
        db = mDBHelper.getWritableDatabase();
    }

    public long insert(T object){
        return db.insert(getTable(), null, convertToContent(object));
    }

    public void update(T object){
        ContentValues values = convertToContent(object);
        String selection = object.COLUMN_ID + " LIKE ?";
        String[] selectionArgs = {String.valueOf(object.getId())};

        db.update(getTable(), values, selection, selectionArgs);
    }

    public void delete(T object){
        String selection = object.COLUMN_ID + " LIKE ?";
        String[] selectionArgs = { String.valueOf(object.getId()) };
        db.delete(getTable(), selection, selectionArgs);
    }

    public List<T> get(){
        Cursor c = db.query(getTable(), getColumns(), null, null, null, null, null);
        c.moveToFirst();

        List<T> objectLis = new ArrayList<T>();

        if (c.moveToFirst()) {
            do {
                T object = convertToObject(c);
                objectLis.add(object);
            } while (c.moveToNext());
        }
        return objectLis;
    }

    protected abstract ContentValues convertToContent(T object);

    protected abstract T convertToObject(Cursor cursor);

    protected abstract String getTable();

    protected abstract String[] getColumns();
}

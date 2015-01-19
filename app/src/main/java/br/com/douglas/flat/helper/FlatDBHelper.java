package br.com.douglas.flat.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import br.com.douglas.flat.model.Client;
import br.com.douglas.flat.model.Contact;

/**
 * Created by douglas on 16/01/15.
 */
public class FlatDBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Flat.db";

    public FlatDBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Client.SQL_CREATE);
        db.execSQL(Contact.SQL_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(Client.SQL_DELETE);
        db.execSQL(Contact.SQL_DELETE);
        onCreate(db);
    }
}

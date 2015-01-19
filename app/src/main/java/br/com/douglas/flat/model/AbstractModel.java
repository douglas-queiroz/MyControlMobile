package br.com.douglas.flat.model;

import java.io.Serializable;

/**
 * Created by douglas on 16/01/15.
 */
public abstract class AbstractModel implements Serializable{

    public static final String COLUMN_ID = "_id";

    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

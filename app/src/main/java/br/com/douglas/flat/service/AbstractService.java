package br.com.douglas.flat.service;

import android.content.Context;

import java.util.List;

import br.com.douglas.flat.dao.AbstractDAO;
import br.com.douglas.flat.model.AbstractModel;

/**
 * Created by douglas on 16/01/15.
 */
public abstract class AbstractService<T extends AbstractModel> {

    public long save(T object){
        if(object.getId() == 0){
            return getDao().insert(object);
        }else{
            getDao().update(object);
            return object.getId();
        }
    }

    public void delete(T object){
        getDao().delete(object);
    }

    public List<T> get(){
        return getDao().get();
    }

    public T get(int Id){
        return (T) getDao().get(Id);
    }

    public abstract AbstractDAO getDao();
}

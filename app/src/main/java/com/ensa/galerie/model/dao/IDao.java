package com.ensa.galerie.model.dao;

import java.util.List;

public interface IDao<T> {
    public List<T> findAll();
    public T findById(int id);

    public void add(T o);

    public void del(T o);

    public void update(T o);

}

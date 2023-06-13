package model.dao;

import java.util.List;

public interface ObjectDao <T> {
    void insert (T entity);
    void update (T entity, Integer id);
    void deleteById (Integer id);
    T getById(Integer id) throws ClassNotFoundException;
    List<T> getAll();
}

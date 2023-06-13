package model.dao;

import db.DB;
import model.dao.impl.SellerDaoJDBC;

import java.sql.Connection;

public class DaoFactory {
    public static SellerDaoJDBC createSellerDao() throws ClassNotFoundException {
        return new SellerDaoJDBC(DB.getConnection());
    }
}

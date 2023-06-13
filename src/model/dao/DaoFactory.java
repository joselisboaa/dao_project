package model.dao;

import db.DB;
import model.dao.impl.DepartmentDaoJDBC;
import model.dao.impl.SellerDaoJDBC;

public class DaoFactory {
    public static SellerDaoJDBC createSellerDao() throws ClassNotFoundException {
        return new SellerDaoJDBC(DB.getConnection());
    }

    public static DepartmentDaoJDBC createDepartmentDao() throws ClassNotFoundException {
        return new DepartmentDaoJDBC(DB.getConnection());
    }
}

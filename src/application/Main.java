package application;

import model.dao.DaoFactory;
import model.dao.ObjectDao;
import model.dao.impl.DepartmentDaoJDBC;
import model.entities.Department;
import model.entities.Seller;

import java.util.List;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException {

        ObjectDao<Seller> sellerDao = DaoFactory.createSellerDao();
        ObjectDao<Department> departmentDao = DaoFactory.createDepartmentDao();

        Department department = new Department(1, "Seller");

        Seller seller = sellerDao.getById(1);
        Department department1 = departmentDao.getById(2);

        List<Seller> allSellers = sellerDao.getAll();
        System.out.println(seller);
        System.out.println("Department " + department1);

        for (Seller sel : allSellers) {
            System.out.println(sel);
        }

    }
}
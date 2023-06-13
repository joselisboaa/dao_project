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

        Seller seller = sellerDao.getById(1);
        Department department1 = departmentDao.getById(2);

        List<Seller> allSellers = sellerDao.getAll();
        List<Department> allDepartments = departmentDao.getAll();

        System.out.println(seller);
        System.out.println("Department " + department1);

        for (Seller sel : allSellers) {
            System.out.println(sel);
        }

        for (Department dep : allDepartments) {
            System.out.println(dep);
        }
    }
}
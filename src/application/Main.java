package application;

import model.dao.DaoFactory;
import model.dao.ObjectDao;
import model.entities.Department;
import model.entities.Seller;

import java.util.List;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException {

        ObjectDao<Seller> sellerDao = DaoFactory.createSellerDao();
        Department department = new Department(1, "Seller");

        Seller seller = sellerDao.getById(1);
        List<Seller> allSellers = sellerDao.getAll();
        System.out.println(seller);

        for (Seller sel : allSellers) {
            System.out.println(sel);
        }

    }
}
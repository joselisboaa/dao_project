package application;

import model.dao.DaoFactory;
import model.dao.ObjectDao;
import model.entities.Department;
import model.entities.Seller;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {

        ObjectDao<Seller> sellerDao = DaoFactory.createSellerDao();
        Department department = new Department(1, "Seller");

        Seller seller = sellerDao.getById(1);

    }
}
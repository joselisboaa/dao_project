package application;

import model.entities.Department;
import model.entities.Seller;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        Department department = new Department(1, "Seller");

        Seller seller = new Seller(1, "José", "jose@gmail.com", LocalDate.now(), 2000.00, department);

    }
}
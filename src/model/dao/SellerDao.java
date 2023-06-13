package model.dao;

import model.entities.Department;
import model.entities.Seller;

import java.util.List;

public interface SellerDao extends ObjectDao<Seller> {
    List<Seller> getByDepartment(Department department);
}

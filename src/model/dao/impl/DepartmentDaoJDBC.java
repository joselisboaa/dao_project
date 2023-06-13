package model.dao.impl;

import db.DbException;
import model.dao.DaoFactory;
import model.dao.ObjectDao;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDaoJDBC implements ObjectDao<Department> {
    private Connection conn;

    public DepartmentDaoJDBC (Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Department entity) {
        PreparedStatement st = null;

        try {
            st = conn.prepareStatement("INSERT INTO department VALUES(?, ?)");
            st.setObject(1, entity.getId());
            st.setObject(2, entity.getName());
            st.execute();

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }

    @Override
    public void update(Department entity) {

    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public List<Department> getAll() {
        PreparedStatement st = null;
        ResultSet rs = null;
        List<Department> departments = new ArrayList<>();

        try {
            st = conn.prepareStatement("SELECT name, id AS department_id FROM department");
            rs = st.executeQuery();

            while(rs.next()) {
                Department department = instantiateDepartment(rs);
                departments.add(department);
            }

            return departments;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }

    @Override
    public Department getById(Integer id) throws ClassNotFoundException {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement("SELECT name, id AS department_id FROM department WHERE id = ?");
            st.setInt(1, id);
            rs = st.executeQuery();

            if (rs.next()) {
                Department department = instantiateDepartment(rs);
                ObjectDao<Seller> sellerDao = DaoFactory.createSellerDao();
                List<Seller> sellerList = sellerDao.getAll();

                for (Seller seller : sellerList) {
                    department.addSeller(seller);
                }

                return department;
            }

            return null;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }

    private Department instantiateDepartment(ResultSet rs) throws SQLException {
        Integer depId = rs.getInt("department_id");
        String depName = rs.getString("name");

        Department department = new Department(depId, depName);

        return department;
    }
}

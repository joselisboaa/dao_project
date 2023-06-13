package model.dao.impl;

import db.DB;
import db.DbException;
import model.dao.ObjectDao;
import model.entities.Department;
import model.entities.Seller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SellerDaoJDBC implements ObjectDao<Seller> {
    private Connection conn;

    public SellerDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Seller entity) {

    }

    @Override
    public void update(Seller entity) {

    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public List<Seller> getAll() {
        PreparedStatement st = null;
        ResultSet rs = null;
        List<Seller> sellerList = new ArrayList<>();

        try {
            st = conn.prepareStatement("SELECT seller.*, department.name as depName, department.id as department_id FROM seller INNER JOIN department ON seller.department_id = department.id");
            rs = st.executeQuery();


            while (rs.next()) {
                Department department = instantiateDepartment(rs);
                Seller seller = instantiateSeller(rs, department);

                sellerList.add(seller);
            }

            return sellerList;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }

    @Override
    public Seller getById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement(
                    "SELECT seller.*, department.name as DepName FROM seller INNER JOIN department ON seller.department_id = department.id WHERE seller.id = ?"
            );

            st.setInt(1, id);
            rs = st.executeQuery();

            if(rs.next()) {
                Department department = instantiateDepartment(rs);
                Seller seller = instantiateSeller(rs, department);

                return seller;
            }

            return null;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    private Department instantiateDepartment(ResultSet rs) throws SQLException {
        Integer depId = rs.getInt("department_id");
        String depName = rs.getString("DepName");

        Department department = new Department(depId, depName);

        return department;
    }

    private Seller instantiateSeller(ResultSet rs, Department dp) throws SQLException {
        Integer sellerId = rs.getInt("id");
        String sellerName = rs.getString("name");
        String sellerEmail = rs.getString("email");
        String birthDateString = rs.getString("birth_date");
        Double baseSalary = rs.getDouble("base_salary");

        LocalDate birthDate = LocalDate.parse(birthDateString);

        Seller seller = new Seller(sellerId, sellerName, sellerEmail, birthDate, baseSalary, dp);

        return seller;
    }
}

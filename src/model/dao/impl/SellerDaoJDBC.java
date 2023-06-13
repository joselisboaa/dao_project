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
    public Seller getById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement(
                    "SELECT seller.*, department.name as DepName " +
                            "FROM seller INNER JOIN department " +
                            "ON seller.DepartmentId = department.Id " +
                            "WHERE seller.Id = ?"
            );

            st.setInt(1, id);
            rs = st.executeQuery();

            if(rs.next()) {
                Integer depId = rs.getInt("DepartmentId");
                String depName = rs.getString("DepName");
                Integer sellerId = rs.getInt("id");
                String sellerName = rs.getString("name");
                String sellerEmail = rs.getString("email");
                String birthDateString = rs.getString("birthdate");
                Double baseSalary = rs.getDouble("basesalary");

                LocalDate birthDate = LocalDate.parse(birthDateString);

                Department department = new Department(depId, depName);
                Seller seller = new Seller(sellerId, sellerName, sellerEmail, birthDate, baseSalary, department);

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

    @Override
    public List<Seller> getAll() {
        return null;
    }
}

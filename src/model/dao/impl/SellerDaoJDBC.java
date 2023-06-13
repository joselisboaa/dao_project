package model.dao.impl;

import db.DB;
import db.DbException;
import model.dao.ObjectDao;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SellerDaoJDBC implements SellerDao {
    private Connection conn;

    public SellerDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Seller entity) {
        PreparedStatement st = null;

        try {
            st = conn.prepareStatement("INSERT INTO seller VALUES(?, ?, ?, ?, ?, ?)");
            st.setObject(1, entity.getId());
            st.setObject(2, entity.getDepartment().getId());
            st.setObject(3, entity.getName());
            st.setObject(4, entity.getEmail());
            st.setObject(5, entity.getBirthDate());
            st.setObject(6, entity.getSalary());

            st.executeUpdate();
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void update(Seller entity, Integer id) {
        PreparedStatement st = null;

        try {
            st = conn.prepareStatement("UPDATE seller SET name = ?, email = ?, birth_date = ?," +
                    " base_salary = ?, department_id = ? WHERE id = ?");
            st.setString(1, entity.getName());
            st.setString(2, entity.getEmail());
            st.setObject(3, entity.getBirthDate());
            st.setDouble(4, entity.getSalary());
            st.setInt(5, entity.getDepartment().getId());
            st.setInt(6, id);

            st.executeUpdate();;

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }

    @Override
    public void deleteById(Integer id) {
        PreparedStatement st = null;

        try {
            st = conn.prepareStatement("DELETE * FROM seller WHERE id = ?");
            st.setInt(1, id);
            int rows = st.executeUpdate();

            if (rows == 0) {
                throw new DbException("Seller not found.");
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
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
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
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

    @Override
    public List<Seller> getByDepartment(Department department) {
        PreparedStatement st = null;
        ResultSet rs = null;
        List<Seller> sellerList = new ArrayList<>();

        try {
            st = conn.prepareStatement("SELECT seller.*, department.name as DepName " +
                    "FROM seller INNER JOIN department ON seller.department_id = department.id " +
                    "WHERE department_id = ? ORDER BY NAME");
            st.setInt(1, department.getId());
            rs = st.executeQuery();

            while(rs.next()) {
                Department dep = instantiateDepartment(rs);
                Seller seller = instantiateSeller(rs, dep);

                sellerList.add(seller);
            }

            return sellerList;
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

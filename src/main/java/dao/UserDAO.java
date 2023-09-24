package dao;

import com.mysql.cj.exceptions.ConnectionIsClosedException;
import model.EGender;
import model.Role;
import model.User;
import service.dto.Page;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDAO extends DatabaseConnection{
    public Page<User> findAll(int page, boolean isShowRestore, String search){
        var result = new Page<User>();
        final int TOTAL_ELEMENT = 5;
        result.setCurrentPage(page);
        var content = new ArrayList<User>();

        if(search == null){
            search = "";
        }
        search = "%" + search.toLowerCase() + "%";
        final var DELETED = isShowRestore ? 1 : 0;

        var SELECT_ALL = "SELECT u.*, r.name role_name " +
                "FROM users u " +
                "JOIN roles r ON u.role_id = r.id " +
                "JOIN eGender g ON u.gender = g.name" +
                "WHERE u.deleted = ? AND (LOWER(u.firstName) LIKE ? OR LOWER(u.lastName) LIKE ? " +
                "OR LOWER(u.username) LIKE ? OR LOWER(u.email) LIKE ? OR LOWER(u.role) LIKE ? OR LOWER(u.gender) LIKE ? " +
                "LIMIT ? OFFSET ?";

        var SELECT_COUNT = "SELECT count(*) cnt " +
                "FROM users u " +
                "JOIN roles r ON u.role_id = r.id " +
                "JOIN eGender g ON u.gender = g.name" +
                "WHERE u.deleted = ? AND (LOWER(u.firstName) LIKE ? OR LOWER(u.lastName) LIKE ? " +
                "OR LOWER(u.username) LIKE ? OR LOWER(u.email) LIKE ? OR LOWER(u.role) LIKE ? OR LOWER(u.gender) LIKE ? ";

        try {
            Connection connection = getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL);
            preparedStatement.setInt(1,DELETED);
            preparedStatement.setString(2,search);
            preparedStatement.setString(3,search);
            preparedStatement.setString(4,search);
            preparedStatement.setString(5,search);
            preparedStatement.setString(6,search);
            preparedStatement.setString(7,search);
            preparedStatement.setInt(8,TOTAL_ELEMENT);
            preparedStatement.setInt(9,(page-1)*TOTAL_ELEMENT);
            System.out.println(preparedStatement);

            var rs = preparedStatement.executeQuery();
            while (rs.next()){
                content.add(getUserByResultSet(rs));
            }

            result.setContent(content);

            PreparedStatement preparedStatementCount = connection.prepareStatement(SELECT_COUNT);
            preparedStatementCount.setInt(1,DELETED);
            preparedStatementCount.setString(2,search);
            preparedStatementCount.setString(3,search);
            preparedStatementCount.setString(4,search);
            preparedStatementCount.setString(5,search);
            preparedStatementCount.setString(6,search);
            preparedStatementCount.setString(7,search);

            var rsCount = preparedStatementCount.executeQuery();
            if(rsCount.next()){
                result.setTotalPage((int) Math.ceil((double) rsCount.getInt("cnt")/TOTAL_ELEMENT));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    public void create(User user){
        String CREATE = "INSERT INTO `demojdbc`.`users` (`firstName`, `lastName`, `username`, `email`,`dob`,`role_id`,`gender`) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(CREATE);
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2,user.getLastName());
            preparedStatement.setString(3,user.getUsername());
            preparedStatement.setString(4,user.getEmail());
            preparedStatement.setDate(5,user.getDob());
            preparedStatement.setInt(6,user.getRole().getId());
            preparedStatement.setString(7,user.getGender().toString());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void update(User user){
        String UPDATE = "INSERT INTO `demojdbc`.`users` (`firstName`, `lastName`, `username`, `email`,`dob`,`role_id`,`gender`) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE);
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2,user.getLastName());
            preparedStatement.setString(3,user.getUsername());
            preparedStatement.setString(4,user.getEmail());
            preparedStatement.setDate(5,user.getDob());
            preparedStatement.setInt(6,user.getRole().getId());
            preparedStatement.setString(7,user.getGender().toString());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void delete(int id){
        String DELETE = "UPDATE `demojdbc`.users " +
                "SET `deleted` = '1' " +
                "WHERE (`id` = ?)";
        try{
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE);
            preparedStatement.setInt(1,id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void restore(int id) {
        String RESTORE = "UPDATE `demojdbc`.users " +
                "SET `deleted` = '0' " +
                "WHERE (`id` = ?)";

        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(RESTORE);
            preparedStatement.setInt(1,id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public User findById(int id){
        String SELECT_BY_ID = "SELECT u.*, r.name role_name " +
                "FROM users u" +
                "JOIN roles r ON u.role_id = r.id " +
                "WHERE u.id = ? AND deleted = '0'";
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID);
            preparedStatement.setInt(1,id);
            var rs = preparedStatement.executeQuery();
            if(rs.next()){
                return getUserByResultSet(rs);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    private User getUserByResultSet(ResultSet rs) throws SQLException {
        var user = new User();
        user.setId(rs.getInt("id"));
        user.setFirstName(rs.getString("firstName"));
        user.setLastName(rs.getString("lastName"));
        user.setUsername(rs.getString("userName"));
        user.setEmail(rs.getString("email"));
        user.setDob(rs.getDate("dob"));
        user.setRole(new Role(rs.getString("role_name")));
        user.setGender(EGender.valueOf(rs.getString("gender")));
        return user;
    }
}

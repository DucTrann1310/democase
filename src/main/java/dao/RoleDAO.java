package dao;

import model.Role;
import service.dto.Page;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoleDAO extends DatabaseConnection{
    public Page<Role> findAll(int page, boolean isShowRestore, String search){
        var result = new Page<Role>();
        final int TOTAL_ELEMENT = 5;
        result.setCurrentPage(page);
        var content = new ArrayList<Role>();

        if(search == null){
            search = "";
        }
        search = "%" + search.toLowerCase() + "%";
        final var DELETED = isShowRestore ? 1 : 0;

        var SELECT_ALL = "SELECT * " +
                "FROM roles r " +
                "WHERE r.deleted = ? AND LOWER(r.name) LIKE ? " +
                "LIMIT ? OFFSET ?";

        var SELECT_COUNT = "SELECT count(*) cnt " +
                "FROM roles r " +
                "WHERE r.deleted = ? AND LOWER(r.name) LIKE ? ";

        try {
            Connection connection = getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL);
            preparedStatement.setInt(1,DELETED);
            preparedStatement.setString(2,search);
            preparedStatement.setInt(3,TOTAL_ELEMENT);
            preparedStatement.setInt(4,(page-1)*TOTAL_ELEMENT);
            System.out.println(preparedStatement);

            var rs = preparedStatement.executeQuery();
            while (rs.next()){
                content.add(getRoleByResultSet(rs));
            }

            result.setContent(content);

            PreparedStatement preparedStatementCount = connection.prepareStatement(SELECT_COUNT);
            preparedStatementCount.setInt(1,DELETED);
            preparedStatementCount.setString(2,search);

            var rsCount = preparedStatementCount.executeQuery();
            if(rsCount.next()){
                result.setTotalPage((int) Math.ceil((double) rsCount.getInt("cnt")/TOTAL_ELEMENT));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }
    public List<Role> getAll(){
        List<Role> result = new ArrayList<>();
        var SELECT_ALL = "SELECT * " +
                "FROM roles r " +
                "WHERE r.deleted = '0'";
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL);
            System.out.println(preparedStatement);
            var rs = preparedStatement.executeQuery();
            while (rs.next()){
                result.add(getRoleByResultSet(rs));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }


    public void create(Role role){
        String CREATE = "INSERT INTO `demojdbc`.`roles` (`name`) " +
                "VALUES (?)";
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(CREATE);
            preparedStatement.setString(1,role.getName());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void update(Role role){
        String UPDATE = "UPDATE `demojdbc`.`roles` " +
                "SET `name` = ? " +
                "WHERE `id` = ?";
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE);
            preparedStatement.setString(1,role.getName());
            preparedStatement.setInt(2,role.getId());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void delete(int id){
        String DELETE = "UPDATE `demojdbc`.roles " +
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
        String RESTORE = "UPDATE `demojdbc`.roles " +
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

    public Role findById(int id){
        String SELECT_BY_ID = "SELECT * " +
                "FROM roles r " +
                "WHERE r.id = ? AND r.deleted = '0'";
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID);
            preparedStatement.setInt(1,id);
            var rs = preparedStatement.executeQuery();
            if(rs.next()){
                return getRoleByResultSet(rs);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    private Role getRoleByResultSet(ResultSet rs) throws SQLException {
        var role = new Role();
        role.setId(rs.getInt("id"));
        role.setName(rs.getString("name"));
        return role;
    }

}

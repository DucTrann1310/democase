package service;

import dao.UserDAO;
import model.User;
import service.dto.Page;

public class UserService {
    private final UserDAO userDAO;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }
    public void create(User user){
        userDAO.create(user);
    }
    public Page<User> getUsers(int page, boolean isShowRestore, String search){
        return userDAO.findAll(page,isShowRestore,search);
    }
    public User findById(int id){
        return userDAO.findById(id);
    }
    public void update(User user){
        userDAO.update(user);
    }
    public void restore(String[] ids){
        for(var id : ids){
            userDAO.restore(Integer.parseInt(id));
        }
    }
    public void delete(String[] ids){
        for(var id : ids){
            userDAO.delete(Integer.parseInt(id));
        }
    }
}

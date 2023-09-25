package service;

import dao.RoleDAO;
import model.Role;
import service.dto.Page;

import java.util.List;

public class RoleService {
    private static List<Role> roles;
    private final RoleDAO roleDAO;

    public RoleService() {
        this.roleDAO = new RoleDAO();
    }
    public Page<Role> getRoles(int page, boolean isShowRestore, String search){
        return roleDAO.findAll(page,isShowRestore,search);
    }
    public List<Role> getAll(){
        return roleDAO.getAll();
    }
    public void create(Role role){
        roleDAO.create(role);
    }
    public void update(Role role, int id){
        role.setId(id);
        roleDAO.update(role);
    }

    public void delete(int id){
            roleDAO.delete(id);
    }
    public void restore(String[] ids){
        for(var id : ids){
            roleDAO.restore(Integer.parseInt(id));
        }
    }
    public Role findById(int id){
        return roleDAO.findById(id);
    }
}

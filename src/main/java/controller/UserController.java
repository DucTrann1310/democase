package controller;

import model.EGender;
import model.Role;
import model.User;
import service.RoleService;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;

@WebServlet(name = "userController",urlPatterns = "/user")
public class UserController extends HttpServlet {
    private RoleService roleService;
    private UserService userService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if(action == null){
            action = "";
        }
        switch (action){
            default -> showList(req,resp);
            case "create" -> showCreate(req,resp);
            case "edit" -> showEdit(req,resp);
            case "delete" -> delete(req,resp);
            case "restore" -> showRestore(req,resp);
            case "searchDeleted" -> showRestore(req,resp);
        }
    }

    private void showRestore(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        showTable(req,true,resp,"restore");
    }

    private void delete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        userService.delete(id);
        resp.sendRedirect("/user?message=Deleted");
    }

    private void showEdit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("user", userService.findById(Integer.parseInt(req.getParameter("id"))));
        req.setAttribute("roles",roleService.getAll());
        req.setAttribute("genders",EGender.values());
        req.getRequestDispatcher("user/edit.jsp").forward(req,resp);
    }

    private void showCreate(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("user", new Role());
        req.setAttribute("roles",roleService.getAll());
        req.setAttribute("genders", EGender.values());
        req.getRequestDispatcher("/user/create.jsp").forward(req,resp);
    }

    private void showList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        showTable(req,false, resp, "showList");
    }

    private void showTable(HttpServletRequest req, boolean isShowRestore, HttpServletResponse resp, String action) throws ServletException, IOException {
        String pageString = req.getParameter("page");
        if(pageString == null){
            pageString = "1";
        }

        req.setAttribute("search",req.getParameter("search"));
        req.setAttribute("page", userService.getUsers(Integer.parseInt(pageString),isShowRestore,req.getParameter("search")));
        req.setAttribute("message",req.getParameter("message"));
        req.setAttribute("isShowRestore",isShowRestore);
        if(action.equals("showList")){
            req.getRequestDispatcher("/user/index.jsp").forward(req,resp);
        }
        if(action.equals("restore")){
            req.setAttribute("page", userService.getUsers(Integer.parseInt(pageString),isShowRestore,req.getParameter("searchDeleted")));
            req.getRequestDispatcher("/user/restore.jsp").forward(req,resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) {
            action = "";
        }
        switch (action){
            case "create" -> create(req,resp);
            case "edit" -> edit(req,resp);
            case "restore" -> restore(req,resp);
        }
    }

    private void restore(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String[] restoredUserIds = req.getParameterValues("restoredUser");
        if(restoredUserIds != null && restoredUserIds.length > 0){
            for(int i = 0;i < restoredUserIds.length;i++){
                userService.restore(restoredUserIds);
            }
            resp.sendRedirect("/user?message=Restored");
        }else {
            resp.sendRedirect("/user?message=NoUsersSelected");
        }
    }

    private void edit(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        userService.update(getUserByRequest(req), Integer.parseInt(req.getParameter("id")));
        resp.sendRedirect("/user?message=Updated");
    }

    private void create(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        userService.create(getUserByRequest(req));
        resp.sendRedirect("/user?message=Created");
    }

    private User getUserByRequest(HttpServletRequest req) {
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String username = req.getParameter("username");
        String email = req.getParameter("email");
        String dob = req.getParameter("dob");
        String idrole = req.getParameter("role");
        Role role = new Role(Integer.parseInt(idrole));
        String strGender = req.getParameter("gender");
        EGender gender = EGender.valueOf(strGender);
        return  new User(firstName,lastName,username,email, Date.valueOf(dob),role,gender);
    }

    @Override
    public void init() throws ServletException {
        userService = new UserService();
        roleService = new RoleService();
    }
}

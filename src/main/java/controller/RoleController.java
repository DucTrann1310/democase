package controller;

import dao.RoleDAO;
import model.Book;
import model.Role;
import service.RoleService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "roleController",urlPatterns = "/role")
public class RoleController extends HttpServlet {
    private RoleService roleService;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if(action == null){
            action = "";
        }
        switch (action){
            default -> showList(req,resp);
            case "delete" -> delete(req,resp);
            case "restore" -> showRestore(req,resp);
            case "search" -> showList(req,resp);
            case "searchDeleted" ->showRestore(req,resp);
            case "create" -> showCreate(req,resp);
        }
    }

    private void showCreate(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("role", new Role());
        req.getRequestDispatcher("/role/create.jsp").forward(req,resp);
    }


    private void showRestore(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        showTable(req,true,resp,"restore");
    }

    private void delete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        roleService.delete(id);
        resp.sendRedirect("/role?message=Deleted");
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
        req.setAttribute("page", roleService.getRoles(Integer.parseInt(pageString),isShowRestore,req.getParameter("search")));
        req.setAttribute("message",req.getParameter("message"));
        req.setAttribute("isShowRestore",isShowRestore);
        if(action.equals("showList")){
            req.getRequestDispatcher("/role/index.jsp").forward(req,resp);
        }
        if(action.equals("restore")){
            req.setAttribute("page", roleService.getRoles(Integer.parseInt(pageString),isShowRestore,req.getParameter("searchDeleted")));
            req.getRequestDispatcher("/role/restore.jsp").forward(req,resp);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) {
            action = "";
        }
        switch (action){
            case "restore" -> restore(req,resp);
            case "create" -> create(req,resp);
        }
    }

    private void create(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        roleService.create(getRoleByRequest(req));
        resp.sendRedirect("/role?message=Created");
    }

    private Role getRoleByRequest(HttpServletRequest req) {
        String name = req.getParameter("name");
        return new Role(name);
    }

    private void restore(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String[] restoredBooksIds = req.getParameterValues("restoredRole");
        if(restoredBooksIds != null && restoredBooksIds.length > 0){
            for(int i = 0;i < restoredBooksIds.length;i++){
                roleService.restore(restoredBooksIds);
            }
            resp.sendRedirect("/role?message=Restored");
        }else {
            resp.sendRedirect("/role?message=NoProductsSelected");
        }
    }


    @Override
    public void init() throws ServletException {
        roleService = new RoleService();
    }
}

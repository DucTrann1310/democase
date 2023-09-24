package controller;

import model.EGender;
import model.Students;
import service.StudentService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "studentController", value = "/student")
public class StudentController extends HttpServlet {
    private StudentService studentService;

    @Override
    public void init() throws ServletException {
        studentService = new StudentService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action"); // localhost:8080/student?action
        if (action == null) {
            action = "";
        }
        switch (action) {
            case "create":
                showCreate(req, resp);
                break;
            case "edit":
                showEdit(req, resp);
                break;
            case "delete":
                delete(req,resp);
                break;
            case "showRestore":
                showRestore(req,resp);
                break;
            case "restore":
                restore(req,resp);
                break;
            default:
                showList(req, resp);
        }
    }

    private void restore(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        studentService.restoreStudent(Integer.parseInt(req.getParameter("id")));
        resp.sendRedirect("/student?action=showRestore&message=Restored");
    }

    private void showRestore(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("students", studentService.getStudents(true));
        req.setAttribute("message",req.getParameter("message"));
        req.getRequestDispatcher("restore.jsp").forward(req,resp);
    }

    private void showEdit(HttpServletRequest req, HttpServletResponse resp) {
        int id = Integer.parseInt(req.getParameter("id"));
        Students student = studentService.findById(id);
        RequestDispatcher dispatcher;
        if (student == null) {
            dispatcher = req.getRequestDispatcher("error-404.jsp");
        } else {
            req.setAttribute("genders", EGender.values());
            req.setAttribute("student", student);
            dispatcher = req.getRequestDispatcher("edit.jsp");
            //
        }
        try {
            dispatcher.forward(req, resp);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showCreate(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("genders", EGender.values());
        req.getRequestDispatcher("create.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) {
            action = "";
        }
        switch (action) {
            case "create":
                create(req, resp);
                break;
            case "edit":
                update(req, resp);
                break;
            case "restore":
                restore(req,resp);
//            case "delete":
//                delete(req,resp);
//                break;

        }
    }


    private void delete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        studentService.removeStudent(id);
        resp.sendRedirect("/student?message=Deleted");
    }

    private void update(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        int id = Integer.parseInt(req.getParameter("id"));
        String name = req.getParameter("name");
        String dob = req.getParameter("dob");
        String gender = req.getParameter("gender");
        Students student = studentService.findById(id);

        if (checkEmpty(name,dob)){
            req.setAttribute("student", student);
            req.setAttribute("genders", EGender.values());
            req.setAttribute("message","Fields cannot be empty");
            req.getRequestDispatcher("edit.jsp").forward(req,resp);
        } else {
            studentService.updateStudent(student.getId(), name, dob, gender);
            resp.sendRedirect("/student?message=Updated");
        }
}
    private void create(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String name = req.getParameter("name");
        String dob = req.getParameter("dob");
        String gender = req.getParameter("gender");

        if (checkEmpty(name,dob)) {
            req.setAttribute("genders", EGender.values());
            req.setAttribute("message","Fields cannot be empty");
            req.getRequestDispatcher("create.jsp").forward(req, resp);
        } else {
            studentService.addStudent(name, dob, gender);
            resp.sendRedirect("/student?message=Created");
        }
    }
    private void showList(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException{
        req.setAttribute("students", studentService.getStudents(false));
        req.setAttribute("message",req.getParameter("message"));
        req.getRequestDispatcher("student.jsp").forward(req,resp);
    }
    private boolean checkEmpty(String name, String dob){
        if(name.isEmpty() || name == null || name.trim().isEmpty() || dob.isEmpty()){
            return true;
        }
        return false;
    }
}

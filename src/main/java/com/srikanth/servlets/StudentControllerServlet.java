package com.srikanth.servlets;

import com.srikanth.database.StudentDbUtil;
import com.srikanth.pojo.Student;
import jakarta.annotation.Resource;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/studentControllerServlet")
public class StudentControllerServlet extends HttpServlet {
    @Resource(name = "jdbc/student")
    private DataSource dataSource;

    private StudentDbUtil studentDb;

    @Override
    public void init() throws ServletException {
        super.init();
        try{
            studentDb = new StudentDbUtil(dataSource);
        }catch (Exception e){
            throw new ServletException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String command = req.getParameter("command");

        if(command==null){
            command="LIST";
        }

        switch (command) {
            case "ADD" :
                        addStudent(req, resp);
                        break;
            case "LOAD" :
                        loadStudent(req,resp);
                        break;
            case "UPDATE" :
                        updateStudent(req,resp);
                        break;
            case "DELETE":
                    try {
                        deleteStudent(req, resp);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                break;
            default:
                    listStudents(req, resp);
        }

    }
    private void deleteStudent(HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        // read student id from form data
        String theStudentId = request.getParameter("studentId");

        // delete student from database
        studentDb.deleteStudent(theStudentId);

        // send them back to "list students" page
        listStudents(request, response);
    }
    private void updateStudent(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("studentId"));
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String email = req.getParameter("email");

        Student student = new Student(firstName,lastName,email,id);
        System.out.println(student);
        try{
            studentDb.updateStudent(student);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        listStudents(req, resp);
    }

    private void loadStudent(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("studentId");
        Student student = null;
        try{
             student = studentDb.getStudent(id);
            System.out.println("load student " + student);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        req.setAttribute("THE_STUDENT", student);

        RequestDispatcher rd = req.getRequestDispatcher("/student-update-form.jsp");
        rd.forward(req,resp);
    }

    private void addStudent(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        read student from form data`
        String firstName= req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String email = req.getParameter("email");
//        create new student object
        Student newStudent = new Student(firstName,lastName,email);
//        add the student  to the database
        try{
            studentDb.addStudent(newStudent);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

//        send back to main page
        listStudents(req, resp);
    }

    private void listStudents(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Student> studentList = studentDb.getStudents();
        req.setAttribute("Students", studentList);

        RequestDispatcher rd = req.getRequestDispatcher("/list-students.jsp");
        rd.forward(req, resp);
    }
}

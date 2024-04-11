package com.example.database;

import com.example.pojo.Student;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDbUtil {
    private DataSource dataSource;

    public StudentDbUtil(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    public List<Student> getStudents(){
        List<Student> students = new ArrayList<>();
        Connection con = null;
        Statement st = null;
        ResultSet rs = null;

        try {
            con = dataSource.getConnection();
            String query = "select * from student_info order by secondName";
            st=con.createStatement();
            rs= st.executeQuery(query);
            while (rs.next()){
                int id = rs.getInt("id");
                String  firstName = rs.getString("firstName");
                String secondName = rs.getString("secondName");
                String email = rs.getString("email");

                Student student = new Student(firstName,secondName,email,id);
                students.add(student);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            try{
                if(con != null ){
                    con.close();
                }
                if(st != null ){
                    st.close();
                }
                if(rs != null){
                    rs.close();
                }


            } catch (SQLException e) {
                System.out.println("unable  to close the connection!");
            }
        }
        return students;

    }
    public void addStudent(Student student) throws SQLException {

        PreparedStatement ps = null;
        try (Connection con = dataSource.getConnection()) {
            String query = "insert into student_info(firstName,secondName,email) values(?,?,?)";
            ps = con.prepareCall(query);
            ps.setString(1,student.getFirstName());
            ps.setString(2,student.getLastName());
            ps.setString(3,student.getEmail());

            ps.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            if(ps != null){
                ps.close();
            }
        }
    }

    public Student getStudent(String id) throws SQLException {
        Student student = null;
        PreparedStatement st = null;
        int studentId;
        ResultSet rs = null;
        try(Connection con = dataSource.getConnection()) {
            studentId = Integer.parseInt(id);
            String query = "select * from student_info where id = ? ";
            st = con.prepareStatement(query);
            System.out.println("Student_id at db getStudent is : " + studentId);
            // set params
            st.setInt(1, studentId);

            // execute statement
            rs = st.executeQuery();
            while(rs.next()){
                String fName= rs.getString("firstName");
                String lName = rs.getString("secondName");
                String email = rs.getString("email");
                student = new Student(fName,lName,email,studentId);
                System.out.println("get_student "+student);
            }
            System.out.println("working just fine!");
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            if(st != null ){
                st.close();
            }
            if(rs != null){
                rs.close();
            }
        }
        return student;
    }

    public void updateStudent(Student student) throws SQLException {
        PreparedStatement st = null;
        try(Connection con = dataSource.getConnection()){
            String query = "update student_info set firstName= ? , secondName = ?, email= ? where id = ? ";
            st = con.prepareStatement(query);
            st.setString(1, student.getFirstName());
            st.setString(2, student.getLastName());
            st.setString(3, student.getEmail());
            st.setInt(4, student.getId());
            System.out.println("query : "+st.toString());
            st.execute();
            System.out.println("performed update!!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            if(st != null){
                st.close();
            }
        }
    }
    public void deleteStudent(String theStudentId) throws Exception {

        Connection myConn = null;
        PreparedStatement myStmt = null;

        try {
            // convert student id to int
            int studentId = Integer.parseInt(theStudentId);

            // get connection to database
            myConn = dataSource.getConnection();

            // create sql to delete student
            String sql = "delete from student_info where id=?";

            // prepare statement
            myStmt = myConn.prepareStatement(sql);

            // set params
            myStmt.setInt(1, studentId);

            // execute sql statement
            myStmt.execute();
        }
        finally {
            // clean up JDBC code
            try{
                if(myConn != null ){
                    myConn.close();
                }
                if(myStmt != null ){
                    myStmt.close();
                }



            } catch (SQLException e) {
                System.out.println("unable  to close the connection!");
            }
        }
    }
}

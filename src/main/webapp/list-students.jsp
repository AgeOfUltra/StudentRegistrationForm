<%@page import="com.srikanth.pojo.Student"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ page import="java.util.*" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Student Tracker App</title>
    <link type="text/css" rel="stylesheet" href="css/style.css">
</head>
<body>

    <%
        List<Student> studentsList = (List<Student>)request.getAttribute("Students");

    %>

    <div id="wrapper">
        <div id="header">
            <h2>TonyStark University</h2>
        </div>
    </div>

    <div id="container">
        <div id="content">
            <input class="add-student-button" type="button" value ="add student"
            onclick="window.location.href='add-studentForm.jsp';return false"/>
            <table>
                <tr>
                    <th>First Name</th>
                    <th>Second Name</th>
                    <th>Email</th>
                    <th>Action</th>
                </tr>
                <c:forEach var="temp" items="<%= studentsList %>">
                    <c:url var="tempLink" value="studentControllerServlet">
                        <c:param name="command" value="LOAD"/>
                        <c:param name="studentId" value="${temp.getId()}"/>

                    </c:url>

                    <c:url var="tempLinkD" value="studentControllerServlet">
                        <c:param name="command" value="DELETE"/>
                        <c:param name="studentId" value="${temp.getId()}"/>

                    </c:url>
                    <tr>
                        <td>${temp.firstName}</td>
                        <td>${temp.lastName}</td>
                        <td>${temp.email}</td>
                        <!-- The links should be dynamic so we are using the jstl link tag -->
                        <td> <a href="${tempLink}">Update</a>
                         |
                         <a href="${tempLinkD}" onclick="if( !(confirm('Are you sure want to delete this student ?'))) return false">
                         Delete</a>
                         </td>
                    </tr>
                </c:forEach>
                <!-- <% for (Student temp : studentsList) { %>
                    <tr>
                        <td> <%= temp.getFirstName() %></td>
                        <td> <%= temp.getLastName() %></td>
                        <td> <%= temp.getEmail() %></td>
                        <td> <a href="${tempLink}">Update</a> </td>
                    </tr>
                <% } %> -->
            </table>
        </div>
    </div>

</body>
</html>
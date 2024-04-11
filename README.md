<h3>Description</h3>
<p>This project Learned from <a href="https://luv2code.com/">Luv2Code</a> </p>
<p>The Objective of this project is to perform a CRUD operation using jsp and servlets and handling  pool connection</p>

<hr>
<h3>Set up</h3>
<ul>
  <li>I used  intellij Idea for this small project! You can use eclips or any other IDE</li>
  <li>Create a Maven Webapp Archetype </li>
  <li>Setup tomcat in intellij </li>
  <li>Used MySql for database</li>
</ul>
<p>Now You can download this repo and test it!.</p>
<hr>
<p>Copy paste the sql commands and run in the mysql</p>
<div>
create database student;
  <br>
create table student_info(id int not null auto_increment, firstName varchar(60) default null, secondName varchar(60) default null,email  varchar(100) default null,PRIMARY KEY (id));
  <br>
insert into student_info values(1,"Zoro","Roronova","zoro@gmail.com"),(2,"luffy","monkey.d","meat@gmail.com");

</div>
<p>In the folder <a href="src/main/webapp/META-INF">webapp/META-INF/</a> we have created a context.xml file where we have configured the pool connection for data connection for multiple request</p>
<p>Using DataSource interface to connect the connection pool</p>
<p>Performing the queries in the way we do in JDBC</p>
<p>To forward the generated data in the servlet to jsp I have used Request Dispatcher.</p>
<p>Instead of request Dispatcher we can also use Sessions, Cookies </p>



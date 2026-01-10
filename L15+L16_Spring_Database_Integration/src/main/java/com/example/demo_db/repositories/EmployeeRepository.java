package com.example.demo_db.repositories;

import com.example.demo_db.dtos.CreateEmployeeRequest;
import com.example.demo_db.models.Employee;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class EmployeeRepository {

//    @Value("${employee_db.url}")
//    private String url;
//
//    @Value("${employee_db.user}")
//    private String username;
//
//    @Value("${employee_db.password}")
//    private String password;
//
//    EmployeeRepository() throws SQLException {
//        createEmployeeSQLTable();
//    }

    // --------------------------------------------------------------------------

    private String url;
    private String username;
    private String password;

    private Connection connection = null;

    EmployeeRepository(@Value("${employee_db.url}") String url,
                       @Value("${employee_db.user}") String username,
                       @Value("${employee_db.password}") String password) throws SQLException {
        this.url = url;
        this.username = username;
        this.password = password;
        createEmployeeSQLTable();
    }

    private Connection getConnection() throws SQLException {
        if(connection == null) {
            connection = DriverManager.getConnection(url, username, password);
        }

        return connection;
    }

//
    // POJO : Plain old java object

    /**
     * There are 2 types of sql statements that you can write in Java language
     * 1. Normal statement : static query i.e will execute whatever is present in the query
     * 2. Prepared statement: dynamic query i.e
     *
     * Whenever you run a SQL query on a database server:
     * 1. Query compilation :
     * 2. Query execution
     *
     * Generally SQL DB servers cache the query for certain amount of time, which discards the need for compilation
     *
     * insert into employees(firstName, lastName, age, email) VALUES ('Piyush', 'Aggarwal', 10, 'abc@google.com')
     * insert into employees(firstName, lastName, age, email) VALUES ('Deepak', 'Kumar', 20, 'deepak@google.com')
     *
     * insert into employees(firstName, lastName, age, email) VALUES (?, ?, ?, ?)
     * PHV : [Piyush, Aggarwal, 10, abc@google.com]
     * insert into employees(firstName, lastName, age, email) VALUES (?, ?, ?, ?)
     * PHV : ['Deepak', 'Kumar', 20, 'deepak@google.com']
     *
     *
     *
     */

    /** 1, 2, 3, 10, 7
     *
     * Option A: 4 First Missing positive number
     * Option B: 6 Total records so far + 1
     * Option C: 11 Largest id inserted so far + 1
     * Option D: 8 Last id inserted + 1
     * Option E: error
     */

    /**
     * Insert query - converting the data from java object --> sql table
     * select queries - retrieving the data from sql table --> java object

     * Object relation Mapping - ORM
     * Hibernate, OpenJPA, EclipseLink - These tools help the users do the object relation mapping instead of the users doing it themselves
     * Advantages: 1. We just need to add properties in java object (model class), and these tools internally map them to the sql column
     *             2. Similarly, any new model / entity if specified in the app server will also be added as a table in sql database
     *             3. Queries will always be optimized
     *
     * @param createEmployeeRequest
     * @throws SQLException
     */

    /**
     * update employees set firstName = ?, lastName = ? where id = ?

     * delete from employees where id = ?
     *
     *
     * @param createEmployeeRequest
     * @throws SQLException
     */

    public void createEmployee(CreateEmployeeRequest createEmployeeRequest) throws SQLException {

        // insert into employee(...)values(...)

        String sql = "INSERT INTO employees(firstName, lastName, age, email) VALUES (?, ?, ?, ?)";
        Connection connection = getConnection();

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, createEmployeeRequest.getFirstName());
        preparedStatement.setString(2, createEmployeeRequest.getLastName());
        preparedStatement.setInt(3, createEmployeeRequest.getAge());
        preparedStatement.setString(4, createEmployeeRequest.getEmail());

        int result = preparedStatement.executeUpdate();
        System.out.println("Number of rows modified: " + result);
//        Statement statement = connection.createStatement();


//        statement.execute(sql); // boolean
//        statement.executeUpdate(sql); // int : Check the number of modified rows
//        statement.executeQuery(sql); // ResultSet : Select

    }

    public Employee getEmployeeById(Integer employeeId) throws SQLException {
        String sql = "SELECT * FROM employees WHERE id = ?";
        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, employeeId);

        ResultSet resultSet = preparedStatement.executeQuery();
        Employee result = null;
        while(resultSet.next()){
            Integer age = resultSet.getInt("age");
            String firstName = resultSet.getString("firstName");
            String lastName = resultSet.getString("lastName");
            String email = resultSet.getString("email");
            Integer id = resultSet.getInt("id");

            result = new Employee();
            result.setFirstName(firstName);
            result.setLastName(lastName);
            result.setAge(age);
            result.setEmail(email);
            result.setId(id);

        }

        return result;
    }

    public List<Employee> getAllEmployees() throws SQLException {

        String sql = "SELECT * FROM employees";
        Connection connection = this.getConnection();

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        List<Employee> employeeList = new ArrayList<>();

        while(resultSet.next()){
            Integer age = resultSet.getInt("age");
            String firstName = resultSet.getString("firstName");
            String lastName = resultSet.getString("lastName");

            String email = resultSet.getString("email");
//            Integer id = resultSet.getInt("id");
            Integer id = resultSet.getInt(1);

            Employee employee = new Employee();
            employee.setFirstName(firstName);
            employee.setLastName(lastName);
            employee.setAge(age);
            employee.setEmail(email);
            employee.setId(id);

            employeeList.add(employee);
        }

        return employeeList;
    }

    // select * from employees where id = 1;
    // 1. when you want to get the details of the employee : executeQuery()
    // 2. when you want to just know if the employee is present in the table or not: true / false

    public void createEmployeeSQLTable() throws SQLException {
        System.out.println("Creating employee table... url - " + this.url + ", user - " + this.username + ", password - " + this.password);
        Connection connection = DriverManager.getConnection(this.url, this.username, this.password);

        Statement statement = connection.createStatement();
        statement.execute("CREATE TABLE if not exists employees(id int primary key auto_increment, firstName varchar(255), lastName varchar(255), age int, email varchar(255))");

//        System.out.println("Table created: " + result);

    }
}

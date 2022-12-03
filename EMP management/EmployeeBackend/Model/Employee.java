package com.EmployeeManagement.EmployeeBackend.Model;



import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

// @Document (Name_of_collection) : indicate this class is a candidate for mapping to the database.
@Document(collection = "Employee")
public class Employee {

    @Transient //  by default all private fields are mapped to the document, this annotation excludes this field where it is applied from being stored in the database
    public static final String SEQUENCE_NAME = "users_sequence";

    @Id
    private long id;

   // @NotBlank
 //   @Size(max = 100)
   // @Indexed(unique = true) // used to create unique indexes for this field
    //@NotNull
    private String firstName;
    private String lastName;
   

    @NotBlank
  //  @Size(max = 100)
    //@Indexed(unique = true)
   // @NotNull
    private String email;
    
    private int salary;
    
    private String job;

    public Employee() {

    }

    public Employee(String firstName, String lastName, String email,int salary,String job) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.salary=salary;
        this.job=job;
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public int getSalary() {
    	return salary;
    }
    public void setSalary(int s) {
    	this.salary=s;
    }
    public String getJob() {
    	return job;
    }
    public void setJob(String job) {
    	this.job=job;
    }

    @Override
    public String toString() {
        return "Employee [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email +", job=" + job +
                "]";
    }
}
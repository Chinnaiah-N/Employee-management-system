package com.EmployeeManagement.EmployeeBackend.Controller;


import java.io.IOException;
//import java.net.BindException;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.EmployeeManagement.EmployeeBackend.Exception.ResourceNotFoundException;
import com.EmployeeManagement.EmployeeBackend.Model.Employee;
import com.EmployeeManagement.EmployeeBackend.Model.EmployeeExcelExporter;
import com.EmployeeManagement.EmployeeBackend.Model.EmployeePDFExporter;
import com.EmployeeManagement.EmployeeBackend.Repository.EmployeeRepository;
import com.lowagie.text.DocumentException;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin("*") //To enable CORS on the server
@RestController
@RequestMapping("/api/employee")
public class EmployeeController {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;

    @GetMapping("/getAll")
    public List <Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }


    @GetMapping("/getEmployeeByID/{id}")
    public ResponseEntity < Employee > getEmployeeById(@PathVariable(value="id") Long id) throws ResourceNotFoundException
    {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id = " + id));
        return ResponseEntity.ok().body(employee);
    }

    @PostMapping("/addEmployee")
    public Employee addEmployee(@Valid @RequestBody Employee employee)
    {
        employee.setId(sequenceGeneratorService.generateSequence(Employee.SEQUENCE_NAME));
        return employeeRepository.save(employee);
        
    }

    @PutMapping("/updateEmployee/{id}")
    public ResponseEntity < Employee > updateEmployee(@PathVariable(value = "id") Long id,
                                                      @Valid @RequestBody Employee employeeDetails) throws ResourceNotFoundException {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + id));

        employee.setEmail(employeeDetails.getEmail());
        employee.setLastName(employeeDetails.getLastName());
        employee.setFirstName(employeeDetails.getFirstName());
        employee.setSalary(employeeDetails.getSalary());
        employee.setJob(employeeDetails.getJob());
        final Employee updatedEmployee = employeeRepository.save(employee);
        return ResponseEntity.ok(updatedEmployee);
    }

    @DeleteMapping("/deleteEmployeeById/{id}")
    public Map < String, Boolean >  deleteEmployee(@PathVariable(value="id") Long id)  throws ResourceNotFoundException
    {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + id));
        employeeRepository.delete(employee);
        Map<String,Boolean> response = new HashMap <> ();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

    @GetMapping("/export/{req}")
    public void exportToPDF(@PathVariable int req,HttpServletResponse response) throws DocumentException, IOException {
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
        
        List<Employee> listEmployees = employeeRepository.findAll(); 
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
       
        if(req==1) {
        	String headerValue = "attachment; filename=users_" + currentDateTime + ".pdf";
        	response.setHeader(headerKey, headerValue);
            
        	 EmployeePDFExporter exporter = new EmployeePDFExporter(listEmployees);
             exporter.export(response);
        }
        else {
        	String headerValue = "attachment; filename=users_" + currentDateTime + ".xlsx";
            response.setHeader(headerKey, headerValue);
            
            EmployeeExcelExporter Excelexporter = new EmployeeExcelExporter(listEmployees);
            Excelexporter.export(response);
        }
         
    }
   /* @GetMapping("/employee/export/excel")
    public void exportToExcel(HttpServletResponse response) throws DocumentException, IOException {
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
         
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);
         
        List<Employee> listEmployees = employeeRepository.findAll();
         
        EmployeeExcelExporter Excelexporter = new EmployeeExcelExporter(listEmployees);
        Excelexporter.export(response);
         
    }*/


}
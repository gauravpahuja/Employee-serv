package com.paypal.bfs.test.employeeserv;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import javax.annotation.PostConstruct;

import com.paypal.bfs.test.employeeserv.exception.ErrorMessage;
import com.paypal.bfs.test.employeeserv.models.Address;
import com.paypal.bfs.test.employeeserv.models.Employee;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = EmployeeservApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class EmployeeServiceTest {

    private final String BASE_URL = "/v1/bfs/employees";

    @LocalServerPort
    private int port;

    protected String EMP_URL = null;

    @Autowired
    protected TestRestTemplate testRestTemplate;

    @PostConstruct
    public void init() {
        EMP_URL = "http://localhost:" + port + BASE_URL;
    }

    @Test
    public void createEmployeeTest() {
        Employee emp = createEmployeeObject("ashoka", "gupta", "delhi", "244 pitampura", "delhi", "110085", "india");

        ResponseEntity<Employee> response = this.testRestTemplate.exchange(EMP_URL, HttpMethod.POST,
                new HttpEntity<>(emp, getHeaders()), Employee.class);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }


    @Test
    public void badRequestTestWithEmptyRequestBody() {
        Employee emp = null;

        ResponseEntity<ErrorMessage> response = this.testRestTemplate.exchange(EMP_URL, HttpMethod.POST,
                new HttpEntity<>(emp, getHeaders()), ErrorMessage.class);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }


    @Test
    public void badRequestTestWithInvalidRequestBody() {
        Employee emp = createEmployeeObject(null, null, null, null, null, null, null);

        ResponseEntity<ErrorMessage> response = this.testRestTemplate.exchange(EMP_URL, HttpMethod.POST,
                new HttpEntity<>(emp, getHeaders()), ErrorMessage.class);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertEquals(response.getBody().getErrors().size(), 7);
    }

    @Test
    public void getEmployeeTest() {
        Employee employee = createEmployeeObject("Gaurav", "pahuja", "mohali", "234 avantika", "delhi", "110085", "india");

        ResponseEntity<Employee> createdResponse = this.testRestTemplate.exchange(EMP_URL, HttpMethod.POST,
                new HttpEntity<>(employee, getHeaders()), Employee.class);
        assertThat(createdResponse).isNotNull();
        assertThat(createdResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        ResponseEntity<Employee> response = this.testRestTemplate.exchange(EMP_URL + "/" + createdResponse.getBody().getId(), HttpMethod.GET,
                new HttpEntity<>(getHeaders()), Employee.class);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        assertEquals(response.getBody().getFirstName(), "Gaurav");
        assertEquals(response.getBody().getLastName(), "pahuja");
        assertThat(response.getBody().getDateOfBirth()).isNotNull();

        assertThat(response.getBody().getAddress()).isNotNull();

        Address addressResponse= (Address) response.getBody().getAddress();
        assertEquals(addressResponse.getCity(), "mohali");
        assertEquals(addressResponse.getLine1(), "234 avantika");
        assertEquals(addressResponse.getState(), "delhi");
        assertEquals(addressResponse.getZipCode(), "110085");
        assertEquals(addressResponse.getCountry(), "india");
    }

    @Test
    public void employeeNotFoundTest() {

        Employee employee = createEmployeeObject("ashok", "gupta", "delhi", "242 pitampura", "delhi", "110034", "india");

        ResponseEntity<Employee> createdResponse = this.testRestTemplate.exchange(EMP_URL, HttpMethod.POST,
                new HttpEntity<>(employee, getHeaders()), Employee.class);
        assertThat(createdResponse).isNotNull();
        assertThat(createdResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);


        Integer id = 100;
        ResponseEntity<ErrorMessage> response = this.testRestTemplate.exchange(EMP_URL + "/" + id, HttpMethod.GET,
                new HttpEntity<>(getHeaders()), ErrorMessage.class);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertEquals(response.getBody().getErrors().get(0).getUserMessage(), "Employee details not found for the given id: 100");
    }


    protected HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Accept", "application/json");
        return headers;
    }

    private Employee createEmployeeObject(String firstName, String lastName, String city, String line1, String state, String zipCode, String country) {
        Employee emp = new Employee();

        emp.setFirstName(firstName);
        emp.setLastName(lastName);

       // emp.setDateOfBirth(new Date());

        Address address = new Address();
        address.setCity(city);
        address.setLine1(line1);
        address.setState(state);
        address.setZipCode(zipCode);
        address.setCountry(country);

        emp.setAddress(address);
        return emp;
    }

}

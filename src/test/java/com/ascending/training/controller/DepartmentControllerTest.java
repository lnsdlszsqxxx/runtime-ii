package com.ascending.training.controller;

import com.ascending.training.init.AppInitializer;
import com.ascending.training.model.Department;
import com.ascending.training.service.DepartmentService;
import com.ascending.training.service.StudentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppInitializer.class)
@AutoConfigureMockMvc
public class DepartmentControllerTest {

    @Autowired
    private MockMvc mockMvc; //it needs @AutoConfigureMockMvc


    @MockBean
    private DepartmentService departmentService;

    Department mockDepartment = new Department(110L, "AOEST","atmospheric oceanic and earth sciences test","research hall");

    String exampleDepartment = "{\"id\":110,\"name\":\"AOEST\",\"description\":\"atmospheric oceanic and earth sciences test\",\"location\":\"research hall\"}";

    @Test
    public void getDepartmentByNameTest() throws Exception {

        Mockito.when(
                departmentService.getDepartmentByName(Mockito.anyString())).thenReturn(mockDepartment);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                "http://localhost:8080/departments/AOEST").accept(
                MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        String expected = exampleDepartment;

        System.out.println(result.getResponse().getContentAsString());
        System.out.println(expected);

        //strict=false means that not all the elements has to compare.
        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    }


    @Test
    public void creatDepartmentTest() throws Exception {

        // studentService.addCourse to respond back with mockCourse
        Mockito.when(
                departmentService.save(Mockito.any(Department.class))).thenReturn(true);


        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("http://localhost:8080/departments")
                .accept(MediaType.APPLICATION_JSON).content(exampleDepartment)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("The department was created.",response.getContentAsString());

    }

}

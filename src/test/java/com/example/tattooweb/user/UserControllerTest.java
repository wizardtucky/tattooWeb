package com.example.tattooweb.user;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import java.time.LocalDate;
import java.time.Month;

import static org.mockito.Mockito.verify;


@ContextConfiguration(classes = {UserController.class, UserService.class})
@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
class UserControllerTest {
    @Autowired
    private UserController userController;

    @MockBean
    private UserRepository userRepository;
    @Autowired
    private UserService underTest;
    @Autowired
    private MockMvc mockMvc; // api requests
    private static long savedUserId;

    @BeforeEach
    void setUp() {
        underTest = new UserService(userRepository);
    }

    @Test
    @Order(1)
    public void saveUserTest() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        final String link = "/api/user"; // link to your controller api end point
        UserCreateDto user = new UserCreateDto("Alexa", "alexa.jamal@gmail.com", LocalDate.of(2012, Month.APRIL, 13)); // define your default object here
        /***
         *  note: in your User class you might have id as auto generated, because of this you need two constructor one with id, and other without id
         * , or you will have an error.
         *  you can use objectmapper if you run to an error and just pass the object as json string.
         */

        // here you are performing save request and getting the result
        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders
                        .post(link)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(user))) // this will make the object a json string
                .andExpect(MockMvcResultMatchers.status().isCreated()) // here is what important! it will check the response status, if created 201 the test will success, else it will fail
                .andReturn(); // returning the result
        String response = result.getResponse().getContentAsString();
        System.out.println("from response: " + response); // this is the response you passed on your controller method
        /**
         * knowing your response structure search for the user id
         */
        JsonNode root = objectMapper.readTree(response);
        JsonNode id = root.get("id");
        savedUserId = id.asLong(); //here you save the id in static field savedUserId, then you can pass this id on the next test (delete/update);
        System.out.println(underTest.getUsers());
        System.out.println("id is: " + id.asLong());
    }

    @Test
    @Order(2)
    void shouldDeleteUser() throws IllegalStateException, Exception {
        // here delete the object, and assert the response state is ok, no need to return any value
        final String link = "/api/user/" + savedUserId;
        this.mockMvc.perform(MockMvcRequestBuilders
                        .delete(link)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()); // fail if the response is not 200, couldn't delete the object
        // when
        underTest.deleteUserById(savedUserId);
        //then
        verify(userRepository).deleteById(savedUserId);
    }

    @Test
    public void saveUserTestThrowResponseStatusException() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        final String link = "/api/user"; // link to your controller api end point
        User user = new User(1L, "", null, 10, LocalDate.of(2012, Month.APRIL, 13)); // define your default object here

        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders
                        .post(link)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(user))) // this will make the object a json string
                .andExpect(MockMvcResultMatchers.status().isBadRequest()) // here is what important! it will check the response status, if created 201 the test will success, else it will fail
                .andReturn(); // returning the result
        String response = result.getResponse().getContentAsString();
        System.out.println("from response: " + response); // this is the response you passed on your controller method
        /**
         * knowing your response structure search for the user id
         */
        JsonNode root = objectMapper.readTree(response);
        System.out.println(underTest.getUsers());
    }

    @Test
    void testGetUsers() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }


}


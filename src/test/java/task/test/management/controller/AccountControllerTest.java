package task.test.management.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import task.test.management.entity.UserInfo;
import task.test.management.entity.UserRole;
import task.test.management.service.UserInfoService;


import java.math.BigDecimal;
import java.util.Collections;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AccountController.class)
@DisplayName("Модульные тесты AccountController")
class AccountControllerTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private UserInfoService service;

    @Test
    void getAccountPage_withRoleUser_returnsStatusOkAndCorrectModelAndCorrectView() throws Exception{
        //given
        var user = new UserInfo("user", "user", UserRole.USER);

        //when
        Mockito.when(service.getUserBalance("user")).thenReturn(new BigDecimal(0));
        var result = mvc.perform(MockMvcRequestBuilders.get("/").with(user(user)));

        //then
        result.andExpect(status().isOk())
                .andExpect(model().attributeExists("username", "balance"))
                .andExpect(view().name("userPage.html"));
    }

    @Test
    void getAccountPage_withRoleAdmin_returnsStatusOkAndCorrectModelAndCorrectView() throws Exception{
        //given
        var user = new UserInfo("admin", "admin", UserRole.ADMIN);

        //when
        Mockito.when(service.getAllUsers()).thenReturn(Collections.emptyList());
        var result = mvc.perform(MockMvcRequestBuilders.get("/").with(user(user)));

        //then
        result.andExpect(status().isOk())
                .andExpect(model().attributeExists("username", "allUsers"))
                .andExpect(view().name("adminPage.html"));
    }

    @Test
    @WithAnonymousUser
    void getLoginPage_returnsStatusOkAndCorrectView() throws Exception{
        //given

        //when
        var result = mvc.perform(MockMvcRequestBuilders.get("/login"));

        //then
        result.andExpect(status().isOk())
                .andExpect(view().name("login.html"));
    }
}
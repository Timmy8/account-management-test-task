package task.test.management.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import task.test.management.entity.UserInfo;
import task.test.management.entity.UserRole;
import task.test.management.service.UserInfoService;

import java.math.BigDecimal;

import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(UserController.class)
@DisplayName("Модульные тесты UserControllerTest")
class UserControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserInfoService service;

    @Test
    @WithMockUser(roles = "USER")
    void makeDeposit_withUserRoleAndCorrectAmount_shouldMakeChangesAndRedirect() throws Exception{
        //given
        var user = new UserInfo("user", "user", UserRole.USER);

        //when
        var result = mvc.perform(MockMvcRequestBuilders
                .post("/user/deposit")
                .param("amount", "100")
                .with(user(user)));

        //then
        result.andExpect(status().isFound())
                .andExpect(view().name("redirect:/"));

        verify(service).depositMoney("user", new BigDecimal(100));
    }

    @Test
    @WithMockUser(roles = "USER")
    void makeDeposit_withUserRoleAndIncorrectAmount_shouldThrowRuntimeExceptionAndHandleItAndReturnCorrectView() throws Exception{
        //given
        var user = new UserInfo("user", "user", UserRole.USER);

        //when
        var result = mvc.perform(MockMvcRequestBuilders
                .post("/user/deposit")
                .param("amount", "hundred")
                .with(user(user)));

        //then
        result.andExpect(status().isBadRequest())
                .andExpect(view().name("errorPage.html"));
    }

    @Test
    @WithMockUser(roles = "USER")
    void makeWithdrawal_withUserRoleAndCorrectAmount_shouldMakeChangesAndRedirect() throws Exception{
        //given
        var user = new UserInfo("user", "user", UserRole.USER);

        //when
        var result = mvc.perform(MockMvcRequestBuilders
                .post("/user/withdraw")
                .param("amount", "100")
                .with(user(user)));

        //then
        result.andExpect(status().isFound())
                .andExpect(view().name("redirect:/"));

        verify(service).withdrawMoney("user", new BigDecimal(100));
    }
}
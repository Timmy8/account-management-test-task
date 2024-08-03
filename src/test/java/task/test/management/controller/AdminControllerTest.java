package task.test.management.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import task.test.management.service.UserInfoService;


import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AdminController.class)
@DisplayName("Модульные тесты AdminController")
class AdminControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserInfoService service;

    @Test
    @WithMockUser(roles = "ADMIN")
    void blockUser_withAdminUser_shouldMakeChangesAndReturnsRedirect() throws Exception{
        //when
        var result = mvc.perform(MockMvcRequestBuilders.post("/admin/block").param("username", "user1"));

        //then
        result.andExpect(status().isFound())
                .andExpect(view().name("redirect:/"));

        verify(service).changeBlockedStatus("user1", true);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void unblockUser_withAdminUser_shouldMakeChangesAndReturnsRedirect() throws Exception{
        //when
        var result = mvc.perform(MockMvcRequestBuilders.post("/admin/unblock").param("username", "user1"));

        //then
        result.andExpect(status().isFound())
                .andExpect(view().name("redirect:/"));

        verify(service).changeBlockedStatus("user1", false);
    }
}
package task.test.management.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import task.test.management.entity.UserInfo;
import task.test.management.entity.UserRole;
import task.test.management.service.UserInfoService;

@Controller
public class AccountController {
    private final UserInfoService service;

    public AccountController(UserInfoService service) {
        this.service = service;
    }

    @GetMapping("/")
    public String getAccountPage(Model model, Authentication authentication){
        var user = (UserInfo)authentication.getPrincipal();
        model.addAttribute("username", user.getUsername());

        if (user.getRole() == UserRole.ADMIN){
            model.addAttribute("allUsers", service.getAllUsers());
            return "adminPage.html";
        }
        else if (user.getRole() == UserRole.USER){
            model.addAttribute("balance", service.getUserBalance(user.getUsername()));
            return "userPage.html";
        } else throw new RuntimeException("Unknown user role");
    }

    @GetMapping("/login")
    public String getLoginPage(){
        return "login.html";
    }
}

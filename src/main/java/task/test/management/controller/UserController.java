package task.test.management.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import task.test.management.entity.UserInfo;
import task.test.management.service.UserInfoService;

import java.math.BigDecimal;

@Controller
@RequestMapping("user/")
public class UserController {
    private final UserInfoService service;

    public UserController(UserInfoService service) {
        this.service = service;
    }

    @PostMapping("deposit")
    public String makeDeposit(@ModelAttribute("amount") BigDecimal amount, Authentication authentication){
        var user = (UserInfo)authentication.getPrincipal();

        if (amount.doubleValue() > 0)
            service.depositMoney(user.getUsername(), amount);
        else throw new RuntimeException("Deposit amount must be positive value!");

        return "redirect:/";
    }

    @PostMapping("withdraw")
    public String makeWithdrawal(@ModelAttribute("amount") BigDecimal amount, Authentication authentication){
        var user = (UserInfo)authentication.getPrincipal();

        if (amount.doubleValue() > 0)
            service.withdrawMoney(user.getUsername(), amount);
        else throw new RuntimeException("Withdrawal amount must be positive value!");

        return "redirect:/";
    }
}

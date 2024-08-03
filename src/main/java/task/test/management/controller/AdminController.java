package task.test.management.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import task.test.management.service.UserInfoService;

@Controller
@RequestMapping("admin/")
public class AdminController {
    private final UserInfoService service;

    public AdminController(UserInfoService service) {
        this.service = service;
    }

    @PostMapping("block")
    public String blockUser(@RequestParam("username") String username){
        service.changeBlockedStatus(username, true);
        return "redirect:/";
    }

    @PostMapping("unblock")
    public String unblockUser(@RequestParam("username") String username){
        service.changeBlockedStatus(username, false);
        return "redirect:/";
    }
}

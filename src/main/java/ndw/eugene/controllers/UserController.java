package ndw.eugene.controllers;

import ndw.eugene.domain.User;
import ndw.eugene.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

@RequestMapping("/u")
@SessionAttributes("user")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public String login(@RequestParam("login") String login, @RequestParam("password") String password, Model model){
        User user = userService.getUser(login,password);
        model.addAttribute("user", user);

        return "redirect:/u/user";
    }

    @GetMapping("/user")
    public String getUser(@ModelAttribute("user") User user){
        return "user";
    }

    @GetMapping("/form")
    public String retForm(){
        return "loginpage";
    }

    @GetMapping("/logout")
    public String logout(SessionStatus sessionStatus){
        sessionStatus.setComplete();
        return "redirect:/u/user";
    }
}

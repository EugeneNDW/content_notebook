package ndw.eugene.controllers;

import ndw.eugene.domain.User;
import ndw.eugene.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public String login(HttpSession session, @RequestParam String login, @RequestParam String password, Model model){

        authService.authorizeUser(session.getId(),login,password);
        User user = authService.returnUserIfAuthorized(session.getId());
        model.addAttribute("user", user);

        return "redirect:/note/all";
    }

    @GetMapping("/login")
    public String getLoginForm(){
        return "loginform";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        authService.unAuthUser(session.getId());
        return "loginform";
    }

    @PostMapping("/signup")
    public String signup(HttpSession session, @RequestParam("login") String login, @RequestParam("password") String password){
        authService.registerUser(login, password);
        authService.authorizeUser(session.getId(), login, password);
        return "redirect:/note/all";
    }

    @GetMapping("/signup")
    public String getSignupForm(){
        return "signupform";
    }


    @ExceptionHandler
    public String exceptionHandler(IllegalArgumentException e){
        return "403";
    }
}

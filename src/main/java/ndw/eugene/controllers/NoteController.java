package ndw.eugene.controllers;

import ndw.eugene.domain.Note;
import ndw.eugene.domain.User;
import ndw.eugene.services.AuthService;
import ndw.eugene.services.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.websocket.server.PathParam;
import javax.xml.soap.SAAJResult;
import javax.xml.ws.Response;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/note")
public class NoteController {

    private NoteService service;
    private AuthService authService;

    @Autowired
    public NoteController(@Qualifier("postgres") NoteService service, AuthService authService) {
        this.service = service;
        this.authService = authService;
    }

    @GetMapping("/{id}")
    public String getNote(HttpSession session, @PathVariable int id, Model model){
        User user = authService.returnUserIfAuthorized(session.getId());
        Note n = service.getNoteById(user, id);
        if(n!=null) {
            model.addAttribute("note", n);
            return "onenote";
        }
        return "ErrorPages/404";
    }

    @GetMapping("/all")
    public String getAllNotes(HttpSession session, Model model){
        User user = authService.returnUserIfAuthorized(session.getId());
        model.addAttribute("noteslist", service.getAllNotes(user));
        model.addAttribute("userid", user.getId());
        return "noteslist";
    }

    @GetMapping("/delete/{id}")
    public String deleteNote(HttpSession session, @PathVariable int id){
        User user = authService.returnUserIfAuthorized(session.getId());
        service.deleteNoteById(user, id);
        return "redirect:/note/all";
    }

    @GetMapping("/edit/{id}")
    public String editNote(HttpSession session, @PathVariable int id, Model model){
        User user = authService.returnUserIfAuthorized(session.getId());
        model.addAttribute("note", service.getNoteById(user, id));
        return "editform";
    }

    @PostMapping("/edit/{id}")
    public String edit(HttpSession session, @ModelAttribute Note note){
        User user = authService.returnUserIfAuthorized(session.getId());
        service.editNote(user, note);
        return "redirect:/note/all";
    }

    @GetMapping("/read")
    public String getReadNotes(HttpSession session, @RequestParam boolean isread, Model model){
        User user = authService.returnUserIfAuthorized(session.getId());
        model.addAttribute("noteslist", service.getAllRead(user,isread));
        return "noteslist";
    }

    @GetMapping("/mark/{id}")
    public String markNoteRead(HttpSession session, @PathVariable int id,  @RequestParam boolean isread){
        User user = authService.returnUserIfAuthorized(session.getId());
        service.markAsRead(user, id, isread);
        return "redirect:/note/all";
    }

    @GetMapping("/create")
    public String createNewNote(HttpSession session){
        authService.returnUserIfAuthorized(session.getId());
        return "createform";
    }

    @PostMapping("/create")
    public String create(HttpSession session, @ModelAttribute("note") Note note){
        User user = authService.returnUserIfAuthorized(session.getId());
        service.addNote(user, note);
        return "redirect:all";
    }


    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handle(DataAccessException e){
        return "ErrorPages/500";
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String handle(IllegalArgumentException e) {
        return "ErrorPages/401";
    }

}
package ndw.eugene.controllers;

import ndw.eugene.domain.Note;
import ndw.eugene.domain.User;
import ndw.eugene.services.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.websocket.server.PathParam;
import javax.xml.soap.SAAJResult;
import java.util.List;

@Controller
@RequestMapping("/note")
public class NoteController {

    private NoteService service;

    @Autowired
    public NoteController(@Qualifier("postgres") NoteService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public String getNote(@PathVariable int id, Model model){
        Note n = service.getNoteById(id);
        if(n!=null) {
            model.addAttribute("note", n);
            return "onenote";
        }
        return "notenotfound";
    }

    @GetMapping("/all")
    public String getAllNotes(Model model){
        model.addAttribute("noteslist", service.getAllNotes());
        return "noteslist";
    }

    @GetMapping("/delete/{id}")
    public String deleteNote(@PathVariable int id){
        service.deleteNoteById(id);
        return "redirect:/note/all";
    }

    @GetMapping("/edit/{id}")
    public String editNote(@PathVariable int id, Model model){
        model.addAttribute("note", service.getNoteById(id));
        return "editform";
    }

    @PostMapping("/edit/{id}")
    public String edit(@ModelAttribute Note note){
        service.editNote(note);
        return "redirect:/note/all";
    }

    @GetMapping("/read")
    public String getReadNotes(@RequestParam boolean isread, Model model){
        model.addAttribute("noteslist", service.getAllRead(isread));
        return "noteslist";
    }

    @GetMapping("/mark/{id}")
    public String markNoteRead(@PathVariable int id,  @RequestParam boolean isread){
        service.markAsRead(id, isread);
        return "redirect:/note/all";
    }

    @GetMapping("/create")
    public String createNewNote(){
        return "createform";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute("note") Note note){
        service.addNote(note);
        return "redirect:all";
    }
}
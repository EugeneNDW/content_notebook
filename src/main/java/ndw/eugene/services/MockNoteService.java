package ndw.eugene.services;

import ndw.eugene.domain.Note;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Qualifier("listservice")
public class MockNoteService implements NoteService {

    private List<Note> notes;

    public MockNoteService() {
        notes = new ArrayList<>();

        notes.add(new Note("first note","first text", 1));
        notes.add(new Note("second note","second text", true,2));
        notes.add(new Note("third note","third text",3));
        notes.add(new Note("fourth note","fourth text", true,4));
        notes.add(new Note("fifth note","fifth text", true,5));
        notes.add(new Note("sixth note","sixth text",6));
        notes.add(new Note("seventh note","seventh text", true,7));
        notes.add(new Note("eighth note","eighth text",8));
        notes.add(new Note("ninth note","ninth text", true,9));
        notes.add(new Note("tenth note","tenth text",10));
    }

    @Override
    public void addNote(Note note) {
        notes.add(note);
    }

    @Override
    public Note getNoteById(int id) {
        return search(id);
    }

    @Override
    public List<Note> getAllNotes() {
        return notes;
    }

    @Override
    public void deleteNoteById(int id) {
        Note n = search(id);
        notes.remove(n);
    }

    @Override
    public void editNote(Note note) {
        notes.remove(note.getId());
        notes.add(note);
    }

    @Override
    public void markAsRead(int id, boolean isRead) {
        Note n = getNoteById(id);
        n.setIsRead(isRead);
    }

    @Override
    public List<Note> getAllRead(boolean isRead) {
        List<Note> n = new ArrayList<>();
        for (Note note:notes) {
            if(note.isRead() == isRead){
                n.add(note);
            }
        }
        return n;
    }

    private Note search(int id){
        for (Note n:notes){
            if(n.getId()==id){
                return n;
            }
        }
        return null;
    }
}

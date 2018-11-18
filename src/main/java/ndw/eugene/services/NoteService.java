package ndw.eugene.services;

import ndw.eugene.domain.Note;
import ndw.eugene.domain.User;

import java.util.List;

public interface NoteService {

    void addNote(User user,Note note);
    Note getNoteById(User user, int id);
    List<Note> getAllNotes(User user);
    void deleteNoteById(User user, int id);
    void editNote(User user, Note note);
    void markAsRead(User user, int id, boolean isRead);
    List<Note> getAllRead(User user, boolean isRead);
}

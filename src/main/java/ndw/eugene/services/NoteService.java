package ndw.eugene.services;

import ndw.eugene.domain.Note;

import java.util.List;

public interface NoteService {

    void addNote(Note note);
    Note getNoteById(int id);
    List<Note> getAllNotes();
    void deleteNoteById(int id);
    void editNote(Note note);
    void markAsRead(int id, boolean isRead);
    List<Note> getAllRead(boolean isRead);
}

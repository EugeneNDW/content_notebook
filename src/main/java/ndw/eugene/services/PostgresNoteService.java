package ndw.eugene.services;

import ndw.eugene.DAO.NoteDAO;
import ndw.eugene.domain.Note;
import ndw.eugene.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Qualifier("postgres")
public class PostgresNoteService implements NoteService {

    private NoteDAO noteDAO;

    @Autowired
    public PostgresNoteService(NoteDAO noteDAO) {
        this.noteDAO = noteDAO;
    }

    @Override
    public void addNote(User user, Note note) {
        noteDAO.addNote(note, user.getId());
    }

    @Override
    public Note getNoteById(User user, int id) {
        return noteDAO.getNoteById(id, user.getId());
    }

    @Override
    public List<Note> getAllNotes(User user) {
        return noteDAO.getNotesList(user.getId());
    }

    @Override
    public void deleteNoteById(User user, int id) {
        noteDAO.deleteNoteById(id, user.getId());
    }

    @Override
    public void editNote(User user, Note note) {
        noteDAO.editNote(note, user.getId());
    }

    @Override
    public void markAsRead(User user, int id, boolean isRead) {
        Note note = getNoteById(user, id);
        note.setIsRead(isRead);
        noteDAO.mark(note, user.getId());
    }

    @Override
    public List<Note> getAllRead(User user, boolean isRead) {
        return getAllNotes(user).stream()
                .filter(n->n.isRead()==isRead)
                .collect(Collectors.toList());
    }
}

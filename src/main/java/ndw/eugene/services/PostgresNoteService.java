package ndw.eugene.services;

import ndw.eugene.DAO.NoteDAO;
import ndw.eugene.domain.Note;
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
    public void addNote(Note note) {
        noteDAO.addNote(note);
    }

    @Override
    public Note getNoteById(int id) {
        return noteDAO.getNoteById(id);
    }

    @Override
    public List<Note> getAllNotes() {
        return noteDAO.getNotesList();
    }

    @Override
    public void deleteNoteById(int id) {
        noteDAO.deleteNoteById(id);
    }

    @Override
    public void editNote(Note note) {
        noteDAO.editNote(note);
    }

    @Override
    public void markAsRead(int id, boolean isRead) {
        Note note = getNoteById(id);
        note.setIsRead(isRead);
        noteDAO.mark(note);
    }

    @Override
    public List<Note> getAllRead(boolean isRead) {
        return getAllNotes().stream()
                .filter(n->n.isRead()==isRead)
                .collect(Collectors.toList());
    }
}


//todo прочитать про view в коллекциях
//todo обработка ошибок (тут либо страницы контейнера, либо каждому сервису свой хэндлер, либо читать про AOP)
//todo тесты к DAO
//todo прочитать про наследование от внутреннего класса и про модификатор final у классов

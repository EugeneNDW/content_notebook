package ndw.eugene.services;

import ndw.eugene.domain.Note;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MockNoteServiceTest {

    private NoteService service;

    @BeforeEach
    void init(){
        service = new MockNoteService();
    }

    @Test
    void Initialization(){
        assertEquals("first note", service.getNoteById(1).getHeader());
        assertEquals("tenth note", service.getNoteById(10).getHeader());
    }

    @Test
    void addNote(){
        Note n = new Note("eleventh note", "eleventh text", 11);
        service.addNote(n);
        assertEquals(n, service.getNoteById(n.getId()));
    }

    @Test
    void deleteNote(){
       assertNotNull(service.getNoteById(1));
       service.deleteNoteById(1);
       assertNull(service.getNoteById(1));
    }

    @Test
    void updateNote(){
        Note n = service.getNoteById(2);
        n.setText("updated note");
        n.setHeader("updated note");
        service.editNote(n);

        assertEquals("updated note", service.getNoteById(2).getHeader());
        assertEquals("updated note", service.getNoteById(2).getText());
    }

    @Test
    void markAsRead(){
        Note n = new Note("read", "read note", 11);
        service.addNote(n);
        assertFalse(service.getNoteById(n.getId()).isRead());
        service.markAsRead(n.getId(), true);
        assertTrue(service.getNoteById(n.getId()).isRead());
    }
}
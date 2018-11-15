package ndw.eugene.services;

import ndw.eugene.DAO.NoteDAO;
import ndw.eugene.domain.Note;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PostgresNoteServiceTest {

    NoteService ps;
    NoteDAO nd;


    @BeforeEach
    void instInit(){
        nd = mock(NoteDAO.class);
        ps = new PostgresNoteService(nd);
    }

    @Test
    void getNoteById() {
        ps.getNoteById(1);
        verify(nd).getNoteById(1);
    }

    @Test
    void getNotesList(){
        ps.getAllNotes();
        verify(nd).getNotesList();
    }

    @Test
    void deleteNote(){
        ps.deleteNoteById(1);
        verify(nd).deleteNoteById(1);
    }

    @Test
    void editNote(){
        Note n = new Note();
        ps.editNote(n);
        verify(nd).editNote(n);
    }



    @Test
    void onlyReadNotes(){
        List<Note> list = new ArrayList<>();
        list.add(new Note("first", "text", true, 1));
        list.add(new Note("second", "text",false, 2));
        list.add(new Note("third", "text", false,3));
        list.add(new Note("fourth", "text", false, 4));
        list.add(new Note("fifth", "text", true, 5));
        list.add(new Note("sixth", "text",true, 6));
        list.add(new Note("seventh", "text", false, 7));

        when(nd.getNotesList()).thenReturn(list);

        List<Note> trueList = ps.getAllRead(true);
        assertEquals(3, trueList.size());
        for (Note n:trueList){
            assertTrue(n.isRead());
        }


        List<Note> falseList =  ps.getAllRead(false);
        assertEquals(4, falseList.size());
        for (Note n:falseList){
            assertFalse(n.isRead());
        }


        when(nd.getNotesList()).thenReturn(new ArrayList<>());
        assertEquals(0,ps.getAllRead(true).size());
        assertEquals(0,ps.getAllRead(false).size());
    }

}
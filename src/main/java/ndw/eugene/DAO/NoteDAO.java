package ndw.eugene.DAO;

import ndw.eugene.domain.Note;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class NoteDAO{

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public NoteDAO(@Qualifier("postgres") DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void addNote(Note note, int user_id){
        jdbcTemplate.update("INSERT INTO notes(header, text, isread, user_id) VALUES (?,?,?,?)",
                note.getHeader(), note.getText(), note.isRead(), user_id);
    }

    public List<Note> getNotesList(){
        return jdbcTemplate.query("SELECT * FROM notes",new NoteMapper());
    }


    public List<Note> getNotesList(int userId){
        return jdbcTemplate.query("SELECT * FROM notes WHERE user_id = ?", new Object[]{userId},new NoteMapper());
    }

    public Note getNoteById(int id, int user_id){
        return jdbcTemplate.queryForObject("SELECT * FROM notes WHERE id=? AND user_id=?", new Object[]{id, user_id}, new NoteMapper() );
    }

    public void editNote(Note note, int user_id){
        jdbcTemplate.update("UPDATE notes SET header=?, text=?, isread=? WHERE id=? AND user_id=?;", note.getHeader(),
                note.getText(), note.isRead(), note.getId(), user_id);
    }

    public void deleteNoteById(int id, int user_id){
        jdbcTemplate.update("DELETE FROM notes WHERE id=? AND user_id = ?", id, user_id);
    }

    public void mark(Note note, int user_id){
        jdbcTemplate.update("UPDATE  notes SET isread=? WHERE id=? AND user_id=?", note.isRead(), note.getId(), user_id);
    }

    private static final class NoteMapper implements RowMapper<Note>{
        @Override
        public Note mapRow(ResultSet resultSet, int i) throws SQLException {
            Note note = new Note();
            note.setId(resultSet.getInt("id"));
            note.setHeader(resultSet.getString("header"));
            note.setText(resultSet.getString("text"));
            note.setIsRead(resultSet.getBoolean("isread"));
            return note;
        }
    }
}

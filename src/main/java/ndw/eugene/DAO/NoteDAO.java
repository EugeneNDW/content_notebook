package ndw.eugene.DAO;

import ndw.eugene.domain.Note;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class NoteDAO{

    private JdbcTemplate jdbcTemplate;

    @Autowired
    NoteDAO(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void addNote(Note note){
        jdbcTemplate.update("insert into notes(header, text, isread, user_id) values (?,?,?,?)",
                note.getHeader(), note.getText(), note.isRead(), note.getUser().getId());
    }

    public List<Note> getNotesList(){
        return jdbcTemplate.query("SELECT * FROM notes",new NoteMapper());
    }

    public Note getNoteById(int id){
        return jdbcTemplate.queryForObject("SELECT * FROM notes WHERE id=?", new Object[]{id}, new NoteMapper() );
    }

    public void editNote(Note note){
        jdbcTemplate.update("UPDATE notes set header=?, text=?, isread=? where id=?;", note.getHeader(),
                note.getText(), note.isRead(), note.getId());
    }

    public void deleteNoteById(int id){
        jdbcTemplate.update("delete from notes where id=?", id);
    }

    public void mark(Note note){
        jdbcTemplate.update("UPDATE  notes set isread=? where id=?", note.isRead(), note.getId());
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

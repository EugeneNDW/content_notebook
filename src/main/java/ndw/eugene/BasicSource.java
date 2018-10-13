package ndw.eugene;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

public class BasicSource extends BasicDataSource {

    public BasicSource() {
        System.getenv("");
        setDriverClassName("org.postgresql.Driver");
        setUrl("jdbc:postgresql://localhost:5432/content_notes");
        setUsername("Asus");
        setPassword("Asus");
    }
}

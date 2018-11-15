package ndw.eugene.configurations;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.SQLException;

@Configuration
public class DataSourceConfiguration {

    private static final String POSTGRES_USER = "Asus";
    private static final String POSTGRES_DRIVER_NAME = "org.postgresql.Driver";
    private static final String POSTGRES_URL = "jdbc:postgresql://localhost:5432/content_notes";
    private static final String POSTGRES_PASSWORD = "Asus";

    @Bean
    @Qualifier("postgres")
    public BasicDataSource postgresDataSource(){
        BasicDataSource ds = new BasicDataSource();

        ds.setDriverClassName(POSTGRES_DRIVER_NAME);
        ds.setUrl(POSTGRES_URL);
        ds.setUsername(POSTGRES_USER);
        ds.setPassword(POSTGRES_PASSWORD);

        return ds;
    }


    @Qualifier("heroku")
    public BasicDataSource herokuDataSource() throws URISyntaxException {
        URI dbUri = new URI(System.getenv("DATABASE_URL"));

        String username = dbUri.getUserInfo().split(":")[0];
        String password = dbUri.getUserInfo().split(":")[1];
        String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath();

        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setUrl(dbUrl);
        basicDataSource.setUsername(username);
        basicDataSource.setPassword(password);

        return basicDataSource;
    }

    @Bean
    @Qualifier("test")
    public BasicDataSource createDataSource(){
        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName("org.h2.Driver");
        ds.setUrl("jdbc:h2:mem:test");
        createTable(ds);
        return ds;
    }

    private void createTable(DataSource ds) {
        try(Connection c = ds.getConnection()) {
            c.createStatement().execute("create table notes(id serial not null constraint notes_pkey primary key, \n" +
                    "header varchar(255) not null , text varchar(1000) not null , isread boolean not null default false );");
        } catch (SQLException e){
            System.out.println(e.getMessage());
            System.exit(-1);
        }
    }
}

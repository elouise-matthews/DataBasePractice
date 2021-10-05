package obsolete;

import org.jdbi.v3.core.Jdbi;
import org.postgresql.ds.PGPoolingDataSource;

import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.sql.DataSource;
import java.sql.*;


public class DataSourceConnector {
    private static Jdbi jdbi;
    public static void main(String[] args) {
        PGPoolingDataSource source = new PGPoolingDataSource();
        source.setDataSourceName("A Data Source");
        source.setServerName( "localhost"); ///setServerNames(new String[] {"localhost"});
        source.setDatabaseName("test");
        source.setUser("testuser");
        source.setPassword("testpassword");
        source.setMaxConnections(10);
        jdbi = Jdbi.create(source);
        try (Connection conn = source.getConnection())
        {
            // use connection
        }
        catch (SQLException e)
        {
            // log error
        }
    }

}

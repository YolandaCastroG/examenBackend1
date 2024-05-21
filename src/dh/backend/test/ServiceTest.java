package dh.backend.test;

import org.apache.log4j.Logger;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ServiceTest {
    @BeforeAll
    static void crearTablas(){
        Connection connection = null;
        try {
            Class.forName("org.h2.Driver");
            connection = DriverManager.getConnection("jdbc:h2:~/ExamenPreparacion1;INIT=RUNSCRIPT FROM 'create.sql'", "sa", "sa");
        } catch (Exception e){
            e.printStackTrace();
            LOGGER.error(e.getMessage());
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                LOGGER.error(e.getMessage());
            }
        }
    }
    public static Logger LOGGER = Logger.getLogger(ServiceTest.class);

    @Test
    @DisplayName("Testear que ...")
    void testear(){

    }
}

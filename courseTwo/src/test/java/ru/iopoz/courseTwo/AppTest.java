package ru.iopoz.courseTwo;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import ru.iopoz.courseTwo.homework7.DBConnector;

import java.sql.SQLException;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }

    @Test
    public void checkUser() throws SQLException {
        DBConnector dbConnector = new DBConnector();
        boolean flag = dbConnector.isUserAuthorized("server", "server");
        boolean flag_2 = dbConnector.isUserAuthorized("iopoz", "server");
    }

    @Test
    public void sendMsg() throws SQLException {
        DBConnector dbConnector = new DBConnector();
        dbConnector.sendMsg("Hi", "1", "");
    }

    @Test
    public void getMsg() throws SQLException {
        DBConnector dbConnector = new DBConnector();
        dbConnector.getAllMessages("1");
    }
}

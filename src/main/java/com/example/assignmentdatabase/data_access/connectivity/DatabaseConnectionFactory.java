package com.example.assignmentdatabase.data_access.connectivity;

import java.sql.Connection;
import java.sql.SQLException;

public interface DatabaseConnectionFactory {
    Connection getConnection() throws SQLException;
}

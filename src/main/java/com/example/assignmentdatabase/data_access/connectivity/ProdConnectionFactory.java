package com.example.assignmentdatabase.data_access.connectivity;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//Connection to chinook database
@Component
@Profile("production")
public class ProdConnectionFactory implements DatabaseConnectionFactory {

        static final String URL = "jdbc:sqlite::resource:Chinook_Sqlite.sqlite";

        public Connection getConnection() throws SQLException {
            return DriverManager.getConnection(URL);
        }

}

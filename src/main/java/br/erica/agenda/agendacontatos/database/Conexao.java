/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.erica.agenda.agendacontatos.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author erica
 */
public class Conexao {
    private static final String URL = "jdbc:postgresql://localhost:5432/agenda";
    private static final String USUARIO = "postgres";
    private static final String SENHA = "123456";
    
    public static Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, SENHA);
        
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cineguide;

/**
 *
 * @author USER
 */
import java.sql.*;
import javax.swing.JOptionPane;

public class Koneksi {
    private String url = "jdbc:mysql://localhost/cineguide";
    private String username_xampp = "root";
    private String password_xampp = "";
    private Connection con;
    
    public void connect(){
        try{
            con = DriverManager.getConnection(url, username_xampp, password_xampp);
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    public Connection getCon(){
        return con;
    }
}

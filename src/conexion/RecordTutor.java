/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conexion;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import negociodistribuidos.Tutor;

/**
 *
 * @author migue
 */
public class RecordTutor {
    
    public Tutor checkLogin(String user, String pass, Connection con){
        ArrayList<Tutor> tutores = getTutores(con);
        for (Tutor tutore : tutores) {
            System.out.println("---------");
            System.out.println(tutore.getNombre());
            System.out.println("----------");
            if(tutore.getUsuario().equals(user) && tutore.getContrasenia().equals(pass)){
                return tutore;
            }
        }
        return null;
    }
    
    private ArrayList<Tutor> getTutores(Connection con){
        String sql = "SELECT * FROM tutores";
        ResultSet rs;
        CallableStatement cst;
        ArrayList<Tutor> tutores = new ArrayList<Tutor>();
        try {
            cst = con.prepareCall(sql);
            rs = cst.executeQuery();
            while(rs.next()){
                Tutor tut= new Tutor(
                    rs.getString("nombre"),
                    rs.getString("usuario"),
                    rs.getString("pass"),
                    rs.getInt("id"),
                    rs.getInt("id_alumno")
                );
                
                tutores.add(tut);

            }
            
        } catch (SQLException ex) {
            Logger.getLogger(RecordTutor.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        return tutores;
    } 
    
}

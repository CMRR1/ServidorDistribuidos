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
import negociodistribuidos.Alumno;


/**
 *
 * @author migue
 */
public class RecordAlumnos {
    public ArrayList<Alumno> getAlumnos(Connection con){
        String sql = "SELECT * FROM alumnos";
        ResultSet rs;
        CallableStatement cst;
        ArrayList<Alumno> alumnos = new ArrayList<Alumno>();
        try {
            cst = con.prepareCall(sql);
            rs = cst.executeQuery();
            while(rs.next()){
                Alumno alum= new Alumno(rs.getString("nombre"), 
                        null, 
                        null, 
                        null, 
                        rs.getInt("id"));

                alumnos.add(alum);

            }
            
        } catch (SQLException ex) {
            Logger.getLogger(RecordTutor.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        return alumnos;
    } 
}

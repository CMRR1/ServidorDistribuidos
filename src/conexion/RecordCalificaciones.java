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
import negociodistribuidos.Calificacion;

/**
 *
 * @author migue
 */
public class RecordCalificaciones {
    
    public ArrayList<Calificacion> getCalificacions(Connection con, int id){
        String sql = "SELECT * FROM calificaciones WHERE id_alumno="+id;
        ResultSet rs;
        CallableStatement cst;
        ArrayList<Calificacion> asig = new ArrayList<Calificacion>();
        try {
            cst = con.prepareCall(sql);
            rs = cst.executeQuery();
            while(rs.next()){
                Calificacion asign = new Calificacion(
                        rs.getInt("calificacion"), 
                        rs.getInt("id"), 
                        rs.getInt("tareas"), 
                        rs.getString("nombre_curso"));
                        
                System.out.println(asign.getCurso());
                
                asig.add(asign);

            }
            
        } catch (SQLException ex) {
            Logger.getLogger(RecordTutor.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        return asig;
    } 
    
}

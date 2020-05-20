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
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import negociodistribuidos.Asignacion;


/**
 *
 * @author migue
 */
public class RecordAsignaciones {
    
    public ArrayList<Asignacion> getAsignacionesAlumno(Connection con,int id){
        String sql = "SELECT * FROM asignaciones WHERE alumno_id = "+id+";";
        ResultSet rs;
        CallableStatement cst;
        ArrayList<Asignacion> asig = new ArrayList<Asignacion>();
        
        try {
            cst = con.prepareCall(sql);
            rs = cst.executeQuery();
            while(rs.next()){
                Asignacion asign = new Asignacion(
                        rs.getString("nombre"),
                        rs.getInt("calificacion"),
                        rs.getInt("id"),
                        rs.getBoolean("estado"),
                        rs.getBoolean("tipo"),
                        rs.getString("nombre_Curso"),
                rs.getString("anotaciones")); 
                System.out.println(asign.getNombre());
                asig.add(asign);

            }
        } catch (SQLException ex) {
            Logger.getLogger(RecordAsignaciones.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        
        return asig;
    }
    
    public boolean revisarTarea(boolean revisar,Connection con,int id){
        try {
            Statement stmt = con.createStatement();
            
            String sql = "UPDATE asignaciones "+
                    "SET estado="+revisar+ " WHERE id = " + id;
            
            stmt.execute(sql);
            System.out.println("TODO BIEN");
            return true;
            
        } catch (SQLException ex) {
            Logger.getLogger(RecordAsignaciones.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public ArrayList<Asignacion> getAsignacions(Connection con){
        String sql = "SELECT * FROM asignaciones";
        ResultSet rs;
        CallableStatement cst;
        ArrayList<Asignacion> asig = new ArrayList<Asignacion>();
        try {
            cst = con.prepareCall(sql);
            rs = cst.executeQuery();
            while(rs.next()){
                Asignacion asign = new Asignacion(
                        rs.getString("nombre"),
                        rs.getInt("calificacion"),
                        rs.getInt("id"),
                        rs.getBoolean("estado"),
                        rs.getBoolean("tipo"),
                rs.getString("nombre_Curso"),
                rs.getString("anotaciones")); 

                asig.add(asign);

            }
            
        } catch (SQLException ex) {
            Logger.getLogger(RecordTutor.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        return asig;
    } 
}

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
import negociodistribuidos.Curso;


/**
 *
 * @author migue
 */
public class RecordCursos {
    
    public ArrayList<Curso> getCursos(Connection con){
        String sql = "SELECT * FROM cursos";
        ResultSet rs;
        CallableStatement cst;
        ArrayList<Curso> asig = new ArrayList<Curso>();
        try {
            cst = con.prepareCall(sql);
            rs = cst.executeQuery();
            while(rs.next()){
                Curso asign = new Curso(rs.getString("nombre"), 
                        null,
                        rs.getInt("id"),
                        null);
                        

                asig.add(asign);

            }
            
        } catch (SQLException ex) {
            Logger.getLogger(RecordTutor.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        return asig;
    } 
    
}

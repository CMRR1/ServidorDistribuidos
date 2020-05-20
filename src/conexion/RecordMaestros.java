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
import negociodistribuidos.Maestro;


/**
 *
 * @author migue
 */
public class RecordMaestros {
    
    public ArrayList<Maestro> getMaestros(Connection con){
        String sql = "SELECT * FROM maestros";
        ResultSet rs;
        CallableStatement cst;
        ArrayList<Maestro> asig = new ArrayList<Maestro>();
        try {
            cst = con.prepareCall(sql);
            rs = cst.executeQuery();
            while(rs.next()){
                Maestro asign = new Maestro(
                        null,
                        rs.getString("usuario"),
                        rs.getString("pass"),
                        rs.getInt("id"));
                        

                asig.add(asign);

            }
            
        } catch (SQLException ex) {
            Logger.getLogger(RecordTutor.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        return asig;
    } 
    
}

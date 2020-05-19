/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidordistribuidos;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import conexion.RecordTutor;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import negociodistribuidos.Tutor;

/**
 *
 * @author ceccy
 */
public class ServidorDistribuidos {

    public static final String URL = "jdbc:mysql://localhost:3306/distribuidos?useTimezone=true&serverTimezone=UTC";
    public static final String user = "root";
    public static final String pass = "root";

    //Sentencia que se le envía al manejador de la base de datos para ejecutar un SP
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ClassNotFoundException {
        try {

            ServerSocket server = null;
            Socket sc = null;
            int puerto = 5000;
            server = new ServerSocket(puerto);
            DataInputStream in;
            DataOutputStream out;
            ObjectOutputStream objOut;
            ArrayList<Tutor> tutores = new ArrayList<Tutor>();

            while (true) {
                sc = server.accept();
                System.out.println("INICIO");
                in = new DataInputStream(sc.getInputStream());
                out = new DataOutputStream(sc.getOutputStream());
                objOut = new ObjectOutputStream(sc.getOutputStream());

                try {
                    // TODO code application logic here
                    //private static final String url ="jdbc:mysql://192.168.1.90:3306/fhdb?useTimezone=true&serverTimezone=UTC";
                    Connection con = DriverManager.getConnection(URL, user, pass);

                    RecordTutor re = new RecordTutor();
                    System.out.println(re.getTutores(con));
                    tutores = re.getTutores(con);
                    String mensaje = in.readUTF();
                    System.out.println(mensaje);
                    out.writeInt(tutores.size());
                    for (Tutor tutore : tutores) {
                        System.out.println(tutore);
                        objOut.writeObject(tutore);
                    }
                    System.out.println("Lista enviada");
                    sc.close();
                    System.out.println("FIN");
                    con.close();
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }

            }

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

    }

}

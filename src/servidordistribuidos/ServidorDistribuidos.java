/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidordistribuidos;

import conexion.RecordAlumnos;
import conexion.RecordAsignaciones;
import conexion.RecordCalificaciones;
import conexion.RecordCursos;
import conexion.RecordMaestros;
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
import negociodistribuidos.Alumno;
import negociodistribuidos.Asignacion;
import negociodistribuidos.Calificacion;
import negociodistribuidos.Curso;
import negociodistribuidos.Maestro;

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
    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        //Connection con = DriverManager.getConnection(URL, user, pass);
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
                System.out.println("Esperando conexion...");
                sc = server.accept();
                System.out.println("INICIO");
                // ESTOS SON LOS QUE ENVIA Y RECIBEN DE LA LOGICA
                in = new DataInputStream(sc.getInputStream());
                out = new DataOutputStream(sc.getOutputStream());
                objOut = new ObjectOutputStream(sc.getOutputStream());
                System.out.println("HOLA");
                int opcion = in.readInt();

                System.out.println(opcion);
                /*
                OPCIONES:
                0 => login
                1 => obtenerAlumno
                2 => obtener asignaciones del alumno
                3 => validar asignacion por tutor
                 */

                switch (opcion) {
                    case 0: {
                        System.out.println("Opcion Login");
                        String user = in.readUTF();
                        String pass = in.readUTF();
                        try {
                            // TODO code application logic here
                            //private static final String url ="jdbc:mysql://192.168.1.90:3306/fhdb?useTimezone=true&serverTimezone=UTC";
                            Connection con = DriverManager.getConnection(URL, "root", "root");

                            RecordTutor re = new RecordTutor();
                            Tutor tut = re.checkLogin(user, pass, con);
                            if (tut != null) {
                                System.out.println("Existe el objeto");
                                out.writeBoolean(true);
                                objOut.writeObject(tut);
                            } else {
                                out.writeBoolean(false);
                            }
                            //tutores = re.getTutores(con);
                            //String mensaje = in.readUTF();
                            //System.out.println(mensaje);
                            //out.writeInt(tutores.size());
                            //for (Tutor tutore : tutores) {
                            //  System.out.println(tutore);

                            //}
                            //System.out.println("Lista enviada");
                            sc.close();
                            System.out.println("FIN");
                            con.close();
                        } catch (SQLException ex) {
                            System.out.println(ex.getMessage());
                        }

                        break;
                    }
                    case 1: {
                        break;
                    }
                    case 2: {
                        System.out.println("Opciones asignaciones");
                        RecordAsignaciones as = new RecordAsignaciones();
                        System.out.println("Leyendo el id del alumno");
                        int id = in.readInt();
                        Connection con = DriverManager.getConnection(URL, "root", "root");
                        System.out.println("Creando la lista");
                        ArrayList<Asignacion> asignaciones = as.getAsignacionesAlumno(con, id);
                        out.writeInt(asignaciones.size());
                        System.out.println("Viendo la cantidad de asignaciones");
                        System.out.println(asignaciones.size());
                        if(asignaciones.size() > 0){
                            System.out.println("Enviando las asignaciones para hacer la lista");
                            for(int i = 0; i<asignaciones.size();i++){
                               System.out.println(asignaciones.get(i).getNombre());
                                objOut.writeObject(asignaciones.get(i)); 
                            }

                        }
                        sc.close();
                        System.out.println("FIN");
                        con.close();
                        
                        break;
                    }
                    case 3:{
                        System.out.println("Opcion revision asignaciones");
                        RecordAsignaciones as = new RecordAsignaciones();
                        System.out.println("Leyendo el id de la asignacion a validar");
                        int id_asignacion = in.readInt();
                        Connection con = DriverManager.getConnection(URL, "root", "root");
                        System.out.println("Enviando instruccion de validacion");
                        out.writeBoolean(as.revisarTarea(true, con, id_asignacion));
                        System.out.println("Asignacion revisada");
                        
                        sc.close();
                        System.out.println("FIN");
                        con.close();
                        break;
                    }
                    default:
                        break;
                }

            }

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

    }

}

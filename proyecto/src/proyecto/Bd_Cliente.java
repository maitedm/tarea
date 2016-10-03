/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto;

import java.sql.*;

import javax.swing.JOptionPane;
import javax.swing.JTextField;


public class Bd_Cliente {
    
    Connection conexion = null;
    Statement estado = null;
    
    public void metodoConexion()
    {
        try{
            Class.forName("org.sqlite.JDBC");
            conexion = DriverManager.getConnection("jdbc:sqlite:restorant.sqlite");
            
            DatabaseMetaData metaDatos = conexion.getMetaData();
            ResultSet rs = metaDatos.getTables("restorant", null, "clientes", null);
            
            boolean existe = false;
            while(rs.next()){
                existe = true;
            }
            
            if(existe == false){
                JOptionPane.showMessageDialog(null, "no existe la tabla");
                String sql = "create table clientes(rut text PRIMARY KEY, nombre text, apellido text);";
                      
                estado = conexion.createStatement();
                estado.executeUpdate(sql);
                estado.close();
            }
            rs.close();
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, "error en: " + e);
        }
    }
    
    public int insertar(String Rut, String Nombre, String Apellido){
        try{
            metodoConexion();
            String sql = "insert into clientes(rut, nombre, apellido) "
                    + "values ('"+Rut+"', '"+Nombre+"', '"+Apellido+"')";
            
            estado = conexion.createStatement();
            estado.executeUpdate(sql);
            estado.close();
            conexion.close();
            JOptionPane.showMessageDialog(null, "Datos ingresados con exito");
            return 1;
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, "error en: " + e.getMessage());
            return 0;
        }
    }
    
    public int borrar(String Rut){
        try{
            metodoConexion();
            String sql = "delete from clientes where rut='"+Rut+"' ";
            
            estado = conexion.createStatement();
            estado.executeUpdate(sql);
            estado.close();
            conexion.close();
            JOptionPane.showMessageDialog(null, "Datos eliminados con exito");
            return 1;
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, "error en: " + e.getMessage());
            return 0;
        }
    }
    
    public int actualizar(String Rut, String Nombre, String Apellido){
        try{
            metodoConexion();
            String sql = "update clientes set nombre='"+Nombre+"', apellido='"+Apellido+"' where rut='"+Rut+"' ";
            
            estado = conexion.createStatement();
            estado.executeUpdate(sql);
            estado.close();
            conexion.close();
            JOptionPane.showMessageDialog(null, "Datos actualizados con exito");
            return 1;
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, "error en: " + e.getMessage());
            return 0;
        }
    }
    
    public void mostrar(String Rut, JTextField elRut, JTextField elNombre, JTextField elApellido){
        try{
            metodoConexion();
            String sql = "select * from clientes where rut='"+Rut+"' ";
            
            estado = conexion.createStatement();
            ResultSet resultado = estado.executeQuery(sql);
            
            boolean existe = false;
            while(resultado.next()){
                elRut.setText(resultado.getString("rut"));
                elNombre.setText(resultado.getString("nombre"));
                elApellido.setText(resultado.getString("apellido"));
                existe = true;
            }
            if(existe != true){
                JOptionPane.showMessageDialog(null, "no existe usuario");
            }
            
            estado.close();
            conexion.close();
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, "error en: " + e.getMessage());
        }
    }
}

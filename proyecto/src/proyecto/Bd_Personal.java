/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto;

import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class Bd_Personal {
    
    Connection conexion = null;
    Statement estado = null;
    
    public void metodoConexion()
    {
        try{
            Class.forName("org.sqlite.JDBC");
            conexion = DriverManager.getConnection("jdbc:sqlite:restorant.sqlite");
            
            DatabaseMetaData metaDatos = conexion.getMetaData();
            ResultSet rs = metaDatos.getTables("restorant", null, "empleados", null);
            
            boolean existe = false;
            while(rs.next()){
                existe = true;
            }
            
            if(existe == false){
                JOptionPane.showMessageDialog(null, "no existe la tabla");
                String sql = "create table empleados(rut text PRIMARY KEY, nombre text, apellido text, cargo text);";
                      
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
    
    public int insertar(String Rut, String Nombre, String Apellido, String cargo){
        try{
            metodoConexion();
            String sql = "insert into empleados(rut, nombre, apellido, cargo) "
                    + "values ('"+Rut+"', '"+Nombre+"', '"+Apellido+"', '"+cargo+"')";
            
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
    
    public int eliminar(String Rut){
        try{
            metodoConexion();
            String sql = "delete from empleados where rut='"+Rut+"' ";
            
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
    
    public int actualizar(String Rut, String Nombre, String Apellido, String cargo){
        try{
            metodoConexion();
            String sql = "update empleados set nombre='"+Nombre+"', apellido='"+Apellido+"', cargo='"+cargo+"' where rut='"+Rut+"' ";
            
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
    
    public void mostrar(String Rut, JTextField tuRut, JTextField tuNombre, JTextField tuApellido, JTextField tuCargo){
        try{
            metodoConexion();
            String sql = "select * from empleados where rut='"+Rut+"' ";
            
            estado = conexion.createStatement();
            ResultSet resultado = estado.executeQuery(sql);
            
            boolean existe = false;
            while(resultado.next()){
               tuRut.setText(resultado.getString("rut"));
               tuNombre.setText(resultado.getString("nombre"));
               tuApellido.setText(resultado.getString("apellido"));
               tuCargo.setText(resultado.getString("cargo"));
               existe = true;
            }
            if(existe != true){
                JOptionPane.showMessageDialog(null, "el usuario no existe");
            }
            
            estado.close();
            conexion.close();
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, "error en: " + e.getMessage());
        }
    }
}


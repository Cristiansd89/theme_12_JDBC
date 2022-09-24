package PaqueteConector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.cj.protocol.Resultset;

public class Conector {
	
	String url;
	String usuario;
	String password;
	Connection conexion;
	
	/*
	 * Metodo Constructor por defecto
	 */
	public Conector() {
		url ="jdbc:mysql://localhost:3306/dam2";
		usuario ="superProfe";
		password ="abc123";
		try {
			 conexion = DriverManager.getConnection(url, usuario, password); // Creamos la conexion
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/*
	 * Metodo para Introducir los parametros de uno nuevo
	 */
	public Conector(String url,String usuario,String password) {
		this.url = url;
		this.usuario = usuario;
		this.password = usuario;
		
		try {
			conexion = DriverManager.getConnection(url, usuario, password); // Creamos la conexion
		} catch (SQLException e) {
				System.out.println("Error a l conectar con la bases de datos");
		}
		
	}
	
	public void insertaDatos(String columna1,String columna2,String columna3) {
		try {
			String query = "INSERT INTO alumnos  VALUES(NULL,'"+columna1+"','"+columna2+"', '"+columna3+"')";

			PreparedStatement sql = conexion.prepareStatement(query); //  Statement objeto para el envío de Instrucciones SQL a la base de datos. 

			sql.executeUpdate();
			
			
			sql.close();
			conexion.close();
			
			
		} catch (SQLException e) {
			System.out.println("Error a la hora de insertar datos");
			e.printStackTrace();
		} 

	}
	
	public void modificarDatos(String SetNombre, String DatoMod, String WhereColumna, String datoColumna) {
		String query = "UPDATE alumnos SET "+SetNombre+"='"+DatoMod+"' WHERE " +WhereColumna+"='"+datoColumna+"'";
		
		PreparedStatement sql; //  Statement objeto para el envío de Instrucciones SQL a la base de datos. 
		try {
			sql = conexion.prepareStatement(query);
			
			sql.executeUpdate(); // Carga la sentencia
			
			sql.close();
			conexion.close();
		} catch (SQLException e) {
			System.out.println("Error Modificando los campos");
		} 

	}
	
	
	public void eliminarDatos(String nomTabla,String condColumna, String condDatos ) {
		
		//DELETE FROM alumnos WHERE nombre='Rodrigo';
		String query = "DELETE FROM "+nomTabla+" WHERE "+condColumna+"= '"+condDatos+"'";
		
		
		
		try {
			PreparedStatement sql = conexion.prepareStatement(query);
			
			sql.executeUpdate();
			
			
			sql.close();
			conexion.close();
			
			
		}catch (SQLException e) {
			System.out.println("Error en el metodo eliminar datos ");
		}
		
	}
	
	
	public void mostarDatos(String nomTabla) {
		
		String query = "select * from "+nomTabla;
		
		
		
		try {
			PreparedStatement sql = conexion.prepareStatement(query);
			
			ResultSet res = sql.executeQuery(); 
			
			// almacena el resultado dentro de la variable res  // Para leer tablas se necesita executeQuery()
			
			while(res.next()) {											  //Recorremos  el resultado de la query 
				System.out.println(res.getString(2)+" " +res.getString(3)+" " +res.getString(4));//
				
			}
			res.first();//res.beforeFirst();
			sql.close();
			conexion.close();
			
		}catch (SQLException e) {
			System.out.println(" ");
			System.out.println("Fin de la Tabla ");
		}
		
		
		
		
	}

}

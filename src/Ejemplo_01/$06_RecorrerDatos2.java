package Ejemplo_01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class $06_RecorrerDatos2 {

	public static void main(String[] args) {
		String url = "jdbc:mysql://localhost:3306/dam2"; 
		String usuario = "superProfe";
		String password = "abc123";
		
		String nombre;
		String apellido1;
		String apellido2;
		
		
		try {
			
			//Creamos la conexion con la BBDD
			Connection conn = DriverManager.getConnection(url, usuario, password);
			//Trabajamos con la BBDD :Para ello, Creamos una query
			
			
			//Valores por defecto Statement sql = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			//TYPE_SCROLL_INSENSITIVE Sirve para recorrer la tabla
			//CONCUR_UPDATABLE para que todos lo cambios que se hacen reflejen en la base de datos
			Statement sql = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			
		
			//Alamacenanos  el resultado de la query en  una variable a la que hemos llamado "resultado
			ResultSet res = sql.executeQuery("select * from alumnos"); // almacenaria el resultado dentro de la variable res
			//Recorremos  el resultado de la query 
			while(res.next()) {
				System.out.println(res.getString(2)+" "+ res.getString(3)+" " +res.getString(4));
			}
				System.out.println("****** Ahora voy a  cambiar el primero*********");
				
				//Le decimos que se coloce en la primera posicion
				res.first();
				//Puedes poner el nombre de la columna o el numero que le corresponderia a la columna
				res.updateString(3, "Perez");
				res.updateString(4, "Martinez");
				//para cargar las modificaciones sin update no se actualiza
				res.updateRow();
				
				
			
			//Volvemos a colocarnos en la posicion inicial para poder a leerle la base de datos
			res.beforeFirst();
			while(res.next()) {
				System.out.println(res.getString(2)+" "+ res.getString(3)+" " +res.getString(4));
				
			}
			//CErramos la conexion
			conn.close();

			
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		

	}
		
	}



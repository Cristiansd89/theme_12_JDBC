package Ejemplo_01;

import java.util.Scanner;
import java.sql.*;

public class $02_InsertSelect {

	public static void main(String[] args) {
		String url = "jdbc:mysql://localhost:3306/dam2"; 
		String usuario = "profe";
		String password = "abc123";
		
		String nombre;
		String apellido1;
		String apellido2;
		
		
		try {
			Connection conn = DriverManager.getConnection(url, usuario, password);
			//Trabajamos con la BBDD :Para ello, Creamos una query
			Statement sql = conn.createStatement();
			
			//*****************  (Otra Manera de introducir los datos en una BBDD
			String query = "INSERT INTO alumnos VALUES (NULL,?,?,?)";
			String apellido = "Diaz";
			PreparedStatement sql2 = conn.prepareStatement(query);
			//Con esta sentencia le estamos diciendo que introduzca en la primera interrogacion la palabra entre comillada
			///Empieza a contar a partir el primer signo de interrogacion y se comienza por el 1
			sql2.setString(1,"Rodrigo");
			sql2.setString(2, apellido);
			sql2.setString(3, "de Vivar");
			sql2.executeUpdate();
			
			
			
			//Alamacenanos  el resultado de la query en  una variable a la que hemos llamado "resultado
			ResultSet res = sql.executeQuery("select * from alumnos"); // almacenaria el resultado dentro de la variable res
			//Recorremos  el resultado de la query 
			while(res.next()) {
				System.out.println(res.getString(2)+" "+ res.getString(3)+" " +res.getString(4));//
			}
			
			//CErramos la conexion
			conn.close();

			
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		

	}
		

	}




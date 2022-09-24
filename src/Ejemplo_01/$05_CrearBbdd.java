package Ejemplo_01;

import java.sql.*;
import java.util.Scanner;



public class $05_CrearBbdd {
	
	

	public static void main(String[] args) {
		String url = "jdbc:mysql://localhost:3306/"; 
		//String url = " jdbc:oracle:thin:@localhost:1521:XE"; (Base de datos de Oracle)
		String usuario ="superProfe";
		String password ="abc123";	
		
		String nombre;
		String apellido1;
		String apellido2;
		
		
		Scanner ent = new Scanner(System.in);
		
		try {
			//EStablecemos la conexion con la BBDD
			Connection conexion = DriverManager.getConnection(url, usuario, password);
			Statement sql = conexion.createStatement();
			
			//Para poder Crear la Basee de datos
			String crea = "CREATE DATABASE IF NOT EXISTS prueba";
			sql.executeUpdate(crea);
			
			String usar = "USE prueba";
			sql.executeUpdate(usar);
			
			String crearTabla = "CREATE TABLE  IF NOT EXISTS alumnos (id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,nombre VARCHAR(50),apellido1 VARCHAR(50),apellido2 VARCHAR(50));";
			sql.executeUpdate(crearTabla);
			
			
			System.out.println("Introduce Los datos del alumno :");
			System.out.println("Nombre: ");
			nombre = ent.nextLine();
			
			
			System.out.println("Primer apellido: ");
			apellido1 = ent.nextLine();
			
			
			System.out.println("Segundo apellido");
			apellido2 = ent.nextLine();
		
		
			
			String insertar = "INSERT INTO alumnos  VALUES(NULL,'"+nombre+"','"+apellido1+"', '"+apellido2+"')";
			sql.executeUpdate(insertar);
			
			ResultSet res = sql.executeQuery("select * from alumnos");
			while(res.next()) {
				System.out.println(res.getString(3)+" " +res.getString(4));//
			}
			
			//CErramos la conexion
			conexion.close();
			
			
			
		}catch(SQLException e) {
			e.printStackTrace();		}
	}

	

}

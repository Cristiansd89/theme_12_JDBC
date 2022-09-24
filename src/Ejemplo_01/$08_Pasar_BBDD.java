package Ejemplo_01;

import java.sql.*;
import java.util.Scanner;

public class $08_Pasar_BBDD {
	
	
	
		
	public static void main(String[] args) {
		
		String url = "jdbc:mysql://localhost:3306/"; 
		String usuario = "superProfe";
		String password = "abc123";
		
		String usar;
		String UsarDes;
		String nombre;
		String nombreDes;
		String query;
		String CrearD;
		String nombreVet;
		
		
		try {
			
			//Creamos la conecion de la primera Tabla
			Connection conexion_Origen = DriverManager.getConnection(url, usuario, password);
			Statement sql_Origen = conexion_Origen.createStatement();
			
			
			
			///Conexion con la Segunda BBDD;
			Connection conexion_Destino = DriverManager.getConnection(url, usuario, password);
			PreparedStatement sql_Destino = conexion_Destino.prepareStatement("INSERT INTO Traza_Ministerio VALUES (NOT NULL,?,?,?,?)");
			
			
			
			//Solicitamos el nombre de la base de datos que queremos utilizar
			nombre = pedirNombre();
			usar = "USE "+nombre;
			sql_Origen.executeUpdate(usar);
			
			nombreDes = pedirNombre();
			nombreVet = pedirVeterinario();
			
			//Creamos la segunda BBDD si no existiera

			CrearD = "CREATE DATABASE IF NOT EXISTS "+nombreDes;
			sql_Destino.executeUpdate(CrearD);
			
			UsarDes = "USE "+nombreDes;
			sql_Destino.executeUpdate(UsarDes);
			String crearTabla = "CREATE TABLE  IF NOT EXISTS Traza_Ministerio(id INT NOT NULL PRIMARY KEY AUTO_INCREMENT, "+nombreVet+" VARCHAR(15), Origen VARCHAR(15), reses numeric(3), Destino VARCHAR(15))";
			sql_Destino.executeUpdate(crearTabla);
			
		
			
			
			ResultSet res_Origen= sql_Origen.executeQuery("select * from Traza_Aragon");
			
			while(res_Origen.next()) {
				System.out.println(res_Origen.getString(1) +" "+res_Origen.getString(2) +" "+res_Origen.getString(3)+" " +res_Origen.getString(4));	
				sql_Destino.setString(1, nombreVet);
				sql_Destino.setString(2, res_Origen.getString(1));
				sql_Destino.setString(3, res_Origen.getString(3));
				sql_Destino.setString(4, res_Origen.getString(2));
				sql_Destino.executeUpdate();
			
			}
			
			conexion_Origen.close();
			conexion_Destino.close();
			
		}catch(SQLException E) {
			E.printStackTrace();
		}
		
		}
		
	
	
	public static String pedirNombre() {
		Scanner ent = new Scanner(System.in);
		System.out.println("Nombre de la Base de Datos :");
		String nombre = ent.nextLine();
		return nombre;
		
	}
	public static String pedirVeterinario() {
		Scanner ent = new Scanner(System.in);
		System.out.println("Nombre de veterinario :");
		String nombreVet = ent.nextLine();
		return nombreVet;
	}
	
	


}

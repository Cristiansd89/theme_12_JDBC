package Pre_Examen;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.Scanner;

public class Practica_1 {

	public static void main(String[] args) {
		Scanner ent = new Scanner(System.in);
		
		int opcion;
		boolean salir = false;

		String url = "jdbc:mysql://localhost:3306/world"; 
		String usuario = "trivial";
		String password = "abc123";
		
		Connection conexion;
		try {
			
			
			conexion = DriverManager.getConnection(url, usuario, password);
			
			do {
				System.out.println("1- Mostrar Alumnos");
				System.out.println("2-Insertar Nuevo Alumno");
				System.out.println("3 - para Salir");
				opcion = ent.nextInt();
				
					switch (opcion) {
					case 1:
						mostrarAlumnos(conexion);
						break;
						
					case 2:
						insertarAlumno(conexion);
						break;
		
					case 3 :
						salir = true;
						break;
					}
			}while(!salir);
			
			
			
			
			
			
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}  // Flujo de conexion
	

	}
	
	
	public static void mostrarAlumnos(Connection conexion) {
		

		try {
			
			String query = "SELECT * FROM alumnos";
			
			Statement sql = conexion.createStatement();
			
			ResultSet res = sql.executeQuery(query);
			
			
			while(res.next()) {
				System.out.println(" "+res.getString(2)+" "+res.getString(3)+"  "+res.getString(4));
			}
			
		}catch(SQLException e) {
			
		}
	
	}
	
	public static void insertarAlumno(Connection conexion) {
		Scanner ent = new Scanner (System.in);

		String nombre;
		String apellido1;
		String apellido2;
		
		System.out.println("Introduce el nombre :");
		nombre = ent.next();
		
		System.out.println("Introduce primer apellido :");
		apellido1 = ent.next();
		
		
		System.out.println("Introduce segundo apellido :");
		apellido2 = ent.next();
		
		
		
		String query = "INSERT INTO alumnos VALUES (NULL,? ,? ,?)";
		try {
			PreparedStatement sql2 = conexion.prepareStatement(query);
			
			
			sql2.setString(1, nombre);
			sql2.setString(2, apellido1);
			sql2.setString(3, apellido2);
			
			
			sql2.executeUpdate(); // Para insertar no hace falta el resulset ese metodo solo es para poder mostrar
			
			
			
			
			
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
				
		
	
		
		
		
		
		
		
		
		
	}
}

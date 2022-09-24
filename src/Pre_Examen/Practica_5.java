package Pre_Examen;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Practica_5 {

	public static void main(String[] args) {

		Scanner ent = new Scanner(System.in);
		
		String url = "jdbc:mysql://localhost:3306/cursos"; 
		String usuario = "trivial";
		String password = "abc123";
		
		boolean salir = false;
		
		Connection conexion;
		String id;

		try {
			conexion = DriverManager.getConnection(url, usuario, password);
			
			
			do {
				int opcion = preguntarTipo();
				switch (opcion) {
				case 1:
					 id	= mostrarDirector(conexion);
					mostrarPersonas(id, conexion);
					break;
					
				case 2: 
					 id	= mostrarProfesores(conexion);
					mostrarPersonas(id, conexion);
					break;
					
				case 3: 
					id	= mostrarAlumnos(conexion);
					mostrarPersonas(id, conexion);
					break;
				case 4:
					salir = true;
		
				}
				
			}while(!salir);
			
			
			
			
		}catch (SQLException e) {
			// TODO: handle exception
		}
		
		
		

	}
	
	public static int preguntarTipo() {
		
		Scanner ent = new Scanner(System.in);
		
		System.out.println("Que quieres ver :");
		System.out.println("1- Directores");
		System.out.println("2- Profesores");
		System.out.println("3- Alumnos ");
		System.out.println("4- para Salir");
		System.out.println(" ");
		
		int opcion = ent.nextInt();
		
		
		return opcion;
		
		
		
		
	}
	
	
	public static String mostrarProfesores(Connection conexion) {
		
		String query = "SELECT id FROM rol where id='3'";
		String profesores = null;
		
		try {
			
			PreparedStatement sql = conexion.prepareStatement(query,ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			ResultSet res = sql.executeQuery();
			
			while(res.next()) {
				
			 profesores = res.getString(1);
				
				
			}
			
			
		} catch (SQLException e) {
			System.out.println("Ha ocurrido un error ");
			System.out.println("Codigo del Error : "+ e.getErrorCode());
			System.out.println("Mensaje :  "  +e.getMessage());
			System.out.println("Estado : " + e.getSQLState());
		}
		return profesores;
		
		
	}
	
	public static String mostrarAlumnos(Connection conexion) {
			
			String query = "SELECT id FROM rol where id='4'";
			String alumno = null;
			
			try {
				
				PreparedStatement sql1 = conexion.prepareStatement(query,ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
				
				ResultSet res = sql1.executeQuery();
				
				while(res.next()) {
					
					alumno = res.getString(1);
					
					
				}
		
			} catch (SQLException e) {
				System.out.println("Ha ocurrido un error ");
				System.out.println("Codigo del Error : "+ e.getErrorCode());
				System.out.println("Mensaje :  "  +e.getMessage());
				System.out.println("Estado : " + e.getSQLState());
			}
			return alumno;
			
			
		}

	public static String mostrarDirector(Connection conexion) {
		
		String query = "SELECT id FROM rol where id='2'";
		String director = null;
		
		try {
			
			PreparedStatement sql2 = conexion.prepareStatement(query,ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			ResultSet res2 = sql2.executeQuery();
			
			while(res2.next()) {
				
				director = res2.getString(1);
				
				
			}
			
			
		} catch (SQLException e) {
			System.out.println("Ha ocurrido un error ");
			System.out.println("Codigo del Error : "+ e.getErrorCode());
			System.out.println("Mensaje :  "  +e.getMessage());
			System.out.println("Estado : " + e.getSQLState());
		
		}
		return director;
		
		
	}
	
	
	public static void mostrarPersonas(String tipo ,Connection conexion ) {
		
		String query = "SELECT nombre, apellidos from persona where rol =?";
		
		try {
			PreparedStatement sql3 = conexion.prepareStatement(query,ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			sql3.setString(1, tipo);
			
			ResultSet res3 = sql3.executeQuery();
			
			
			while(res3.next()) {
				System.out.println("Nombre :"+res3.getString(1)+ " " +res3.getString(2));
			}
			System.out.println("  ");
			
		} catch (SQLException e) {
			System.out.println("Ha ocurrido un error ");
			System.out.println("Codigo del Error : "+ e.getErrorCode());
			System.out.println("Mensaje :  "  +e.getMessage());
			System.out.println("Estado : " + e.getSQLState());
		}
		
		
		
		
	}
	
	
	

}

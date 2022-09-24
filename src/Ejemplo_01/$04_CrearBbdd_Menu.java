package Ejemplo_01;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class $04_CrearBbdd_Menu {

	public static void main(String[] args) {
		Scanner ent = new Scanner(System.in);
		
		String url = "jdbc:mysql://localhost:3306/"; 
		String usuario = "superProfe";
		String password = "abc123";
		
		String nombre;
		String apellido1;
		String apellido2;
		
		int opcion;
		boolean salir;
		
		try {
			do {
				opcion = Menu();
				salir =(opcion == 4);
				
				
				Connection conexion = DriverManager.getConnection(url, usuario, password);
				Statement sql = conexion.createStatement();
			
				switch(opcion) {
				case 1 :
					String crea = "CREATE DATABASE IF NOT EXISTS prueba";
					sql.executeUpdate(crea);
					String usar = "USE prueba";
					sql.executeUpdate(usar);
					break;
					
				case 2 :
					System.out.println("Introduce Los datos del alumno :");
					System.out.println("Nombre: ");
					nombre = ent.nextLine();
					
					
					System.out.println("Primer apellido: ");
					apellido1 = ent.nextLine();
					
					
					System.out.println("Segundo apellido");
					apellido2 = ent.nextLine();
					
					usar = "USE prueba";
					sql.executeUpdate(usar);
					
					
					
					String insertar = "INSERT INTO alumnos  VALUES(NULL,'"+nombre+"','"+apellido1+"', '"+apellido2+"')";
					sql.executeUpdate(insertar);
					System.out.println(" ");
						break;
						
				case 3 :
					usar = "USE prueba";
					sql.executeUpdate(usar);
					ResultSet res = sql.executeQuery("select * from alumnos");
					while(res.next()) {
						System.out.println(res.getString(2) +" "+res.getString(3)+" " +res.getString(4));
						conexion.close();
					}
						break;
				}			
				
			}while(!salir);
				
		}catch(SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Fin del programa");
		
		
		

}
	public static int Menu() {
		int opcion;
		
		boolean opValida;
		Scanner ent = new Scanner(System.in);
		
		System.out.println(" ");
		System.out.println("Menu de operaciones ");
		System.out.println("1- Crea base de datos ");
		System.out.println("2- Añadir alumno  ");
		System.out.println("3- Mostrar Alumnos ");
		System.out.println("4- Para terminar");
		
		
		do {
			opcion = ent.nextInt();
			opValida = (opcion >=1 && opcion <=4);
			if(!opValida) {
				System.out.println("La opcion es entre 1 y 4");
			}
			
		}while(!opValida);
		
		return opcion;
		
	}

}

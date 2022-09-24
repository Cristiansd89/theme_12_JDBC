package Ejemplo_01;

import java.io.BufferedReader;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class LeerFichero_PasarBBDD {

	public static void main(String[] args){
		
		
		String url = "jdbc:mysql://localhost:3306/dam2"; 
		String usuario ="profe";
		String password ="abc123";
		
		Scanner ent = new Scanner(System.in);
		String stringArray [];
		/*
		
		String nombre;
		System.out.println("Nombre del archivo : ");
		nombre = ent.nextLine();*/
		String texto = "";
		
		String separador =",";
		try {
			
			Connection conn = DriverManager.getConnection(url, usuario, password);
			Statement sql = conn.createStatement();
			
			
			File archivo = new File("/home/cristian/Escritorio/DAM2/ADAT/nombres.txt");
			BufferedReader leer = new BufferedReader(new FileReader(archivo));
			stringArray = new String [3];
			
			String use = "use Alumnos";
			sql.executeUpdate(use);
			
			
			String insertar = "INSERT INTO alumnos VALUES (NOT NULL,?,?,?,?)";
			sql.executeUpdate(insertar);
			
			
			String linea = leer.readLine();
			
			while (linea != null) {
				
				texto = texto + linea +"\n";
				linea = leer.readLine();
				//Vale con esta sentencia esta separada la primera linea
				//del texto
				String [] TextoArra = texto.split(separador);
				
				
				
			}
			leer.close();
		}catch(IOException fallo) {
			System.out.println("No se pudo abrir el Archivo ");
			System.out.println(" ");
			System.out.println(fallo.getMessage());
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		
		System.out.println(texto);
		

	}

}







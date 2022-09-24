package Ejemplo_01;

import java.sql.*;

import java.util.Scanner;

public class Metadatos {

	public static void main(String[] args) {
		// Paramettos de conexion con la Base de DAtos
		String url = "jdbc:mysql://localhost:3306/"; 
		String usuario = "superProfe";
		String password = "abc123";
	
		Scanner ent = new Scanner(System.in);
		System.out.println("Que Bases de datos quieres Consultar ?");
		String BBDD = ent.nextLine();
		
		
		
		
		try {
			
			//Creamos la conexion con la BBDD
			Connection conexion = DriverManager.getConnection(url, usuario, password);
			//Trabajamos con la BBDD :Para ello, Creamos una query
			
			
			DatabaseMetaData dmbd = conexion.getMetaData();
			
			System.out.println( "Gestor : " +dmbd.getDatabaseProductName());
			System.out.println( "Driver : " +dmbd.getDriverName());
			System.out.println( "URL : " +dmbd.getURL());
			System.out.println( "Usuario : " +dmbd.getUserName());
			
			
			
			ResultSet exe = dmbd.getTables(BBDD, null, null,null);// Le indicamos que BBDD queremos ver
			
			
			///ResultSet pk = dmbd.getPrimaryKeys(null, null, nombre);
			while(exe.next()) {
				String tipo = exe.getNString("TABLE_TYPE"); // Siempre es igual un 4 para el tipo 
				String nombre = exe.getString("TABLE_NAME"); // 3 para el nombre
				System.out.println( "Tipo " +tipo+ " \nNombre :"  +nombre);
			
				ResultSet cols = dmbd.getColumns(null, null, nombre, null); // Para obtener la informacion de la columnas
				
				while(cols.next()){
					System.out.println("Nombre de la columna " +cols.getString( "COLUMN_NAME"));
					System.out.println("Tipo de Dato  -"+cols.getString( "TYPE_NAME"));
					System.out.println("Tamaño :("+cols.getString( "COLUMN_SIZE")+")");
					System.out.println("Puede ser nulo? " +cols.getString( "IS_NULLABLE"));
					System.out.println("");
				}
			
			}
			
			
			
					
			//CErramos la conexion
			conexion.close();

			
			
		}catch(SQLException e) {
			System.out.println("Ha ocurrido un Error");
			System.out.println("Cogido del Error :" +e.getErrorCode()); // Muestra el codigo de Error
			System.out.println("Mensaje :  " + e.getMessage());		   	// Motivo del Error
			System.out.println("Estado : " +e.getSQLState());		  	// Este cogido hace referencia al estado de la base de datos(Otro Codigo)
		}
		

	}

	}



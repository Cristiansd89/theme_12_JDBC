package Practica_03;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import com.mysql.cj.protocol.Resultset;
import com.mysql.cj.xdevapi.Result;

public class CiudadesPais {

	public static void main(String[] args) {
		
		Scanner ent = new Scanner(System.in);
		
		String url = "jdbc:mysql://localhost:3306/world"; 
		String usuario = "trivial";
		String password = "abc123";
		
		
		try {
			Connection conexion = DriverManager.getConnection(url, usuario, password); // Flujo de conexion
			
			System.out.println("Conexion establecida");
			
			System.out.println(" Introduce una ciudad :");
			String ciudad = ent.nextLine();
			
			String query ="SELECT ID, CountryCode FROM city WHERE Name = ?";
			PreparedStatement sql = conexion.prepareStatement(query,ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			sql.setString(1,ciudad);
			ResultSet res = sql.executeQuery();
			
			res.last(); // Se coloca el puntero en la ultima posicion
			int n = res.getRow(); // Devuelve el numero de filas leidas con la query
			
			if(n == 0) {
				System.out.println("La ciudad de " +ciudad+ " No existe");
			}else if(n == 1){
				System.out.println("Hay una ciudad llamada " +ciudad );
			}else {
				System.out.println("Hay varias ciudades llamadas " +ciudad);
			}
			
			if(n!=0) {
				res.beforeFirst(); //Colocamos el puntero al principio para poder leer la query
				while(res.next()) {
					
					String query2 = "SELECT Code, Name from country where Code = ?";
					PreparedStatement sql2 = conexion.prepareStatement(query2);
					sql2.setString(1, res.getString("CountryCode")); // Introduce en el segundo query lo que hay en la columna del resultado enterior
					ResultSet res2 = sql2.executeQuery(); // Esto carga los resultados
					
					res2.next();
					System.out.println(" La ciudad de " +ciudad+ " esta en el pais de " +res2.getString("Name")); // De volvemos el valor de la columna que queremos
				
					
					String query3 = "SELECT Name from city where CountryCode = ?";
					PreparedStatement sql3 = conexion.prepareStatement(query3);
					sql3.setString(1,res.getString("CountryCode")); // pasamo el code de la primera tabla
					ResultSet res3 = sql3.executeQuery(); // Esto carga los resultado					
					
					System.out.println(" las Ciudades de "+res2.getString("Name")+" son :");
					
					while(res3.next()) {
						System.out.println(" - " +res3.getString("Name")); // Devuelve el resultado de  el tercer query
					}
				}
				
	
				
			}
			
			
			///res.close();
			conexion.close();
			
			
			
		}catch (SQLException e) {
			System.out.println("Ha ocurrido un error ");
			System.out.println("Codigo del Error : "+ e.getErrorCode());
			System.out.println("Mensaje :  "  +e.getMessage());
			System.out.println("Estado : " + e.getSQLState());
		}
	}

}

package Pre_Examen;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Practica_2 {
	Scanner ent = new Scanner (System.in);

	public static void main(String[] args) {
		
		String url = "jdbc:mysql://localhost:3306/world"; 
		String usuario = "trivial";
		String password = "abc123";
		Connection conexion;
		
		try {
			conexion = DriverManager.getConnection(url, usuario, password);
			String ciudad =   pedirCiudad();
			String codigoCap =  comprobarCiudad(ciudad, conexion);
			comprobarCapital(codigoCap, conexion);
			mostrarLenguaje(codigoCap, conexion);
			
		} catch (SQLException e) {

			e.printStackTrace();
		}
		
		
	}
	
	
	public static String pedirCiudad() {
		Scanner ent = new Scanner (System.in);
		
		System.out.println("Introduce una ciudad :");
		
		String ciudad = ent.next();
		
		return ciudad;
		
	}
	
	public static String comprobarCiudad(String ciudad,Connection conexion) {
		
		String query = "SELECT * FROM city WHERE Name =? ";
		String codigoCapital = null;
		PreparedStatement sql;
		
		try {
			sql = conexion.prepareStatement(query,ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			sql.setString(1, ciudad);
			ResultSet res = sql.executeQuery();
			
			res.last(); // Se coloca el puntero en la ultima posicion
			int n = res.getRow(); // Devuelve el numero de filas leidas con la query

			if(n != 0) {
				System.out.println("La ciudad de " +ciudad+  " Existe");
				
				res.beforeFirst();
				while(res.next()) {
					
					codigoCapital = res.getString(3);
					System.out.println(" Codigo : "  +codigoCapital);
				}
				
			}else {
				System.out.println("La ciudad de " +ciudad+ " No existe");
			}
			  
			
			
			
		} catch (SQLException e) {
			
		}
		return  codigoCapital;
		
	}
	
	
	public  static void comprobarCapital(String codigoCapital,Connection conexion) {
		
		String query2 = "SELECT * FROM country where Code = ? and Capital !='NULL'";
		
		try {
			PreparedStatement sql2 =  conexion.prepareStatement(query2,ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			sql2.setString(1, codigoCapital);
			ResultSet res2 = sql2.executeQuery();
			
			res2.last(); // Se coloca el puntero en la ultima posicion
			int n = res2.getRow(); // Devuelve el numero de filas leidas con la query
			
			if(n != 0) {
				res2.beforeFirst();
				while(res2.next()) {
					System.out.println("La ciudad es  capital " +res2.getString(2));
				}
			}else {
				System.out.println("No es una capital");
			}
			
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	
	public static void mostrarLenguaje(String codigoCapital,Connection conexion) {
		
		String query3  = "SELECT Language FROM countrylanguage where CountryCode = ?";
		try {
			PreparedStatement sql3 =  conexion.prepareStatement(query3,ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			sql3.setString(1, codigoCapital);
			
			ResultSet res3 = sql3.executeQuery();
			
	
			res3.last(); // Se coloca el puntero en la ultima posicion
			int n = res3.getRow(); // Devuelve el numero de filas leidas con la query
	
			if(n != 0) {
				res3.beforeFirst();
				while(res3.next()) {
					System.out.println(" Idiomas - " +res3.getString(1));
				}
			}else {
				System.out.println("No se conocen");
			}
		} catch (SQLException e) {
			
		}
	
	}

}

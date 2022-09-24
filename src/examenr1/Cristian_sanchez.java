package examenr1;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Pre_recu_04.Pre_recu_04.Conexion;

public class Cristian_sanchez {

	private static Connection conector;
	
	public static void main(String[] args) {
		verIdiomas("Spain");
		
		verIdiomas("España");
		
		System.out.println("----------Primer Ejercicio");
		grabarEstadisticas("Spain", 047350000, 1281484640);
		grabarEstadisticas("España", 047350000, 1281484640);
		
		System.out.println("----------Segundo Ejercicio");

	}
	
	
	public static void verIdiomas(String pais) {
		conector = Conexion.conectar();
		
		String query = "SELECT t1.name,t3.language "
				+ "from countries t1 "
				+ "join country_languages t2 on t1.country_id = t2.country_id "
				+ "join languages t3 on t2.language_id = t3.language_id "
				+ "where t1.name =?";
		
		
		try {
			PreparedStatement sql = conector.prepareStatement(query,ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			sql.setString(1, pais);
			
			ResultSet res = sql.executeQuery();
			res.last();
			
			int filas = res.getRow();
			String paisOf = res.getString(1);
			
			
			
			
			if (filas == 0) {
				System.out.println("Ese Pais no existe");
			}else {
				res.beforeFirst();
				System.out.println(" Pais :"  +paisOf);
				while(res.next()) {
					
					System.out.println(" Lenguas :"  +res.getString(2));
					
					
				}
				
				String query2 = "SELECT t1.name,t3.language "
						+ "from countries t1 "
						+ "join country_languages t2 on t1.country_id = t2.country_id "
						+ "join languages t3 on t2.language_id = t3.language_id "
						+ "where t1.name =? and t2.official =1 ";
				
				
				PreparedStatement sql2 = conector.prepareStatement(query2,ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
				sql2.setString(1, pais);
				
				ResultSet res2 = sql2.executeQuery();
				res2.last();
				
				String lengOf = res2.getString(1);
				
				System.out.println(" Oficial " +lengOf);
				
				
			}
			
			
			
			
		} catch (SQLException e) {
			System.out.println("Ha ocurrido un error ");
			System.out.println("Codigo del Error : "+ e.getErrorCode());
			System.out.println("Mensaje :  "  +e.getMessage());
			System.out.println("Estado : " + e.getSQLState());
		}
		
		
		
		
	}
	
	public static void grabarEstadisticas(String pais, int cif_pob, int gdp) {
		conector = Conexion.conectar();
		
		
		
		
		String query = "SELECT  t1.country_id,t2.population, t2.gdp "
				+ "from countries t1 "
				+ "join country_stats t2 on t1.country_id = t2.country_id "
				+ "Where t1.name =? ";
		
		PreparedStatement sql;
		try {
			sql = conector.prepareStatement(query,ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			sql.setString(1, pais);
			
			ResultSet res = sql.executeQuery();
			res.last();
			
			int filas = res.getRow();
			
			

			if (filas == 0) {
				System.out.println("Ese Pais no existe");
				
			}else {
				int cod_pais = res.getInt(1);
				query = "INSERT INTO country_stats (country_id,year,population,gdp) VALUES (?,2022,?,?) ";
				
				PreparedStatement sql2 = conector.prepareStatement(query,ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
				sql2.setInt(1, cod_pais);
				sql2.setInt(2, gdp);
				sql2.setInt(3, gdp);
				
				sql2.executeUpdate();
			}
		} catch (SQLException e) {
			System.out.println("Ha ocurrido un error ");
			System.out.println("Codigo del Error : "+ e.getErrorCode());
			System.out.println("Mensaje :  "  +e.getMessage());
			System.out.println("Estado : " + e.getSQLState());
		}
		
		
	}
	
	public static void borrarIdioma ( String pais, String lengua) {
		conector = Conexion.conectar();
		
		
		String query="S";
		
		PreparedStatement sql;
		try {
			sql = conector.prepareStatement(query,ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			sql.setString(1, pais);
			
			ResultSet res = sql.executeQuery();
			res.last();
			int filas = res.getRow();
			
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
	

	public static class Conexion {
			
			/*
			 * Atributos
			 */
			private static String url;
			private static String usuario;
			private static String password;
			private static Connection conect = null;
			
			public static Connection conectar() {
				/*
				 * Inicializamos las variableas
				 */
				
				try {
					url = "jdbc:mysql://localhost:3306/examenr1"; 
					usuario = "examenr1";
					password = "Abcd1234+";
					conect = DriverManager.getConnection(url,usuario,password);
					
					System.out.println("Conexion Establecida");
					
					
					
				} catch (SQLException e) {
					System.err.println("Error en la conexion "+ e.getMessage());
					e.printStackTrace();
				}
				
				 return conect;
			}
				
				
		}

}

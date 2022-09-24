package Practica_02_JoseMaria;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Ciudades {

	public static void main(String[] args) {
		
			Scanner ent = new Scanner(System.in);
			
			String url = "jdbc:mysql://localhost:3306/world"; 
			String usuario = "trivial";
			String password = "abc123";
			
			
			try {
				
				Connection conexion = DriverManager.getConnection(url, usuario, password);  // Flujo de conexion
				System.out.println("conexion establecida ");
				
				String ciudad = "Madrid";
			
				String query=" SELECT ID, CountryCode from city where  Name = ?";
				

				/***
				 * Primer Bloque de codigo
				 */
				PreparedStatement sql = conexion.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
				sql.setString(1, ciudad);
				ResultSet res = sql.executeQuery();
				
				res.last(); // Colocamos el cursos al final de la tabla de la sentencia
				int n = res.getRow(); // Cuenta el numero de filas que ha leido  con la condicion
				if(n==0) {
					System.out.println("No hay ninguna ciudad con ese nombre ");
				}else if(n == 1) {
					System.out.println(" hay 1 ciudad con ese nombre");
				}else {
					System.out.println("Hay" + n +" ciudades con ese nombre");
				}
				/***
				 * Segundo Bloque de codigo
				 */
				
				if(n!=0) {				   // Primera sentencia que comprueba si esta la primera condicion se da
					res.beforeFirst(); // El puntero se coloca antes de la primera fila
					while(res.next()) {
						
						String query2 ="SELECT Name, Capital FROM country where Code = ?";
						PreparedStatement sql2 = conexion.prepareStatement(query2);
						sql2.setString(1, res.getString("CountryCode"));  /// Se utiliza como modificador de la query 
						//el primer numero hace referencia a que interrogacion buscamos 
						//El segundo parameto a lo que queres hacer referencia
						
						ResultSet res2 = sql2.executeQuery();
						res2.next();
						System.out.println("\n"+ciudad+ "  pertenece a "  +res2.getString("Name"));
						
						if(res.getInt("ID") == res2.getInt("Capital")) {
							System.out.println("Es la capital de ese pais");
						}
						
						String query3 ="SELECT Language  FROM countrylanguage where CountryCode = ?";
						PreparedStatement sql3 = conexion.prepareStatement(query3);
						sql3.setString(1, res.getString("CountryCode"));
						ResultSet res3 = sql3.executeQuery();
						
						System.out.println("En " +res2.getString("Name") +" Se habla :");
						
						
						while(res3.next()){
							System.out.println(" - " + res3.getString("Language"));
						}
						
					}
						
				}
				
				conexion.close();
			}catch (SQLException e) {
				System.out.println("Ha ocurrido un error ");
				System.out.println("Codigo del Error : "+ e.getErrorCode());
				System.out.println("Mensaje :  "  +e.getMessage());
				System.out.println("Estado : " + e.getSQLState());
			}
			

		}

		
	}



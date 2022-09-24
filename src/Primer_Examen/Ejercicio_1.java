package Primer_Examen;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import com.mysql.cj.protocol.Resultset;

public class Ejercicio_1 {

	public static void main(String[] args) {
		
		String url = "jdbc:mysql://localhost:3306/examen"; 
		String usuario = "examen";
		String password = "Abcd1234+";
		Connection conexion;

		try {
			conexion = DriverManager.getConnection(url, usuario, password);
			
			//datos_empleado(conexion,205);
			//borrar_empleado(conexion, 20);
			
			transladar_departamento(conexion, 4);
			
			
		}catch(SQLException e) {
			
		}
	}
	
	
	public static String pedirDatos() {
		Scanner ent = new Scanner(System.in);
		String datos = ent.nextLine();
		return datos;
	}
	
	public static void datos_empleado(Connection conexion, int id) {
		
		
		
		String 	query = "select t1.job_title as cargo, t5.first_name as jefe, t3.department_name, t4.city from jobs t1 join employees t2 on t1.job_id = t2.job_id join employees t5 on t2.employee_id =t5.employee_id join departments t3 on t2.department_id = t3.department_id join locations t4 on t3.location_id=t4.location_id where t2.employee_id = ?";
		
		
		try {
			PreparedStatement sql = conexion.prepareStatement(query,ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			sql.setInt(1, id);
			ResultSet res = sql.executeQuery();
			
			res.last(); // Se coloca el puntero en la ultima posicion
			int n = res.getRow(); // Devuelve el numero de filas leidas con la query
			
			if(n !=0) {
				res.beforeFirst();
				while(res.next()) {
					System.out.println(" "+res.getString(1)+" "+res.getString(2)+" "+res.getString(3)+" "+res.getString(4));
				}
			
			}else {
				System.out.println("Ese empleado no esta en la BBDD");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	
		
		
	}
	
	public static void transladar_departamento(Connection conexion,int idDerpart) {
		

		
		String query = "SELECT  department_id, department_name,location_id FROM departments where  department_id =?";
				
		try {
			PreparedStatement sql2 = conexion.prepareStatement(query,ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			sql2.setInt(1, idDerpart);
			ResultSet res2 = sql2.executeQuery();
			

			res2.last(); // Se coloca el puntero en la ultima posicion
			int n = res2.getRow(); // Devuelve el numero de filas leidas con la query
			
			if(n !=0) {
				res2.beforeFirst();
				while(res2.next()) {
					System.out.println("Nombre Departamento :" +res2.getString(2));
					
					String query2 = "Select city,country_id from locations where location_id = ?";
					PreparedStatement sql3 = conexion.prepareStatement(query2);
					sql3.setString(1, res2.getString(3));
					ResultSet res3 = sql3.executeQuery();
 
					
					while(res3.next()) {
						System.out.println("Ciudad :" +res3.getString(1));
						
						
						
						String query3 = "SELECT country_name FROM countries where country_id =?";
						PreparedStatement sql4 = conexion.prepareStatement(query3);
						sql4.setString(1, res3.getString(2));
						ResultSet res4 = sql4.executeQuery();

						while(res4.next()) {
							System.out.println( "Pais: " +res4.getString(1));
							
							System.out.println("Cambiar Ubicacion  :");
							
							System.out.println("Ciudad destino :");
							String destino = pedirDatos();
							
							System.out.println("Pais destino :");
							String pDestino = pedirDatos();
							
							
							String 	query4 = "UPDATE locations set city = ?  where city = ? ";
							PreparedStatement sql5 = conexion.prepareStatement(query4);
							sql5.setString(2, res3.getString(1));
							sql5.setString(1, destino);
							
							
							String 	query5 = "UPDATE countries set country_name = ?  where country_name = ? ";
							PreparedStatement sql6 = conexion.prepareStatement(query4);
							sql6.setString(2, res4.getString(1));
							sql6.setString(1, pDestino);
							
							sql5.executeUpdate();
						}
			
						
					}
					
					
					
				
					
					
				
				}
			
			
			}
		}catch(SQLException e ) {
			System.out.println("Ha ocurrido un error ");
			System.out.println("Codigo del Error : "+ e.getErrorCode());
			System.out.println("Mensaje :  "  +e.getMessage());
			System.out.println("Estado : " + e.getSQLState());
		}
			
		
	}
	
	public static void borrar_empleado(Connection conexion,int id) {
		
		
		
		String query3 ="select t1.first_name, t1.last_name ,t1.relationship,t2.first_name, t2.last_name from dependents t1 join employees t2 on t1.employee_id =t2.employee_id where t1.dependent_id = ?";
		
		
		try {
			PreparedStatement sql3 = conexion.prepareStatement(query3,ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			sql3.setInt(1,id );
			ResultSet res3 = sql3.executeQuery();
			res3.last(); // Se coloca el puntero en la ultima posicion
			int n = res3.getRow(); // Devuelve el numero de filas leidas con la query
			if(n !=0) {
				res3.beforeFirst();
				while(res3.next()) {
					System.out.println(" "+res3.getString(1)+" "+res3.getString(2)+" "+res3.getString(3)+" "+res3.getString(4)+" "+res3.getString(5));
					
					String nombre = res3.getString(1);
					String query4 ="DELETE from dependents where first_name = ? ";
					PreparedStatement sql4 = conexion.prepareStatement(query3);
					sql4.setString(1, nombre);
					sql4.executeUpdate();
					
				}
				
				
				
				
				
			}else {
				System.out.println("El dependiente no existe");
			}
			
		} catch (SQLException e) {
			System.out.println("Ha ocurrido un error ");
			System.out.println("Codigo del Error : "+ e.getErrorCode());
			System.out.println("Mensaje :  "  +e.getMessage());
			System.out.println("Estado : " + e.getSQLState());
		}
		
	
	
	
	}
	
	
	
	
	
	
	
	

}

package Pre_recu_03;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Pre_recu_03 {

	private static Connection conector;
	
	public static void main(String[] args) {
		//datos_empleado(101);
		//transladar_empleado(7, 2500);
		borrar_dependiente(10);
	}
	
	
	public static void datos_empleado (int cod_emple) {
		
		conector = Conexion.conectar();
		
		
		String query = "SELECT t2.job_title,t3.first_name, t3.last_name, t4.department_name,t5.city "
				+ "from employees t1 "
				+ "JOIN jobs t2 on t1.job_id = t2.job_id "
				+ "JOIN employees t3 on t1.manager_id = t3.employee_id "
				+ "JOIN departments t4 on t1.department_id = t4.department_id "
				+ "JOIN locations t5 on t4.location_id = t5.location_id "
				+ "where t1.employee_id = ?";
		
		
		
		try {
			PreparedStatement sql = conector.prepareStatement(query,ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			sql.setInt(1, cod_emple);
			
			
			
			ResultSet res = sql.executeQuery();
			
			res.last();
			int filas =	res.getRow();
			
			String cargo = res.getString(1);
			String nombre = res.getString(2);
			String apellido = res.getString(3);
			String departament = res.getString(4);
			String ciudad = res.getString(5);
			
			
			if(filas == 0) {
				System.out.println("Ese empleado no existe");
			}else {
				
				res.beforeFirst();
				while(res.next()) {
					System.out.println(" Cargo :"  +cargo);
					System.out.println(" Nombre :"  +nombre+ " " +apellido);
					System.out.println(" Departament :"   +departament);
					System.out.println(" Ciudad  : "   +ciudad);
				}
			}
			
			
			
			
		} catch (SQLException e) {
			System.out.println("Ha ocurrido un error ");
			System.out.println("Codigo del Error : "+ e.getErrorCode());
			System.out.println("Mensaje :  "  +e.getMessage());
			System.out.println("Estado : " + e.getSQLState());
			e.printStackTrace();
			
		}
	}
	
	
	
	
	public static void transladar_empleado(int cod_dep, int cod_loc) {
		
		conector = Conexion.conectar();
		
		
		String query = "SELECT t1.department_name,t2.city,t3.country_name "
				+ "FROM departments t1 "
				+ "JOIN locations t2 on t1.location_id = t2.location_id "
				+ "JOIN countries t3 on t2.country_id = t3.country_id "
				+ "WHERE t1.department_id= ? ";
		
		try {
			PreparedStatement sql = conector.prepareStatement(query,ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			sql.setInt(1, cod_dep);

			ResultSet res = sql.executeQuery();
			
			res.last();
			int filas = res.getRow();
			
			
			String depart = res.getString(1);
			String ciuOrig = res.getString(2);
			String PaisOrig = res.getString(3);
			
			
			if(filas == 0) {
				System.out.println("Ese departamento no existe");
			}else {
				System.out.println("Nombre departamento : "  +depart);
				System.out.println("Ciudad Origen : "  +ciuOrig);
				System.out.println("Pais Origen : "  +PaisOrig);
				
				
				query = "UPDATE departments SET location_id = ? WHERE department_id = ? ";
				
				PreparedStatement sql2 = conector.prepareStatement(query,ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
				sql2.setInt(1,cod_loc);
				sql2.setInt(2,cod_dep);
				
				sql2.executeUpdate();
				
		
				
			}
			
			
			
			String query2 = "SELECT t1.department_name,t2.city,t3.country_name "
					+ "FROM departments t1 "
					+ "JOIN locations t2 on t1.location_id = t2.location_id "
					+ "JOIN countries t3 on t2.country_id = t3.country_id "
					+ "WHERE t1.department_id= ? ";
			
			PreparedStatement sql3 = conector.prepareStatement(query2,ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet res2 = sql.executeQuery();
			res2.last();
			
			String ciuDestino = res2.getString(2);
			String paisDestino = res2.getString(3);
			
			
			System.out.println("Ciudad Destino : " +ciuDestino);
			System.out.println("Pais Destino :"   +paisDestino);
			
			
			
		} catch (SQLException e) {
			System.out.println("Ha ocurrido un error ");
			System.out.println("Codigo del Error : "+ e.getErrorCode());
			System.out.println("Mensaje :  "  +e.getMessage());
			System.out.println("Estado : " + e.getSQLState());
			e.printStackTrace();
		}
	}
	
	
	
	
	public static void borrar_dependiente(int cod_dep) {
		
		conector = Conexion.conectar();
		
		String query="SELECt t1.first_name, t1.last_name,t1.relationship, t2.first_name, t2.last_name "
				+ "FROM dependents t1 "
				+ "JOIN employees t2 on t1.employee_id = t2.employee_id "
				+ "where t1.dependent_id = ? ";
		
		
		
		try {
			PreparedStatement sql = conector.prepareStatement(query,ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			sql.setInt(1, cod_dep);
			
			
			ResultSet res = sql.executeQuery();
			
			res.last();
			
			String nombre = res.getString(1);
			String apellido = res.getString(2);
			String relacion = res.getString(3);
			String nombreP = res.getString(4);
			String apellidoP = res.getString(5);
			
			
			int filas = res.getRow();
			if (filas == 0) {
				System.out.println("No existe  esa persona");
			}else {
				res.beforeFirst();
				while(res.next()) {
					System.out.println("Nombre hijo" +nombre+ " " +apellido);
					System.out.println("Relacion "  +relacion);
					System.out.println("Nombre Padre "  +nombreP+" " +apellidoP);
					
					query= "DELETE FROM dependents  WHERE dependent_id = ?";
					
					PreparedStatement sql2 = conector.prepareStatement(query,ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
					
					sql2.setInt(1, cod_dep);
					
					sql2.executeUpdate();
					
					
				}
			}
			
			
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
	}
	
	/*
	 * Clase anidada
	 */
	
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
				url = "jdbc:mysql://localhost:3306/examen"; 
				usuario = "examen";
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

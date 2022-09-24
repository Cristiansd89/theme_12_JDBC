package Pre_recu_04;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import ConectoresPaulo.Conector;

public class Pre_recu_04 {
	
	private static Connection conector;

	public static void main(String[] args) {
			
		//datos_empleado(105);
		//transaladar_empleado(10, 1700);
		//borrar_dependiente(15);
		
		insertar_dependiente(104, 15);
	}
	
	
	
	public static void datos_empleado(int cod_empleado) {
		
			conector = Conexion.conectar();
			
			String query = "SELECT t2.job_title, t3.first_name, t3.last_name, t4.department_name, t5.city "
					+ "FROM employees t1  "
					+ "JOIN jobs t2 on t1.job_id = t2.job_id "
					+ "JOIN employees t3 on t1.manager_id = t3.employee_id "
					+ "JOIN departments t4 on t1.department_id = t4.department_id "
					+ "JOIN locations t5 on t4.location_id = t5.location_id "
					+ "WHERE t1.employee_id = ?";
			
			try {
				PreparedStatement sql = conector.prepareStatement(query,ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
				
				sql.setInt(1, cod_empleado);
				
				
				ResultSet res = sql.executeQuery();
				res.last();
				int filas = res.getRow();
				
				String cargo = res.getString(1);
				String nombre = res.getString(2);
				String apellidos = res.getString(3);
				String depart = res.getString(4);
				String ciudad = res.getString(5);
				
				
				if (filas == 0) {
					System.out.println("Ese empleado no existe");
				}else {
					res.beforeFirst();
					while(res.next()) {
						System.out.println(" Cargo :"  +cargo);
						System.out.println(" Responsable :"  +nombre+ " " +apellidos);
						System.out.println(" Departamentos : " +depart);
						System.out.println(" Ciudad :"  +ciudad);
						
						
					}
				}
				
			} catch (SQLException e) {
				System.out.println("Ha ocurrido un error ");
				System.out.println("Codigo del Error : "+ e.getErrorCode());
				System.out.println("Mensaje :  "  +e.getMessage());
				System.out.println("Estado : " + e.getSQLState());
			}
			
		
		
	}
	
	
	public static void transaladar_empleado(int cod_dep, int cod_loc) {
		conector = Conexion.conectar();
		
		String query = "SELECT t1.department_name, t2.city, t3.country_name "
				+ "FROM departments t1 "
				+ "JOIN locations t2 on t1.location_id = t2.location_id "
				+ "JOIN countries t3 on t2.country_id = t3.country_id "
				+ "WHERE t1.department_id = ?";
		
		try {
			PreparedStatement sql = conector.prepareStatement(query,ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			sql.setInt(1, cod_dep);
			ResultSet res = sql.executeQuery();
			
			res.last();
			int filas = res.getRow();
			
			String depart = res.getString(1);
			String cityO = res.getString(2);
			String paisO = res.getString(3);
			
			if (filas ==0) {
				System.out.println("No existe ese Departamento");
			}else {
				res.beforeFirst();
				while(res.next()) {
					System.out.println(" Departamento :" +depart);
					System.out.println(" Ciudad Origen :"  +cityO);
					System.out.println(" Pais de Origen :"  +paisO);
					
					
					
					query = "UPDATE departments SET location_id =? WHERE department_id = ?";
					//PreparedStatement sql2 = conector.prepareStatement(query,ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
					PreparedStatement sql2 = conector.prepareStatement(query,ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
					sql2.setInt(1, cod_loc);
					sql2.setInt(2, cod_dep);
					sql2.executeUpdate();
					
					
				
				
			}
			
			
			}	
			
			String query2 = "SELECT t2.city, t3.country_name "
					+ "FROM departments t1 "
					+ "JOIN locations t2 on t1.location_id = t2.location_id "
					+ "JOIN countries t3 on t2.country_id = t3.country_id "
					+ "WHERE t1.department_id =? ";
			
			
			PreparedStatement sql3 = conector.prepareStatement(query2,ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			sql3.setInt(1, cod_dep);
			
			ResultSet res2 = sql3.executeQuery();
			res2.last();
			
			String cityD = res2.getString(1);
			String paisD = res2.getString(2);
			
			
			System.out.println(" Ciudad Destino :"  +cityD);
			System.out.println(" Pais de Destino :"  +paisD);
			
			
			
		} catch (SQLException e) {
			
			System.out.println("Ha ocurrido un error ");
			System.out.println("Codigo del Error : "+ e.getErrorCode());
			System.out.println("Mensaje :  "  +e.getMessage());
			System.out.println("Estado : " + e.getSQLState());
		}
		
	}
	
	public static void borrar_dependiente(int cod_dependiente) {
		
		conector = Conexion.conectar();
		
		String query = "SELECT t1.first_name,t1.last_name, t1.relationship, t2.first_name, t2.last_name "
				+ "FROM dependents t1 "
				+ "JOIN employees t2 on t1.employee_id = t2.employee_id "
				+ "WHERE t1.dependent_id =? ";
		
		try {
			PreparedStatement  sql = conector.prepareStatement(query,ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			sql.setInt(1, cod_dependiente);
			
			sql.executeQuery();
			
			ResultSet res = sql.executeQuery();
			
			res.last();
			int filas = res.getRow();
			
			String nomHijo = res.getString(1);
			String apeHijo = res.getString(2);
			String relacion = res.getString(3);
			String nomPadre = res.getString(4);
			String apePadre = res.getString(5);
			
			
			if(filas == 0) {
				System.out.println("No existe ese dependiente ");
			}else {
				System.out.println(" Nombre Hijo :" +nomHijo+ " " +apeHijo);
				System.out.println(" Relacion :"  +relacion);
				System.out.println("Nombre Padre : "  +nomPadre+ " " + apePadre);
				
				
				query= "DELETE FROM dependents WHERE dependent_id = ?";
				PreparedStatement sql2 = conector.prepareStatement(query,ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
				sql2.setInt(1, cod_dependiente);
				sql2.executeUpdate();
				
			}
			
			
		} catch (SQLException e) {
			
		}
		
		
	}
	
	public static void insertar_dependiente(int cod_emp, int cod_dep) {
		conector = Conexion.conectar();
		
		
		String query ="SELECT t1.first_name,t1.last_name, t2.job_title, t3.department_name, t4.city, t4.state_province, t5.country_name "
				+ "FROM employees t1 "
				+ "JOIN jobs t2 on t1.job_id = t2.job_id "
				+ "JOIN departments t3 on t1.department_id = t3.department_id "
				+ "JOIN locations t4 on t3.location_id = t4.location_id "
				+ "JOIN countries t5 on t4.country_id = t5.country_id "
				+ "WHERE t1.employee_id =? " ;
		
		try {
			PreparedStatement sql = conector.prepareStatement(query,ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			sql.setInt(1, cod_emp);
			
			ResultSet res = sql.executeQuery();
			
			res.last();
			int filas = res.getRow();
			
			String nomEmp = res.getString(1);
			String apeEm = res.getString(2);
			String cargo = res.getString(3);
			String depart = res.getString(4);
			String ciudad = res.getString(5);
			String estado = res.getString(6);
			String pais = res.getString(7);
			
			
			if(filas == 0) {
				System.out.println("No existe ese empleado :");
				
			}else {
				res.beforeFirst();
				while(res.next()) {
					System.out.println(" Nombre empleado : " +nomEmp+" "+apeEm);
					System.out.println(" Cargo : "+cargo);
					System.out.println(" depart : "  +depart);
					System.out.println(" Ciudad : "  +ciudad);
					System.out.println(" Estado : "  +estado);
					System.out.println(" pais : " +pais);
					
					
					query = "INSERT INTO dependents (dependent_id,first_name,last_name,relationship,employee_id) VALUES (?,?,?,?,?) ";
					
					PreparedStatement sql2 = conector.prepareStatement(query,ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
					sql2.setInt(1, cod_dep);
					sql2.setString(2, "Cuba");
					sql2.setString(3, "Ernst");
					sql2.setString(4, "Child");
					sql2.setInt(5, cod_emp);
					
					sql2.executeUpdate();
				}
			}
			
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

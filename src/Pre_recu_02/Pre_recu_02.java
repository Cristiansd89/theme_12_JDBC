package Pre_recu_02;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Pre_recu_02 {

	
	private static Connection conector;
	
	public static void main(String[] args) {
		//datos_empleado(103);
		//transladar_empleado(8, 1700);
		//borrar_empleado(11);
		
		
		
		

	}
	
	
	public static void  datos_empleado(int cod_empleado) {
		
		conector = Conexion.conectar();
		
		String query = "select t2.job_title,t3.first_name, t3.last_name, t4.department_name, t5.city "
				+ "from employees t1 "
				+ "join jobs t2 on t1.job_id = t2.job_id "
				+ "join employees t3 on t1.manager_id = t3.employee_id "
				+ "join departments t4 on t1.department_id = t4.department_id "
				+ "join locations  t5 on t4.location_id = t5.location_id "
				+ "where t1.employee_id = ?";
		
		try {
			PreparedStatement sql = conector.prepareStatement(query,ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			sql.setInt(1, cod_empleado);
			
			ResultSet res = sql.executeQuery();
			
			res.last(); // Coloca el puntero en la ultima posicion
			int n = res.getRow(); // Me devuelve un numero de filas leidas por la query
			
			String cargo = res.getString(1);
			String nombre = res.getString(2);
			String apellido = res.getString(3);
			String departamento = res.getString(4);
			String ciudad = res.getString(5);
			
			
			
			if(n == 0) {
				System.out.println("No ese empleadono existe");
			}else {
				res.beforeFirst();
				while(res.next()) {
					System.out.println("Cargo : "  +cargo);
					System.out.println("nombre Jefe :  "  +nombre+" " +apellido);
					System.out.println("Departamento : "   +departamento);
					System.out.println("Ciudad :"  +ciudad);
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
	
	
	
	public static void transladar_empleado(int cod_Depart, int cod_loc) {
		
		conector = Conexion.conectar();
		
		
		String query = "select t1.department_name, t2.city, t3.country_name "
				+ "from departments t1 "
				+ "join locations t2 on t1.location_id = t2.location_id "
				+ "join countries t3 on t2.country_id = t3.country_id "
				+ "where  t1.department_id = ?";
		
		PreparedStatement sql;
		try {
			sql = conector.prepareStatement(query,ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			sql.setInt(1, cod_Depart);
			
			ResultSet res = sql.executeQuery();
			
			res.last(); // Coloca el puntero en la ultima posicion
			int n = res.getRow(); // Me devuelve un numero de filas leidas por la query
			
			String depart = res.getString(1);
			String ciuOrigen = res.getString(2);
			String pais = res.getString(3);
			
			if(n == 0) {
				System.out.println("Ese departamentos no existe ");
			}else {
				res.beforeFirst();
				while(res.next()) {
					System.out.println("Departamento " +depart);
					System.out.println("Ciudad Origen : " +ciuOrigen );
					System.out.println("Pais Origen: "  +pais);
					
					 query ="UPDATE departments SET location_id =? where department_id = ?;";
					 
					 PreparedStatement sql2 = conector.prepareStatement(query,ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
					 sql2.setInt(1, cod_loc);
					 sql2.setInt(2, cod_Depart);
					 
					 sql2.executeUpdate();
					
					
					
					
				}
				
				
				String query2 ="select t1.department_name, t2.city, t3.country_name "
						+ "from departments t1 "
						+ "join locations t2 on t1.location_id = t2.location_id "
						+ "join countries t3 on t2.country_id = t3.country_id "
						+ "where  t1.department_id = ?";
				
				
				PreparedStatement sql3 = conector.prepareStatement(query2,ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
				sql3.setInt(1, cod_Depart);
				ResultSet res2 = sql3.executeQuery();
				res2.last();
				
				String ciuDest = res2.getString(2);
				String paisDest = res2.getString(3);
				
				res.beforeFirst();
				while (res.next()) {
					System.out.println("Ciudad  Destino : " +ciuDest);
					System.out.println("Paus Destino : " +paisDest);
					
				}
				
			}
			
		} catch (SQLException e) {
			System.out.println("Ha ocurrido un error ");
			System.out.println("Codigo del Error : "+ e.getErrorCode());
			System.out.println("Mensaje :  "  +e.getMessage());
			System.out.println("Estado : " + e.getSQLState());
		}
		
		
		
	}
	
	
	public static void borrar_empleado(int cod_dep) {
		
		conector = Conexion.conectar();
		
		
		String query = "Select t1.first_name, t1.last_name, t1.relationship, t2.first_name, t2.last_name "
				+ "from dependents t1 "
				+ "join employees t2 on t1.employee_id = t2.employee_id "
				+ "where t1.dependent_id = ?";
		try {
			PreparedStatement sql = conector.prepareStatement(query,ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			sql.setInt(1, cod_dep);
			ResultSet res = sql.executeQuery();
			
			res.last(); // Coloca el puntero en la ultima posicion
			int n = res.getRow(); // Me devuelve un numero de filas leidas por la query
			
			String nombreH = res.getString(1);
			String apellidoH = res.getString(2);
			String relacion = res.getString(3);
			String nombreP = res.getString(4);
			String apellidoP = res.getString(5);
			
			if(n == 0){
				System.out.println("Ese dependiente no existe");
			}else {
				
				res.beforeFirst();
				while(res.next()) {
					System.out.println(" Nombre hijo :"  +nombreH+ " " +apellidoH);
					System.out.println("Relacion : "   +relacion);
					System.out.println("Nombre Padre : "  +nombreP+" "+apellidoP);
					
					
					
					query = "Delete from dependents where dependent_id =?";
					
					PreparedStatement sql2 = conector.prepareStatement(query,ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
					sql2.setInt(1, cod_dep);
					
					sql2.executeUpdate();
				}
				
			}
			
			
			
		} catch (SQLException e) {
			System.out.println("Ha ocurrido un error ");
			System.out.println("Codigo del Error : "+ e.getErrorCode());
			System.out.println("Mensaje :  "  +e.getMessage());
			System.out.println("Estado : " + e.getSQLState());
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

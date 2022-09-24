package Pre_Recu;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;



public class Pre_Recu {
	
	private static Connection conector;

	public static void main(String[] args) {
		//datos_empleado(105);
		//transladar_departamento(8, 1700);
		//borrar_dependiente(11);
		insertar_depent(113, 11);

	}
	
	
	
	public static void datos_empleado(int numEmpleado) {
		
		/*
		 * Con la Clase estatica creada  llamamos a la conexion
		 */
		 conector = Conexion.conectar();
		 
		 String query = "select t2.job_title as Cargo, concat(t3.first_name,' ', t3.last_name) as jefe, t4.department_name as Nombre_Departamento, t5.city as ubicacion_departamento from employees t1 join jobs t2 on t1.job_id = t2.job_id join employees t3 on t3.employee_id = t1.manager_id join departments t4 on t1.department_id = t4.department_id join locations t5 on t4.location_id = t5.location_id where t1.employee_id =?"; 
				 
		try {
			PreparedStatement sql = conector.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);// Establecemos la conexion 
			sql.setInt(1, numEmpleado); // Le decimos que en el primer interrogante Introduzcamos el numero de empleado	
			
			ResultSet res = sql.executeQuery(); // Cargamos la query
			
			res.last(); // Coloca el puntero en la ultima posicion
			int n = res.getRow(); // Me devuelve un numero de filas leidas por la query
			
			if(n == 0) {
				System.out.print("Ese empleado no existe");
			}else if (n ==1){
				res.beforeFirst();
				while(res.next()){
					System.out.println("Cargo : "+res.getString(1));
					System.out.println("Responsable : "+res.getString(2));
					System.out.println("Departamento : "+res.getString(3));
					System.out.println("Ciudad : "+res.getString(4));
					
				
				}
				
			}
			 conector.close();
		} catch (SQLException e) {
			System.out.println("Ha ocurrido un error ");
			System.out.println("Codigo del Error : "+ e.getErrorCode());
			System.out.println("Mensaje :  "  +e.getMessage());
			System.out.println("Estado : " + e.getSQLState());
		}
		
	}
	
	
	public static void transladar_departamento(int dep_orig, int loc_dest) {
		Scanner ent = new Scanner(System.in);
		
		conector = Conexion.conectar(); // Abrirmos la conexion con la BBDD
		 /*
		  * Este query Muestra el primer resultado de origen
		  */
		 String query = "select t1.department_name, t2.city,t3.country_name "
		 		+ "from departments t1 "
		 		+ "join locations t2 on t1.location_id = t2.location_id "
		 		+ "join countries t3 on t2.country_id = t3.country_id "
		 		+ "where t1.department_id =?"; 
		 try {
			
			 PreparedStatement sql = conector.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			 sql.setInt(1, dep_orig);  // Insertamos en el primer Interrogante el numero de departamento
		 
			 ResultSet res = sql.executeQuery(); // Cargamos la query
			 
			 res.last(); // Coloca el puntero en la ultima posicion
			 int n = res.getRow(); // Me devuelve un numero de filas leidas por la query
			 
			 
			 String nombreDepartamento = res.getString(1); //Insertamos en la variables para posteriormente mostrar el origen 
			 String ciudadOrigen =res.getString(2);
			 String paisOrigen = res.getString(3);
			 
			 if(n ==0) {
				 System.out.print("Ese departamento no existe");
			 }else if (n == 1) {
				 res.beforeFirst();
				 while(res.next()) {
					 System.out.println("Nombre Deparmento :" +nombreDepartamento);
					 System.out.println("Ciudad Origen  " +ciudadOrigen + " || Pais Origen " +paisOrigen);
				 
				 
					 
					 
					 query = "UPDATE departments SET location_id =? where department_id=?" ;
							 
					 PreparedStatement sql2 = conector.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
					 sql2.setInt(1,loc_dest);
					 sql2.setInt(2,dep_orig);
					 
					 
					 sql2.executeUpdate();
					 
					
				}
			 }
				 
		
			 
			 String query2 = "select t1.department_name, t2.city,t3.country_name "
				 		+ "from departments t1 "
				 		+ "join locations t2 on t1.location_id = t2.location_id "
				 		+ "join countries t3 on t2.country_id = t3.country_id "
				 		+ "where t1.department_id =?"; 
				 
					 PreparedStatement sql2 = conector.prepareStatement(query2, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
					 sql.setInt(1, dep_orig);  // Insertamos en el primer Interrogante el numero de departamento
				 
					 ResultSet res2 = sql2.executeQuery(); // Cargamos la query
					 
					 while(res2.next()) {
						 
						 String ciudadDestino = res2.getString("t2.city");
						 String paisDestino = res2.getString("t3.country_name");
						 
						 
						System.out.println("Ciudad de destino:  " +ciudadDestino);
						System.out.println("pais de Destino: "+paisDestino);
						 
					 }
					 
			 
			 
				conector.close();
			 
		 } catch (SQLException e) {
			System.out.println("Ha ocurrido un error ");
			System.out.println("Codigo del Error : "+ e.getErrorCode());
			System.out.println("Mensaje :  "  +e.getMessage());
			System.out.println("Estado : " + e.getSQLState());
			e.printStackTrace();
		}
		 
		 }
	public static void borrar_dependiente(int codigo_dep) {
		/*
		 * Con la Clase estatica creada  llamamos a la conexion
		 */
		 conector = Conexion.conectar();
		 
		 String query ="select t1.first_name as nombre, t1.last_name as apellido, t1.relationship as relacion, t2.first_name as nombre, t2.last_name as apellido "
		 		+ "from dependents t1 "
		 		+ "join employees t2 on t1.employee_id = t2.employee_id "
		 		+ "where dependent_id = ?";
		 
		 PreparedStatement sql;
		try {
			sql = conector.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			sql.setInt(1, codigo_dep);  // Insertamos en el primer Interrogante el numero de departamento
			 
			ResultSet res = sql.executeQuery(); // Cargamos la query
			
			
			 res.last(); // Coloca el puntero en la ultima posicion
			 int n = res.getRow(); // Me devuelve un numero de filas leidas por la query
			 
			 String nombreD = res.getNString(1);
			 String apellidoD = res.getString(2);
			 String parentesco = res.getString(3);
			 String nombreEm = res.getString(4);
			 String apellidoEm = res.getString(5);
			 if(n == 0) {
				 System.out.println("Ese dependiente no existe");
			 }else {
				 res.beforeFirst();
				 while(res.next()) {
					 
					 System.out.println("Nombre y apellidos dependiente : "  +nombreD +" "+apellidoD);
					 System.out.println("parentesco : "  +parentesco);
					 System.out.println("Nombre y apellidos empleado : "  +nombreEm +" "+apellidoEm);
					 
					 query = "DELETE FROM dependents where dependent_id =?";
					 
					PreparedStatement sql2 = conector.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
					
					sql2.setInt(1, codigo_dep);
					
					sql2.executeUpdate();
				 }
			 }
			
			//Hazte el ultimo insertando por si acaso te cae en el examen
			
			
		} catch (SQLException e) {
			System.out.println("Ha ocurrido un error ");
			System.out.println("Codigo del Error : "+ e.getErrorCode());
			System.out.println("Mensaje :  "  +e.getMessage());
			System.out.println("Estado : " + e.getSQLState());
			e.printStackTrace();
		}
		
		 
		 
	}
	
	public static void insertar_depent(int cod_empleado, int cod_depent) {
		
		conector = Conexion.conectar();
		
		
		
		String query = "select t1.first_name, t1.last_name, t2.department_name, t3.city, t3.state_province, t4.country_name "
				+ "from employees t1 "
				+ "join departments t2 on t1.department_id = t2.department_id "
				+ "join locations t3 on  t2.location_id = t3.location_id "
				+ "join countries t4 on t3.country_id = t4.country_id "
				+ "where t1.employee_id = ?";
		
		
		try {
			PreparedStatement sql = conector.prepareStatement(query,ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			sql.setInt(1, cod_empleado);
			
			ResultSet res = sql.executeQuery();
			
			 res.last(); // Coloca el puntero en la ultima posicion
			 int n = res.getRow(); // Me devuelve un numero de filas leidas por la query
			 String nombreEm = res.getString(1);
			 String apellidosEm = res.getString(2);
			 String departemento = res.getString(3);
			 String city =res.getString(4);
			 String estado = res.getString(5);
			 String pais = res.getString(6);
			
			if (n == 0){
				System.out.println("Ese Empleado o existe");
			}else {
				res.beforeFirst(); // Colocamos el puntero antes de la primera posicion
				while(res.next()) {
					System.out.println("Nombre Empleado :" +nombreEm +" " +apellidosEm);
					System.out.println("Departamento : " +departemento);
					System.out.println("Ciudad : " +city);
					System.out.println("Estado :" +estado);
					System.out.println("Pais : " +pais);
					
					
					query = "INSERT INTO dependents (dependent_id,first_name,last_name,relationship,employee_id) "
							+ " VALUES (?,?,?,?,?) ";
					
					
					PreparedStatement sql2 = conector.prepareStatement(query,ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
					
					/*
					 * Tiene que haber el mismo numero de interrogaciones que datos que 
					 * Introduces
					 */
					sql2.setInt(1, cod_depent);
					sql2.setString(2,"Zero");
					sql2.setString(3,"Popp");
					sql2.setString(4,"child" );
					sql2.setInt(5,cod_empleado);
					
					sql2.executeUpdate();
							
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
		 
	
	
		 
	
	

	
	
	/*
	 * Creamos una clase tipo Conexion para establecer la conexion
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




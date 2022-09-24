package Pre_Examen;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.Scanner;
public class ModeloLuis {

	public static void main(String[] args) {
		
		
		String url = "jdbc:mysql://localhost:3306/dam2"; 
		String usuario = "trivial";
		String password = "abc123";
		Connection conexion;

		try {
			conexion = DriverManager.getConnection(url, usuario, password);
			
			//mostrarAlumnos(conexion);
			//insertarPersona(conexion);
			//eliminarUsuario(conexion);
			//modificarUsuario(conexion);
			
		}catch(SQLException e) {
			
		}
	}
	
	
	public static String pedirDatos() {
		Scanner ent = new Scanner(System.in);
		String datos = ent.nextLine();
		return datos;
	}
	
	public static void mostrarAlumnos(Connection conexion) {
		
		String query = "SELECT * FROM alumnos";
		
		try {
			PreparedStatement sql = conexion.prepareStatement(query,ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet res = sql.executeQuery();
			
			res.last(); // Se coloca el puntero en la ultima posicion
			int n = res.getRow(); // Devuelve el numero de filas leidas con la query
			
			if(n != 0) {
				res.beforeFirst();
				while(res.next()) {
					
					System.out.println(""+res.getString(2)+" " +res.getString(3)+ " "+res.getString(4));
				}
				
			}
			
			
			
		} catch (SQLException e) {
			System.out.println("Ha ocurrido un error ");
			System.out.println("Codigo del Error : "+ e.getErrorCode());
			System.out.println("Mensaje :  "  +e.getMessage());
			System.out.println("Estado : " + e.getSQLState());
		}
		
		
	}
	
	
	public static void insertarPersona(Connection conexion) {
		
		
		
		
		
		System.out.println("Nombre :");
		String nombre = pedirDatos();
		
		System.out.println(" Primer apellido :");
		String apellido1 = pedirDatos();
		
		
		System.out.println("Segundo apellido : ");
		String apellido2 = pedirDatos();
		
		String query ="INSERT INTO alumnos VALUES (NULL,?,?,?)";
		
		try {
			PreparedStatement sql1 = conexion.prepareStatement(query);
			
			sql1.setString(1, nombre);
			sql1.setString(2, apellido1);
			sql1.setString(3, apellido2);
			
			sql1.executeUpdate();
			
			
			
		} catch (SQLException e) {
			System.out.println("Ha ocurrido un error ");
			System.out.println("Codigo del Error : "+ e.getErrorCode());
			System.out.println("Mensaje :  "  +e.getMessage());
			System.out.println("Estado : " + e.getSQLState());
		}
		
	}
	
	public static void eliminarUsuario(Connection conexion) {
		
		System.out.println("Nombre del usuario");
		String nombre = pedirDatos();
		
		System.out.println("Primer apellido :");
		String apellido = pedirDatos();
		
		
		String query = "DELETE from alumnos where nombre=? and apellido1 = ?";
		
		try {
			PreparedStatement sql = conexion.prepareStatement(query);
			sql.setString(1, nombre);
			sql.setString(2, apellido);
			
			sql.executeUpdate();
			
			
			
			
		} catch (SQLException e) {
			System.out.println("Ha ocurrido un error en Eliminar usuario ");
			System.out.println("Codigo del Error : "+ e.getErrorCode());
			System.out.println("Mensaje :  "  +e.getMessage());
			System.out.println("Estado : " + e.getSQLState());
		}
		
		
	
	}
	
	public static void modificarUsuario(Connection conexion) {
		
		Scanner ent = new Scanner(System.in);
		
		
		
		System.out.println("Nombre del Usuario :");
		String nombre = pedirDatos();
		
		System.out.println(" Apellido :");
		String apellido = pedirDatos();
		
		
		
		System.out.println("Que quieres Modificar :");
		System.out.println("1- Nombre");
		System.out.println("2- Primer Apellido");
		System.out.println("3- Segundo Apellido");
		
		int opcion = ent.nextInt();
		
		switch (opcion) {
			case 1:
				
				
				
				System.out.println("Nuevo nombre: ");
				String nuevoNom = pedirDatos();
				
				String query = "UPDATE  alumnos SET nombre =? where nombre =? and apellido1 = ?" ;
				
				PreparedStatement sql;
				try {
					sql = conexion.prepareStatement(query);
					
					sql.setString(1, nuevoNom);
					sql.setString(2, nombre);
					sql.setString(3, apellido);
					
					sql.executeUpdate();
					
				} catch (SQLException e) {
					
					System.out.println("Ha ocurrido un error en Eliminar usuario ");
					System.out.println("Codigo del Error : "+ e.getErrorCode());
					System.out.println("Mensaje :  "  +e.getMessage());
					System.out.println("Estado : " + e.getSQLState());
				}
				break;
				
				
				
				
			case 2:
					
					
					System.out.println("Nuevo Apellido: ");
					String nuevoApe = pedirDatos();
					
					String query2 = "UPDATE  alumnos SET apellido1 =? where nombre =? and apellido1 = ?" ;
					
					PreparedStatement sql2;
					try {
						sql2 = conexion.prepareStatement(query2);
						
						sql2.setString(1, nuevoApe);
						sql2.setString(2, nombre);
						sql2.setString(3, apellido);
						
						sql2.executeUpdate();
						
					} catch (SQLException e) {
						
						System.out.println("Ha ocurrido un error en Eliminar usuario ");
						System.out.println("Codigo del Error : "+ e.getErrorCode());
						System.out.println("Mensaje :  "  +e.getMessage());
						System.out.println("Estado : " + e.getSQLState());
					}
					
					
					
					
					break;
					
					
			case 3:
				
				System.out.println("Nuevo Apellido: ");
				String nuevoApe2 = pedirDatos();
				
				String query3 = "UPDATE  alumnos SET apellido2 =? where nombre =?  and apellido1 = ?";
				
				PreparedStatement sql3;
				try {
					sql3 = conexion.prepareStatement(query3);
					sql3.setString(1, nuevoApe2);
					sql3.setString(2, nombre);
					sql3.setString(3, apellido);
					
					sql3.executeUpdate();
					
				} catch (SQLException e) {
					
					System.out.println("Ha ocurrido un error en Eliminar usuario ");
					System.out.println("Codigo del Error : "+ e.getErrorCode());
					System.out.println("Mensaje :  "  +e.getMessage());
					System.out.println("Estado : " + e.getSQLState());
				}

				
				break;

				default:
					break;
		}
		
	}

}

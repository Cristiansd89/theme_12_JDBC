package Pre_Examen;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.Scanner;

import com.mysql.cj.protocol.Resultset;

public class Practica_4 {

	public static void main(String[] args) {
		
		Scanner ent = new Scanner(System.in);
		
		String url = "jdbc:mysql://localhost:3306/cursos"; 
		String usuario = "trivial";
		String password = "abc123";
		Connection conexion;
		
		System.out.println(" De que Barrio de Madrid son los profesores que quieres ver: ");
		String ciudad = ent.next();
		
		try {
			conexion = DriverManager.getConnection(url, usuario, password);
			
			String query ="SELECT id FROM ciudad where nombre = ?";
			
			PreparedStatement sql =conexion.prepareStatement(query,ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			sql.setString(1, ciudad);
			
			ResultSet res = sql.executeQuery();
			
			res.last(); // Se coloca el puntero en la ultima posicion
			int n = res.getRow(); // Devuelve el numero de filas leidas con la query
			
			if(n !=0) {
				res.beforeFirst();
				while(res.next()) {
					res.getString(1);
					
					String query2 = "SELECT nombre, apellidos from persona where ciudad =?";
					
					PreparedStatement sql2 = conexion.prepareStatement(query2,ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY); 
					sql2.setString(1, res.getString(1));
					
					ResultSet res2 = sql2.executeQuery();
					

					while(res2.next()) {
							System.out.println(res2.getString(1)+ " " + res2.getString(2));
						}
					}
					
				
			}else {
				System.out.println("Ese barrio no esta en la BBDD");
			}
			
			
			
			
		}catch(SQLException e) {
			
		}
		

	}

}

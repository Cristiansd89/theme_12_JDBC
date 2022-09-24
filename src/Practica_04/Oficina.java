package Practica_04;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import com.mysql.cj.protocol.Resultset;

public class Oficina {

	public static void main(String[] args) {
		
		Scanner ent = new Scanner(System.in);
		
		String url = "jdbc:mysql://localhost:3306/classicmodels"; 
		String usuario = "trivial";
		String password = "abc123";
		
		try {
			Connection conexion = DriverManager.getConnection(url, usuario, password);
		
			System.out.println("Conexion establecida");
			
			
			System.out.println("Codigo de la oficina :");
			 int codigo = ent.nextInt();
			 
			 
			 /*
			  * Primera Tabla
			  */
			 
			 String query ="SELECT officeCode from offices where  officeCode = ? ";
			 PreparedStatement sql =  conexion.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY); // Establecemos la conexion
			 sql.setInt(1,codigo); // Le decimos que en el primer interrogante introduzca el codigo
			 
			 ResultSet res = sql.executeQuery(); // Cargamos la query
			 
			 
			 res.last(); // Se coloca el puntero en la ultima posicion
			 int n = res.getRow(); // Devuelve el numero de filas leidas con la query
				
			 if(n == 0) {
				System.out.println("La Oficina  Con el codigo " +codigo+ " No existe");
			 }else if(n == 1){
				System.out.println("Hay una Oficina llamada " +codigo );
		
		
			 }
			 /*
			  * Segunda tabla
			  */
			 
			String query2 = "SELECT lastName, firstName from  employees where officeCode = ?";
			PreparedStatement sql2 = conexion.prepareStatement(query2);
			sql2.setInt(1, res.getInt(1));
			ResultSet res2 = sql2.executeQuery();
			
			System.out.println("Tiene estos empleados :");
			while(res2.next()) {
				
				System.out.println(" "+res2.getString(2) + " "+res2.getString(1) );
			}
			/***
			 * tercera Tabla Creamos la oficina de Madrid
			 */
			/*
			System.out.println("Quieres crear una nueva oficina ? si/no" );
			String opcion = ent.next();
			
			if(opcion.equalsIgnoreCase("si")) {
				String query3 = "INSERT INTO offices VALUES ('8','Madrid','+34 91 7761211','Fuente de San Pedro','NULL' ,'Madrid','España','28032','España')";
				PreparedStatement sql3 = conexion.prepareStatement(query3);
				sql3.executeUpdate();
				System.out.println("Oficina creada");
			}else {
				System.out.println("Fin del programa");
			}
			*/
			
			System.out.println("Oficina de Origen :");
			int origen= ent.nextInt();
			
			System.err.println("Oficina de Destino : ");
			int destino = ent.nextInt();
			
			/*
			 * No esta Terminado
			 */
			
			String 	query4 = "UPDATE employes set officeCode = ? where officeCode = ? ";
			PreparedStatement sql4 = conexion.prepareStatement(query4);
			sql4.setInt(2, origen);
			sql4.setInt(1, destino);
			sql4.executeUpdate();
			System.out.println(" Empleados Transladados");
			
			
			conexion.close();
			
			
			
			
			
		} catch (SQLException e) {
			System.out.println("Ha ocurrido un error ");
			System.out.println("Codigo del Error : "+ e.getErrorCode());
			System.out.println("Mensaje :  "  +e.getMessage());
			System.out.println("Estado : " + e.getSQLState());
		} 
	}

}

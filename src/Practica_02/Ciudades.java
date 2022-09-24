package Practica_02;

import java.sql.Connection;
import java.sql.DriverManager;
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
		
		int filas = 0;

		/*if(res.last()) {
			filas = res.getRow();
			res.beforeFirst();
			
		}*/
		
		try {
			
			Connection conexion = DriverManager.getConnection(url, usuario, password);  // Flujo de conexion
			System.out.println("Introduce una ciudad :");
			
			
			Statement sql = conexion.createStatement();
			
			String nombre = ent.nextLine();
			
			
			
			
			ResultSet res = sql.executeQuery("select * from city where Name='"+nombre+"';");   // Primera sentencia que comprueba si esta la primera condicion se da
			
			if(res.next()) { // Si es diferente a null entrara en el condicionante
				System.out.println("La ciudad Existe "+nombre);
				ResultSet res2 = sql.executeQuery("select t1.id ,t1.Name from city t1 join country t2 on t1.id = t2.Capital where t1.Name ='"+nombre+"';");
				if(res2.next()) { //Si el segundo query es diferente a null entrara
					
					System.out.println("Es una Capital de pais");
					
					ResultSet res3 = sql.executeQuery("select t1.Language from countrylanguage t1 join country t2 on t2.Code = t1.CountryCode join city t3 on  t2.Capital = t3.id where t3.Name = '"+nombre+"';");
					if(res3.next()) {
						System.out.println("En"+nombre+" se habla :");
						while(res3.next()) {
							System.out.println(res3.getString(1));
						}
						res.close();
						res2.close();
						res3.close();
					}else {
						System.out.println(" No se habla ningun idioma");
					}
					
				}else {
					ResultSet res4 = sql.executeQuery("select t1.Language from countrylanguage t1 join city t2 on t1.CountryCode = t2.CountryCode where Name='"+nombre+"';");

					System.out.println("No es una capital");
					if(res4.next()) {
						System.out.println("En "+nombre+" se habla :");
						while(res4.next()) {
							System.out.println(res4.getString(1));

						}
						res4.close();
					}else {
						System.out.println("No se conocen los idiomas hablados en " +nombre);
					}
				}
				
				
			}else{
				System.out.println(" La ciudad no existe " +nombre);
			
				res.first();
				while(res.next()) {
					System.out.println(res.getString(2)+" "+ res.getString(3)+" " +res.getString(4));
				}
				res.close();	
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

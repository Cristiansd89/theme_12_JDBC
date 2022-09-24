package Practica_Examen_;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OficinaExamen {

	public static void main(String[] args) {
		
		
		String url = "jdbc:mysql://localhost:3306/classicmodels"; 
		String usuario = "trivial";
		String password = "abc123";
		
		
		try {
			Connection conexion = DriverManager.getConnection(url, usuario, password);
			System.out.println("Conexion establecida");
			
			
			
			
			String query = "select distinct orderNumber from orderdetails where quantityOrdered > 40 and productCode ='S24_2022';";
			PreparedStatement sql =  conexion.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			ResultSet res = sql.executeQuery();
			res.beforeFirst();
			while(res.next()) {
				
				
				
				
				String query2 = "SELECT  distinct customerNumber FROM orders WHERE orderNumber= ?";
				PreparedStatement sql2 =  conexion.prepareStatement(query2, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
				sql2.setString(1, res.getString(1));
				ResultSet res2 = sql2.executeQuery();
				
				while(res2.next()) {
					
				
					
					String query3 ="select distinct customerName,contactFirstName,contactLastName,phone from customers WHERE customerNumber = ?" ;
					PreparedStatement sql3 =  conexion.prepareStatement(query3, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
					sql3.setString(1, res2.getString(1));
					ResultSet res3 = sql3.executeQuery();
					
					while(res3.next()) {
						System.out.println(""+ res3.getString(1)+ ""+ res3.getString(2)+""+ res3.getString(3)+""+ res3.getString(4));
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

package Pre_Examen;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Practica_3 {

	public static void main(String[] args) {
		
		String url = "jdbc:mysql://localhost:3306/classicmodels"; 
		String usuario = "trivial";
		String password = "abc123";
		Connection conexion;
		
		
		try {
			conexion = DriverManager.getConnection(url, usuario, password);
			
			String query = "select * from orderdetails where productCode = 'S24_2022' and quantityOrdered > 40";
			
			PreparedStatement sql = conexion.prepareStatement(query,ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet res = sql.executeQuery();
			
			res.last(); // Se coloca el puntero en la ultima posicion
			int n = res.getRow(); // Devuelve el numero de filas leidas con la query

			if(n != 0) {
				res.beforeFirst();
				while(res.next()) {
					res.getString(1);
					
					String query2 = "select * from orders where orderNumber = ?";
					PreparedStatement sql2 = conexion.prepareStatement(query2,ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
					
					sql2.setString(1, res.getString(1));
					ResultSet res2 = sql2.executeQuery();
					res2.beforeFirst();
					while(res2.next()) {
						
						//System.out.println(res2.getString(7));
						String query3 ="select customerName,contactFirstName,contactLastName,phone from customers where customerNumber = ? ";
						PreparedStatement sql3 = conexion.prepareStatement(query3,ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
						sql3.setString(1, res2.getString(7));
						
						ResultSet res3 = sql3.executeQuery();
						res3.beforeFirst();
						
						while(res3.next()) {
							System.out.println(""+res3.getString(1)+ " / "+res3.getString(2)+" / "+res3.getString(3)+" / "+res3.getString(4));
						
					}
					
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

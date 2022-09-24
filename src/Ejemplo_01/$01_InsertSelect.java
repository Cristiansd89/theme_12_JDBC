package Ejemplo_01;

import java.sql.Statement;//Representa la sentencia sql
import java.sql.ResultSet; // Resultado que devuelve esa cuenta
import java.sql.DriverManager;

import java.sql.SQLException;
import java.sql.*;

public class $01_InsertSelect {

	public static void main(String[] args) {
		/* Paso 01 : Conencrtamos a la BBDD (identificarnos)
		 * Para ello siempre utilizamos la misma linea de código :
		 * Connection conn = DriverManager.getConnection(url, usuario, password);
		 * Esta linea puede lanzar error, por lo que debemos atraparla en una estructura  tyr / catch 
		 * 
		 */
		
		
		String url = "jdbc:mysql://localhost:3306/dam2"; 
		String usuario ="profe";
		String password ="abc123";
		
		/* jdbc:mysql: - Identifica el servicio al que nos  vamos a conetar.Esta parte es fije.
		 * //localhost: - Identifica la máquina en la que reside la BBDD; en este caso es nuestro servidor;
		 * 3306 - Puerto al que nos estamos conectando . El puerto  3306  es el que se instala por defecto 
		 * dam2 . Es el nombre de la BBDD a la que nos estamos conectando
		 * 
		 */
		
		try {
			
			//Creamos la conexion
			Connection conn = DriverManager.getConnection(url, usuario, password);
			
			//Trabajamos con la BBDD :Para ello, Creamos una query
			Statement sql = conn.createStatement(); 
			
			String i = "INSERT INTO alumnos  VALUES(NULL,'Joe Maria','Morales','Vázquez')";
			//Insertamos un registro nuevo en la BBDD
			sql.executeUpdate(i);
			
			//Alamacenanos  el resultado de la query en  una variable a la que hemos llamado "resultado
			ResultSet res = sql.executeQuery("select * from alumnos"); // almacenaria el resultado dentro de la variable res
			//Recorremos  el resultado de la query 
			while(res.next()) {
				System.out.println(res.getString(3)+" " +res.getString(4));//
			}
			
			//CErramos la conexion
			
			//Se puede el flujo de insercion
			sql.close();
			
			//Se puede cerrar el flujo del resultado
			res.close();
			
			
			//Para cerrar la conexion
			conn.close();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}

	}

}

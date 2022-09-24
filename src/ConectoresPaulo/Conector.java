package ConectoresPaulo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conector {
	
	private static String url = "jdbc:mysql://localhost:3306/dam2?serverTimezone=UTC"; //?serverTimezone=UTC esta parte es para corregir la diferencia horaria con el servidor mysql.(si no te da fallo no hace falta que lo pongas).
	private static String usuario = "....";                                             //usuario.
	private static String password = ".....";                                           //contraseña.
	private static Connection conectar = null;                                         //Objeto conector.
	
	//M�todo de tipo Connection, me devuelve una conexion.
	public static Connection conectar() {

	    try {
	    	//1.Crear conexion.
			conectar = DriverManager.getConnection(url, usuario, password);
			System.out.println("Conexi�n establecida");
			
		} catch (SQLException e) {
			System.err.println("Error en la conexi�n "+ e.getMessage());
			e.printStackTrace();
		}
	    return conectar;
		
	}

}

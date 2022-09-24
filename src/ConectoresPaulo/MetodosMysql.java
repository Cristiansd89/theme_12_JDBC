package ConectoresPaulo;

import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import ConectoresPaulo.Conector;

/* 
 * Dentro de esta clase creamos todos los m�todos, por ejm:
 * Selects, Drops, Updates, etc. 
 */
public class MetodosMysql {
	
	private static Connection conect;
	private static Statement st = null;
	private static ResultSet rs = null;
	
	//M�todo para visualizar registros.
	 public static void mostrar() {
		 
		  String sel = "SELECT * FROM alumnos";
		  
		 try {
			 //Invocamos el metodo conectar de la clase Conector.
			 conect = Conector.conectar();
			 st = conect.createStatement();
			 
			 //Ejecutamos la query.
			 rs = st.executeQuery(sel);
			 
			 //Recorremos la tabla.
			 while(rs.next()) {
				 System.out.println(rs.getString("id")+" "+rs.getString("nombre")+" "+rs.getString("apellido1")+" "+rs.getString("apellido2"));
				 
			 }
			 //Cerramos las sentencias y conecci�nes.
			 st.close();
			 rs.close();
			 conect.close();			
			 
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		 
	}
    
	public static void main(String[] args) {
		
		System.out.println("*** Mostramos registros de la BBDD Dam2  ****");
        MetodosMysql.mostrar();
	}

}

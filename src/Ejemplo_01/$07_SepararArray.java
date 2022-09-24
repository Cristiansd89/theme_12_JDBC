package Ejemplo_01;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;



public class $07_SepararArray {
	
	 public static void main(String[] args) {

	        String url = "jdbc:mysql://localhost:3306/dam2";
	        String usuario = "gonzalo";
	        String password = "abc123";

	        try {
	        	/*
	        	 * Creamo la conxion con la BBDD
	        	 */
	            
	        	Connection conn =  DriverManager.getConnection(url, usuario, password);
	        	
	        	/*
	        	 *  Trabajamos con la BBDD :Para ello, Creamos una query
	        	 *  Valores por defecto Statement sql = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
					TYPE_SCROLL_INSENSITIVE Sirve para recorrer la tabla
					CONCUR_UPDATABLE para que todos lo cambios que se hacen reflejen en la base de datos
	        	 */
	            Statement sql = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);


	            final String eof = null;


	            FileReader reader = new FileReader(args[0]);
	            BufferedReader flujoE = new BufferedReader(reader);

	            try {
	                String frase = flujoE.readLine();
	                sql.executeUpdate("DELETE FROM alumnos");

	                while (frase != eof) {
	                    String[] sinComas = frase.split(",");
	                    String query = "INSERT INTO alumnos VALUES (NULL, ?, ?, ?)";
	                    PreparedStatement stmt = conn.prepareStatement(query);
	                    stmt.setString(1, sinComas[0]);
	                    stmt.setString(2, sinComas[1]);
	                    stmt.setString(3, sinComas[2]);
	                    stmt.executeUpdate();
	                    frase = flujoE.readLine();
	                }
	                reader.close();
	            } catch (IOException e) {
	                System.err.println("Error en la salida de datos.");
	            }


	            ResultSet rs = sql.executeQuery("SELECT * FROM alumnos");
	            while (rs.next()) {

	                System.out.println(rs.getString(2) + rs.getString(3) + rs.getString(4));

	            }

	        } catch (SQLException error) {
	            System.err.println(error.getMessage());
	        }
	        catch (FileNotFoundException error){
	            System.err.println("El archivo indicado no existe");
	        }

	    }
}

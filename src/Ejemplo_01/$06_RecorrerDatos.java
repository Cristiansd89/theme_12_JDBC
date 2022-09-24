package Ejemplo_01;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


public class $06_RecorrerDatos {

	public static void main(String[] args) {
		
		// Paramettos de conexion con la Base de DAtos
		String url = "jdbc:mysql://localhost:3306/dam2"; 
		String usuario = "superProfe";
		String password = "abc123";
		
		JFrame ventana;
		
		
		
		
		try {
			
			//Creamos la conexion con la BBDD
			Connection conn = DriverManager.getConnection(url, usuario, password);
			//Trabajamos con la BBDD :Para ello, Creamos una query
			
			
			//Valores por defecto Statement sql = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			//TYPE_SCROLL_INSENSITIVE Sirve para recorrer la tabla
			//CONCUR_UPDATABLE para que todos lo cambios que se hacen reflejen en la base de datos
			Statement sql = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			
			
			ventana = new JFrame("Listado de alumnos"); // Creamos la ventana
			//Creamos la tabla
			DefaultTableModel model = new DefaultTableModel();
			JTable tabla = new JTable(model); // Visualizar la tabla
			
			model.addColumn("nombre");
			model.addColumn("apellidos");
			model.addColumn("Clave"); // Añanidimos las columnas
			
			//viculamos la tabla con la ventana
			JScrollPane scroll = new JScrollPane(tabla); // Le pasamos la tablas para crear un scroll
			ventana.getContentPane().add(scroll);
			
			
			//Visualizamos la ventana que hemos creado
			ventana.pack();
			ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			ventana.setVisible(true);
			
			
			
			
			
			//Alamacenanos  el resultado de la query en  una variable a la que hemos llamado "resultado
			ResultSet res = sql.executeQuery("select * from alumnos"); // almacenaria el resultado dentro de la variable res
			//Recorremos  el resultado de la query 
			while(res.next()) {
				System.out.println(res.getString(2)+" "+ res.getString(3)+" " +res.getString(4));
				Object[] fila = new Object[3];
				fila [0] = res.getInt(2);
				fila [1] = res.getInt(3);
				fila [2] = res.getInt(4);
				model.addRow(fila);
				
			}
				
					
			//CErramos la conexion
			conn.close();

			
			
		}catch(SQLException e) {
			System.out.println("Ha ocurrido un Error");
			System.out.println("Cogido del Error :" +e.getErrorCode()); // Muestra el codigo de Error
			System.out.println("Mensaje :  " + e.getMessage());		   	// Motivo del Error
			System.out.println("Estado : " +e.getSQLState());		  	// Este cogido hace referencia al estado de la base de datos(Otro Codigo)
		}
		

	}
		
	}



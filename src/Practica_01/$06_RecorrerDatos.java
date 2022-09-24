package Practica_01;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


public class $06_RecorrerDatos {
	
	/*
	 * Atributos
	 */
	String url = "jdbc:mysql://localhost:3306/dam2"; 
	String usuario = "superProfe";
	String password = "abc123";
	
	
	JFrame ventana; 
	JButton boton;
	
	DefaultTableModel model;
	JTable tabla;
	
	JScrollPane scroll;

	public static void main(String[] args) {
	
			
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

			
		
		

	}
	public void crearElementos() {
		
		
		try {
			Connection conn = DriverManager.getConnection(url, usuario, password);
			Statement sql = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			
			ventana = new JFrame("Listado de alumnos"); // Creamos la ventana
			
			//Creamos la tabla
			 model = new DefaultTableModel();
			 tabla = new JTable(model); // Visualizar la tabla
			
			
			JScrollPane scroll = new JScrollPane(tabla); // Creamos un Scroll y se lo añadimos a la tabla
			
			
		}catch(SQLException e) {
				e.printStackTrace();
			}
		
	}
	
	public void añadirElementos() {
		
		model.addColumn("nombre");
		model.addColumn("apellidos");
		model.addColumn("Clave"); // Añanidimos las columnas
		
		ventana.getContentPane().add(scroll);
		
		
		//Visualizamos la ventana que hemos creado
		ventana.pack();
		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ventana.setVisible(true);
		
		
		}
	
	public void registrarEventos {
		boton.
		
	}
}
		
	



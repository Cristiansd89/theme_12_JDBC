package PaqueteConector;

public class TestConector {

	public static void main(String[] args) {
		
		String alumnos="alumnos";
		String nombreColumna ="nombre";
		String Condicion ="Rodrigo";
		
		Conector primeraConexion = new Conector();
		
		
		
		//primeraConexion.eliminarDatos("alumnos","nombre","Joe");
		
		primeraConexion.insertaDatos("Fernando","Alonso","Garcia");
		
		System.out.println("----------MODIFICACION---------");
		System.out.println(" ");
		
		///primeraConexion.modificarDatos("apellido1", "Hitler", "nombre", "Pepe");
		primeraConexion.mostarDatos(alumnos);
	}

}

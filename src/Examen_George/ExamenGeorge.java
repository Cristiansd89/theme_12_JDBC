package Examen_George;

import java.sql.*;

public class ExamenGeorge {

    public static void main(String[] args) {

        int codigoEmpleadoPrueba = 115;

        //datos_empleado(codigoEmpleadoPrueba);
        //transladar_departamento(2,1400);
        borrar_dependiente(20);



    }

    public static void datos_empleado(int codigoEmpleado) {
        String url = "jdbc:mysql://localhost:3306/examen";
        String usuario = "examen";
        String password = "Abc1234+"; //Me equivoque y me falto colocar la d.

        try {
            Connection conn = DriverManager.getConnection(url, usuario, password);
            Statement stm = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            System.out.println("Conexion realizada correctamente!");

            String query = "SELECT jobs.job_title, employees.manager_id, departments.department_name, locations.city " +
                    "FROM employees " +
                    "JOIN jobs ON jobs.job_id = employees.job_id " +
                    "JOIN departments ON departments.department_id = employees.department_id " +
                    "JOIN locations ON locations.location_id = departments.location_id " +
                    "WHERE employees.employee_id = " + codigoEmpleado + ";";

            ResultSet rs = stm.executeQuery(query);
            rs.last();
            int numeroFilas = rs.getRow();

           if (numeroFilas == 0) {
               System.out.println("El empleado no existe.");
           } else {
               rs.beforeFirst();
               while (rs.next()) {
                   System.out.println("Cargo: " + rs.getString("jobs.job_title"));
                   System.out.println("Departamento: " + rs.getString("departments.department_name"));
                   System.out.println("Ciudad: " + rs.getString("locations.city"));

                   int idJefe = rs.getInt("employees.manager_id");

                   query = "SELECT employees.first_name, employees.last_name from employees WHERE employees.employee_id = " + idJefe + ";";
                   rs = stm.executeQuery(query);

                   while (rs.next()) {
                       System.out.println("Jefe: " + rs.getString("employees.first_name") + " " + rs.getString("employees.last_name"));
                   }

               }
           }

            conn.close();
        } catch (
                SQLException e) {
            System.out.println("Ha ocurrido un error: ");
            System.out.println("Codigo de error: " + e.getErrorCode());
            System.out.println("Mensaje: " + e.getMessage());
            System.out.println("Estado " + e.getSQLState());
        }
    }

    public static void transladar_departamento(int codigoDepartamento, int codigoLocalizacion) {
        String url = "jdbc:mysql://localhost:3306/examen";
        String usuario = "examen";
        String password = "Abc1234+"; //Me equivoque y me falto colocar la d.

        try {
            Connection conn = DriverManager.getConnection(url, usuario, password);
            Statement stm = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            System.out.println("Conexion realizada correctamente!");

            String query = "SELECT departments.department_name, locations.city, countries.country_name " +
                    "FROM departments " +
                    "JOIN locations ON departments.location_id = locations.location_id " +
                    "JOIN countries ON locations.country_id = countries.country_id " +
                    "WHERE departments.department_id = "+ codigoDepartamento + ";";
            ResultSet rs = stm.executeQuery(query);
            rs.last();
            int numeroFilas = rs.getRow();
            String ciudadOriginal = rs.getString("locations.city");
            String paisOriginal = rs.getString("countries.country_name");


            if (numeroFilas != 1) {
                System.out.println("El departamento no existe.");
            } else {
                rs.beforeFirst();

                while (rs.next()) {

                    System.out.println("Ciudad original: " + ciudadOriginal);
                    System.out.println("Pais original: " + paisOriginal + "\n");

                    query = "UPDATE departments SET departments.location_id = "+ codigoLocalizacion + " WHERE departments.department_id = " + codigoDepartamento;
                    Statement stm2 = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                    stm2.executeUpdate(query);

                    String ciudadDestino = rs.getString("locations.city");
                    String paisDestino = rs.getString("countries.country_name");

                    System.out.println("Ciudad destino: " + ciudadDestino);
                    System.out.println("Pais destino: " + paisDestino + "\n");

                }




            }
            conn.close();
        } catch (
                SQLException e) {
            System.out.println("Ha ocurrido un error: ");
            System.out.println("Codigo de error: " + e.getErrorCode());
            System.out.println("Mensaje: " + e.getMessage());
            System.out.println("Estado " + e.getSQLState());
        }

    }

    public static void borrar_dependiente(int codigoDependiente) {
        String url = "jdbc:mysql://localhost:3306/examen";
        String usuario = "examen";
        String password = "Abc1234+"; //Me equivoque y me falto colocar la d.

        try {
            Connection conn = DriverManager.getConnection(url, usuario, password);
            Statement stm = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            System.out.println("Conexion realizada correctamente!");

            String query = "SELECT dependents.first_name, dependents.last_name, dependents.relationship, employees.first_name, employees.last_name " +
                    "FROM dependents " +
                    "JOIN employees on dependents.employee_id = employees.employee_id " +
                    "WHERE dependents.dependent_id = " + codigoDependiente + ";";

            ResultSet rs = stm.executeQuery(query);
            rs.last();
            int numeroFilas = rs.getRow();

            if (numeroFilas != 1) {
                System.out.println("No existe una persona con ese ID.");
            } else {
                    rs.beforeFirst();
                    while (rs.next()) {
                        System.out.println("Nombre empleado: " + rs.getString("dependents.first_name") + " " + rs.getString("dependents.last_name"));
                        System.out.println("Relacion: " + rs.getString("dependents.relationship"));
                        System.out.println("Nombre dependiente: " + rs.getString("employees.first_name") + " " + rs.getString("employees.last_name"));

                    }

                    query = "DELETE FROM dependents WHERE dependent_id = " + codigoDependiente + ";";
                    Statement stm2 = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                    stm2.executeUpdate(query);
            }
            conn.close();
        } catch (
                SQLException e) {
            System.out.println("Ha ocurrido un error: ");
            System.out.println("Codigo de error: " + e.getErrorCode());
            System.out.println("Mensaje: " + e.getMessage());
            System.out.println("Estado " + e.getSQLState());
        }
    }
}

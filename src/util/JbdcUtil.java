package util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * @author Adrián Gómez
 */
public class JbdcUtil {

    private static final String RUTA_PROPERTIES = "./BD/conexion.properties";

    public static Connection getConnection() {
        Properties props = new Properties();

        try (FileInputStream fis = new FileInputStream(RUTA_PROPERTIES)) {
            props.load(fis);
        } catch (IOException e) {
            System.out.println("Error al leer el fichero properties: " + e.getMessage());
            return null;
        }

        String url = props.getProperty("db.url");
        String user = props.getProperty("db.user");
        String password = props.getProperty("db.password", "");

        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            System.out.println("Error de sql: " + e.getMessage());
            return null;
        }
    }

    public static boolean executeUpdate(String fichero, Connection conexion) {
        boolean insercionExitosa = false;
        try {
            conexion.setAutoCommit(false);
            BufferedReader in = new BufferedReader(new FileReader(fichero));
            Statement sentencia = conexion.createStatement();
            sentencia.execute(in.readAllAsString());
            in.close();
            conexion.commit();
            insercionExitosa = true;
        } catch (SQLException e) {
            System.out.println("Se ha producido un error accediendo a la base de datos " + e.getMessage());
            try {
                conexion.rollback();
            } catch (SQLException eRL) {
                System.out.println("Se ha producido un error al tratar de deshacer la transacción " + eRL.getMessage());
            }
        } catch (FileNotFoundException e) {
            System.out.println("El fichero que se está pasando no es accesible por el programa " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Se ha producido un error tratando de acceder al fichero " + e.getMessage());
        } finally {
            try {
                conexion.setAutoCommit(true);
            } catch (SQLException e) {
                System.out.println("Se ha producido un error al iniciar el auto commit " + e.getMessage());
            }
        }

        return insercionExitosa;
    }
}
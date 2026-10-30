package util;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Hugo Romay y Pablo
 */

public class crearBD {

    public static void main(String[] args) {

        try {
            Files.createDirectories(Path.of("BD"));

            String script = Files.readString(Path.of("BD/AvaliacionDiaria.sql"));

            try (Connection con = util.JbdcUtil.getConnection();
                 Statement st = con.createStatement()) {

                String[] sentencias = script.split(";");

                for (int i = 0; i < sentencias.length; i++) {

                    String sentencia = limpiar(sentencias[i]);

                    if (!sentencia.isEmpty()) {
                        st.execute(sentencia);
                    }
                }

                System.out.println("Base de datos generada correctamente.");
            }

        } catch (IOException e) {
            System.out.println("Error al leer sql: " + e.getMessage());

        } catch (SQLException e) {
            System.out.println("Error al generar la base de datos: " + e.getMessage());
        }
    }

    public static String limpiar(String sentencia) {

        String[] lineas = sentencia.split("\n");
        String resultado = "";

        for (int i = 0; i < lineas.length; i++) {

            String linea = lineas[i].trim();

            if (!linea.startsWith("--") && !linea.isEmpty()) {
                resultado = resultado + linea + " ";
            }
        }

        return resultado.trim();
    }
}

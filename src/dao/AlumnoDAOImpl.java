package dao;

import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import dto.AlumnoConRegistroDTO;
import dto.AlumnoDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Ignacio MR
 *         AlumnoDAOImplTran
 */
public class AlumnoDAOImpl implements AlumnoDAO {
    private Connection con;

    public AlumnoDAOImpl(Connection con) {
        this.con = con;
    }

    @Override
    public List<AlumnoDTO> listarTodos() {
        List<AlumnoDTO> alumnos = null;

        String sql = "SELECT * FROM alumnos";
        try (Statement sentenciaSQL = con.createStatement();
                ResultSet rs = sentenciaSQL.executeQuery(sql);) {
            if (rs.next()) {
                alumnos = new ArrayList<>();
                do {
                    alumnos.add(new AlumnoDTO(
                            rs.getInt("id"),
                            rs.getString("nombre"),
                            rs.getString("apellidos"),
                            rs.getString("curso")));
                } while (rs.next());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return alumnos;
    }

    @Override
    public int insertar(AlumnoDTO alumno) {
        String sql = """
                INSERT INTO alumnos
                (nombre, apellidos, curso)
                VALUES
                (?, ?, ?)
                """;
        try (PreparedStatement sentenciaSQL = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
            sentenciaSQL.setString(1, alumno.getNombre());
            sentenciaSQL.setString(2, alumno.getApellidos());
            sentenciaSQL.setString(3, alumno.getCurso());
            if (sentenciaSQL.executeUpdate() > 0) {
                try (ResultSet claves = sentenciaSQL.getGeneratedKeys()) {
                    if (claves.next()) {
                        return claves.getInt(1);
                    }
                    // Si no generó claves, sale por -1
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            return -1;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public boolean eliminar(int id) {
        String sql = """
                DELETE FROM alumnos
                WHERE ID = ?
                """;
        try (PreparedStatement sentenciaSQL = con.prepareStatement(sql);) {
            sentenciaSQL.setInt(1, id);
            return sentenciaSQL.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public AlumnoDTO buscarPorId(int id) {
        AlumnoDTO alumnoRes = null;

        String sql = """
                SELECT * FROM alumnos
                WHERE ID = ?
                """;
        try (PreparedStatement sentenciaSQL = con.prepareStatement(sql);) {

            sentenciaSQL.setInt(1, id);

            ResultSet rs = sentenciaSQL.executeQuery();
            if (rs.next()) {
                alumnoRes = new AlumnoDTO(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("apellidos"),
                        rs.getString("curso"));
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return alumnoRes;
    }

    @Override
    public boolean actualizar(AlumnoDTO alumno) {
        String sql = """
                UPDATE alumnos
                SET nombre = ?,
                    apellidos = ?,
                    curso = ?
                WHERE ID = ?
                """;
        try (PreparedStatement sentenciaSQL = con.prepareStatement(sql);) {
            sentenciaSQL.setString(1, alumno.getNombre());
            sentenciaSQL.setString(2, alumno.getApellidos());
            sentenciaSQL.setString(3, alumno.getCurso());

            sentenciaSQL.setInt(4, alumno.getId());
            return sentenciaSQL.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Recupera todos los alumnos de la base de datos y devuelve uno al aleatorio.
     * 
     * @author AmerCz
     */
    @Override
    public AlumnoDTO sortearAlumno() {
        List<AlumnoDTO> alumnos = new ArrayList<>();

        String sql = "SELECT * FROM alumnos";

        try (Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                alumnos.add(new AlumnoDTO(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("apellidos"),
                        rs.getString("curso")));
            }

            Collections.shuffle(alumnos);

        } catch (SQLException e) {
            System.out.println("Error sql: " + e.getMessage());
        }

        return alumnos.get(new Random().nextInt(alumnos.size()));
    }

    @Override
    public void listarAlumnoConRegistrosYMedia() {

        List<AlumnoConRegistroDTO> alumnos = new ArrayList<>();
        String sql = """

                SELECT
                    a.id,
                    a.nombre,
                    a.apellidos,
                    a.curso,
                    r.nombre AS ejercicio,
                    r.fecha,
                    r.puntuacion,
                    ROUND(AVG(r.puntuacion) OVER (
                        PARTITION BY a.id
                    ), 2) AS media
                FROM alumnos a
                LEFT JOIN registro_ejercicios r
                    ON a.id = r.id_alumno
                ORDER BY a.apellidos, a.nombre, r.fecha;

                        """;

        try (Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                LocalDate fecha;
                if (rs.getDate("fecha") != null) {
                    fecha = rs.getDate("fecha").toLocalDate();
                } else {
                    fecha = null;
                }

                alumnos.add(new AlumnoConRegistroDTO(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("apellidos"),
                        rs.getString("curso"),
                        rs.getString("ejercicio"),
                        fecha,
                        rs.getDouble("puntuacion"),
                        rs.getDouble("media")));

            }

        } catch (SQLException e) {
            System.out.println("Error sql: " + e.getMessage());
        }

        for (AlumnoConRegistroDTO alumnoConRegistroDTO : alumnos) {
            System.out.println(alumnoConRegistroDTO);
        }

    }

}

package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import dto.RegistroConAlumnoDTO;
import dto.RegistroDTO;

public class RegistroDAOImpl implements RegistroDAO {
    private Connection con;

    public RegistroDAOImpl(Connection con) {
        this.con = con; 
    }

    @Override
    public boolean insertar(RegistroDTO registro) {
        String sql = """
            INSERT INTO registro_ejercicios (nombre, fecha, id_alumno, puntuacion)
            VALUES (?, ?, ?, ?)
        """;

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, registro.getNombre());
            ps.setDate(2, Date.valueOf(registro.getFecha()));
            ps.setInt(3, registro.getId_alumno());
            
            if (registro.getPuntuacion() != null) {
                ps.setInt(4, registro.getPuntuacion());
            } else {
                ps.setNull(4, java.sql.Types.INTEGER);
            }

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error SQL: " + e.getMessage());

            return false;
        }
    }
  
    /**@author Ignacio R. */  
    @Override
    public List<RegistroConAlumnoDTO> listarConAlumno() {
        String sql = """
                SELECT id_alumno, alumno.nombre AS nombreAlumno, alumno.apellidos AS apellidosAlumno, reg.id AS idRegistro, reg.nombre AS nombreRegistro, reg.fecha AS fechaRegistro, reg.puntuacion AS puntuacionRegistro FROM registro_ejercicios reg INNER JOIN alumnos alumno ON reg.id_alumno = alumno.id;
                """;

        List<RegistroConAlumnoDTO> listaConAlumnos = new ArrayList<>();
        try (Statement stm = con.createStatement();
             ResultSet rs = stm.executeQuery(sql);) {

            while (rs.next()) {
                listaConAlumnos.add(new RegistroConAlumnoDTO(
                        rs.getInt("id_alumno"),
                        rs.getString("nombreAlumno"),
                        rs.getString("apellidosAlumno"),
                        rs.getInt("idRegistro"),
                        rs.getString("nombreRegistro"),
                        rs.getDate("fechaRegistro"),
                        rs.getInt("puntuacionRegistro")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaConAlumnos;
    }  

    /**@author Samuel */ 
    @Override
    public List<RegistroDTO> listarRegistros() {
        List<RegistroDTO> registros = null;

        String sql = """
                select * from registro_ejercicios
                """;

        try (Statement sentencia = con.createStatement();
                ResultSet rs = sentencia.executeQuery(sql);) {

            registros = new ArrayList<>();
            while (rs.next()) {
                registros.add(new RegistroDTO(rs.getString("nombre"), rs.getDate("fecha").toLocalDate(),
                        rs.getInt("id_alumno"), rs.getInt("puntuacion")));
            }

        } catch (SQLException e) {
            System.out.println("Error SQL: " + e.getMessage());
        }

        return registros;
    }

}

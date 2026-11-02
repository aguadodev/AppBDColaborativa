package dao;

import java.util.List;

import dto.AlumnoConRegistroDTO;
import dto.AlumnoDTO;

/**
 * @author Ignacio MR
 *         AlumnoDAO
 */
public interface AlumnoDAO {
    public List<AlumnoDTO> listarTodos();

    public int insertar(AlumnoDTO alumno);

    public boolean eliminar(int id);

    public AlumnoDTO buscarPorId(int id);

    public boolean actualizar(AlumnoDTO alumno);

    public void listarAlumnoConRegistrosYMedia();

    // Otros métodos
    public AlumnoDTO sortearAlumno();
}

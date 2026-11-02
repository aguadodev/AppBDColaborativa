package dao;

import java.util.List;

import dto.RegistroConAlumnoDTO;
import dto.RegistroDTO;

public interface RegistroDAO {
    public List<RegistroConAlumnoDTO> listarConAlumno();
    public boolean insertar(RegistroDTO registro);
    public List<RegistroDTO> listarRegistros();
}

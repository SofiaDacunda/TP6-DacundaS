package resol.DacundaS.dao;

import resol.DacundaS.dto.EmpleadoDTO;
import java.sql.SQLException;
import java.util.List;

public interface EmpleadoDAO {
    List<EmpleadoDTO> findAllConAntiguedadYBono() throws SQLException;
    List<EmpleadoDTO> cumpleaniosMesActual() throws SQLException;
}
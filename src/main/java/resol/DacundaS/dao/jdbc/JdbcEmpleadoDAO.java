package resol.DacundaS.dao.jdbc;

import resol.DacundaS.dao.EmpleadoDAO;
import resol.DacundaS.dto.EmpleadoDTO;
import resol.DacundaS.util.Conexion;

import java.sql.*;
        import java.util.ArrayList;
import java.util.List;

public class JdbcEmpleadoDAO implements EmpleadoDAO {

    @Override
    public List<EmpleadoDTO> findAllConAntiguedadYBono() throws SQLException {
        String sql = """
            SELECT e.id_empleado,
                   (e.nombre || ' ' || e.apellido)             AS nombre_completo,
                   COALESCE(EXTRACT(YEAR FROM AGE(CURRENT_DATE, e.fecha_contratacion))::int, 0) AS antiguedad,
                   CASE
                     WHEN e.fecha_contratacion IS NULL THEN 0
                     WHEN EXTRACT(YEAR FROM AGE(CURRENT_DATE, e.fecha_contratacion)) >= 10 THEN 20
                     WHEN EXTRACT(YEAR FROM AGE(CURRENT_DATE, e.fecha_contratacion)) >= 5  THEN 10
                     ELSE 5
                   END                                         AS bonificacion,
                   l.nombre                                    AS localidad
            FROM empleado e
            LEFT JOIN localidad l ON l.id_localidad = e.id_localidad
            ORDER BY e.id_empleado
        """;
        List<EmpleadoDTO> out = new ArrayList<>();
        try (Connection c = Conexion.obtener();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                EmpleadoDTO dto = new EmpleadoDTO();
                dto.setIdEmpleado(rs.getInt("id_empleado"));
                dto.setNombreCompleto(rs.getString("nombre_completo"));
                dto.setAntiguedad(rs.getInt("antiguedad"));
                dto.setBonificacion(rs.getInt("bonificacion"));
                dto.setLocalidad(rs.getString("localidad"));
                out.add(dto);
            }
        }
        return out;
    }

    @Override
    public List<EmpleadoDTO> cumpleaniosMesActual() throws SQLException {
        String sql = """
            SELECT e.id_empleado,
                   (e.nombre || ' ' || e.apellido) AS nombre_completo,
                   l.nombre                        AS localidad
            FROM empleado e
            LEFT JOIN localidad l ON l.id_localidad = e.id_localidad
            WHERE EXTRACT(MONTH FROM e.fecha_nacimiento) = EXTRACT(MONTH FROM CURRENT_DATE)
            ORDER BY e.apellido, e.nombre
        """;
        List<EmpleadoDTO> out = new ArrayList<>();
        try (Connection c = Conexion.obtener();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                EmpleadoDTO dto = new EmpleadoDTO();
                dto.setIdEmpleado(rs.getInt("id_empleado"));
                dto.setNombreCompleto(rs.getString("nombre_completo"));
                dto.setLocalidad(rs.getString("localidad"));
                out.add(dto);
            }
        }
        return out;
    }
}
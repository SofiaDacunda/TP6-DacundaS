package resol.DacundaS.dao.jdbc;

import resol.DacundaS.dao.ClienteDAO;
import resol.DacundaS.dto.ClienteDTO;
import resol.DacundaS.util.Conexion;

import java.sql.*;
        import java.util.ArrayList;
import java.util.List;

public class JdbcClienteDAO implements ClienteDAO {

    private static ClienteDTO map(ResultSet rs) throws SQLException {
        ClienteDTO c = new ClienteDTO();
        c.setIdCliente(rs.getString("id_cliente"));
        c.setNombreCompleto(rs.getString("nombre_contacto"));
        c.setNombreEmpresa(rs.getString("nombre_empresa"));
        c.setTipoEmpresa(rs.getString("tipo_empresa"));
        c.setLocalidad(rs.getString("localidad"));
        return c;
    }

    @Override public List<ClienteDTO> findAll() throws SQLException {
        String sql = """
            SELECT c.id_cliente,
                   (c.nombre_contacto) AS nombre_contacto,
                   c.nombre_empresa,
                   et.nombre AS tipo_empresa,
                   l.nombre  AS localidad
            FROM cliente c
            LEFT JOIN empresa_tipo et ON et.id_empresa_tipo = c.id_empresa_tipo
            LEFT JOIN localidad    l  ON l.id_localidad     = c.id_localidad
            ORDER BY c.id_cliente
        """;
        List<ClienteDTO> out = new ArrayList<>();
        try (Connection cn = Conexion.obtenerConexion();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) out.add(map(rs));
        }
        return out;
    }

    @Override public ClienteDTO findById(String id) throws SQLException {
        String sql = """
            SELECT c.id_cliente,
                   (c.nombre_contacto) AS nombre_contacto,
                   c.nombre_empresa,
                   et.nombre AS tipo_empresa,
                   l.nombre  AS localidad
            FROM cliente c
            LEFT JOIN empresa_tipo et ON et.id_empresa_tipo = c.id_empresa_tipo
            LEFT JOIN localidad    l  ON l.id_localidad     = c.id_localidad
            WHERE c.id_cliente = ?
        """;
        try (Connection cn = Conexion.obtenerConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? map(rs) : null;
            }
        }
    }

    @Override public void insert(ClienteDTO c) throws SQLException {
        String sql = """
            INSERT INTO cliente (id_cliente, nombre_empresa, nombre_contacto, id_localidad, id_empresa_tipo)
            VALUES (?, ?, ?, ?, ?)
        """;
        try (Connection cn = Conexion.obtenerConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, c.getIdCliente());
            ps.setString(2, c.getNombreEmpresa());
            ps.setString(3, c.getNombreCompleto());
            ps.setInt(4, 10);
            ps.setInt(5, 1);
            ps.executeUpdate();
        }
    }

    @Override public int update(String id, ClienteDTO c) throws SQLException {
        String sql = """
            UPDATE cliente
               SET nombre_empresa=?, nombre_contacto=?
             WHERE id_cliente=?
        """;
        try (Connection cn = Conexion.obtenerConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, c.getNombreEmpresa());
            ps.setString(2, c.getNombreCompleto());
            ps.setString(3, id);
            return ps.executeUpdate();
        }
    }

    @Override public int delete(String id) throws SQLException {
        try (Connection cn = Conexion.obtenerConexion();
             PreparedStatement ps = cn.prepareStatement("DELETE FROM cliente WHERE id_cliente=?")) {
            ps.setString(1, id);
            return ps.executeUpdate();
        }
    }
}
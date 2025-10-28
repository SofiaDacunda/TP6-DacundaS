package resol.DacundaS.dao.jdbc;

import resol.DacundaS.dao.ProductoDAO;
import resol.DacundaS.dto.ProductoDTO;
import resol.DacundaS.util.Conexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcProductoDAO implements ProductoDAO {

    private static ProductoDTO map(ResultSet rs) throws SQLException {
        ProductoDTO p = new ProductoDTO();
        p.setIdProducto(rs.getInt("id_producto"));
        p.setNombreProducto(rs.getString("nombre_producto"));
        p.setPrecioUnidad(rs.getBigDecimal("precio_unidad"));
        p.setUnidadesEnExistencia(rs.getInt("unidades_en_existencia"));
        p.setNivelNuevoPedido(rs.getInt("nivel_nuevo_pedido"));
        p.setSuspendido(rs.getBoolean("suspendido"));

        p.calcularEstado();
        return p;
    }

    @Override
    public List<ProductoDTO> findAll() throws SQLException {
        String sql = """
            SELECT id_producto, nombre_producto, precio_unidad,
                   unidades_en_existencia, nivel_nuevo_pedido, suspendido
            FROM producto
            ORDER BY id_producto
            """;
        List<ProductoDTO> out = new ArrayList<>();
        try (Connection cn = Conexion.obtenerConexion();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) out.add(map(rs));
        }
        return out;
    }

    @Override
    public List<ProductoDTO> findDisponibles() throws SQLException {
        String sql = """
            SELECT id_producto, nombre_producto, precio_unidad,
                   unidades_en_existencia, nivel_nuevo_pedido, suspendido
            FROM producto
            WHERE suspendido = FALSE
            ORDER BY id_producto
            """;
        List<ProductoDTO> out = new ArrayList<>();
        try (Connection cn = Conexion.obtenerConexion();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) out.add(map(rs));
        }
        return out;
    }

    @Override
    public List<ProductoDTO> findNecesitanReposicion() throws SQLException {
        String sql = """
            SELECT id_producto, nombre_producto, precio_unidad,
                   unidades_en_existencia, nivel_nuevo_pedido, suspendido
            FROM producto
            WHERE unidades_en_existencia <= nivel_nuevo_pedido
            ORDER BY id_producto
            """;
        List<ProductoDTO> out = new ArrayList<>();
        try (Connection cn = Conexion.obtenerConexion();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) out.add(map(rs));
        }
        return out;
    }

    @Override
    public int insert(ProductoDTO p) throws SQLException {
        if (p.getNombreProducto() == null || p.getPrecioUnidad() == null) {
            throw new IllegalArgumentException("Nombre y precio no pueden ser nulos");
        }

        String sql = """
            INSERT INTO producto
              (nombre_producto, precio_unidad, unidades_en_existencia,
               nivel_nuevo_pedido, suspendido)
            VALUES (?, ?, ?, ?, ?)
            RETURNING id_producto
            """;
        try (Connection cn = Conexion.obtenerConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1,  p.getNombreProducto());
            ps.setBigDecimal(2, p.getPrecioUnidad());
            ps.setInt(3,     p.getUnidadesEnExistencia());
            ps.setInt(4,     p.getNivelNuevoPedido());
            ps.setBoolean(5, p.getSuspendido());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt(1);
                    System.out.println("Producto insertado con ID: " + id);
                    return id;
                }
            }
        }
        throw new SQLException("No se obtuvo id generado de producto");
    }

    @Override
    public int update(ProductoDTO p) throws SQLException {
        String sql = """
            UPDATE producto SET
              nombre_producto = ?,
              precio_unidad = ?,
              unidades_en_existencia = ?,
              nivel_nuevo_pedido = ?,
              suspendido = ?
            WHERE id_producto = ?
            """;
        try (Connection cn = Conexion.obtenerConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1,  p.getNombreProducto());
            ps.setBigDecimal(2, p.getPrecioUnidad());
            ps.setInt(3,     p.getUnidadesEnExistencia());
            ps.setInt(4,     p.getNivelNuevoPedido());
            ps.setBoolean(5, p.getSuspendido());
            ps.setInt(6,     p.getIdProducto());

            int rows = ps.executeUpdate();
            System.out.println("Producto actualizado: " + p.getIdProducto());
            return rows;
        }
    }

    @Override
    public int delete(int id) throws SQLException {
        String sql = "DELETE FROM producto WHERE id_producto = ?";
        try (Connection cn = Conexion.obtenerConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, id);
            int rows = ps.executeUpdate();
            System.out.println("Producto eliminado: " + id);
            return rows;
        }
    }
}
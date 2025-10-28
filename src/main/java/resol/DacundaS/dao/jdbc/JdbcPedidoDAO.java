package resol.DacundaS.dao.jdbc;

import resol.DacundaS.dao.PedidoDAO;
import resol.DacundaS.dto.PedidoDTO;
import resol.DacundaS.util.Conexion;

import java.math.BigDecimal;
import java.sql.*;
        import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class JdbcPedidoDAO implements PedidoDAO {

    // ========================= Helpers =========================
    private static void setIntOrNull(PreparedStatement ps, int idx, Integer v) throws SQLException {
        if (v == null) ps.setNull(idx, Types.INTEGER);
        else ps.setInt(idx, v);
    }
    private static void setDateOrNull(PreparedStatement ps, int idx, LocalDate d) throws SQLException {
        if (d == null) ps.setNull(idx, Types.DATE);
        else ps.setDate(idx, Date.valueOf(d));
    }
    private static void setBigOrNull(PreparedStatement ps, int idx, BigDecimal v) throws SQLException {
        if (v == null) ps.setNull(idx, Types.NUMERIC);
        else ps.setBigDecimal(idx, v);
    }
    private static String estadoDerivado(LocalDate fEnvio, LocalDate fEntrega) {
        if (fEnvio == null) return "Pendiente";
        if (fEntrega == null) return "Enviado";
        return "Entregado";
    }

    private static PedidoDTO mapPedido(ResultSet rs) throws SQLException {
        PedidoDTO p = new PedidoDTO();
        p.setIdPedido(rs.getInt("id_pedido"));
        int idCli = rs.getInt("id_cliente");  p.setIdCliente(rs.wasNull()? null : idCli);
        int idEmp = rs.getInt("id_empleado"); p.setIdEmpleado(rs.wasNull()? null : idEmp);
        Date fp = rs.getDate("fecha_pedido");   p.setFechaPedido(fp == null? null : fp.toLocalDate());
        Date fe = rs.getDate("fecha_envio");    p.setFechaEnvio(fe == null? null : fe.toLocalDate());
        Date fent = rs.getDate("fecha_entrega");p.setFechaEntrega(fent == null? null : fent.toLocalDate());
        String est = rs.getString("estado");
        if (est == null || est.isBlank()) est = estadoDerivado(p.getFechaEnvio(), p.getFechaEntrega());
        p.setEstado(est);
        BigDecimal total = rs.getBigDecimal("monto_total");
        p.setMontoTotal(total);
        return p;
    }

    private static int nextIdPedido(Connection cn) throws SQLException {
        try (PreparedStatement ps = cn.prepareStatement("SELECT COALESCE(MAX(id_pedido),0)+1 FROM pedido");
             ResultSet rs = ps.executeQuery()) {
            rs.next();
            return rs.getInt(1);
        }
    }

    // ========================= Consultas =========================

    @Override
    public List<PedidoDTO> findByCliente(String idCliente) throws SQLException {
        String sql = """
            SELECT p.id_pedido, p.id_cliente, p.id_empleado,
                   p.fecha_pedido, p.fecha_envio, p.fecha_entrega,
                   p.estado, p.monto_total
            FROM pedido p
            WHERE CAST(p.id_cliente AS TEXT) = ?
            ORDER BY p.id_pedido
        """;
        List<PedidoDTO> out = new ArrayList<>();
        try (Connection cn = Conexion.obtenerConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, idCliente);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) out.add(mapPedido(rs));
            }
        }
        return out;
    }

    @Override
    public PedidoDTO findById(int id) throws SQLException {
        String sql = """
            SELECT p.id_pedido, p.id_cliente, p.id_empleado,
                   p.fecha_pedido, p.fecha_envio, p.fecha_entrega,
                   p.estado, p.monto_total
            FROM pedido p
            WHERE p.id_pedido = ?
        """;
        try (Connection cn = Conexion.obtenerConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next()? mapPedido(rs) : null;
            }
        }
    }

    @Override
    public int insert(PedidoDTO p) throws SQLException {
        String insPed = """
            INSERT INTO pedido (id_pedido, id_cliente, id_empleado, fecha_pedido, fecha_envio, fecha_entrega, estado, monto_total)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?)
        """;
        String insDet = """
            INSERT INTO detalle_pedido (id_pedido, id_producto, cantidad, precio_unidad, descuento)
            VALUES (?, ?, ?, ?, ?)
        """;
        try (Connection cn = Conexion.obtenerConexion()) {
            cn.setAutoCommit(false);
            try {
                int id = nextIdPedido(cn);
                p.setIdPedido(id);

                try (PreparedStatement ps = cn.prepareStatement(insPed)) {
                    ps.setInt(1, id);
                    setIntOrNull(ps, 2, p.getIdCliente());
                    setIntOrNull(ps, 3, p.getIdEmpleado());
                    setDateOrNull(ps, 4, p.getFechaPedido());
                    setDateOrNull(ps, 5, p.getFechaEnvio());
                    setDateOrNull(ps, 6, p.getFechaEntrega());
                    ps.setString(7, p.getEstado());
                    setBigOrNull(ps, 8, p.getMontoTotal());
                    ps.executeUpdate();
                }

                try (PreparedStatement psd = cn.prepareStatement(insDet)) {
                    for (PedidoDTO.Detalle d : p.getDetallesParaInsert()) {
                        psd.setInt(1, id);
                        setIntOrNull(psd, 2, d.getIdProducto());
                        setIntOrNull(psd, 3, d.getCantidad());
                        setBigOrNull(psd, 4, d.getPrecioUnidad());
                        setBigOrNull(psd, 5, d.getDescuento());
                        psd.addBatch();
                    }
                    psd.executeBatch();
                }

                cn.commit();
                return 1;
            } catch (Exception ex) {
                cn.rollback();
                throw ex;
            } finally {
                cn.setAutoCommit(true);
            }
        }
    }

    @Override
    public int update(PedidoDTO p) throws SQLException {
        String sql = """
            UPDATE pedido SET
                id_cliente=?, id_empleado=?, fecha_pedido=?, fecha_envio=?, fecha_entrega=?, estado=?, monto_total=?
            WHERE id_pedido=?
        """;
        try (Connection cn = Conexion.obtenerConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            setIntOrNull(ps, 1, p.getIdCliente());
            setIntOrNull(ps, 2, p.getIdEmpleado());
            setDateOrNull(ps, 3, p.getFechaPedido());
            setDateOrNull(ps, 4, p.getFechaEnvio());
            setDateOrNull(ps, 5, p.getFechaEntrega());
            ps.setString(6, p.getEstado());
            setBigOrNull(ps, 7, p.getMontoTotal());
            ps.setInt(8, p.getIdPedido());
            return ps.executeUpdate();
        }
    }

    @Override
    public int delete(int id) throws SQLException {
        try (Connection cn = Conexion.obtenerConexion()) {
            cn.setAutoCommit(false);
            try {
                try (PreparedStatement ps = cn.prepareStatement("DELETE FROM detalle_pedido WHERE id_pedido=?")) {
                    ps.setInt(1, id);
                    ps.executeUpdate();
                }
                try (PreparedStatement ps = cn.prepareStatement("DELETE FROM pedido WHERE id_pedido=?")) {
                    ps.setInt(1, id);
                    int rows = ps.executeUpdate();
                    cn.commit();
                    return rows;
                }
            } catch (Exception ex) {
                cn.rollback();
                throw ex;
            } finally {
                cn.setAutoCommit(true);
            }
        }
    }

    @Override
    public int cambiarEstado(int idPedido, String nuevoEstado) throws SQLException {
        String sql = "UPDATE pedido SET estado=? WHERE id_pedido=?";
        try (Connection cn = Conexion.obtenerConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, nuevoEstado);
            ps.setInt(2, idPedido);
            return ps.executeUpdate();
        }
    }
}
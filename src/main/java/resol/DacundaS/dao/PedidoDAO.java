package resol.DacundaS.dao;

import resol.DacundaS.dto.PedidoDTO;
import java.sql.SQLException;
import java.util.List;

public interface PedidoDAO {
    List<PedidoDTO> findByCliente(String idCliente) throws SQLException;
    PedidoDTO findById(int id) throws SQLException;

    int insert(PedidoDTO p) throws SQLException;
    int update(PedidoDTO p) throws SQLException;
    int delete(int id) throws SQLException;

    int cambiarEstado(int idPedido, String nuevoEstado) throws SQLException;
}
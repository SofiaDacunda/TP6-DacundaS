package resol.DacundaS.dao;

import resol.DacundaS.dto.ProductoDTO;
import java.sql.SQLException;
import java.util.List;

public interface ProductoDAO {
    List<ProductoDTO> findAll() throws SQLException;
    List<ProductoDTO> findDisponibles() throws SQLException;
    List<ProductoDTO> findNecesitanReposicion() throws SQLException;

    int insert(ProductoDTO p) throws SQLException;
    int update(ProductoDTO p) throws SQLException;
    int delete(int id) throws SQLException;
}
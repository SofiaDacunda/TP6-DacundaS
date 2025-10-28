package resol.DacundaS.dao;

import resol.DacundaS.dto.ClienteDTO;
import java.sql.SQLException;
import java.util.List;

public interface ClienteDAO {
    List<ClienteDTO> findAll() throws SQLException;
    ClienteDTO findById(String id) throws SQLException;

    void insert(ClienteDTO c) throws SQLException;
    int  update(String id, ClienteDTO c) throws SQLException;
    int  delete(String id) throws SQLException;
}
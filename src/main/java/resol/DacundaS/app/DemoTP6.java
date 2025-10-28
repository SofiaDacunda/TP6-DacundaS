package resol.DacundaS.app;

import java.math.BigDecimal;

import resol.DacundaS.dao.ProductoDAO;
import resol.DacundaS.dao.jdbc.JdbcProductoDAO;
import resol.DacundaS.dto.ProductoDTO;

public class DemoTP6 {
    public static void main(String[] args) throws Exception {

        // === PRODUCTOS ===
        ProductoDAO prodDao = new JdbcProductoDAO();

        System.out.println("\n== PRODUCTOS (5) ==");
        prodDao.findAll()
                .stream()
                .limit(5)
                .forEach(System.out::println);

        System.out.println("\n== INSERT/UPDATE/DELETE PRODUCTO ==");

        // INSERT
        ProductoDTO p = new ProductoDTO();
        p.setNombreProducto("Snack TP6");
        p.setPrecioUnidad(new BigDecimal("3.50"));
        p.setUnidadesEnExistencia(6);
        p.setNivelNuevoPedido(10);
        p.setSuspendido(false);

        int idProd = prodDao.insert(p);
        System.out.println("Insert prod id = " + idProd);

        // UPDATE
        ProductoDTO actualizado = new ProductoDTO();
        actualizado.setIdProducto(idProd);
        actualizado.setNombreProducto("Snack TP6");
        actualizado.setPrecioUnidad(new BigDecimal("3.99"));
        actualizado.setUnidadesEnExistencia(6);
        actualizado.setNivelNuevoPedido(10);
        actualizado.setSuspendido(false);

        int up = prodDao.update(actualizado);
        System.out.println("Update rows = " + up);

        // DELETE
        int del = prodDao.delete(idProd);
        System.out.println("Delete rows = " + del);
    }
}
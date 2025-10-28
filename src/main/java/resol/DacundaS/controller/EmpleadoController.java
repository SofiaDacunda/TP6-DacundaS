package resol.DacundaS.controller;

import resol.DacundaS.dao.jdbc.JdbcEmpleadoDAO;
import resol.DacundaS.dto.EmpleadoDTO;

import java.util.List;

public class EmpleadoController {
    private final JdbcEmpleadoDAO dao = new JdbcEmpleadoDAO();

    public void listarAntiguedadYBonos() {
        try {
            List<EmpleadoDTO> lista = dao.findAllConAntiguedadYBono();
            if (lista == null || lista.isEmpty()) System.out.println("(sin empleados)");
            else lista.forEach(System.out::println);
        } catch (Exception e) { e.printStackTrace(); }
    }

    public void cumpleaniosMes() {
        try {
            List<EmpleadoDTO> lista = dao.cumpleaniosMesActual();
            if (lista == null || lista.isEmpty()) System.out.println("(sin cumpleañeros este mes)");
            else lista.forEach(System.out::println);
        } catch (Exception e) { e.printStackTrace(); }
    }
}
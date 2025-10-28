package resol.DacundaS;

import resol.DacundaS.controller.*;
        import resol.DacundaS.view.*;
        import java.util.Scanner;

public class Principal {
    public static void main(String[] args) {
        var in = new Scanner(System.in);
        var main = new MenuView();

        var empC  = new EmpleadoController();
        var prodC = new ProductoController();
        var cliC  = new ClienteController();
        var pedC  = new PedidoController();
        var cliV  = new ClienteView();
        var pedV  = new PedidoView();

        while (true) {
            switch (main.menuPrincipal(in)) {
                case 1 -> { // Empleados (igual que TP5)
                    System.out.println("— usa tus opciones existentes —");
                    empC.listarAntiguedadYBonos();
                }
                case 2 -> { // Productos (igual que TP5)
                    System.out.println("— usa tus opciones existentes —");
                    prodC.listarDisponibles();
                }
                case 3 -> { // Clientes
                    loopClientes:
                    while (true) {
                        switch (cliV.menu(in)) {
                            case 1 -> cliC.listar();
                            case 2 -> cliC.agregar(cliV.pedirId(in), cliV.pedirNombre(in), cliV.pedirEmpresa(in));
                            case 3 -> cliC.actualizar(cliV.pedirId(in), cliV.pedirNombre(in), cliV.pedirEmpresa(in));
                            case 4 -> cliC.eliminar(cliV.pedirId(in));
                            case 0 -> { break loopClientes; }
                            default -> System.out.println("Opción inválida");
                        }
                    }
                }
                case 4 -> { // Pedidos
                    loopPedidos:
                    while (true) {
                        switch (pedV.menu(in)) {
                            case 1 -> pedC.listarPorCliente(pedV.pedirCliente(in));
                            case 2 -> pedC.crearPedidoDemo();
                            case 3 -> pedC.actualizarFechas(pedV.pedirIdPedido(in), true, false);
                            case 4 -> pedC.cambiarEstado(pedV.pedirIdPedido(in), pedV.pedirEstado(in));
                            case 5 -> pedC.eliminar(pedV.pedirIdPedido(in));
                            case 0 -> { break loopPedidos; }
                            default -> System.out.println("Opción inválida");
                        }
                    }
                }
                case 0 -> { System.out.println("Salir."); return; }
                default -> System.out.println("Opción inválida");
            }
        }
    }
}
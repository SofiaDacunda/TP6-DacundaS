package resol.DacundaS.view;

import java.util.Scanner;

public class PedidoView {
    public int menu(Scanner in) {
        System.out.println("\n===== MENÚ PEDIDOS =====");
        System.out.println("1. Listar pedidos de un cliente");
        System.out.println("2. Crear nuevo pedido");
        System.out.println("3. Actualizar fechas (envío/entrega)");
        System.out.println("4. Cambiar estado (Pendiente/Enviado/Entregado)");
        System.out.println("5. Eliminar pedido");
        System.out.println("0. Volver");
        System.out.print("> ");
        return Integer.parseInt(in.nextLine().trim());
    }
    public String pedirCliente(Scanner in) { System.out.print("ID cliente: "); return in.nextLine().trim(); }
    public int pedirIdPedido(Scanner in) { System.out.print("ID pedido: "); return Integer.parseInt(in.nextLine().trim()); }
    public String pedirEstado(Scanner in) { System.out.print("Nuevo estado (Pendiente/Enviado/Entregado): "); return in.nextLine().trim(); }
}
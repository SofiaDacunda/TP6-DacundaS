package resol.DacundaS.view;

import java.util.Scanner;

public class MenuView {
    public int menuPrincipal(Scanner in) {
        System.out.println("\n===== MENÚ PRINCIPAL =====");
        System.out.println("1. Gestión de Empleados");
        System.out.println("2. Gestión de Productos");
        System.out.println("3. Gestión de Clientes");
        System.out.println("4. Gestión de Pedidos");
        System.out.println("0. Salir");
        System.out.print("> ");
        return Integer.parseInt(in.nextLine().trim());
    }
}
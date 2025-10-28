package resol.DacundaS.view;

import java.util.Scanner;

public class ClienteView {
    public int menu(Scanner in) {
        System.out.println("\n===== MENÚ CLIENTES =====");
        System.out.println("1. Listar clientes");
        System.out.println("2. Agregar cliente");
        System.out.println("3. Actualizar cliente");
        System.out.println("4. Eliminar cliente");
        System.out.println("0. Volver");
        System.out.print("> ");
        return Integer.parseInt(in.nextLine().trim());
    }

    public String pedirId(Scanner in) {
        System.out.print("ID cliente (5 chars): ");
        return in.nextLine().trim();
    }

    public String pedirNombre(Scanner in) {
        System.out.print("Nombre contacto: ");
        return in.nextLine().trim();
    }

    public String pedirEmpresa(Scanner in) {
        System.out.print("Nombre empresa: ");
        return in.nextLine().trim();
    }
}
import java.util.Scanner;

public class MenuPrincipal {

    public static void main(String[] args) {
        Biblioteca biblioteca = new Biblioteca();
        Scanner teclado = new Scanner(System.in);
        int opcion;

        do {
            //--- menu
            System.out.println("\n ============== MENU PRINCIPAL =================");
            System.out.println("    1.Listar todos los libros");
            System.out.println("    2. Buscar libros por ID");
            System.out.println("    3. Burcar libro por título" );
            System.out.println("    4. Agregar nuevo libro");
            System.out.println("    5. Actualizar Libro");
            System.out.println("    6. Eliminar Libro");
            System.out.println("    7. Prestar Libro");
            System.out.println("    8. Devolver libro");
            System.out.println("    0. Salir");
            System.out.println("    Selecciona una opción:");

            while(!teclado.hasNext()){
                System.out.println("Opcion no valida. Ingrese número");
                teclado.next();
            }
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion){
                case 1 : biblioteca.listarLibros(); break;
                case 2 : biblioteca.buscarPorID(); break;
                case 3 : biblioteca.buscarPorTitulo(); break;
                case 4 : biblioteca.agregarLibro(); break;
                case 5 : biblioteca.actualizarLibro(); break;
                case 6 : biblioteca.eliminarLibro(); break;
                case 7 : biblioteca.prestarLibro(); break;
                case 8 : biblioteca.devolverLibro(); break;
                case 0 : System.out.println("Hasta Luego");; break;
                default: System.out.println("_Opcion no valida intente de nuevo");



            }

        } while (opcion != 0);
            teclado.close();
    }
}

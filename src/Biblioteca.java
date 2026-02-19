import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Biblioteca {
    // arrays donde  se guardan los libros en memoria
    private ArrayList<Libro> libros;
    private int contadorId; // generar id´s automaticos
    private Scanner teclado;

    //constructor
    public Biblioteca() {
        this(true);
    }

    /**
     * constructor flexible: si cargarEjemplos= false, la lista empieza vacia
     * Los test biblioteca (false)  para tener siempre control de los datos
     */
    public Biblioteca(boolean cargarEjemplos) {
        libros = new ArrayList<>();
        contadorId = 1;
        teclado = new Scanner(System.in);
        if (cargarEjemplos) cargaLibrosEjemplo();
    }

    // cargar de libros

    private void cargaLibrosEjemplo() {
        libros.add(new Libro(contadorId++, "Cien años de Soledad", "Gabriel Garcia Marquéz", 1967, "Novela"));
        libros.add(new Libro(contadorId++, "Don Quijote", "Miguel de Cervantes", 1605, "Novela"));
        libros.add(new Libro(contadorId++, "El Principito", "Antoine de Saint-E", 1943, "Infatil"));
        libros.add(new Libro(contadorId++, "1984", "George Orwell", 1949, "Ciencia Ficción"));
        libros.add(new Libro(contadorId++, "Carmilla", "Sheridan Le Fanu", 1872, "Ficción Gótica"));
    }

    //pensado en test logica del negocio NO USAN SCANNER

    /**
     * create : crear un libro con los datos y lo agramos al arrayList
     *
     * @return el libre recien asignado con el id automatico
     *
     */

    public Libro agregarLibroLogica(String titulo, String autor, int anio, String genero) {
        Libro nuevo = new Libro(contadorId++, titulo, autor, anio, genero);
        libros.add(nuevo);
        return nuevo;
    }

    /**
     * READ buscar un libro por ID
     *
     * @return el existe o null si no lo encuentra
     *
     */
    public Libro buscarLibroPorID(int id) {
        return encontrarLibroPorID(id);
    }

    /**
     * read buscar por titulo
     *
     * @return lista de libros coincidente
     *
     */
    public List<Libro> buscarLibrosPorTitulo(String texto) {
        List<Libro> resultado = new ArrayList<>();
        for (Libro libro : libros){
            if (libro.getTitulo().toLowerCase().contains(texto.toLowerCase())) {
            resultado.add(libro);
        }
    }
        return resultado;
}
   public List<Libro> obtenerTodos(){
        return new ArrayList<>(libros);
   }

   public boolean actualizarLibroLogica(int id, String titulo, String autor, int anio, String genero){
        Libro libro = encontrarLibroPorID(id);
        if (libro == null) return false;
        libro.setTitulo(titulo);
        libro.setAutor(autor);
        libro.setAnio(anio);
        libro.setGenero(genero);
        return true;
   }

   public boolean eliminarLibroLogica(int id) {
        Libro libro = encontrarLibroPorID(id);
        if (libro == null) return false;
        libros.remove(libro);
        return true;

   }

   public boolean prestarLogicaLibro(int id){
       Libro libro = encontrarLibroPorID(id);
       if (libro == null || libro.isPrestado()) return false;
       libro.setPrestado(true);
       return true;
   }

   public boolean devolverLibroLogica(int id){
       Libro libro = encontrarLibroPorID(id);
       if (libro == null || !libro.isPrestado()) return false;
       libro.setPrestado(false);
       return true;
   }
   // @return cantidad total de libros en el ArrayList
   public int getTotalLibros(){
        return libros.size();
   }
    // logica de negocio y vista por consola

    //read-leer: listar todos los libros

    public void listarLibros(){
        if (libros.isEmpty()) {
            System.out.println("\n No tenemos libros registrados");
            return;
        }
        imprimirCabecera();
        for (Libro libro : libros){
            System.out.println(libro.toString());
        }
        imprimirPie();
        System.out.println("  Total de libros: " + libros.size());
    }

    //read- buscar por ID
    public void buscarPorID(){
        System.out.println("\n Ingrese ID a buscar");
        int id = leerEntero();
        Libro libro = encontrarLibroPorID(id);
        if (libro != null){
            imprimirCabecera();
            System.out.println(libro);
            imprimirPie();
        }else {
            System.out.println(" No existe libro con ID ingresado " + id);
        }
    }

    //read- buscar por titulo
    public void buscarPorTitulo(){
        System.out.println("\n Ingrese texto a buscar en el titulo: ");
        String busqueda = teclado.nextLine().toLowerCase();
        boolean encontrado = false;
        imprimirCabecera();
        for (Libro libro : libros){
            if (libro.getTitulo().toLowerCase().contains(busqueda)){
                System.out.println(libro);
                encontrado = true;
            }
        }
        imprimirPie();
        if (!encontrado){
            System.out.println(" No se encontro libro con \"" + busqueda + "\"");
        }
    }

// create - agregar libro

    public void agregarLibro(){
        System.out.println("\n ----Agregar Libro Nuevo----");

        System.out.println("    Titulo  : ");
        String titulo = teclado.nextLine();


        System.out.println("    Autor  : ");
        String autor = teclado.nextLine();


        System.out.println("    Año  : ");
        int anio = leerEntero();

        System.out.println("    Genero  : ");
        String genero = teclado.nextLine();

        Libro nuevo = new Libro(contadorId++, titulo, autor, anio, genero);
        libros.add(nuevo);
        System.out.println("\n Libro agregado con ID: " +nuevo.getId());
    }

// update- actualizar libro
    public void actualizarLibro(){
        System.out.println("\n Ingrese ID a actualizar: ");
        int id = leerEntero();
        Libro libro = encontrarLibroPorID(id);

        if (libro == null){
            System.out.println("No existe un libro con ID: " + id);
        return;
        }

        //mostrar libro actual
        imprimirCabecera();
        System.out.println(libro);
        imprimirPie();

        // sub-menu de actualización
        System.out.println("\n ¿Qué desea actualizar?");
        System.out.println("    1. Título");
        System.out.println("    2. Autor");
        System.out.println("    3. Año");
        System.out.println("    4. Genero");
        System.out.println("    5. Todos los campos");
        System.out.println("    Opcion: ");
        int opcion = leerEntero();

        switch (opcion){
            case 1:
                System.out.println("    Nuevo Título");
                libro.setTitulo(teclado.nextLine());
                break;
            case 2:
                System.out.println("    Nuevo autor");
                libro.setAutor(teclado.nextLine());
                break;
            case 3:
                System.out.println("    Nuevo año");
                libro.setAnio(leerEntero());
                break;
            case 4:
                System.out.println("    Nuevo genero");
                libro.setGenero(teclado.nextLine());
                break;
            case 5:
                System.out.println("    Nuevo Título"); libro.setTitulo(teclado.nextLine());
                System.out.println("    Nuevo autor"); libro.setAutor(teclado.nextLine());
                System.out.println("    Nuevo año"); libro.setAnio(leerEntero());
                System.out.println("    Nuevo genero"); libro.setGenero(teclado.nextLine());
                break;
            default:
                System.out.println("Opcion no válida");
                return;
        }
        System.out.println("\n Libro se actualizo correctamente");

    }

    // delete - eliminar un libro
    public void eliminarLibro(){
        System.out.println("\n Ingrese el ID del libro a eliminar");
        int id = leerEntero();
        Libro libro = encontrarLibroPorID(id);

        if (libro == null) {
            System.out.println("No existe un libro con ID: " + id);
            return;
        }
        //mostrar libro antes de eliminar
        imprimirCabecera();
        System.out.println(libro);
        imprimirPie();

        //pedir confirmacion
        System.out.println("○2n Esta Seguro que desea eliminar este libro S/N: ");
        String confirmacion = teclado.nextLine();

        if (confirmacion.equalsIgnoreCase("S")){
            System.out.println("Libreo eliminado correctamente");
        }else {
            System.out.println("Eliminacion cancelada");
        }
    }

    // prestar libro

    public void prestarLibro(){
        System.out.println("\n Ingrese ID de libro a prestar: ");
        int id = leerEntero();
        Libro libro = encontrarLibroPorID(id);

        if (libro == null) {
            System.out.println("No existe un libro con ID: " + id);
        } else if (libro.isPrestado()) {
            System.out.println("El libro ya esta prestado.");

        }else {
            System.out.println( "Libro \"" + libro.getTitulo() + "\" marcado como prestado.");
        }
    }

    // devolver libro
    public void devolverLibro(){
        System.out.println("\n Ingrese ID de libro a devolver: ");
        int id = leerEntero();
        Libro libro = encontrarLibroPorID(id);

        if (libro == null) {
            System.out.println("No existe un libro con ID: " + id);
        } else if (!libro.isPrestado()) {
            System.out.println("El libro ya esta disponible.");

        }else {
            System.out.println( "Libro \"" + libro.getTitulo() + "\" devuelto.");
        }
    }
    //metodo auxiliar

    private int leerEntero(){
        while (!teclado.hasNextInt()) {
            System.out.println(" Ingrese número válido");
            teclado.next();
        }
        int numero = teclado.nextInt();
        teclado.nextLine();
        return numero;
    }

    private  Libro encontrarLibroPorID(int id){
        for (Libro libro : libros){
            if (libro.getId()==id){
                return libro;
            }
        }
        return null; // no encontrado
    }

    private void imprimirCabecera(){
        System.out.println("\n  +------+---------------------------+----------------------+--------+--------------+-------------+");
        System.out.println("  | ID   | TITULO                    | AUTOR                | ANIO   | GENERO       | ESTADO      |");
        System.out.println("  +------+---------------------------+----------------------+--------+--------------+-------------+");
    }

    private void imprimirPie() {
        System.out.println("  +------+---------------------------+----------------------+--------+--------------+-------------+");
    }
}























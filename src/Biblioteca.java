import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Biblioteca {
    // ─── Estado interno ───────────────────────────────────────────────────────
    private ArrayList<Libro> libros;
    private int contadorId;
    private Scanner teclado;

    // ─── Constructor principal (carga datos de ejemplo) ───────────────────────
    public Biblioteca() {
        this(true);
    }

    /**
     * Constructor flexible: si cargarEjemplos=false, la lista empieza vacía.
     * Los tests usan Biblioteca(false) para tener control total sobre los datos.
     */
    public Biblioteca(boolean cargarEjemplos) {
        libros     = new ArrayList<>();
        contadorId = 1;
        teclado    = new Scanner(System.in);
        if (cargarEjemplos) cargarLibrosEjemplo();
    }

    private void cargarLibrosEjemplo() {
        libros.add(new Libro(contadorId++, "Cien Anios de Soledad", "Gabriel Garcia M.",   1967, "Novela"));
        libros.add(new Libro(contadorId++, "Don Quijote",           "Miguel de Cervantes", 1605, "Novela"));
        Libro ej = new Libro(contadorId++, "El Principito",         "Antoine de Saint-E.", 1943, "Infantil");
        ej.setPrestado(true);
        libros.add(ej);
        libros.add(new Libro(contadorId++, "1984",                  "George Orwell",       1949, "Ciencia Fic."));
        libros.add(new Libro(contadorId++, "Clean Code",            "Robert C. Martin",    2008, "Tecnologia"));
    }

    // ════════════════════════════════════════════════════════════════════════
    //  LÓGICA DE NEGOCIO — métodos testables (no usan Scanner)
    // ════════════════════════════════════════════════════════════════════════

    /**
     * CREATE: Crea un libro con los datos dados y lo agrega al ArrayList.
     * @return el Libro recién creado (con su ID automático asignado).
     */
    public Libro agregarLibroLogica(String titulo, String autor, int anio, String genero) {
        Libro nuevo = new Libro(contadorId++, titulo, autor, anio, genero);
        libros.add(nuevo);
        return nuevo;
    }

    /**
     * READ: Busca un libro por ID.
     * @return el Libro si existe, o null si no se encuentra.
     */
    public Libro buscarLibroPorId(int id) {
        return encontrarLibroPorId(id);
    }

    /**
     * READ: Busca libros cuyo título contenga el texto dado (sin distinguir mayúsculas).
     * @return lista con los libros coincidentes (puede estar vacía).
     */
    public List<Libro> buscarLibrosPorTitulo(String texto) {
        List<Libro> resultado = new ArrayList<>();
        for (Libro libro : libros) {
            if (libro.getTitulo().toLowerCase().contains(texto.toLowerCase())) {
                resultado.add(libro);
            }
        }
        return resultado;
    }

    /**
     * READ: Devuelve una copia de todos los libros registrados.
     */
    public List<Libro> obtenerTodos() {
        return new ArrayList<>(libros);
    }

    /**
     * UPDATE: Actualiza todos los campos de un libro buscado por ID.
     * @return true si se actualizó, false si el ID no existe.
     */
    public boolean actualizarLibroLogica(int id, String titulo, String autor, int anio, String genero) {
        Libro libro = encontrarLibroPorId(id);
        if (libro == null) return false;
        libro.setTitulo(titulo);
        libro.setAutor(autor);
        libro.setAnio(anio);
        libro.setGenero(genero);
        return true;
    }

    /**
     * DELETE: Elimina el libro con el ID dado del ArrayList.
     * @return true si se eliminó, false si el ID no existe.
     */
    public boolean eliminarLibroLogica(int id) {
        Libro libro = encontrarLibroPorId(id);
        if (libro == null) return false;
        libros.remove(libro);
        return true;
    }

    /**
     * BONUS: Presta un libro disponible.
     * @return true si se prestó exitosamente, false si no existe o ya estaba prestado.
     */
    public boolean prestarLibroLogica(int id) {
        Libro libro = encontrarLibroPorId(id);
        if (libro == null || libro.isPrestado()) return false;
        libro.setPrestado(true);
        return true;
    }

    /**
     * BONUS: Devuelve un libro prestado.
     * @return true si se devolvió exitosamente, false si no existe o ya estaba disponible.
     */
    public boolean devolverLibroLogica(int id) {
        Libro libro = encontrarLibroPorId(id);
        if (libro == null || !libro.isPrestado()) return false;
        libro.setPrestado(false);
        return true;
    }

    /** @return cantidad total de libros en el ArrayList. */
    public int getTotalLibros() {
        return libros.size();
    }

    // ════════════════════════════════════════════════════════════════════════
    //  VISTA POR CONSOLA — delegan en los métodos de lógica de arriba
    // ════════════════════════════════════════════════════════════════════════

    public void listarLibros() {
        if (libros.isEmpty()) { System.out.println("\n  ⚠ No hay libros registrados."); return; }
        imprimirCabecera();
        for (Libro libro : libros) System.out.println(libro);
        imprimirPie();
        System.out.println("  Total de libros: " + libros.size());
    }

    public void buscarPorId() {
        System.out.print("\n  Ingrese el ID a buscar: ");
        int id = leerEntero();
        Libro libro = buscarLibroPorId(id);
        if (libro != null) { imprimirCabecera(); System.out.println(libro); imprimirPie(); }
        else System.out.println("  ❌ No existe un libro con ID " + id);
    }

    public void buscarPorTitulo() {
        System.out.print("\n  Ingrese texto a buscar en el título: ");
        String busqueda = teclado.nextLine();
        List<Libro> resultado = buscarLibrosPorTitulo(busqueda);
        imprimirCabecera();
        resultado.forEach(System.out::println);
        imprimirPie();
        if (resultado.isEmpty()) System.out.println("  ❌ Ningún libro coincide con \"" + busqueda + "\"");
    }

    public void agregarLibro() {
        System.out.println("\n  ─── Agregar nuevo libro ───");
        System.out.print("  Título  : "); String titulo = teclado.nextLine();
        System.out.print("  Autor   : "); String autor  = teclado.nextLine();
        System.out.print("  Año     : "); int    anio   = leerEntero();
        System.out.print("  Género  : "); String genero = teclado.nextLine();
        Libro nuevo = agregarLibroLogica(titulo, autor, anio, genero);
        System.out.println("\n  ✅ Libro agregado con ID " + nuevo.getId());
    }

    public void actualizarLibro() {
        System.out.print("\n  Ingrese el ID del libro a actualizar: ");
        int id = leerEntero();
        Libro libro = buscarLibroPorId(id);
        if (libro == null) { System.out.println("  ❌ No existe un libro con ID " + id); return; }
        imprimirCabecera(); System.out.println(libro); imprimirPie();
        System.out.println("\n  ¿Qué desea actualizar?");
        System.out.println("  1. Título  2. Autor  3. Año  4. Género  5. Todos");
        System.out.print("  Opción: ");
        int opcion = leerEntero();
        switch (opcion) {
            case 1: System.out.print("  Nuevo título: "); libro.setTitulo(teclado.nextLine()); break;
            case 2: System.out.print("  Nuevo autor: ");  libro.setAutor(teclado.nextLine());  break;
            case 3: System.out.print("  Nuevo año: ");    libro.setAnio(leerEntero());          break;
            case 4: System.out.print("  Nuevo género: "); libro.setGenero(teclado.nextLine());  break;
            case 5:
                System.out.print("  Nuevo título: "); String t = teclado.nextLine();
                System.out.print("  Nuevo autor: ");  String a = teclado.nextLine();
                System.out.print("  Nuevo año: ");    int    y = leerEntero();
                System.out.print("  Nuevo género: "); String g = teclado.nextLine();
                actualizarLibroLogica(id, t, a, y, g);
                System.out.println("\n  ✅ Libro actualizado correctamente."); return;
            default: System.out.println("  Opción no válida."); return;
        }
        System.out.println("\n  ✅ Libro actualizado correctamente.");
    }

    public void eliminarLibro() {
        System.out.print("\n  Ingrese el ID del libro a eliminar: ");
        int id = leerEntero();
        Libro libro = buscarLibroPorId(id);
        if (libro == null) { System.out.println("  ❌ No existe un libro con ID " + id); return; }
        imprimirCabecera(); System.out.println(libro); imprimirPie();
        System.out.print("\n  ¿Está seguro? (S/N): ");
        if (teclado.nextLine().equalsIgnoreCase("S")) {
            eliminarLibroLogica(id);
            System.out.println("  ✅ Libro eliminado correctamente.");
        } else {
            System.out.println("  ⚠ Eliminación cancelada.");
        }
    }

    public void prestarLibro() {
        System.out.print("\n  Ingrese el ID del libro a prestar: ");
        int id = leerEntero();
        if (buscarLibroPorId(id) == null)    { System.out.println("  ❌ No existe un libro con ID " + id); return; }
        if (!prestarLibroLogica(id))         { System.out.println("  ⚠ El libro ya está prestado."); return; }
        System.out.println("  ✅ Libro marcado como Prestado.");
    }

    public void devolverLibro() {
        System.out.print("\n  Ingrese el ID del libro a devolver: ");
        int id = leerEntero();
        if (buscarLibroPorId(id) == null)    { System.out.println("  ❌ No existe un libro con ID " + id); return; }
        if (!devolverLibroLogica(id))        { System.out.println("  ⚠ El libro ya está disponible."); return; }
        System.out.println("  ✅ Libro devuelto. Ahora Disponible.");
    }

    // ─── Auxiliares ──────────────────────────────────────────────────────────
    private Libro encontrarLibroPorId(int id) {
        for (Libro libro : libros) {
            if (libro.getId() == id) return libro;
        }
        return null;
    }

    private int leerEntero() {
        while (!teclado.hasNextInt()) {
            System.out.print("  ⚠ Ingrese un número válido: ");
            teclado.next();
        }
        int numero = teclado.nextInt();
        teclado.nextLine();
        return numero;
    }

    private void imprimirCabecera() {
        System.out.println("\n  +------+---------------------------+----------------------+--------+--------------+-------------+");
        System.out.println("  | ID   | TITULO                    | AUTOR                | ANIO   | GENERO       | ESTADO      |");
        System.out.println("  +------+---------------------------+----------------------+--------+--------------+-------------+");
    }

    private void imprimirPie() {
        System.out.println("  +------+---------------------------+----------------------+--------+--------------+-------------+");
    }
}



























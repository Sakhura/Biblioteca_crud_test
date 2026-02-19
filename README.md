# 📚 Sistema de Gestión de Biblioteca — CRUD en Java

Ejercicio práctico de programación orientada a objetos que implementa las 4 operaciones **CRUD** (Create, Read, Update, Delete) sobre una colección de libros, usando `ArrayList` en memoria y una interfaz por consola.

---

## 📁 Estructura del Proyecto

```
src/
├── main/java/Biblioteca/
│   ├── Libro.java            ← Modelo (atributos, getters, setters, toString)
│   ├── Biblioteca.java       ← Lógica CRUD + Vista por consola
│   └── MenuPrincipal.java    ← Punto de entrada (método main)
│
└── test/java/Biblioteca/
    ├── LibroTest.java         ← Tests unitarios de la clase Libro
    └── BibliotecaTest.java    ← Tests unitarios de la lógica CRUD
```

---

## 🧱 Clases principales

### `Libro.java` — Modelo
Representa un libro con los atributos: `id`, `titulo`, `autor`, `anio`, `genero` y `prestado`. Todos los atributos son **privados** (encapsulamiento) y se acceden mediante getters y setters. El método `toString()` está sobreescrito para mostrar los datos en formato tabla.

### `Biblioteca.java` — Lógica + Vista
Contiene el `ArrayList<Libro>` y está dividida en dos capas:

- **Métodos de lógica** (`agregarLibroLogica`, `buscarLibroPorId`, etc.): reciben los datos como parámetros, sin usar `Scanner`. Son los que se prueban con tests unitarios.
- **Métodos de consola** (`agregarLibro`, `buscarPorId`, etc.): leen del teclado y luego delegan en los métodos de lógica.

### `MenuPrincipal.java` — Vista
Contiene el `main`. Muestra el menú interactivo con `do-while` y `switch`, y llama a los métodos de `Biblioteca`.

---

## ✅ Tests Unitarios

Los tests usan **JUnit 5** y están organizados en dos archivos.

---

### 📄 `LibroTest.java`

Prueba el comportamiento de la clase `Libro` de forma aislada.

Antes de cada test, `@BeforeEach` crea un libro nuevo con datos fijos para que los tests no se afecten entre sí.

| Test | ¿Qué verifica? |
|------|---------------|
| `testConstructorAsignaAtributos` | Que al crear un `Libro`, todos sus atributos (id, titulo, autor, anio, genero) quedan guardados correctamente. |
| `testLibroNuevoEstaDisponible` | Que un libro recién creado empieza con `prestado = false`. Todo libro nuevo debe estar disponible por defecto. |
| `testSetTitulo` | Que `setTitulo()` reemplaza el título correctamente. |
| `testSetAutor` | Que `setAutor()` reemplaza el autor correctamente. |
| `testSetAnio` | Que `setAnio()` reemplaza el año correctamente. |
| `testSetGenero` | Que `setGenero()` reemplaza el género correctamente. |
| `testSetPrestadoTrue` | Que `setPrestado(true)` marca el libro como prestado. |
| `testSetPrestadoFalse` | Que `setPrestado(false)` regresa el libro a disponible después de haberlo prestado. |
| `testToStringContieneTitulo` | Que el resultado de `toString()` incluye el título del libro. |
| `testToStringMuestraDisponible` | Que `toString()` muestra el texto `"Disponible"` cuando el libro no está prestado. |
| `testToStringMuestraPrestado` | Que `toString()` muestra el texto `"Prestado"` cuando el libro sí está prestado. |
| `testToStringContieneId` | Que `toString()` incluye el ID del libro. |

---

### 📄 `BibliotecaTest.java`

Prueba la lógica CRUD completa. Se usa `new Biblioteca(false)` para iniciar con una lista **vacía** en cada test, teniendo control total sobre los datos sin depender de los libros de ejemplo.

Los tests están agrupados con `@Nested` por operación CRUD para facilitar su lectura.

---

#### 🟢 CREATE — `agregarLibroLogica()`

| Test | ¿Qué verifica? |
|------|---------------|
| `testAgregarAumentaTotal` | Que después de agregar un libro, `getTotalLibros()` devuelve 1. |
| `testAgregarYRecuperar` | Que el libro recién agregado puede encontrarse después por su ID. |
| `testIdsUnicos` | Que al agregar dos libros, cada uno recibe un ID diferente (el contador automático funciona). |
| `testAgregarVarios` | Que agregar 3 libros resulta en un total de 3. |
| `testLibroNuevoDisponible` | Que todo libro agregado comienza con estado Disponible. |

---

#### 🔵 READ — `buscarLibroPorId()` y `buscarLibrosPorTitulo()`

| Test | ¿Qué verifica? |
|------|---------------|
| `testBuscarPorIdExistente` | Que buscar un ID que existe devuelve el libro correcto (no null). |
| `testBuscarPorIdInexistente` | Que buscar un ID que no existe devuelve `null` en lugar de lanzar error. |
| `testBuscarPorTituloParcial` | Que al buscar "Clean" se encuentran los 2 libros que contienen esa palabra en el título. |
| `testBuscarPorTituloSinDistinguirMayusculas` | Que buscar "quijote", "QUIJOTE" y "Quijote" devuelven el mismo resultado (la búsqueda ignora mayúsculas). |
| `testBuscarPorTituloSinCoincidencias` | Que buscar un texto que no existe devuelve una lista vacía (no null, no error). |
| `testObtenerTodos` | Que `obtenerTodos()` devuelve exactamente todos los libros registrados. |

---

#### 🟡 UPDATE — `actualizarLibroLogica()`

| Test | ¿Qué verifica? |
|------|---------------|
| `testActualizarExistente` | Que actualizar un libro existente devuelve `true` y que los 4 campos (titulo, autor, anio, genero) cambian correctamente. |
| `testActualizarIdInexistente` | Que intentar actualizar un ID que no existe devuelve `false` sin lanzar excepción. |
| `testActualizarNoAfectaOtros` | Que actualizar el libro 1 no modifica los datos del libro 2. |

---

#### 🔴 DELETE — `eliminarLibroLogica()`

| Test | ¿Qué verifica? |
|------|---------------|
| `testEliminarExistente` | Que eliminar un libro existente devuelve `true` y que el total de libros baja. |
| `testEliminarYBuscar` | Que después de eliminar un libro, buscarlo por su ID devuelve `null`. |
| `testEliminarIdInexistente` | Que intentar eliminar un ID que no existe devuelve `false` sin lanzar excepción. |
| `testEliminarNoAfectaOtros` | Que eliminar el libro 1 no elimina ni modifica el libro 2. |

---

#### 🟣 BONUS — `prestarLibroLogica()` y `devolverLibroLogica()`

| Test | ¿Qué verifica? |
|------|---------------|
| `testPrestarDisponible` | Que prestar un libro disponible devuelve `true` y cambia su estado a `prestado = true`. |
| `testPrestarLibroYaPrestado` | Que intentar prestar un libro que ya está prestado devuelve `false` (no se puede prestar dos veces). |
| `testDevolverPrestado` | Que devolver un libro prestado devuelve `true` y cambia su estado a `prestado = false`. |
| `testDevolverLibroYaDisponible` | Que intentar devolver un libro que ya está disponible devuelve `false`. |
| `testPrestarIdInexistente` | Que prestar un ID que no existe devuelve `false`. |
| `testDevolverIdInexistente` | Que devolver un ID que no existe devuelve `false`. |

---

## ⚙️ Cómo ejecutar los tests en IntelliJ

1. Abre el proyecto en IntelliJ IDEA.
2. Coloca los archivos `LibroTest.java` y `BibliotecaTest.java` en `src/test/java/Biblioteca/`.
3. Cuando IntelliJ marque en rojo el `import org.junit.jupiter`, haz clic y elige **"Add JUnit 5 to classpath"**.
4. Haz clic derecho sobre cualquier archivo de test → **Run 'LibroTest'** (o `BibliotecaTest`).
5. También puedes correr todos los tests juntos haciendo clic derecho sobre la carpeta `test` → **Run All Tests**.

Si usas **Maven**, agrega esta dependencia en tu `pom.xml`:

```xml
<dependency>
    <groupId>org.junit.jupiter</groupId>
    <artifactId>junit-jupiter</artifactId>
    <version>5.10.0</version>
    <scope>test</scope>
</dependency>
```

---

## 🧠 Conceptos aplicados

| Concepto | Dónde se usa |
|----------|-------------|
| Encapsulamiento (`private` + getters/setters) | `Libro.java` |
| `@Override toString()` | `Libro.java` |
| `ArrayList<Libro>` | `Biblioteca.java` |
| Separación lógica / vista | `Biblioteca.java` (métodos `...Logica` vs métodos de consola) |
| `do-while` + `switch` | `MenuPrincipal.java` |
| Validación de entrada (`leerEntero`) | `Biblioteca.java` |
| Tests unitarios con JUnit 5 | `LibroTest.java`, `BibliotecaTest.java` |
| `@BeforeEach` para datos limpios | Ambos archivos de test |
| `@Nested` para agrupar tests | `BibliotecaTest.java` |

---

> 💡 **Nota para el estudiante:** Los tests solo prueban los métodos de lógica (los que reciben parámetros). Los métodos que usan `Scanner` no se prueban con tests unitarios porque dependen de que alguien escriba en la consola — eso se prueba manualmente ejecutando el programa.
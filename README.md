# LlanquihueTourPolimorfismo
Ejercicio para practicar el polimorfismo entre una estructura de clases por medio de una interfaz que permite la implementacion en multiples clases sin relacion de parentesco.

## Descripción
El programa permite la elavoracion de una coleccion unificada de todas las intancias que integran una interfaz sin importar su super clase inicial.

## Estructura
📁LlanquihueTour
├── 📁resources/
│   ├── 📄BaseDatosCliente.txt
│   ├── 📄BaseDatosColaboradorExterno.txt
│   ├── 📄BaseDatosExcursionCultural.txt
│   ├── 📄BaseDatosGuiaTuristico.txt
│   ├── 📄BaseDatosPaseoLacustre.txt
│   ├── 📄BaseDatosReserva.txt
│   ├── 📄BaseDatosRutaGastronomica.txt
│   ├── 📄BaseDatosTrekkingAltaMontania.txt
│   └── 📄BaseDatosVehiculo.txt
└── 📁src/
    ├── 📁data/
    │   ├── ☕CargarDatos.java
    │   ├── ☕DiccionarioFormularios.java
    │   ├── ☕GeneradorCodigosServicios.java
    │   ├── ☕GestorEntidades.java
    │   └── ☕GestorEscritura.java
    ├── 📁model/
    │   ├── 📁core/
    │   │   ├── ☕ActivoEmpresa.java
    │   │   ├── ☕Persona.java
    │   │   └── ☕ServicioTuristico.java
    │   ├── 📁entities/
    │   │   ├── 📁activities/
    │   │   │   ├── ☕ExcursionCultural.java
    │   │   │   ├── ☕PaseoLacustre.java
    │   │   │   ├── ☕RutaGastronomica.java
    │   │   │   └── ☕TrekkingAltaMontania.java
    │   │   ├── 📁assets/
    │   │   │   └── ☕Vehiculo.java
    │   │   ├── 📁business/
    │   │   │   └── ☕Reserva.java
    │   │   └── 📁people/
    │   │       ├── ☕Cliente.java
    │   │       ├── ☕ColaboradorExterno.java
    │   │       └── ☕GuiaTuristico.java
    │   ├── 📁interfaces/
    │   │   └── ☕Registrable.java
    │   └── 📁valueobjects/
    │       ├── ☕CorreoContacto.java
    │       ├── ☕Rut.java
    │       └── ☕TarjetaCredito.java
    ├── 📁ui/
    │   └── ☕LlanquihueTour.java
    └── 📁view/
        ├── ☕PanelBuscadorRut.java
        ├── ☕VentanaPrincipal.java
        └── ☕VentanaRegistro.java

## Cómo ejecutar
El programa se ejecuta en la clase LlanquihueTour que se encuentra en el paquete ui.

La clase principal crea una interfaz de usuario sencilla que permite visualizar todas las instancias de las clases que implementan la interfaz Registrable.

La interfaz permite filtrar dichos registros por tipo de clase para facilitar la visualizacion de los mismos según los requerimientos del usuario.

Además, la interfaz gráfica cuenta con un botón que nos permite agregar registros nuevos, los cuales seran almacenados en la base de datos correspondientes.

El proceso se lleva a cabo por medio de la elección de la clase que se quiere en un menú desplegable el cual desplegara un formulario con el constructor de la clase correspondiente.

#Actualización

La interfaz cuenta con 3 botones, agregar registro, actualizar pantalla y finalizar.

En agregar registro se añadieron algunos componentes diferentes para controlar las opciones que pueda ingresar el usuario por medio de menus desplegables.
En la ventana principal se añade una función que nos permite obtener los datos de un cliente buscandolo por su rut.
Se añaden diferentes clases y metodos auxiliares para poder tener mayor flexibilidad en este proyecto polimorfico.
# LlanquihueTourPolimorfismo
Ejercicio para practicar el polimorfismo entre una estructura de clases por medio de una interfaz que permite la implementacion en multiples clases sin relacion de parentesco.

## Descripción
El programa permite la elavoracion de una coleccion unificada de todas las intancias que integran una interfaz sin importar su super clase inicial.

## Estructura
📁 LlanquihueTour
├── 📁 resources/                
│   ├── 📄 BaseDatosColaboradorExterno.txt
│   ├── 📄 BaseDatosExcursionCultural.txt
│   ├── 📄 BaseDatosGuiaTuristico.txt
│   ├── 📄 BaseDatosPaseoLacustre.txt
│   ├── 📄 BaseDatosRutaGastronomica.txt
│   ├── 📄 BaseDatosTrekkingAltaMontania.txt
│   └── 📄 BaseDatosVehiculo.txt
│
└── 📁 src/                      
    │
    ├── 📁 data/                 
    │   ├── ☕ CargarDatos.java
    │   ├── ☕ DiccionarioFormularios.java
    │   ├── ☕ GestorEntidades.java
    │   ├── ☕ GestorEscritura.java
    │   └── ☕ GestorInstancias.java
    │
    ├── 📁 model/                
    │   ├── 📁 core/             
    │   │   ├── ☕ ActivoEmpresa.java
    │   │   ├── ☕ Persona.java
    │   │   └── ☕ ServicioTuristico.java
    │   │
    │   ├── 📁 entities/        
    │   │   ├── 📁 activities/
    │   │   │   ├── ☕ ExcursionCultural.java
    │   │   │   ├── ☕ PaseoLacustre.java
    │   │   │   ├── ☕ RutaGastronomica.java
    │   │   │   └── ☕ TrekkingAltaMontania.java
    │   │   ├── 📁 assets/
    │   │   │   └── ☕ Vehiculo.java
    │   │   └── 📁 people/
    │   │       ├── ☕ ColaboradorExterno.java
    │   │       └── ☕ GuiaTuristico.java
    │   │
    │   └── 📁 interfaces/       
    │       └── ☕ Registrable.java
    │
    ├── 📁 ui/                   
    │   └── ☕ LlanquihueTour.java
    │
    └── 📁 view/                 
        ├── ☕ VentanaPrincipal.java
        └── ☕ VentanaRegistro.java

## Cómo ejecutar
El programa se ejecuta en la clase LlanquihueTour que se encuentra en el paquete ui.

La clase principal crea una interfaz de usuario sencilla que permite visualizar todas las instancias que contienen la interfaz Registrable.

La interfaz permite filtrar dichos registros por clase para facilitar la visualizacion de los mismos segun los requerimientos del usuario.

Además, la interfaz gráfica cuenta con un botón que nos permite agregar registros nuevos, los cuales seran almacenados en la base de datos correspondientes.

El proceso se lleva a cabo por medio de la elección de la clase que se quiere en un menú desplegable el cual desplegara un formulario con el constructor de la clase correspondiente.
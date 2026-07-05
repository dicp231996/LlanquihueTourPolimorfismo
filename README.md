# LlanquihueTourPolimorfismo
Ejercicio para practicar el polimorfismo entre una estructura de clases por medio de la herencia.

## Descripción
El programa permite escalar la creacion de nuevas subclases de manera dinamica sin tener que añadir nada mas que una nueva base de datos y nuevas entidades que representen los nuevos servicios.

## Estructura
.
├── resources/
│   ├── BaseDatosExcursionCultural.txt
│   ├── BaseDatosPaseoLacustre.txt
│   ├── BaseDatosRutaGastronomica.txt
│   └── BaseDatosTrekkingAltaMontana.txt
└── src/
├── app/
│   └── LlanquihueTour.java
├── data/
│   └── CargarDatos.java
├── model/
│   ├── core/
│   │   └── ServicioTuristico.java
│   └── entities/
│       ├── ExcursionCultural.java
│       ├── PaseoLacustre.java
│       ├── RutaGastronomica.java
│       └── TrekkingAltaMontana.java
└── util/
└── GestorServicios.java

## Cómo ejecutar
El programa se ejecuta en la clase LlanquihueTour que se encuentra en el paquete app.

Esta clase llamará a la de GestorServicios, a su vez esta llamará a CargarDatos, la cual leera todos los archivos txt de la carpeta resorces que tengan el prefijo "BaseDatos".

Obtendra todos los nombres de las clases que se encuentren dentro del paquete model.entities y con base en los substrings resultantes de quitar el prefijo a los nombres de los archivos txt conseguira instanciar de manera correcta las clases correspondientes a cada base de datos.

Las clases del paquete de entities heredan sus parametros base de la superclase ServicioTuristico que se encuentra en el paquete model.core.
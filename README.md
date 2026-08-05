# LifeDoc Care — Sistema de Citas Médicas

Sistema de control y agendamiento de citas médicas para un centro de salud, desarrollado en Java (Swing) con NetBeans y MySQL.

## Descripción general

LifeDoc Care es una aplicación de escritorio que administra citas médicas, historias clínicas y facturación en un centro médico. Contempla cinco roles: **Usuario**, **Administrador del centro**, **Administrador del sistema**, **Médico** y **Operario**, cada uno con sus propios permisos y vistas dentro del sistema.

## Objetivos clave

- **Autenticación segura**, con validación de contraseñas mediante expresiones regulares (nivel de exigencia medio).
- **Módulo de citas médicas**: registro, consulta y cancelación, con validación para evitar citas duplicadas el mismo día.
- **Historia clínica**: anotaciones médicas, exportación a PDF y almacenamiento de la imagen del paciente en la base de datos.
- **Facturación**, calculada según la categoría del afiliado y su edad.
- **Herencia y polimorfismo**: la clase `Medico` se especializa en las subclases `MedicoGeneral` y `MedicoEspecialista`, donde el especialista recibe una bonificación en el salario. (Esta jerarquía explica la existencia de clases `Medico`/`Medicos` relacionadas dentro del proyecto).

## Requisito de diseño obligatorio

El proyecto exige investigar, justificar e implementar como mínimo **3 patrones de diseño de software** (no vienen predefinidos). Algunas áreas sugeridas donde podrían aplicarse son:

- Gestión de roles y permisos.
- Sistema de notificaciones.
- Búsquedas y filtros.
- Desacoplamiento entre módulos.
- Procesos centralizados.

## Entregables de documentación

Cada equipo mantiene una carpeta compartida en OneDrive con:

- Matriz de stakeholders.
- Requerimientos funcionales y no funcionales.
- Historias de usuario.
- Diagramas UML (casos de uso, secuencia, clases y despliegue) en formato JPG y StarUML.
- Prototipo no funcional en Figma.
- Video explicativo del proyecto.
- Logo del proyecto.

## Requisitos del entorno

- Java JDK 21.
- NetBeans IDE.
- XAMPP (MySQL / MariaDB).

## Tecnologías y librerías usadas

### Interfaz gráfica
- **Java Swing** — Framework nativo de Java para construir interfaces gráficas de escritorio (ventanas, botones, tablas, formularios). Es la base de todas las vistas del proyecto (`OperarioInterfaz`, `MedicoInterfaz`, etc.).
- **LGoodDatePicker-11.2.1.jar** — Componente de selección de fechas y horas para Swing (calendario visual), usado para elegir la fecha y hora de las citas de forma amigable en lugar de campos de texto libres.

### Generación de PDF
- **itextpdf-5.5.13.2.jar** — Librería para crear y manipular documentos PDF desde Java. Es la que probablemente usa la clase `CreadorPdf` para generar historias clínicas y facturas.

### Códigos QR / lectura de imágenes (ZXing)
- **core-3.5.3.jar.zip** — Núcleo de la librería ZXing (Zebra Crossing), usada para generar y leer códigos de barras y QR. Podría emplearse para identificar pacientes o citas mediante códigos QR.
- **javase-3.5.3.jar.zip** — Extensión de ZXing específica para entornos Java SE (escritorio); permite trabajar con imágenes (`BufferedImage`) para generar y leer códigos QR desde una app de escritorio como esta.

### Correo electrónico (JavaMail y dependencias)
- **mail.jar** — API de JavaMail, permite enviar correos electrónicos (SMTP) desde Java. Es la base de la clase `EnvioCorreos.java` para las notificaciones a pacientes.
- **javax.activation-1.1.0.v201105071233.jar** — JAF (Java Activation Framework), dependencia requerida por JavaMail para manejar tipos de datos y adjuntos en los correos.

### Especificaciones Java EE (probablemente heredadas, no todas en uso activo)
- **javax.ejb.jar** — API de Enterprise JavaBeans (Java EE), pensada para componentes de negocio distribuidos y transaccionales. En un proyecto Swing de escritorio con JDBC simple normalmente no se necesita; podría ser un remanente de una plantilla o una dependencia transitiva.
- **javax.faces-api-2.0.jar** — API de JavaServer Faces (JSF), framework para interfaces web. No aplica a una app de escritorio Swing; es señal de que puede tratarse de una dependencia innecesaria en el classpath.
- **javax-inject.jar** — Especificación estándar de inyección de dependencias (`@Inject`, JSR-330). Se usa cuando el proyecto emplea un contenedor de inyección de dependencias; en un proyecto Swing/JDBC simple, sin framework de DI, su uso es opcional o inexistente.

### Logging
- **commons-logging-1.4.0.jar** — Fachada de logging de Apache Commons, que permite registrar mensajes (info, error, debug) delegando a una implementación subyacente. Suele llegar como dependencia transitiva de otras librerías, como MySQL Connector o Spring.

### Base de datos
- **mysql-connector-j-8.0.32.jar** — Driver JDBC oficial de MySQL/Oracle. Es el conector que permite al código Java conectarse a la base de datos MySQL/MariaDB (vía XAMPP) usando JDBC puro, tal como está estructurado el proyecto.
- **MySQL vía XAMPP** — Servidor de base de datos relacional MySQL/MariaDB, gestionado localmente mediante XAMPP (incluye phpMyAdmin para administración visual). Almacena las 22 tablas del sistema (pacientes, citas, médicos, historias clínicas, etc.).
- **JDBC** — Java Database Connectivity, la API estándar de Java para ejecutar SQL y gestionar conexiones a bases de datos. Es el mecanismo que usan las clases DAO (`CitaDao`, etc.) para interactuar con MySQL sin un ORM.

### Seguridad
- **spring-security-crypto-6.4.4.jar** — Módulo de criptografía de Spring Security, independiente del resto del framework Spring. Ofrece utilidades para el hashing de contraseñas (BCrypt, Argon2, etc.) y cifrado. Es la librería usada para implementar el hashing seguro de contraseñas que exige el objetivo de autenticación del proyecto.

## Recursos visuales

- [Dribbble](https://dribbble.com/) — Inspiración de diseño de interfaces.
- [Pinterest](https://co.pinterest.com/) — Referencias visuales adicionales.

## Herramientas de descarga de recursos y creación de paneles

- [Figma](https://www.figma.com) — Prototipado de interfaces.
- [Visualizador de tamaños de imagen](https://gqinformatica.com/herramientas/visualizador-tamanos-imagen-online-calculadora-dimensiones-ratios/) — Calculadora de dimensiones y ratios de imagen.
- [Flaticon](https://www.flaticon.com/) — Íconos para la interfaz.
- [MyInstants](https://www.myinstants.com/es/index/co/) — Efectos de sonido cortos.
- [Freesound](https://freesound.org/) — Banco de sonidos libres.
- [SpeechGen](https://speechgen.io/) — Generación de voz a partir de texto.

## Webgrafía

Documentación y referencias técnicas:

- [Java SE 8 API Documentation](https://docs.oracle.com/javase/8/docs/api/)
- [Stack Overflow](https://stackoverflow.com/questions)

Videos de apoyo (tutoriales y referencias en YouTube):

- [Video 1](https://www.youtube.com/watch?v=sL1s4YyONSg&t=8s)
- [Video 2](https://www.youtube.com/watch?v=nEkM1sA1z7g&t=90s)
- [Video 3](https://www.youtube.com/watch?v=hIBEmpV30Ao&t=148s)
- [Video 4](https://www.youtube.com/watch?v=EpKZw0NNCUA&t=4s)
- [Video 5](https://www.youtube.com/watch?v=uaPB1el-M0k)
- [Video 6](https://www.youtube.com/watch?v=l8zdauUWv9g&t=1518s)
- [Video 7](https://www.youtube.com/watch?v=UmoryILvQ_Q&t=217s)
- [Video 8](https://www.youtube.com/watch?v=8hMWNGGg_OI)
- [Video 9](https://www.youtube.com/watch?v=h2Zb56eVgIM)
- [Video 10](https://www.youtube.com/watch?v=GwjUKVeVtCo)
- [Video 11](https://www.youtube.com/watch?v=Ryf5icqLSKk)
- [Video 12](https://www.youtube.com/watch?v=fVGOwiEyyj4)
- [Video 13](https://www.youtube.com/watch?v=ZggjlwLzrxg)
- [Video 14](https://www.youtube.com/watch?v=jjM4Q6MBKQY)
- [Video 15](https://www.youtube.com/watch?v=sW4FyA-z5Yg&t=850s)
- [Video 16](https://www.youtube.com/watch?v=gx_iVi67fjE)
- [Video 17](https://www.youtube.com/watch?v=aZng9YPL4-s)
- [Video 18](https://www.youtube.com/watch?v=Z6uvh7xM-m0&t=62s)
- [Video 19](https://www.youtube.com/watch?v=uINUPW3xGpE)
- [Video 20](https://www.youtube.com/watch?v=xvvMSJ3nfT8)
- [Video 21](https://www.youtube.com/watch?v=UtoKA2IzE2M)
- [Video 22](https://www.youtube.com/watch?v=SPpditsfY1o)
- [Video 23](https://www.youtube.com/watch?v=j9i2i--xRGw)
- [Video 24](https://www.youtube.com/watch?v=X5llkhXOYIA)

*(Se revisaron todos los enlaces del listado original: no había duplicados, y se conservaron todos).*

## Apoyo de agentes de IA

- Claude
- ChatGPT
- Gemini (generación de imágenes)

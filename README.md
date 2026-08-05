# LifeDoc Care - Sistema de Citas Médicas
Sistema de control y agendamiento de citas médicas para centro medico, desarrollado en Java con NetBeans.

Qué es
Un sistema informático (con roles Usuario, Administrador del centro, administrador del sistema, Médico y Operario) para gestionar citas médicas, historias clínicas y facturación en un centro de salud.

Objetivos clave

Autenticación segura con validación de contraseñas (regex, nivel medio).
Módulo de citas médicas (registro, consulta, cancelación, evitar duplicados el mismo día).
Historia clínica: anotaciones médicas, exportación a PDF, imagen del paciente en BD.
Facturación según categoría del afiliado y edad.
Herencia y polimorfismo: clase Médico con subclases Médico General y Médico Especialista, donde el especialista recibe bonificación en el salario (esto explica por qué tu proyecto tiene clases Medico/Medicos duplicadas — probablemente de esta jerarquía).

Requisito de diseño obligatorio
Deben investigar, justificar e implementar mínimo 3 patrones de diseño de software (no vienen predefinidos). El documento sugiere áreas donde podrían aplicar: gestión de roles/permisos, notificaciones, búsquedas/filtros, desacoplamiento entre módulos, procesos centralizados.

Entregables de documentación (carpeta por equipo en OneDrive)
Matriz de stakeholders, requerimientos funcionales/no funcionales, historias de usuario, diagramas UML (casos de uso, secuencia, clases, despliegue) en JPG + StarUML, prototipo no funcional en Figma, video explicativo, y logo del proyecto.

## Requisitos del equipo

- Java JDK 21.
- NetBeans IDE.
- XAMPP (MySQL / MariaDB)
   
## Tecnologías usadas

- Java Swing (interfaz gráfica).
- commons-logging-1.4.0.jar
- core-3.5.3.jar.zip
- itextpdf-5.5.13.2.jar
- javase-3.5.3.jar.zip
- javax.activation-1.1.0.v201105071233.jar
- javax.ejb.jar
- javax.faces-api-2.0.jar
- javax-inject.jar
- LGoodDatePicker-11.2.1.jar
- mail.jar
- mysql-connector-j-8.0.32.jar
- spring-security-crypto-6.4.4.jar
- MySQL via XAMPP (base de datos).
- JDBC (conexión base de datos).

## Interfaz y base
Java Swing — Framework nativo de Java para construir interfaces gráficas de escritorio (ventanas, botones, tablas, formularios). Es la base de todas las vistas (OperarioInterfaz, MedicoInterfaz, etc.) en LifeDoc-Care.
LGoodDatePicker-11.2.1.jar — Componente de selección de fechas/horas para Swing (calendario visual). Útil para elegir fecha y hora de citas de forma amigable, en vez de campos de texto libres.

## Generación de PDF
itextpdf-5.5.13.2.jar — Librería para crear y manipular documentos PDF desde Java. Es probablemente la que usa tu clase CreadorPdf para generar historias clínicas y facturas.

## Códigos QR / lectura de imágenes (ZXing)
core-3.5.3.jar.zip — Núcleo de la librería ZXing (Zebra Crossing), usada para generar y leer códigos de barras/QR. Podría emplearse para identificar pacientes o citas mediante códigos QR.
javase-3.5.3.jar.zip — Extensión de ZXing específica para entornos Java SE (escritorio), permite trabajar con imágenes (BufferedImage) para generar/leer códigos QR desde una app de escritorio como esta.

## Correo electrónico (JavaMail y dependencias)
mail.jar — API de JavaMail, permite enviar correos electrónicos (SMTP) desde Java. Es la base de tu clase EnvioCorreos.java para notificaciones a pacientes.
javax.activation-1.1.0.v201105071233.jar — JAF (Java Activation Framework), dependencia requerida por JavaMail para manejar tipos de datos y adjuntos en los correos.

## Especificaciones Java EE (probablemente heredadas/no todas en uso activo)
javax.ejb.jar — API de Enterprise JavaBeans (Java EE), para componentes de negocio distribuidos/transaccionales. En un proyecto Swing de escritorio simple con JDBC, normalmente no se necesita; puede ser un remanente de una plantilla o dependencia transitiva.
javax.faces-api-2.0.jar — API de JavaServer Faces, framework para interfaces web (JSF). No aplica a una app de escritorio Swing; es señal de que puede ser una dependencia innecesaria en el classpath.
javax-inject.jar — Especificación estándar de inyección de dependencias (@Inject, JSR-330). Se usa si el proyecto emplea algún contenedor de DI; en un proyecto Swing/JDBC simple sin framework de DI, su uso es opcional o inexistente.

## Logging
commons-logging-1.4.0.jar — Fachada de logging de Apache Commons, que permite registrar mensajes (info, error, debug) delegando a una implementación subyacente. A menudo es dependencia transitiva de otras librerías (como MySQL Connector o Spring).

## Base de datos
mysql-connector-j-8.0.32.jar — Driver JDBC oficial de MySQL/Oracle. Es el conector que permite a tu código Java conectarse a la base de datos MySQL/MariaDB (vía XAMPP) usando JDBC puro, tal como está estructurado el proyecto.
MySQL via XAMPP — Servidor de base de datos relacional MySQL/MariaDB, gestionado localmente mediante XAMPP (incluye phpMyAdmin para administración visual). Almacena las 22 tablas del sistema (pacientes, citas, médicos, historias clínicas, etc.).
JDBC — Java Database Connectivity, la API estándar de Java para ejecutar SQL y gestionar conexiones a bases de datos. Es el mecanismo que usan tus clases DAO (CitaDao, etc.) para interactuar con MySQL sin un ORM.

## Seguridad
spring-security-crypto-6.4.4.jar — Módulo de criptografía de Spring Security (independiente del resto del framework Spring). Ofrece utilidades para hashing de contraseñas (BCrypt, Argon2, etc.) y cifrado. Dado que en tu proyecto hay contraseñas en texto plano en la BD, esta librería sería la solución ideal para implementar el hashing seguro que pide el objetivo 1 del proyecto ("autenticación con contraseña segura").

## Recursos Visuales

- https://dribbble.com/
- https://co.pinterest.com/
  
##1. Paginas de descarga recursos visuales y creación de paneles 

- https://www.figma.com
- https://gqinformatica.com/herramientas/visualizador-tamanos-imagen-online-calculadora-dimensiones-ratios/
- https://www.flaticon.com/
- https://www.myinstants.com/es/index/co/
- https://freesound.org/
- https://speechgen.io/

## Webgrafía 

- https://docs.oracle.com/javase/8/docs/api/
- https://stackoverflow.com/questions
- https://www.youtube.com/watch?v=sL1s4YyONSg&t=8s
- https://www.youtube.com/watch?v=nEkM1sA1z7g&t=90s
- https://www.youtube.com/watch?v=hIBEmpV30Ao&t=148s
- https://www.youtube.com/watch?v=EpKZw0NNCUA&t=4s
- https://www.youtube.com/watch?v=uaPB1el-M0k
- https://www.youtube.com/watch?v=l8zdauUWv9g&t=1518s
- https://www.youtube.com/watch?v=UmoryILvQ_Q&t=217s
- https://www.youtube.com/watch?v=8hMWNGGg_OI
- https://www.youtube.com/watch?v=h2Zb56eVgIM
- https://www.youtube.com/watch?v=GwjUKVeVtCo
- https://www.youtube.com/watch?v=Ryf5icqLSKk
- https://www.youtube.com/watch?v=fVGOwiEyyj4
- https://www.youtube.com/watch?v=ZggjlwLzrxg
- https://www.youtube.com/watch?v=jjM4Q6MBKQY
- https://www.youtube.com/watch?v=sW4FyA-z5Yg&t=850s
- https://www.youtube.com/watch?v=gx_iVi67fjE
- https://www.youtube.com/watch?v=aZng9YPL4-s
- https://www.youtube.com/watch?v=Z6uvh7xM-m0&t=62s
- https://www.youtube.com/watch?v=uINUPW3xGpE
- https://www.youtube.com/watch?v=xvvMSJ3nfT8
- https://www.youtube.com/watch?v=UtoKA2IzE2M
- https://www.youtube.com/watch?v=SPpditsfY1o
- https://www.youtube.com/watch?v=j9i2i--xRGw
- https://www.youtube.com/watch?v=X5llkhXOYIA
- y un montón de cosas mas.

## AYUDA DE AGENTES 
- Claude.
- Chat gpt.
- Gemini - imagenes.


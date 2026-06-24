# LifeDoc Care - Sistema de Citas Médicas

Sistema de control y agendamiento de citas médicas para consultorios, desarrollado en Java con NetBeans.

## Requisitos

- Java JDK 21
- NetBeans IDE
- XAMPP (MySQL / MariaDB)

## Instalación de la librería del Calendario

1. Descarga el archivo `LGoodDatePicker-11.2.1.jar` desde:
   https://repo1.maven.org/maven2/com/github/lgooddatepicker/LGoodDatePicker/11.2.1/LGoodDatePicker-11.2.1.jar

2. En NetBeans haz clic derecho en tu proyecto → Properties → Libraries
3. Clic en el "+" de Classpath → Add JAR/Folder
4. Selecciona el archivo descargado y haz clic en Abrir

## Configuración de la Base de Datos

1. Inicia XAMPP y enciende el servicio MySQL
2. Abre phpMyAdmin en http://localhost/phpmyadmin
3. Crea una base de datos nueva
4. Importa el archivo `database.sql` que está en la raíz del proyecto
5. Configura los datos de conexión en el código según tu usuario y contraseña de MySQL

## Tecnologías usadas

- Java Swing (interfaz gráfica)
- LGoodDatePicker 11.2.1 (calendario)
- MySQL via XAMPP (base de datos)
- JDBC (conexión base de datos)

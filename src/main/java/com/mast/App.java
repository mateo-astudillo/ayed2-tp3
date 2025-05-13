package com.mast;

import java.util.Optional;
import java.util.UUID;

/**
 * App
 */
public class App {
	public static void main(String[] args) {
		HashTaskTable hashTaskTable = new HashTaskTable();
		Task tasks[] = {
			new Task("Planificar proyecto"),
			new Task("Redactar informe"),
			new Task("Revisar documentos"),
			new Task("Enviar correos"),
			new Task("Actualizar base de datos"),
			new Task("Crear presupuesto"),
			new Task("Importar datos", "Cargar datos desde archivos o fuentes externas a un sistema o base de datos."),
			new Task("Editar contenido"),
			new Task("Optimizar sitio web"),
			new Task("Publicar en redes sociales"),
			new Task("Coordinar equipo"),
			new Task("Diseñar flujos", "Crear diagramas o mapas que representen procesos o interacciones en un sistema."),
			new Task("Diseñar presentación"),
			new Task("Investigar mercado"),
			new Task("Programar reunión"),
			new Task("Analizar datos"),
			new Task("Revisar usabilidad", "Evaluar la experiencia de usuario en una aplicación para identificar mejoras en la interfaz."),
			new Task("Realizar pruebas"),
			new Task("Configurar software"),
			new Task("Completar formulario"),
			new Task("Preparar agenda"),
			new Task("Configurar notificaciones", "Establecer alertas automáticas para eventos importantes, como errores o tareas completadas."),
			new Task("Revisar inventario"),
			new Task("Generar factura"),
			new Task("Actualizar API", "Modificar integraciones con APIs externas para soportar nuevas versiones o funcionalidades."),
			new Task("Capacitar personal"),
			new Task("Monitorear progreso"),
			new Task("Resolver incidencias"),
			new Task("Generar gráficos", "Crear visualizaciones de datos como gráficos o tablas para reportes o presentaciones."),
			new Task("Configurar CI/CD", "Implementar pipelines de integración y despliegue continuo para automatizar builds y despliegues."),
			new Task("Realizar benchmarking", "Medir el rendimiento de un sistema o componente comparándolo con estándares o alternativas."),
			new Task("Actualizar calendario"),
			new Task("Organizar archivos"),
			new Task("Realizar respaldo"),
			new Task("Automatizar backups", "Configurar scripts para realizar copias de seguridad automáticas de archivos o bases de datos."),
			new Task("Refactorizar código", "Reorganizar el código para mejorar su legibilidad y eficiencia sin cambiar su funcionalidad."),
			new Task("Contactar cliente"),
			new Task("Evaluar proveedores"),
			new Task("Implementar cambios"),
			new Task("Optimizar imágenes", "Reducir el tamaño de imágenes para mejorar el rendimiento de una aplicación o sitio web."),
			new Task("Revisar permisos", "Verificar y ajustar los permisos de acceso a sistemas o recursos para garantizar seguridad."),
			new Task("Crear endpoints", "Desarrollar nuevos puntos de acceso en una API para soportar funcionalidades adicionales."),
			new Task("Verificar calidad"),
			new Task("Planear evento"),
			new Task("Redactar README", "Escribir un archivo README claro para explicar cómo usar o contribuir a un proyecto."),
			new Task("Ejecutar regresiones", "Correr pruebas para asegurar que nuevas funcionalidades no rompan características existentes."),
			new Task("Planificar pruebas", "Definir casos de prueba y estrategias para validar el correcto funcionamiento de un sistema."),
			new Task("Gestionar recursos"),
			new Task("Comprobar métricas"),
			new Task("Instalar equipo"),
			new Task("Redactar propuesta"),
			new Task("Revisar contrato"),
			new Task("Actualizar perfil"),
			new Task("Validar entradas", "Comprobar que los datos ingresados por usuarios cumplan con reglas de formato y requisitos."),
			new Task("Actualizar dependencias", "Revisar y actualizar las librerías o paquetes usados en un proyecto para evitar vulnerabilidades."),
			new Task("Monitorear servidores", "Supervisar el estado y rendimiento de servidores para detectar y resolver problemas."),
			new Task("Recopilar feedback"),
			new Task("Crear prototipo"),
			new Task("Traducir texto"),
			new Task("Documentar API", "Escribir documentación detallada para los endpoints y parámetros de una API.")
		};


		for (Task task : tasks)
			hashTaskTable.insert(task.getUuid(), task);

		Task taskToFind;
		for (int i = 0; i < (5 * 4) + 1; i += 5) {
			taskToFind = tasks[i];
			System.out.println("TAREA A BUSCAR:\n" + taskToFind.toString());

			Optional<Task> task = hashTaskTable.find(taskToFind.getUuid());
			task.ifPresent(t -> System.out.println("TAREA ENCONTRADA\n" + t.toString() + "\n"));
		}

		UUID randomUuid = UUID.randomUUID();
		Optional<Task> notFoundTask = hashTaskTable.find(randomUuid);
		if (notFoundTask.isEmpty())
			System.out.println("NO SE ENCONTRÓ");
	}
}

package com.mast;

import java.util.Optional;
import java.util.UUID;

import com.sun.tools.javac.main.Option;

/**
 * HashTaskTable
 */
public class HashTaskTable {
	private final double R = 0.618_033_988;
	private final int M = 101;
	private int N = 0;
	private double loadFactor;
	private Task tasks[];

	public HashTaskTable() {
		this.tasks = new Task[this.M];
		this.loadFactor = 0;
	}

	public boolean insert(UUID key, Task task) {
		if (this.N == this.M) // Mientras haya un lugar se va a insertar
			return false;

		if (this.loadFactor >= 0.5)
			warning();

		int keyHashed = hash(key); // obtengo el primer índice hasheando la clave
		int index = keyHashed; // este será el índice definitivo para la tabla
		int i = 1;
		while (tasks[index] != null) { // solo cambia el índice si ese lugar está ocupado
			index = solveColision(keyHashed, i);
			i++;
		}
		tasks[index] = task;

		this.N += 1;
		setLoadFactor();
		return true;
	}

	public boolean delete(UUID key) {
		Optional<Task> task = find(key);
		task.ifPresent(t -> t.setRemoved());
		return task.isPresent();
	}

	public Optional<Task> find(UUID key) {
		Task task = tasks[hash(key)];
		// NOTE Al no borrar información si es null nunca se insertó
		if (task == null)
			return Optional.empty();

		int keyHashed = hash(key);
		int index = keyHashed;
		int i = 1;
		while (!task.getUuid().equals(key)) {
			index = solveColision(keyHashed, i);
			i++;
			if (tasks[index] == null)
				continue;
			task = tasks[index];

			// NOTE Como la exploración cuadrática no me garantiza
			// pasar por todos los índices el límite de iteraciones
			// esto puede quedar en un bucle y se coloca un límite de intentos
			// Este límite está pensado para exploración lineal en caso
			// de que el factor de carga sea mayor o igual a 0.5
			// Si es menor con pocos pasos se encuentra o no existe
			// Si es mayor se encuentra o se revisan todos los lugares
			if (i > this.M)
				return Optional.empty();
		}
		return Optional.of(task);
	}

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		for (Task task : tasks) {
			if (task == null)
				continue;
			stringBuilder.append(task.toString() + "\n");
		}
		return stringBuilder.toString();
	}

	private void setLoadFactor() {
		this.loadFactor = (double) this.N / (double) this.M;
	}

	private long UUIDtoInt(UUID key) {
		String keyString = key.toString();
		// NOTE Se eliminan los guiones para mejor disperción
		keyString = keyString.replaceAll("-", "");

		long code = 0;
		// NOTE Los UUID siempre tienen más de 10 caracteres
		for (char c : keyString.substring(0, 10).toCharArray())
			code += (int) c;
		return code;
	}

	private int hash(UUID keyUUID) {
		long key = UUIDtoInt(keyUUID);
		double result = (this.R * key) - (Math.floor(this.R * key));
		return (int) (this.M * result);
	}

	private int solveColision(int index, int i) {
		// NOTE Si no se elige convenientemente el tamaño de la tabla, no se puede
		// asegurar que se prueben todas las posiciones de la tabla.
		// Se puede demostrar que si el tamaño de la tabla es un número primo y el
		// factor de carga no alcanza el 50%, todas las pruebas que se realicen con la
		// secuencia p + i^2 se hacen sobre posiciones
		// de la tabla distintas y siempre se podrá insertar.
		// Hasta el 50% se usa exploración cuadrática y luego del 50% exploración lineal
		return (this.loadFactor < 0.5) ? (index + (i * i)) % M : (index + i) % M;
	}

	private void warning() {
		System.out.println("Se ha superado el 50% de la capacidad de la Tabla Dispersa");
	}
}

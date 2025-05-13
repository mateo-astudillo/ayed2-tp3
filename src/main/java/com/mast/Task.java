package com.mast;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

/**
 * Task
 */
public class Task {
	private final UUID uuid;
	@SuppressWarnings("unused")
	private boolean removed = false;
	private String name;

	private String description;
	private State state; // pendiente - en progrso - finalizada - cancelada
	private final LocalDate createdAt;
	private Optional<LocalDate> finishedAt;
	{ // Código compartido entre constructores
		this.uuid = UUID.randomUUID();
		// this.removed = false;
		this.state = State.PENDING;
		this.createdAt = LocalDate.now();
		this.finishedAt = Optional.empty();
	}

	public Task(String name) {
		this.name = name;
		this.description = "";
	}

	public Task(String name, String description) {
		this.name = name;
		this.description = description;
	}

	public void setRemoved() {
		this.removed = true;
	}

	public void changeState(State newState) {
		if (this.state.equals(newState)) // No es necesario cambiar al mismo estado
			return;

		this.state = switch (this.state) {
			case PENDING -> switch (newState) { // Desde "PENDIENTE" puede ir a
				case IN_PROGRESS -> newState; // "EN PROGRESO"
				case CANCELED -> newState; // "CANCELADA"
				default -> this.state;
			};
			case IN_PROGRESS -> newState; // Desde "PENDIENTE" puede ir a cualquier estado
			case DONE -> switch (newState) { // Desde "FINALIZADA" puede ir a
				case CANCELED -> newState; // "CANCELADA"
				default -> this.state;
			};
			case CANCELED -> switch (newState) { // Desde "CANCELADA" puede ir a
				case PENDING -> newState; // "PENDIENTE"
				default -> this.state;
			};
		};
	}

	public UUID getUuid() {
		return uuid;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public String getState() {
		return switch(this.state) {
			case PENDING -> "pendiente";
			case IN_PROGRESS -> "en progreso";
			case DONE -> "finalizada";
			case CANCELED -> "cancelada";
		};
	}

	public LocalDate getCreatedAt() {
		return createdAt;
	}

	public Optional<LocalDate> getFinishedAt() {
		return finishedAt;
	}

	@Override
	public String toString() {
		return "UUID: " + this.uuid + "\nNOMBRE: " + this.name + "\nESTADO: " + this.getState();
	}
}

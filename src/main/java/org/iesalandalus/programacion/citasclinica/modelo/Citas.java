package org.iesalandalus.programacion.citasclinica.modelo;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.naming.OperationNotSupportedException;

public class Citas {

	// Definimos un array de Cita llamado coleccionCitas.
	private Cita[] coleccionCitas;
	// Tamaño del array
	private int tamano;
	// Capacidad del array
	private int capacidad;

	// Constructor con parametros(capacidad)
	public Citas(int capacidad) {
		if (capacidad <= 0) {
			throw new IllegalArgumentException("ERROR: La capacidad debe ser mayor que cero.");
		}

		this.capacidad = capacidad;
		coleccionCitas = new Cita[capacidad];

	}

	// Devuelve un array de citas
	public Cita[] getCitas() {
		return coleccionCitas;
	}

	// Devuelve un array de todas las citas del dia pasado por parámetro
	public Cita[] getCitas(LocalDate dia) {

		Cita[] citasFechaHora = new Cita[tamano];
		if (dia == null) {
			throw new NullPointerException("ERROR: No se pueden devolver las citas para un día nulo.");

		}
		/*
		 * Nos valemos del método LocalDateTime.of(int year, Month month, int
		 * dayOfMonth, int hour, int minute, int second). Para establecer la hora exacta
		 * del comienzo del día y la hora exacta del minal del día.
		 */
		int j = 0;
		LocalDateTime comienzoDia = LocalDateTime.of(dia.getYear(), dia.getMonth(), dia.getDayOfMonth(), 0, 0, 0);
		LocalDateTime finDia = LocalDateTime.of(dia.getYear(), dia.getMonth(), dia.getDayOfMonth(), 23, 59, 59);
		// Recorremos todo el tamaño del array
		for (int i = 0; !tamanoSuperado(i); i++) {
			// Obtenemos la citas para el día correspondiente
			if (coleccionCitas[i].getFechaHora().isAfter(comienzoDia)
					&& coleccionCitas[i].getFechaHora().isBefore(finDia)) {
				citasFechaHora[j] = new Cita(coleccionCitas[i]);
				j++;
			}
		}
		return citasFechaHora;

	}

	public int getTamano() {
		return tamano;
	}

	public int getCapacidad() {
		return capacidad;
	}

	public void insertar(Cita cita) throws OperationNotSupportedException {

		if (cita == null) {
			throw new NullPointerException("ERROR: No se puede insertar una cita nula.");
		}
		// Comprobamos el tamaño no sea superior a la capacidad
		if (capacidadSuperada(tamano)) {
			throw new OperationNotSupportedException("ERROR: No se aceptan más citas.");
		}
		int indice = buscarIndice(cita);
		if (tamanoSuperado(indice)) {
			coleccionCitas[tamano] = new Cita(cita);

		} else {
			throw new OperationNotSupportedException("ERROR: Ya existe una cita para esa fecha y hora.");

		}
		// Al insertar aumentamos el tamaño
		tamano++;
	}

	// Devolvemos la posición donde se encuentra la cita
	private int buscarIndice(Cita cita) {
		int indice = 0;
		boolean encontrado = false;
		/*
		 * El boleano nos ayuda a no tener que recorrer todo el array en caso de
		 * encontrar el indice antes de llegar al final del tamaño
		 */

		while (!tamanoSuperado(indice) && !encontrado) {
			if (coleccionCitas[indice].equals(cita)) {
				encontrado = true;
			} else {
				indice++;
			}
		}
		return indice;
	}

	private boolean tamanoSuperado(int indiceTamaño) {
		// Devuelce true cuando indice es superior o igual a tamano
		return indiceTamaño >= tamano;
	}

	private boolean capacidadSuperada(int indiceCapacidad) {
		// Devuelce true cuando indice es superior o igual a la capacidad
		return indiceCapacidad >= capacidad;
	}

	public Cita buscar(Cita cita) {
		int indice = buscarIndice(cita);

		if (!tamanoSuperado(indice)) {

			return new Cita(coleccionCitas[indice]);
		} else {

			return null;
		}

	}

	// Accedemos a la posición de la cita en el array y la borramos desplazando
	// posición.
	public void borrar(Cita cita) throws OperationNotSupportedException {
		if (cita == null) {
			throw new IllegalArgumentException("ERROR: No se puede borrar una cita nula.");
		}
		int indice = buscarIndice(cita);
		if (!tamanoSuperado(indice)) {
			desplazarUnaPosicionHaciaIzquierda(indice);

		} else {
			throw new OperationNotSupportedException("ERROR: No existe ninguna cita para esa fecha y hora.");
		}
		// Una vez desplazada la posicion, reducimos el tamaño
		tamano--;
	}

	private void desplazarUnaPosicionHaciaIzquierda(int indice) {
		for (int i = indice; i < coleccionCitas.length - 1; i++) {
			/*
			 * Para desplazar a la izquierda recorrer desde el primero al último en orden
			 * ascendente(+1) (para desplazar a derecha recorrer desde el último al primero
			 * en orden descendente(-1))
			 */
			coleccionCitas[i] = coleccionCitas[i + 1];
		}

	}

}

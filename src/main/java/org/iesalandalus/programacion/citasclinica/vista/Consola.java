package org.iesalandalus.programacion.citasclinica.vista;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import org.iesalandalus.programacion.citasclinica.modelo.Cita;
import org.iesalandalus.programacion.citasclinica.modelo.Paciente;
import org.iesalandalus.programacion.utilidades.Entrada;

public class Consola {

	private Consola() {

	}

	public static void mostrarMenu() {

		System.out.println("0. Salir.");
		System.out.println("1. Insertar cita.");
		System.out.println("2. Buscar cita.");
		System.out.println("3. Borrar Cita.");
		System.out.println("4. Mostrar citas de un determinado día.");
		System.out.println("5. Mostrar todas las citas.");

	}

	public static Opciones elegirOpcion() {
		int opcion = 0;
		// values​() devuelve un array que contiene una lista de las constantes de
		// enumeración.
		Opciones[] opciones = Opciones.values();
		do {
			System.out.print("Elegir opción: ");
			opcion = Entrada.entero();
		} while (opcion < 0 || opcion > opciones.length - 1);

		return opciones[opcion];
	}

	/*
	 * Estamos en una clase intermedia, las excepciones no seap capturadas seran
	 * propagadas.
	 */

	public static Cita leerCita() {
		return new Cita(leerPaciente(), leerFechaHora());

	}

	public static Paciente leerPaciente() {

		System.out.print("Introduzca el nombre del paciente: ");
		String nombre = Entrada.cadena();
		System.out.print("Introduzca el dni del paciente: ");
		String dni = Entrada.cadena();
		System.out.print("Introduzca el teléfono del paciente: ");
		String telefono = Entrada.cadena();

		return new Paciente(nombre, dni, telefono);
	}

	public static LocalDateTime leerFechaHora() {
		LocalDateTime fecha = null;
		/*
		 * Proporcionamos un DateTimeFormatter basado en el atributo publico de la clase
		 * cita del patron FORMATO_FECHA_HORA
		 */

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern(Cita.FORMATO_FECHA_HORA);
		System.out.printf("Introduza una fecha y una hora(dd/MM/yyyy HH:mm):");
		String fechaStr = Entrada.cadena();

		try {
			fecha = LocalDateTime.parse(fechaStr, dtf);
		} catch (DateTimeParseException e) {
			System.out.println("ERROR: El formato de la fecha no es correcto. Formato correcto (dd/MM/yyyy HH:mm)");
		}
		return fecha;
	}

	public static LocalDate leerFecha() {

		LocalDate fecha = null;
		// Eliminamos la hora del patron FORMATO_FECHA_HORA con el método replace.
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern(Cita.FORMATO_FECHA_HORA.replace(" HH:mm", ""));

		System.out.printf("Introduza una fecha(dd/MM/yyyy):");
		String fechaStr = Entrada.cadena();
		try {
			fecha = LocalDate.parse(fechaStr, dtf);
		} catch (DateTimeParseException e) {
			System.out.println("ERROR: El formato de la fecha no es correcto. Formato correcto (dd/MM/yyyy)");
		}

		return fecha;
	}

}


package org.iesalandalus.programacion.citasclinica;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.citasclinica.modelo.Cita;
import org.iesalandalus.programacion.citasclinica.modelo.Citas;
import org.iesalandalus.programacion.citasclinica.modelo.Paciente;
import org.iesalandalus.programacion.citasclinica.vista.Consola;
import org.iesalandalus.programacion.citasclinica.vista.Opciones;

/*
 * Author:José Antonio Del Rey Martínez
 * IES AL-ÁNDALUS
 * PROGRAMACIÓN
 * Tarea online 4
 * Cadenas de caracteres y arrays
 */

public class MainApp {
	// Uso una capacidad pequeña para comprobar excepciones.
	private static final int NUM_MAX_CITAS = 3;
	static Citas citasClinica = new Citas(NUM_MAX_CITAS);

	public static void main(String[] args) {

		System.out.println(" __________________________________");
		System.out.println("|          PROGRAMACIÓN            |");
		System.out.println("|         TAREA ONLINE 4           |");
		System.out.println("|  José Antonio Del Rey Martínez   |");
		System.out.println("|__________________________________|");

		Opciones opciones;

		do {
			Consola.mostrarMenu();
			opciones = Consola.elegirOpcion();
			ejecutarOpcion(opciones);
		} while (opciones != Opciones.SALIR);
		System.out.println("Hasta luego!!!!");

	}

	private static void ejecutarOpcion(Opciones opcion) {
		switch (opcion) {
		case SALIR:
			break;
		case INSERTAR_CITA:
			insertarCita();
			break;
		case BUSCAR_CITA:
			buscarCita();
			break;
		case BORRAR_CITA:
			borrarCita();
			break;
		case MOSTRAR_CITAS_DIA:
			mostrarCitasDia();
			break;
		case MOSTRAR_CITAS:
			mostrarCitas();
			break;
		default:

		}
	}

	private static void insertarCita() {
		try {
			Cita cita = Consola.leerCita();

			citasClinica.insertar(cita);
			System.out.println("La cita " + cita + " ha sido insertada con éxito en el sistema.");
			System.out.println("Disponibles " + (citasClinica.getCapacidad() - citasClinica.getTamano())
					+ " citas para insertar en el sistema.");
			/*
			 * Capturo todas las posibles excepciones al insertar una cita, las de la clase
			 * Paciente, clase Cita, y el método insertar.
			 */
		} catch (OperationNotSupportedException | NullPointerException | IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}

	private static void buscarCita() {
		try {
			Paciente paciente = new Paciente("José Antonio Del Rey Martínez", "22334455Y", "950112233");
			LocalDateTime fechaHora = Consola.leerFechaHora();
			Cita cita = new Cita(paciente, fechaHora);

			if ((citasClinica.buscar(cita)) == null) {
				System.out.println("la cita para el día: "
						+ fechaHora.format(DateTimeFormatter.ofPattern("dd 'de' MMMM 'del' yyyy 'a las' hh:mm"))
						+ " no esta registrada en el sistema");
			} else {
				System.out.println(cita);
			}
			/*
			 * Aquí solo debemos calcular un posible nulo al introducir la fecha, el formato
			 * incorrecto de la fechaHora ya lo capturamos en la clase consola.
			 * 
			 */
		} catch (NullPointerException e) {
			System.out.println(e.getMessage());
		}
	}

	private static void borrarCita() {
		try {
			Paciente paciente = new Paciente("José Antonio Del Rey Martínez", "22334455Y", "950112233");
			LocalDateTime fechaHora = Consola.leerFechaHora();
			Cita cita = new Cita(paciente, fechaHora);

			citasClinica.borrar(cita);
			System.out.println("Borrada del registro la cita: " + cita);

			System.out.println("Disponibles " + (citasClinica.getCapacidad() - citasClinica.getTamano())
					+ " citas para insertar en el sistema.");
			// Capturamos las posibles excepciones.
		} catch (NullPointerException | IllegalArgumentException | OperationNotSupportedException e) {
			System.out.println(e.getMessage());
		}
	}

	private static void mostrarCitasDia() {
		try {

			LocalDate fechaCitasDia = Consola.leerFecha();

			boolean citasRegistradas = false;

			for (int i = 0; i <= citasClinica.getCitas(fechaCitasDia).length - 1; i++) {
				if (citasClinica.getCitas(fechaCitasDia)[i] != null) {
					citasRegistradas = true;
					System.out.println(citasClinica.getCitas(fechaCitasDia)[i]);

				}
			}

			if (citasClinica.getTamano() == 0) {
				System.out.println("El sistema no tiene citas en su registro. Introduzca primero una cita.");
			} else if (!citasRegistradas) {
				System.out.println("No hay citas registradas en el sistema para el día: "
						+ fechaCitasDia.format(DateTimeFormatter.ofPattern("dd 'de' MMMM 'del' yyyy")));

			}
			// Capturamos el posible nulo al introducir una cita, EL formato fecha y hora ya
			// lo capturamos en la clase consola.
		} catch (NullPointerException e) {
			System.out.println(e.getMessage());
		}

	}

	private static void mostrarCitas() {

		// Si el tamaño es 0 no puede haber ninguna cita insertada.
		if (citasClinica.getTamano() == 0) {
			System.out.println("El sistema no tiene citas en su registro.");
		}

		for (int i = 0; i < citasClinica.getTamano(); i++) {
			System.out.println(citasClinica.getCitas()[i]);

		}

	}

}

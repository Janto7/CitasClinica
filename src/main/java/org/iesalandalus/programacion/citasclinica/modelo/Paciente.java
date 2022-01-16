package org.iesalandalus.programacion.citasclinica.modelo;

import java.util.Objects;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Paciente {

	// Creación de atributos indicados por el diagrama
	private static final String ER_DNI = "([0-9]{8})([a-zA-Z])";
	private static final String ER_TELEFONO = "(9|6)[0-9]{8}";

	private String nombre;
	private String dni;
	private String telefono;

	// Constructor con parametros
	public Paciente(String nombre, String dni, String telefono) {
		setNombre(nombre);
		setDni(dni);
		setTelefono(telefono);
	}

	// Constructor copia
	public Paciente(Paciente p) {
		{
			if (p == null)
				throw new NullPointerException("ERROR: No es posible copiar un paciente nulo.");

			setNombre(p.getNombre());
			setDni(p.getDni());
			setTelefono(p.getTelefono());
		}
	}

	// Setters and Getters

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {

		if (nombre == null || nombre.trim().isEmpty()) {
			throw new NullPointerException("ERROR: El nombre de un paciente no puede ser nulo o vacío.");
		}
		// Para establecer el nombre utilizamos el método formateaNombre.
		this.nombre = formateaNombre(nombre);

	}

	private String formateaNombre(String nombre) {

		// Paso 1 :Paso: Creamos un objeto del método StringTokenizer
		StringTokenizer st = new StringTokenizer(nombre);
		/*
		 * Paso 2: Contamos la cantidad de palabras que tiene la variable que contenga
		 * el texto y la guardamos en una variable entera.
		 */

		int cantidadPalabras = st.countTokens();
		// Paso 3: Creamos un ciclo for que corra por cada una de las palabras.
		String nombreCompleto = " ";
		for (int i = 0; i < cantidadPalabras; i++) {
			String PalabraIndividual = st.nextToken();
			/*
			 * Nombre formateado (+=) primera letra de cada palabra en mayuscula, mas resto
			 * de letras de cada palabra en miniscu
			 */

			nombreCompleto += PalabraIndividual.substring(0, 1).toUpperCase()
					+ PalabraIndividual.substring(1).toLowerCase() + " ";
		}
		nombre = nombreCompleto.trim();
		return nombre;
	}

	public String getDni() {
		return dni;
	}

	private void setDni(String dni) {
		if (dni == null || dni.trim().isEmpty()) {
			throw new NullPointerException("ERROR: El DNI de un paciente no puede ser nulo o vacío.");
		}
		if (!dni.matches(ER_DNI)) {
			throw new IllegalArgumentException("ERROR: El DNI no tiene un formato válido.");
		}
		if (!comprobarLetraDni(dni)) {
			throw new IllegalArgumentException("ERROR: La letra del DNI no es correcta.");
		}
		this.dni = dni.toUpperCase();
	}

	private boolean comprobarLetraDni(String dni) {
		// Inicializamos el booleano comprobarLetra
		boolean dniAValidar = false;
		// Letras validas DNI
		String[] letraValida = { "T", "R", "W", "A", "G", "M", "Y", "F", "P", "D", "X", "B", "N", "J", "Z", "S", "Q",
				"V", "H", "L", "C", "K", "E" };
		// La clase Pattern se utiliza para crear el patrón.
		Pattern patron = Pattern.compile(ER_DNI);

		/*
		 * La clase Matcher compara el patrón con la cadena. Contiene el método
		 * matches() que recibe como parámetro el String a validar y devuelve true si
		 * coincide con el patrón y false en caso contrario.
		 * 
		 */

		Matcher comparador = patron.matcher(dni);
		if (comparador.matches()) {
			// Si la letra del DNI convertida a mayúscula(Grupo 2) es igual a la letra que
			// corresponde al resto de la parte númerica(grupo 1) entre 23
			if (comparador.group(2).toUpperCase().equals(letraValida[Integer.parseInt(comparador.group(1)) % 23])) {
				// Es válido
				dniAValidar = true;
			}

		}
		return dniAValidar;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		if (telefono == null || telefono.trim().isEmpty()) {
			throw new NullPointerException("ERROR: El teléfono de un paciente no puede ser nulo o vacío.");
		}
		if (!telefono.matches(ER_TELEFONO)) {
			throw new IllegalArgumentException("ERROR: El teléfono no tiene un formato válido.");
		}

		this.telefono = telefono;
	}

	private String getIniciales() {

		StringBuilder builder = new StringBuilder();

		// Se crea un array que contiene todas las palabras
		String palabras[] = nombre.split(" ");

		// Se recorren todas las palabras del array
		for (String palabra : palabras) {

			// Extraemos el primer caracter
			builder.append(palabra.substring(0, 1));

		}
		// se retorna el string(cadena) formado por el primer caracter(las iniciales)
		return builder.toString();
	}

	/*
	 * Sabiendo que dos pacientes se considerarán iguales si su DNI es el mismo,
	 * solo necestamos usar el atributo DNI(Métodos generados por IDE Eclipse)
	 */

	@Override
	public int hashCode() {
		return Objects.hash(dni);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Paciente other = (Paciente) obj;
		return Objects.equals(dni, other.dni);

	}

	/*
	 * toString exacto nos pide el test, generado por el propio IDE y modificado
	 * para obtener la cadena exacta pide el test.
	 */

	@Override
	public String toString() {
		return "nombre=" + getNombre() + " (" + getIniciales() + ")" + ", DNI=" + getDni() + ", teléfono="
				+ getTelefono();
	}

}

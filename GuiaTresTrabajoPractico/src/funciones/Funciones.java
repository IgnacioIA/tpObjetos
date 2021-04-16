package funciones;

import java.time.LocalDate;

public class Funciones {

	public static String fechaCorta(LocalDate fecha) {

		String fechaCorta=""+ fecha.getDayOfMonth() +"/"+ fecha.getMonthValue() +"/"+ fecha.getYear();

		return fechaCorta;
	}
}

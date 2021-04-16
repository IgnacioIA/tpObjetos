package test;
import java.time.LocalDate;
import datos.*;
import negocio.*;

public class TestDeCuotaYPrestamo {

	public static void main(String[] args) {
		ClienteABM clienteABM1= new ClienteABM();
		PrestamoABM prestamoABM1= new PrestamoABM();		
		try {
			//clienteABM1.agregar("pepe", "Javier", 4512542, LocalDate.now());
			prestamoABM1.agregar(LocalDate.of(2021, 4, 2), 1000, 10, 4, clienteABM1.traer(4512542));
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		

	}

}

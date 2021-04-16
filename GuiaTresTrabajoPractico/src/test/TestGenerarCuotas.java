package test;
import java.time.LocalDate;
import datos.*;
import negocio.*;

public class TestGenerarCuotas {

	public static void main(String[] args) {
		PrestamoABM prestamoABM= new PrestamoABM();
		ClienteABM cliente= new ClienteABM();
		long id=2;
		
		try {
			//cliente.agregar("ignacio", "Cruz", 41581415, LocalDate.of(1999, 5, 17));
			//prestamoABM.agregar(LocalDate.now(), 5000, 10, 5, cliente.traer(41581415));
			prestamoABM.pagarCuota(4);
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
		

	}

}

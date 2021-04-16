package test;
import java.time.LocalDate;
import datos.*;
import negocio.*;

public class TestEliminar {

	public static void main(String[] args) {
		
		ClienteABM clienteABM= new ClienteABM();
		
		clienteABM.eliminar(2);
		

	}

}

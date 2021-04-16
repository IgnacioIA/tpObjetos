package negocio;
import java.time.LocalDate;
import java.util.List;
import dao.ClienteDao;
import dao.PrestamoDao;
import datos.Cliente;
import datos.Prestamo;
public class ClienteABM {
	ClienteDao dao = new ClienteDao();
	
	public Cliente traer(long idCliente) {
		Cliente c = dao.traer(idCliente);
		return c;
	}
	public Cliente traer(int dni) {
		Cliente c = dao.traer(dni);
		return c;
	}
	public void tienePrestamo(int dni)throws Exception {
		Cliente cliente= traer(dni);
		if(cliente==null) {
			throw new Exception("El cliente no existe en la base de datos");
		}
		PrestamoDao daoPrestamo= new PrestamoDao();
		List<Prestamo> lstPrestamo= daoPrestamo.traer(cliente);
		if(lstPrestamo==null) {
			throw new Exception("Este cliente no tiene Prestamos otorgados");
		}	
	}
	public void tienePrestamo(long id)throws Exception {
		Cliente cliente= traer(id);
		if(cliente==null) {
			throw new Exception("El cliente no existe en la base de datos");
		}
		PrestamoDao daoPrestamo= new PrestamoDao();
		List<Prestamo> lstPrestamo= daoPrestamo.traer(cliente);
		if(lstPrestamo==null) {
			throw new Exception("Este cliente no tiene Prestamos otorgados");
		}	
	}
	
	public int agregar(String apellido, String nombre, int dni, LocalDate
			fechaDeNacimiento) throws Exception {
		Cliente c= dao.traer(dni);
		if(c!=null) {
			throw new Exception("Esta persona ya ah sido ingresada a la base de datos");
		}
		c= new Cliente(apellido, nombre, dni, fechaDeNacimiento);
		return dao.agregar(c);
	}
	public void modificar(Cliente c) {
		dao.actualizar(c);
	}

	public void eliminar(long idCliente) {
		Cliente c = dao.traer(idCliente);
		dao.eliminar(c);
	}
	public List<Cliente> traer() {
		return dao.traer();
	}
	public Cliente traerClienteYPrestamos(long idCliente) {
		return dao.traerClienteYPrestamos(idCliente);
	}
}

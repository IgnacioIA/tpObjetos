package negocio;
import java.util.List;
import dao.CuotaDao;
import datos.Cuota;
import datos.Prestamo;

public class CuotaABM {
CuotaDao dao= new CuotaDao();

public Cuota traer(long idCuota){
	return dao.traer(idCuota);
}



}

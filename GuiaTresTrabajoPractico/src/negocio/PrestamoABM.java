package negocio;
import dao.CuotaDao;
import java.lang.Math;
import dao.PrestamoDao;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import datos.Cliente;
import datos.Cuota;
import datos.Prestamo;

public class PrestamoABM {
	private PrestamoDao dao=new PrestamoDao();
	
	//Implementar: si no existe el prestamo lanzar la excepción
	public Prestamo traerPrestamo(long idPrestamo) throws Exception{
		
		Prestamo p =dao.traer(idPrestamo);
		if(p==null) {
			throw new Exception("El prestamo no existe");
		}
		return p;
	}
	
	public List<Prestamo> traerPrestamo(Cliente c) {return dao.traer(c);}
	/* Pendiente implementar
	 * Alta, Modificar
	 */
	public int agregar(LocalDate fecha, double monto, double interes, int cantCuotas,Cliente cliente) throws Exception {
	    int id=0;
	    Prestamo p =dao.traer(0);
		while(p!=null) {
			p =dao.traer((long)id);
			if(p.getFecha()==fecha && p.getMonto()==monto && p.getInteres()== interes && p.getCantCuotas()==cantCuotas && p.getCliente().equals(cliente)) {
				throw new Exception("El prestamo de este cliente ya existe");
			}
			id++;
		}
		Prestamo objeto = new Prestamo(fecha, monto, interes, cantCuotas, cliente);
		objeto.setCuotas(generarCuota(objeto));
		id= dao.agregar(objeto);
		return id;
	}
	
	public void modificar(long id,double monto)throws Exception {
		Prestamo prestamo= traerPrestamo(id);
		if(prestamo==null) {
			throw new Exception("El Prestamo nunca fue ingresado");
		}
		prestamo.setMonto(monto);
		dao.actualizar(prestamo);
	}
	
	public boolean estaCancelado(Prestamo prestamo) throws Exception{
		CuotaDao daoCuotas= new CuotaDao();
		boolean cancelado= false;
		List<Cuota> cuotas= daoCuotas.traer(prestamo);
		if(cuotas==null) {
			throw new Exception("Este prestamo no tiene ninguna cuota");
		}
		if(cuotas.get(cuotas.size()-1).getNroCuota() == prestamo.getCantCuotas() && cuotas.get(cuotas.size()-1).isCancelada()==true) {
			cancelado=true;
		}
		return cancelado;
	}
	
	
	private Cuota calcularCuotaUno (Prestamo prestamo,int nroCuota) {
		double amortizacion= ((prestamo.getMonto()*prestamo.getInteres())/(Math.pow((1+prestamo.getInteres()), prestamo.getCantCuotas())-1));
		double interesCuota = (prestamo.getMonto()*prestamo.getInteres());
		double cuota= amortizacion + prestamo.getInteres();
		double deuda= prestamo.getMonto() - amortizacion;
		//Cuota f= new Cuota(fechaVencimiento, saldoPendiente, amortizacion, interesCuota, cuota, deuda, cancelada, fechaDePago, punitorios)
		return new Cuota(nroCuota,prestamo.getFecha().plusMonths(1), prestamo.getMonto(), amortizacion, interesCuota, cuota, deuda, false, null, 2, prestamo);
		
	}
	
	private Cuota calcularCuotaEnesima(Prestamo prestamo,int nroCuota) {
		double amortizacion= ((prestamo.getMonto()*prestamo.getInteres())/(Math.pow((1+prestamo.getInteres()), prestamo.getCantCuotas()-1)-1));
		double interesCuota = (prestamo.getMonto()*prestamo.getInteres());
		double cuota= amortizacion + prestamo.getInteres();
		double deudaYsaldoPendiente= prestamo.getMonto() - amortizacion;
		//Cuota f= new Cuota(fechaVencimiento, saldoPendiente, amortizacion, interesCuota, cuota, deuda, cancelada, fechaDePago, punitorios)
		return new Cuota(nroCuota,prestamo.getFecha().plusMonths(1), prestamo.getMonto(), amortizacion, interesCuota, cuota, deudaYsaldoPendiente, false, null, 2, prestamo);
	}
	
	private Set<Cuota> generarCuota(Prestamo prestamo) {
        Set<Cuota>lstCuota= new HashSet<Cuota>();
        int nroCuota=1;
		lstCuota.add(calcularCuotaUno(prestamo,nroCuota));
		for(nroCuota= 2 ;nroCuota<=prestamo.getCantCuotas();nroCuota++){
			lstCuota.add(calcularCuotaEnesima(prestamo,nroCuota));
		}
		return lstCuota;
	}
	
	public void pagarCuota(long idCuota)throws Exception {
		CuotaDao cuotaDao=new CuotaDao();
		CuotaABM cuotABM=new CuotaABM();
		Cuota cuota= cuotABM.traer(idCuota);
		if(cuota==null){throw new Exception("La cuota no existe");}
		
		cuota.setCancelada(true);
		cuotaDao.actualizar(cuota);
	    System.out.println("pagado");
		
		
	}
}
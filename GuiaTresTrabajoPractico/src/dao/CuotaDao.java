package dao;

import java.util.List;
import datos.Cuota;
import datos.Prestamo;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class CuotaDao {
	private static Session session;
	private Transaction tx;
	
	
	private void iniciaOperacion() throws HibernateException {
		session = HibernateUtil.getSessionFactory().openSession();
		tx = session.beginTransaction();
	}
	
	private void manejaExcepcion(HibernateException he) throws HibernateException {
		tx.rollback();
		throw new HibernateException("ERROR en la capa de acceso a datos", he);
	}
	
	public Cuota traer(long idCuota) throws HibernateException {
		Cuota obj = null;
		try {
			iniciaOperacion();
			String hQL = "from Cuota c inner join fetch c.prestamo p where p.idCuota=" + idCuota;
					obj = (Cuota) session.createQuery(hQL).uniqueResult();
		} finally {
			session.close();
		}
		return obj;
	}
	
	/*public Cuota traer(Prestamo prestamo,int nroCuota) throws HibernateException {
		Cuota obj = null;
		try {
			iniciaOperacion();
			String hQL = "from Prestamo p where p.idPrestamo="+prestamo.getIdPrestamo() +" inner join fetch p.cuota c where c.nroCuota=" + nroCuota;
					obj = (Cuota) session.createQuery(hQL).uniqueResult();
		} finally {
			session.close();
		}
		return obj;
	}*/
	
	@SuppressWarnings("unchecked")
	public List<Cuota> traer(Prestamo pres) throws HibernateException {
		List<Cuota> lista = null;
		try {
			iniciaOperacion();
			String hQL = "from Cuota c inner join fetch c.prestamo p where p.idPrestamo=" + pres.getIdPrestamo();
					lista = session.createQuery(hQL).list();
		} finally {
			session.close();
		}
		return lista;
	}
	
	public int agregar(Cuota objeto) {
		int id = 0;
		try {
			iniciaOperacion();
			id = Integer.parseInt(session.save(objeto).toString());
			tx.commit();
		} catch (HibernateException he) {
			manejaExcepcion(he);
			throw he;
		} finally {
			session.close();
		}
		return id;
	}
	
	public void actualizar(Cuota cuota) {
		
		try {
		iniciaOperacion();
		session.update(cuota);
		tx.commit(); 
		}
		catch (HibernateException he) {
			manejaExcepcion(he);
			throw he;
		} finally {
			session.close();
		}
	}
	
}





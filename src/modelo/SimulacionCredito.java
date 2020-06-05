package modelo;

import java.time.LocalDate;

public class SimulacionCredito {
	
	
	private int noCuota;
	private double mensualidad;
	private LocalDate fechaPago;
	
	
	public SimulacionCredito() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SimulacionCredito(int noCuota, double mensualidad, LocalDate fechaPago) {

		this.noCuota = noCuota;
		this.mensualidad = mensualidad;
		this.fechaPago = fechaPago;
	}
	
	public int getNoCuota() {
		return noCuota;
	}
	public void setNoCuota(int noCuota) {
		this.noCuota = noCuota;
	}
	public double getMensualidad() {
		return mensualidad;
	}
	public void setMensualidad(double mensualidad) {
		this.mensualidad = mensualidad;
	}
	public LocalDate getFechaPago() {
		return fechaPago;
	}
	public void setFechaPago(LocalDate fechaPago) {
		this.fechaPago = fechaPago;
	}
}

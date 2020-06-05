package modelo;

import java.time.LocalDate;

public class Transaccion {

	private int codTrans;
	private String clienteTrans;
	private char tipoTrans;
	private double montoTrans;
	private double tasaTrans;
	private int numCuotas;
	private LocalDate fechaSolicitud;
	private LocalDate fechaAprobacion;
	private LocalDate fechaIniciacion;
	private LocalDate fechaTermino;
	private String estadoSolicitud;	

	public Transaccion() {	
	}

	public Transaccion(int codTrans, String clienteTrans, char tipoTrans, double montoTrans, double tasaTrans,
			int numCuotas, LocalDate fechaSolicitud, LocalDate fechaAprobacion, LocalDate fechaIniciacion,
			LocalDate fechaTermino, String estadoSolicitud) {
		this.codTrans = codTrans;
		this.clienteTrans = clienteTrans;
		this.tipoTrans = tipoTrans;
		this.montoTrans = montoTrans;
		this.tasaTrans = tasaTrans;
		this.numCuotas = numCuotas;
		this.fechaSolicitud = fechaSolicitud;
		this.fechaAprobacion = fechaAprobacion;
		this.fechaIniciacion = fechaIniciacion;
		this.fechaTermino = fechaTermino;
		this.estadoSolicitud = estadoSolicitud;
	}

	public int getCodTrans() {
		return codTrans;
	}

	public String getClienteTrans() {
		return clienteTrans;
	}

	public char getTipoTrans() {
		return tipoTrans;
	}

	public double getMontoTrans() {
		return montoTrans;
	}

	public double getTasaTrans() {
		return tasaTrans;
	}

	public int getNumCuotas() {
		return numCuotas;
	}

	public LocalDate getFechaSolicitud() {
		return fechaSolicitud;
	}

	public LocalDate getFechaAprobacion() {
		return fechaAprobacion;
	}

	public LocalDate getFechaIniciacion() {
		return fechaIniciacion;
	}

	public LocalDate getFechaTermino() {
		return fechaTermino;
	}

	public String getEstadoSolicitud() {
		return estadoSolicitud;
	}

	public void setCodTrans(int codTrans) {
		this.codTrans = codTrans;
	}

	public void setClienteTrans(String clienteTrans) {
		this.clienteTrans = clienteTrans;
	}

	public void setTipoTrans(char tipoTrans) {
		this.tipoTrans = tipoTrans;
	}

	public void setMontoTrans(double montoTrans) {
		this.montoTrans = montoTrans;
	}

	public void setTasaTrans(double tasaTrans) {
		this.tasaTrans = tasaTrans;
	}

	public void setNumCuotas(int numCuotas) {
		this.numCuotas = numCuotas;
	}

	public void setFechaSolicitud(LocalDate fechaSolicitud) {
		this.fechaSolicitud = fechaSolicitud;
	}

	public void setFechaAprobacion(LocalDate fechaAprobacion) {
		this.fechaAprobacion = fechaAprobacion;
	}

	public void setFechaIniciacion(LocalDate fechaIniciacion) {
		this.fechaIniciacion = fechaIniciacion;
	}

	public void setFechaTermino(LocalDate fechaTermino) {
		this.fechaTermino = fechaTermino;
	}

	public void setEstadoSolicitud(String estadoSolicitud) {
		this.estadoSolicitud = estadoSolicitud;
	}

	@Override
	public String toString() {
		return "Transaccion [codTrans=" + codTrans + ", clienteTrans=" + clienteTrans + ", tipoTrans=" + tipoTrans
				+ ", montoTrans=" + montoTrans + ", tasaTrans=" + tasaTrans + ", numCuotas=" + numCuotas
				+ ", fechaSolicitud=" + fechaSolicitud + ", fechaAprobacion=" + fechaAprobacion + ", fechaIniciacion="
				+ fechaIniciacion + ", fechaTermino=" + fechaTermino + ", estadoSolicitud=" + estadoSolicitud + "]";
	}
}

package modelo;

import java.time.LocalDate;

public class CuotaPago {

	private int codCuota;
	private String codComprobante;
	private int transCuota;
	private double mensCuota;
	private LocalDate fechaPagoCuota;
	private LocalDate fechaEfectivaCuota;
	private String modalPagoCuota;
	private char estadoCuota;
	
	
	public CuotaPago() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CuotaPago(int codCuota, String codComprobante, int transCuota, double mensCuota, LocalDate fechaPagoCuota,
			LocalDate fechaEfectivaCuota, String modalPagoCuota, char estadoCuota) {
		this.codCuota = codCuota;
		this.codComprobante = codComprobante;
		this.transCuota = transCuota;
		this.mensCuota = mensCuota;
		this.fechaPagoCuota = fechaPagoCuota;
		this.fechaEfectivaCuota = fechaEfectivaCuota;
		this.modalPagoCuota = modalPagoCuota;
		this.estadoCuota = estadoCuota;
	}

	public int getCodCuota() {
		return codCuota;
	}

	public String getCodComprobante() {
		return codComprobante;
	}

	public int getTransCuota() {
		return transCuota;
	}

	public double getMensCuota() {
		return mensCuota;
	}

	public LocalDate getFechaPagoCuota() {
		return fechaPagoCuota;
	}

	public LocalDate getFechaEfectivaCuota() {
		return fechaEfectivaCuota;
	}

	public String getModalPagoCuota() {
		return modalPagoCuota;
	}

	public char getEstadoCuota() {
		return estadoCuota;
	}

	public void setCodCuota(int codCuota) {
		this.codCuota = codCuota;
	}

	public void setCodComprobante(String codComprobante) {
		this.codComprobante = codComprobante;
	}

	public void setTransCuota(int transCuota) {
		this.transCuota = transCuota;
	}

	public void setMensCuota(double mensCuota) {
		this.mensCuota = mensCuota;
	}

	public void setFechaPagoCuota(LocalDate fechaPagoCuota) {
		this.fechaPagoCuota = fechaPagoCuota;
	}

	public void setFechaEfectivaCuota(LocalDate fechaEfectivaCuota) {
		this.fechaEfectivaCuota = fechaEfectivaCuota;
	}

	public void setModalPagoCuota(String modalPagoCuota) {
		this.modalPagoCuota = modalPagoCuota;
	}

	public void setEstadoCuota(char estadoCuota) {
		this.estadoCuota = estadoCuota;
	}

	@Override
	public String toString() {
		return "CuotaPago [codCuota=" + codCuota + ", codComprobante=" + codComprobante + ", transCuota=" + transCuota
				+ ", mensCuota=" + mensCuota + ", fechaPagoCuota=" + fechaPagoCuota + ", fechaEfectivaCuota="
				+ fechaEfectivaCuota + ", modalPagoCuota=" + modalPagoCuota + ", estadoCuota=" + estadoCuota + "]";
	}
	
}

package modelo;

public class Garantias_Prestamo {

	private int codGarantia;
	private int codPrestamo;
	
	
	
	public Garantias_Prestamo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Garantias_Prestamo(int codGarantia, int codPrestamo) {
		this.codGarantia = codGarantia;
		this.codPrestamo = codPrestamo;
	}

	public int getCodGarantia() {
		return codGarantia;
	}

	public int getCodPrestamo() {
		return codPrestamo;
	}

	public void setCodGarantia(int codGarantia) {
		this.codGarantia = codGarantia;
	}

	public void setCodPrestamo(int codPrestamo) {
		this.codPrestamo = codPrestamo;
	}

	@Override
	public String toString() {
		return "Garantias_Prestamo [codGarantia=" + codGarantia + ", codPrestamo=" + codPrestamo + "]";
	}
}

package modelo;

public class Prestamo {

	private int codPrestamo;
	private String fiador;
	
	public Prestamo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Prestamo(int codPrestamo, String fiador) {
		this.codPrestamo = codPrestamo;
		this.fiador = fiador;
	}

	public int getCodPrestamo() {
		return codPrestamo;
	}

	public String getFiador() {
		return fiador;
	}

	public void setCodPrestamo(int codPrestamo) {
		this.codPrestamo = codPrestamo;
	}

	public void setFiador(String fiador) {
		this.fiador = fiador;
	}

	@Override
	public String toString() {
		return "Prestamo [codPrestamo=" + codPrestamo + ", fiador=" + fiador + "]";
	}
	
}

package modelo;

public class CuotasPagoInversion {

	private int codCuotaInversion;
	private String cuentaPagoActual;
	
	
	public CuotasPagoInversion() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CuotasPagoInversion(int codCuotaInversion, String cuentaPagoActual) {
		this.codCuotaInversion = codCuotaInversion;
		this.cuentaPagoActual = cuentaPagoActual;
	}
	
	public int getCodCuotaInversion() {
		return codCuotaInversion;
	}
	public String getCuentaPagoActual() {
		return cuentaPagoActual;
	}
	public void setCodCuotaInversion(int codCuotaInversion) {
		this.codCuotaInversion = codCuotaInversion;
	}
	public void setCuentaPagoActual(String cuentaPagoActual) {
		this.cuentaPagoActual = cuentaPagoActual;
	}
	@Override
	public String toString() {
		return "CuotasPagoInversion [codCuotaInversion=" + codCuotaInversion + ", cuentaPagoActual=" + cuentaPagoActual
				+ "]";
	}
}

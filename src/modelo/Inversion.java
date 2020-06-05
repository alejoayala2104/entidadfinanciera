package modelo;

public class Inversion {

	private int codInversion;
	private String cuentaPagoGeneral;
	
	public Inversion() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Inversion(int codInversion, String cuentaPagoGeneral) {
		this.codInversion = codInversion;
		this.cuentaPagoGeneral = cuentaPagoGeneral;
	}

	public int getCodInversion() {
		return codInversion;
	}

	public String getCuentaPagoGeneral() {
		return cuentaPagoGeneral;
	}

	public void setCodInversion(int codInversion) {
		this.codInversion = codInversion;
	}

	public void setCuentaPagoGeneral(String cuentaPagoGeneral) {
		this.cuentaPagoGeneral = cuentaPagoGeneral;
	}

	@Override
	public String toString() {
		return "Inversion [codInversion=" + codInversion + ", cuentaPagoGeneral=" + cuentaPagoGeneral + "]";
	}	
}

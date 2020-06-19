package modelo;

public class Garantia {

	private int codGarantia;
	private String clienteGarantia;
	private String tipoGarantia;
	private String valorGarantia;
	private String ubiGarantia;
	
	
	public Garantia() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Garantia(int codGarantia, String tipoGarantia, String valorGarantia,
			String ubiGarantia) {
		this.codGarantia = codGarantia;
		this.tipoGarantia = tipoGarantia;
		this.valorGarantia = valorGarantia;
		this.ubiGarantia = ubiGarantia;
	}

	public Garantia(int codGarantia, String clienteGarantia, String tipoGarantia, String valorGarantia,
			String ubiGarantia) {
		this.codGarantia = codGarantia;
		this.clienteGarantia = clienteGarantia;
		this.tipoGarantia = tipoGarantia;
		this.valorGarantia = valorGarantia;
		this.ubiGarantia = ubiGarantia;
	}

	public int getCodGarantia() {
		return codGarantia;
	}

	public String getClienteGarantia() {
		return clienteGarantia;
	}

	public String getTipoGarantia() {
		return tipoGarantia;
	}

	public String getValorGarantia() {
		return valorGarantia;
	}

	public String getUbiGarantia() {
		return ubiGarantia;
	}

	public void setCodGarantia(int codGarantia) {
		this.codGarantia = codGarantia;
	}

	public void setClienteGarantia(String clienteGarantia) {
		this.clienteGarantia = clienteGarantia;
	}

	public void setTipoGarantia(String tipoGarantia) {
		this.tipoGarantia = tipoGarantia;
	}

	public void setValorGarantia(String valorGarantia) {
		this.valorGarantia = valorGarantia;
	}

	public void setUbiGarantia(String ubiGarantia) {
		this.ubiGarantia = ubiGarantia;
	}

	@Override
	public String toString() {
		return "COD: " + this.codGarantia + " - TIPO: " + this.tipoGarantia.toUpperCase() + " - VALOR: $" + this.valorGarantia.toUpperCase() +
				" - UBICACIÓN: " + this.ubiGarantia.toUpperCase();
	}
	
	public String mostrarGarantia() {
		return "COD: " + this.codGarantia + " - TIPO: " + this.tipoGarantia.toUpperCase() + " - VALOR: $" + this.valorGarantia.toUpperCase() +
				" - UBICACIÓN: " + this.ubiGarantia.toUpperCase();
	}
}

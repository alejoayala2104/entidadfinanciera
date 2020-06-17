package modelo;

public class CuentaBancaria {

	private String numCuentaBanc;
	private String clienteCuenta;
	private String bancoCuentaBanc;
	private String tipoCuentaBanc;
	
	public CuentaBancaria() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CuentaBancaria(String numCuentaBanc, String clienteCuenta, String bancoCuentaBanc, String tipoCuentaBanc) {
		this.numCuentaBanc = numCuentaBanc;
		this.clienteCuenta = clienteCuenta;
		this.bancoCuentaBanc = bancoCuentaBanc;
		this.tipoCuentaBanc = tipoCuentaBanc;
	}

	public String getNumCuentaBanc() {
		return numCuentaBanc;
	}

	public String getClienteCuenta() {
		return clienteCuenta;
	}

	public String getBancoCuentaBanc() {
		return bancoCuentaBanc;
	}

	public String getTipoCuentaBanc() {
		return tipoCuentaBanc;
	}

	public void setNumCuentaBanc(String numCuentaBanc) {
		this.numCuentaBanc = numCuentaBanc;
	}

	public void setClienteCuenta(String clienteCuenta) {
		this.clienteCuenta = clienteCuenta;
	}

	public void setBancoCuentaBanc(String bancoCuentaBanc) {
		this.bancoCuentaBanc = bancoCuentaBanc;
	}

	public void setTipoCuentaBanc(String tipoCuentaBanc) {
		this.tipoCuentaBanc = tipoCuentaBanc;
	}

	@Override
	public String toString() {
		return "CuentaBancaria [numCuentaBanc=" + numCuentaBanc + ", clienteCuenta=" + clienteCuenta
				+ ", bancoCuentaBanc=" + bancoCuentaBanc + ", tipoCuentaBanc=" + tipoCuentaBanc + "]";
	}	
	
	public String mostrarCuentaBancaria() {		
		return "NÚMERO DE CUENTA: " + this.numCuentaBanc + " - BANCO: " + this.bancoCuentaBanc.toUpperCase() + " - TIPO: " + tipoCuentaBanc;
	}
}

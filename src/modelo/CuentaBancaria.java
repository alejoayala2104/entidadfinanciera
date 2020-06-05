package modelo;

public class CuentaBancaria {

	private String numCuentaBanc;
	private String clienteCuenta;
	private String bancoCuentaBanc;
	private char tipoCuentaBanc;
	
	public CuentaBancaria() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CuentaBancaria(String numCuentaBanc, String clienteCuenta, String bancoCuentaBanc, char tipoCuentaBanc) {
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

	public char getTipoCuentaBanc() {
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

	public void setTipoCuentaBanc(char tipoCuentaBanc) {
		this.tipoCuentaBanc = tipoCuentaBanc;
	}

	@Override
	public String toString() {
		return "CuentaBancaria [numCuentaBanc=" + numCuentaBanc + ", clienteCuenta=" + clienteCuenta
				+ ", bancoCuentaBanc=" + bancoCuentaBanc + ", tipoCuentaBanc=" + tipoCuentaBanc + "]";
	}
}

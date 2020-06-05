package modelo;

public class Cliente {

	private String cedula;
	private String nombres;
	private String telefono;
	private String email;
	private String direccion;
	
	
	
	public Cliente() {
	}

	public Cliente(String cedula, String nombres, String telefono, String email, String direccion) {
		this.cedula = cedula;
		this.nombres = nombres;
		this.telefono = telefono;
		this.email = email;
		this.direccion = direccion;
	}

	public String getCedula() {
		return cedula;
	}

	public String getNombres() {
		return nombres;
	}

	public String getTelefono() {
		return telefono;
	}

	public String getEmail() {
		return email;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setCedula(String cedula) {
		this.cedula = cedula;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	@Override
	public String toString() {
		return "Cliente [cedula=" + cedula + ", nombres=" + nombres + ", telefono=" + telefono + ", email=" + email
				+ ", direccion=" + direccion + "]";
	}
}

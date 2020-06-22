package controlador;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.embed.swt.FXCanvas;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyEvent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Window;
import modelo.*;

public class TransControlador implements Initializable {
	
	//Iconos
	
    @FXML
    private ImageView inicio_logo = new ImageView (new Image (getClass().getResourceAsStream("/vista/icon/inicio_logo.png")));

    @FXML
    private ImageView reg_logo = new ImageView (new Image (getClass().getResourceAsStream("/vista/icon/reg_logo.png")));
    
    @FXML
    private ImageView cons_logo = new ImageView (new Image (getClass().getResourceAsStream("/vista/icon/cons_logo.png")));
    
    @FXML
    private ImageView act_logo = new ImageView (new Image (getClass().getResourceAsStream("/vista/icon/act_logo.png")));
	
	ControladorGeneral controlGeneral = new ControladorGeneral();
		
	//Objetos para manejar la base de datos.
	Cliente objCliente = new Cliente(null,null,null,null,null);
	CuentaBancaria objCuentaBancaria = new CuentaBancaria();
	Garantia objGarantia = new Garantia();
	Garantias_Prestamo objGarantias_Prestamo = new Garantias_Prestamo();
	Inversion objInversion = new Inversion();
	Prestamo objPrestamo = new Prestamo();
	Transaccion objTransaccion = new Transaccion();
	
	//Generales	
	String cedulaCliente,tipoTrans,cedulaFiador;
	double monto,tasaEA,mensualidad;
	int numCuotas;
	LocalDate fechaIniciacion,fechaTermino,fechaSolicitud;
	
	ResultSet clienteRegistro;
	
	//Banderas
    private boolean fiadorOK = false;
    
	//Main Transacciones
    @FXML
    private BorderPane bpnTransMain;    
    @FXML
    private StackPane stpCenter;
    @FXML
    private Label regTransBienvenido;
    
    //Registrar transacción: Cedula
    @FXML
    private AnchorPane regTransCedula;
    @FXML
    private TextField txfRegTransCedula;   
    
    
    //Registrar transacción: Tipo de transacción
    @FXML
    private AnchorPane regTransTipo;
    @FXML
    private ComboBox<String> regTransCbxTipoTrans;
    
    
    //Registrar transacción: Simulación
    @FXML
    private AnchorPane regTransSimulacion;
    @FXML
    private TextField txfRegTransMonto;
    @FXML
    private TextField txfRegTransNumCuotas;
    @FXML
    private TextField txfRegTransTasa;
    @FXML
    private DatePicker dtpFechaIniciacion;
    @FXML
    private TableView<SimulacionCredito> tableSimulacion;
    @FXML
    private TableColumn<SimulacionCredito, Integer> tableSimulacionNoCuota;
    @FXML
    private TableColumn<SimulacionCredito, Double> tableSimulacionMens;
    @FXML
    private TableColumn<SimulacionCredito, LocalDate> tableSimulacionFPago;    
    ObservableList<SimulacionCredito> listaSimulacion;
    
    
    //Registrar transacción : Soportes - Garantias
    @FXML
    private AnchorPane regTransSoportes;     
    @FXML
    private Label lblRegTransClieSinGarantias;    
    @FXML
    private VBox vbxRegTransTablaGarantias;
    @FXML
    private TableView<Garantia> tableRegTransGarantias;
    @FXML
    private TableColumn<Garantia, Integer> tableRegTransGarantiasCod;
    @FXML
    private TableColumn<Garantia, String> tableRegTransGarantiasTipo;
    @FXML
    private TableColumn<Garantia, Double> tableRegTransGarantiasVal;
    @FXML
    private TableColumn<Garantia, String> tableRegTransGarantiasUbi;
    @FXML
    private HBox hbxGarantiasAñadidas;
    @FXML
    private TextArea taGarantiasAñadidas;
    ObservableList<Garantia> listaGarantias;
    ArrayList<Garantia> listaGarantiasAñadidas = new ArrayList<Garantia>();     
      
    
    //Registrar transacción : Soportes - Fiador  
    @FXML
    private AnchorPane regTransSoportesFiador; 
    @FXML
    private TextField txfRegTransFiador;        
    
   //Registrar transacción: Información de Pago Inversión
    @FXML
    private AnchorPane regTransInfoPago;
    @FXML
    private Label lblRegTransClienteSinCuentas;
    @FXML
    private VBox vbxRegTransInfoPago;
    @FXML
    private TableView<CuentaBancaria> tableRegTransCuentasBanc;
    @FXML
    private TableColumn<CuentaBancaria, String> tableColumnRegTransCuentasBancNumCuenta;
    @FXML
    private TableColumn<CuentaBancaria, String> tableColumnRegTransCuentasBancBanco;
    @FXML
    private TableColumn<CuentaBancaria, Character> tableColumnRegTransCuentasBancTipo;
    @FXML
    private TextField txfRegTransCuentaAsociada;      
    ObservableList<CuentaBancaria> listaCuentasBancarias;
    CuentaBancaria cuentaBancariaAsociada = new CuentaBancaria();
    
        
        
   
	@FXML    
    public void entrarHome(ActionEvent event) throws IOException {
    	Parent home = FXMLLoader.load(getClass().getResource("/vista/home.fxml"));
		Scene homeScene = new Scene(home);
		Window nodo = ((Node) event.getSource()).getScene().getWindow();
		Stage ventana = (Stage)(nodo);
		ventana.setScene(homeScene);
		ventana.show();
    }
		
	
    
    @FXML
    public void registrarTrans(ActionEvent event) throws IOException {    	
    	txfRegTransCedula.clear();
    	esconderPanesMenosIndicado(regTransCedula);
    }

    @FXML
    public void continuarATipoTransaccion(ActionEvent event) throws IOException, SQLException {
    	
    	//Resetea todo si se decide escribir otro usuario al darle atrás
    	if(!(txfRegTransCedula.getText().equalsIgnoreCase(objCliente.getCedula()))) {
    		resetearTodo();
    	}
    	
    	clienteRegistro = controlGeneral.ejecutarSentencia("SELECT * from cliente WHERE cedula like '" + txfRegTransCedula.getText() +"';");
    	if(clienteRegistro.next()) {
    		cedulaCliente = clienteRegistro.getString("cedula");    		 		 	
    		objTransaccion.setClienteTrans(cedulaCliente);
    		esconderPanesMenosIndicado(regTransTipo);
    	}
    	else {
    		controlGeneral.mostrarAlerta(AlertType.ERROR, "Cédula incorrecta", "Cliente no encontrado", "No se encontraron registros del cliente en el sistema.");
    	}    	
    }
    
    public void resetearTodo() {
    	this.objCliente = new Cliente(null,null,null,null,null);
    	this.objCuentaBancaria = new CuentaBancaria();
    	this.objGarantia = new Garantia();
    	this.objGarantias_Prestamo = new Garantias_Prestamo();
    	this.objInversion = new Inversion();
    	this.objPrestamo = new Prestamo();
    	this.objTransaccion = new Transaccion();
		
		//Generales	
		this.cedulaCliente = "";
		this.tipoTrans = null;
		this.cedulaFiador=null;
		this.monto = 0;this.tasaEA=0;this.mensualidad=0;
		this.numCuotas = 0;
		this.fechaIniciacion=null;
		this.fechaTermino=null;
		this.fechaSolicitud=null;
		
		this.clienteRegistro=null;
		
		txfRegTransMonto.clear();
		txfRegTransNumCuotas.clear();
		txfRegTransTasa.clear();
		dtpFechaIniciacion.setValue(LocalDate.now());
		txfRegTransCuentaAsociada.clear();
		txfRegTransFiador.clear();
		tableSimulacion.getItems().clear();
		
		this.listaCuentasBancarias = null;
		this.cuentaBancariaAsociada = new CuentaBancaria();
		this.listaGarantias = null;
		this.listaGarantiasAñadidas = new ArrayList<Garantia>();  	    
		this.listaSimulacion = null;
    }

    @FXML
    public void continuarASimulacion(ActionEvent event) {
    	//Guarda el tipo de transacción en el objeto.
    	if(regTransCbxTipoTrans.getValue().equals("Inversión")) {
			objTransaccion.setTipoTrans('I');			
    	}
		else {
			objTransaccion.setTipoTrans('P');			
		}
    	esconderPanesMenosIndicado(regTransSimulacion);
    }
    
    @FXML
    public void generarSimulacion(ActionEvent event) {
    	
    	if(txfRegTransMonto.getText().isEmpty() || txfRegTransNumCuotas.getText().isEmpty()
    			|| txfRegTransTasa.getText().isEmpty() |dtpFechaIniciacion.getValue() == null) {
    		controlGeneral.mostrarAlerta(AlertType.ERROR, "Campos vacíos", "Por favor rellene todos los campos", null);
    		return;    		
    	}
    	
    	//Fórmula de la anualidad para calcular la mensualidad.
    	this.monto = Double.parseDouble(txfRegTransMonto.getText());
    	this.numCuotas = Integer.parseInt(txfRegTransNumCuotas.getText());
    	this.tasaEA = Double.parseDouble(txfRegTransTasa.getText());
  
    	double tasaMensual = (Math.pow(tasaEA+1, 1.0/12.0)) - 1.0;
    	
    	if(objTransaccion.getTipoTrans()=='I') {
    		this.mensualidad = monto * ((Math.pow(1+(tasaMensual/100.0), numCuotas))-1);
    	}else {
    		this.mensualidad= (tasaMensual*monto)/(1.0 - (Math.pow((1+tasaMensual),-numCuotas)));
    	}
    	
    	this.fechaIniciacion = dtpFechaIniciacion.getValue();        	
    	
    	//Table view simulación
    	listaSimulacion = FXCollections.observableArrayList();
    	tableSimulacionNoCuota.setCellValueFactory(new PropertyValueFactory<>("noCuota"));
    	tableSimulacionMens.setCellValueFactory(new PropertyValueFactory<>("mensualidad"));
    	tableSimulacionFPago.setCellValueFactory(new PropertyValueFactory<>("fechaPago"));
    	
    	for(int i=0; i<numCuotas;i++) {
    		int noCuota = i+1;
    		LocalDate fechaPago = fechaIniciacion.plusMonths(i+1);
    		SimulacionCredito simulacionCuota = new SimulacionCredito(noCuota, mensualidad, fechaPago);
    		listaSimulacion.add(simulacionCuota);
    	}
    	
    	tableSimulacion.setItems(listaSimulacion);
    	
    }
    
        
    @FXML
    public void continuarASoportes(ActionEvent event) throws SQLException {
    	
    	//Validación campos vacíos.
    	if(txfRegTransMonto.getText().isEmpty() || txfRegTransNumCuotas.getText().isEmpty()
    			|| txfRegTransTasa.getText().isEmpty() |dtpFechaIniciacion.getValue() == null) {
    		controlGeneral.mostrarAlertaSinContent(AlertType.ERROR, "Campos vacíos", "Por favor rellene todos los campos");
    		return;	
    	}
    	//Se muestra la simulación de crédito según los datos introducidos.
    	//A partir de este método se obtiene la mensualidad de las cuotas.
    	try {
    	generarSimulacion(event);
    	}catch(Exception e) {
    		controlGeneral.mostrarAlerta(AlertType.ERROR, "Error de Simulación", "ERROR en los valores de la simulación",
    				"Por favor revise los datos de la simulación.");
    	}
    	
    	if(objTransaccion.getTipoTrans()=='I') {
    		//Por defecto, el cliente no tiene cuentas bancarias, entonces se esconden antes de hacer la búsqueda.
    		vbxRegTransInfoPago.setVisible(false);
    		lblRegTransClienteSinCuentas.setVisible(true);
    		
    		//Mostrar las cuentas bancarias
    		mostrarCuentasBancarias();
    	}
    	else {
    		//Por defecto, el cliente no tiene garantías entonces se esconden los Panes y se muestra el label de no garantías.
        	vbxRegTransTablaGarantias.setVisible(false);
    		taGarantiasAñadidas.setVisible(false);
    		hbxGarantiasAñadidas.setVisible(false);
    		lblRegTransClieSinGarantias.setVisible(true);
    		//Para limpiar el TextArea
    		cancelarAñadidasRegTrans(event);
    		//Se muestra la tabla de garantías
    		mostrarGarantiasRegTrans();
    	}
    	
    	//Agregar valores al objeto transacción.
    	objTransaccion.setMontoTrans(monto);
    	objTransaccion.setTasaTrans(tasaEA);
    	objTransaccion.setNumCuotas(numCuotas);
    	objTransaccion.setFechaIniciacion(fechaIniciacion);
    	//La fecha de término se cumple cuando se pagan todas las cuotas.
    	fechaTermino = fechaIniciacion.plusMonths(numCuotas);
    	objTransaccion.setFechaTermino(fechaTermino);
    	objTransaccion.setFechaSolicitud(LocalDate.now());
    	objTransaccion.setEstadoSolicitud("INCOMPLETA");

    }
    
    public void mostrarCuentasBancarias() {
    	String buscarCuentasBanc = "SELECT cuentasbancarias.* FROM cliente join cuentasbancarias ON"
    			+ " clientecuenta=cedula where cedula like '"+objTransaccion.getClienteTrans()+"';";
    	ResultSet cuentasBancarias = controlGeneral.ejecutarSentencia(buscarCuentasBanc);
    	
    	try {
    		if(cuentasBancarias.next()) {
    			//Se muestra el Vbox de las cuentas disponibles, y se esconde el mensaje de que el cliente no tiene cuentas.
    			vbxRegTransInfoPago.setVisible(true);
    			lblRegTransClienteSinCuentas.setVisible(false);
    			
    			//Se definen las columnas de la tabla.
    			tableColumnRegTransCuentasBancNumCuenta.setCellValueFactory(new PropertyValueFactory<>("numCuentaBanc"));
    			tableColumnRegTransCuentasBancBanco.setCellValueFactory(new PropertyValueFactory<>("bancoCuentaBanc"));
    			tableColumnRegTransCuentasBancTipo.setCellValueFactory(new PropertyValueFactory<>("tipoCuentaBanc"));
    			
    			//Buscar las cuentas
    			listaCuentasBancarias = buscarCuentasBancarias(objTransaccion.getClienteTrans());
    			//Se agregan los resultados a la tabla
    			tableRegTransCuentasBanc.setItems(listaCuentasBancarias);
    		}
    		
    	}catch(Exception e) {
    		System.out.println(e.getLocalizedMessage());
    	}
    	
    	//Muestra el pane de Información de Pago.
    	esconderPanesMenosIndicado(regTransInfoPago);    	
    	
    }
    
    public void mostrarGarantiasRegTrans() {
    	//Consultar si el cliente asociado tiene garantías.
    	String buscarGarantias = "SELECT  garantias.* FROM garantias JOIN cliente ON clientegarantia=cedula WHERE cedula like '"
    	+ objTransaccion.getClienteTrans()+"';";
    	ResultSet garantias = controlGeneral.ejecutarSentencia(buscarGarantias); 
		
		//Si el cliente tiene garantías se definen las columnas en las tablas. Después, se muestran los resultados.
		try {
    		if(garantias.next()) {
    			//Los panes de la información de las garantías se vuelven visibles.
    			vbxRegTransTablaGarantias.setVisible(true);
    	    	taGarantiasAñadidas.setVisible(true);
    			lblRegTransClieSinGarantias.setVisible(false);
    			hbxGarantiasAñadidas.setVisible(true);
    			
    			//Se definen las columnas de la tabla.
    			tableRegTransGarantiasCod.setCellValueFactory(new PropertyValueFactory<>("codGarantia"));
    			tableRegTransGarantiasTipo.setCellValueFactory(new PropertyValueFactory<>("tipoGarantia"));
    			tableRegTransGarantiasVal.setCellValueFactory(new PropertyValueFactory<>("valorGarantia"));
    			tableRegTransGarantiasUbi.setCellValueFactory(new PropertyValueFactory<>("ubiGarantia"));    			    		
    			
    			//Buscar garantías retorna un ObservableList<> con las garantías del usuario.
    			listaGarantias = buscarGarantias(objTransaccion.getClienteTrans());
    			//Se agrega esa lista de garantías a la tabla.
    			tableRegTransGarantias.setItems(listaGarantias);
    		}
		}catch(Exception e) {
			System.out.println(e.getLocalizedMessage());
		}
			
		//Muestra el pane de Soportes.
		esconderPanesMenosIndicado(regTransSoportes);    	
    }
    
    //Método que busca las garantías que un cliente tiene registradas.
    public ObservableList<Garantia> buscarGarantias(String clienteGarantiaRS) throws SQLException{
    	
    	//Se crea la lista de garantías.
    	ObservableList<Garantia> listaGar = FXCollections.observableArrayList();
    	//Se hace la consulta en la base de datos.
    	String buscarGarantias = "SELECT  garantias.* FROM garantias JOIN cliente ON clientegarantia=cedula WHERE cedula like '"
    	    	+ clienteGarantiaRS +"';";
    	ResultSet garantiasRS = controlGeneral.ejecutarSentencia(buscarGarantias);
    	
    	//Si se encontraron resultados, cree un objeto Garantía y agreguelo a la lista.
    	while(garantiasRS.next()) {
    	Garantia objGarantiaRS = new Garantia(garantiasRS.getInt("codGarantia"),garantiasRS.getString("clienteGarantia"),garantiasRS.getString("tipoGarantia"),
    			garantiasRS.getString("valorGarantia"),garantiasRS.getString("ubiGarantia"));
    	listaGar.add(objGarantiaRS);
    	}
    	return listaGar;
    }
    
    //Método que busca las cuentas bancarias que un cliente tiene registradas.
    public ObservableList<CuentaBancaria> buscarCuentasBancarias(String clienteCuenta) throws SQLException{
    	
    	//Se crea la lista de cuentas bancarias.
    	ObservableList<CuentaBancaria> listaCuentas = FXCollections.observableArrayList();
    	//Se hace la consulta en la base de datos.
    	String buscarCuentasBancarias = "SELECT cuentasbancarias.* FROM cliente join cuentasbancarias ON"
    			+ " clientecuenta=cedula where cedula like '"+objTransaccion.getClienteTrans()+"';";
    	ResultSet cuentasBancarias = controlGeneral.ejecutarSentencia(buscarCuentasBancarias);
    	
    	//Si se encontraron resultados, cree un objeto garantía y agreguelo a la lista.
    	while(cuentasBancarias.next()) {
    		CuentaBancaria objCuentaBancaria = new CuentaBancaria(cuentasBancarias.getString("numcuentabanc"),cuentasBancarias.getString("clientecuenta"),
    				cuentasBancarias.getString("bancocuentabanc"), cuentasBancarias.getString("tipocuentabanc"));
    		listaCuentas.add(objCuentaBancaria);
    	}
    	
    	return listaCuentas;
    }

    @FXML
    public void añadirGarantiaRegTrans(ActionEvent event) {
    	
    	if(tableRegTransGarantias.getSelectionModel().isEmpty()) {
    		controlGeneral.mostrarAlerta(AlertType.ERROR, "Garantía inválida", "Garantía no seleccionada", "Por favor seleccione una garantía para añadirla.");
    		return;
    	}
    	
    	Garantia garantiaAñadida = new Garantia();
    	garantiaAñadida = tableRegTransGarantias.getSelectionModel().getSelectedItem();
    	for(Garantia gar:listaGarantiasAñadidas) {
    		if(gar.equals(garantiaAñadida))
    			return;
    	}
    	listaGarantiasAñadidas.add(garantiaAñadida);
    	taGarantiasAñadidas.appendText("\n"+garantiaAñadida.mostrarGarantia());
    }
    
    @FXML
    public void añadirCuentaBancariaRegTrans(ActionEvent event) {
    	
    	if(tableRegTransCuentasBanc.getSelectionModel().isEmpty()) {
    		controlGeneral.mostrarAlerta(AlertType.ERROR, "Cuenta bancaria inválida", "Cuenta bancaria no seleccionada",
    				"Por favor seleccione una cuenta bancaria para añadirla.");
    		return;
    	}
    	//Se asocia la cuenta seleccionada al objeto de cuenta bancaria de la inversión.
    	cuentaBancariaAsociada = tableRegTransCuentasBanc.getSelectionModel().getSelectedItem();
    	objInversion.setCuentaPagoGeneral(cuentaBancariaAsociada.getNumCuentaBanc());
    	//Se muestra la información de la cuenta bancaria seleccionada.
    	txfRegTransCuentaAsociada.setText(cuentaBancariaAsociada.mostrarCuentaBancaria());
    }
   

    @FXML
    public void cancelarAñadidasRegTrans(ActionEvent event) {
    	listaGarantiasAñadidas = new ArrayList<>();
    	taGarantiasAñadidas.clear();
    }
        
    @FXML
    public void nuevaGarantiaRegTrans(ActionEvent event) throws IOException {

    	//Se carga el FXML Nueva Garantía.
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/nuevaGarantia.fxml"));
    	Parent interfazNuevaGarantia = loader.load();
		//Se crea un objeto del constructor NuevaGarantiaControlador y se le carga el controlador del fxml cargado anteriormente.
    	NuevaGarantiaControlador nuevaGarantiaControlador = loader.getController();
    	//Se ejecuta el método de cambio de cédula del controlador NuevaGarantía.
    	nuevaGarantiaControlador.setClienteNuevaGarantia(objTransaccion.getClienteTrans());
    	
    	Scene escenaNuevaGarantia = new Scene(interfazNuevaGarantia);	
		Stage ventanaNuevaGarantia = new Stage();
		ventanaNuevaGarantia.setScene(escenaNuevaGarantia);
		ventanaNuevaGarantia.setTitle("Nueva garantía");
		ventanaNuevaGarantia.show();

    }
    
    @FXML
    public void nuevaCuentaBancariaRegTrans(ActionEvent event) throws IOException {
    	
    	//Se carga el FXML Nueva Garantía.
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/nuevaCuentaBanc.fxml"));
    	Parent interfazNuevaCuenta = loader.load();
		//Se crea un objeto del constructor NuevaGarantiaControlador y se le carga el controlador del fxml cargado anteriormente.
    	NuevaCuentaBancControlador nuevaCuentaBancControlador = loader.getController();
    	//Se ejecuta el método de cambio de cédula del controlador NuevaGarantía.
    	nuevaCuentaBancControlador.setClienteNuevaCuentabanc(objTransaccion.getClienteTrans());
    	
    	Scene escenaNuevaCuenta = new Scene(interfazNuevaCuenta);	
		Stage ventanaNuevaCuenta = new Stage();
		ventanaNuevaCuenta.setScene(escenaNuevaCuenta);
		ventanaNuevaCuenta.setTitle("Nueva cuenta");
		ventanaNuevaCuenta.show();
    }
    
    @FXML
    public void refrescarTableRegTransGarantias(ActionEvent event) {    	
    	mostrarGarantiasRegTrans();
    }    
    
    @FXML
    public void refrescarTableRegTransCuentasBanc(ActionEvent event) {
    	mostrarCuentasBancarias();
    }
    
    @FXML
    public void continuarAFiadorRegTrans(ActionEvent event) throws SQLException {    	
    	
    	if(listaGarantiasAñadidas.isEmpty()) {
    		controlGeneral.mostrarAlerta(AlertType.INFORMATION, "Garantías pendientes", "No se añadieron garantías", "El estado de la solicitud será INCOMPLETA.");
    		objTransaccion.setEstadoSolicitud("INCOMPLETA");
    	}
    	esconderPanesMenosIndicado(regTransSoportesFiador);    	
    }
    
    @FXML
    public void finalizarRegTransPrestamo(ActionEvent event) throws SQLException {

    	if(txfRegTransFiador.getText().isEmpty()) {
    		controlGeneral.mostrarAlerta(AlertType.INFORMATION, "Fiador pendiente", "Fiador no asociado", "Fiador pendiente: El estado de la solicitud será INCOMPLETA.");
    		objTransaccion.setEstadoSolicitud("INCOMPLETA");
    		cedulaFiador = null;
    	}
    	else if(txfRegTransFiador.getText().equals(objTransaccion.getClienteTrans())){
    		controlGeneral.mostrarAlerta(AlertType.ERROR, "Fiador inválido", "Fiador inválido", "El cliente solicitante no puede ser fiador de sí mismo.");
    		return;
    	}
    	else {
    		fiadorOK = controlGeneral.validarCliente(txfRegTransFiador);
    		if(fiadorOK==false) {
    			controlGeneral.mostrarAlerta(AlertType.ERROR, "Fiador inválido", "Fiador no encontrado", "Si no se ingresará fiador, por favor deje la casilla vacía.");
    			return;
    		}
    		else{
    			cedulaFiador=txfRegTransFiador.getText();
    			objPrestamo.setFiador(cedulaFiador);
    			controlGeneral.mostrarAlertaSinContent(AlertType.INFORMATION, "Fiador asociado", "Fiador definido correctamete");
    		}
    	}
    	
    	if(!(listaGarantiasAñadidas.isEmpty()) && fiadorOK)
    		objTransaccion.setEstadoSolicitud("PARA EVALUAR");
    	
    	//Se crea la transacción en la base de datos y se obtiene el código generado del autoincremento.
     	ResultSet codTransGenerado = insertarTransaccion();
    	if(codTransGenerado.next()) {
    		objTransaccion.setCodTrans(codTransGenerado.getInt("codtrans"));
    		objPrestamo.setCodPrestamo(objTransaccion.getCodTrans());
    	}
    	
    	//Generar el préstamo a partir del código de transferencia obtenido.
    	String insertarPrestamo = "";
    	if(objPrestamo.getFiador()!=null) {
    		insertarPrestamo = "INSERT INTO prestamos VALUES ("+objPrestamo.getCodPrestamo()+",'"+objPrestamo.getFiador()+"');";    		
    	}
    	else {
    		insertarPrestamo = "INSERT INTO prestamos(codPrestamo) VALUES ("+objPrestamo.getCodPrestamo()+");";
    	}
    	controlGeneral.ejecutarSentenciaInsert(insertarPrestamo);
    	
    	//Ingresar las garantías a la base de datos.
    	String insertarGarPrest = "";
    	if(!(listaGarantiasAñadidas.isEmpty())) {
    		for(Garantia gar : listaGarantiasAñadidas) {
    			insertarGarPrest = "INSERT INTO garantias_prestamo VALUES("+gar.getCodGarantia()+","+objPrestamo.getCodPrestamo()+");";
    			controlGeneral.ejecutarSentenciaInsert(insertarGarPrest);
    		}
    		controlGeneral.mostrarAlerta(AlertType.INFORMATION, "Garantias añadidas", "Garantías asociadas al préstamo exitosamente.", null);
    	}
    	controlGeneral.mostrarAlerta(AlertType.INFORMATION, "Solicitud creada", "Solicitud de préstamo creada", "Estado: " + objTransaccion.getEstadoSolicitud());
    	esconderPanesMenosIndicado(regTransBienvenido);
    }
    
    @FXML
    public void finalizarRegTransInversion(ActionEvent event) throws SQLException {
    	
    	//Validación de cuenta bancaria obligatorioa.
    	if(objInversion.getCuentaPagoGeneral()==null) {
    		controlGeneral.mostrarAlerta(AlertType.ERROR, "Cuenta bancaria inválida", "Cuenta bancaria obligatoria",
    				"Es obligatorio asociar una cuenta bancaria a la inversión.");
    		return;
    	}
    	
    	//Dado que el anterior if no permite seguir hasta que se haya asociado una cuenta bancaria,
    	//la siguiente linea cambia el estado de la transacción a pendiente como si estuviese completa antes de la inserción a la bd.
    	objTransaccion.setEstadoSolicitud("PARA EVALUAR");
    	
    	//Inserta la transacción en la base de datos y obtiene la key generada.
    	ResultSet codTransGenerado = insertarTransaccion();
    	if(codTransGenerado.next()) {
    		objTransaccion.setCodTrans(codTransGenerado.getInt("codtrans"));
    		objInversion.setCodInversion(objTransaccion.getCodTrans());
    	}
    	
    	//Generar la inversión en la base de datos a partir del objeto inversión
    	if(objInversion.getCuentaPagoGeneral()!=null) {
    	String insertarInversion = "INSERT INTO inversiones"
    			+ " VALUES ("+objInversion.getCodInversion()+",'"+objInversion.getCuentaPagoGeneral()+"');";
    	controlGeneral.ejecutarSentenciaInsert(insertarInversion);
    	controlGeneral.mostrarAlerta(AlertType.INFORMATION, "Solicitud creada", "Solicitud de inversión creada", "Estado: " + objTransaccion.getEstadoSolicitud());
    	}
    	esconderPanesMenosIndicado(regTransBienvenido);
    }
    
    public ResultSet insertarTransaccion() {
    	//Inserta la transacción en la base de datos y obtiene la primary key generada.
    	String insertarTrans = "INSERT INTO transacciones(clienteTrans,tipoTrans,montoTrans,tasaTrans,numCuotas,fechaSolicitud,\r\n" + 
    			"						  fechaAprobacion,fechaIniciacion,fechaTermino,estadoSolicitud)\r\n" + 
    			"						  VALUES('"+objTransaccion.getClienteTrans()+"','"+objTransaccion.getTipoTrans()+"',"+objTransaccion.getMontoTrans()+
    			","+objTransaccion.getTasaTrans()+","+objTransaccion.getNumCuotas()+",'"+objTransaccion.getFechaSolicitud()+"',"+
    			objTransaccion.getFechaAprobacion()+",'"+objTransaccion.getFechaIniciacion()+"','"+objTransaccion.getFechaTermino()+"','"+
    			objTransaccion.getEstadoSolicitud()+"')  RETURNING codTrans;";   	   
    	ResultSet codTransGenerado = controlGeneral.ejecutarSentencia(insertarTrans);  
    	
    	return codTransGenerado;
    }
    

    //Consultar transacción: Cédula.
    @FXML
    private AnchorPane consTransCedula;
    @FXML
    private TextField txfConsTransCedula;

    //Consultar transacción: Búsqueda.
    @FXML
    private AnchorPane consTransBusqueda;
    @FXML
    private TableView<Transaccion> tblConsultarTrans;
    @FXML
    private TableColumn<Transaccion, Integer> tblConsultarTransCod;
    @FXML
    private TableColumn<Transaccion, Character> tblConsultarTransTipo;
    @FXML
    private TableColumn<Transaccion, Double> tblConsultarTransMonto;
    @FXML
    private TableColumn<Transaccion, LocalDate> tblConsultarTransFechaIni;
    @FXML
    private TableColumn<Transaccion, String> tblConsultarTransEstado;
    ObservableList<Transaccion> listaTransacciones;
    
    @FXML
    public void entrarConsTrans(ActionEvent event) {
    	objTransaccion = new Transaccion();
    	txfConsTransCedula.clear();
    	esconderPanesMenosIndicado(consTransCedula);
    }
    
    @FXML 
    public void consTransContinuarABusqueda(ActionEvent event) throws SQLException {
    	
    	ResultSet clienteConsulta = controlGeneral.ejecutarSentencia("SELECT * from cliente WHERE cedula like '" + txfConsTransCedula.getText() +"';");
    	if(clienteConsulta.next()) {
    		String cedulaConsulta = clienteConsulta.getString("cedula");    		
    		try {
    			
    			listaTransacciones=buscarTransacciones(cedulaConsulta);
    		
	    		if(!listaTransacciones.isEmpty()) {
		    		tblConsultarTransCod.setCellValueFactory(new PropertyValueFactory<>("codTrans"));
		        	tblConsultarTransTipo.setCellValueFactory(new PropertyValueFactory<>("tipoTrans"));
		        	tblConsultarTransMonto.setCellValueFactory(new PropertyValueFactory<>("montoTrans"));
		        	tblConsultarTransFechaIni.setCellValueFactory(new PropertyValueFactory<>("fechaIniciacion"));
		        	tblConsultarTransEstado.setCellValueFactory(new PropertyValueFactory<>("estadoSolicitud"));  
		        	
		        	tblConsultarTrans.setItems(listaTransacciones);
	    		}
    		}catch(Exception e) {
    			System.out.println(e.toString());
    		}
        	
        	esconderPanesMenosIndicado(consTransBusqueda);
    	}
    	else {
    		controlGeneral.mostrarAlerta(AlertType.ERROR, "Cédula incorrecta", "Cliente no encontrado", "No se encontraron registros del cliente en el sistema.");
    	}
    }
   
    public ObservableList<Transaccion> buscarTransacciones(String clienteTrans) throws SQLException{    	
    
    	ObservableList<Transaccion> listaTrans = FXCollections.observableArrayList();    
    	String buscarTrans = "SELECT transacciones.* from transacciones where clientetrans like '"+clienteTrans+"';";
    	ResultSet trans = controlGeneral.ejecutarSentencia(buscarTrans);
    	
    	while(trans.next()) {
    		Transaccion objTrans = new Transaccion();
    		objTrans.setCodTrans(trans.getInt("codTrans"));
    		objTrans.setClienteTrans(trans.getString("clienteTrans"));
    		objTrans.setTipoTrans(trans.getString("tipoTrans").charAt(0));
    		objTrans.setMontoTrans(trans.getDouble("montoTrans"));
    		objTrans.setTasaTrans(trans.getDouble("tasaTrans"));
    		objTrans.setNumCuotas(trans.getInt("numCuotas"));
    		objTrans.setFechaSolicitud(trans.getDate("fechaSolicitud").toLocalDate());
    		objTrans.setFechaAprobacion(null);
    		objTrans.setFechaIniciacion(trans.getDate("fechaIniciacion").toLocalDate());
    		objTrans.setFechaTermino(trans.getDate("fechaTermino").toLocalDate());
    		objTrans.setEstadoSolicitud(trans.getString("estadoSolicitud"));    	
    		
    		listaTrans.add(objTrans);
    	}
    	return listaTrans;
    }
    
    @FXML
    public void verDetallesConsTrans(ActionEvent event) throws SQLException {
    	Transaccion transSelected = tblConsultarTrans.getSelectionModel().getSelectedItem();
    	if(transSelected==null) {
    		controlGeneral.mostrarAlerta(AlertType.ERROR, "ERROR: Transacción no seleccionada", "Transacción no seleccionada",
    				"Por favor haga click sobre alguna transacción y presione Ver Detalles.");
    		return;
    	}
    	
    	AnchorPane verDetalles = new AnchorPane();
    	TextArea txaVerDetalles = new TextArea();
    	verDetalles.getChildren().add(txaVerDetalles);
    	
    	txaVerDetalles.setText(transSelected.toString());
    	String fechaAprobacion;
    	if(transSelected.getFechaAprobacion()==null)
    		fechaAprobacion = "No definida aún";
    	else
    		fechaAprobacion = transSelected.getFechaAprobacion().toString();
    	
    	//Búsqueda de todos los datos de la transacción.
    	String datosTrans = "\nTRANSACCIÓN"+
					    	"\nCódigo:" + transSelected.getCodTrans()+
					    	"\nTipo: " +transSelected.getTipoTrans()+
					    	"\nMonto: " +transSelected.getMontoTrans()+
					    	"\nTasa efectiva anual: " + transSelected.getTasaTrans()+
					    	"\nNúmero de periodos: " + transSelected.getNumCuotas()+
					    	"\nFecha de solicitud: " + transSelected.getFechaSolicitud()+
					    	"\nFecha de aprobación: " + fechaAprobacion+
					    	"\nFecha de iniciación: " + transSelected.getFechaIniciacion()+
					    	"\nFecha de término: " + transSelected.getFechaTermino()+
					    	"\nEstado de solicitud: " + transSelected.getEstadoSolicitud();
    	
    	//Obtención de los datos del cliente.
    	String datosCliente ="";
    	ResultSet clienteTrans = controlGeneral.ejecutarSentencia("select cliente.* from cliente "
    			+ "join transacciones on cedula=clientetrans where codtrans = "+transSelected.getCodTrans()+";");
    	if(clienteTrans.next()) {
    	datosCliente = "\n\nCLIENTE"+
				    	"\nCédula: " + clienteTrans.getString("cedula")+
				    	"\nNombre: " +clienteTrans.getString("nombres")+
				    	"\nTeléfono: " + clienteTrans.getString("telefono")+
				    	"\nEmail: " + clienteTrans.getString("email")+
				    	"\nDirección: " + clienteTrans.getString("direccion");
    	}
    	
    	String datosExtra="";
    	
    	if(transSelected.getTipoTrans()=='I') {
    		//Se obtiene la cuenta bancaria.
    		ResultSet cuentaTrans = controlGeneral.ejecutarSentencia("select cuentasbancarias.* from cuentasbancarias join inversiones on cuentapagogeneral=numcuentabanc\r\n" + 
    				"join transacciones on codtrans=codinversion where codtrans ="+transSelected.getCodTrans()+";");
    		if(cuentaTrans.next()) {
    		datosExtra = "\n\nCUENTA BANCARIA"+
    					"\nNúmero de cuenta: " + cuentaTrans.getString("numcuentabanc")+
    					"\nBanco: " + cuentaTrans.getString("bancocuentabanc")+
    					"\nTipo de cuenta: " + cuentaTrans.getString("tipocuentabanc");
    		}    		 		
    	}
    	else {//Si no es una inversión, es un préstamo.
    		
    		//Se obtiene el Fiador.
    		ResultSet fiadorTrans = controlGeneral.ejecutarSentencia("select cliente.* from cliente "
    				+ "join prestamos on fiador=cedula join transacciones on codprestamo=codtrans\r\n" + 
    				"where codtrans = "+transSelected.getCodTrans()+";");
    		//Se obtiene las garantías.
    		ResultSet garantiasTrans = controlGeneral.ejecutarSentencia("select garantias.* from garantias natural join garantias_prestamo join transacciones on codprestamo=codtrans\r\n" + 
    				"where codtrans = "+transSelected.getCodTrans()+";");    		
    		
    		if(fiadorTrans.next()) {
    		datosExtra = "\n\nFIADOR"+
    				"\nCédula: " + fiadorTrans.getString("cedula")+
    		    	"\nNombre: " +fiadorTrans.getString("nombres")+
    		    	"\nTeléfono: " + fiadorTrans.getString("telefono")+
    		    	"\nEmail: " + fiadorTrans.getString("email")+
    		    	"\nDirección: " + fiadorTrans.getString("direccion");
    		}
    		StringBuilder datosGarantias = new StringBuilder();
    		if(garantiasTrans.next()) {
    			ResultSet garantiasTrans2 = controlGeneral.ejecutarSentencia("select garantias.* from garantias natural join garantias_prestamo join transacciones on codprestamo=codtrans\r\n" + 
        				"where codtrans = "+transSelected.getCodTrans()+";"); 
	    		datosGarantias.append("\n\nGARANTIAS");
	    		while(garantiasTrans2.next()) {
	    			datosGarantias.append("\n--------------------------------------"+
	    							"\nCódigo: " + garantiasTrans2.getInt("codgarantia")+
	    							"\nTipo: " + garantiasTrans2.getString("tipogarantia")+
	    							"\nValor: " + garantiasTrans2.getString("valorgarantia")+
	    							"\nUbicación: " + garantiasTrans2.getString("ubigarantia"));
	    		}
	    		
	    		datosExtra = datosExtra + datosGarantias.toString();
    		}
    	}
    	
    	String stgVerDetalles = "DETALLES\n" + datosTrans + datosCliente+datosExtra;   	
    	
    	txaVerDetalles.setText(stgVerDetalles);
    	txaVerDetalles.setPrefSize(500, 500);
    	txaVerDetalles.setStyle("-fx-font-size:16");
    	
    	Scene verDetallesScene = new Scene(verDetalles);
		Stage verDetallesStage = new Stage();
		verDetallesStage.setScene(verDetallesScene);
		verDetallesStage.setTitle("Detalles de transacción");
		verDetallesStage.show();
    	System.out.println(transSelected.toString());
    }
   
    @FXML
    private AnchorPane actTransCedula;
    
    @FXML
    private TextField txfActTransCedula;
    
    @FXML
    private AnchorPane actTransBusqueda;

    @FXML
    private TableView<Transaccion> tblActTrans;

    @FXML
    private TableColumn<Transaccion, Integer> tblActTransCod;

    @FXML
    private TableColumn<Transaccion, Character> tblActTransTipo;

    @FXML
    private TableColumn<Transaccion, Double> tblActTransMonto;

    @FXML
    private TableColumn<Transaccion, LocalDate> tblActTransFechaIni;

    @FXML
    private TableColumn<Transaccion, String> tblActTransEstado;    
    
    ObservableList<Transaccion> listaTransaccionesAct;
    

    @FXML
    private AnchorPane actTransGarantias;

    @FXML
    private Label lblActTransClienteSinGarantias;

    @FXML
    private HBox hbxActTransGarantiasAñadidas;

    @FXML
    private VBox vbxActTransTableGarantias;

    @FXML
    private TableView<Garantia> tblActTransGarantias;

    @FXML
    private TableColumn<Garantia, Integer> tblActTransGarantiasCod;

    @FXML
    private TableColumn<Garantia, String> tblActTransGarantiasTipo;

    @FXML
    private TableColumn<Garantia, String> tblActTransGarantiasValor;

    @FXML
    private TableColumn<Garantia, String> tblActTransGarantiasUbicacion;
    
    @FXML
    private ListView<Garantia> lsvActTransGarantiasAñadidas;
    
    ObservableList<Garantia> listaGarantiasAct;
    ObservableList<Garantia> listaGarantiasTrans;
    
    
    @FXML
    private AnchorPane actTransFiador;

    @FXML
    private TextField txfActTransFiador;
    
    
    
    @FXML
    private AnchorPane actTransInfoPago;

    @FXML
    private Label lblActTransClienteSinCuentas;

    @FXML
    private VBox vbxActTransCuentas;

    @FXML
    private TableView<CuentaBancaria> tblActTransCuentas;

    @FXML
    private TableColumn<CuentaBancaria, String> tblColActTransCuentasNumCuenta;

    @FXML
    private TableColumn<CuentaBancaria, String> tblColActTransCuentasBanco;

    @FXML
    private TableColumn<CuentaBancaria, String> tblColActTransCuentasTipoCuenta;

    @FXML
    private TextField txfActTransCuentaAsociada;
    
    CuentaBancaria actTransCuentaAsociada = new CuentaBancaria();
    
    ObservableList<CuentaBancaria> listaCuentasAct;
    
  
    @FXML
    public void entrarActReg(ActionEvent event) {
    	txfActTransCedula.clear();
    	objTransaccion = new Transaccion();
    	esconderPanesMenosIndicado(actTransCedula);
    }
    
    @FXML
    public void actTransContinuarABusqueda(ActionEvent event) throws SQLException {

    	ResultSet clienteConsulta = controlGeneral.ejecutarSentencia("SELECT * from cliente WHERE cedula like '" + txfActTransCedula.getText() +"';");
    	if(clienteConsulta.next()) {
    		String cedulaConsulta = clienteConsulta.getString("cedula");    		
    		try {
    			
    			listaTransaccionesAct=buscarTransacciones(cedulaConsulta);
    		
	    		if(!listaTransaccionesAct.isEmpty()) {
	    			tblActTransCod.setCellValueFactory(new PropertyValueFactory<>("codTrans"));
	    			tblActTransTipo.setCellValueFactory(new PropertyValueFactory<>("tipoTrans"));
	    			tblActTransMonto.setCellValueFactory(new PropertyValueFactory<>("montoTrans"));
	    			tblActTransFechaIni.setCellValueFactory(new PropertyValueFactory<>("fechaIniciacion"));
	    			tblActTransEstado.setCellValueFactory(new PropertyValueFactory<>("estadoSolicitud"));  
		        	
	    			tblActTrans.setItems(listaTransaccionesAct);
	    		}
    		}catch(Exception e) {
    			System.out.println(e.toString());
    		}
        	
        	esconderPanesMenosIndicado(actTransBusqueda);
    	}
    	else {
    		controlGeneral.mostrarAlerta(AlertType.ERROR, "Cédula incorrecta", "Cliente no encontrado", "No se encontraron registros del cliente en el sistema.");
    	}
    }
    
    @FXML
    public void actTransContinuarAModificacion(ActionEvent event) throws SQLException{
    	
    	if(tblActTrans.getSelectionModel().getSelectedItem()==null) {
    		controlGeneral.mostrarAlerta(AlertType.ERROR, "ERROR: Actualizar transacción", "Transacción no seleccionada",
    				"Por favor elija una de las transacciones de la tabla y presione continuar");
    	return;
    	}
    	
    	this.objTransaccion = new Transaccion();
    	this.objTransaccion = tblActTrans.getSelectionModel().getSelectedItem();
    	
    	if(this.objTransaccion.getTipoTrans()=='P') {    		
	    	mostrarGarantiasActTrans();
	    	this.listaGarantiasTrans = buscarGarantiasTrans(this.objTransaccion.getCodTrans());
	    	this.lsvActTransGarantiasAñadidas.setItems(this.listaGarantiasTrans);
    	}
    	else {
    		mostrarCuentasActTrans();
    		this.actTransCuentaAsociada = buscarCuentaBancariaTrans(this.objTransaccion.getCodTrans());
        	txfActTransCuentaAsociada.setText(this.actTransCuentaAsociada.mostrarCuentaBancaria());
    	}
    }
    
    public void mostrarCuentasActTrans() {
    	String buscarCuentasBanc = "SELECT cuentasbancarias.* FROM cliente join cuentasbancarias ON"
    			+ " clientecuenta=cedula where cedula like '"+this.objTransaccion.getClienteTrans()+"';";
    	ResultSet cuentasBancarias = controlGeneral.ejecutarSentencia(buscarCuentasBanc);
    	
    	try {
    		if(cuentasBancarias.next()) {
    			//Se muestra el Vbox de las cuentas disponibles, y se esconde el mensaje de que el cliente no tiene cuentas.
    			vbxActTransCuentas.setVisible(true);
    			lblActTransClienteSinCuentas.setVisible(false);
    			
    			//Se definen las columnas de la tabla.
    			tblColActTransCuentasNumCuenta.setCellValueFactory(new PropertyValueFactory<>("numCuentaBanc"));
    			tblColActTransCuentasBanco.setCellValueFactory(new PropertyValueFactory<>("bancoCuentaBanc"));
    			tblColActTransCuentasTipoCuenta.setCellValueFactory(new PropertyValueFactory<>("tipoCuentaBanc"));
    			
    			//Buscar las cuentas
    			this.listaCuentasAct = buscarCuentasBancarias(this.objTransaccion.getClienteTrans());
    			//Se agregan los resultados a la tabla
    			tblActTransCuentas.setItems(this.listaCuentasAct);
    		}
    		
    	}catch(Exception e) {
    		System.out.println(e.getLocalizedMessage());
    	}
    	
    	//Muestra el pane de Información de Pago.
    	esconderPanesMenosIndicado(actTransInfoPago);    	
    	
    }
      
    public void mostrarGarantiasActTrans() {

    	//Consultar si el cliente asociado tiene garantías.
    	String buscarGarantias = "SELECT  garantias.* FROM garantias JOIN cliente ON clientegarantia=cedula WHERE cedula like '"
    	+ this.objTransaccion.getClienteTrans()+"';";
    	ResultSet garantias = controlGeneral.ejecutarSentencia(buscarGarantias); 
		
		//Si el cliente tiene garantías se definen las columnas en las tablas. Después, se muestran los resultados.
		try {
    		if(garantias.next()) {
    			//Los panes de la información de las garantías se vuelven visibles.
    			vbxActTransTableGarantias.setVisible(true);
    			lsvActTransGarantiasAñadidas.setVisible(true);
    			lblActTransClienteSinGarantias.setVisible(false);
    			hbxActTransGarantiasAñadidas.setVisible(true);
    			
    			//Se definen las columnas de la tabla.
    			tblActTransGarantiasCod.setCellValueFactory(new PropertyValueFactory<>("codGarantia"));
    			tblActTransGarantiasTipo.setCellValueFactory(new PropertyValueFactory<>("tipoGarantia"));
    			tblActTransGarantiasValor.setCellValueFactory(new PropertyValueFactory<>("valorGarantia"));
    			tblActTransGarantiasUbicacion.setCellValueFactory(new PropertyValueFactory<>("ubiGarantia"));    			    		
    			
    			//Buscar garantías retorna un ObservableList<> con las garantías del usuario.
    			listaGarantiasAct = buscarGarantias(this.objTransaccion.getClienteTrans());
    			//Se agrega esa lista de garantías a la tabla.
    			tblActTransGarantias.setItems(listaGarantiasAct);
    		}
		}catch(Exception e) {
			System.out.println(e.getLocalizedMessage());
		}
			
		//Muestra el pane de Garantias
		esconderPanesMenosIndicado(actTransGarantias);
    	   	
    }
       
    public ObservableList<Garantia> buscarGarantiasTrans(int codTrans) throws SQLException{
    	
    	ObservableList<Garantia> listaGarantiasTrans = FXCollections.observableArrayList();
    	codTrans = this.objTransaccion.getCodTrans();
    	String buscarGarantia = "select * from transacciones join prestamos on codtrans=codprestamo "
    			+ "natural join garantias_prestamo natural join garantias where codtrans="+codTrans+";";
    	ResultSet garantiasRS = controlGeneral.ejecutarSentencia(buscarGarantia);
    	
    	while(garantiasRS.next()) {
        	Garantia objGarantiaRS = new Garantia(garantiasRS.getInt("codGarantia"),garantiasRS.getString("clienteGarantia"),garantiasRS.getString("tipoGarantia"),
        			garantiasRS.getString("valorGarantia"),garantiasRS.getString("ubiGarantia"));
        	listaGarantiasTrans.add(objGarantiaRS);
        	}    	
    	return listaGarantiasTrans;    	
    }
    
    public CuentaBancaria buscarCuentaBancariaTrans(int codTrans) throws SQLException {
    	CuentaBancaria cuentaBancariaAsociada = new CuentaBancaria();    	
    	String buscarCuenta = "select * from transacciones join inversiones on codtrans=codinversion"
    			+ " join cuentasbancarias on cuentapagogeneral=numcuentabanc where codtrans="+codTrans+";";
    	ResultSet cuentaRS = controlGeneral.ejecutarSentencia(buscarCuenta);
    	
    	while(cuentaRS.next()) {
    		cuentaBancariaAsociada = new CuentaBancaria(cuentaRS.getString("numcuentabanc"),cuentaRS.getString("clientecuenta"),
    				cuentaRS.getString("bancocuentabanc"), cuentaRS.getString("tipocuentabanc"));
    	}
    	
    	return cuentaBancariaAsociada;
    }
    
    @FXML
    public void refrescarTblActTransGarantias() {
    	mostrarGarantiasActTrans();
    }
    
    @FXML
    public void refrescarTblActTransCuentas() {
    	mostrarCuentasActTrans();
    }
    
    @FXML
    public void actTransAsociarCuentaTextField(ActionEvent event) throws SQLException {
    	if(tblActTransCuentas.getSelectionModel().getSelectedItem()==null) {
    		controlGeneral.mostrarAlerta(AlertType.ERROR, "ERROR: Cuenta no seleccionada", "Cuenta no seleccionada",
    				"Por favor seleccione una cuenta de la tabla y presione Asociar");
    		return;
    	}
    	
    	this.actTransCuentaAsociada = tblActTransCuentas.getSelectionModel().getSelectedItem();
    	txfActTransCuentaAsociada.setText(this.actTransCuentaAsociada.mostrarCuentaBancaria());
    }
    
    @FXML
    public void actTransUpdateCuentaAsociada(ActionEvent event) {
    	
    	String updateCuenta = "update inversiones set cuentapagogeneral = '"+this.actTransCuentaAsociada.getNumCuentaBanc()+"' where codinversion="+this.objTransaccion.getCodTrans()+";";
    	controlGeneral.ejecutarSentenciaInsert(updateCuenta);    	
    	controlGeneral.mostrarAlerta(AlertType.INFORMATION, "Cuenta actualizada", "Cuenta bancaria actualizada exitosamente", null);
    }
    
    
    @FXML
    public void actTransAñadirGarantiaListView(ActionEvent event) throws SQLException {
    	if(tblActTransGarantias.getSelectionModel().getSelectedItem()==null) {
    		controlGeneral.mostrarAlerta(AlertType.ERROR, "ERROR: Garantia no seleccionada", "Garantía no seleccionada",
    				"Por favor seleccione una garantía de la tabla y presione Añadir");
    		return;
    	}
    	
    	Garantia garantiaAñadida = tblActTransGarantias.getSelectionModel().getSelectedItem();    	
    	for(int i=0;i<this.listaGarantiasTrans.size();i++) {    		
    		if(garantiaAñadida.getCodGarantia()==listaGarantiasTrans.get(i).getCodGarantia()) {
    			return;
    		}
    	}
    	this.listaGarantiasTrans.add(garantiaAñadida);
		this.lsvActTransGarantiasAñadidas.setItems(this.listaGarantiasTrans);    	
    }
    
    @FXML
    public void actTransBorrarGarantiaListView(ActionEvent event) {
    	if(this.lsvActTransGarantiasAñadidas.getSelectionModel().getSelectedItem()==null) {
    		controlGeneral.mostrarAlerta(AlertType.ERROR, "ERROR: Garantia no seleccionada", "Garantía no seleccionada",
    				"Por favor seleccione una garantía de la lista para borrarla.");
    		return;
    	}
    	
    	Garantia garantiaABorrar = this.lsvActTransGarantiasAñadidas.getSelectionModel().getSelectedItem();
    	this.listaGarantiasTrans.remove(garantiaABorrar);
    	this.lsvActTransGarantiasAñadidas.setItems(this.listaGarantiasTrans);   	
    }
    
    @FXML
    public void actTransContinuarAFiador(ActionEvent event) {    	
    	esconderPanesMenosIndicado(actTransFiador);
    }
    
    @FXML
    public void actTransUpdateGarantias() {
    	
    	String deleteTodasGarantias = "delete from garantias_prestamo where codprestamo="+this.objTransaccion.getCodTrans()+";";
    	controlGeneral.ejecutarSentenciaInsert(deleteTodasGarantias);
    	
    	this.listaGarantiasTrans.forEach(garantia ->{
    		String updateGarantia = "insert into garantias_prestamo values("+garantia.getCodGarantia()+","+this.objTransaccion.getCodTrans()+");";
    		controlGeneral.ejecutarSentenciaInsert(updateGarantia);
    	});
    	
    	controlGeneral.mostrarAlerta(AlertType.INFORMATION, "Garantias guardadas", "Garantías modificadas exitosamente", null);
    }
    
    @FXML
    public void actTransUpdateFiador() throws SQLException {
    	
    	//Validar si el fiador ingresado en el textfield existe.
    	this.fiadorOK = controlGeneral.validarCliente(txfActTransFiador);
    	String cedulaFiador = txfActTransFiador.getText(); 
		if(fiadorOK==false) {
			controlGeneral.mostrarAlerta(AlertType.ERROR, "Fiador inválido", "Fiador no encontrado", null);
			return;
		}		
		else if(this.objTransaccion.getClienteTrans().equals(cedulaFiador)) {
			controlGeneral.mostrarAlerta(AlertType.ERROR, "Fiador inválido", "El fiador no puede ser el mismo cliente de la transacción", null);
			return;
		}
		else {
			   	
	    	String updateFiador ="update prestamos set fiador= "+cedulaFiador+" where codprestamo = "+this.objTransaccion.getCodTrans()+"; ";
	    	controlGeneral.ejecutarSentenciaInsert(updateFiador);
	    	controlGeneral.mostrarAlerta(AlertType.INFORMATION, "Fiador guardado", "Fiador modificado exitosamente", null);
		}
    }
    
    
    //Metodos generales.
    @FXML
	public void validarInputNumerico(KeyEvent event) {
		try {
			TextField textfield = (TextField) event.getSource();
			textfield.setTextFormatter(new TextFormatter<>(change ->
	        (change.getControlNewText().matches("[0-9]{0,13}(\\.[0-9]*)?")) ? change : null));
			}catch(Exception e) {
				e.getStackTrace();
		}
	}
	
	@FXML
	public void validarInputEntero(KeyEvent event) {
		try {
			TextField textfield = (TextField) event.getSource();
			textfield.setTextFormatter(new TextFormatter<>(change ->
	        (change.getControlNewText().matches("^[0-9]{0,3}$")) ? change : null));
			}catch(Exception e) {
				e.getStackTrace();
		}
	}
	
	@FXML
  	public void validarInputEnteroSinLimite(KeyEvent event) {
  		try {
  			TextField textfield = (TextField) event.getSource();
  			textfield.setTextFormatter(new TextFormatter<>(change ->
  	        (change.getControlNewText().matches("^[0-9]{0,20}$")) ? change : null));
  			}catch(Exception e) {
  				e.getStackTrace();
  		}
  	}	
	
	public void esconderPanesMenosIndicado(Node nodo) {
		
		ObservableList<Node> hijos = stpCenter.getChildren();
		for(Node hijo : hijos) {
			hijo.setVisible(false);
		}
		nodo.setVisible(true);
	}
	
	@FXML
	public void botonAtras(ActionEvent event) {
		Node nodo = (Node) event.getSource();
		Parent actual = nodo.getParent();
		
		if(actual.equals(regTransInfoPago)) {
			esconderPanesMenosIndicado(regTransSimulacion);
		}
		else if(actual.equals(actTransInfoPago)){
			esconderPanesMenosIndicado(actTransBusqueda);
		}else {
			ObservableList<Node> hijos = stpCenter.getChildren();
			for(int i=0; i<hijos.size();i++) {
				if(hijos.get(i).equals(actual))
					esconderPanesMenosIndicado(hijos.get(i-1));
			}
		}
	}
	
	
	@FXML
	private void abrirClientesOtraVentana(ActionEvent event) throws IOException {
		
		Parent interfazClientes = FXMLLoader.load(getClass().getResource("/vista/clientes.fxml"));
		Scene escenaClientes = new Scene(interfazClientes);
		Stage ventana = new Stage();
		ventana.setScene(escenaClientes);
		ventana.show();
	}
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//Panel de Bienvenida
		esconderPanesMenosIndicado(regTransBienvenido);	
		
		//Se inicia las opciones del ComboBox TipoTransaccion y se selecciona la primera.
		regTransCbxTipoTrans.getItems().addAll("Préstamo","Inversión");
		regTransCbxTipoTrans.getSelectionModel().selectFirst();
		
		//Se esconden los VBox que contienen las info de garantias. La tabla y las que serán añadidas.
		vbxRegTransTablaGarantias.setVisible(false);
		taGarantiasAñadidas.setVisible(false);
		hbxGarantiasAñadidas.setVisible(false);
		
		//En la casilla fechaIniciacion se pone por defecto la hora del sistema.
		dtpFechaIniciacion.setValue(LocalDate.now());
		
		//Se esconde el VBox que contiene la info de las cuentas bancarias del cliente.
		vbxRegTransInfoPago.setVisible(false);
		lblRegTransClienteSinCuentas.setVisible(true);
		
		vbxActTransCuentas.setVisible(false);
		lblActTransClienteSinCuentas.setVisible(true);		
	
	}

 }

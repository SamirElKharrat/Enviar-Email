package dad.enviar.email.mvc;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.util.converter.NumberStringConverter;

public class EnviarController implements Initializable {
	
	private EnviarModel model = new EnviarModel();
	
	public EnviarController() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/View.fxml"));
		loader.setController(this);
		loader.load();
	}

	@FXML
	private BorderPane view;

	@FXML
	private TextField txtSMTP;

	@FXML
	private TextField txtRemitente;

	@FXML
	private TextField txtDestinatario;

	@FXML
	private TextField txtAsunto;

	@FXML
	private TextField txtPuerto;

	@FXML
	private TextArea txtMensaje;

	@FXML
	private PasswordField txtPass;

	@FXML
	private CheckBox checkSSL;

	@FXML
	private Button btEnviar;

	@FXML
	private Button btVaciar;

	@FXML
	private Button btCerrar;




	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Bindings.bindBidirectional(model.smtpProperty(), txtSMTP.textProperty());
		Bindings.bindBidirectional(txtPuerto.textProperty(), model.puertoProperty(), new NumberStringConverter());
		Bindings.bindBidirectional(model.sslProperty(), checkSSL.selectedProperty());
		Bindings.bindBidirectional(model.remitenteProperty(), txtRemitente.textProperty());
		Bindings.bindBidirectional(model.contraseñaProperty(), txtPass.textProperty());
		Bindings.bindBidirectional(model.destinatarioProperty(), txtDestinatario.textProperty());
		Bindings.bindBidirectional(model.asuntoProperty(), txtAsunto.textProperty());
		Bindings.bindBidirectional(model.mensajeProperty(), txtMensaje.textProperty());

	}

	@FXML
	public void OnActionEnviar(ActionEvent e) {
		Email email = new SimpleEmail();

		try {

			email.setHostName(model.getSmtp());
			email.setSmtpPort(model.getPuerto());
			email.setAuthenticator(new DefaultAuthenticator(model.getRemitente(), model.getContraseña()));
			email.setSSLOnConnect(model.isSsl());
			email.setFrom(model.getRemitente());
			email.setSubject(model.getAsunto());
			email.setMsg(model.getMensaje());
			email.addTo(model.getDestinatario());
			email.send();
			Alert alert = new Alert(AlertType.INFORMATION);
			
		    alert.setTitle("Enviar Email");
			alert.setHeaderText("Enviado");
			alert.setContentText("Email enviado al destinatario");

			alert.showAndWait();

		} catch (EmailException e1) {
			Alert alert = new Alert(AlertType.ERROR);
			
		    alert.setTitle("Enviar Email");
			alert.setHeaderText("Error");
			alert.setContentText("Email no mandado correctamente");

			alert.showAndWait();
			e1.printStackTrace();
		}

	}
	
	@FXML
	public void OnActionVaciar(ActionEvent v) {
		model.setSmtp("");
		model.setPuerto(0);
		model.isSsl();
		model.setRemitente("");
		model.setContraseña("");
		model.setDestinatario("");
		model.setAsunto("");
		model.setMensaje("");
		
	}
	
	@FXML
	public void OnActionCerrar(ActionEvent c) {
		Platform.exit();
	}
	
	
	public BorderPane getView() {
		return view;
	}

	public void setView(BorderPane view) {
		this.view = view;
	}


}

package shopmanagementsystem;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jfoenix.controls.IFXTextInputControl;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.base.ValidatorBase;

import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;

public class Alerts {

	public static void setSuccessAlert(String title, String text) {
		Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
        successAlert.setTitle(title);
        successAlert.setContentText(text);
        successAlert.showAndWait();
	}

	public static void setErrorAlert() {
		Alert errorInformation = new Alert(Alert.AlertType.ERROR);
        errorInformation.setTitle("opération échouée");
        errorInformation.setContentText("Nous avons rencontré des erreurs lors de cette opération");
        errorInformation.showAndWait();
       // Logger.getLogger(Alerts.class.getName()).log(Level.SEVERE, null, e);
	}
	
	public static Boolean setAlertConfirmation(String title, String header, String text) {

		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(text);
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			return true;
		}
		return false;
		
	}
	
	public static void setValidators(List<String> errorMessages) {
		// show error message
        StringBuilder errorMessagBuilder = new StringBuilder();
        //remove dups
        HashSet<String> hashSet = new HashSet<String>();
        hashSet.addAll(errorMessages);
        errorMessages.clear();
        errorMessages.addAll(hashSet);
        Alert validationErrorMessage = new Alert(Alert.AlertType.ERROR);
        for (String errorMessage : errorMessages) {
            errorMessagBuilder.append(errorMessage);
            errorMessagBuilder.append("\r\n");
        }
        String errorMessStr = errorMessagBuilder.toString();
        validationErrorMessage.setTitle("S'il vous plait fixez les problèmes suivants");
        validationErrorMessage.setContentText(errorMessStr);
        validationErrorMessage.showAndWait();
	}
	
	public static void addValidators(List<String> errorMessages, List<IFXTextInputControl> formFields) {
		
		errorMessages.clear();
		
		for (IFXTextInputControl formField : formFields) {
            formField.validate();
            if (formField instanceof JFXTextField) {
                JFXTextField formField2 = (JFXTextField) formField;
                ObservableList<ValidatorBase> validators = formField2.getValidators();
                for (ValidatorBase validator : validators) {
                    if (validator.getHasErrors()) {
                    	errorMessages.add(validator.getMessage());
                    }
                }
            }
        }
	}
}

package shopmanagementsystem.customerManagement;

import java.util.ArrayList;
import java.util.List;

import com.jfoenix.controls.IFXTextInputControl;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import shopmanagementsystem.Alerts;
import shopmanagementsystem.Program;
import shopmanagementsystem.productManagement.Product;

public class CustomerView {

	private BorderPane pane = new BorderPane();
	private HBox hboxTitle = new HBox();
	private Label labelTitle = new Label("Gestion des clients");
	private GridPane grid = new GridPane();
	private Label labelNom = new Label("Nom");
	private Label labelPrenom = new Label("Prenom");
	private Label labelEmail = new Label("Email");
	private Label labelAdresse = new Label("Adresse");
	private JFXTextField textNom = new JFXTextField();
	private JFXTextField textPrenom = new JFXTextField();
	private JFXTextField textEmail = new JFXTextField();
	private JFXTextField textAdresse = new JFXTextField();
	private VBox vboxTable = new VBox();
	private HBox HboxSearch = new HBox();
	private Label labelSearch = new Label("Chercher par");
	private ComboBox<String> comboBoxSearch = new ComboBox<String>();
	private JFXTextField textSearch = new JFXTextField();
	private Button backBtn = new Button("Précédent");
	private Button addBtn = new Button("Enregistrer");
	private Button clearBtn = new Button("Rénitialiser");
	private Button updateBtn = new Button("Modifier");
	private Button deleteBtn = new Button("Supprimer");
	private TableView<Customer> tableCustomer = new TableView<Customer>();
	private HBox hboxBtn = new HBox();
	
	private CustomerDaoImpl CDI = new CustomerDaoImpl();
	
	private Customer currentCustomer = null;
	
	List<Customer> customers = new ArrayList<Customer>();
	
	ObservableList<Customer> observableListCustomers = FXCollections.observableArrayList();
	
	private RequiredFieldValidator nomValidator;
	private RequiredFieldValidator prenomValidator;
	private RequiredFieldValidator emailValidator;
	private RequiredFieldValidator adresseValidator;
	
	private List<IFXTextInputControl> formFields = new ArrayList<IFXTextInputControl>();
	private List<String> errorMessages = new ArrayList<String>();
	
	public CustomerView() {
		pane.setPrefWidth(1000);
		pane.setPrefHeight(700);
		pane.setTop(hboxTitle);
		pane.setCenter(grid);
		pane.setRight(vboxTable);
		addContent();
		setEvents();
		setCSS();
		setMarginElements();
		resizeElements();
		initValidator();
		registerValidator();
		registerFormFields();
	}
	
	public BorderPane getPane() {
		return pane;
	}

	public void setPane(BorderPane pane) {
		this.pane = pane;
	}

	private void addContent() {
		hboxTitle.getChildren().add(backBtn);
		hboxTitle.getChildren().add(labelTitle);
		grid.add(labelNom, 0, 0);
		grid.add(labelPrenom, 0, 1);
		grid.add(labelEmail, 0, 2);
		grid.add(labelAdresse, 0, 3);
		grid.add(textNom, 1, 0);
		grid.add(textPrenom, 1, 1);
		grid.add(textEmail, 1, 2);
		grid.add(textAdresse, 1, 3);
		grid.add(addBtn, 0, 4);
		grid.add(clearBtn, 1, 4);
		HboxSearch.getChildren().add(labelSearch);
		HboxSearch.getChildren().add(comboBoxSearch);
		vboxTable.getChildren().add(HboxSearch);
		textSearch.setPromptText("Recherche");
		vboxTable.getChildren().add(textSearch);
		initTable();
		vboxTable.getChildren().add(tableCustomer);
		hboxBtn.getChildren().add(updateBtn);
		hboxBtn.getChildren().add(deleteBtn);
		vboxTable.getChildren().add(hboxBtn);
		initComboBoxSearch();
	}
	
	private void initTable() {
		TableColumn<Customer, String> idColumn = new TableColumn<Customer, String>("Id");
		idColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("idCustomer"));
		tableCustomer.getColumns().add(idColumn);
		idColumn.setPrefWidth(100);
		
		TableColumn<Customer, String> nomColumn = new TableColumn<Customer, String>("Nom");
		nomColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("nom"));
		tableCustomer.getColumns().add(nomColumn);
		nomColumn.setPrefWidth(100);
		
		TableColumn<Customer, String> prenomColumn = new TableColumn<Customer, String>("Prenom");
		prenomColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("prenom"));
		tableCustomer.getColumns().add(prenomColumn);
		prenomColumn.setPrefWidth(100);
		
		TableColumn<Customer, String> emailColumn = new TableColumn<Customer, String>("Email");
		emailColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("email"));
		tableCustomer.getColumns().add(emailColumn);
		emailColumn.setPrefWidth(150);
		
		TableColumn<Customer, String> adresseColumn = new TableColumn<Customer, String>("Adresse");
		adresseColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("adresse"));
		tableCustomer.getColumns().add(adresseColumn);
		adresseColumn.setPrefWidth(150);
		
		tableCustomer.setPrefWidth(600);
		
		getAllCustomers();
		observableListCustomers.addAll(customers);
		tableCustomer.setItems(observableListCustomers);
	}
	
	private void getAllCustomers() {
		customers = (List<Customer>) CDI.getAll();
	}
	
	private void clearAll() {
		textNom.setText("");
		textPrenom.setText("");
		textEmail.setText("");
		textAdresse.setText("");
	}
	
	private void refrechTable() {
		tableCustomer.getItems().clear();
		customers.removeAll(customers);
		getAllCustomers();
		observableListCustomers.addAll(customers);
		tableCustomer.setItems(observableListCustomers);
	}
	
	private void setEvents() {
		backBtn.setOnAction((event) -> {
			Program.back();
		});
		
		addBtn.setOnAction((event) -> {
			Alerts.addValidators(errorMessages, formFields);
			if(errorMessages.size() == 0) {
				if(currentCustomer == null) {
					Boolean b = CDI.add(new Customer(textNom.getText(), textPrenom.getText(), textEmail.getText(), textAdresse.getText()));
					if(b) {
						Alerts.setSuccessAlert("Enregistrement réussie", "Le client est ajouté avec succés");
						refrechTable();
						clearAll();
					}
					else {
						Alerts.setErrorAlert();
					}
				}
				else {
					Boolean confiramtion = Alerts.setAlertConfirmation("Confirmation de la modification", Long.toString(currentCustomer.getIdCustomer()) + " " + currentCustomer.getNom() + " " + currentCustomer.getPrenom(), "Etes vous sur de vouloir modifier le client ?");
					if(confiramtion) {
						Boolean b = CDI.update(new Customer(textNom.getText(), textPrenom.getText(), textEmail.getText(), textAdresse.getText()), currentCustomer.getIdCustomer());
						if(b) {
							Alerts.setSuccessAlert("Modification réussie", "Le client est modifié avec succés");
							refrechTable();
							clearAll();
						}
						else {
							Alerts.setErrorAlert();
						}
					}
					else {
						Alerts.setSuccessAlert("Opération annulée", "La modification est annulée");
					}
				}
			}
			
			else {
				Alerts.setValidators(errorMessages);
			}
			
		});
		
		clearBtn.setOnAction((event) -> {
			clearAll();
		});
		
		updateBtn.setOnAction((event) -> {
			currentCustomer = tableCustomer.getSelectionModel().getSelectedItem();
			if(currentCustomer!=null) {
				textNom.setText(currentCustomer.getNom());
				textPrenom.setText(currentCustomer.getPrenom());
				textEmail.setText(currentCustomer.getEmail());
				textAdresse.setText(currentCustomer.getAdresse());
			}
			
			else {
				Alerts.setSuccessAlert("Remarque", "S'il vous plait selectionner un client");
			}
		});
		
		deleteBtn.setOnAction((event) -> {
			currentCustomer = tableCustomer.getSelectionModel().getSelectedItem();
			if(currentCustomer!=null) {
				Boolean confirmation = Alerts.setAlertConfirmation("Confirmation de la suppression", Long.toString(currentCustomer.getIdCustomer()) + " " + currentCustomer.getNom() + " " + currentCustomer.getPrenom(), "Etes vous sur de vouloir supprimer le client ?");
				if(confirmation) {
					Boolean b = CDI.delete(currentCustomer.getIdCustomer());
					if(b) {
						Alerts.setSuccessAlert("Suppression réussie", "Le client est supprimé avec succés");
						refrechTable();
						clearAll();
					}
					else {
						Alerts.setErrorAlert();
					}
				}
				else {
					Alerts.setSuccessAlert("Opération annulée", "La suppression est annulée");
				}
			}
			
			else {
				Alerts.setSuccessAlert("Remarque", "S'il vous plait selectionner un client");
			}
		});
		
		textSearch.setOnKeyReleased((event) -> {
			if(textSearch.getText().isEmpty()) {
				refrechTable();
			}
			else if(comboBoxSearch.getSelectionModel().getSelectedItem() == "Identifiant") {
				Long id;
				Boolean b = true;
				try {
					id = Long.parseLong(textSearch.getText());
				}
				catch(NumberFormatException e) {
					b = false;
				}
				if(b) {
					id = Long.parseLong(textSearch.getText());
					Customer c = CDI.getOne(id);
					tableCustomer.getItems().clear();
					customers.removeAll(customers);
					customers.add(c);
					observableListCustomers.addAll(customers);
					tableCustomer.setItems(observableListCustomers);
				}
				
			}
			
			else if(comboBoxSearch.getSelectionModel().getSelectedItem() == "Nom") {
				customers.removeAll(customers);
				tableCustomer.getItems().clear();
				customers = (List<Customer>) CDI.getAllByNom(textSearch.getText());
				observableListCustomers.addAll(customers);
				tableCustomer.setItems(observableListCustomers);
			}
			
			else if(comboBoxSearch.getSelectionModel().getSelectedItem() == "Prenom") {
				customers.removeAll(customers);
				tableCustomer.getItems().clear();
				customers = (List<Customer>) CDI.getAllByPrenom(textSearch.getText());
				observableListCustomers.addAll(customers);
				tableCustomer.setItems(observableListCustomers);
			}
			
			else if(comboBoxSearch.getSelectionModel().getSelectedItem() == "Email") {
				customers.removeAll(customers);
				tableCustomer.getItems().clear();
				customers = (List<Customer>) CDI.getAllByEmail(textSearch.getText());
				observableListCustomers.addAll(customers);
				tableCustomer.setItems(observableListCustomers);
			}
			
			else if(comboBoxSearch.getSelectionModel().getSelectedItem() == "Adresse") {
				customers.removeAll(customers);
				tableCustomer.getItems().clear();
				customers = (List<Customer>) CDI.getAllByAdresse(textSearch.getText());
				observableListCustomers.addAll(customers);
				tableCustomer.setItems(observableListCustomers);
			}
		});
	}
	
	private void setCSS() {
		hboxTitle.getStyleClass().add("custom-pane-top");
		labelTitle.getStyleClass().add("custom-label");
		labelNom.getStyleClass().add("custom-label");
		labelPrenom.getStyleClass().add("custom-label");
		labelEmail.getStyleClass().add("custom-label");
		labelAdresse.getStyleClass().add("custom-label");
		labelSearch.getStyleClass().add("custom-label");
		grid.getStyleClass().add("custom-pane-center");
		vboxTable.getStyleClass().add("custom-pane-right");
		addBtn.getStyleClass().add("custom-button");
		backBtn.getStyleClass().add("custom-button");
		clearBtn.getStyleClass().add("custom-button");
		updateBtn.getStyleClass().add("custom-button");
		deleteBtn.getStyleClass().add("custom-button");
	}
	
	private void setMarginElements() {
		hboxTitle.setMargin(labelTitle, new Insets(20, 0, 20, 250));
		hboxTitle.setMargin(backBtn, new Insets(20, 0, 20, 30));
		vboxTable.setMargin(tableCustomer, new Insets(30, 30, 0, 0));
		vboxTable.setMargin(textSearch, new Insets(0, 100, 0, 0));
		HboxSearch.setMargin(comboBoxSearch, new Insets(15, 0, 0, 50));
		hboxBtn.setMargin(updateBtn, new Insets(10, 50, 0, 0));
		hboxBtn.setMargin(deleteBtn, new Insets(10, 50, 0, 0));
		grid.setMargin(labelNom, new Insets(100, 0, 0, 20));
		grid.setMargin(textNom, new Insets(100, 0, 0, 0));
		grid.setMargin(labelPrenom, new Insets(20, 0, 0, 20));
		grid.setMargin(labelEmail, new Insets(20, 0, 0, 20));
		grid.setMargin(labelAdresse, new Insets(20, 0, 0, 20));
		grid.setMargin(addBtn, new Insets(20, 0, 0, 30));
		grid.setMargin(clearBtn, new Insets(20, 0, 0, 30));
	}
	
	private void resizeElements() {
		textNom.setPrefWidth(200);
		comboBoxSearch.setPrefWidth(200);
		textSearch.setPrefWidth(300);
	}
	
	private void initComboBoxSearch() {
		comboBoxSearch.getItems().addAll("Identifiant", "Nom", "Prenom", "Email", "Adresse");
		comboBoxSearch.getSelectionModel().selectFirst();
	}
	
	private void initValidator() {
		nomValidator = new RequiredFieldValidator();
		nomValidator.setMessage("Le nom est obligatoire");
		prenomValidator = new RequiredFieldValidator();
		prenomValidator.setMessage("Le prenom est obligatoire");
		emailValidator = new RequiredFieldValidator();
		emailValidator.setMessage("L'email est obligatoire");
		adresseValidator = new RequiredFieldValidator();
		adresseValidator.setMessage("L'adresse est obligatoire");
	}
	
	private void registerValidator() {
		textNom.getValidators().add(nomValidator);
		textPrenom.getValidators().add(prenomValidator);
		textEmail.getValidators().add(emailValidator);
		textAdresse.getValidators().add(adresseValidator);
	}
	
	private void registerFormFields() {
		formFields.add(textNom);
		formFields.add(textPrenom);
		formFields.add(textEmail);
		formFields.add(textAdresse);
	}
}

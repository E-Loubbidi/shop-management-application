package shopmanagementsystem.categoryManagement;

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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import shopmanagementsystem.Alerts;
import shopmanagementsystem.Program;
import shopmanagementsystem.productManagement.ProductDaoImpl;
import shopmanagementsystem.productManagement.ProductView;

public class CategoryView {

	private BorderPane pane = new BorderPane();
	private HBox hboxTitle = new HBox();
	private Label labelTitle = new Label("Gestion des categories");
	private Label labelIntitule = new Label("Intitulé");
	private JFXTextField textIntitule = new JFXTextField();
	private GridPane grid = new GridPane();
	private Button addBtn = new Button("Enregistrer");
	private Button updateBtn = new Button("Modifier");
	private Button deleteBtn = new Button("Supprimer");
	private Button clearBtn = new Button("Rénitialiser");
	private TableView<Category> tableCategory = new TableView<Category>();
	private VBox vboxTable = new VBox();
	private HBox hboxBtn = new HBox();
	private Button backBtn = new Button("Précédent");
	private Label labelSearch = new Label("Chercher par");
	private ComboBox<String> comboBoxSearch = new ComboBox<String>();
	private HBox hboxSearch = new HBox();
	private JFXTextField textSearch = new JFXTextField();
	
	private RequiredFieldValidator intituleValidator;
	
	private List<Category> categories = new ArrayList<Category>();
	private ObservableList<Category> observaleListCategory = FXCollections.observableArrayList();
	
	private CategoryDaoImpl CDI = new CategoryDaoImpl();
	
	private Category currentCategory = null;
	
	private List<IFXTextInputControl> formFields = new ArrayList<>();
	private List<String> errorMessages = new ArrayList<>();
	
	private void addContent() {
		
		hboxTitle.getChildren().add(backBtn);
		hboxTitle.getChildren().add(labelTitle);
		grid.add(labelIntitule, 0, 0);
		grid.add(textIntitule, 1, 0);
		grid.add(addBtn, 0, 1);
		grid.add(clearBtn, 1, 1);
		hboxSearch.getChildren().add(labelSearch);
		hboxSearch.getChildren().add(comboBoxSearch);
		vboxTable.getChildren().add(hboxSearch);
		textSearch.setPromptText("Recherche");
		vboxTable.getChildren().add(textSearch);
		getAllCategories();
		initTable();
		vboxTable.getChildren().add(tableCategory);
		hboxBtn.getChildren().add(updateBtn);
		hboxBtn.getChildren().add(deleteBtn);
		vboxTable.getChildren().add(hboxBtn);
		initComboBoxSearch();
	}
	
	private void setEvent() {
		
		addBtn.setOnAction((event) -> {
			Alerts.addValidators(errorMessages, formFields);
			if(errorMessages.size()==0) {
				if(currentCategory != null) {
					Boolean confirmation = Alerts.setAlertConfirmation("Confirmation de la modification", Long.toString(currentCategory.getIdCategory()) + " " + currentCategory.getIntitule(), "Etes vous sur de vouloir modifier la catégorie ?");
					if(confirmation == true) {
						Boolean b = CDI.update(new Category(textIntitule.getText()), currentCategory.getIdCategory());
						if(b == true) {
							Alerts.setSuccessAlert("Modification réussie", "la catégorie est modifiée avec succés");
						}
						else{
							Alerts.setErrorAlert();
						}
					}
					else {
						Alerts.setSuccessAlert("Opération annulée", "La modification est annulée");
					}
					
				}
				else {
					Boolean b = CDI.add(new Category(textIntitule.getText()));
					if(b == true) {
						Alerts.setSuccessAlert("Catégorie ajouté", "La catégorie est ajoutée ave succés");
					}
					else {
						Alerts.setErrorAlert();
					}
				}
				
				refrechTable();
				clearAll();
			}
			else {
				Alerts.setValidators(errorMessages);
			}
		}); 
		
		updateBtn.setOnAction((event) -> {
			currentCategory = tableCategory.getSelectionModel().getSelectedItem();
			if(currentCategory!=null) {
				textIntitule.setText(currentCategory.getIntitule());
			}
			else {
				Alerts.setSuccessAlert("Remarque", "S'il vous plait selectionner un produit");
			}
		});
		
		deleteBtn.setOnAction((event) -> {
			currentCategory = tableCategory.getSelectionModel().getSelectedItem();
			if(currentCategory!=null) {
				Boolean confirmation = Alerts.setAlertConfirmation("Confiramtion de la suppression", currentCategory.getIdCategory() + " " + currentCategory.getIntitule(), "Etes vous sur de vouloir supprimer la catégorie ?");
				if(confirmation==true) {
					Boolean b = CDI.delete(currentCategory.getIdCategory());
					if(b==true) {
						refrechTable();
						Alerts.setSuccessAlert("Suppression réussie", "La catégorie est supprimé");
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
				Alerts.setSuccessAlert("Remarque", "S'il vous plait selectionner une catégorie");
			}
			
		});
		
		backBtn.setOnAction((event) -> {
			Program.back();
		});
		
		clearBtn.setOnAction((event) -> {
			clearAll();
		});
		
		textSearch.setOnKeyReleased((event) -> {
			if(textSearch.getText().isEmpty()) {
				refrechTable();
			}
			else {
				String key = comboBoxSearch.getValue();
				if(key == "Identifiant") {
					Boolean b = true;
					Long id;
					try {
						id = Long.parseLong(textSearch.getText());
						}
					catch(NumberFormatException e) {
						b = false;
					}
					if(b) {
						id = Long.parseLong(textSearch.getText());
						categories.removeAll(categories);
						Category c = CDI.getOne(id);
						categories.add(c);
						tableCategory.getItems().removeAll(observaleListCategory);
						observaleListCategory.addAll(categories);
						tableCategory.setItems(observaleListCategory);
					}
				}
				else if(key == "Intitulé") {
					categories.removeAll(categories);
					categories = (List<Category>) CDI.getAllByIntitule(textSearch.getText());
					tableCategory.getItems().removeAll(observaleListCategory);
					observaleListCategory.addAll(categories);
					tableCategory.setItems(observaleListCategory);
				}
			}
		});
	}
	
	public CategoryView() {
		addContent();
		pane.setTop(hboxTitle);
		pane.setCenter(grid);
		pane.setRight(vboxTable);
		pane.setPrefWidth(1000);
		pane.setPrefHeight(700);
		setEvent();
		initValidator();
		registerValidator();
		registerFormFields();
		setCSS();
		setMargingElements();
		resizeElements();
	}
	
	public BorderPane getPane() {
		return pane;
	}

	public void setPane(BorderPane pane) {
		this.pane = pane;
	}
	
	private void initComboBoxSearch() {
		comboBoxSearch.getItems().addAll("Identifiant", "Intitulé");
		comboBoxSearch.getSelectionModel().selectFirst();
	}
	
	private void initTable() {
		TableColumn<Category, String> idColumn = new TableColumn<Category, String>("Id");
		idColumn.setCellValueFactory(new PropertyValueFactory<>("idCategory"));
		tableCategory.getColumns().add(idColumn);
		idColumn.setPrefWidth(250);
		
		TableColumn<Category, String> intituleColumn = new TableColumn<Category, String>("Intitulé");
		intituleColumn.setCellValueFactory(new PropertyValueFactory<>("intitule"));
		tableCategory.getColumns().add(intituleColumn);
		intituleColumn.setPrefWidth(250);
		
		tableCategory.setPrefWidth(500);
		
		observaleListCategory.addAll(categories);
		tableCategory.setItems(observaleListCategory);
	}
	
	private void getAllCategories(){
		categories = (List<Category>) CDI.getAll();
	}
	
	private void clearAll() {
		textIntitule.setText("");
	}
	
	private void refrechTable() {
		tableCategory.getItems().removeAll(observaleListCategory);
		getAllCategories();
		observaleListCategory.addAll(categories);
		tableCategory.setItems(observaleListCategory);
	}
	
	private void initValidator() {
		intituleValidator = new RequiredFieldValidator();
		intituleValidator.setMessage("l'intitulé est obligatoire");
	}
	
	private void registerValidator() {
		textIntitule.getValidators().add(intituleValidator);
	}
	
	private void registerFormFields() {
		formFields.add(textIntitule);
	}
	
	private void setCSS() {
		hboxTitle.getStyleClass().add("custom-pane-top");
		labelTitle.getStyleClass().add("custom-label");
		labelIntitule.getStyleClass().add("custom-label");
		labelSearch.getStyleClass().add("custom-label");
		addBtn.getStyleClass().add("custom-button");
		clearBtn.getStyleClass().add("custom-button");
		updateBtn.getStyleClass().add("custom-button");
		deleteBtn.getStyleClass().add("custom-button");
		backBtn.getStyleClass().add("custom-button");
		grid.getStyleClass().add("custom-pane-center");
		vboxTable.getStyleClass().add("custom-pane-center");
	}
	
	private void setMargingElements() {
		hboxTitle.setMargin(labelTitle, new Insets(30, 0, 30, 250));
		vboxTable.setMargin(tableCategory, new Insets(20, 50, 0, 0));
		vboxTable.setMargin(textSearch, new Insets(0, 50, 0, 0));
		hboxSearch.setMargin(labelSearch, new Insets(10, 10, 0, 0));
		hboxSearch.setMargin(comboBoxSearch, new Insets(20, 10, 0, 20));
		grid.setMargin(labelIntitule, new Insets(150, 0, 0, 30));
		grid.setMargin(textIntitule, new Insets(150, 0, 0, 0));
		grid.setMargin(addBtn, new Insets(30, 10, 0, 40));
		grid.setMargin(clearBtn, new Insets(30, 0, 0, 10));
		hboxBtn.setMargin(updateBtn, new Insets(5, 15, 0, 10));
		hboxBtn.setMargin(deleteBtn, new Insets(5, 10, 0, 15));
		hboxTitle.setMargin(backBtn, new Insets(25, 0, 0, 40));
	}
	
	private void resizeElements() {
		comboBoxSearch.setPrefWidth(200);
		textIntitule.setPrefWidth(200);
	}
}

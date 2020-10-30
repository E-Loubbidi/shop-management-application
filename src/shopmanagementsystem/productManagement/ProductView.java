package shopmanagementsystem.productManagement;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import com.jfoenix.controls.IFXTextInputControl;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import com.jfoenix.validation.base.ValidatorBase;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
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
import shopmanagementsystem.categoryManagement.CategoryDaoImpl;
import shopmanagementsystem.categoryManagement.Category;

public class ProductView{

	private BorderPane pane = new BorderPane();
	
	private HBox hboxTitle = new HBox();
	private Label labelTitle = new Label("Gestion des produits");
	private Label labelDesignation  =new Label("Designation");
	private JFXTextField textDesignation = new JFXTextField();
	private Label labelPrixAchat = new Label("Prix d'achat");
	private JFXTextField textPrixAchat = new JFXTextField();
	private Label labelPrixVente = new Label("Prix de vente");
	private JFXTextField textPrixVente = new JFXTextField();
	private Label labelCategorie = new Label("Categorie");
	private ComboBox<String> comboBoxCategorie = new ComboBox<String>();
	private GridPane grid = new GridPane(); 
	private Button addBtn = new Button("Enregistrer");
	private Button updateBtn = new Button("Modifier");
	private Button deleteBtn = new Button("Supprimer");
	private Button clearBtn = new Button("Rénitialiser");
	private VBox vboxTable = new VBox();
	private TableView<Product> table = new TableView<Product>();
	private ObservableList<Product> observaleListProduct = FXCollections.observableArrayList();
	private List<Product> products = new ArrayList<>();
	private Label labelSearch = new Label("Chercher par");
	private ComboBox<String> comboBoxSearch = new ComboBox<String>();
	private HBox hboxSearch = new HBox();
	private JFXTextField textSearch = new JFXTextField();
	private HBox hboxBtn = new HBox();
	private Button backBtn = new Button("Précédent");
	
	private RequiredFieldValidator designationValidator;
	private RequiredFieldValidator prixAchatValidator;
	private RequiredFieldValidator prixVenteValidator;
	
	private List<IFXTextInputControl> formFields = new ArrayList<>();
	private List<String> errorMessages = new ArrayList<>();
	
	private ProductDaoImpl PDO = new ProductDaoImpl();
	
	private Product currentProduct = null;
	
	private CategoryDaoImpl CDI = new CategoryDaoImpl();
	
	private void addContent() {
		
		hboxTitle.getChildren().add(backBtn);
		hboxTitle.getChildren().add(labelTitle);
		grid.add(labelDesignation, 0, 0);
		grid.add(labelPrixAchat, 0, 1);
		grid.add(labelPrixVente, 0, 2);
		grid.add(labelCategorie, 0, 3);
		grid.add(textDesignation, 1, 0);
		grid.add(textPrixAchat, 1, 1);
		grid.add(textPrixVente, 1, 2);
		grid.add(comboBoxCategorie, 1, 3);
		grid.add(addBtn, 0, 4);
		grid.add(clearBtn, 1, 4);
		hboxSearch.getChildren().add(labelSearch);
		hboxSearch.getChildren().add(comboBoxSearch);
		vboxTable.getChildren().add(hboxSearch);
		vboxTable.getChildren().add(textSearch);
		textSearch.setPromptText("Recherche");
		getAllProducts();
		initTable();
		vboxTable.getChildren().add(table);
		hboxBtn.getChildren().add(updateBtn);
		hboxBtn.getChildren().add(deleteBtn);
		vboxTable.getChildren().add(hboxBtn);
		initComboBoxCategory();
		initComboBoxSearch();
	}
	
	private void initTable() {
		
		TableColumn<Product, String> columnId = new TableColumn<>("Id");
		columnId.setCellValueFactory(new PropertyValueFactory<>("idProduct"));
		table.getColumns().add(columnId);
		columnId.setPrefWidth(100);
		
		TableColumn<Product, String> columnDesignation = new TableColumn<>("Designation");
		columnDesignation.setCellValueFactory(new PropertyValueFactory<>("designation"));
		table.getColumns().add(columnDesignation);
		columnDesignation.setPrefWidth(100);
		
		TableColumn<Product, String> columnPrixAchat = new TableColumn<>("Prix d'achat");
		columnPrixAchat.setCellValueFactory(new PropertyValueFactory<>("prixAchat"));
		table.getColumns().add(columnPrixAchat);
		columnPrixAchat.setPrefWidth(100);
		
		TableColumn<Product, String> columnPrixVente = new TableColumn<>("Prix de vente");
		columnPrixVente.setCellValueFactory(new PropertyValueFactory<>("PrixVente"));
		table.getColumns().add(columnPrixVente);
		columnPrixVente.setPrefWidth(100);
		
		TableColumn<Product, String> columnCategory = new TableColumn<>("Categorie");
		columnCategory.setCellValueFactory(new PropertyValueFactory<>("categoryIntitule"));
		table.getColumns().add(columnCategory);
		 columnCategory.setPrefWidth(100);
		
		observaleListProduct.addAll(products);
		table.setItems(observaleListProduct);
		table.setPrefWidth(500);
	}
	
	private void refreshTable() {
		table.getItems().removeAll(observaleListProduct);
		getAllProducts();
		observaleListProduct.addAll(products);
		table.setItems(observaleListProduct);
	}
	
	public ProductView() {
		pane.setPrefWidth(1000);
		pane.setPrefHeight(700);
		addContent();
		pane.setTop(hboxTitle);
		pane.setCenter(grid);
		pane.setRight(vboxTable);
		setEvents();
		initValidators();
		registerValidator();
		registerFormFields();
		setCSS();
		setMarginElements();
		resizeElements();
	}

	public BorderPane getPane() {
		return pane;
	}

	public void setPane(BorderPane pane) {
		this.pane = pane;
	}
	
	private void getAllProducts() {
		
		products = (List<Product>) PDO.getAll();
	}
	
	private void initComboBoxSearch() {
		comboBoxSearch.getItems().addAll("Identifiant", "Designation", "Categorie");
		comboBoxSearch.getSelectionModel().selectFirst();
	}
	
	private void setEvents() {
		
		updateBtn.setOnAction((event) -> {
			currentProduct = table.getSelectionModel().getSelectedItem();
			if(currentProduct!=null) {
				textDesignation.setText(currentProduct.getDesignation());
				textPrixAchat.setText(Double.toString(currentProduct.getPrixAchat()));
				textPrixVente.setText(Double.toString(currentProduct.getPrixVente()));
				comboBoxCategorie.getSelectionModel().select(currentProduct.getCategory().getIntitule());
			}
			else {
				Alerts.setSuccessAlert("Remarque", "S'il vous plait selectionner un produit");
			}
		});
		
		addBtn.setOnAction((event) -> {
			Alerts.addValidators(errorMessages, formFields);
			if(this.errorMessages.size()==0) {
				if(currentProduct!=null) {
					Boolean confiramtion = Alerts.setAlertConfirmation("Confirmation de la modification", Long.toString(currentProduct.getIdProduct()) + " " + currentProduct.getDesignation() + " " + currentProduct.getPrixVente(), "Etes vous sur de vouloir modifier le produit ?");
					if(confiramtion) {
						Boolean b = PDO.update(new Product(textDesignation.getText(), Double.parseDouble(textPrixAchat.getText()), Double.parseDouble(textPrixVente.getText()), currentProduct.getCategory()), currentProduct.getIdProduct());
						if(b) {
							Alerts.setSuccessAlert("Modification réussie", "Le produit est modifié avec succés");
						}
						else {
							Alerts.setErrorAlert();
						}
					}
					else {
						Alerts.setSuccessAlert("Opération annulée", "La modification est annulée");
					}
					refreshTable();
					clearAll();
				}
				else {
					Category c = CDI.getOneByIntitule(comboBoxCategorie.getValue());
					Boolean b = PDO.add(new Product(textDesignation.getText(), Double.parseDouble(textPrixAchat.getText()), Double.parseDouble(textPrixVente.getText()), c));
					if(b) {
						Alerts.setSuccessAlert("Enregistrement réussie", "Le produit est ajouté avec succés");
						refreshTable();
						clearAll();
					}
					else {
						Alerts.setErrorAlert();
					}
				}
			}
			
			else {
				Alerts.setValidators(errorMessages);
			}
		});
		
		backBtn.setOnAction((event) -> {
			Program.back();
		});
		
		deleteBtn.setOnAction((event) -> {
			currentProduct = table.getSelectionModel().getSelectedItem();
			if(currentProduct != null) {
				Product p = table.getSelectionModel().getSelectedItem();
				Boolean confirmation = Alerts.setAlertConfirmation("Confirmation de la suppression", Long.toString(currentProduct.getIdProduct()) + " " + currentProduct.getDesignation() + " " + currentProduct.getPrixVente(), "Etes vous sur de vouloir supprimer le produit ?");
				if(confirmation) {
					Boolean b = PDO.delete(p);
					if(b) {
						Alerts.setSuccessAlert("Suppression réussie", "La vente est supprimée avec succés");
						refreshTable();
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
				Alerts.setSuccessAlert("Remarque", "S'il vous plait selectionner un produit");
			}
		});
		
		clearBtn.setOnAction((event) -> {
			clearAll();
		});
		
		textSearch.setOnKeyReleased((event) -> {
		if(textSearch.getText().isEmpty()) {
			refreshTable();
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
				Product p;
				if(b) {
					id = Long.parseLong(textSearch.getText());
					 p = PDO.getOne(id);
				
				products.removeAll(products);
				products.add(p);
				table.getItems().removeAll(observaleListProduct);
				observaleListProduct.addAll(products);
				table.setItems(observaleListProduct);}
			}
			else 
				if(key == "Designation") {
					products.removeAll(products);
					products = (List<Product>) PDO.getAllByDesignation(textSearch.getText());
					table.getItems().removeAll(observaleListProduct);
					observaleListProduct.addAll(products);
					table.setItems(observaleListProduct);
				}
			else if(key == "Categorie"){
				products.removeAll(products);
				Category category = CDI.getOneByIntitule(textSearch.getText());
				products = (List<Product>) PDO.getAllByCategory(category);
				table.getItems().removeAll(observaleListProduct);
				observaleListProduct.addAll(products);
				table.setItems(observaleListProduct);
			}
		}
		});
	}
	
	public void initComboBoxCategory() {
		comboBoxCategorie.setPromptText("Catégorie");
		List<Category> categories = new ArrayList<>();
		List<String> intitules = new ArrayList<>();
		categories = (List<Category>) CDI.getAll();
		for(Category c : categories) {
			intitules.add(c.getIntitule());
		}
		comboBoxCategorie.getItems().clear();
		comboBoxCategorie.getItems().addAll(intitules);
		comboBoxCategorie.getSelectionModel().selectFirst();
	}
	
	private void clearAll() {
		textDesignation.setText("");
		textPrixAchat.setText("");
		textPrixVente.setText("");
		comboBoxCategorie.getSelectionModel().selectFirst();
	}
	
	private void initValidators() {
		designationValidator = new RequiredFieldValidator();
		designationValidator.setMessage("La designation est obligatoire");
		prixAchatValidator = new RequiredFieldValidator();
		prixAchatValidator.setMessage("Le prix d'achat est obligatoire");
		prixVenteValidator = new RequiredFieldValidator();
		prixVenteValidator.setMessage("Le prix de vente est obligatoire");
	}
	
	private void registerValidator() {
		textDesignation.getValidators().add(designationValidator);
		textPrixAchat.getValidators().add(prixAchatValidator);
		textPrixVente.getValidators().add(prixVenteValidator);
	}
	
	private void registerFormFields() {
		formFields.add(textDesignation);
		formFields.add(textPrixAchat);
		formFields.add(textPrixVente);
	}
	
	private void setCSS() {
		hboxTitle.getStyleClass().add("custom-pane-top");
		labelTitle.getStyleClass().add("custom-label");
		addBtn.getStyleClass().add("custom-button");
		clearBtn.getStyleClass().add("custom-button");
		updateBtn.getStyleClass().add("custom-button");
		deleteBtn.getStyleClass().add("custom-button");
		backBtn.getStyleClass().add("custom-button");
		labelDesignation.getStyleClass().add("custom-label");
		labelPrixAchat.getStyleClass().add("custom-label");
		labelPrixVente.getStyleClass().add("custom-label");
		labelCategorie.getStyleClass().add("custom-label");
		labelSearch.getStyleClass().add("custom-label");
		grid.getStyleClass().add("custom-pane-center");
		vboxTable.getStyleClass().add("custom-pane-right");
	}
	
	private void setMarginElements() {
		hboxTitle.setMargin(labelTitle, new Insets(20, 0, 20, 250));
		hboxTitle.setMargin(backBtn, new Insets(20, 0, 20, 30));
		vboxTable.setMargin(table, new Insets(30, 30, 0, 0));
		vboxTable.setMargin(textSearch, new Insets(0, 100, 0, 0));
		hboxSearch.setMargin(comboBoxSearch, new Insets(15, 0, 0, 50));
		hboxBtn.setMargin(updateBtn, new Insets(10, 50, 0, 0));
		hboxBtn.setMargin(deleteBtn, new Insets(10, 50, 0, 0));
		grid.setMargin(labelDesignation, new Insets(100, 0, 0, 20));
		grid.setMargin(textDesignation, new Insets(100, 0, 0, 0));
		grid.setMargin(labelPrixAchat, new Insets(20, 0, 0, 20));
		grid.setMargin(labelPrixVente, new Insets(20, 0, 0, 20));
		grid.setMargin(labelCategorie, new Insets(20, 0, 0, 20));
		grid.setMargin(addBtn, new Insets(20, 0, 0, 30));
		grid.setMargin(clearBtn, new Insets(20, 0, 0, 30));
	}
	
	private void resizeElements() {
		textDesignation.setPrefWidth(200);
		comboBoxCategorie.setPrefWidth(200);
		comboBoxSearch.setPrefWidth(200);
		textSearch.setPrefWidth(300);
	}

}

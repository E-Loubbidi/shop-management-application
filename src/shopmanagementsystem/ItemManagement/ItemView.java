package shopmanagementsystem.ItemManagement;

import java.awt.peer.LabelPeer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.jfoenix.controls.IFXTextInputControl;
import com.jfoenix.controls.JFXComboBox;
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
import shopmanagementsystem.productManagement.ProductDaoImpl;
import shopmanagementsystem.saleManagement.SaleDaoImpl;
import shopmanagementsystem.saleManagement.SaleView;

public class ItemView {
	
	private BorderPane pane = new BorderPane();
	private HBox hboxTitle = new HBox();
	private Label labelTitle = new Label("Gestion des lignes de commande");
	private Button backBtn = new Button("Précédent");
	private Label labelQuantite = new Label("Quantité");
	private JFXTextField textQuantite = new JFXTextField();
	private Label labelProduct = new Label("Produit");
	private JFXComboBox<String> comboBoxProduct = new JFXComboBox<String>();
	private TableView<Item> tableItems = new TableView<Item>();
	private GridPane grid = new GridPane();
	private VBox vboxTable = new VBox();
	private Button addBtn = new Button("Enregistrer");
	private Button updateBtn = new Button("Modifier");
	private Button deleteBtn = new Button("Supprimer");
	private Button clearBtn = new Button("Rénitialiser");
	private Label labelSearch = new Label("Chercher par");
	private ComboBox<String> comboBoxSearch = new ComboBox<String>();
	private HBox hboxSearch = new HBox();
	private JFXTextField textSearch = new JFXTextField();
	private HBox hboxBtn = new HBox();
	
	private List<Item> items = new ArrayList<Item>();
	private ObservableList<Item> observableListItems = FXCollections.observableArrayList();
	
	private ItemDaoImpl IDI = new ItemDaoImpl();
	
	private ProductDaoImpl PDI = new ProductDaoImpl();
	
	private SaleDaoImpl SDI = new SaleDaoImpl();
	
	private SaleView sv = null;
	
	private long idSale;
	
	private RequiredFieldValidator quantiteValidator;
	private List<IFXTextInputControl> formFields = new ArrayList<>();
	private List<String> errorMessages = new ArrayList<>();
	
	private Item currentItem = null;
	
	public ItemView() {
		pane.setPrefWidth(1000);
		pane.setPrefHeight(700);
		addContent();
		pane.setTop(hboxTitle);
		pane.setCenter(grid);
		pane.setRight(vboxTable);
		setEvents();
		setCSS();
		setMarginElements();
		resizeElements();
		initValidators();
		registerValidator();
		registerFormFields();
	}

	public BorderPane getPane() {
		return pane;
	}

	public void setPane(BorderPane pane) {
		this.pane = pane;
	}
	
	public TableView<Item> getTableItems() {
		return tableItems;
	}
	public Button getBackBtn() {
		return backBtn;
	}
	
	private void addContent() {
		hboxTitle.getChildren().add(backBtn);
		hboxTitle.getChildren().add(labelTitle);
		hboxSearch.getChildren().add(labelSearch);
		hboxSearch.getChildren().add(comboBoxSearch);
		vboxTable.getChildren().add(hboxSearch);
		vboxTable.getChildren().add(textSearch);
		textSearch.setPromptText("Recherche");
		grid.add(labelQuantite, 0, 0);
		grid.add(textQuantite, 1, 0);
		grid.add(labelProduct, 0, 1);
		grid.add(comboBoxProduct, 1, 1);
		grid.add(addBtn, 0, 2);
		grid.add(clearBtn, 1, 2);
		initTable();
		vboxTable.getChildren().add(tableItems);
		hboxBtn.getChildren().add(updateBtn);
		hboxBtn.getChildren().add(deleteBtn);
		vboxTable.getChildren().add(hboxBtn);
		initcomboBoxSearch();
		initComboBoxProduct();
	}
	
	private void initTable() {
		TableColumn<Item, String> idColumn = new TableColumn<Item, String>("Id");
		idColumn.setCellValueFactory(new PropertyValueFactory<Item, String>("idItem"));
		tableItems.getColumns().add(idColumn);
		idColumn.setPrefWidth(100);
		
		TableColumn<Item, String> productColumn = new TableColumn<Item, String>("Produit");
		productColumn.setCellValueFactory(new PropertyValueFactory<Item, String>("designationProduct"));
		tableItems.getColumns().add(productColumn);
		productColumn.setPrefWidth(150);
		
		TableColumn<Item, String> prixVenteColumn = new TableColumn<Item, String>("Prix de vente");
		prixVenteColumn.setCellValueFactory(new PropertyValueFactory<Item, String>("prixVenteProduct"));
		tableItems.getColumns().add(prixVenteColumn);
		prixVenteColumn.setPrefWidth(100);
		
		TableColumn<Item, String> quantiteColumn = new TableColumn<Item, String>("Quantité");
		quantiteColumn.setCellValueFactory(new PropertyValueFactory<Item, String>("quantite"));
		tableItems.getColumns().add(quantiteColumn);
		quantiteColumn.setPrefWidth(100);
		
		TableColumn<Item, String> sousTotalColumn = new TableColumn<Item, String>("Sous-total");
		sousTotalColumn.setCellValueFactory(new PropertyValueFactory<Item, String>("sousTotal"));
		tableItems.getColumns().add(sousTotalColumn);
		sousTotalColumn.setPrefWidth(100);
		
		tableItems.setPrefWidth(550);
		if(SaleView.getCurrentSale()!=null) {
			idSale = SaleView.getCurrentSale().getIdSale();
		}
		System.out.println(idSale);
		getAllItems();
		observableListItems.addAll(items);
		tableItems.setItems(observableListItems);
	}
	
	private void getAllItems() {
		items = (List<Item>) IDI.getAllItemsByIdSale(idSale);
	}
	
	private void initcomboBoxSearch() {
		comboBoxSearch.getItems().addAll("Identifiant", "Produit");
		comboBoxSearch.getSelectionModel().selectFirst();
	}
	
	private void initComboBoxProduct() {
		List<Product> products = (List<Product>) PDI.getAll();
		for(Product p : products) {
			comboBoxProduct.getItems().add(p.getIdProduct() + " " + p.getDesignation());
		}
		comboBoxProduct.getSelectionModel().selectFirst();
	}
	
	private void setEvents() {
		backBtn.setOnAction((event) -> {
			SaleView.currentSale.setTotal(calculerTotal(SaleView.mapItems));
			Boolean b = SDI.update(SaleView.currentSale, SaleView.currentSale.getIdSale());
			if(b) {
				sv = new SaleView();
				Program.recentHistoryPanes.set(Program.recentHistoryPanes.size() - 2, sv.getPane());
			}
			Program.back();
		});
		
		addBtn.setOnAction((event) -> {
			Alerts.addValidators(errorMessages, formFields);
			if(errorMessages.size()==0) {
				String designation = comboBoxProduct.getValue();
				String productDetail[] = designation.split(" ");
				Product p = PDI.getOne(Long.parseLong(productDetail[0]));
				double sousTotal = calculerSousTotal(p.getPrixVente(), Long.parseLong(textQuantite.getText()));
				Item item = new Item(p, Long.parseLong(textQuantite.getText()), sousTotal, SaleView.currentSale);
				
				if(SaleView.mapItems.containsKey(p.getIdProduct())) {
					item.setIdItem(SaleView.mapItems.get(p.getIdProduct()).getIdItem());
					if(currentItem==null) {
						item.setSousTotal(SaleView.mapItems.get(p.getIdProduct()).getSousTotal() + item.getSousTotal());
						item.setQuantite(SaleView.mapItems.get(p.getIdProduct()).getQuantite() + item.getQuantite());
					}
					else {
						Boolean confirmation = Alerts.setAlertConfirmation("Confiramtion de la modification", currentItem.getIdItem() + " " + currentItem.getQuantite() + " " + currentItem.getSousTotal(), "Etes vous sur de vouloir modifier la ligne de commande ?");
						if(confirmation) {
							item.setQuantite(item.getQuantite());
							item.setSousTotal(item.getSousTotal());
						}
						else {
							Alerts.setSuccessAlert("Opération annulée", "La modification a été annulée");
						}
					}
				}	
					
					SaleView.mapItems.put(p.getIdProduct(), item);
					clearAll();
					IDI.deleteAllByIdSale(SaleView.currentSale.getIdSale());
					for(Entry<Long, Item> it : SaleView.mapItems.entrySet()) {
						Boolean b = IDI.addItem(it.getValue());
					}
					if(currentItem!=null) {
						Alerts.setSuccessAlert("Modification réussie", "La ligne de commande est modifiée avec succés");
					}
					else {
						Alerts.setSuccessAlert("Ajout réussi", "La ligne de commande est ajoutée avec succés");
					}
					refreshTable();
//					comboBoxProduct.getSelectionModel().selectFirst();
			}
			
			else {
				Alerts.setValidators(errorMessages);
			}
		});
		
		clearBtn.setOnAction((event) -> {
			clearAll();
		});
		
		updateBtn.setOnAction((event) -> {
			currentItem = tableItems.getSelectionModel().getSelectedItem();
			if(currentItem != null) {
				textQuantite.setText(Long.toString(currentItem.getQuantite()));
				String designation = currentItem.getProduct().getIdProduct() + " " + currentItem.getProduct().getDesignation();
				comboBoxProduct.getSelectionModel().select(designation);
			}
			else {
				Alerts.setSuccessAlert("Remarque", "S'il vous plait séléctionnez une ligne de commande");
			}
		});
		
		deleteBtn.setOnAction((event) -> {
			currentItem = tableItems.getSelectionModel().getSelectedItem();
			if(currentItem != null) {
				
					Boolean confiramtion = Alerts.setAlertConfirmation("Confirmation de la suppression", currentItem.getIdItem() + " " + currentItem.getQuantite()  + " " + currentItem.getSousTotal(), "Etes vous sur de vouloir supprimer la ligne de commande ?");
					if(confiramtion) {
						Boolean b = IDI.delete(currentItem.getIdItem());
						if(b!=null) {
							Alerts.setSuccessAlert("Suppression réussie", "La ligne de commande a été supprimée avec succés");
							refreshTable();
							clearAll();
						}
						else {
							Alerts.setErrorAlert();
						}
					}
					
					else {
						Alerts.setSuccessAlert("Opération annulée", "La suppression a été annulée");
					}
				
			}
			else {
				Alerts.setSuccessAlert("Remarque", "S'il vous plait séléctionnez une ligne de commande");
			}
		});
		
		textSearch.setOnKeyReleased((event) -> {
			if(textSearch.getText().isEmpty()) {
				refreshTable();
			}
			else if(comboBoxSearch.getValue() == "Identifiant") {
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
					Item item = IDI.getOne(id);
					tableItems.getItems().clear();
					items.removeAll(items);
					observableListItems.clear();
					items.add(item);
					observableListItems.addAll(items);
					tableItems.setItems(observableListItems);
				}
			}
			else if(comboBoxSearch.getValue() == "Produit"){
				String key = textSearch.getText();
				Item item = IDI.getOneByDesignation(key);
				tableItems.getItems().clear();
				items.removeAll(items);
				observableListItems.clear();
				items.add(item);
				observableListItems.addAll(items);
				tableItems.setItems(observableListItems);
			}
		});
		
	}
	
	private void refreshTable() {
		items.removeAll(items);
		tableItems.getItems().clear();
		observableListItems.clear();
		getAllItems();
		observableListItems.addAll(items);
		tableItems.setItems(observableListItems);
	}
	
	private void clearAll() {
		textQuantite.setText("");
		comboBoxProduct.getSelectionModel().selectFirst();
	}
	
	private double calculerSousTotal(double prixVente, long quantite) {
		return prixVente * quantite;
	}
	
	private double calculerTotal(HashMap<Long, Item> mapItems) {
		double total = 0;
		for(Entry<Long, Item> item : mapItems.entrySet()) {
			total += item.getValue().getSousTotal();
		}
		return total;
	}
	
	private void setCSS() {
		hboxTitle.getStyleClass().add("custom-pane-top");
		labelTitle.getStyleClass().add("custom-label");
		addBtn.getStyleClass().add("custom-button");
		clearBtn.getStyleClass().add("custom-button");
		updateBtn.getStyleClass().add("custom-button");
		deleteBtn.getStyleClass().add("custom-button");
		backBtn.getStyleClass().add("custom-button");
		labelQuantite.getStyleClass().add("custom-label");
		labelProduct.getStyleClass().add("custom-label");
		labelSearch.getStyleClass().add("custom-label");
		grid.getStyleClass().add("custom-pane-center");
		vboxTable.getStyleClass().add("custom-pane-right");
	}
	
	private void setMarginElements() {
		hboxTitle.setMargin(labelTitle, new Insets(20, 0, 20, 230));
		hboxTitle.setMargin(backBtn, new Insets(20, 0, 20, 30));
		vboxTable.setMargin(tableItems, new Insets(30, 30, 0, 0));
		vboxTable.setMargin(textSearch, new Insets(0, 100, 0, 0));
		hboxSearch.setMargin(comboBoxSearch, new Insets(15, 0, 0, 50));
		hboxBtn.setMargin(updateBtn, new Insets(10, 50, 0, 0));
		hboxBtn.setMargin(deleteBtn, new Insets(10, 50, 0, 0));
		grid.setMargin(labelQuantite, new Insets(150, 0, 0, 20));
		grid.setMargin(textQuantite, new Insets(150, 0, 0, 0));
		grid.setMargin(labelProduct, new Insets(40, 0, 0, 20));
		grid.setMargin(comboBoxProduct, new Insets(35, 0, 0, 20));
		grid.setMargin(addBtn, new Insets(40, 0, 0, 30));
		grid.setMargin(clearBtn, new Insets(40, 0, 0, 30));
	}
	
	private void resizeElements() {
		textQuantite.setPrefWidth(200);
		comboBoxProduct.setPrefWidth(200);
		comboBoxSearch.setPrefWidth(200);
		textSearch.setPrefWidth(300);
	}
	
	private void initValidators() {
		quantiteValidator = new RequiredFieldValidator();
		quantiteValidator.setMessage("La quantité est obligatoire");
	}
	
	private void registerValidator() {
		textQuantite.getValidators().add(quantiteValidator);
	}
	
	private void registerFormFields() {
		formFields.add(textQuantite);

	}
}

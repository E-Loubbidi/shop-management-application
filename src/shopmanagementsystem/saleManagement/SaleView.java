package shopmanagementsystem.saleManagement;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import com.jfoenix.controls.IFXTextInputControl;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import shopmanagementsystem.Alerts;
import shopmanagementsystem.Program;
import shopmanagementsystem.ItemManagement.Item;
import shopmanagementsystem.ItemManagement.ItemDaoImpl;
import shopmanagementsystem.ItemManagement.ItemView;
import shopmanagementsystem.customerManagement.Customer;
import shopmanagementsystem.customerManagement.CustomerDaoImpl;
import shopmanagementsystem.productManagement.Product;
import shopmanagementsystem.productManagement.ProductDaoImpl;

public class SaleView {

	private BorderPane pane = new BorderPane();
	private HBox hboxTitle = new HBox();
	private Label labelTitle = new Label("Gestion des ventes");
	private Button backBtn = new Button("Précédent");
	private TableView<Sale> tableSales = new TableView<Sale>();
	private VBox vboxTable = new VBox();
	private Label  labelSearch = new Label("Chercher par");
	private HBox hboxSearch = new HBox();
	private ComboBox<String> comboBoxSearch = new ComboBox<String>();
	private JFXTextField textSearch = new JFXTextField();
	private Button displayBtn = new Button("Consulter");
	private Button addBtn = new Button("Ajouter");
//	private Button updateBtn = new Button("Modifier");
	private Button deleteBtn = new Button("Supprimer");
	private VBox vboxBtn = new VBox();
	private ComboBox<String> comboBoxCustomer = new ComboBox<String>();
	private Label labelCustomer = new Label("Client");
	
	private List<Sale> sales = new ArrayList<Sale>();
	private ObservableList<Sale> observableListSale = FXCollections.observableArrayList();
	
	private SaleDaoImpl SDI = new SaleDaoImpl();
	
	public static Sale currentSale = null;
	
	private ItemView iv = null;
	private ItemDaoImpl IDI = new ItemDaoImpl();
	
	private CustomerDaoImpl CDI = new CustomerDaoImpl();
	
	private ProductDaoImpl PDI = new ProductDaoImpl();
	
	public static HashMap<Long, Item> mapItems = null;
	
//	private Stage windowNewItem = new Stage();
//	private BorderPane root = new BorderPane();
//	private Scene sceneNewItem = new Scene(root);
	
	private List<Item> items = null;
	
	public SaleView() {
		pane.setPrefWidth(1000);
		pane.setPrefHeight(700);
		addContent();
		pane.setTop(hboxTitle);
		pane.setCenter(vboxTable);
		pane.setLeft(vboxBtn);
		setCSS();
		setMarginElements();
		resizeElements();
		setEvents();
	}

	public BorderPane getPane() {
		return pane;
	}

	public void setPane(BorderPane pane) {
		this.pane = pane;
	}
	
	public static Sale getCurrentSale() {
		return currentSale;
	}
	
	private void addContent() {
		hboxTitle.getChildren().add(backBtn);
		hboxTitle.getChildren().add(labelTitle);
		initComboBoxCustomer();
		vboxBtn.getChildren().add(labelCustomer);
		vboxBtn.getChildren().add(comboBoxCustomer);
		vboxBtn.getChildren().add(addBtn);
		vboxBtn.getChildren().add(displayBtn);
//		vboxBtn.getChildren().add(updateBtn);
		vboxBtn.getChildren().add(deleteBtn);
		hboxSearch.getChildren().add(labelSearch);
		hboxSearch.getChildren().add(comboBoxSearch);
		vboxTable.getChildren().add(hboxSearch);
		textSearch.setPromptText("Recherche");
		vboxTable.getChildren().add(textSearch);
		vboxTable.getChildren().add(tableSales);
		initTable();
		initComboBoxSearch();
	}
	
	private void initTable() {
		TableColumn<Sale, String> idColumn = new TableColumn<Sale, String>("Id");
		idColumn.setCellValueFactory(new PropertyValueFactory<Sale, String>("idSale"));
		tableSales.getColumns().add(idColumn);
		idColumn.setPrefWidth(150);
		
		TableColumn<Sale, String> dateColumn = new TableColumn<Sale, String>("Date de vente");
		dateColumn.setCellValueFactory(new PropertyValueFactory<Sale, String>("dateSale"));
		tableSales.getColumns().add(dateColumn);
		dateColumn.setPrefWidth(150);
		
		TableColumn<Sale, String> totalColumn = new TableColumn<Sale, String>("Total de vente");
		totalColumn.setCellValueFactory(new PropertyValueFactory<Sale, String>("total"));
		tableSales.getColumns().add(totalColumn);
		totalColumn.setPrefWidth(150);
		
		TableColumn<Sale, String> clientColumn = new TableColumn<Sale, String>("Client");
		clientColumn.setCellValueFactory(new PropertyValueFactory<Sale, String>("nameCustomer"));
		tableSales.getColumns().add(clientColumn);
		clientColumn.setPrefWidth(250);
		
		tableSales.setPrefWidth(700);
		
		getAllSales();
		observableListSale.addAll(sales);
		tableSales.setItems(observableListSale);
	}
	
	private void getAllSales() {
		sales = (List<Sale>) SDI.getAll();
	}
	
	private void initComboBoxSearch() {
		comboBoxSearch.getItems().addAll("Identifiant", "Date de vente", "Client");
		comboBoxSearch.getSelectionModel().selectFirst();
	}
	
	private void initComboBoxCustomer() {
		List<Customer> customers = (List<Customer>) CDI.getAll();
		//comboBoxCustomer.setEditable(true);
		for(Customer c : customers) {
			comboBoxCustomer.getItems().add(Long.toString(c.getIdCustomer()) + " " + c.getNom() + " " + c.getPrenom());
		}
		comboBoxCustomer.getSelectionModel().selectFirst();
	}
	
	private void addNewWindowItem() {
		
		 Stage windowNewItem = new Stage();
		 BorderPane root = new BorderPane();
		 Scene sceneNewItem = new Scene(root);
		
		sceneNewItem.getStylesheets().add("style.css");
		windowNewItem.getIcons().add(new Image("iconShop.png"));
		windowNewItem.setScene(sceneNewItem);
		root.setPrefWidth(500);
		root.setPrefHeight(300);
		Label labelQuantite = new Label("Quantite");
		labelQuantite.getStyleClass().add("custom-label");
		JFXTextField textQuantite = new JFXTextField();
		Label labelProduct = new Label("Produit");
		labelProduct.getStyleClass().add("custom-label");
		JFXComboBox<String> comboBoxProduct = new JFXComboBox<String>();
		comboBoxProduct.setPrefWidth(200);
		Label labelTitle = new Label("Nouvelle ligne de commande");
		labelTitle.getStyleClass().add("custom-label");
		HBox hboxTitle = new HBox();
		hboxTitle.getChildren().add(labelTitle);
		hboxTitle.setMargin(labelTitle, new Insets(30, 0, 20, 20));
		Button backBtn = new Button("Quitter");
		Button addBtn = new Button("Ajouter");
		
		List<String> errorMessages = new ArrayList<String>();
		List<IFXTextInputControl> formFields = new ArrayList<IFXTextInputControl>();
		RequiredFieldValidator quantiteValidator = new RequiredFieldValidator();
		quantiteValidator.setMessage("La quantité est obligatoire");
		textQuantite.getValidators().add(quantiteValidator);
		formFields.add(textQuantite);
		
		backBtn.getStyleClass().add("custom-button");
		addBtn.getStyleClass().add("custom-button");
		backBtn.setPrefWidth(150);
		addBtn.setPrefWidth(350);
		GridPane grid = new GridPane();
		grid.add(labelQuantite, 0, 0);
		grid.add(textQuantite, 1, 0);
		grid.add(labelProduct, 0, 1);
		grid.add(comboBoxProduct, 1, 1);
		grid.add(addBtn, 0, 2);
		grid.add(backBtn, 1, 2);
		grid.setMargin(labelQuantite, new Insets(10, 10, 10, 10));
		grid.setMargin(textQuantite, new Insets(10, 10, 10, 10));
		grid.setMargin(labelProduct, new Insets(10, 10, 10, 10));
		grid.setMargin(comboBoxProduct, new Insets(10, 10, 10, 10));
		grid.setMargin(addBtn, new Insets(20, 20, 0, 10));
		grid.setMargin(backBtn, new Insets(20, 0, 0, 20));
		List<Product> products = (List<Product>) PDI.getAll();
		for(Product p : products) {
			comboBoxProduct.getItems().add(p.getIdProduct() + " " + p.getDesignation());
		}
		comboBoxProduct.getSelectionModel().selectFirst();
		
		windowNewItem.initStyle(StageStyle.UNDECORATED);
		hboxTitle.getStyleClass().add("custom-pane-top");
		grid.getStyleClass().add("custom-pane-center");
		root.setTop(hboxTitle);
		root.setCenter(grid);
		windowNewItem.show();
		
//		List<Item> items = new ArrayList<Item>();
		
		addBtn.setOnAction((event) -> {
			Alerts.addValidators(errorMessages, formFields);
			if(errorMessages.size()==0) {
				String designation = comboBoxProduct.getValue();
				String productDetail[] = designation.split(" ");
				Product p = PDI.getOne(Long.parseLong(productDetail[0]));
				double sousTotal = calculerSousTotal(p.getPrixVente(), Long.parseLong(textQuantite.getText()));
				Item item = new Item(p, Long.parseLong(textQuantite.getText()), sousTotal, currentSale);
				
					if(mapItems.containsKey(p.getIdProduct())) {
						item.setQuantite(mapItems.get(p.getIdProduct()).getQuantite() + item.getQuantite());
						item.setSousTotal(calculerSousTotal(item.getProduct().getPrixVente(), item.getQuantite()));
					}
					mapItems.put(p.getIdProduct(), item);
//					items.add(mapItems.get(p.getIdProduct()));
					textQuantite.setText("");
					comboBoxProduct.getSelectionModel().selectFirst();
			}
			
			else {
				Alerts.setValidators(errorMessages);
			}
		});
		
		backBtn.setOnAction((event) -> {
			if(mapItems.isEmpty()) {
				Boolean b = Alerts.setAlertConfirmation("Remarque", "", "S'il vous plait ajouter au moins une ligne de commande pour pouvoir créer la vente");
				if(b == false){
					windowNewItem.close();
					SDI.delete(currentSale.getIdSale());
				}
			}
			else {
				for(Entry<Long, Item> item : mapItems.entrySet()) {
					Boolean b = IDI.add(item.getValue());
				}
				currentSale.setTotal(calculerTotal(mapItems));
				SDI.update(currentSale, currentSale.getIdSale());
				windowNewItem.close();
				Alerts.setSuccessAlert("Enregistrement réussie", "La vente est ajoutée avec succés");
				refreshTable();
				this.comboBoxCustomer.getSelectionModel().selectFirst();
			}
		});
	}
	
	private void setEvents() {
		backBtn.setOnAction((event) -> {
			Program.back();
		});
		
		displayBtn.setOnAction((event) -> {
			currentSale = tableSales.getSelectionModel().getSelectedItem();
			if(currentSale!=null) {
				mapItems = new HashMap();
				List<Item> items = (List<Item>) IDI.getAllItemsByIdSale(currentSale.getIdSale());
				for(Item item : items) {
					mapItems.put(item.getProduct().getIdProduct(), item);
				}
				iv = new ItemView();
				Program.showViews(iv.getPane());
			}
			else {
				Alerts.setSuccessAlert("Remarque", "S'il vous plait séléctionnez une vente");
			}
		});
		
		addBtn.setOnAction((event) -> {
			double total = 0;
			java.util.Date date = new java.util.Date();
			String customerDetail = comboBoxCustomer.getSelectionModel().getSelectedItem();
			String CustomerDetails[] = customerDetail.split(" ");
			Customer customer = CDI.getOne(Long.parseLong(CustomerDetails[0]));
//			List<Item> items = (List<Item>) IDI.getAllItemsByIdSale(currentSale.getIdSale());
			currentSale = SDI.add(new Sale(new Date(date.getTime()), total, customer, items, "Non payee"));
			if(currentSale!=null) {
				System.out.println("currentSale id = " + currentSale.getIdSale());
				
				mapItems = new HashMap<Long, Item>();
					addNewWindowItem();
					
			}
			else {
				Alerts.setErrorAlert();
			}
		});
		
		deleteBtn.setOnAction((event) -> {
			currentSale = tableSales.getSelectionModel().getSelectedItem();
			if(currentSale!=null) {
				boolean confirmation = Alerts.setAlertConfirmation("Confirmation de la suppression", Long.toString(currentSale.getIdSale()) + " " + currentSale.getTotal(), "Etes vous sur de vouloir supprimer la vente ?");
				if(confirmation) {
					Boolean bool = SDI.deleteByIdSale(currentSale.getIdSale());
					if(bool) {System.out.println(bool);
					
					Boolean b = SDI.delete(currentSale.getIdSale());
						if(b) {
							Alerts.setSuccessAlert("Suppression réussie", "La vente est supprimée avec succés");
							refreshTable();
						}
						else {
							Alerts.setErrorAlert();
						}
						
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
				Alerts.setSuccessAlert("Remarque", "S'il vous plait selectionner une vente");
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
					Sale sale = SDI.getOne(id);
					tableSales.getItems().clear();
					sales.removeAll(sales);
					observableListSale.clear();
					sales.add(sale);
					observableListSale.addAll(sales);
					tableSales.setItems(observableListSale);
				}
			}
			else if(comboBoxSearch.getValue() == "Date de vente") {
				String key = textSearch.getText();
				sales.removeAll(sales);
				sales = (List<Sale>) SDI.getAllByDate(key);
				tableSales.getItems().clear();
				observableListSale.clear();
				observableListSale.addAll(sales);
				tableSales.setItems(observableListSale);
			}
			
			else if(comboBoxSearch.getValue() == "Client") {
				String key = textSearch.getText();
				sales.removeAll(sales);
				sales = (List<Sale>) SDI.getAllByClient(key);
				tableSales.getItems().clear();
				observableListSale.clear();
				observableListSale.addAll(sales);
				tableSales.setItems(observableListSale);
			}
		});
		
	}
	
	private void refreshTable() {
		tableSales.getItems().clear();
		sales.removeAll(sales);
		getAllSales();
		observableListSale.addAll(sales);
		tableSales.setItems(observableListSale);
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
		labelCustomer.getStyleClass().add("custom-label");
		displayBtn.getStyleClass().add("custom-button");
		addBtn.getStyleClass().add("custom-button");
		backBtn.getStyleClass().add("custom-button");
//		clearBtn.getStyleClass().add("custom-button");
//		updateBtn.getStyleClass().add("custom-button");
		deleteBtn.getStyleClass().add("custom-button");
		vboxBtn.getStyleClass().add("custom-pane-left");
		labelSearch.getStyleClass().add("custom-label");
		vboxTable.getStyleClass().add("custom-pane-center");
	}
	
	private void setMarginElements() {
		hboxTitle.setMargin(backBtn, new Insets(20, 0, 20, 30));
		hboxTitle.setMargin(labelTitle, new Insets(20, 0, 20, 250));
		vboxTable.setMargin(tableSales, new Insets(30, 50, 0, 50));
		vboxTable.setMargin(textSearch, new Insets(0, 100, 0, 100));
		hboxSearch.setMargin(labelSearch, new Insets(30, 50, 0, 100));
		hboxSearch.setMargin(comboBoxSearch, new Insets(45, 0, 0, 50));
		vboxBtn.setMargin(labelCustomer, new Insets(35, 0, 0, 20));
		vboxBtn.setMargin(comboBoxCustomer, new Insets(20, 0, 0, 20));
		vboxBtn.setMargin(displayBtn, new Insets(50, 0, 0, 0));
		vboxBtn.setMargin(addBtn, new Insets(50, 0, 0, 0));
//		vboxBtn.setMargin(updateBtn, new Insets(50, 0, 0, 0));
		vboxBtn.setMargin(deleteBtn, new Insets(50, 0, 0, 0));
	}
	
	private void resizeElements() {
		displayBtn.setPrefWidth(200);
		addBtn.setPrefWidth(200);
//		updateBtn.setPrefWidth(200);
		deleteBtn.setPrefWidth(200);
		comboBoxSearch.setPrefWidth(200);
	}
}

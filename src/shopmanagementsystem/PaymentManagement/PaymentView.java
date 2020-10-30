package shopmanagementsystem.PaymentManagement;

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
import shopmanagementsystem.PaymentManagement.Traite.TraiteView;
import shopmanagementsystem.customerManagement.Customer;
import shopmanagementsystem.customerManagement.CustomerDaoImpl;
import shopmanagementsystem.productManagement.Product;
import shopmanagementsystem.productManagement.ProductDaoImpl;
import shopmanagementsystem.saleManagement.Sale;
import shopmanagementsystem.saleManagement.SaleDaoImpl;

public class PaymentView {
	private BorderPane pane = new BorderPane();
	private HBox hboxTitle = new HBox();
	private Label labelTitle = new Label("Gestion des paiements");
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
	private ComboBox<String> comboBoxReglement = new ComboBox<String>();
	private Label labelReglement = new Label("Réglement");
	
	private List<Sale> sales = new ArrayList<Sale>();
	private ObservableList<Sale> observableListSale = FXCollections.observableArrayList();
	
	private SaleDaoImpl SDI = new SaleDaoImpl();
	
	public static Sale currentSale = null;
	
	private ItemView iv = null;
	private ItemDaoImpl IDI = new ItemDaoImpl();
	
	private CustomerDaoImpl CDI = new CustomerDaoImpl();
	
	private ProductDaoImpl PDI = new ProductDaoImpl();
	
	private PaymentDaoImpl PDM = new PaymentDaoImpl();
	
	public static HashMap<Long, Item> mapItems = null;
	
	//Table of payments
	private TableView<Payment> tablePayment = new TableView<Payment>();
	private ObservableList<Payment> observableListPayment = FXCollections.observableArrayList();
	private List<Payment> payments = new ArrayList<Payment>();
	private VBox vboxTablePayment = new VBox();
	private Label  labelSearchPayment = new Label("Chercher par");
	private JFXTextField textSearchPayment = new JFXTextField();
	private HBox hboxSearchPayment = new HBox();
	private ComboBox<String> comboBoxSearchPayment = new ComboBox<String>();
	private Label labelSale = new Label("Les Ventes non payées");
	private Label labelPayment = new Label("Les Paiements");
	
	private Payment currentPayment = null;
	
	public PaymentView() {
		pane.setPrefWidth(1000);
		pane.setPrefHeight(700);
		addContent();
		pane.setTop(hboxTitle);
		pane.setRight(vboxTable);
		pane.setLeft(vboxBtn);
		pane.setCenter(vboxTablePayment);
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
		initComboBoxReglement();
		vboxBtn.getChildren().add(labelReglement);
		vboxBtn.getChildren().add(comboBoxReglement);
		vboxBtn.getChildren().add(addBtn);
		vboxBtn.getChildren().add(displayBtn);
//		vboxBtn.getChildren().add(updateBtn);
		vboxBtn.getChildren().add(deleteBtn);
		vboxBtn.setPrefWidth(150);
		vboxTable.getChildren().add(labelSale);
		hboxSearch.getChildren().add(labelSearch);
		hboxSearch.getChildren().add(comboBoxSearch);
		vboxTable.getChildren().add(hboxSearch);
		textSearch.setPromptText("Recherche");
		vboxTable.getChildren().add(textSearch);
		vboxTable.getChildren().add(tableSales);
		vboxTable.setPrefWidth(430);
		initTable();
		initComboBoxSearch();
		initTablePayment();
		vboxTablePayment.getChildren().add(labelPayment);
		hboxSearchPayment.getChildren().add(labelSearchPayment);
		initComboBoxSearchPayment();
		hboxSearchPayment.getChildren().add(comboBoxSearchPayment);
		vboxTablePayment.getChildren().add(hboxSearchPayment);
		vboxTablePayment.getChildren().add(textSearchPayment);
		vboxTablePayment.getChildren().add(tablePayment);
		textSearchPayment.setPromptText("Recherche");
		
	}
	
	private void initTable() {
		TableColumn<Sale, String> idColumn = new TableColumn<Sale, String>("Id");
		idColumn.setCellValueFactory(new PropertyValueFactory<Sale, String>("idSale"));
		tableSales.getColumns().add(idColumn);
		idColumn.setPrefWidth(70);
		
		TableColumn<Sale, String> dateColumn = new TableColumn<Sale, String>("Date de vente");
		dateColumn.setCellValueFactory(new PropertyValueFactory<Sale, String>("dateSale"));
		tableSales.getColumns().add(dateColumn);
		dateColumn.setPrefWidth(100);
		
		TableColumn<Sale, String> totalColumn = new TableColumn<Sale, String>("Total de vente");
		totalColumn.setCellValueFactory(new PropertyValueFactory<Sale, String>("total"));
		tableSales.getColumns().add(totalColumn);
		totalColumn.setPrefWidth(100);
		
		TableColumn<Sale, String> clientColumn = new TableColumn<Sale, String>("Client");
		clientColumn.setCellValueFactory(new PropertyValueFactory<Sale, String>("nameCustomer"));
		tableSales.getColumns().add(clientColumn);
		clientColumn.setPrefWidth(150);
		
		tableSales.setPrefWidth(420);
		
		getAllSales();
		observableListSale.addAll(sales);
		tableSales.setItems(observableListSale);
	}
	
	private void getAllSales() {
		sales = (List<Sale>) PDM.getAllSalesNotPayed("Non Payee");
	}
	
	private void initTablePayment() {
		TableColumn<Payment, String> idPayment = new TableColumn<Payment, String>("Id");
		idPayment.setCellValueFactory(new PropertyValueFactory<Payment, String>("idPayment"));
		tablePayment.getColumns().add(idPayment);
		idPayment.setPrefWidth(70);
		
		TableColumn<Payment, String> datePayment = new TableColumn<Payment, String>("Date de paiement");
		datePayment.setCellValueFactory(new PropertyValueFactory<Payment, String>("datePayment"));
		tablePayment.getColumns().add(datePayment);
		datePayment.setPrefWidth(100);
		
		TableColumn<Payment, String> typeReglement = new TableColumn<Payment, String>("Type");
		typeReglement.setCellValueFactory(new PropertyValueFactory<Payment, String>("typeReglement"));
		tablePayment.getColumns().add(typeReglement);
		typeReglement.setPrefWidth(90);
		
		TableColumn<Payment, String> sale = new TableColumn<Payment, String>("Vente N°");
		sale.setCellValueFactory(new PropertyValueFactory<Payment, String>("idSale"));
		tablePayment.getColumns().add(sale);
		sale.setPrefWidth(70);
		
		TableColumn<Payment, String> montant = new TableColumn<Payment, String>("Montant");
		montant.setCellValueFactory(new PropertyValueFactory<Payment, String>("montant"));
		tablePayment.getColumns().add(montant);
		montant.setPrefWidth(70);
		
		
		tablePayment.setPrefWidth(370);
		
		getAllPayments();
		observableListPayment.addAll(payments);
		tablePayment.setItems(observableListPayment);
	}
	
	private void initComboBoxSearch() {
		comboBoxSearch.getItems().addAll("Identifiant", "Date de vente", "Client");
		comboBoxSearch.getSelectionModel().selectFirst();
	}
	
	private void initComboBoxSearchPayment() {
		comboBoxSearchPayment.getItems().addAll("Identifiant", "Date de paiement", "Type de réglement");
		comboBoxSearchPayment.getSelectionModel().selectFirst();
	}
	
	private void initComboBoxReglement() {
		comboBoxReglement.getItems().addAll("Espèce" , "Chèque","En ligne","Traites");
		comboBoxReglement.getSelectionModel().selectFirst();
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
				this.comboBoxReglement.getSelectionModel().selectFirst();
			}
		});
	}
	
	public void addPayment(String typeReglement) {
		currentSale.setEtat("Payee");
		SDI.update(currentSale, currentSale.getIdSale());
		refreshTable();
		java.util.Date date = new java.util.Date();
		Payment p = new Payment(new Date(date.getTime()), typeReglement, currentSale.getTotal(), currentSale);
		Boolean b = PDM.add(p);
		refreshTablePayment();
		comboBoxReglement.getSelectionModel().selectFirst();
		if(b) {
			Alerts.setSuccessAlert("Ajout réussi", "Le paiement est ajouté avec succés");
		}
	}
	
	private void setEvents() {
		backBtn.setOnAction((event) -> {
			Program.back();
		});
		
		addBtn.setOnAction((event) -> {
			java.util.Date date = new java.util.Date();
			String typeReglement = comboBoxReglement.getValue();
			currentSale = tableSales.getSelectionModel().getSelectedItem();
			if(currentSale!=null) {
				Boolean b = false;
				if(typeReglement.equals("Traites")) {
					TraiteView.newPaymentWithTraite();
					refreshTablePayment();
				}
				else {
					currentSale.setEtat("Payee");
					SDI.update(currentSale, currentSale.getIdSale());
					refreshTable();
					Payment p = new Payment(new Date(date.getTime()), typeReglement, currentSale.getTotal(), currentSale);
					b = PDM.add(p);
					refreshTablePayment();
					comboBoxReglement.getSelectionModel().selectFirst();
				}
				if(b) {
					Alerts.setSuccessAlert("Ajout réussi", "Le paiement est ajouté avec succés");
				}
			}
			else {
				Alerts.setSuccessAlert("Remarque", "S'il vous plait selectionner une vente");
			}
		});
		
		deleteBtn.setOnAction((event) -> {
			currentPayment = tablePayment.getSelectionModel().getSelectedItem();
			if(currentPayment!=null) {
				boolean confirmation = Alerts.setAlertConfirmation("Confirmation de la suppression", Long.toString(currentPayment.getIdSale()) + " " + currentPayment.getTypeReglement(), "Etes vous sur de vouloir supprimer le paiement ?");
				if(confirmation) {

					Boolean b = PDM.delete(currentPayment.getIdPayment());
						if(b) {
							refreshTablePayment();
							Alerts.setSuccessAlert("Suppression réussie", "Le paiement est supprimé avec succés");
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
				Alerts.setSuccessAlert("Remarque", "S'il vous plait selectionner un paiement");
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
		
		textSearchPayment.setOnKeyReleased((event) -> {
			if(textSearchPayment.getText().isEmpty()) {
				refreshTablePayment();
			}
			else if(comboBoxSearchPayment.getValue() == "Identifiant") {
				Long id;
				Boolean b = true;
				try {
					id = Long.parseLong(textSearchPayment.getText());
				}
				catch(NumberFormatException e) {
					b = false;
				}
				if(b) {
					id = Long.parseLong(textSearchPayment.getText());
					Payment payment = PDM.getOne(id);
					tablePayment.getItems().clear();
					payments.removeAll(payments);
					observableListPayment.clear();
					payments.add(payment);
					observableListPayment.addAll(payments);
					tablePayment.setItems(observableListPayment);
				}
			}
			else if(comboBoxSearchPayment.getValue() == "Date de paiement") {
				String key = textSearchPayment.getText();
				payments.removeAll(payments);
				payments = (List<Payment>) PDM.getAllByDate(key);
				tablePayment.getItems().clear();
				observableListPayment.clear();
				observableListPayment.addAll(payments);
				tablePayment.setItems(observableListPayment);
			}
			
			else if(comboBoxSearchPayment.getValue() == "Type de réglement") {
				String key = textSearchPayment.getText();
				payments.removeAll(payments);
				payments = (List<Payment>) PDM.getAllByType(key);
				tablePayment.getItems().clear();
				observableListPayment.clear();
				observableListPayment.addAll(payments);
				tablePayment.setItems(observableListPayment);
			}
		});
		
		displayBtn.setOnAction((event) ->{
			currentPayment = tablePayment.getSelectionModel().getSelectedItem();
			if(currentPayment!=null) {
				if(currentPayment.getTypeReglement().equals("Traites")) {
					TraiteView traiteView = new TraiteView(currentPayment.getSale());
					Program.showViews(traiteView.getPane());
				}
			}
			TraiteView.calculer(currentPayment.getSale());
		});
		
	}
	
	private void refreshTable() {
		tableSales.getItems().clear();
		sales.removeAll(sales);
		getAllSales();
		observableListSale.addAll(sales);
		tableSales.setItems(observableListSale);
	}
	
	private void getAllPayments() {
		payments = (List<Payment>) PDM.getAllPayement();
	}
	
	private void refreshTablePayment() {
		tablePayment.getItems().clear();
		payments.removeAll(payments);
		getAllPayments();
		observableListPayment.addAll(payments);
		tablePayment.setItems(observableListPayment);
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
		labelReglement.getStyleClass().add("custom-label");
		displayBtn.getStyleClass().add("custom-button");
		addBtn.getStyleClass().add("custom-button");
		backBtn.getStyleClass().add("custom-button");
//		clearBtn.getStyleClass().add("custom-button");
//		updateBtn.getStyleClass().add("custom-button");
		deleteBtn.getStyleClass().add("custom-button");
		vboxBtn.getStyleClass().add("custom-pane-left");
		labelSearch.getStyleClass().add("custom-label");
		labelSearchPayment.getStyleClass().add("custom-label");
		vboxTable.getStyleClass().add("custom-pane-center");
		vboxTablePayment.getStyleClass().add("custom-pane-center");
		labelPayment.getStyleClass().add("custom-label");
		labelSale.getStyleClass().add("custom-label");
	}
	
	private void setMarginElements() {
		hboxTitle.setMargin(backBtn, new Insets(20, 0, 20, 30));
		hboxTitle.setMargin(labelTitle, new Insets(20, 0, 20, 250));
		vboxTable.setMargin(tableSales, new Insets(20, 10, 0, 0));
		vboxTable.setMargin(textSearch, new Insets(0, 100, 0, 10));
		hboxSearch.setMargin(labelSearch, new Insets(0, 50, 0, 10));
		hboxSearch.setMargin(comboBoxSearch, new Insets(10, 0, 0, -17));
		vboxBtn.setMargin(labelReglement, new Insets(35, 0, 0, 0));
		vboxBtn.setMargin(comboBoxReglement, new Insets(20, 0, 0, 20));
		vboxBtn.setMargin(displayBtn, new Insets(50, 0, 0, 0));
		vboxBtn.setMargin(addBtn, new Insets(50, 0, 0, 0));
//		vboxBtn.setMargin(updateBtn, new Insets(50, 0, 0, 0));
		vboxBtn.setMargin(deleteBtn, new Insets(50, 0, 0, 0));
		vboxTablePayment.setMargin(tablePayment, new Insets(20, 5, 0, 5));
		vboxTablePayment.setMargin(textSearchPayment, new Insets(0, 10, 0, 10));
		hboxSearchPayment.setMargin(labelSearchPayment, new Insets(0, 10, 0, 10));
		hboxSearchPayment.setMargin(comboBoxSearchPayment, new Insets(10, 0, 0, 0));
		vboxTablePayment.setMargin(labelPayment, new Insets(0, 10, 0, 100));
		vboxTable.setMargin(labelSale, new Insets(0, 10, 0, 50));
		
	}
	
	private void resizeElements() {
		displayBtn.setPrefWidth(150);
		addBtn.setPrefWidth(150);
//		updateBtn.setPrefWidth(200);
		deleteBtn.setPrefWidth(150);
		comboBoxSearch.setPrefWidth(150);
	}
}

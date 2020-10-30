package shopmanagementsystem.PaymentManagement.Traite;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.jfoenix.controls.JFXTextField;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import shopmanagementsystem.Program;
import shopmanagementsystem.ItemManagement.Item;
import shopmanagementsystem.ItemManagement.ItemDaoImpl;
import shopmanagementsystem.PaymentManagement.PaymentView;
import shopmanagementsystem.saleManagement.Sale;

public class TraiteView {

	private BorderPane root = new BorderPane();
	private Scene scene = new Scene(root);
	private HBox hboxTop = new HBox();
	private Label labelEntete = new Label("Vente N° du");
	private Label labelClient = new Label();
	private Button backBtn = new Button("Précédent");
	private TableView<Item> tableItem = new TableView<Item>();
	private VBox vboxItem = new VBox();
	private TableView<Traite> tableTraite = new TableView<Traite>();
	private VBox vboxTraite = new VBox();
	private Label labelTotal =new Label("Total : ");
	private static Label labelPaye = new Label("Total payé : ");
	private static Label labelReste = new Label("Reste à payé : ");
	private HBox hboxButtom = new HBox();
	
	private List<Item> items = new ArrayList<Item>();
	private ObservableList<Item> observableListItem = FXCollections.observableArrayList();
	
	private List<Traite> traites = new ArrayList<Traite>();
	private ObservableList<Traite> observableListTraites = FXCollections.observableArrayList();
	
	private ItemDaoImpl IDI = new ItemDaoImpl();
	private static TraiteDaoImpl TDI = new TraiteDaoImpl();
	
	private static Sale sale;
	
	public static Traite currentTraite = null;
	
	private Button payerTraiteBtn = new Button("Payer Traite");
	
	private static double montantPaye = 0;
	private static double resteAPaye = 0;
	
	public BorderPane getPane() {
		return root;
	}
	
	public TraiteView(Sale sale) {
		
		this.sale = sale;
		setElements();
		addContent();
		setEvents();
		setCSS();
		setMarging();
		resizeElements();
		
	}
	
	private void setElements() {
		hboxTop.getChildren().add(backBtn);
		hboxTop.getChildren().add(labelEntete);
		hboxTop.getChildren().add(labelClient);
		setTableItem();
		vboxItem.getChildren().add(tableItem);
		vboxTraite.getChildren().add(payerTraiteBtn);
		setTableTraite();
		vboxTraite.getChildren().add(tableTraite);
		hboxButtom.getChildren().add(labelPaye);
		hboxButtom.getChildren().add(labelReste);
		hboxButtom.getChildren().add(labelTotal);
		labelEntete.setText("Vente N° " + sale.getIdSale() + " du " + sale.getDateSale());
		labelTotal.setText("Total : " + sale.getTotal());
	}
	
	private void addContent() {
		root.setTop(hboxTop);
		root.setCenter(vboxTraite);
		root.setRight(vboxItem);
		root.setBottom(hboxButtom);
	}
	
	private void resizeElements() {
		root.setPrefWidth(1000);
		root.setPrefHeight(663);
	}
	
	private void setTableItem() {
		TableColumn<Item, String> designationColumn = new TableColumn<Item, String>("Designation");
		designationColumn.setCellValueFactory(new PropertyValueFactory<Item,String>("DesignationProduct"));
		tableItem.getColumns().add(designationColumn);
		designationColumn.setPrefWidth(100);
		
		TableColumn<Item, String> prixColumn = new TableColumn<Item, String>("Prix unitaire");
		prixColumn.setCellValueFactory(new PropertyValueFactory<Item,String>("PrixVenteProduct"));
		tableItem.getColumns().add(prixColumn);
		prixColumn.setPrefWidth(100);
		
		TableColumn<Item, String> quantiteColumn = new TableColumn<Item, String>("Quantité");
		quantiteColumn.setCellValueFactory(new PropertyValueFactory<Item,String>("quantite"));
		tableItem.getColumns().add(quantiteColumn);
		quantiteColumn.setPrefWidth(100);
		
		TableColumn<Item, String> sousTotalColumn = new TableColumn<Item, String>("Sous-total");
		sousTotalColumn.setCellValueFactory(new PropertyValueFactory<Item,String>("sousTotal"));
		tableItem.getColumns().add(sousTotalColumn);
		sousTotalColumn.setPrefWidth(100);
		
		tableItem.setPrefWidth(400);
		tableItem.setPrefHeight(400);
		
		getAllItems();
		observableListItem.addAll(items);
		tableItem.setItems(observableListItem);
	}
	
	private void getAllItems() {
		items = (List<Item>) IDI.getAllItemsByIdSale(sale.getIdSale());
	}
	
	private void setTableTraite() {
		TableColumn<Traite, String> datePrevueColumn = new TableColumn<Traite, String>("Date prévue");
		datePrevueColumn.setCellValueFactory(new PropertyValueFactory<Traite, String>("datePrevue"));
		tableTraite.getColumns().add(datePrevueColumn);
		datePrevueColumn.setPrefWidth(100);
		
//		TableColumn<Traite, String> dateEffectiveColumn = new TableColumn<Traite, String>("Date éffective");
//		dateEffectiveColumn.setCellValueFactory(new PropertyValueFactory<Traite, String>("dateEffective"));
//		tableTraite.getColumns().add(dateEffectiveColumn);
//		dateEffectiveColumn.setPrefWidth(100);
		
		TableColumn<Traite, String> montantColumn = new TableColumn<Traite, String>("Montant");
		montantColumn.setCellValueFactory(new PropertyValueFactory<Traite, String>("montant"));
		tableTraite.getColumns().add(montantColumn);
		montantColumn.setPrefWidth(100);
		
//		TableColumn<Traite, String> typeColumn = new TableColumn<Traite, String>("type");
//		typeColumn.setCellValueFactory(new PropertyValueFactory<Traite, String>("type"));
//		tableTraite.getColumns().add(typeColumn);
//		typeColumn.setPrefWidth(100);
		
		TableColumn<Traite, String> numChequeColumn = new TableColumn<Traite, String>("Chèque N°");
		numChequeColumn.setCellValueFactory(new PropertyValueFactory<Traite, String>("numCheque"));
		tableTraite.getColumns().add(numChequeColumn);
		numChequeColumn.setPrefWidth(100);
		
		TableColumn<Traite, String> etatColumn = new TableColumn<Traite, String>("Etat");
		etatColumn.setCellValueFactory(new PropertyValueFactory<Traite, String>("etat"));
		tableTraite.getColumns().add(etatColumn);
		etatColumn.setPrefWidth(100);
		
		tableTraite.setPrefWidth(600);
		tableTraite.setPrefHeight(400);
		
		getAllTraites();
		observableListTraites.addAll(traites);
		tableTraite.setItems(observableListTraites);
	}
	
	private void refreshTableTraite() {
		traites.clear();
		observableListTraites.clear();
		tableTraite.getItems().clear();
		getAllTraites();
		observableListTraites.addAll(traites);
		tableTraite.setItems(observableListTraites);
	}
	
	private void getAllTraites() {
		traites =  (List<Traite>) TDI.getAllByIdSale(sale.getIdSale());
	}
	
	private void setEvents() {
		backBtn.setOnAction((event) -> {
			Program.back();
		});
		
		payerTraiteBtn.setOnAction((event) -> {
			payerTraite();
		});
	}
	
	private void setCSS() {
		hboxTop.getStyleClass().add("custom-pane-top");
		labelEntete.getStyleClass().add("custom-label");
		labelClient.getStyleClass().add("custom-label");
		labelPaye.getStyleClass().add("custom-label");
		labelReste.getStyleClass().add("custom-label");
		labelTotal.getStyleClass().add("custom-label");
		backBtn.getStyleClass().add("custom-button");
		hboxTop.getStyleClass().add("custom-pane-top");
		vboxTraite.getStyleClass().add("custom-pane-center");
		vboxItem.getStyleClass().add("custom-pane-center");
		hboxButtom.getStyleClass().add("custom-pane-top");	
		payerTraiteBtn.getStyleClass().add("custom-button");
	}
	
	private void setMarging() {
		hboxTop.setMargin(backBtn, new Insets(20, 0, 20, 30));
		hboxTop.setMargin(labelClient, new Insets(20, 0, 20, 250));
		vboxTraite.setMargin(tableTraite, new Insets(10, 20, 0, 30));
		vboxItem.setMargin(tableItem, new Insets(60, 30, 0, 20));
		hboxButtom.setMargin(labelTotal, new Insets(0, 0, 10, 150));
		hboxButtom.setMargin(labelPaye, new Insets(0, 0, 10, 40));
		hboxButtom.setMargin(labelReste, new Insets(0, 0, 10, 100));
		vboxTraite.setMargin(payerTraiteBtn, new Insets(10, 0, 0, 20));
		
	}
	
	public static void newPaymentWithTraite() {
		BorderPane root = new BorderPane();
		Scene scene = new Scene(root);
		scene.getStylesheets().add("style.css");
		Stage window = new Stage();
		window.initStyle(StageStyle.UNDECORATED);
		window.setScene(scene);
		GridPane grid = new GridPane();
		Label labelTraite = new Label("Nouvel paiement par traite");
		HBox hboxTitle = new HBox();
		hboxTitle.getChildren().add(labelTraite);
		Label nbreTraiteLabel = new Label("Nombre de traites");
		JFXTextField nbreTraiteText = new JFXTextField();
		Label montantTraiteLabel = new Label("Montant de traite");
		JFXTextField montantTraiteText = new JFXTextField();
		Button addBtn = new Button("Ajouter");
		Button annulerBtn = new Button("Annuler");
		grid.add(nbreTraiteLabel, 0, 0);
		grid.add(nbreTraiteText, 1, 0);
		grid.add(montantTraiteLabel, 0, 1);
		grid.add(montantTraiteText, 1, 1);
		grid.add(addBtn, 0, 2);
		grid.add(annulerBtn, 1, 2);
		
		root.setTop(hboxTitle);
		root.setCenter(grid);
		
		hboxTitle.getStyleClass().add("custom-pane-top");
		labelTraite.getStyleClass().add("custom-label");
		nbreTraiteLabel.getStyleClass().add("custom-label");
		montantTraiteLabel.getStyleClass().add("custom-label");
		addBtn.getStyleClass().add("custom-button");
		annulerBtn.getStyleClass().add("custom-button");
		grid.getStyleClass().add("custom-pane-center");
		
		grid.setMargin(annulerBtn, new Insets(20, 0, 20, 30));
		
		annulerBtn.setOnAction((event) -> {
			window.close();
		});
		
		addBtn.setOnAction((event) -> {
			int nbreTraite = Integer.parseInt(nbreTraiteText.getText());
			LocalDate futureDate = LocalDate.now();
			Date date = new Date(System.currentTimeMillis());
			for(int i = 0; i < nbreTraite; i++) {
				Traite traite = new Traite(date, date, Double.parseDouble(montantTraiteText.getText()), "0", "Non payee", PaymentView.currentSale);
				TDI.add(traite);
				futureDate = futureDate.plusMonths(1);
				date = date.valueOf(futureDate);
			}
			PaymentView pv = new PaymentView();
			pv.addPayment("Traites");
		});
		
		window.show();
	}
	
	public void payerTraite() {
		BorderPane root = new BorderPane();
		Scene scene = new Scene(root);
		scene.getStylesheets().add("style.css");
		Stage window = new Stage();
		window.initStyle(StageStyle.UNDECORATED);
		window.setScene(scene);
		GridPane grid = new GridPane();
		Label labelTraite = new Label("Paiement de la traite");
		HBox hboxTitle = new HBox();
		hboxTitle.getChildren().add(labelTraite);
		Label numChequeLabel = new Label("Numéro de chèque");
		JFXTextField numChequeText = new JFXTextField();
		Label montantTraiteLabel = new Label("Montant de traite");
		JFXTextField montantTraiteText = new JFXTextField();
		Button addBtn = new Button("Payer");
		Button annulerBtn = new Button("Annuler");
		grid.add(numChequeLabel, 0, 0);
		grid.add(numChequeText, 1, 0);
		grid.add(montantTraiteLabel, 0, 1);
		grid.add(montantTraiteText, 1, 1);
		grid.add(addBtn, 0, 2);
		grid.add(annulerBtn, 1, 2);
		
		root.setTop(hboxTitle);
		root.setCenter(grid);
		
		hboxTitle.getStyleClass().add("custom-pane-top");
		labelTraite.getStyleClass().add("custom-label");
		numChequeLabel.getStyleClass().add("custom-label");
		montantTraiteLabel.getStyleClass().add("custom-label");
		addBtn.getStyleClass().add("custom-button");
		annulerBtn.getStyleClass().add("custom-button");
		grid.getStyleClass().add("custom-pane-center");
		
		grid.setMargin(annulerBtn, new Insets(20, 0, 20, 30));
		
		annulerBtn.setOnAction((event) -> {
			window.close();
		});
		
		addBtn.setOnAction((event) -> {
			currentTraite = tableTraite.getSelectionModel().getSelectedItem();
			Traite traite = new Traite(currentTraite.getDatePrevue(), currentTraite.getDateEffective(), currentTraite.getMontant(), numChequeText.getText(), "Payee", currentTraite.getSale());
			TDI.update(traite, currentTraite.getIdTraite());
			refreshTableTraite();
		});
		
		window.show();
	}
	
	public static void calculer(Sale sale) {
		List<Traite> list = (List<Traite>) TDI.getAllByIdSale(sale.getIdSale());
		montantPaye = 0;
		resteAPaye = sale.getTotal();
		for(Traite traite : list) {
			if(traite.getEtat().equals("Payee")) {
				montantPaye += traite.getMontant();
				resteAPaye -= traite.getMontant();
			}
		}
		labelPaye.setText("Total payé : " + montantPaye);
		labelReste.setText("Reste à payé : " + resteAPaye);
	}

}

package shopmanagementsystem;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import shopmanagementsystem.PaymentManagement.PaymentView;
import shopmanagementsystem.categoryManagement.CategoryView;
import shopmanagementsystem.customerManagement.CustomerView;
import shopmanagementsystem.productManagement.ProductView;
import shopmanagementsystem.saleManagement.SaleView;

public class Dashbord {

	private BorderPane pane  = new BorderPane();
	
	private HBox hboxTitle = new HBox();
	private Label labelTitle = new Label("Systéme de Gestion du Magasin");
	private VBox vbox = new VBox();
	private Button productBtn = new Button("Produits");
	private Button categoryBtn = new Button("Categories");
	private Button customerBtn = new Button("Clients");
	private Button saleBtn = new Button("Ventes");
	private Button paymentBtn = new Button("Paiement");
	
	private ProductView pv = null;
	private CategoryView cv = null;
	private CustomerView cuv = null;
	private SaleView sv = null;
	private PaymentView pav = null;
	
	private void addContent() {
		hboxTitle.getChildren().add(labelTitle);
		vbox.getChildren().add(productBtn);
		vbox.getChildren().add(categoryBtn);
		vbox.getChildren().add(customerBtn);
		vbox.getChildren().add(saleBtn);
		vbox.getChildren().add(paymentBtn);
	}
	
	private void setEvents() {
		productBtn.setOnAction((event) -> {
			pv = new ProductView();
			Program.showViews(pv.getPane());
			});
		
		categoryBtn.setOnAction((event) -> {
			cv = new CategoryView();
			Program.showViews(cv.getPane());
		});
		
		customerBtn.setOnAction((event) -> {
			cuv = new CustomerView();
			Program.showViews(cuv.getPane());
		});
		
		saleBtn.setOnAction((event) -> {
			sv = new SaleView();
			Program.showViews(sv.getPane());
		});
		
		paymentBtn.setOnAction((event) -> {
			pav = new PaymentView();
			Program.showViews(pav.getPane());
		});
		
		}
	
	public Dashbord() {
		
		addContent();
		pane.setPrefWidth(1000);
		pane.setPrefHeight(700);
		pane.setTop(hboxTitle);
		pane.setLeft(vbox);
		setEvents();
		setCSS();
		setMargingElements();
		resizeElements();
		Image img = new Image("shop.jpg", 1000, 700, false, false);
		BackgroundSize bgSize = new BackgroundSize(1000, 700, false, false, false, false);
		Background bgImage = new Background(new BackgroundImage(img, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, bgSize));
		pane.setBackground(bgImage);
	}
	
	public BorderPane getPane() {
		return pane;
	}

	public void setPane(BorderPane pane) {
		this.pane = pane;
	}
	
	private void setCSS() {
		hboxTitle.getStyleClass().add("custom-pane-center");
		labelTitle.getStyleClass().add("custom-label");
		vbox.getStyleClass().add("custom-pane-top");
		productBtn.getStyleClass().add("custom-button");
		categoryBtn.getStyleClass().add("custom-button");
		customerBtn.getStyleClass().add("custom-button");
		saleBtn.getStyleClass().add("custom-button");
		paymentBtn.getStyleClass().add("custom-button");
	}
	
	private void setMargingElements() {
		vbox.setMargin(productBtn, new Insets(40, 0, 0, 0));
		vbox.setMargin(categoryBtn, new Insets(40, 0, 0, 0));
		vbox.setMargin(customerBtn, new Insets(40, 0, 0, 0));
		vbox.setMargin(saleBtn, new Insets(40, 0, 0, 0));
		vbox.setMargin(paymentBtn, new Insets(40, 0, 0, 0));
		hboxTitle.setMargin(labelTitle, new Insets(30, 0, 30, 300));
	}
	
	private void resizeElements() {
		productBtn.setPrefWidth(200);
		categoryBtn.setPrefWidth(200);
		customerBtn.setPrefWidth(200);
		saleBtn.setPrefWidth(200);
		paymentBtn.setPrefWidth(200);
	}
	
}

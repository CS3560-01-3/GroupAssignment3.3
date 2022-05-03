package dBs;

import java.time.LocalDate;

import application.DBFunc;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Offering class
 *
 */
public class Offering {
	int productID;
	int supplierID;
	Double supplierPrice;

	public Offering(String productName, String supplierName, Double price) {
		this.productID = DBFunc.nameToIdDB(productName, "product");
		this.supplierID = DBFunc.nameToIdDB(supplierName, "supplier");
		this.supplierPrice = price;
	}

	public Offering(String productName, String supplierName) {
		this.productID = DBFunc.nameToIdDB(productName, "product");
		this.supplierID = DBFunc.nameToIdDB(supplierName, "supplier");
	}

	public static void register(Stage primaryStage, Scene prev) {
		VBox regInputs = new VBox();
		Label pagePrompt = new Label("Enter the product name, supplier and price below");
		Button back = new Button("Back");
		back.setOnAction(e -> primaryStage.setScene(prev));
		TextField productName = new TextField("Product Name");
		TextField supplier = new TextField("Supplier");
		TextField price = new TextField("0");
		// TODO
		Button submit = new Button("submit");
		submit.setOnAction(e -> {
			try {
				if (!DBFunc.verifyDB(productName.getText(), "product")
						&& !DBFunc.verifyDB(supplier.getText(), "supplier")) {
					Offering a = new Offering(productName.getText(), supplier.getText(),
							Double.parseDouble(price.getText()));
					a.writeTo();
				}

			} catch (NumberFormatException e1) {
				System.out.println("Not a number");
			}

			primaryStage.setScene(prev);
		});

		regInputs.getChildren().addAll(pagePrompt, productName, supplier, price, submit, back);
		Scene register1 = new Scene(regInputs, 300, 250);
		primaryStage.setScene(register1);
	}

	public static void deregister(Stage primaryStage, Scene prev) {
		VBox regInputs = new VBox();
		Label pagePrompt = new Label("Enter the offering ID below");
		Button back = new Button("Back");
		back.setOnAction(e -> primaryStage.setScene(prev));
		TextField productName = new TextField("Product Name");
		TextField supplier = new TextField("Supplier");
		Button submit = new Button("submit");
		submit.setOnAction(e -> {
			Offering a = new Offering(productName.getText(), supplier.getText());
			a.remove();
			primaryStage.setScene(prev);
		});

		regInputs.getChildren().addAll(pagePrompt, productName, supplier, submit, back);
		Scene register1 = new Scene(regInputs, 300, 250);
		primaryStage.setScene(register1);
	}

	public void writeTo() {

		String sql = "insert into offering " + "values(" + productID + "," + supplierID + "," + supplierPrice + ")";
		DBFunc.execDB(sql);

	}

	public void remove() {
		String sql = "delete from offering where productID = " + productID + " and " + "supplierID= " + supplierID;
		DBFunc.execDB(sql);

	}
}

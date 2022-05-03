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
 * Order class
 *
 */
public class Order {
	int orderID;
	int productID;
	int supplierID;
	int quantity;
	String datePlaced;
	String dateReceived;

	public Order(int productID, int supplierID, int quantity, String datePlaced) {
		this.orderID = DBFunc.getLastDB("orders");
		this.productID = productID;
		this.supplierID = supplierID;
		this.quantity = quantity;
		this.datePlaced = datePlaced;
	}
	private Order(int orderID) {
		this.orderID = orderID;
		this.quantity=DBFunc.getIntDB(orderID,"orders","quantity");
		this.productID=DBFunc.getIntDB(orderID,"orders","productID");
	}
	/**
	 * When the product is shipped, change the information regarding the shipping
	 * status
	 * 
	 * @param productID of the product
	 */
	public static void recieved(Stage primaryStage, Scene prev) {
		VBox recieveInputs = new VBox();
		Label pagePrompt = new Label("Enter the Order ID below");

		TextField targetID = new TextField();

		// will mark order as received and increment product quantity
		Button submit = new Button("Submit");
		submit.setOnAction(e -> {

			try {
				if ((!DBFunc.verifyIdDB(targetID.getText(), "orders"))
						&& (DBFunc.getStrDB(Integer.parseInt(targetID.getText()), "orders", "dateReceived").equals("null"))) { 
					
					Order a = new Order(Integer.parseInt(targetID.getText()));
					a.update();
					
					primaryStage.setScene(prev);
				} else {
					Label DNE = new Label("Invalid ID (not a number or received order)");
					recieveInputs.getChildren().add(DNE);
				}

			} catch (NumberFormatException e1) {
				System.out.println("Not a number");
			}

		});

		// will go to previous scene, exiting this use case
		Button back = new Button("Back");
		back.setOnAction(e -> {
			primaryStage.setScene(prev);
		});

		// present GUI
		recieveInputs.getChildren().addAll(pagePrompt, targetID, submit, back);
		Scene register1 = new Scene(recieveInputs, 300, 250);
		primaryStage.setScene(register1);

	}

	/**
	 * When the inventory stock is low, the System orders more products to ensure it
	 * remains in stock.
	 * 
	 * @param productID of the product
	 */
	public static void placeOrder(Stage primaryStage, Scene prev) {

		VBox placeInputs = new VBox();
		Label pagePrompt = new Label("Enter the product name, supplier, and quantity below");

		Button back = new Button("Back");
		back.setOnAction(e -> primaryStage.setScene(prev));

		TextField productName = new TextField("Product Name");
		TextField supplier = new TextField("Supplier");
		TextField quantity = new TextField("Quantity");

		Button submit = new Button("Submit");
		submit.setOnAction(e -> {
			// TODO
			try {
				if (!DBFunc.verifyDB(productName.getText(), "product")
						&& !DBFunc.verifyDB(supplier.getText(), "supplier")) {
					Order a = new Order(DBFunc.nameToIdDB(productName.getText(), "product"),
							DBFunc.nameToIdDB(supplier.getText(), "supplier"), Integer.parseInt(quantity.getText()),
							"" + LocalDate.now());
					a.writeTo();
				}

			} catch (NumberFormatException e1) {
				System.out.println("Not a number");
			}

			// once text boxes have been verified:

			primaryStage.setScene(prev);
		});

		placeInputs.getChildren().addAll(pagePrompt, productName, supplier, quantity, submit, back);
		Scene register1 = new Scene(placeInputs, 300, 250);
		primaryStage.setScene(register1);

	}
	
	public void writeTo() {
		String sql = "insert into orders " + "values(" + orderID + "," + productID + "," + supplierID + "," + quantity
				+ ",'" + datePlaced + "'," + "'null'" + ")";
		DBFunc.execDB(sql);
	}

	public void update() {
		String sql = "update orders set dateReceived = '" + LocalDate.now() + "' where ordersID = " + orderID;
		DBFunc.execDB(sql);
		sql = "update product set quantity = " + (DBFunc.getIntDB(productID, "product", "quantity") + quantity)
				+ " where productID = " + productID;
		DBFunc.execDB(sql);
	}
}
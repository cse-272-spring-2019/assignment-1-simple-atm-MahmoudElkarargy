package pkg1;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Transaction {
	private String savedPass = "1234"; //The system password.
	private int wrongPassNumb = 0;
	private static int ArrayIndex = 0, j = 0, HoldI, max = -1;
	private static boolean zeroEntery = true, isNext = false, isPerv = false, MAX = false;
	private static double[] array = new double[5];

	// Checking password.
	public int checkPass(String pass) {
		if (OnlyNumbers(pass)) { //check if any letter is entered.
			if (pass.length() == 4) { // Most of ATM machines's password must be 4.
				if (pass.contentEquals(savedPass)) {
					AlertBox("Welcome", "Right Password", 0);
					return 0;
				} else {
					AlertBox("WRONG!!", "Wrong Password", 1);
					wrongPassNumb++;
				}
			} else {
				AlertBox("WRONG!!", "Enter 4 digit numbers", 1);
				wrongPassNumb++;
			}
		} else {
			AlertBox("WRONG!!", "Only numbers are acceptable", 1);
			wrongPassNumb++;
		}
		if (wrongPassNumb == 3) {
			AlertBox("GOODBYE!!", "System is closing!", 1);
			return 3;
		} else
			return 1;
	}

	// Checking for transaction amount to be positive number
	public double transctionEvent(String amount, String message, double balanceAmount) {
		if (!OnlyNumbers(amount)) {
			AlertBox("Failed", "Enter proper amount", 1);
			return 0;
		}
		if (Double.valueOf(amount) > 0.0) { //make sure the entered value bigger than zero.
			if (message.contentEquals("Deposit")) { //check whether it's Deposit or Withdrawal.
				AlertBox("Success", "Your Transiction is completed", 0);
				update(ArrayIndex++, Double.valueOf(amount)); //Save the transaction in the array with it's value.
				return Double.valueOf(amount);
			} else {
				if (Double.valueOf(amount) > 0 && balanceAmount >= Double.valueOf(amount)) { //check the entered value 
					// bigger than zero and bigger than or equal balance.
					AlertBox("Success", "Your Transiction is completed", 0);
					update(ArrayIndex++, -1 * Double.valueOf(amount)); //Save the transaction in the array 
					//with it's negative value.
					return Double.valueOf(amount);
				} else if (Double.valueOf(amount) >= 0.0) {
					AlertBox("WRONG!!", "Your Transaction is failed!", 1);
				}
			}
		}
		else
			AlertBox("Failed", "Enter proper amount", 1);
		return 0;
	}
	

	// Update the index of the array.
	private static void update(int i, double value) {
		zeroEntery = false; //to handle using next or previous buttons before making any transaction.
		isNext = false; //boolean for both using next and previous buttons.
		isPerv = false;
		if (i > 4) { //Max of array, then shift the array.
			MAX = true;
			i = 0;
			for (j = 0; j < 4; j++, i++) //Shifting
				array[j] = array[i + 1];
			i = 4;
		} else
			max++; //Integer to know the max number of transaction used if it's smaller than 5.
		if (MAX) { //if true, then you did 5 or more transactions.
			max = 4;
		}
		array[i] = value; //Adding the value in the array.
		HoldI = i; //Save the index of the array.
	}

	//Show next history.
	public void showNext(Label message) {
		if (!zeroEntery) { //to handle if you didn't made any transaction yet.
			if (isNext) { //if you used next button before then ..
				if (HoldI < max)  
					HoldI++;
				//First, make sure you didn't reach the max of array.. 
				//Example, if you made only 3 transactions then max will be 2
				//HoldI will start with 2 and won't increase untill you press previous button
				//and it won't never reach 3 "max of array"
				else
					HoldI = max;
			}
			print(message);
			isNext = true; //become true bec. you pressed next button.
		} else
			message.setText("You haven't done any transaction yet.");
	}
	
	
	//Show prev History.
	public void showPerv(Label message) {
		if (!zeroEntery) { //Explained before.
			if (isPerv) {
				if (HoldI != 0) { //Here, we checked the array from the other side and make sure min nb is zero
					HoldI--;
					isNext = true; //If you presed prev then you can re-used enter as the index dec. so you have next index avaliabe to see.
				}
			}
			print(message);
			isPerv = true;
		} else
			message.setText("You haven't done any transaction yet.");
	}

	//Print the balance.
	private static void print(Label message) {
		if (array[HoldI] > 0) { //If value bigger than zero then it's Deposit.
			message.setText("Nb(" + (HoldI + 1) + "): You Deposited " + array[HoldI] + "$");
		} else if (array[HoldI] < 0) {
			message.setText("Nb(" + (HoldI + 1) + "): You Withdraw " + (-1 * array[HoldI]) + "$");
		}

	}

	private static boolean OnlyNumbers(String Text) { //Handling entering numbers only. 
		try {
			double pass = Double.parseDouble(Text);
			if (pass >= 0)
				return true;
			else
				return false;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
	
	// Alert box.
	//the int type is only to change the background color of GUI.
	//Green for Success and Red for errors :D
	private static void AlertBox(String title, String message, int type) {
		Stage alertWindow = new Stage();
		alertWindow.initModality(Modality.APPLICATION_MODAL);
		alertWindow.setTitle(title);
		Pane root = new Pane();
		Label label = new Label(message);
		label.setStyle("-fx-text-fill: #000;-fx-font-size: 20px");
		Button closeButton = new Button("Close");

		if (type == 1) {
			root.setStyle("-fx-background-color:#DC143C");
			closeButton.setStyle("-fx-background-color: #3CB371;-fx-text-fill:#FFF;" + "-fx-background-radius: 6");

		} else {
			root.setStyle("-fx-background-color:#3CB371");
			closeButton.setStyle("-fx-background-color: #DC143C;-fx-text-fill:#FFF;" + "-fx-background-radius: 6");

		}
		closeButton.setOnAction(e -> alertWindow.close());

		VBox layout = new VBox(20);
		layout.getChildren().addAll(label, closeButton);
		layout.setAlignment(Pos.CENTER);
		layout.setPadding(new Insets(20, 20, 20, 20));

		root.getChildren().add(layout);
		Scene scene = new Scene(root, 320, 120);
		alertWindow.setScene(scene);
		alertWindow.showAndWait();
	}

}

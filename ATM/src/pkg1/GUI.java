/*
 * 		Mahmoud Ahmed Elkarargy.
 * 			   ID: 5462.
 */

/* this code is divided into 3 classes.
 * GUI class, MyAtm and Transaction.
 * This class, Contain only GUI functions, no calculations!
 * Where, you can take the Transaction class and it will work on any GUI.
 */

package pkg1;

import javafx.application.Application;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.control.TextField;

public class GUI extends Application {
	MyATM myATM = new MyATM();  //Creating object from the interface.
	
	Stage MainWindow; 
	Scene mainScene, withdrawScene, depositScene, loginScene;
	Button check, withdrawal, deposit, balanceInquiry, previous, next; 
	Label cardNumber, password, balance;
	GridPane passwordLayout, mainLayout;
	TextField cardNumberText;
	PasswordField passwordText;
	Pane root,root1;
	String pass = "1234", passIn;
	private double balanceAmount=0,newbalance;
	private int numbersOfWrongPass=0;
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		MainWindow = primaryStage; //Renaming the stage to be more easier in use.
		MainWindow.setTitle("ATM");
		root    = new Pane();
		root1   = new Pane();
		root.setStyle("-fx-background-color:#000;-fx-border-color: #20B2AA;-fx-border-width: 4px;"
				+ "-fx-border-style: solid;"); //Setting the style of loginScene
		passwordLayout = new GridPane();
		passwordLayout.setPadding(new Insets(20,20,20,20)); //Setting gaps in corners
		passwordLayout.setVgap(8);
		passwordLayout.setHgap(10);
		
		
		
		//Buttons, Labels and Text feilds of login Scene. 
		
		//Card Number label and TextField.
		cardNumber = new Label("Card Number: ");
		GridPane.setConstraints(cardNumber, 0,0);
		cardNumber.setStyle("-fx-text-fill: #20B2AA;-fx-font-size: 20px");
		cardNumberText = new TextField("3246 8790 6520 7655");
		GridPane.setConstraints(cardNumberText, 1,0);
		cardNumberText.setStyle("-fx-background-color: #20B2AA;-fx-border-color: #000;"
				+ "-fx-text-fill: #000; -fx-font-size: 12px;-fx-border-width: 2px");
		//Password label and TextField.
		password = new Label("Password:");
		GridPane.setConstraints(password, 0,1);
		password.setStyle("-fx-text-fill: #20B2AA;-fx-font-size: 20px;");
		passwordText = new PasswordField();
		passwordText.setPromptText("Password"); //to be hidden not like card number.
		GridPane.setConstraints(passwordText, 1,1);
		passwordText.setStyle("-fx-background-color: #20B2AA;-fx-border-color: #000;"
				+ "-fx-Text-fill: #000; -fx-font-size: 12px;-fx-border-width: 2px");
		
		//Check button.
		check = new Button("Check");
		GridPane.setConstraints(check, 1,2);
		check.setStyle("-fx-background-color: #DC143C;-fx-text-fill:#FFFFFF;"
				+ "-fx-background-radius: 6");
		check.setMaxSize(200, 50);
		
		//First Scene - login Scene.
		passwordLayout.getChildren().addAll(password,check,passwordText,cardNumber,cardNumberText);
		root.getChildren().add(passwordLayout);
		loginScene = new Scene(root,350,150);
		
		//Second layout - mainlayout
		mainLayout = new GridPane();
		mainLayout.setPadding(new Insets(30,30,30,30));
		mainLayout.setVgap(12);
		mainLayout.setHgap(18);
		
		//Buttons and Labels.
		withdrawal = new Button("withdrawal");
		GridPane.setConstraints(withdrawal, 0,0);
		deposit = new Button("deposit");
		GridPane.setConstraints(deposit, 0,2);
		balanceInquiry = new Button("balance In quiry");
		GridPane.setConstraints(balanceInquiry, 0,4);
		next = new Button("next");
		GridPane.setConstraints(next, 9,7);
		previous = new Button("previous");
		GridPane.setConstraints(previous, 8,7);
		balance = new Label();
		GridPane.setConstraints(balance, 4,2);
		
		//Buttons and Labels styles.
		balance.setStyle("-fx-text-fill: #20B2AA;-fx-font-size: 20px");
		withdrawal.setStyle("-fx-background-color: #20B2AA;-fx-text-fill:#000;"
				+ "-fx-background-radius: 6;-fx-font-size: 15px;");
		deposit.setStyle("-fx-background-color: #20B2AA;-fx-text-fill:#000;"
				+ "-fx-background-radius: 6;-fx-font-size: 15px;");
		balanceInquiry.setStyle("-fx-background-color: #20B2AA;-fx-text-fill:#000;"
				+ "-fx-background-radius: 6;-fx-font-size: 15px;");
		next.setStyle("-fx-background-color: #3CB371;-fx-text-fill:#FFEBCD;"
				+ "-fx-background-radius: 6;-fx-font-size: 15px;");
		previous.setStyle("-fx-background-color: #DC143C;-fx-text-fill:#FFEBCD;"
				+ "-fx-background-radius: 6;-fx-font-size: 15px;");
		
		
		//Second Scene - main Scene.
		root1.setStyle("-fx-background-color:#000;-fx-border-color: #20B2AA;-fx-border-width: 4px;"
				+ "-fx-border-style: solid;");
		mainLayout.getChildren().addAll(withdrawal,deposit,balanceInquiry,next,previous,balance);
		root1.getChildren().add(mainLayout);
		mainScene = new Scene(root1,850,250);
		
		
		
		//Check button selected
		check.setOnAction(e->{
			numbersOfWrongPass = myATM.checkPassword(passwordText.getText());
			if(numbersOfWrongPass==0) {
				primaryStage.setScene(mainScene);
			}
			else if(numbersOfWrongPass==3){
				MainWindow.close();
			}
		});
		
		//balance check button
		balanceInquiry.setOnAction(e->{
			balance.setText("Your Balance is: "+Double.toString(balanceAmount)+"$");
		});
		
		//deposit button clicked.
		deposit.setOnAction(e->{			
			deposit("Deposit");
		});
		
		//withdrawal button clicked.
		withdrawal.setOnAction(e->{
			deposit("Withdraw");
		});
		
		//next button clicked.
		next.setOnAction(e->{
			myATM.next(balance);
		});
		
		//previous button clicked.
		previous.setOnAction(e->{
			myATM.prev(balance);
		});
		
		primaryStage.setScene(loginScene);
		primaryStage.show();
	}
	
	private  void deposit(String message) {
		//Creating new stage and scene for deposit and withdraw scene.
		Stage Window = new Stage();
		Window.initModality(Modality.APPLICATION_MODAL);
		Window.setTitle(message);
		Pane root   = new Pane();
		root.setStyle("-fx-background-color:#20B2AA;-fx-border-color: #000;-fx-border-width: 4px;"
				+ "-fx-border-style: solid;");
		GridPane layout = new GridPane();
		layout.setPadding(new Insets(10,10,10,10));
		layout.setVgap(8);
		layout.setHgap(10);
		
		Label label = new Label("Enter Amount");
		label.setStyle("-fx-font-size: 20px");
		GridPane.setConstraints(label, 0,0);		
		TextField text = new TextField();
		text.setPromptText("ex: 300,025");
		GridPane.setConstraints(text, 1,0);		
		Button doneButton = new Button("Done");
		GridPane.setConstraints(doneButton, 1,2);		
		
		Button cancelButton = new Button("Cancel");
		GridPane.setConstraints(cancelButton, 2,2);		
		
		//Cancel button pressed.
		cancelButton.setOnAction(e-> {
			Window.close();
		});
		
		//Done button pressed.
		doneButton.setOnAction(e->{
			if(message.contentEquals("Deposit")) //Check if it's deposit or withdraw.
				balanceAmount += myATM.deposit(text.getText(),balanceAmount); //Add
			else
				balanceAmount -= myATM.withdraw(text.getText(),balanceAmount); //Substitute
			
			balance.setText("Your New Balance is: "+balanceAmount+"$");
			Window.close();
		});
		doneButton.setStyle("-fx-background-color: #3CB371;-fx-text-fill:#FFEBCD;"
				+ "-fx-background-radius: 6;-fx-font-size: 15px;");
		cancelButton.setStyle("-fx-background-color: #DC143C;-fx-text-fill:#FFEBCD;"
				+ "-fx-background-radius: 6;-fx-font-size: 15px;");
		text.setStyle("-fx-Text-fill: #000;-fx-font-size: 15px");
		layout.getChildren().addAll(doneButton,cancelButton,label,text);
		root.getChildren().add(layout);
		Scene scene = new Scene(root,450,100);
		Window.setScene(scene);
		Window.showAndWait();
		
	}
}
package pkg1;

import javafx.scene.control.Label;

interface ATM {
	//Returns the current balance in string format
	public String getCurrentBalance();
	//withdraws from current balance and updates it
	public double withdraw(String amount,double balance);
	//adds to the current balance and updates it
	public double deposit(String amount,double balance);
	//returns the prev transaction in String format, or Null if no more history
	public void prev(Label balance);
	//returns the next transaction in String format, or Null if no more history
	public void next(Label balance); 
	//returns boolean of the password entered
	public int checkPassword(String pass);
}

public class MyATM implements ATM{
	Transaction transcation = new Transaction();
	private int wrongPassNumb=0;
	@Override
	public String getCurrentBalance() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double withdraw(String amount,double balance) {
		// TODO Auto-generated method stub
		return transcation.transctionEvent(amount,"Withdraw",balance);
	}

	@Override
	public double deposit(String amount,double balance) {
		return transcation.transctionEvent(amount,"Deposit",balance);
		
	}

	@Override
	public void prev(Label balance) {
		transcation.showPerv(balance);	
	}
	
	@Override
	public void next(Label balance) {
		transcation.showNext(balance);	
	}

	@Override
	public int checkPassword(String pass) {
		wrongPassNumb = transcation.checkPass(pass);
		if(wrongPassNumb==0) {
			return 0;
		}
		else if(wrongPassNumb==3)
			return 3;
		else
			return 1;
	}
}

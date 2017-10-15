import javax.swing.*;


/**
 * @author Abdul Rehman
 *
 */
public class Button extends JButton{
	private int num;
	private boolean toggle;

	/**
	 * constructors to initialize the buttons by type
	 */
	
	//initial board positions
	public Button(String text) {
		super (text);
	}
	//board buttons
	public Button (String text, int n){
		super (text);
		this.num = n;
	}
	//toggle switches
	public Button (String text, int n, boolean t){
		super (text);
		this.toggle = t;
		this.num = n;
	}
	/**
	 * sets the number of the button pushed
	 */
	public void setNum(int n){
		this.num = n;
	}
	/**
	 * gets the value of the button
	 */
	public int getNum(){
		return this.num;
	}
	/**
	 * sets the button to off or on 
	 * depending in its current status
	 */
	public void toggleSwitch(){
		if (this.toggle){
			this.toggle = false;
		}else{
			this.toggle = true;
		}
	}
	/**
	 * gets the status of the button 
	 * (if it is pressed or not)
	 */
	public boolean getToggle(){
		return this.toggle;
	}
}

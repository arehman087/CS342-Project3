import javax.swing.*;


/**
 * @author Abdul Rehman
 *
 */
public class Button extends JButton{
	private int num;
	private int row;
	private int col;
	private boolean toggle;
	
	/**
	 * Constructors to initialize the buttons by type
	 */
	
	//initial board positions
	public Button(String text) {
		super (text);
	}
	//board buttons
	public Button (String text, int n, int posR, int posC){
		super (text);
		this.num = n;
		this.row = posR;
		this.col = posC;
	}
	//toggle switches
	public Button (String text, int n, boolean t){
		super (text);
		this.toggle = t;
		this.num = n;
	}
	/**
	 * Sets the number of the button pushed
	 * @param n number that is set to the button
	 */
	public void setNum(int n){
		this.num = n;
	}
	/**
	 * @return gets the value of the button
	 */
	public int getNum(){
		return this.num;
	}
	/**
	 * Sets the button to off or on 
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
	 * @return gets the status of the button 
	 * (if it is pressed or not)
	 */
	public boolean getToggle(){
		return this.toggle;
	}
	/**
	 * @return gets button row
	 **/
	public int getButtonRow(){
		return this.row;
	}
	/**
	 * @return gets button col
	 **/
	public int getButtonCol(){
		return this.col;
	}
}

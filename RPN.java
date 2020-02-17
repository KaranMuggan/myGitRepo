import java.util.Scanner;

class RPN {
	private String command;
	private StackNode top;
	
	public RPN(String command) {
		top = null;
		this.command = command;
	}
	
	private void into(double new_data) {
		StackNode new_node = new StackNode(new_data, top);
		top = new_node;
	}

	private double outof() {
		double top_data = top.getData();
		top = top.getUnderneath();
		return top_data;
	}

	class StackNode {
		private StackNode underneath;
		private double data;
		
		public StackNode(double data, StackNode underneath) {
			this.setData(data);
			this.setUnderneath(underneath);
		}
		private void setData(double data) {
			this.data = data;
		}
		private void setUnderneath(StackNode underneath) {
			this.underneath = underneath;
		}
		public double getData() { 							// return data
			return data;
		}
		public StackNode getUnderneath() { 					// return underneath
			return underneath;
		}
	}
	
	public double getResult() {
		calculation(); 										// performing calculation
		double resultVal = outof();

		if (top != null) {
			throw new IllegalArgumentException();
		}

		return resultVal;
	}

	private void calculation() {
		double stackNum1, stackNum2;
		int j;

		for (int i = 0; i < command.length(); i++) {				
			if (Character.isDigit(command.charAt(i))) {		// if it's a digit
						String temp = "";					// get a string of the number
				for (j = 0; (j < 100)
						&& (Character.isDigit(command.charAt(i)) || (command.charAt(i) == '.')); j++, i++) {
					temp = temp + String.valueOf(command.charAt(i));
				}
				double number = Double.parseDouble(temp);	// convert to double and add to the stack
				into(number);
			//else statement to determine which operator is present and which calculation to do	
			} else if (command.charAt(i) == '+') {			
				stackNum1 = outof();
				stackNum2 = outof();
				into(stackNum2 + stackNum1);
			} else if (command.charAt(i) == '-') {
				stackNum1 = outof();
				stackNum2 = outof();
				into(stackNum2 - stackNum1);
			} else if (command.charAt(i) == '*') {
				stackNum1 = outof();
				stackNum2 = outof();
				into(stackNum2 * stackNum1);
			} else if (command.charAt(i) == '/') {
				stackNum1 = outof();
				stackNum2 = outof();
				into(stackNum2 / stackNum1);
			} else if (command.charAt(i) == '^') {
				stackNum1 = outof();
				stackNum2 = outof();
				into(Math.pow(stackNum2, stackNum1));
			} else if (command.charAt(i) != ' ') {
				throw new IllegalArgumentException();
			}
		}
	}
																	
	public static void main(String args[]) {							//main method
		while (true) {
			Scanner input = new Scanner(System.in);						//scanner introduced to recieve user input
			System.out.println("Enter RPN expression or \"quit\".");	
			String line = input.nextLine();								//scanner reads in users input
			if (line.equals("quit")) {									//if input is quit, program is closed
				break;													
			} else {
				RPN calc = new RPN(line);								//else the calculation is done
				System.out.printf("Answer is %f\n", calc.getResult());
			input.close();
			}
		}
	}
}
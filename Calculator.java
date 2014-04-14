import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.util.regex.*;
import java.util.*;
public class Calculator
{
	JFrame window;
	// stuff for top panel
	JPanel topPanel;
	JTextField expr,result;
	JButton equals;

	// stuff for bottom panel

	JPanel bottomPanel,digitsPanel,opsPanel;
	JButton[] digits,ops;
	JButton clear, clearEntry;
	Container content;
	Listener listener;

	public Calculator()
	{
		listener = new Listener(); // our Listener class implements ActionListener
		window= new JFrame("GUI Calc");
		content=window.getContentPane();
		content.setLayout( new GridLayout(2,1) );
		topPanel=new JPanel();
		topPanel.setLayout( new GridLayout(1,3) );

		// TOP PANEL WORK

		expr = new JTextField( );
		equals = new JButton("=");
		equals.addActionListener( listener );
		result = new JTextField( );

		topPanel.add( expr );
		topPanel.add( equals );
		topPanel.add( result );

		// BOTTOM PANEL WORK

		bottomPanel = new JPanel();
		bottomPanel.setLayout( new GridLayout(1,2) );

		digitsPanel = new JPanel();
		digitsPanel.setLayout( new GridLayout(4,3) );

		opsPanel = new JPanel();
		opsPanel.setLayout( new GridLayout(4,1) );

		digits  = new JButton[12];
		ops = new JButton[4];

		for (int i=0 ; i<10 ; i++)
		{
			digits[i] = new JButton( i + "");
			digits[i].addActionListener(listener);
			digitsPanel.add( digits[i] );
		}

		clear = new JButton( "C" );
		clearEntry = new JButton( "CE" );
		clear.addActionListener(listener);
		clearEntry.addActionListener(listener);
		digitsPanel.add( clear );
		digitsPanel.add( clearEntry);

		String[] oplabels = { "+", "-", "/", "*" };
		for (int i=0 ; i<4 ; i++)
		{
			ops[i] = new JButton( oplabels[i] ) ;
			ops[i].addActionListener(listener);
			opsPanel.add( ops[i] );
		}

		bottomPanel.add( digitsPanel );
		bottomPanel.add( opsPanel );


		content.add( topPanel);
		content.add( bottomPanel);

		window.setVisible(true);
	}
	class Listener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			Component clicked = (Component) e.getSource();
			if ( clicked == equals )
			{
				result.setText( evaluate( expr.getText() ) );
				return;
			}
			for ( int i=0 ; i<10 ; i++)
			{
	
				if ( clicked == digits[i] )
				{
					expr.setText( expr.getText() + Integer.toString(i));
					return;
				}
			}
			if ( clicked == clear )
			{
				// do something
			 	expr.setText("");
			 	return;
			}
			if ( clicked == clearEntry )
			{
			// do something
			String exp = expr.getText();
			String erasedString = "";
			String operandAndOperator = "";
			String[] rands = exp.split("[*/+-]");
			String[] rators = exp.split("\\d+");
			ArrayList<String> operands = new ArrayList<String>( Arrays.asList(rands) );
			ArrayList<String> operators = new ArrayList<String>( Arrays.asList(rators) );
			operators.remove( 0 );
			operands.remove(operands.size()-1);  
			for (int i = 0; i < operators.size();i++){
				operandAndOperator = operands.get(i) + operators.get(i);
				erasedString += operandAndOperator;
			}

			expr.setText(erasedString);
			return;

			}
			for ( int i=0 ; i<4 ; i++ )
			{
				String[] oplabels = { "+", "-", "/", "*" };
				if (clicked == ops[i]){
					expr.setText( expr.getText() + oplabels[i]);
					return;
				}
				// tack on that operator to the expr string
			}

		}
		String evaluate( String exp )
		{
			

			ArrayList<Double> operands = new ArrayList<Double>();
			Pattern myPattern = Pattern.compile("\\d*\\.\\d+|\\d+");
			Matcher myMatcher = myPattern.matcher(exp);
			while (myMatcher.find())
			    operands.add ( Double.parseDouble( myMatcher.group() ) );
			
			ArrayList<String> operators = new ArrayList<String>();
			myPattern = Pattern.compile("[*/+-]");
			myMatcher = myPattern.matcher(exp);
			while (myMatcher.find())
			    operators.add ( myMatcher.group() );

			double sum,firstOperand,secondOperand;

				int indexOfMultiply, indexOfDivide,indexOfSubtraction,indexOfAddition = 0;
				while (operators.contains("/") || operators.contains("*") && operators.size()>0){
					if(operators.contains("/")==true){	
						indexOfDivide = operators.indexOf("/");
						firstOperand = operands.get(indexOfDivide);
						secondOperand = operands.get(indexOfDivide+1);
						sum = firstOperand / secondOperand;
						operators.remove(indexOfDivide);
						operands.remove(indexOfDivide+1);
						operands.set(indexOfDivide,sum);

					}
					if (operators.contains("*")==true) {

						indexOfMultiply = operators.indexOf("*");
						firstOperand = operands.get(indexOfMultiply);
						secondOperand = operands.get(indexOfMultiply+1);
						sum = firstOperand * secondOperand;
						operators.remove(indexOfMultiply);
						operands.remove(indexOfMultiply+1);
						operands.set(indexOfMultiply,sum);

				}
			}
			
			while (operators.contains("+") || operators.contains("-") && operators.size()>0){
					
					if (operators.contains("-")==true) {

						indexOfSubtraction = operators.indexOf("-");
						firstOperand = operands.get(indexOfSubtraction);
						secondOperand = operands.get(indexOfSubtraction+1);
						sum = firstOperand - secondOperand;
						operators.remove(indexOfSubtraction);
						operands.remove(indexOfSubtraction+1);
						operands.set(indexOfSubtraction,sum);

				}
				if(operators.contains("+")==true){	
						indexOfAddition = operators.indexOf("+");
						firstOperand = operands.get(indexOfAddition);
						secondOperand = operands.get(indexOfAddition+1);
						sum = firstOperand + secondOperand;
						operators.remove(indexOfAddition);
						operands.remove(indexOfAddition+1);
						operands.set(indexOfAddition,sum);

					}
			}
				
			return String.valueOf(operands.get(0));
		}
	
	}
	public static void main(String [] args)
	{
		new Calculator();
	}
}


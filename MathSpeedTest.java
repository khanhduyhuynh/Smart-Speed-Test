//  Author: Khanh Duy Huynh
//  Date: 23/07/2011
//  File Name: MathSpeetTest.java

import java.awt.*; // For GUI components support
import java.awt.event.*; // For event handling support
import java.util.Date; // For Date support
import java.util.Calendar; // For Calendar support
import javax.swing.*; // For Swing support
import javax.swing.border.*; // For border support

public class MathSpeedTest extends JFrame
{
	// Declare class components, variables and constants
	private JPanel Top_Panel;
	private JPanel Top_Top_Panel;
	private JPanel Top_Bottom_Panel;
	private JPanel Middle_Panel;
	private JPanel Middle_Left_Panel;
	private JPanel Middle_Left_Left_Panel;
	private JPanel Middle_Left_Right_Panel;
	private JPanel Middle_Right_Panel;
	private JPanel Bottom_Panel;

	private JLabel Title_Label;
	private JLabel Time_Label;

	private JLabel[] Question_Label;
	private NumericTextField[] Answer_TextField;

	private JTextArea Output_TextArea;

	private JButton Generate_Button;
	private JButton Submit_Button;
	private JButton Start_Button;
	private JButton Reset_Button;
	private JButton Exit_Button;

	private SumsClass[] sumsclass;
	private RandomNumber myRandomNumber;
	private Timer timer;

	private Date timeStart;
	private Date timeEnd;

	private int[] array_First;
	private int[] array_Second;

	private final int MAX_MARKS = 200;
	private final int NUM_QUESTIONS = 10;

	private boolean inputError;

	// Create an instance of the class
	public static void main (String[] args)
	{
	   JFrame My_MathSpeedTest = new MathSpeedTest ();
	}

	// Use Constructor to initialize and add GUI components to frame, setup / add listeners for button clicks
	public MathSpeedTest()
	{
		Border lineBdr = BorderFactory.createLineBorder(Color.GRAY, 1);

		Top_Panel = new JPanel();
		Top_Panel.setLayout(new GridLayout(2, 1, 1, 1));
		Top_Panel.setBorder(lineBdr);

		Top_Top_Panel = new JPanel();
		Top_Top_Panel.setLayout(new FlowLayout(FlowLayout.CENTER));

		Title_Label = new JLabel("Math Speed Test");
		Title_Label.setForeground(Color.BLUE);
		Title_Label.setFont(new Font("Times New Roman", Font.BOLD, 30));
		Top_Top_Panel.add(Title_Label);

		Top_Bottom_Panel = new JPanel();
		Top_Bottom_Panel.setLayout(new FlowLayout(20, 40, 4));

		Generate_Button = createGenerateButton();

		Start_Button = createStartButton();

		Time_Label = new JLabel("");
		Time_Label.setForeground(Color.BLUE);
		Time_Label.setFont(new Font("Times New Roman", Font.BOLD, 24));

		Top_Bottom_Panel.add(Generate_Button);
		Top_Bottom_Panel.add(Start_Button);
		Top_Bottom_Panel.add(Time_Label);

		Top_Panel.add(Top_Top_Panel);
		Top_Panel.add(Top_Bottom_Panel);

		Middle_Panel = new JPanel();
		Middle_Panel.setLayout(new GridLayout(1, 2, 8, 8));
		Middle_Panel.setBorder(lineBdr);

		Middle_Left_Panel = new JPanel();
		Middle_Left_Panel.setLayout(new GridLayout(1, 2, 6, 6));
		Middle_Left_Panel.setBorder(lineBdr);

		Middle_Left_Left_Panel = new JPanel();
		Middle_Left_Left_Panel.setLayout(new GridLayout(10, 1, 0, 0));
		Middle_Left_Left_Panel.setBorder(lineBdr);

		Middle_Left_Right_Panel = new JPanel();
		Middle_Left_Right_Panel.setLayout(new GridLayout(10, 1, 0, 0));

		Question_Label = new JLabel[NUM_QUESTIONS];
		Answer_TextField = new NumericTextField[NUM_QUESTIONS];
		for(int i = 0; i < NUM_QUESTIONS; i++)
		{
			Question_Label[i] = new JLabel("", JLabel.CENTER);
			Question_Label[i].setFont(new Font("Times New Roman", Font.BOLD, 20));
			Middle_Left_Left_Panel.add(Question_Label[i]);

			Answer_TextField[i] = new NumericTextField();
			Answer_TextField[i].setColumns(10);
			Answer_TextField[i].setFont(new Font("Times New Roman", Font.BOLD, 20));
			Middle_Left_Right_Panel.add(Answer_TextField[i]);
		}

		Middle_Left_Panel.add(Middle_Left_Left_Panel);
		Middle_Left_Panel.add(Middle_Left_Right_Panel);

		Middle_Right_Panel = new JPanel();
		Middle_Right_Panel.setLayout(new GridLayout(1, 1, 0, 0));
		Middle_Right_Panel.setBorder(lineBdr);

		Output_TextArea = new JTextArea(20,20);
		Output_TextArea.setEditable(false);
		Output_TextArea.setForeground(Color.BLACK);
		Output_TextArea.setFont(new Font("Times New Roman", Font.BOLD, 20));
		Middle_Right_Panel.add(Output_TextArea);

		Middle_Panel.add(Middle_Left_Panel);
		Middle_Panel.add(Middle_Right_Panel);

		Bottom_Panel = new JPanel();
		Bottom_Panel.setLayout(new FlowLayout(FlowLayout.CENTER));

		Submit_Button = createSubmitButton();

		Reset_Button = createResetButton();

		Exit_Button = createExitButton();

		Bottom_Panel.add(Submit_Button);
		Bottom_Panel.add(Reset_Button);
		Bottom_Panel.add(Exit_Button);

		this.setLayout(new BorderLayout(100, 10));
		add(Top_Panel, BorderLayout.NORTH);
		add(Middle_Panel, BorderLayout.CENTER);
		add(Bottom_Panel, BorderLayout.SOUTH);

		setTitle ("Math Speet Test");
		pack();
		setLocationRelativeTo(null);
		setSize(650, 500);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	   	setVisible(true);

		setDefault();
	}

	// Act on mouse clicks on Generate buttons
	private JButton createGenerateButton()
	{
		final JButton Generate_Button = new JButton("Generate Test");
		Generate_Button.setFont(new Font("Times New Roman", Font.BOLD, 16));
		class GenerateListener implements ActionListener
		{
			public void actionPerformed(ActionEvent event)
			{
				Middle_Left_Left_Panel.setVisible(true);
				Middle_Left_Right_Panel.setVisible(true);

				Generate_Button.setEnabled(false);
				Start_Button.setEnabled(true);
				Submit_Button.setEnabled(false);
				Reset_Button.setEnabled(true);

				enablebTextField(false);
				loadRandomNumbers();
			}
		}
		ActionListener listener1 = new GenerateListener();
		Generate_Button.addActionListener(listener1);
		return Generate_Button;
	}

	// Act on mouse clicks on Start buttons
	private JButton createStartButton()
	{
		final JButton Start_Button = new JButton("START TEST");
		Generate_Button.setFont(new Font("Times New Roman", Font.BOLD, 16));
		class StartListener implements ActionListener
		{
			public void actionPerformed(ActionEvent event)
			{
				timeStart = new Date();
				if(timer == null)
				{
					timer = new Timer(1000, new ClockListener());
				}
				timer.start();
				Time_Label.setVisible(true);

				Generate_Button.setEnabled(false);
				Start_Button.setEnabled(false);
				Submit_Button.setEnabled(true);

				enablebTextField(true);
			}
		}
		ActionListener listener2 = new StartListener();
		Start_Button.addActionListener(listener2);
		return Start_Button;
	}

	// Act on mouse clicks on Submit buttons
	private JButton createSubmitButton()
	{
		JButton Submit_Button = new JButton("SUBMIT");
		Submit_Button.setFont(new Font("Times New Roman", Font.BOLD, 16));
		class SubmitListener implements ActionListener
		{
			public void actionPerformed(ActionEvent event)
			{
				submitClick();
			}
		}
		ActionListener listener3 = new SubmitListener();
		Submit_Button.addActionListener(listener3);
		return Submit_Button;
	}

	// Act on mouse clicks on Reset buttons
	private JButton createResetButton()
	{
		JButton Reset_Button = new JButton("Reset");
		Submit_Button.setFont(new Font("Times New Roman", Font.BOLD, 16));
		class ResetListener implements ActionListener
		{
			public void actionPerformed(ActionEvent event)
			{
				int intYesNoOption;
				intYesNoOption = JOptionPane.showConfirmDialog(null, "Are you sure you want to reset?", "Reset data ", JOptionPane.YES_NO_OPTION);
				if(intYesNoOption == 0)
				{
					setDefault();
				}
			}
		}
		ActionListener listener4 = new ResetListener();
		Reset_Button.addActionListener(listener4);
		return Reset_Button;
	}

	// Act on mouse clicks on Exit buttons
	private JButton createExitButton()
	{
		JButton Exit_Button = new JButton("Exit");
		Exit_Button.setFont(new Font("Times New Roman", Font.BOLD, 16));
		class ExitListener implements ActionListener
		{
			public void actionPerformed(ActionEvent event)
			{
				int intYesNoOption;
				intYesNoOption = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", "Exit program ", JOptionPane.YES_NO_OPTION);
				if(intYesNoOption == 0)
				{
					System.exit(0);
				}
			}
		}
		ActionListener listener5 = new ExitListener();
		Exit_Button.addActionListener(listener5);
		return Exit_Button;
	}

	// Set the default for components when application run in the first time or when it is reseted
	private void setDefault()
	{
		Time_Label.setVisible(false);
		Middle_Left_Left_Panel.setVisible(false);
		Middle_Left_Right_Panel.setVisible(true);

		enablebTextField(false);

		Generate_Button.setEnabled(true);
		Start_Button.setEnabled(false);
		Submit_Button.setEnabled(false);
		Reset_Button.setEnabled(false);

		for(int i = 0; i < NUM_QUESTIONS; i++)
		{
			Answer_TextField[i].setText("");
			Answer_TextField[i].setBackground(Color.WHITE);
		}

		Output_TextArea.setText("");
	}

	//  Generate ramdom numbers and put them on the labels as well as the array of SumsClass when button Generate is clicked
	private void loadRandomNumbers()
	{
		myRandomNumber = new RandomNumber();

		array_First = new int[NUM_QUESTIONS];
		array_First = myRandomNumber.getFirst();

		array_Second = new int[NUM_QUESTIONS];
		array_Second = myRandomNumber.getSecond();

		sumsclass = new SumsClass[NUM_QUESTIONS];

		for(int i = 0; i < NUM_QUESTIONS; i++)
		{
			sumsclass[i] = new SumsClass();
			sumsclass[i].setOpsAndSum(array_First[i], array_Second[i]);
			Question_Label[i].setText(Integer.toString(sumsclass[i].getopA()) + "  +  " + Integer.toString(sumsclass[i].getopB()) + "  =  ");
		}
	 }

   // Set the status of textfield is enabled or not depends on the parameter
   private void enablebTextField(boolean enabled)
   {
		for(int i = 0; i < NUM_QUESTIONS; i++)
		{
			Answer_TextField[i].setEnabled(enabled);
		}
	}

	// Calculate the number of correct answer as well as the score user gets
	private void submitClick()
	{
		checkError();
		if(inputError == false)
		{
			timeEnd = new Date();
			timer.stop();
			long timeDiff = timeEnd.getTime() - timeStart.getTime();
			int seconds = (int)(timeDiff/1000);

			int correctAnswer = 0;
			for(int i = 0; i < NUM_QUESTIONS; i++)
			{
				Answer_TextField[i].setBackground(Color.ORANGE);
				sumsclass[i].setUserAnswer(Integer.parseInt(Answer_TextField[i].getText()));
			}

			for(int i = 0; i < NUM_QUESTIONS; i++)
			{
				if(sumsclass[i].getCorrectSum() == sumsclass[i].getUserAnswer())
				{
					correctAnswer++;
					Answer_TextField[i].setBackground(Color.GREEN);
				}
			}

			String output = "Time taken: " + Integer.toString(seconds) + " seconds \n\n";
			output = output + "Correct Answers: " + Integer.toString(correctAnswer) + "\n\n";

			double percentage = (double) correctAnswer / NUM_QUESTIONS;
			double rawScore = (double) (MAX_MARKS * (correctAnswer * 2) / seconds * percentage);
			int intScore = (int) rawScore;

			output = output + "Your Score: " + Integer.toString(intScore) + "\n\n";
			output = output + "Maximum Score: " + MAX_MARKS;

			Output_TextArea.setText(output);

			Generate_Button.setEnabled(false);
			Start_Button.setEnabled(false);
			Submit_Button.setEnabled(false);
		}
	 }

	// Check and show the message asking user input data if any answer textfield is blank
	private void checkError()
	{
		inputError = false;

		for(int i = 0; i < 10; i++)
		{
			if(Answer_TextField[i].getText().equals(""))
			{
				showErrorMessage("Please answer question "+Integer.toString(i + 1) + "!!!");
				Answer_TextField[i].requestFocus();
				inputError = true;
				return;
			}
		}

	}

	// Show error message
	private void showErrorMessage(String str)
	{
		JOptionPane.showMessageDialog(this, str, "Error", JOptionPane.ERROR_MESSAGE);
	}

	// Get the current time and display it in the textfield
	class ClockListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			Time_Label.setText(timeNow());
		}
		public String timeNow()
		{
		   Calendar now = Calendar.getInstance();
		   int hrs = now.get(Calendar.HOUR_OF_DAY);
		   int min = now.get(Calendar.MINUTE);
		   int sec = now.get(Calendar.SECOND);

		   String time = zero(hrs)+":"+zero(min)+":"+zero(sec);
		   return time;
		}

		public String zero(int num)
		{
		   String number = "";
		   if( num < 10)
		   {
			   number = "0" + Integer.toString(num);
		   }
		   else
		   {
			   number = Integer.toString(num);
		   }
		   return number;
		}
	}
}
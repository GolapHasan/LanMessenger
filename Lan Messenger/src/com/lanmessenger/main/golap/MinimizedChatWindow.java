package com.lanmessenger.main.golap;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
public class MinimizedChatWindow extends JPanel{

	/*
	 * constructor
	 */
	public MinimizedChatWindow(BackgroundPanel backgroundpanel, int i) 
	{
		//TODO Auto-generated constructor stub
		this.currenti = i;
		this.backgroundPanel = backgroundpanel;
//		this.maximize = m;
		this.maximize = backgroundpanel.maximizeButton[i];
		
		declaringButtonHandler();
		declaringButtons();
		declaringLabel();
	}
	
	/*
	 * painting part of the class
	 * (non-Javadoc)
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		this.g = g;
		
		titleLabelLocation();
		buttonLocation();
	}

	/*
	 * set tittle for the minimized window
	 */
	public void setTitleOfMinimizedWindow(String title)
	{
		titleOfWindow = title;
	}
	
	/*
	 * user defined Action handler declaring
	 */
	public void declaringButtonHandler()
	{
		buttonHandler = new ButtonHandler();
	}
	
	/*
	 * buttons declaring
	 */
	public void declaringButtons()
	{
		exit = new JButton("X");
		exit.addActionListener(buttonHandler);
		add(exit);
		
		maximize.setFont(new Font("Shyam", Font.BOLD, 15) );
		maximize.addActionListener(buttonHandler);
	}
	
	/*
	 * title label declaring
	 */
	public void declaringLabel()
	{
		titleLabel = new JLabel();
		add(titleLabel);
		add(maximize);
	}
	
	/*
	 * title label location
	 */
	public void titleLabelLocation()
	{
		titleLabel.setText(titleOfWindow);
		titleLabel.setForeground(Color.BLACK);
		titleLabel.setLocation(5, 0);
	}
	
	/*
	 * maximize and exit button location
	 */
	public void buttonLocation()
	{
		maximize.setSize(50, 15);
		maximize.setLocation(getWidth()-105, 0);		
		maximize.setToolTipText("Maximize the Chat window");
		maximize.setBackground(Color.GREEN);
		
		exit.setSize(50, 15);
		exit.setLocation(getWidth()-55,0);
		exit.setToolTipText("Exit the Window");
		exit.setBackground(Color.RED);
		exit.setForeground(Color.WHITE);
	}
	
	/*
	 * user defined Action listener
	 */
	private class ButtonHandler implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent event) {
			// TODO Auto-generated method stub
			if(event.getSource() == exit)
			{
				setVisible(false);
				backgroundPanel.on_chat_people_counter--;
				backgroundPanel.WindowState[currenti] = false;
				backgroundPanel.CHECK[ backgroundPanel.PI[currenti] ] = false;
				backgroundPanel.MoveChatWindow(currenti);
			}
		}
		
	}
	
	/*
	 * declaring part of the class
	 */
	private String titleOfWindow;
	private JLabel titleLabel;
	private JButton maximize, exit;
	private BackgroundPanel backgroundPanel;
	private int currenti;
	private Graphics g;
	
	private ButtonHandler buttonHandler;
}
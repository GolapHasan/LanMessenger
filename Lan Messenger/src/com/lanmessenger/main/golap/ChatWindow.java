package com.lanmessenger.main.golap;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.xml.ws.handler.MessageContext.Scope;

import com.lanmessenger.communication.sender;

import java.io.*;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Scanner;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ChatWindow extends JPanel
{
	/*
	 * constructor of this class
	 */
	public ChatWindow(BackgroundPanel backgroudpanel, int i) 
	{
		this.backgroudPanel = backgroudpanel;
		this.currenti = i;
		this.minimizeWindow = backgroudpanel.minimizeButton[i];
		
		buttonDeclaring();
		labelDeclaring();
	
		textAreaDeclaring();
		textMessageDeclaring();
	}

	/*
	 * control all painting operation of this class
	 * (non-Javadoc)
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	public void paintComponent( Graphics g )
	{
		super.paintComponent(g);
		this.g = g;
		scrollPaneAndTextAreaLocation();
		
		messageBoxLocation();
		
		titleLabelLocation();
		
		buttonsLocation();
	}
	
	/*
	 * title label declaring
	 */
	public void labelDeclaring()
	{
		titleLabel = new JLabel();	
		add(titleLabel);
	}
	
	/*
	 * textPane declaring
	 */
	public void textAreaDeclaring()
	{
		textArea = new JTextPane();
		textArea.setEditable(false);
		textArea.setToolTipText("Messages");
		textArea.setFont(new Font("Calibri Regular",Font.PLAIN, 15));
		textArea.addMouseListener(mouseHandler);
		
		textArea.setMargin(new Insets(5, 5, 5, 5));
		
		
		doc = textArea.getStyledDocument();
		style = textArea.addStyle("I'm a Style", null);
		
		scrollPane = new JScrollPane(textArea);
		scrollPane.setAutoscrolls(true);
		textArea.setAutoscrolls(true);
		add(scrollPane);
	}
	
	/*
	 * writing messagebox declaring
	 */
	public void textMessageDeclaring()
	{
		textMessage = new JTextField("Write message here...");
		textMessage.setToolTipText("Write message here...");
		textMessage.setFont(new Font("Calibri Regular",Font.PLAIN, 15));
		textMessage.setForeground(Color.BLACK);
		textMessage.addActionListener(buttonHandler);
		textMessage.addMouseListener(mouseHandler);
		textMessage.setEnabled(false);		
		
		messagePane = new JScrollPane(textMessage);	
		add(messagePane);
	}
	
	/*
	 * textPane location
	 */
	public void scrollPaneAndTextAreaLocation()
	{
		scrollPane.setSize(getWidth()-10, getHeight()-80);
		scrollPane.setLocation(5, 20);
		scrollPane.setBackground(Color.LIGHT_GRAY);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		textArea.setForeground(Color.BLUE);
		textArea.setBackground(Color.LIGHT_GRAY);	
	}
	
	/*
	 * writing message box location
	 */
	public void messageBoxLocation()
	{
		textMessage.setLocation(5, 20);
		textMessage.setSize(getWidth()-10, getHeight()-80);
		messagePane.setSize(getWidth()-10, 50);
		messagePane.setLocation(5, 250);
	}
	
	/*
	 * chat window title locaton
	 */
	public void titleLabelLocation()
	{
		g.setColor(Color.BLUE);
		titleLabel.setText(WindowTitle);
		titleLabel.setForeground(Color.BLACK);
		titleLabel.setLocation(5, 0);
		titleLabel.setToolTipText("You connect with this people now!");	
		titleLabel.setBackground(Color.yellow);			
	}
	
	/*
	 * decalring of various types of buttons
	 */
	public void buttonDeclaring()
	{
		minimizeWindow.setFont(new Font("Shyan", Font.PLAIN, 25) );
		minimizeWindow.setForeground(Color.BLACK);
		minimizeWindow.setToolTipText("Minimize chat window.");
		
		exitChatWindow =  new JButton("X");
		exitChatWindow.addActionListener(buttonHandler);
		
		deleteMessage = new JButton("*");
		deleteMessage.setFont( new Font("Shyam", Font.PLAIN, 25) );
		deleteMessage.setForeground(Color.RED);
		deleteMessage.setBackground(Color.GREEN);
		deleteMessage.setToolTipText("Delete messages.");
		deleteMessage.addActionListener(buttonHandler);
		
		addFile = new JButton("+");
		addFile.setFont( new Font("Shyam", Font.PLAIN, 25) );
		addFile.setToolTipText("Add file");
		addFile.addActionListener(buttonHandler);
		add(addFile);
		
		add(deleteMessage);
		add(exitChatWindow);
		add(minimizeWindow);
	}
	
	/*
	 * set location for various types of buttons
	 */
	public void buttonsLocation()
	{
		exitChatWindow.setSize(50, 15);
		exitChatWindow.setLocation(getWidth()-50, 2);
		exitChatWindow.setForeground(Color.RED);		
				
		minimizeWindow.setSize(50, 15);
		minimizeWindow.setLocation(getWidth()-205, 2);
		minimizeWindow.setBackground(Color.green);
		minimizeWindow.setForeground(Color.BLACK);
		
		deleteMessage.setSize(50, 15);
		deleteMessage.setLocation(getWidth()-102, 2);		
		
		addFile.setSize(50, 15);
		addFile.setLocation(getWidth()-154, 2);
		addFile.setBackground(Color.green);
		addFile.setForeground(Color.BLACK);
	}
	
	private int messageSendCounter = 0;
	
	/*
	 * User defined ActionHandler
	 */
	private class ButtonHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent event) 
		{			
			if(event.getSource() == textMessage)
			{
				work_of_textMessage();
			}
			else if(event.getSource() == exitChatWindow)
			{
				work_of_exitChatWindow();
			}
			else if(event.getSource() == deleteMessage)
			{
				work_of_deleteMessage();
			}
			else if(event.getSource()==addFile)
			{
				work_of_addFile();
			}
		}
	}
	
	/*
	 * User defined Mouse listener
	 */
	private class MouseHandler implements MouseListener
	{
		public void mouseClicked(MouseEvent event) 
		{
			if(event.getSource() == textMessage)
			{
				textMessage.setEnabled(true);
				if(textMessageBoxClickCounter == 0)
				{
					textMessage.setText("");					
				}
				textMessageBoxClickCounter++;
			}
		}

		public void mouseEntered(MouseEvent event) 
		{
			if(event.getSource() == textArea)
			{
				Cursor cursor = new Cursor( Cursor.TEXT_CURSOR );
				textArea.setCursor(cursor);
			}						
		}

		public void mouseExited(MouseEvent arg0) 
		{
			
		}
		public void mousePressed(MouseEvent arg0) 
		{
			
		}
		public void mouseReleased(MouseEvent arg0) 
		{
			
		}
	}
	
	protected sender SEND = new sender(this);
	
	/*
	 * select file to send
	 */
	public void work_of_addFile()
	{
		backgroudPanel.working_msg.setText("Sending File... ");
		SEND.sendFiletoserver(backgroudPanel.checkToCreateServerOrNotFrame.serverInfoFrame, 
				backgroudPanel.checkToCreateServerOrNotFrame.serverInfoFrame.UI, NUM);
		this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		JOptionPane.showMessageDialog(null, "File Sending Completed");
	}
	
	
	/*
	 * return name of chat window
	 */
	public String getName_chatWindow()
	{
		return WindowTitle;
	}
	
	
	/*
	 * add textmessage to textPane and send it to receiver
	 */
	public void work_of_textMessage()
	{
		message = "";
		String time = "";
		if(textMessage.getText().length() != 0) 
		{
			if(converstionStartingCounting == 0)
			{
				cal = Calendar.getInstance();
				java.sql.Date sqlDate = new java.sql.Date(cal.getTimeInMillis());
				DateFormat fullDf = DateFormat.getDateInstance(DateFormat.FULL);
				
				messageSentTime = String.format("%tr", Calendar.getInstance());
				
				time +=	    "......................................." + messageSentTime + "\n";
			}
			else
			{
				messageSentTime = String.format("%tr", Calendar.getInstance());
				time +=	"....................................."  + messageSentTime + "\n";
			}
			
			message = textMessage.getText() + "\n"; 
			
			try 
			{
				StyleConstants.setFontFamily(style, "Arial");
				StyleConstants.setForeground(style, Color.gray);
				doc.insertString(doc.getLength(), "Your Message:\n", style);
				
				
				StyleConstants.setFontFamily(style, "Arial");
				StyleConstants.setForeground(style, Color.BLACK);
				doc.insertString(doc.getLength(), message + " ", style);
				
				StyleConstants.setFontFamily(style, "Arial");
				StyleConstants.setForeground(style, Color.GRAY);
				doc.insertString(doc.getLength(), time + " ", style);
				
				scrollPane.setAutoscrolls(true);
				textArea.setAutoscrolls(true);
				
			}
	        catch (BadLocationException e)
	        {
	        	JOptionPane.showMessageDialog(null, "Working with textPane from Chatwindow error!");
	        }
			
			textArea.setAutoscrolls(true);
			SEND.sendtoserver(backgroudPanel.checkToCreateServerOrNotFrame.serverInfoFrame.dataOutputStream,
					message, 
					backgroudPanel.checkToCreateServerOrNotFrame.serverInfoFrame.UI, NUM);
			
		}
		textMessage.setText("");
		converstionStartingCounting++;
	}
	
	/*
	 * exit the chat window Panel
	 */
	public void work_of_exitChatWindow()
	{
		setVisible(false);
		backgroudPanel.on_chat_people_counter--;
		backgroudPanel.WindowState[currenti] = false;
		backgroudPanel.CHECK[ backgroudPanel.PI[currenti] ] = false;
		backgroudPanel.MoveChatWindow(currenti);
	}
	
	/*
	 * Deleting message from textPane
	 */
	public void work_of_deleteMessage()
	{
		if(message.length()>0)
		{
			int sure = JOptionPane.showConfirmDialog(null, "Are you sure to delete message? " +
					"You can't restore these message again.\n" +
					"To delete click \"Yes\" " +
					"otherwise click \"No\" or \"Cancel\".");
			if(sure == 0) textArea.setText("");
		}
		else
		{
			JOptionPane.showMessageDialog(null, "There is no message to delete!");
		}
	}

	/*
	 * Setting title to the chatWindow
	 */
	public void setChatWindowTitle(String Title, int num)
	{
		WindowTitle = Title;
		NUM = num;
	}
	
	
	/*
	 * Declaring part of the ChatWindow class
	 */
	public StyledDocument doc; 
	public Style style;
    
    public JTextPane textArea;
    public JTextField textMessage;
	public String message = "" ;
	public JButton exitChatWindow, minimizeWindow, deleteMessage, addFile;
	public String WindowTitle; 
	public JLabel titleLabel;
	public BackgroundPanel backgroudPanel;
	public int currenti, textMessageBoxClickCounter=0, converstionStartingCounting=0;
	public String OldMessage = "", messageSentTime;
	public String fileMessage = "";
	public Writer fileWriter;
	public JScrollPane scrollPane, messagePane;
	public Calendar cal;
	public ButtonHandler buttonHandler = new ButtonHandler();
	public MouseHandler mouseHandler = new MouseHandler();
	public Graphics g;
	public int NUM;
}

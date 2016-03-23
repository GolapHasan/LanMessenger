package com.lanmessenger.main.golap;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.UIManager;

/*
 * class info-------
 * this is the background class.
 * this class controlling everything
 */
public class BackgroundPanel extends JPanel
{
	/*
	 * constructor of this class
	 */
	public BackgroundPanel(JFrame mainFrame, CheckToCreateServerOrNotFrame checkToCreateServerOrNotFrame) 
	{		
		this.mainFrame = mainFrame;
		this.checkToCreateServerOrNotFrame = checkToCreateServerOrNotFrame;
		this.NofP = 0;
		backgroundPanel = this;
		
		setUIManager();
		
		buttonHandlerDeclaring();
		
		mouse_Handler_Declaring();
		
		peopleLabelDeclaring();
		
		//infoFrameDeclaring();
		
		windowStateInitializing();
		
		availableLabel();
		
		menuAndMenuItemDeclaring();
		
		buttonDeclaring();
		
		windowDeclaring();
		
		backgroundLabelDeclaring();
	}
	
	/*
	 * painting of the elements of this class
	 * (non-Javadoc)
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		this.g = g;
		setBackground(Color.WHITE);
		
		//keeping copy of current window size
		XofChatWindow = getWidth() - 500;
		YofChatwindow = getHeight() - 300;
		
		if(counter == 0) getXofChatWindowAndYofChatWindow(getWidth()-500, getHeight()-300);
		counter++;
		
		setBackgroundLocation();
		owner_label_location();
		connectedPeopleLocation();
		menuAndMenuItemLocation();
		chatWindowLocation();			
		graphicsPartDesign();
	}
	
	
	/*
	 * user defined AtionHandler
	 */
	public class ButtonHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent event) 
		{
			if(event.getSource()==exit)
			{
				work_of_exitButton();
			}
			else if(event.getSource()==info)
			{
				work_of_info_MenuItem();
			}
			else if(event.getSource() == searchPeople)
			{
				work_of_searchPeople_textField();
			}
			else 
			{
				for(int i=0; i<50; i++)
				{
					if(event.getSource() == minimizeButton[i]) work_of_minimizingButton(i);
					else if(event.getSource() == maximizeButton[i]) work_of_maximizing_Button(i);
				}
			}
		}
	}

	/*
	 * User defined MouseHandler
	 */
	public class MouseHandler implements MouseListener
	{
		public void mouseClicked(MouseEvent event) 
		{
			if(event.getSource() == searchPeople)
			{
				mouseClicked_on_searchPeople();
				return;
			}
			for(int i=0; i<=NofP; i++)
			{				   
				if(event.getSource() == person[i])
				{
					if(mouseClicked_on_person(i))
					{
						JOptionPane.showMessageDialog(null, "Your are chatting with " + person[i].getText() + "!");						
						break;
					}
				}
			}				
		}
		
		public void mouseEntered(MouseEvent event) 
		{
			for(int i=0; i<50; i++)
			{
				if(event.getSource() == person[i])
				{
					mouse_entered_on_person(i);
				}
			}
		}

		public void mouseExited(MouseEvent event) 
		{
			for(int i=0; i<50; i++)
			{
				if(event.getSource() == person[i])
				{
					mouseExited_from_person(i);
				}					
			}
		}
		
		public void mousePressed(MouseEvent event) 
		{
			if(event.getSource() == searchPeople)
			{
				mousePressed_on_searchPeople_JTextField();
			}
		}

		public void mouseReleased(MouseEvent arg0) 
		{
			
		}
	}
	
	/*
	 * Button handler declaring
	 */
	public void buttonHandlerDeclaring()
	{
		buttonHandler = new ButtonHandler();
	}
	
	/*
	 * Exit the main program
	 */
	public void work_of_exitButton()
	{
		System.exit(1);
	}
	
	/*
	 * visible information frame by clicking info
	 */
	public void work_of_info_MenuItem()
	{
		//infoFrame.setVisible(true);
	}
	
	/*
	 * search people from corrently available list
	 */
	public void work_of_searchPeople_textField()
	{
		int i = 0;
		for(; i<NofP; i++)
		{
			if(searchPeople.getText().equalsIgnoreCase(person[i].getText()))
			{
				JOptionPane.showMessageDialog(null, searchPeople.getText() + " availabel now!");
				break;
			}
		}
		
		if(i == NofP)
		{
			JOptionPane.showMessageDialog(null, searchPeople.getText() + " not availabel now!");
		}
		
		searchPeople.setText("");
	}
	
	/*
	 * minimize the chat window by clicking the minimize button
	 */
	public void work_of_minimizingButton(int i)
	{
		chatWindow[i].setVisible(false);
		minimizedChatWindow[i].setVisible(true);
		WindowState[i] = true;
	}
	
	/*
	 * maximize the chat window by clicking the maximize button
	 */
	public void work_of_maximizing_Button(int i)
	{
		//maximizing operation						
		chatWindow[i].setVisible(true);
		minimizedChatWindow[i].setVisible(false);
		WindowState[i] = true;
	}
	
	/*
	 * reset text when search people clicked
	 */
	public void mouseClicked_on_searchPeople()
	{
		searchPeople.setEnabled(true);
	}
	
	/*
	 * make visible chat window by clicking on person name label
	 */
	public boolean mouseClicked_on_person(int i)
	{
		if(on_chat_people_counter == 3)
		{
			JOptionPane.showMessageDialog(null, "You are already connected with three people!\n" +
					"You can't chat more than three people. Thanks");
			return false;
		}
		
		if(WindowState[i]==true) return true;
		on_chat_people_counter++;
		
		chatWindowCounter++;
		chatWindow[i].setVisible(true);
		minimizedChatWindow[i].setVisible(false);
		WindowState[i] = true;
			
		int j;
		for( j=0; j<3; j++)
		{
			if(CHECK[j]==false)
			{
				break;
			}
		}
			
		if(j!=3)
		{
			CHECK[j]=true;
			PI[i]=j;
			CWX1[i]=CWX[j];
			CWY1[i]=CWY[j];
			repaint();
				
			PP[j]=i;
		}
		return false;
	}
	
	/*
	 * change mouse shape and reset color when mouse at on person
	 */
	public void mouse_entered_on_person(int i)
	{
		Cursor cursor = new Cursor(Cursor.HAND_CURSOR);
		person[i].setForeground(Color.red);
		person[i].setCursor(cursor);
	}
	
	/*
	 * reset after mouse exit from the person name label
	 */
	public void mouseExited_from_person(int i)
	{
		person[i].setForeground(Color.green);
	}
	
	/*
	 * reset text of searchpeople by clicking on it
	 */
	public void mousePressed_on_searchPeople_JTextField()
	{
		if(searchPeopleClicCounter == 0)
		{
			searchPeople.setText("");
		}						
		searchPeopleClicCounter++;
	}
	
	/*
	 * available label declaring
	 */
	public void availableLabel()
	{
		availableLabel = new JLabel("People now available:");
		
		availableLabel.setForeground(Color.lightGray);
	}
	
	/*
	 * information frame declaring
	 */
//	public void infoFrameDeclaring()
//	{
//		infoFrame = new InfoFrame();
//		infoFrame.setVisible(false);
//		infoFrame.setLocationRelativeTo(null);
//	}
//	
	/*
	 * window state initializng
	 */
	public void windowStateInitializing()
	{
		for(int i=0; i<50; i++)
			WindowState[i] = false;
	}

	/*
	 * menu item and menu declaring
	 */
	public void menuAndMenuItemDeclaring()
	{
		Color menuColor = Color.BLACK;
		file = new JMenu("File");
		file.setForeground(menuColor);
		
		setting = new JMenu("Setting");
		about = new JMenu("About");
		about.setForeground(menuColor);
		
		exit = new JMenuItem("Exit");
		exit.setMnemonic('E');
		exit.setForeground(menuColor);
		
		info = new JMenuItem("Info");
		info.setMnemonic('I');
		info.setForeground(menuColor);

		about.setToolTipText( "About me!" );

		file.setToolTipText( "File" );
		file.setForeground(menuColor);
		
		//settingSubMenuClass = new SettingSubMenuClass(this, setting);
		
		//action defining of info, exit command
		info.addActionListener(buttonHandler);
		exit.addActionListener(buttonHandler);
		
		file.add(exit); //adding exit as sub menu to file
		add(menuBar);//menuar adding
		menuBar.add(file);//file menu 
		
		menuBar.add(setting);
		
		menuBar.add(about);//about adding to menubar
		about.add(info);//adding info sub menu to about menu
		
		add(availableLabel);//adding available to the top of connected people of chatting
		add(searchPeople);
		
		searchPeople.setText("Search your people");
		searchPeople.setToolTipText( "Search people here" );
		searchPeople.setForeground(menuColor);
		searchPeople.setEnabled(false);
		
		//action defining of searchpeople textField
		searchPeople.addMouseListener(mouseHandler);
		searchPeople.addActionListener(buttonHandler);
	}
	
	/*
	 * menu and menu item location
	 */
	public void menuAndMenuItemLocation()
	{
		availableLabel.setLocation(getWidth()-190, 20);
		//paint of searching people textField
		searchPeople.setLocation(getWidth()-200, getHeight()-20);
		searchPeople.setSize( 200, 20);		
				
		//paint of menubar
		menuBar.setSize(getWidth(),20);
		menuBar.setLocation(0,  0);
				
		//paint of file menu, main menu
		file.setLocation(0, 0);
				
		setting.setLocation(30, 0);	
				
		//position of the about option, menu of the menubar
		about.setLocation(80, 0);	
	}
	
	/*
	 * buttons declaring
	 */
	public void buttonDeclaring()
	{
		for(int i=0; i<50; i++)
		{
			minimizeButton[i] = new JButton("-");
			minimizeButton[i].addActionListener(buttonHandler);
			maximizeButton[i] = new JButton("+");
			maximizeButton[i].addActionListener(buttonHandler);
		}	
	}

	/*
	 * people lable declaring
	 */
	public void peopleLabelDeclaring()
	{
		for(int i=0; i<50; i++) {
				person[i] = new JLabel();
				person[i].addMouseListener( mouseHandler );
				person[i].setForeground(Color.green);
				add(person[i]);
				
				CWX1[i] = 0;
				CWY1[i] = 0;
				CWX[i] = 0;
				CWY[i] = 0;
				CHECK[i] = false;
		}
	}
	
	/*
	 * location of connected people right now
	 */
	public void connectedPeopleLocation()
	{
		//paint of the text "people now available". located on top of connected people
		
		int YofPersonLocation = 40;
		for(int i=0; i<50; i++)
		{
			//paint for each connected people
			person[i].setLocation(getWidth()-190, YofPersonLocation);
			person[i].setToolTipText("Click to conversion");
			YofPersonLocation +=20;
		}		
	}
	
	/*
	 * update connected people list by joinig a one new
	 */
	public void connectedPeopleSet()
	{
		for(int i=0; i<NofP; i++)
		{
			person[i].setToolTipText("Click to conversion");
			person[i].setText(person[i].getText());
			
		}		
	}
	
	/*
	 * people label adding to panel
	 */
	public void peopleLabelAdd()
	{
		for(int i=0; i<NofP; i++) {add(person[i]); person[i].setBackground(Color.cyan);  System.out.println(person[i].getText());}
		repaint();
		
	}
	
	/*
	 * chatwindow and minimized chat window declaring
	 */
	public void windowDeclaring()
	{
		for(int i=0; i<50; i++)
		{
			chatWindow[i] = new ChatWindow(this, i);
			//chatting window
			chatWindow[i].setVisible(false);
			add(chatWindow[i]);
			
			minimizedChatWindow[i] = new MinimizedChatWindow(this, i);
			minimizedChatWindow[i].addMouseListener(mouseHandler);
			minimizedChatWindow[i].setVisible(false);
			add(minimizedChatWindow[i]);//minimized chat window
		}	
	}
	
	/*
	 * chat window and minimized chat window location
	 */
	public void chatWindowLocation()
	{
		for(int i=0; i<3; i++)
		{
			//paint of the chatwindow
			chatWindow[i].setBackground(Color.green);
			chatWindow[i].setSize(300, 300);
			chatWindow[i].setForeground(Color.RED);
			chatWindow[i].setChatWindowTitle(person[i].getText(), personI[i]);
			
			chatWindow[i].setLocation(CWX1[i], CWY1[i]);
			//paint of minimized window while chatting
			
			minimizedChatWindow[i].setBackground(Color.green);
			minimizedChatWindow[i].setTitleOfMinimizedWindow(name[i]);
			minimizedChatWindow[i].setForeground(Color.BLACK);
			minimizedChatWindow[i].setSize(200, 20);
			
			minimizedChatWindow[i].setLocation(CWX1[i]+100, CWY1[i]+280);
		}
	}
	
	/*
	 * graphics part handling for the class
	 */
	public void graphicsPartDesign()
	{
		//drawing table in each people name, connected in conversion, here called as JLabel.
		g.setColor(Color.darkGray);
		g.fillRect(getWidth()-200, 0, 200, getHeight());
		g.setColor(Color.BLACK);		
				
		//drawing line on people connected list, to separate them from each other
		int heght = getHeight()/20 + 5;		
		
		for(int i=0; i<50; i++)
		{
			g.drawLine(getWidth()-200, heght, getWidth(), heght);
			heght +=20;
		}
				
		//drawing the shapes designed in right top to right side portion of the window
		int width = getWidth()-210, height = getHeight(), x=width+10, y=40;
		g.setColor(Color.magenta);
		
		for(int i=0; i<250; i++)
		{
			if(i==25) g.setColor(Color.green);
				g.drawLine(width, height, x, y);
				width -= getWidth()/40;			
				y += getHeight()/40;
		}
	}
	
	/*
	 * own name, working message and background label declaring
	 */
	public void backgroundLabelDeclaring()
	{
		owner_name1 = new JLabel("Welcome: ");
		owner_name1.setFont(new Font("", Font.PLAIN, 20) );
		owner_name1.setForeground(Color.green);
		add(owner_name1);
		
		owner_name2 = new JLabel();
		owner_name2.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN , 20));
		//Font font = new Font("Arial Rounded MT Bold", Font.PLAIN , 12);
		owner_name2.setForeground(Color.YELLOW);
		add(owner_name2);
		
		working_msg = new JLabel();
		working_msg.setFont(new Font("", Font.PLAIN, 15) );
		add(working_msg);
		
		//backgroundLabel = new JLabel(new ImageIcon(getClass().getResource("home1600.jpg")) );
		//add(backgroundLabel);
	}
	
	/*
	 * owner name, working message location
	 */
	public void owner_label_location()
	{
		owner_name1.setLocation(450, 50);
		owner_name2.setLocation(540, 50);
		working_msg.setLocation(350, 140);
	}
	
	/*
	 * setting background location
	 */
	public void setBackgroundLocation()
	{
		backgroundLabel.setSize(945, 690);
		backgroundLabel.setLocation(0, 0);
	}
	
	/*
	 * denoting the ChatWindow box's location #calling from paintComponent method
	 */
	public void getXofChatWindowAndYofChatWindow(int x, int y)
	{
		XofChatWindow = x;
		YofChatwindow = y;
		for(int i=0; i<3; i++)
		{
			CWX[i] = XofChatWindow;
			CWY[i] = YofChatwindow;
			
			XofChatWindow -=310;
		}
	}
	
	/*
	 * setting system look and feel
	 */
	public void setUIManager()
	{
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, "UI look error!");
		}
	}
	
	public int[] PP  = {0,0,0};
	
	/*
	 * rearrange chat window by clicking to a new person or 
	 * by exiting the chatwindow
	 */
	public void MoveChatWindow(int per)
	{
		for(int i=0; i<2; i++)
		{
			if(CHECK[i]==true) continue;
			
			int j;
			
			for(j=i+1; j<3; j++)
			{
				if(CHECK[j]==true) break;	
			}
			
			if(j<3)
			{			
				CWX1[PP[j]]=CWX[i];
				CWY1[PP[j]]=CWY[i];
				
				CHECK[j]=false;
				CHECK[i]=true;
				
				PI[PP[j]]=i;
				
				PP[i]=PP[j];
				
				repaint();
			}
		}
	}
	
	/*
	 * set name of the people who recently connected to the network
	 */
	public void set_Name_of_recently_connectly_connected_people(String name)
	{
		
		System.out.println("person : " + NofP + " name: " + name);
		person[NofP].setText(name);
	}
	
	/*
	 * setting chat window location
	 */
	public void setChatWindowLocation(ChatWindow chatWin, MinimizedChatWindow miniChatWin) 
	{
		miniChatWin.setLocation(XofChatWindow+100, YofChatwindow+280);		
		XofChatWindow -= 320;
	}
	
	/*
	 * mouse handler class declaring
	 */
	public void mouse_Handler_Declaring()
	{
		mouseHandler = new MouseHandler();
	}
	
	/*
	 * declaring part of used elements in this class
	 */
	//declaring usage elements
	private JMenuBar menuBar = new JMenuBar();
	private JMenu file, about, setting;
	private JMenuItem exit, info;
	private JLabel availableLabel;
	
	public int NofP;
	public JLabel[] person = new JLabel[50];
	public int[] personS = new int[50];
	public int[] personI = new int[50];
	
	public ChatWindow[] chatWindow = new ChatWindow[50];
	private MinimizedChatWindow[] minimizedChatWindow = new MinimizedChatWindow[50];
	
	private boolean chatWindowShowState = false;
	private JTextField searchPeople = new JTextField();
	private String searchPeopleVariable;
	private int XofChatWindow, YofChatwindow, clickCount, loopVariablei;
	protected JButton minimizeButton[] = new JButton[50];
	protected JButton maximizeButton[] = new JButton[50];
	private String[] name = new String[50];
	private MouseHandler mouseHandler;//userdefined MouseListener
	private ButtonHandler buttonHandler;//userdefined JButtonHandler
	private int chatWindowCounter, searchPeopleClicCounter=0;
	protected boolean[] WindowState = new boolean[50];

	private JScrollPane messagePane, messageTextAreapane;
	
	//private InfoFrame infoFrame;
	private int[] CWX = new int[50];// = {851,531,211};
	private int[] CWY = new int[50]; // = {401,401,401};
	public int[] CWX1 = new int[50];
	public int[] CWY1 = new int[50];
	public int[]  PI = new int[50];
	public boolean[] CHECK =  new boolean[50];
	private int MCWX = 0;
	private int MCWY = 0;
	protected int counter, on_chat_people_counter = 0;
	private String masg;
	
	private Graphics g;
	protected JLabel backgroundLabel, owner_name1;
	public JLabel owner_name2;
	protected JLabel working_msg;
	
	//protected CheckUserNamePasswordAtOpennigFrame passwordFrame;
	protected JFrame mainFrame;
	//protected SettingSubMenuClass settingSubMenuClass;
	protected CheckToCreateServerOrNotFrame checkToCreateServerOrNotFrame;
	protected BackgroundPanel backgroundPanel;
	
	protected boolean visibilityOfMainFrame = true, visibilityOfPasswordFrame=false;	
}
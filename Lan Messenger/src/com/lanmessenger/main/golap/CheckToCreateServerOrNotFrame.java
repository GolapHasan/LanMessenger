package com.lanmessenger.main.golap;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.Scanner;

import javax.sql.rowset.JoinRowSet;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.lanmessenger.communication.ServerInfoFrame;
import com.lanmessenger.communication.receiver;

/*
 * this project already covered 5200 lines
 * 
 * class info --------
 * this class will start the project and will check
 * if user want to create a server or trying to login 
 * to a exist server
 */

public class CheckToCreateServerOrNotFrame extends JPanel  implements Runnable
{
	/*
	 * This is the main method of the software
	 */
	public static void main(String[] args) 
	{
		new CheckToCreateServerOrNotFrame();
	}
	
	/*
	 * constructor of this class
	 */
	public CheckToCreateServerOrNotFrame() 
	{
		setSystemLook();
		
		buttonHandler_declaring();
		
		serverInfoFrame_Declaring();
		
		buttonDeclaring();
		
		backgroundLabelDeclaring();
		
		setFrameInfo();	
		
		set_FrameVisibility_and_authentication();
	}

	
	/*
	 * painting part of the class
	 * (non-Javadoc)
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		createServer.setSize(200, 40);
		createServer.setLocation(100, 50);
		
		createServerLabel.setLocation(10, 20);
		
		separatorline.setLocation(10, 110);
		
		joinToServer.setSize(200, 40);
		joinToServer.setLocation(100, 170);
		
		joinServerLabel.setLocation(10, 140);
		backgroundLabel.setLocation(0, 0);
	}
	
	protected ServerSocket socket; 
	private Thread T;
	protected String port_Number;
	
	/*
	 * creating server
	 */
	public void createServerMethod()
	{
		try
		{
			socket = new ServerSocket(2222, 100);
			
			System.out.println("Server Running. . .  ");
			T = new Thread(this);
			T.start();
		}
		catch(IOException e)
		{
			//serverInfoFrame.serverCreated = false;
			JOptionPane.showMessageDialog(null, "Server creating not possible!!!\n"
					+ "May be there already exist one as same name.");
			
		}
	}
	
	/*
	 * always checking which client wanting to join to this server
	 */
	int N=0;
	public void run() 
	{
		try
		{   
			while(true)
			{
				System.out.println(". " + N );
				N++;
				new receiver(this, socket.accept()).start();
			}	
		}
		catch(IOException e) {}
	}
	
	/*
	 * User defined Action listener
	 */
	private class ButtonHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			if(event.getSource()==createServer)
			{
				//serverInfoFrame.command = "Create Server";
				createServerMethod();
				
				frame.dispose();
				serverInfoFrame.frame.setVisible(true);
			}
			if(event.getSource()==joinToServer)
			{
				serverInfoFrame.command = "join Server";
				frame.dispose();
				serverInfoFrame.frame.setVisible(true);
			}
		}
	}
	
	/*
	 * user defined buttonhandler declaring
	 */
	public void buttonHandler_declaring()
	{
		buttonHandler = new ButtonHandler();
	}
	
	/*
	 * server info frame declaring
	 */
	public void serverInfoFrame_Declaring()
	{
		serverInfoFrame = new ServerInfoFrame(this);
	}

	/*
	 * setting frame information
	 */
	public void setFrameInfo()
	{
		
		frame = new JDialog();
		frame.setAlwaysOnTop(true);
		frame.setVisible(false);
		frame.setTitle("Welcome!");
		frame.setSize(400, 300);
		frame.setModal(true);
		frame.add(this);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(frame.DISPOSE_ON_CLOSE);
	}
	
	/*
	 * buttons declaring
	 */
	public void buttonDeclaring()
	{
		Font font = new Font("", Font.BOLD, 20);
		createServer = new JButton("Create Server");
		createServer.setOpaque(false);
		createServer.setBackground(Color.GREEN);
		createServer.setFont(font);
		createServer.setForeground(Color.RED);
		createServer.addActionListener(buttonHandler);
		add(createServer);
		
		joinToServer = new JButton("Join To Server");
		joinToServer.setBackground(Color.GREEN);
		joinToServer.setFont(font);
		joinToServer.setForeground(Color.RED);
		joinToServer.addActionListener(buttonHandler);
		add(joinToServer);
		
		Font labelfont = new Font("", Font.PLAIN, 15);
		createServerLabel = new JLabel("Create Server to build up a new network.");
		createServerLabel.setForeground(Color.YELLOW);
		createServerLabel.setFont(labelfont);
		add(createServerLabel);
		
		joinServerLabel = new JLabel("Join to a existing network.");
		joinServerLabel.setForeground(Color.YELLOW);
		joinServerLabel.setFont(labelfont);
		add(joinServerLabel);
		
		separatorline = new JLabel("_______________________________________________");
		separatorline.setForeground(Color.WHITE);
		separatorline.setFont(labelfont);
		add(separatorline);
	}

	
	/*
	 * background label declaring
	 */
	public void backgroundLabelDeclaring()
	{
		backgroundLabel = new JLabel(new ImageIcon(getClass().getResource("checkToCreateServerOrNotFrameBackground.JPG")) );
		add(backgroundLabel);
	}
	
	/*
	 * settting system look and feel
	 */
	public void setSystemLook()
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
	
	/*
	 * controlling which frame will visible
	 */
	public void set_FrameVisibility_and_authentication()
	{
		try
		{
//			fileScanner = new Scanner(new File("frameVisibilityControl/frameVisibilityFile.txt"));
//			if(fileScanner.hasNext()) visibility_of_main_frame = fileScanner.nextLine();
//			if(fileScanner.hasNext()) visibility_of_password_frame = fileScanner.nextLine();
			
			frame.setVisible(true);

			System.out.println("Executed: " + visibility_of_main_frame + " " + visibility_of_password_frame);
		} 
		catch (Exception e) 
		{
			JOptionPane.showMessageDialog(null, "File not found");
		}
	}
	
	/*
	 * declaring part of the class
	 */
	public HashSet<CLIENT> CLIENT = new HashSet<CLIENT>();
	private JButton createServer, joinToServer;
	public JDialog frame;
	private JLabel backgroundLabel;
	private JLabel createServerLabel, joinServerLabel, separatorline;
	private Scanner fileScanner; 
	public String visibility_of_main_frame, visibility_of_password_frame;
	
	public ServerInfoFrame serverInfoFrame;
	
	private ButtonHandler buttonHandler;
	
	private CheckToCreateServerOrNotFrame checkToCreateServerOrNotFrame;
	
	public MAIN mainClass = new MAIN(this);
	private static final Frame JDialog = null;
}

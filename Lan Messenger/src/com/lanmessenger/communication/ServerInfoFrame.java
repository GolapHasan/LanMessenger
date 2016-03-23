package com.lanmessenger.communication;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.Scanner;
import java.util.StringTokenizer;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyleConstants;

import com.lanmessenger.main.golap.CheckToCreateServerOrNotFrame;
import com.lanmessenger.main.golap.MAIN;

public class ServerInfoFrame extends JPanel implements Runnable
{
	/*
	 * constructor of this class
	 */
	public ServerInfoFrame(
			CheckToCreateServerOrNotFrame checkToCreateServerOrNotFrame)
	{
		this.checkToCreateServerOrNotFrame = checkToCreateServerOrNotFrame;
		this.mainClass = checkToCreateServerOrNotFrame.mainClass;

		buttonHandler_declaring();
		button_declaring();
		label_declaring();
		textField_declaring();
		setFrameInfo();
		backgroundLabelDeclaring();
	}

	/*
	 * painting all the elements of the class (non-Javadoc)
	 * 
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	protected void paintComponent(Graphics g) 
	{
		super.paintComponent(g);
		setBackground(Color.GREEN);

		button_Location();
		label_Location();
		textField_Location();
		backgroundLabel.setLocation(0, 0);
	}

	/*
	 * join to a existing a server
	 */
	private boolean joinServer()
	{
		String n = hostName;
		try 
		{
			host = InetAddress.getByName(hostName);
		} 
		catch (IOException e)
		{
			return false;
		}
		try 
		{
			socket = new Socket(host, 2222);

			Socket socket = this.socket;
			dataInputStream = new DataInputStream(socket.getInputStream());
			dataOutputStream = new DataOutputStream(socket.getOutputStream());
			is = socket.getInputStream();
			JOptionPane.showMessageDialog(null, "Join successful!!!");

			System.out.println(dataOutputStream);
			System.out.println(socket);
		} 
		catch (IOException e)
		{
			JOptionPane.showMessageDialog(null, "Join failure!!!");
			return false;
		}

		try 
		{
			dataOutputStream.writeUTF("<<NEW>> " + userNameString);
		}
		catch (IOException e) 
		{
			JOptionPane.showMessageDialog(null,
					"New user error from server info frame!");
		}

		Thread T = new Thread(this);
		T.start();

		return true;

	}

	protected StringTokenizer st;
	protected String S1;
	protected InputStream is = null;
	public int UI;

	protected int bytesRead;
	protected int currentTot = 0;
	protected boolean check = false;
	// protected int aa=0;
	protected int sender1 = 0;
	protected int receiver1 = 0;
	protected int size = 0;
	protected String file_name = null, ss;
	protected FileOutputStream fileOutputStream2 = null;
	protected BufferedOutputStream bufferedOutputStream = null;

	protected String All_message = "";
	protected FileOutputStream fileOutputStream;

	public void run()
	{
		while (true) 
		{
			try 
			{
				check = false;

				S = dataInputStream.readUTF();
				System.out.println("sms " + S);

				if (S.startsWith("<<START>>")) 
				{
					file_will_receive_so_initialize_variables();
				}

				if (check == true)
				{
					file_write_to_clint_received_from_server();

				}

				if (S.startsWith("<<Pstart>>")) 
				{
					mainClass.background.NofP = 0;
				}
				if (S.startsWith("<<PM>>"))
				{
					write_post_message();
				} 
				else if (S.startsWith("<<UI>>")) 
				{
					set_user_identity();
				} 
				else if (S.startsWith("<<P>>"))
				{
					new_person_have_joined();
				}
				if (S.startsWith("<<Pstop>>")) 
				{
					mainClass.background.connectedPeopleSet();
					mainClass.background.peopleLabelAdd();
				}
			} 
			catch (IOException e)
			{
				JOptionPane.showMessageDialog(null,
						"Server is unavailable! Please Close Connection!");
				System.exit(1);
			}
		}
	}

	/*
	 * user defined actionListener and Keylistener
	 */
	private class ButtonHandler implements ActionListener, KeyListener 
	{
		public void actionPerformed(ActionEvent event) 
		{
			if (event.getSource() == ok || event.getSource() == userNameField
					|| event.getSource() == hostNameField
					|| event.getSource() == portNumberField)
			{
				if (userNameField.getText().length() != 0
						&& hostNameField.getText().length() != 0
						&& portNumberField.getText().length() != 0)
				{
					userNameString = userNameField.getText();
					hostName = hostNameField.getText();
					portNumberString = portNumberField.getText();
					mainClass.background.owner_name2.setText(userNameString);

					if (userNameString.equals("") || hostName.equals("")
							|| portNumberString.equals("")) 
					{
						JOptionPane
								.showMessageDialog(null,
										"No Field can empty. Please Enter input correctly. Thanks!");
						return;
					}
					if (joinServer())
					{
						frame.dispose();
						mainClass.frame.setVisible(true);
					} 
					else
					{
						JOptionPane.showMessageDialog(null,
								"Unable to connect Server!");
					}
				} 
				else
				{
					JOptionPane.showMessageDialog(null,
							"No Field would be free!");
				}
			}
		}

		@Override
		public void keyPressed(KeyEvent event) 
		{
			// TODO Auto-generated method stub
			// if(event.getKeyChar())
			// System.out.println("you pressed: " + event.getKeyChar());
		}

		@Override
		public void keyReleased(KeyEvent arg0)
		{
			// TODO Auto-generated method stub

		}

		@Override
		public void keyTyped(KeyEvent event) 
		{
			// TODO Auto-generated method stub
			String space = event.getKeyChar() + "";
			if (space.equals(" ")) 
			{
				JOptionPane
						.showMessageDialog(null,
								"Name can't contain any space.\nSample Name Format: Md_Kamrul_Hasan.\nThanks");
				userNameField.setText("");
			}
			// System.out.println("typed: " + event.getKeyChar());
		}
	}

	/*
	 * user defined button handler declaring
	 */
	public void buttonHandler_declaring()
	{
		buttonHandler = new ButtonHandler();
	}

	/*
	 * buttons declaring
	 */
	public void button_declaring() 
	{
		ok = new JButton("OK");
		ok.setFont(new Font("", Font.BOLD, 15));
		ok.setForeground(Color.BLUE);
		ok.addActionListener(buttonHandler);
		add(ok);
	}

	/*
	 * location of ok button
	 */
	public void button_Location()
	{
		ok.setSize(60, 25);
		ok.setLocation(295, 200);
	}

	/*
	 * set informatin of frame of this class
	 */
	public void setFrameInfo() 
	{
		frame = new JDialog();
		frame.setTitle("Login Info!");
		serverInfoFrame = this;
		frame.setSize(400, 300);
		frame.setModal(true);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(frame.DISPOSE_ON_CLOSE);
		frame.add(this);
	}

	/*
	 * different types of label declaring
	 */
	public void label_declaring()
	{
		userName = new JLabel("User Name :");
		userName.setFont(new Font("", Font.BOLD, 15));
		userName.setForeground(Color.YELLOW);
		add(userName);

		hostNameAddress = new JLabel("Server Name :");
		hostNameAddress.setFont(new Font("", Font.BOLD, 15));
		hostNameAddress.setForeground(Color.YELLOW);
		add(hostNameAddress);

		portNumber = new JLabel("Port Number :");
		portNumber.setFont(new Font("", Font.BOLD, 15));
		portNumber.setForeground(Color.YELLOW);
		add(portNumber);

		frameInfoLabel = new JLabel("Local Area Network Information.");
		frameInfoLabel.setFont(new Font("", Font.PLAIN, 15));
		frameInfoLabel.setForeground(Color.WHITE);
		add(frameInfoLabel);
	}

	/*
	 * userNameField, portNumberField, hostNameField declaring
	 */
	public void textField_declaring()
	{
		try 
		{
			userNameField = new JTextField(""
					+ Inet4Address.getLocalHost().getHostName(), 20);
		} 
		catch (UnknownHostException e1)
		{
			e1.printStackTrace();
		}

		userNameField.addActionListener(buttonHandler);
		userNameField.addKeyListener(buttonHandler);
		userNameField.setFont(new Font("", Font.PLAIN, 12));
		add(userNameField);

		try 
		{
			hostNameField = new JTextField(""
					+ Inet4Address.getLocalHost().getHostName(), 20);
		} 
		catch (UnknownHostException e)
		{
			e.printStackTrace();
		}

		portNumberField = new JTextField("2222", 20);
		portNumberField.addActionListener(buttonHandler);
		portNumberField.setFont(new Font("", Font.PLAIN, 12));
		add(portNumberField);

		hostNameField.addActionListener(buttonHandler);
		hostNameField.setFont(new Font("", Font.PLAIN, 12));
		add(hostNameField);
	}

	/*
	 * lables location
	 */
	public void label_Location()
	{

		userName.setLocation(10, 100);
		portNumber.setLocation(10, 140);
		hostNameAddress.setLocation(10, 170);
		frameInfoLabel.setLocation(10, 20);
	}

	/*
	 * usernameField, portnumberField, hostNameField location
	 */
	public void textField_Location() 
	{
		userNameField.setLocation(130, 100);
		portNumberField.setLocation(130, 140);
		hostNameField.setLocation(130, 170);
	}

	/*
	 * background label declaring
	 */
	public void backgroundLabelDeclaring() 
	{
//		backgroundLabel = new JLabel(new ImageIcon(getClass().getResource(
//				"serverInfoFrameBackground.jpg")));
//		add(backgroundLabel);
	}

	public void file_will_receive_so_initialize_variables() 
	{
		currentTot = 65536;

		st = new StringTokenizer(S);
		ss = st.nextToken();
		sender1 = Integer.parseInt(st.nextToken());
		file_name = st.nextToken();

		System.out.println(sender1 + " " + file_name);

		size = Integer.parseInt(st.nextToken());

		System.out.println("FILE " + size);
		if (check == false)
			check = true;
	}

	public void file_write_to_clint_received_from_server()
	{
		try 
		{
			byte[] bytearray2 = new byte[65536];

			File f = new File("Golap_" + file_name);
			fileOutputStream = new FileOutputStream(f);
			bufferedOutputStream = new BufferedOutputStream(fileOutputStream);

			int loop_continue_time = (int) Math.ceil((double) (size / 65536.0));

			byte[][] STORE = new byte[loop_continue_time][65540];

			for (int qq = 0; qq < loop_continue_time; qq++)
			{
				bytesRead = is.read(bytearray2, 0, 65536);

				for (int i = 0; i < 65536; i++) 
				{
					STORE[qq][i] = bytearray2[i];
				}
				check = false;
			}

			for (int i = 0; i < loop_continue_time; i++)
			{
				bufferedOutputStream.write(STORE[i], 0, 65536);
			}

			bufferedOutputStream.close();
			fileOutputStream.close();
			f.canWrite();
			
			JOptionPane.showMessageDialog(null, "File Received!");
		}
		catch (Exception e)
		{

		}
	}

	public void write_post_message()
	{
		st = new StringTokenizer(S);
		S = st.nextToken();
		int user = Integer.parseInt(st.nextToken());

		S = "";
		while (st.hasMoreTokens()) 
		{
			S = S + " " + st.nextToken();
		}
		System.out.println(user + " " + S);

		for (int i = 0; i < 50; i++) 
		{
			System.out
					.println(checkToCreateServerOrNotFrame.mainClass.background.chatWindow[i].NUM);
			if (checkToCreateServerOrNotFrame.mainClass.background.chatWindow[i].NUM == user) {
				try 
				{
					String messageSentTime = String.format("%tr",
							Calendar.getInstance());

					String time = "......................................."
							+ messageSentTime + "\n";

					StyleConstants
							.setForeground(
									checkToCreateServerOrNotFrame.mainClass.background.chatWindow[i].style,
									Color.gray);
					checkToCreateServerOrNotFrame.mainClass.background.chatWindow[i].doc
							.insertString(
									checkToCreateServerOrNotFrame.mainClass.background.chatWindow[i].doc
											.getLength(),
									"Message from \""
											+ checkToCreateServerOrNotFrame.mainClass.background.chatWindow[i]
													.getName_chatWindow()
											+ "\":\n",
									checkToCreateServerOrNotFrame.mainClass.background.chatWindow[i].style);

					StyleConstants
							.setForeground(
									checkToCreateServerOrNotFrame.mainClass.background.chatWindow[i].style,
									Color.blue);
					checkToCreateServerOrNotFrame.mainClass.background.chatWindow[i].doc
							.insertString(
									checkToCreateServerOrNotFrame.mainClass.background.chatWindow[i].doc
											.getLength(),
									S + "\n",
									checkToCreateServerOrNotFrame.mainClass.background.chatWindow[i].style);

					StyleConstants
							.setForeground(
									checkToCreateServerOrNotFrame.mainClass.background.chatWindow[i].style,
									Color.gray);
					checkToCreateServerOrNotFrame.mainClass.background.chatWindow[i].doc
							.insertString(
									checkToCreateServerOrNotFrame.mainClass.background.chatWindow[i].doc
											.getLength(),
									time + " ",
									checkToCreateServerOrNotFrame.mainClass.background.chatWindow[i].style);
					checkToCreateServerOrNotFrame.mainClass.background.chatWindow[i].textArea
							.setAutoscrolls(true);
					checkToCreateServerOrNotFrame.mainClass.background.chatWindow[i].scrollPane
							.setAutoscrolls(true);
				} 
				catch (BadLocationException e) 
				{
					
				}
				break;
			}
		}
	}

	public void new_person_have_joined() 
	{
		st = new StringTokenizer(S);
		S1 = st.nextToken();
		S1 = st.nextToken();
		int serial = Integer.parseInt(S1);
		S1 = st.nextToken();
		int num = Integer.parseInt(S1);
		S1 = st.nextToken();
		String name = S1;

		mainClass.background.person[serial].setText(name);
		mainClass.background.person[serial].setVisible(true);
		mainClass.background.personI[serial] = num;
		mainClass.background.NofP++;
	}

	public void set_user_identity() 
	{
		st = new StringTokenizer(S);
		S1 = st.nextToken();
		S1 = st.nextToken();
		UI = Integer.parseInt(S1);
	}

	/*
	 * declaring part of used elements in this class
	 */
	protected boolean serverCreated = false;

	public JLabel userName, hostNameAddress, portNumber, frameInfoLabel;
	public String userNameString, hostName, portNumberString;
	public JTextField userNameField, hostNameField, portNumberField;
	public JButton ok;

	public MAIN mainClass;
	public JDialog frame;
	public ServerInfoFrame serverInfoFrame;
	public static CheckToCreateServerOrNotFrame checkToCreateServerOrNotFrame;

	public InetAddress host;
	public Socket socket;
	public DataInputStream dataInputStream = null;
	public DataOutputStream dataOutputStream = null;
	public String S;

	public JLabel backgroundLabel;
	public ButtonHandler buttonHandler;
	public String command = "";

}

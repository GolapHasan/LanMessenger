package com.lanmessenger.main.golap;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.lanmessenger.communication.ServerInfoFrame;
public class MAIN extends JPanel
{
	/*
	 * constructor with parameter
	 */
	public MAIN(CheckToCreateServerOrNotFrame checkToCreateServerOrNotFrame)
	{
			
		this.checkToCreateServerOrNotFrame = checkToCreateServerOrNotFrame;
		this.serverInfoFrame = checkToCreateServerOrNotFrame.serverInfoFrame;
		
		backgroundPanelInfoSet();
		frameInfoSet();	
	}
	
	/*
	 * constructor without parameter
	 */
	public MAIN()
	{
		backgroundPanelInfoSet();
		frameInfoSet();	
	}
	
//	public static void main(String[] args) 
//	{
//		new MAIN();
//	}
	
	/*
	 * info of main frame class
	 */
	public void frameInfoSet()
	{
		frame = new JMFrame("LAN Chat by - Golap & Milon, (CSE, 2K11)");
		frame.add(background);
		Image icon = Toolkit.getDefaultToolkit().getImage("src/FrameIcon.JPG");
		frame.setIconImage(icon);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		//frame.setVisible(true);
		frame.setSize(1150, 650);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
	}
	
	/*
	 * declaring background as object of BackgroundPanel class
	 */
	public void backgroundPanelInfoSet()
	{
		background = new BackgroundPanel(frame, checkToCreateServerOrNotFrame);
	}
	
	/*
	 * setvisibility of main frame
	 */
	public void visibleMainFrame()
	{
		frame.setVisible(true);
		frame.setEnabled(true);
	}
	
	/*
	 * checking that user sure to exit the whole software
	 */
	public class JMFrame extends JFrame
	{
		public JMFrame(String s) 
		{
			super(s);
		}

		public void dispose()
		{
			int i = JOptionPane.showConfirmDialog(null, "Are you sure to exit?");
			if(i==0)
			{
				System.exit(0);
			}
		}
	}
	
	/*
	 * declaring part of the class
	 */
	private static final CheckToCreateServerOrNotFrame CheckToCreateServerOrNotFrame = null;
	public JMFrame frame;
	public BackgroundPanel background;
	
	public ServerInfoFrame serverInfoFrame;
	public CheckToCreateServerOrNotFrame checkToCreateServerOrNotFrame;
}

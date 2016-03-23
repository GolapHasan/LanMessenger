package com.lanmessenger.communication;
import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import com.lanmessenger.main.golap.ChatWindow;


public class sender 
{
	protected ChatWindow chatWindow;
	public sender(ChatWindow chatWindow)
	{
		this.chatWindow = chatWindow;
	}
	DataOutputStream dataOutputStream;
	public void sendtoserver(DataOutputStream dataOutputStream, String sms,  int sender, int receiver)
	{
		this.dataOutputStream = dataOutputStream;
		String S = "<<PM>> " + sender+  " " + receiver + " " + sms;
		try 
		{
			dataOutputStream.writeUTF(S);
		} catch (IOException e) {}
		
	}
	
	public void sendFiletoserver(ServerInfoFrame SIF, int sender, int receiver)
	{
		try{
			JFileChooser filChooser = new JFileChooser();
			filChooser.setMultiSelectionEnabled(true);
			filChooser.setDragEnabled(true);
			filChooser.setFileSelectionMode(filChooser.FILES_ONLY);
			filChooser.showOpenDialog(null);
			
			File transferFile; 
			try{
				transferFile = filChooser.getSelectedFile();
				Cursor cursor = new Cursor(Cursor.WAIT_CURSOR);
				chatWindow.setCursor(cursor);
				set_Mouse_to_wait();
			}
			catch(Exception e)
			{
				return;
			}
			String fileName;
			try
			{
				fileName = transferFile.getName();
			}
			catch(Exception e)
			{
				return;
			}
			String temp_Name = "";
			for(int i=0; i<fileName.length(); i++)
			{
				String temp  = fileName.charAt(i) + "";
				if(temp.equals(" ")) temp_Name += "_";
				else temp_Name += temp;
			}
			fileName = temp_Name;
			
			System.out.println("File Name: " + fileName + " size: " + transferFile.length());
			int outofmemory = (int) (transferFile.length() / 65536);
			if(outofmemory > 800)
			{
				JOptionPane.showMessageDialog(null, "File Size is out of memory!");
	        	return;
			}
			SIF.dataOutputStream.writeUTF("<<START>> " + fileName + " "+ sender +" " + receiver +" "+(int)transferFile.length()) ;
           
			byte [] bytearray = null;
			try
			{
				bytearray = new byte [(int)transferFile.length()];
			}
			catch(Exception e)
			{
				JOptionPane.showMessageDialog(null, "File Size is out of memory!");
	        	return;
			}
			
            FileInputStream fin = new FileInputStream(transferFile);
            BufferedInputStream bin = new BufferedInputStream(fin);
           
            bin.read(bytearray,0,bytearray.length);
            OutputStream os = SIF.socket.getOutputStream();
            System.out.println("Sending Files...");           
           	os.write(bytearray, 0, bytearray.length);
           
            
            System.out.println("File transfer complete from sender " + (int)transferFile.length());
            
            bin.close();
            fin.close();
			
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
	}
	
	public void set_Mouse_to_wait()
	{
		new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		};
	}
}

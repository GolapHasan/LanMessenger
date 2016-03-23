package com.lanmessenger.communication;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.StringTokenizer;

import javax.swing.JOptionPane;

import com.lanmessenger.main.golap.CLIENT;
import com.lanmessenger.main.golap.CheckToCreateServerOrNotFrame;

public class receiver extends Thread
{
	protected CheckToCreateServerOrNotFrame checkToCreateServerOrNotFrame;
	protected DataInputStream data_input_stream = null;
	protected InputStream input_stream = null;
	protected OutputStream output_stream = null;
	protected DataOutputStream data_output_stream= null;
	protected CLIENT client = new CLIENT();
    
	public receiver(CheckToCreateServerOrNotFrame cf, Socket socket)
	{
		this.checkToCreateServerOrNotFrame = cf;
		try 
		{
			input_stream = socket.getInputStream();
			data_input_stream = client.data_Input_Stream = new DataInputStream(socket.getInputStream());
			data_output_stream = client.data_Output_Stream =  new DataOutputStream(socket.getOutputStream());
			client.person_name = "";
			client.number_of_the_person = cf.CLIENT.size();
			client.socket = socket;
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	protected FileOutputStream file_Output_Stream = null;
	protected BufferedOutputStream buffered_Output_Stream = null;
	protected int bytesRead;
	protected int currentTot = 0;
	protected boolean check = false;
	protected int sender1 = 0;
	protected int receiver1 = 0;
	protected int file_size = 0;
	protected String name = null;
	protected String readin, S = null;
    
	private StringTokenizer string_tokenize;
	public void run()
	{
		try
		{
			while(true)
			{
				readin = data_input_stream.readUTF();
				System.out.println("IN " + readin);
				
		        if(readin.startsWith("<<START>>"))
				{
		        	initialize_variables_to_receive_and_send_file();
				}
		       
		         if(check == true)
				{
					/*
					 * File Receiving Server From Client
					 */

					Receive_File_From_Client();

					/*
					 * Now Sending it to Client
					 */

					Send_File_To_Client_From_this_Server();
				}
				
				if(readin.length()>0 && readin.startsWith("<<NEW>>"))
				{	
					new_people_have_joined_work();
				}
				else if(readin.length()>0 && readin.startsWith("<<PM>>"))
				{
					message_received_and_proccessing();
				}
			}
		}
		catch( Exception e)
		{
			
		}
	}
	
	public void new_people_have_joined_work()
	{
		try
		{
			string_tokenize = new StringTokenizer(readin);
			S = string_tokenize.nextToken();
			S = string_tokenize.nextToken();
			client.person_name = S;
			checkToCreateServerOrNotFrame.CLIENT.add(client);
			
			for(CLIENT  C : checkToCreateServerOrNotFrame.CLIENT)
			{
				int i=0;
				data_output_stream.writeUTF("<<Pstart>>");
				for(CLIENT  D : checkToCreateServerOrNotFrame.CLIENT)
				{
					if(C==D) 
					{
						C.data_Output_Stream.writeUTF("<<UI>> " + D.number_of_the_person);
						continue;
					}
					
					String S1;
					S1 = "<<P>> " + i + " " + D.number_of_the_person  + " " + D.person_name;
					C.data_Output_Stream.writeUTF(S1);
					i++;
				}
				System.out.println("People Name: " + C.person_name);
			}
			data_output_stream.writeUTF("<<Pstop>>");
			System.out.println("New People Connected Successfully");
		}
		catch(Exception e)
		{
			
		}
	}
	
	public void message_received_and_proccessing()
	{
		try
		{
			string_tokenize = new StringTokenizer(readin);
			S=string_tokenize.nextToken();
			int sender =Integer.parseInt(string_tokenize.nextToken());
			int receiver =Integer.parseInt(string_tokenize.nextToken());
			
			S = "";
			while(string_tokenize.hasMoreTokens())
			{
				S = S + " "+  string_tokenize.nextToken();
			}
			
			CLIENT CC = new  CLIENT();
			
			int i=0;
			
			for(CLIENT E : checkToCreateServerOrNotFrame.CLIENT)
			{
				if(E.number_of_the_person==receiver)
				{
					E.data_Output_Stream.writeUTF("<<PM>> "+ sender + " " + S);
					break;
					
				}
			}
		}
		catch(Exception e)
		{
			
		}
		
	}
	
	public void initialize_variables_to_receive_and_send_file()
	{
		string_tokenize = new StringTokenizer(readin);
		S = string_tokenize.nextToken();
		name = string_tokenize.nextToken();
		sender1 = Integer.parseInt(string_tokenize.nextToken());
		receiver1 = Integer.parseInt(string_tokenize.nextToken());
		System.out.println(sender1 + " " + receiver1 + " " + name);

		file_size = Integer.parseInt(string_tokenize.nextToken());

		System.out.println("FILE " + file_size);
		if (check == false)
			check = true;
	}
	
	public void Receive_File_From_Client()
	{
		try
		{
			byte [] bytearray2  = new byte [65536];
	    	
	    	File f = new File(name);
	        file_Output_Stream = new FileOutputStream(f);
	        buffered_Output_Stream = new BufferedOutputStream(file_Output_Stream);
	        
	        int k = (int) Math.ceil((double)(file_size / 65536.0));
	        System.out.println("File Will send after Continueing : " + k + " times");
	        
	        byte[][] STORE = null;
	        try
	        {
	        	STORE = new byte[k][65540];
	        }
	        catch(Exception e)
	        {
	        	JOptionPane.showMessageDialog(null, "File Size is out of memory!");
	        	return;
	        }
	        
	        for(int qq=0; qq<k; qq++)
	        {
	        	bytesRead = input_stream.read(bytearray2,0,65536);
	        	
	        	for(int i=0; i<65536; i++)
	        	{
	        		STORE[qq][i]=bytearray2[i];
	        	}
		    	check = false;
	        } 
	      
	        for(int i=0; i<k; i++)
	        {
	        	buffered_Output_Stream.write(STORE[i], 0 , 65536);
	        }			         
	        
	        buffered_Output_Stream.close();
	        file_Output_Stream.close();
	        f.canWrite();
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, "File Receiving From Client to Server Error");
		}
		System.out.println("File Received From Clent to Server.");
		
	}
	
	public void Send_File_To_Client_From_this_Server()
	{
		try
		{
			File F = new File(name);

			byte[] bytearray1 = new byte[(int) F.length()];

			FileInputStream fin = new FileInputStream(F);
			BufferedInputStream bin = new BufferedInputStream(fin);
			bin.read(bytearray1, 0, bytearray1.length);

			System.out.println("Sending Files to Client From Server...");

			for (CLIENT E : checkToCreateServerOrNotFrame.CLIENT) 
			{
				if (E.number_of_the_person == receiver1) 
				{
					output_stream = E.socket.getOutputStream();
					String SS = "<<START>> " + sender1 + " " + F.getName()
							+ " " + (int) F.length();
					E.data_Output_Stream.writeUTF(SS);
					output_stream.write(bytearray1, 0, bytearray1.length);
					break;
				}
			}

			System.out
					.println("--File Sending From Server to CLient Successful... "
							+ bytearray1.length);

			bin.close();
			fin.close();
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, "Sending to Client From Server Error");
		}
		System.out.println("File Sent From Server to Client.");
	}

}

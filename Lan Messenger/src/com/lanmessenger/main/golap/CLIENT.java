package com.lanmessenger.main.golap;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class CLIENT 
{	
	/*
	 * empty constructor
	 */
	public CLIENT()
	{
		
	}
	
	/*
	 * declaring part of the CLIENT class
	 */
	public DataInputStream data_Input_Stream = null;
	public DataOutputStream data_Output_Stream = null;
	public String person_name;
	public int number_of_the_person;
	public Socket socket;
}

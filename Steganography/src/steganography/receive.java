package steganography;
import javax.swing.*;

import java.awt.event.*;
import java.awt.*;

import javax.swing.ImageIcon;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import java.net.*;	//this file for networking
import java.io.*;
class Receive
{
	JToolBar tb;
	JButton b1,b2,b3,b4,b5,b6,b7,b8,b9,b10,b11;
	JTextField tf;
	JTextField targetPath;
	JTextField fileName;
	Socket s;
	ObjectInputStream ois;
	ObjectOutputStream oos;
	AudioStream audioStream;
	public Receive()
	{
		String IP = JOptionPane.showInputDialog("Please Enter Server's IP Address:");
		JFrame f1 = new JFrame("CLIENT!!!");
		f1.setSize(400,400);
		f1.setVisible(true);
		f1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel p1 = new JPanel();
		p1.setLayout(null);
		f1.add(p1);
		tb = new JToolBar();
		tb.setBounds(150,10,75,50);
		p1.add(tb);
		b1 = new JButton();
		b2 = new JButton();
		b9 = new JButton("RECEIVE");
		b9.setBounds(50,275,100,40);
		p1.add(b9);
		b10 = new JButton("BROWSE");
		b10.setBounds(240,275,100,40);
		p1.add(b10);
		tf = new JTextField("Server Message",10);
		tf.setBounds(125,160,150,20);
		tf.setFont(new Font("Verdana",Font.PLAIN,14));
		p1.add(tf);
		targetPath = new JTextField("Target Folder",10);
		targetPath.setBounds(125,190,150,20);
		targetPath.setFont(new Font("Verdana",Font.PLAIN,14));
		p1.add(targetPath);
		fileName = new JTextField("File Name",10);
		fileName.setBounds(125,210,150,20);
		fileName.setFont(new Font("Verdana",Font.PLAIN,14));
		p1.add(fileName);
		b1.setIcon(new ImageIcon("C:\\Users\\Mohib\\Desktop\\shafiya\\Play24.gif"));
		b2.setIcon(new ImageIcon("C:\\Users\\Mohib\\Desktop\\shafiya\\Pause24.gif"));
		tb.add(b1);
		tb.add(b2);
		tb.setFloatable(false);
		b1.addActionListener(new Play());
		b2.addActionListener(new Pause());
		b9.addActionListener(new Receive1());
		b10.addActionListener(new Play1());
		tf.addActionListener(new TField());

		/*****************************************************************************************
			ADDING NECESSARY CODE FOR THE CLIENT
		*******************************************************************************************/

		try
		{
			s=new Socket(IP,10);
			ois=new ObjectInputStream(s.getInputStream());
			oos=new ObjectOutputStream(s.getOutputStream());
			//startChat();
		}
		catch(Exception e){}
		}//constructor
		void startChat()throws IOException
		{
			try
			{
				Transfer tobj=(Transfer)ois.readObject();
				System.out.println("bytes recieved");
				byte [] b=tobj.getBytes();
				int length=tobj.getLength();
				JFileChooser fileChooser = new JFileChooser();
				
	 			// For Directory
	        	fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				fileChooser.setDialogTitle("Target Location");
				int rVal = fileChooser.showOpenDialog(null);
	        			if (rVal == JFileChooser.APPROVE_OPTION) {
	          			targetPath.setText(fileChooser.getSelectedFile().toString());}
				String target=targetPath.getText();
				target=target+"\\";
				target=target+fileName.getText();
				FileToArrayOfBytes.getFileFromBytes(b,target+".wav");
				FileToArrayOfBytes.createTextFile(length,target+".txt");
				System.out.println("File created");
				String rmessage=Steganography.pop_Message(tobj);
				System.out.println(rmessage);
				rmessage=ByteToBinary.toMessageFromBinary(rmessage);
				System.out.println("Message:"+rmessage);
				tf.setText(rmessage);
			}catch(Exception e){}
		}//startchat
	public static void main(String[] args)
	{
		Receive tb1 = new Receive();
		//tb1.setVisible(true);
	}
	class Play implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			System.out.println("playing the selected audio");
			String playFile="";
			try
			{
				playFile = targetPath.getText();	//get audio File path
				if(playFile=="")
				JOptionPane.showMessageDialog(null,"NO FILE PATH SPECIFIED :-(","Cancel dialog box",JOptionPane.WARNING_MESSAGE);
				else
				{
						String file_type=playFile.substring(playFile.length()-4,playFile.length());
					System.out.println(file_type);
					if((file_type.compareTo(".wav"))!=0)
					JOptionPane.showMessageDialog(null,"File Not Compatible :-(","Cancel dialog box",JOptionPane.WARNING_MESSAGE);
					//OPENING AUDIO STREAM FOR THE AUDIO TO BE PLAYED
					else
					{
							InputStream in = new FileInputStream(playFile);
							// create an audiostream from the inputstream
							audioStream = new AudioStream(in);
							// play the audio clip with the audioplayer class
							AudioPlayer.player.start(audioStream);
					}//else
				}//else
			}catch(Exception er){System.out.println("aaa");}
		
	}
}
	class Pause implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			AudioPlayer.player.stop(audioStream);
			
		}
	}
	class Receive1 implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			
			try
			{
				startChat();
			}catch(Exception err){}
			
		}
	}
	class Play1 implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			
			JFileChooser fc = new JFileChooser();
			File fi = new File("C:/");
			File NamePath;
			int checker;
			fc.setCurrentDirectory(fi);
			fc.setDialogTitle("Choose an Audio File");
			checker = fc.showOpenDialog(b9);
			if(checker == JFileChooser.APPROVE_OPTION)
			{
				NamePath = fc.getSelectedFile();
				targetPath.setText(NamePath.getAbsolutePath());
			}
			else
			{
				JOptionPane.showMessageDialog(null,"You have clicked Cancel :-(","Cancel dialog box",JOptionPane.WARNING_MESSAGE);
			}
			byte [] b=FileToArrayOfBytes.getBytesFromFile(targetPath.getText());
			int len = 0;
			String Temp=targetPath.getText();
			String txt_file=Temp.substring(0,Temp.length()-4);
			try
			{
				System.out.println(txt_file);
				FileInputStream fis=new FileInputStream(txt_file+".txt");
				len=fis.read();
				System.out.println("len:"+len);
				fis.close();
			}catch(Exception err){}
			String mesg="";
			mesg=Steganography.pop_Message(new Transfer(b,len));
			mesg=ByteToBinary.toMessageFromBinary(mesg);
			tf.setText(mesg);
			
		}
	}
	class TField implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			JFrame f6 = new JFrame("THIS IS FOR TEXT FIELD!!!");
			f6.setSize(200,200);
			f6.setVisible(true);
			f6.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			JPanel p6 = new JPanel();
			f6.add(p6);
			JLabel l6 = new JLabel("FOCUS ON TEXT FIELD");
			p6.add(l6);
		}
	}
}

package steganography;

import java.awt.Font;
import javax.sound.sampled.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;	//this file for networking
import java.net.Socket;
import java.net.SocketException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToolBar;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
class Send
{
	JToolBar tb;
	JButton b1,b2,b3,b4,b5,b6,b7,b8,b9,b10,b11;
	JTextField tf;
	JTextField msg_field;
	ServerSocket ss;
	Socket s;
	ObjectInputStream ois;
	ObjectOutputStream oos;
	AudioStream audioStream;
	public Send()
	{
		JFrame f1 = new JFrame("SERVER!!!!");
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
		b9 = new JButton("BROWSE");
		b9.setBounds(240,180,100,40);
		p1.add(b9);
		b10 = new JButton("SEND");
		b10.setBounds(50,275,100,40);
		p1.add(b10);
		b11 = new JButton("PLAY");
		b11.setBounds(240,275,100,40);
		p1.add(b11);
		tf = new JTextField("File Path",10);
		tf.setBounds(50,190,150,20);
		tf.setFont(new Font("Verdana",Font.PLAIN,14));
		p1.add(tf);
		msg_field = new JTextField("Type Your Message",10);
		msg_field.setBounds(50,150,150,20);
		msg_field.setFont(new Font("Verdana",Font.PLAIN,14));
		p1.add(msg_field);
		b1.setIcon(new ImageIcon("C:\\Users\\Mohib\\Desktop\\shafiya\\Play24.gif"));
		b2.setIcon(new ImageIcon("C:\\Users\\Mohib\\Desktop\\shafiya\\Pause24.gif"));
		tb.add(b1);
		tb.add(b2);
		tb.setFloatable(false);
		msg_field.setVisible(true);
		b1.addActionListener(new Play());
		b2.addActionListener(new Pause());
		b9.addActionListener(new Browse());
		b10.addActionListener(new Send1());
		b11.addActionListener(new Play1());
		/**********************************************************************************
			ADDING NECESSARY CODE FOR SOCKET PROGRAMMING
		**********************************************************************************/
		System.out.println("SERVER STARTED..WAITING FOR CLIENT..");
		try
		{
		ss=new ServerSocket(10);	//specific port is the argument
		s=ss.accept();				//to establish a connection
		System.out.println("SOCKET CONNECTED : "+s);
		oos=new ObjectOutputStream(s.getOutputStream());
		}
		catch (SocketException soe)
		{
		System.out.println("Socket ERROR OCCURED "+soe);
		}
		catch (IOException ioe)
		{
		System.out.println("IO STREAM ERROR OCCURED "+ioe);
		}
		}//constructor
	
		void startChat(String input,String filepath)throws IOException
		{
		//System.out.println("SERVER..PLEASE TYPE : ");
		//String str=br.readLine();
		byte [] b=FileToArrayOfBytes.getBytesFromFile(filepath);
		int length=input.length();
		System.out.println(length);
		System.out.println("bytes recieved");
		input=ByteToBinary.toBinaryFromString(input);
		System.out.println(input);
		Transfer tobj=Steganography.push_Message(new Transfer(b,length),input);
		System.out.println("Object Recieved");
		oos.writeObject(tobj);
		oos.flush();
		}//start chat
		
	public static void main(String[] args)
	{
		Send tb1 = new Send();
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
					playFile = tf.getText();	//get audio File path
					System.out.println(playFile);
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
			AudioPlayer.player.stop(audioStream);;
		}
	}
	class Browse implements ActionListener
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
				tf.setText(NamePath.getAbsolutePath());
			}
			else
			{
				JOptionPane.showMessageDialog(null,"You have clicked Cancel :-(","Cancel dialog box",JOptionPane.WARNING_MESSAGE);
			}
		}
	}
	class Send1 implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			
			String server_msg=msg_field.getText();
			try
			{
				if(server_msg.compareTo("stop")==0)
				System.exit(0);
				String filepath=tf.getText();
				String file_type=filepath.substring(filepath.length()-4,filepath.length());
				System.out.println(file_type);
				if((file_type.compareTo(".wav"))!=0)
				JOptionPane.showMessageDialog(null,"File Not Compatible :-(","Note",JOptionPane.WARNING_MESSAGE);
				else
				startChat(server_msg,filepath);
				
			}catch(Exception err){}
		}
	}
	class Play1 implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			JFrame f5 = new JFrame("THIS IS FOR PAUSE!!!");
			f5.setSize(200,200);
			f5.setVisible(true);
			f5.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			JPanel p5 = new JPanel();
			f5.add(p5);
			JLabel l5 = new JLabel("PLAY BUTTON WAS CLICKED");
			p5.add(l5);
		}
	}
}

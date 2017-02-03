package steganography;

import java.io.Serializable;
class Transfer implements Serializable
{
	private static final long serialVersionUID = 1073158917107196276L;
	
		private byte [] bFile;
		private int length;
		Transfer(byte [] b,int len)
		{
			length=len;
			bFile=new byte[b.length];
			for(int i=0;i<b.length;i++)
			bFile[i]=b[i];
		}//constructor
		byte [] getBytes()
		{
			return bFile;
		}//return bytes from object
		int getLength()
		{
			return length;
		}//returns length of message from object	
}//class

package steganography;

class Steganography
{
	//THIS METHOD PUSHES THE MESSAGE INTO THE AUDIO BYTES
	static Transfer push_Message(Transfer t,String binary_message)
	{
		char [] msg_char=binary_message.toCharArray();		//MESSAGE TO BE ENCODED
		int blocation=44;			//skipping headers (44 bytes header in every .wav file)
		byte [] bfile=t.getBytes();		//AUDIO BYTES
		int blength=t.getLength();		
		System.out.println("message at client");
		for(int i=0;i<msg_char.length;i++)
		{
			if(msg_char[i]=='1')
			{
				if(bfile[blocation]>=0)
				{
					if(bfile[blocation]%2==0)
					bfile[blocation]++;
				}//if
				else
				{
					if((-bfile[blocation]%2)==0)
					bfile[blocation]--;
				}//else
			}//if
			else
			{
				if(bfile[blocation]>0)
				{
					if(bfile[blocation]%2==1)
					bfile[blocation]-=1;
				}//if
				else
				{
					if((-bfile[blocation]%2)==1)
					bfile[blocation]+=1;
				}//else
			}//else
			blocation++;
		}//for		//message has been embeded into the bytes,object created	
		return new Transfer(bfile,blength);
	}//pushmessage
	
	//THIS METHOD POPS THE MESSAGE FROM THE AUDIO BYTES
	static String pop_Message(Transfer t)
	{
		String binary_msg="";
		byte [] bfile=t.getBytes();
		int mlength=t.getLength();
		mlength*=8; 
		for(int i=44;i<mlength+44;i++)
		{
			if(bfile[i]%2==0)
			binary_msg=binary_msg+"0";
			else
			binary_msg=binary_msg+"1";
		}//for
		return binary_msg;
	}//pop_Message
}//class								
package steganography;
import static java.lang.Math.pow;
class ByteToBinary
{
	
	//FUNCTION CONVERTING A CHARACTER TO BINARY EQUIVAENT (8 bits)
	static String toBinaryFromChar(char ch)
	{
		int a=(int)ch;
		String s="";
		while(a>0)
		{
			int temp=a%2;
			if(temp==1)
			s=s+"1";
			else
			s=s+"0";
			a=a/2;
		}//while
		long l=s.length();
		if(l<8)
		{
			while(true)
			{
				if(l==8)
				break;
				else
				{
					s=s+"0";
					l++;
				}//else
			}//while
		}//if
		s=new StringBuffer(s).reverse().toString();
		return s;
	}//fn

	//THIS FUNCTION CONVERTS WHOLE STRING INTO BINARY
	static String toBinaryFromString(String message)
	{
		String binary_msg="";
		char [] msg=message.toCharArray();		//CONVERTING STRING TO CHARACTER ARRAY
		for(int i=0;i<msg.length;i++)
		binary_msg=binary_msg+(toBinaryFromChar(msg[i]));
		System.out.println("message converted to binary");
		return binary_msg;
	}
	
	//THIS FUNCTION CONVERTS A BINARY STRING TO A CHARACTER STRING (THE ACTUAL MESSAGE)
	static String toMessageFromBinary(String bmsg)
	{
		int msglength=bmsg.length();
		System.out.println(msglength);
		msglength/=8;
		int w=0;
		char [] c_bmsg=bmsg.toCharArray();
		char [] msg=new char[msglength];						
		for (int i=0;i<msglength;i++)
		{
			int l=7;
			int temp=0;
			while(l>=0)
			{
				if(c_bmsg[w]=='1')
				temp+=pow(2,l);
				l--;
				w++;
			}//while
			msg[i]=(char)temp;
		}//for
		return new String(msg);
	}//fn
						
}//class	
	
		 
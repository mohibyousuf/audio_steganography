package steganography;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
 
class FileToArrayOfBytes
{
    static byte [] getBytesFromFile(String filepath)
    {
    	FileInputStream fileInputStream=null;
    	File file = new File(filepath);
 	byte[] bFile = new byte[(int) file.length()];
 	try {
            		//convert file into array of bytes
	    	fileInputStream = new FileInputStream(file);
	    	fileInputStream.read(bFile);
	    	fileInputStream.close();
 		
		/*for(int i=0;i<bFile.length;i++)
		{
			if(i>43)
			bFile[i]++;
		}//for*/
		 }catch(Exception e){
        	e.printStackTrace();
        	}
	return bFile;
    }
	static void getFileFromBytes(byte [] bFile,String targetPath)throws IOException
	{
		FileOutputStream fileOutputStream=null;
		try
		{
		File file2 = new File(targetPath);
		fileOutputStream = new FileOutputStream(file2);
		fileOutputStream.write(bFile);	
		System.out.println("Done");
		}catch(Exception e){}
		finally {
			fileOutputStream.close();
		}
	}//getFile
	static void createTextFile(int length,String targetPath)throws IOException
	{
		FileOutputStream fileOutputStream=null;
		FileInputStream fileInputStream=null;
		try
		{
		File file2 = new File(targetPath);
		fileOutputStream = new FileOutputStream(file2);
		fileOutputStream.write(length);	
		fileInputStream = new FileInputStream(file2);
		int l=fileInputStream.read();
		System.out.println("in file:"+l);
		System.out.println("Done");
		}catch(Exception e){}
		finally {
			fileOutputStream.close();
		}
	}
}//class
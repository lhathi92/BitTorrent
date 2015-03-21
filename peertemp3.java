
import java.io.*;
import java.util.*;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

class peertemp2 extends Thread
{
 int i=1;
 int j=1;
 public int id=0; 
Socket s=null;
ServerSocket ss=null;	
  Socket s2=null;
public int m[]=new int[50];
  int port=1025;

               public Socket connection1(Socket s,int port)
               {
	try
	{
	s = new Socket("localhost",port);

	}
	catch(IOException e)
	{
	System.out.println("socket connection error"+e);
	}
	return s;
               }
         

      
	  public void run()
	{
	        Socket s1=null;
	System.out.println("Connected to fileowner");
		try{
			System.out.println("Entered run");
  			 int x=1;
			System.out.println("Enter total number of chunks:");
			Scanner sc = new Scanner(System.in);  
			String ids = sc.nextLine();  
			id=Integer.parseInt(ids);
			int y=id%5;
			for(int t=1;t<=id;t++)
			m[t]=0;
	   if(id>5)
             		{    x=id/5;  }
	if(y>=3)
		{x=x+1; }
              else
 		{x=1; }
		
		 s1=  connection1(s,port);
		System.out.println("going to recievefile");
  
		for(int p=1;p<=x;p++)
		recieveFile(s1);
		}
			 catch (IOException ex) {
			 System.out.println("the problem is here");
		        Logger.getLogger(peertemp2.class.getName()).log(Level.SEVERE, null, ex);
    			}
            
	  catch(Exception io) {
	      System.out.println("error"+io);
 		 }
			finally
				{

				    try {
				        s1.close();
				System.out.println("Connection closed");

		for(int u=1;u<=id;u++)
		{
	 run2torecieve();
     	    try
	{
            run1tosend();
	}
	       catch(IOException ie)
	{ 
	System.out.println("run1to send error "+ie);  
	}
          
         		}
       append(id);	

				   }  catch (IOException ex) {
        			Logger.getLogger(peertemp2.class.getName()).log(Level.SEVERE, null, ex);
    				}    
		}
        }


	public void recieveFile(Socket s) throws IOException
	{
	    try
    		{
		System.out.println("Entered recievefile");
		int bytesRead,q=1,x1;
		InputStream in = s.getInputStream();

	            DataInputStream clientData = new DataInputStream(in);
		int x = clientData.readInt();

		System.out.println("x="+x);

	       String fileName = clientData.readUTF();
		System.out.println("FileName="+fileName);

	            DataOutputStream output = new DataOutputStream(new FileOutputStream(new File("client2/",fileName)));
          		  long size = clientData.readLong();
	System.out.println("size="+size);
	
        
        	   byte[] buffer = new byte[1024];

		while (size > 0 && (bytesRead = clientData.read(buffer, 0, (int) Math.min(buffer.length, size))) != -1) {
		                output.write(buffer, 0, bytesRead);
		                size -= bytesRead;
            			}

	            System.out.println("File "+fileName+" received from Server.");
    		}

	    catch(IOException e)
    	{
      		  System.out.println("ioexception"+e);
  		  }
 }
   

public void run1tosend() throws IOException
{

ss=new ServerSocket(14002);

	for(int p=1;p<=id;p++)
			{
			String file_name=String.valueOf(p);
			 File f = new File("client2/file_name");
				if(f.exists())
					{
				    System.out.println("success");
					}
				else{
  				 sendfile(p,ss.accept());
				       }
			}
}


public void sendfile(int h,Socket s2) throws IOException

{
	try
	{
	Socket s3=null;
	s3=s2;
	String k=String.valueOf(h);
	File myFile = new File(k);
            byte[] mybytearray = new byte[(int) myFile.length()];

            FileInputStream fis = new FileInputStream(myFile);
	   BufferedInputStream bis = new BufferedInputStream(fis);
	

	System.out.println("File name="+myFile);

	  DataInputStream dis = new DataInputStream(bis);
            dis.read(mybytearray, 0, mybytearray.length);

            OutputStream os = s2.getOutputStream();

            //Sending file size to the server

            DataOutputStream dos = new DataOutputStream(os);

            dos.writeLong(mybytearray.length);


	 BufferedOutputStream bos = new BufferedOutputStream(os);
            bos.write(mybytearray, 0, mybytearray.length);

System.out.println("file "+k+" sent to neighbour");

	}
		catch(IOException e)
		{
		System.out.println("ioexception"+e);
		append(id);
		}
	}



  public void run2torecieve()
	{
	        Socket s1=null;
	System.out.println("Connected to neighbour");
			try{
			System.out.println("Entered run");

			 s1=  connection1(s,14003);
			System.out.println("going to recievefile");
  
			for(int p=1;p<=id;p++)
			{
			String file_name=String.valueOf(p);
			 File f = new File("client2/file_name");
				if(f.exists())
					{
				    System.out.println("success");
					}
				else{
  				 recievefile(s1,p);
				       }
			}
	}
		  catch(Exception io) {
		      System.out.println("error"+io);
  			}
				finally
					{

  					  try {
   					     s1.close();
				System.out.println("Connection closed");
  					  } 
					catch (IOException ex) {
				        Logger.getLogger(peertemp2.class.getName()).log(Level.SEVERE, null, ex);
    						}    
					}
        }


	public void recievefile(Socket s,int p) throws IOException
	{
		    try
			    {
			System.out.println("Entered recievefile");
			int bytesRead;
		        InputStream in = s.getInputStream();

		            DataInputStream clientData = new DataInputStream(in);
		       String fileName =String.valueOf(p);
			System.out.println("FileName="+fileName);

	            DataOutputStream output = new DataOutputStream(new FileOutputStream(new File("client2/",fileName)));
		            long size = clientData.readLong();
		System.out.println("size="+size);

           byte[] buffer = new byte[1024];

		while (size > 0 && (bytesRead = clientData.read(buffer, 0, (int) Math.min(buffer.length, size))) != -1) {
		                output.write(buffer, 0, bytesRead);
		                size -= bytesRead;
            				}

            System.out.println("File "+fileName+" received from neighbour.");
	    }
		    catch(IOException e)
    			{
		        System.out.println("ioexception"+e);
    			}
 }


	public void append(int id) throws IOException
	{
	System.out.println("Entered append");
	int bytesread=0;
	int mbytes;
		String op1="op";
	File op=new File("client2/"+op1);

	for(int i=1;i<=id;i++)
	{
	String fnm=String.valueOf(i);
	File f=new File("client2/"+fnm);
	if(f.exists())
	{

	try
	{
		FileInputStream fi=new FileInputStream(fnm);
		BufferedInputStream bi=new BufferedInputStream(fi);

		FileOutputStream fo=new FileOutputStream("client2/op");
		BufferedOutputStream bo=new BufferedOutputStream(fo);

		mbytes=fnm.length();
		byte[] buffer1=new byte[(int)fnm.length()];

		 bi.read(buffer1, 0, buffer1.length);

		   bo.write(buffer1, bytesread, mbytes);
			bytesread=bytesread+mbytes;
	}

	catch(IOException x)
	{
		System.out.println("error in appending files="+x);
	}
		}
		else
	System.out.println("File missing");
		}

}


public static void main(String args[])
	{
 
peertemp2 client = new peertemp2();

 client.run();


}
}

import java.io.*;
import java.net.*;
import java.util.*;
import java.io.DataInputStream;
import java.util.logging.Level;
import java.util.logging.Logger;


public class FileOwner3{
       public static long chunkSize = 100000;
        public static int id=0;
        public static long fsize=0;
	    // public int j=1;

    public static void main(String arg[]) throws IOException
    {
     
      
System.out.println("server is on waiting for connection");
int i=1;
System.out.println("Enter filename:");
Scanner sc = new Scanner(System.in);  
        String file_name = sc.nextLine();  
        try {
            split(file_name);
        } catch (IOException ex) {
            Logger.getLogger(FileOwner3.class.getName()).log(Level.SEVERE, null, ex);
        }

        ServerSocket listener = new ServerSocket(1025);
		int clientNum = 1;
        	try {
            		while(clientNum<=5) {
	System.out.println("hi i am server");

		new Handler(listener.accept(),clientNum).start();
	
	
				System.out.println("Client "  + clientNum + " is connected!");
				clientNum++;
            			}
        	} finally {
            		listener.close();
        	} 
 
    	}
    

public static void split(String filename) throws FileNotFoundException, IOException
		{

// open the file
		BufferedInputStream in = new BufferedInputStream(new FileInputStream(filename));
		
		// get the file length
		File f = new File(filename);
		long fileSize = f.length();
              		  fsize=fileSize;
		System.out.println("Splitting");
		// loop for each full chunk
		int subfile;
		for (subfile = 1; subfile < fileSize / chunkSize; subfile++)
			{
			// open the output file
                                String fnm=String.valueOf(subfile);
	//System.out.println("fnm="+fnm);

			BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(fnm));
		// write the right amount of bytes
			for (int currentByte = 0; currentByte < chunkSize; currentByte++)
				{
				// load one byte from the input file and write it to the output file
				out.write(in.read());
				}
				System.out.println(".");
			// close the file
			id++;

			//System.out.println("file id="+id);
			out.close();
			}
		
		// loop for the last chunk (which may be smaller than the chunk size)
		if (fileSize != chunkSize * (subfile - 1))
			{
			// open the output file
                          String fnm=String.valueOf(subfile);
			BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(fnm));
			
			// write the rest of the file
			int b;
			while ((b = in.read()) != -1)
				out.write(b);
			id++;	
                     
			// close the file
			out.close();	
				
			}
		
		// close the file
	System.out.println("The file is split in "+id+" parts");
		in.close();
                
                }

}

	/**
     	* A handler thread class.  Handlers are spawned from the listening
     	* loop and are responsible for dealing with a single client's requests.
     	*/
     class Handler extends Thread {
        	private String message;    //message received from the client
		private String MESSAGE;    //uppercase message send to the client
		private Socket connection;
        	private ObjectInputStream in;	//stream read from the socket
        	private ObjectOutputStream out; //stream write to the socket
                private DataInputStream in1;	//stream read from the socket
        	private DataOutputStream out1; 
		private int no;		//The index number of the client

        	public Handler(Socket connection, int no) {
            		this.connection = connection;
	    		this.no = no;
        	}

FileOwner3 fo=new FileOwner3();
     
int i=1;
        public void run() {
 		
                  try{

int x=0;
	 int j=1;
int id=fo.id;

System.out.println("id="+id);

 int y=id%5;
        long flsize=fo.fsize;  
              
              if(id>5)
             {    x=id/5;  }
	if(y>=3)
{	x=x+1; }
              else
 {                 x=5; }


 System.out.println("file size="+flsize);

                           while(i<=x && j<=id)
                   {
	
		   j=3*(no-1)+i;

		sendFile(x,id,j);

		++i; 

	}

}
		catch(IOException ioException){
			System.out.println("Disconnect with Client with error" + no);
		}
                        
		finally{
			//Close connections
			try{
				
				connection.close();
				System.out.println("Disconnect with Client " + no);
			}
			catch(IOException ioException){
				System.out.println("Disconnect with Client coz of error " + no);
			}
		}
	}


public void sendFile(int x,int id,int j) throws IOException
{
	try
	{
	int x1=x;
	int id1=id;
	int j1=j;

               
	String k=String.valueOf(j1);

		
		File myFile = new File(k);

            byte[] mybytearray = new byte[(int) myFile.length()];

	System.out.println("filesize "+myFile.length());

            FileInputStream fis = new FileInputStream(myFile);
	
	   BufferedInputStream bis = new BufferedInputStream(fis);

	System.out.println("File name="+myFile)



  DataInputStream dis = new DataInputStream(bis);

            dis.read(mybytearray, 0, mybytearray.length);




            OutputStream os = connection.getOutputStream();
 


            //Sending file name and file size to the server
            DataOutputStream dos = new DataOutputStream(os);



dos.writeInt(x);

dos.writeUTF(myFile.getName());



            dos.writeLong(mybytearray.length);





 BufferedOutputStream bos = new BufferedOutputStream(os);
            bos.write(mybytearray, 0, mybytearray.length);

System.out.println("writes file");

   
  
System.out.println("file "+j1+" sent to client"+no);




	}
		catch(IOException e)
		{
		System.out.println("ioexception"+e);
		}

                   
	}
			
		
        }


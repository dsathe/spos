package macros;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;


public class macro {
	LinkedList<String> mdt =new LinkedList<String>();
	int mntid;
	LinkedList<nametable> mnt=new LinkedList<nametable>();
	
	public macro()
	{
		mntid=0;
	}
	
	void pass1() throws IOException
	{
		BufferedReader br=new BufferedReader(new FileReader ("/home/TE/3167/spos/workspace/macro.asm"));
		String line="";
		FileWriter fw=new FileWriter(new File("/home/TE/3167/spos/workspace/Macros/intermediate.asm"));
		BufferedWriter out =new BufferedWriter(fw);
		Boolean inmacro=false;
		while(true)
		{
			line=br.readLine();
			//System.out.println(line);
			if(line.contains("\tEND"))
			{
				//System.out.println("Hi checked");
				for(int i=0;i<mdt.size();i++)
				{
					String s=mdt.get(i);
					System.out.println(s);
				}

				for(int i=0;i<mntid;i++)
				{
					nametable n=mnt.get(i);
					System.out.println(n.getname()+"  "+n.getstartid());
				}
				break;
			}
			if(line.contains("MACRO"))
			{
				String[] lp=new String[4];
				lp=line.split("\t");
				
				int count=0;
			
				for(int i=0;i<lp[3].length();i++)
				{
					if(lp[3].charAt(i)=='&')
					{
						count++;
					}
				}
				nametable nt=new nametable(lp[2],count,mdt.size());
				mnt.add(nt);
				mntid++;
				System.out.println("Macro added !!!");
				//System.out.println(nt.getname());
				inmacro=true;
				continue;
			}
			if(line.contains("MEND"))
			{
				mdt.add(line);
				System.out.println("Macro completed !!!");
				inmacro=false;
				continue;
			}
			if(inmacro==true)
			{
				mdt.add(line);	
			}
			else
			{
				out.write(line);
				out.flush();
			}
			
			
		}
		out.close();
		br.close();
		
	}
	
	public static void main(String args[]) throws IOException
	{
		macro m=new macro();
		m.pass1();
	}
}

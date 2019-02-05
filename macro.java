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
		String arg[]=new String[4];
		int argno=0;
		while(true)
		{
			line=br.readLine();
			//System.out.println(line);
			if(line.contains("\tEND"))
			{
				System.out.println("MDT contents");
				out.write(line);
				for(int i=0;i<mdt.size();i++)
				{
					String s=mdt.get(i);
					System.out.println(s);
				}
				System.out.println("MNT contents");
				for(int i=0;i<mntid;i++)
				{
					nametable n=mnt.get(i);
					System.out.println(n.getname()+"  "+n.nop+"  "+n.getstartid());
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
				argno=count;
				nametable nt=new nametable(lp[2],count,mdt.size());
				mnt.add(nt);
				mntid++;
				arg=lp[3].split(",");
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
				String line1=line;
				String changed="";
				int flag=1;
				if(line1.contains("&"))
				{
					int pos=line1.indexOf("&");
					String args=line1.substring(pos,pos+2);
					//System.out.println(argno);
					for(int i=0;i<argno;i++)
					{
						if(arg[i].equals(args))
						{
							String rep="#"+(i+1);
							changed=line1.replace(args, rep);
							flag=0;
						}
					}
				}
				if(flag==0)
				{
					mdt.add(changed);
				}
				else
				{
					mdt.add(line1);
				}
			}
			else
			{
				out.write(line+"\n");
				out.flush();
			}
			
			
		}
		out.close();
		br.close();	
	}
	
	
	void pass2() throws IOException
	{
		BufferedReader br=new BufferedReader(new FileReader ("/home/TE/3167/spos/workspace/Macros/intermediate.asm"));
		String line="";
		FileWriter fw=new FileWriter(new File("/home/TE/3167/spos/workspace/Macros/macroexpan.asm"));
		BufferedWriter out =new BufferedWriter(fw);
		Boolean inmacro=false;
		String arg[]=new String[4];
		int argno=0;
		while(true)
		{
			line=br.readLine();
			String lp[]=new String[4];
			lp=line.split("\t");
			int flag=0;
			if(line.contains("\tEND"))
			{
				out.write(line);
				break;
			}
			for(int i=0;i<mnt.size();i++)
			{
				nametable n=mnt.get(i);
				if(lp[1].contains(n.getname()))
				{
					arg=lp[2].split(",");
					flag=1;
					int ptr=n.getstartid();
					while(true)
					{
						
					}
				}
			}
			if(flag==1)
			{
				out.write(line);
			}
		}
		br.close();
		out.close();
	}
	
	public static void main(String args[]) throws IOException
	{
		macro m=new macro();
		m.pass1();
	}
}

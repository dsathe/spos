package assembler;

import java.util.*;
import java.io.*;


class sym
{
	String name;
	int add;
	int len;
	
public	sym()
	{
		add=len=0;
	}
	String getname()
	{
		return name;
	}
	int getadd()
	{
		return add;
	}
}

class  lit
{
	String name;
	int add;
public lit()
	{
		add=0;
	}
}

class pass1 {
	HashMap<String,String> is=new HashMap<String,String>();
	//HashMap<String,String> ad=new HashMap<String,String>();
	HashMap<String,String> cc=new HashMap<String,String>();
	
	ArrayList<sym> symtab=new ArrayList<sym>();
	ArrayList<lit> littab=new ArrayList<lit>();
	
	int[] pooltab=new int[10];
	
	int ptptr=0;
	int loc_cntr=0;
	int symptr=0;
	int litptr=0;
	
	public pass1()
	{
		is.put("STOP","00");
		is.put("ADD","01");
		is.put("SUB","02");
		is.put("MULT","03");
		is.put("MOVER","04");
		is.put("MOVEM","05");
		is.put("COMP","06");
		is.put("BC","07");
		is.put("DIV","08");
		is.put("READ","09");
		is.put("PRINT", "10");
		/*
		ad.put("START","01");
		ad.put("END","02");
		ad.put("EQU","03");
		ad.put("LTORG","04");
		ad.put("ORIGIN","05");
		*/
		cc.put("LT","01");
		cc.put("LE","02");
		cc.put("EQ","03");
		cc.put("GT","04");
		cc.put("GE","05");
		cc.put("ANY","06");
		pooltab[ptptr]=0;
	}
	
	Boolean islabel(String l)
	{
		if(l.charAt(0)=='\t')
		{
			return false;
		}
		return true;
	}
	
	void genint() throws IOException
	{
		BufferedReader br=new BufferedReader(new FileReader ("/home/TE/3167/spos/workspace/demo.asm"));
		String line=br.readLine();
		FileWriter fw=new FileWriter("intermediate.asm",true);
		BufferedWriter out =new BufferedWriter(fw);
		
		while(!line.equals("END"))
		{
			System.out.println(line);
			line=br.readLine();
			String[] lp=new String[3];
			lp=line.split("\t",3);
			
			if(islabel(line))	//if label exist
			{
				sym s=null;int flag=0;
				for(int i=0;i<symtab.size();i++)
				{
					s=symtab.get(i);
					if(s.getname()==lp[0])
					{
						flag=1;break;
					}
					if(flag==1)
					{
						s.add=loc_cntr;
					}
					else
					{
						s=new sym();
						s.name=lp[0];
						s.add=loc_cntr;
					}
				}
			}
			if(lp[1]=="LTORG")
			{
				for(int j=pooltab[ptptr];j<litptr;j++)
				{
					lit l=littab.get(j);
					l.add=loc_cntr;
					loc_cntr++;
				}
				ptptr++;
				pooltab[ptptr]=litptr;
			}
			
			if(lp[1].equals("START"))
			{
				loc_cntr=Integer.parseInt(lp[2]);
			}
			if(lp[1].equals("ORIGIN"))
			{
				if(lp[2].length()==1)
				{
					for(int i=0;i<symtab.size();i++)
					{
						sym s=symtab.get(i);
						if(s.name==lp[2])
						{
							loc_cntr=s.add;
						}
					}
				}
				else if(lp[2].length()==3)
				{
					String[] exp=new String[2];
					exp=lp[2].split("+");
					int offset=Integer.parseInt(exp[1]);
					for(int i=0;i<symtab.size();i++)
					{
						sym s=symtab.get(i);
						if(s.name==lp[2])
						{
							loc_cntr=s.add+offset;
						}
					}
				}
			}
			if(lp[1].equals("EQU"))
			{
				sym s=null;
				for(int i=0;i<symtab.size();i++)
				{
					s=symtab.get(i);
					if(s.name==lp[0])
					{
						break;
					}
				}
				if(lp[2].length()==1)
				{
					for(int i=0;i<symtab.size();i++)
					{
						sym s1=symtab.get(i);
						if(s1.name==lp[2])
						{
							s.add=s1.add;
						}
					}
				}
				else if(lp[2].length()==3)
				{
					String[] exp=new String[2];
					exp=lp[2].split("+");
					int offset=Integer.parseInt(exp[1]);
					for(int i=0;i<symtab.size();i++)
					{
						sym s1=symtab.get(i);
						if(s1.name==lp[2])
						{
							s.add=s1.add+offset;
						}
					}
				}
			}
			
			if(lp[1].equals("DS"))
			{
				String write="";
				write=write+"(DL,02)";
				int size=Integer.parseInt(lp[2]);
				sym s=null;
				for(int i=0;i<symtab.size();i++)
				{
					s=symtab.get(i);
					if(s.getname()==lp[0])
					{
						s.add=loc_cntr;
						loc_cntr=+size;
						write=write+"\t(C,"+i+")\n";
						break;
					}
					
				}
				out.append(write);
						
			}
			if(lp[1].equals("DC"))
			{
				String write="";
				write=write+"(DL,01)";
				sym s=null;
				for(int i=0;i<symtab.size();i++)
				{
					s=symtab.get(i);
					if(s.getname()==lp[0])
					{
						s.add=loc_cntr;
						loc_cntr=+1;
						write=write+"\t(C,"+i+")\n";
						break;
					}
					
				}
				out.append(write);
			}
			if(is.containsKey(lp[1]))
			{
				if(lp[1].equals("MOVER")||lp[1].equals("MOVEM")||lp[1].equals("ADD")||lp[1].equals("SUB")||lp[1].equals("MULT")||lp[1].equals("DIV"))
				{
					String write="(IS,"+is.get(lp[1])+")\t";
					String[] exp=new String[2];
					exp=lp[2].split(",");
					
					if(exp[1]=="AREG")
					{
						write=write+"(1)\t";
					}
					if(exp[1]=="BREG")
					{
						write=write+"(2)\t";
					}
					if(exp[1]=="CREG")
					{
						write=write+"(3)\t";
					}
					if(exp[1]=="DREG")
					{
						write=write+"(4)\t";
					}
					int flag=0;
					for(int i=0;i<symtab.size();i++)
					{
						sym s=symtab.get(i);
						if(s.getname()==exp[2])
						{
							write=write+"(S,"+i+")/n";
							flag=1;
							break;
						}
					}
					if(flag!=0)
					{
						
					}
				}
			}
			
		}
		
		br.close();
		out.close();
	}
	
	
}

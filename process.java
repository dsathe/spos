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
	String getname()
	{
		return name;
	}
	int getadd()
	{
		return add;
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
	
	Boolean isliteral(String l)
	{
		if(l.charAt(0)=='=')
		{
			return true;
		}
		return false;
	}
	
	void genint() throws IOException
	{
		BufferedReader br=new BufferedReader(new FileReader ("/home/TE/3167/spos/workspace/demo.asm"));
		String line="";
		FileWriter fw=new FileWriter("intermediate.asm",true);
		
		while(true)
		{
			BufferedWriter out =new BufferedWriter(fw);
			line=br.readLine();
			System.out.println(line);
			String[] lp=new String[3];
			lp=line.split("\t");
			if(islabel(line))	//if label exist
			{
				sym s=null;int flag=0;
				if(symtab.size()==0)
				{
					s=new sym();
					s.name=lp[0];
					s.add=loc_cntr;
					symtab.add(s);
					//System.out.print("HI give add"+loc_cntr);
				}
				else
				{	
					for(int i=0;i<symtab.size();i++)
					{
						s=symtab.get(i);
						if(s.getname().equals(lp[0]))
						{
							flag=1;break;
						}	
					}
					if(flag!=1)
					{
						s=new sym();
						s.name=lp[0];
						s.add=loc_cntr;
						//System.out.print("HI give add"+loc_cntr+"GOT"+s.name);
						symtab.add(s);
					}
				}
			}
			
			if(lp[1].equals("LTORG"))
			{
				for(int j=pooltab[ptptr];j<litptr;j++)
				{
					lit l=littab.get(j);
					if (l.add==0)
					{	
						l.add=loc_cntr;
						loc_cntr++;
					}
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
				if(!lp[2].contains("+"))
				{
					for(int i=0;i<symtab.size();i++)
					{
						sym s=symtab.get(i);
						if(s.name.equals(lp[2]))
						{
							loc_cntr=s.add;
						}
					}
				}
				else
				{
					String[] exp=new String[2];
					//System.out.print(lp[2]);
					exp=lp[2].split("\\+");
					int offset=Integer.parseInt(exp[1]);
					for(int i=0;i<symtab.size();i++)
					{
						sym s=symtab.get(i);
						if(s.name.equals(exp[0]))
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
					if(s.name.equals(lp[0]))
					{
						break;
					}
				}
				if(!lp[2].contains("+"))
				{
					//System.out.println(s.name);
					for(int i=0;i<symtab.size();i++)
					{
						sym s1=symtab.get(i);
						if(s1.name.equals(lp[2]))
						{
							System.out.println(s1.name);
							s1.add=s.add;
						}
					}
				}
				else 
				{
					String[] exp=new String[2];
					exp=lp[2].split("\\+",2);
					int offset=Integer.parseInt(exp[1]);
					for(int i=0;i<symtab.size();i++)
					{
						sym s1=symtab.get(i);
						if(s1.name==lp[2])
						{
							s.add=s1.add+offset;
							System.out.println("Settting the address of"+s.name+"to"+s.add);
						}
					}
				}
			}
			
			if(lp[1].equals("DS"))
			{
				String write="";
				write=write+loc_cntr+")\t"+"(DL,02)";
				int size=Integer.parseInt(lp[2]);
				sym s=null;
				int flag=0;
				for(int i=0;i<symtab.size();i++)
				{
					s=symtab.get(i);
					if(s.getname().equals(lp[0]))
					{
						s.add=loc_cntr;
						loc_cntr+=size;
						write=write+"\t(S,"+i+")\n";
						System.out.println(write);
						break;
					}
					
				}
				out.append(write);
						
			}
			if(lp[1].equals("DC"))
			{
				String write="";
				write=write+loc_cntr+")\t"+"(DL,01)";
				sym s=null;
				for(int i=0;i<symtab.size();i++)
				{
					s=symtab.get(i);
					if(s.name.equals(lp[0]))
					{
						s.add=loc_cntr;
						loc_cntr=loc_cntr+1;
						write=write+"\t(S,"+i+")\n";
						System.out.println(write);
						break;
					}
					
				}
				out.append(write);
			}
			if(is.containsKey(lp[1]))
			{
				if(lp[1].equals("MOVER")||lp[1].equals("MOVEM")||lp[1].equals("ADD")||lp[1].equals("SUB")||lp[1].equals("MULT")||lp[1].equals("DIV"))
				{
					String write=loc_cntr+")\t"+"(IS,"+is.get(lp[1])+")\t";
					String[] exp=new String[2];
					exp=lp[2].split(",");
					
					if(exp[0]=="AREG")
					{
						write=write+"(1)\t";
					}
					if(exp[0]=="BREG")
					{
						write=write+"(2)\t";
					}
					if(exp[0]=="CREG")
					{
						write=write+"(3)\t";
					}
					if(exp[0]=="DREG")
					{
						write=write+"(4)\t";
					}
					//System.out.println(exp[1]);
					if(isliteral(exp[1]))
					{	
						String literals=exp[1].substring(2,exp[1].length()-1);
						int flag1=0;
						for(int j=pooltab[ptptr];j<litptr;j++)
						{
							lit l=littab.get(j);
							if(literals==l.getname())
							{
								write=write+"(L"+j+")\n";
								System.out.println(write);
								flag1=1;
								break;
							}
						}
						if(flag1==0)
						{
							lit l=new lit();
							l.name=literals;
							l.add=0;
							littab.add(l);
							litptr++;
							write=write+"(L,"+litptr+")\n";
						}
						loc_cntr++;
					}
					else
					{
						int flag=0;
						for(int i=0;i<symtab.size();i++)
						{
							sym s=symtab.get(i);
						
							if(s.name==exp[1])
							{
								System.out.print("hiohfr");
								write=write+"(S,"+i+")/n";
								System.out.println(write);
								flag=1;
								break;
							}
						}
						if(flag==0)
						{
							sym s=new sym();
							s.name=exp[1];
							s.add=loc_cntr;
							symtab.add(s);
							write=write+"(S,"+symptr+")\n";
							symptr++;
						}
						loc_cntr++;
					}
					System.out.println(write);
					out.append(write);
				}
				
			}
			if(line.contains("END"))
			{
				String write=loc_cntr+")\t"+"(AD,02)"+"\n";
				System.out.println(write);
				out.append(write);
				break;
			}
		}
		
		br.close();
	}
	
	
}

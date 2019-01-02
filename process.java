package sch;

import java.util.*;

class job
{
	int at,bt,id,pr;
	
	
	job()
	{
		at=bt=id=pr=0;
	}
	
	void accept(int i,int b,int a)
	{
		id=i;
		bt=b;
		at=a;
	}
	
	void acc(int i,int b,int a,int p)
	{
		id=i;
		bt=b;
		at=a;
		pr=p;
	}
	
	void display()
	{
		System.out.println("Job id:-"+id+" Burst time:-"+bt+" Arrival time:-"+at+"\n");
	}
}


public class process {

	void fcfs()
	{
		Scanner sc=new Scanner(System.in);
		System.out.println("Using FCFS :-\nEnter no. of operations:-");
		int jcnt=sc.nextInt();
		int a,b,idd;
		job j[]=null;
		j=new job[jcnt];
		for(int i=0;i<jcnt;i++)
		{
			j[i]=new job();
			System.out.println("Enter the job id:-");
			idd=sc.nextInt();
			System.out.println("Enter the burst time:-");
			b=sc.nextInt();
			System.out.println("Enter the arrival time:-");
			a=sc.nextInt();
			j[i].accept(idd, b, a);
		}
		
		LinkedList l1=new LinkedList<job>();
		int count=0,pcnt=0;
		while(pcnt<jcnt)
		{
			for(int i=0;i<jcnt;i++)
			{
				if(j[i].at==count)
				{
					l1.addLast(j[i]);
					pcnt++;
				}
			}
			count++;
		}
		int i=0;
		while(i<jcnt)
		{
			job jj=(job) l1.get(i);
			jj.display();
			i++;
		}
		sc.close();
	}
	
	void sjf()
	{
		Scanner sc=new Scanner(System.in);
		System.out.println("Using SJF :-\nEnter no. of operations:-");
		int jcnt=sc.nextInt();
		int a,b,idd;
		job j[]=null;
		j=new job[jcnt];
		for(int i=0;i<jcnt;i++)
		{
			j[i]=new job();
			System.out.println("Enter the job id:-");
			idd=sc.nextInt();
			System.out.println("Enter the burst time:-");
			b=sc.nextInt();
			System.out.println("Enter the arrival time:-");
			a=sc.nextInt();
			j[i].accept(idd, b, a);
		}
		
		LinkedList l1=new LinkedList<job>();
		int count=0,pcnt=0;
		job jb=null;
		job jb1=null;
		while(pcnt<jcnt)
		{
			for(int i=0;i<jcnt;i++)
			{
				if(j[i].at==count)
				{
					if(l1.size()==0)
					{
						l1.addLast(j[i]);
					}
					else
					{
						for(int k=0;k<l1.size();k++)
						{
							jb1=(job) l1.get(k);
							if(jb1.bt>j[i].bt)
							{
								l1.add(k, j[i]);
								break;
							}
							else if(k==l1.size()-1)
							{
								l1.addLast(j[i]);
								break;
							}
						}

					}
				}
			}	
			jb=(job) l1.getFirst();
			jb.bt--;
			if(jb.bt==0)
			{
				System.out.println("Job id completed :-"+jb.id+" Arrival time:-"+jb.at+"\n");
				l1.removeFirst();
				pcnt++;
			}
			
			count++;
		}
		sc.close();
	}
	
	void psch()
	{
		Scanner sc=new Scanner(System.in);
		System.out.println("Using Priority Scheduling :-\nEnter no. of operations:-");
		int jcnt=sc.nextInt();
		int a,b,idd,p;
		job j[]=null;
		j=new job[jcnt];
		for(int i=0;i<jcnt;i++)
		{
			j[i]=new job();
			System.out.println("Enter the job id:-");
			idd=sc.nextInt();
			System.out.println("Enter the burst time:-");
			b=sc.nextInt();
			System.out.println("Enter the arrival time:-");
			a=sc.nextInt();
			System.out.println("Enter the priority:-");
			p=sc.nextInt();
			j[i].acc(idd, b, a, p);
		}
		
		LinkedList l1=new LinkedList<job>();
		int count=0,pcnt=0;
		job jb=null;
		job jb1=null;
		
		
		while(pcnt<jcnt)
		{
			for(int i=0;i<jcnt;i++)
			{
				if(j[i].at==count)
				{
					if(l1.size()==0)
					{
						l1.addLast(j[i]);
					}
					else
					{
						for(int k=0;k<l1.size();k++)
						{
							jb1=(job) l1.get(k);
							if(jb1.pr>j[i].pr)
							{
								l1.add(k, j[i]);
								break;
							}
							else if(k==l1.size()-1)
							{
								l1.addLast(j[i]);
								break;
							}
						}

					}
				}
			}	
			jb=(job) l1.getFirst();
			jb.bt--;
			if(jb.bt==0)
			{
				System.out.println("Job id completed :-"+jb.id+" Arrival time:-"+jb.at+" Priority:-"+jb.pr+"\n");
				l1.removeFirst();
				pcnt++;
			}
			
			count++;
		}
		sc.close();
		
		
	}
	
	void rrt()
	{
		Scanner sc=new Scanner(System.in);
		System.out.println("Using Round Robin Technique :-\nEnter no. of operations:-");
		int jcnt=sc.nextInt();
		System.out.println("Enter the Time Slice:-");
		int timeslice=sc.nextInt();
		int a,b,idd;
		job j[]=null;
		j=new job[jcnt];
		for(int i=0;i<jcnt;i++)
		{
			j[i]=new job();
			System.out.println("Enter the job id:-");
			idd=sc.nextInt();
			System.out.println("Enter the burst time:-");
			b=sc.nextInt();
			System.out.println("Enter the arrival time:-");
			a=sc.nextInt();
			j[i].accept(idd, b, a);
		}
		
		LinkedList l1=new LinkedList<job>();
		int count=0,pcnt=0;int tt=0;
		job jb=null;
		job jb1=null;
		int k=0;
		while(pcnt<jcnt)
		{
			for(int i=0;i<jcnt;i++)
			{
				if(j[i].at==count)
				{
					l1.addLast(j[i]);
				}
			}	
			if(k>=l1.size())
			{
				k=0;
			}
			jb=(job) l1.get(k);
			jb.bt--;
			tt++;
			if(tt==timeslice)
			{
				k++;
			}
			if(jb.bt==0)
			{
				System.out.println("Job id completed :-"+jb.id+" Arrival time:-"+jb.at+" time:-"+(count+1)+"\n");
				l1.remove(k);
				pcnt++;
				tt=0;
			}
			
			count++;
		}
		sc.close();
	}
	
	public static void main(String args[])
	{
		process p=new process();
		//p.fcfs();
		//p.sjf();
		//p.psch();
		p.rrt();
	}
	
}


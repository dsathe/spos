package Paging;
import java.util.*;

public class page {
	LinkedList<Integer> fifo=new LinkedList<>();
	int pframe;
	Scanner sc;
	page()
	{
		pframe=0;
		sc=new Scanner(System.in);
	}
	void fcfs()
	{
		int hit=0;int pagefault=0;
		System.out.println("Enter the size of frame:-");
		pframe=sc.nextInt();
		while(true)
		{
			System.out.println("Enter the page number:-");
			int p=sc.nextInt();
			if(p<0)
			{
				break;
			}
			int found=0;
			for(int i=0;i<fifo.size();i++)
			{
				if(fifo.get(i)==p)
				{
					hit++;
					found=1;
					break;
				}
			}
			if(found==1)
			{
				continue;
			}
			pagefault++;
			if(fifo.size()<pframe)
			{
				fifo.addLast(p);
			}
			else
			{
				fifo.removeFirst();
				fifo.addLast(p);
			}
		}
		System.out.println("The fifo queue at end of task is:-");
		for(int i=0;i<fifo.size();i++)
		{
			System.out.print(fifo.get(i)+" ");
		}
		System.out.print("\nHits:-"+hit+"\nPageFault:-"+pagefault);
	}
	
	void lru()
	{
		int hit=0;int pagefault=0;
		System.out.println("Enter the size of frame:-");
		pframe=sc.nextInt();
		while(true)
		{
			System.out.println("Enter the page number:-");
			int p=sc.nextInt();
			if(p<0)
			{
				break;
			}
			int found=0;
			for(int i=0;i<fifo.size();i++)
			{
				if(fifo.get(i)==p)
				{
					hit++;
					found=1;
					fifo.remove(i);
					fifo.addLast(p);
					break;
				}
			}
			if(found==1)
			{
				continue;
			}
			pagefault++;
			if(fifo.size()<pframe)
			{
				fifo.addLast(p);
			}
			else
			{
				fifo.removeFirst();
				fifo.addLast(p);
			}
		}
		System.out.println("The fifo queue at end of task is:-");
		for(int i=0;i<fifo.size();i++)
		{
			System.out.print(fifo.get(i)+" ");
		}
		System.out.print("\nHits:-"+hit+"\nPageFault:-"+pagefault);
	}
	
	void optimal()
	{
		int hit=0;int pagefault=0;
		System.out.println("Enter the size of frame:-");
		pframe=sc.nextInt();
		int[] input=new int[]{7,0,1,2,0,3,0,4,2,3,0,2,1,2,0,1,7,0,1};
		int ptr=0;
		while(ptr<input.length)
		{
			int found=0;
			for(int i=0;i<fifo.size();i++)
			{
				if(fifo.get(i)==input[ptr])
				{
					hit++;
					found=1;
					break;
				}
			}
			if(found==1)
			{
				ptr++;
				System.out.println(fifo);
				continue;
			}
			pagefault++;
			if(fifo.size()<pframe)
			{
				fifo.addLast(input[ptr]);
				System.out.println(fifo);
			}
			else
			{
				int count=0;int remptr=0;
				for(int i=0;i<pframe;i++)
				{
					int max=0;
					for(int j=ptr+1;j<input.length;j++)
					{
						if(input[j]==fifo.get(i))
						{
							break;
						}
						max++;	
					}
					if(count<max)
					{
						remptr=i;
						count=max;
					}
					//System.out.println(count);
				}
				fifo.remove(remptr);
				fifo.addLast(input[ptr]);
				System.out.println(fifo);
			}
			ptr++;
		}
		System.out.println("The fifo queue at end of task is:-");
		for(int i=0;i<fifo.size();i++)
		{
			System.out.print(fifo.get(i)+" ");
		}
		System.out.print("\nHits:-"+hit+"\nPageFault:-"+pagefault+"\n");
	}
	
	public static void main(String args[])
	{
		page p=new page();
		System.out.println("Page replacement using First In First Out(FIFO) policy");
		p.fcfs();
		System.out.println("Page replacement using Least Recently Used(FIFO) policy");
		p.lru();
		System.out.println("Page replacement using Optimal Page Replacement Policy");
		p.optimal();				
	}
}

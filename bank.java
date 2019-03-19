package bankers;
import java.util.*;

public class bank {
	 int need[][],allocate[][],max[][],avail[][],np,nr;
	 Scanner sc;
	 bank()
	 {
		 sc=new Scanner(System.in);
	     System.out.print("Enter no. of processes and resources : ");
	     np=sc.nextInt();  
	     nr=sc.nextInt();  
	     need=new int[np][nr];  
	     max=new int[np][nr];
	     allocate=new int[np][nr];
	     avail=new int[1][nr];
	 
	     System.out.println("Enter allocation matrix -->");
	     for(int i=0;i<np;i++)
	     {
	    	 for(int j=0;j<nr;j++)
	    	 {
	        	  allocate[i][j]=sc.nextInt();
	    	 }
	     }
	     System.out.println("Enter max matrix -->");
	     for(int i=0;i<np;i++)
	     {	 
	         for(int j=0;j<nr;j++)
	         {
	        	 max[i][j]=sc.nextInt();  //max matrix
	         }
	     }    
	     System.out.println("Enter available matrix -->");
	     for(int j=0;j<nr;j++)
	     {	
	       	avail[0][j]=sc.nextInt();
	     }	
	 }
	 
	 int[][] calc_need()
	 {
	     for(int i=0;i<np;i++)
	     {    
	    	 for(int j=0;j<nr;j++)  
	    	 {
	    		need[i][j]=max[i][j]-allocate[i][j];
	    	 }
	     }  
	     return need;
	  }
	 
	  boolean check(int i)
	  {
	       for(int j=0;j<nr;j++)
	       {   
	    	   if(avail[0][j]<need[i][j])
	    	   { 
	    		   return false;
	    	   }
	       }
	    return true;
	  }
	  
	  public static void main(String args[])
	  {
		  bank b=new bank();
		  int j=0;
		  b.calc_need();
		  boolean[] done=new boolean[b.np];
		  while(j<b.np)
		  {
			  boolean allocated=false;
			  for(int i=0;i<b.np;i++)
			  {
				  if(!done[i] && b.check(i))
				  {
					  for(int k=0;k<b.nr;k++)
					  {
						  b.avail[0][k]=b.avail[0][k]-b.need[i][k]+b.max[i][k];
					  }
					  System.out.println("Allocated process : "+i);
				      allocated=done[i]=true;
				  }
			  }
			  if(!allocated)
			  {
				  break;
			  }
		  }
		  if(j==b.np)
		  {
			  System.out.println("Processes have been allocated !!!");
		  }
		  else
		  {
			  System.out.println("Processes have not been allocated !!!");
		  }
	  }
}

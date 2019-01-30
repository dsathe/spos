package macros;
import java.util.*;

public class nametable {
	String name;
	int nop;
	int startid;
	
	public nametable(String n,int p,int st)
	{
		this.name=n;
		this.nop=p;
		this.startid=st;
	}
	
	int getstartid()
	{
		return startid;
	}
	
	String getname()
	{
		return name;
	}
}

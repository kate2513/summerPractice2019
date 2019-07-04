import Graph.*;
import Boruvki.*;

public class Main
{
	public static void main(String[] arg)
	{
		Boruvki ob = new Boruvki();

		ob.makeGraphAndInit();
		ob.boruvki();
	}
}

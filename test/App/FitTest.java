package Test.App;

import app.PrettyConsole;

/**
 * Just a test class to try the "Pretty"Console class.
 * @author tguiller
 *
 */
public class FitTest {
	public static void main(String[] args){
		String s = "123456789 1234\n123456789123456789\n12345";
		System.out.println(PrettyConsole.fit(s, 4));
		System.out.println(PrettyConsole.fit(s, 5));
		System.out.println(PrettyConsole.fit(s, 6));
		
		System.out.println(PrettyConsole.HeaderTextBox("Info", "The simulation is now finished !"));
	}
}

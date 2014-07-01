package server.remote;

public class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

//		String input = "12343566x123mouse4";
//		
//		String key_input = input.substring(input.indexOf("mouse"));
//		
//		System.out.println(key_input);
		
		Example.InnerClass test = new Example.InnerClass();
		test.println();
	}

}
class Example
{
	static private int K = 1;
	
	static void display()
	{
		System.out.println("TEST Ω√¿€");
	}
	static class InnerClass
	{
		private int a=36;
		public void println()
		{
			System.out.println(K+a);
			display();
		}
	}
}
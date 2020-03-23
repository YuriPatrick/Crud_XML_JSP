package test;

import org.apache.log4j.Logger;

public class TestLogger {
	
final static Logger logger = Logger.getLogger(TestLogger.class);
	
	public static void main(String[] args) {

		TestLogger obj = new TestLogger();
		
		try{
			obj.divide();
		}catch(ArithmeticException ex){
			logger.error("Sorry, something wrong!", ex);
		}
		
		
	}
	
	private void divide(){
		
		int i = 10 /0;

	}
}

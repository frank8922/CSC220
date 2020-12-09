package prog03;
import prog02.UserInterface;
import prog02.ConsoleUI;
import prog02.GUI;

/**
 *
 * @author vjm
 */
public class Main {
  /** Use this variable to store the result of each call to fib. */
  public static double fibn;

  /** Determine the average time in microseconds it takes to calculate
      the n'th Fibonacci number.
      @param fib an object that implements the Fib interface
      @param n the index of the Fibonacci number to calculate
      @param ncalls the number of calls to average over
      @return the average time per call
  */
  public static double averageTime (Fib fib, int n, int ncalls) {
    // Get the current time in nanoseconds.
    long start = System.nanoTime();

    // Call fib(n) ncalls times (needs a loop!).
    for(int i = 0; i < ncalls; i++)
    {
      fibn = fib.fib(n);
    }

    // Get the current time in nanoseconds.
    long end = System.nanoTime();

    // Return the average time converted to microseconds averaged over ncalls.
    return (end - start) / 1000.0 / ncalls;
  }

  /** Determine the time in microseconds it takes to to calculate the
      n'th Fibonacci number.  Average over enough calls for a total
      time of at least one second.
      @param fib an object that implements the Fib interface
      @param n the index of the Fibonacci number to calculate
      @return the time it it takes to compute the n'th Fibonacci number
  */
  public static double accurateTime (Fib fib, int n) {
    // Get the time in microseconds using the time method above.
    double t = averageTime(fib, n, 1);

    // If the time is (equivalent to) more than a second, return it.
    if(t >= 1E6)
    {
    	return t;
    }



    // Estimate the number of calls that would add up to one second.
    // Use   (int)(YOUR EXPESSION)   so you can save it into an int variable.
    int numcalls = (int)(1E6/t);


    // Get the average time using averageTime above and that many
    // calls and return it.
    return averageTime(fib, n, numcalls);
  }

  private static UserInterface ui =  new GUI("Fibonacci experiments"); 

  public static void doExperiments (Fib fib) {
    System.out.println("doExperiments " + fib);
    // EXERCISES 8 and 9
	double constant = 0;
	double estimatedRuntime;
    while(true)
    {
    	//Holds the users input as a string
    	String input = ui.getInfo("Enter an integer");	
    	
    	if(input == null) { break; }
	
    	try 
    	{ 
    		//Convert string input to integer
    		int integerInput = Integer.parseInt(input);
    		if(integerInput > 0)
    		{
				//Calculate runtime
				double runtime = accurateTime(fib,integerInput);
				
				//Constant = time / O(n)
				if(constant == 0)
				{	
					runtime = accurateTime(fib,integerInput);
					constant = (runtime / fib.O(integerInput));
					System.out.println("Constant : " + constant);
					System.out.println("Runtime : " + runtime);
					
				}
					System.out.println("Constant : " + constant);
					System.out.println("Runtime : " + runtime);
					estimatedRuntime = constant * fib.O(integerInput);
					System.out.println("Estimated Runtime: " + estimatedRuntime);
			
				ui.sendMessage("n: " + integerInput 
						+ "\nfib(" + integerInput + "): " + fib.fib(integerInput) 
						+ " \nestimated runtime: " + estimatedRuntime 
						+ "\nconstant: " + constant);
				double percentageError = Math.abs(((estimatedRuntime - runtime) / runtime) * 100.00);
				
				ui.sendMessage("n: " + integerInput 
						+ "\nfib(" + integerInput + "): " + fib.fib(integerInput) 
						+ " \nactual runtime: " + runtime 
						+ "\npercentage error " + (percentageError) + "%" );	
				
				System.out.println("Estimated : " + estimatedRuntime );
				System.out.println("Actual : " + runtime );
    		}
    		else
    		{
				ui.sendMessage("Integer cannot be negative");
    		}
    	}
    	//If user enters invalid argument, catch exception and let user know input was invalid
		catch(NumberFormatException e) { 
			if(input.isEmpty()) { ui.sendMessage("Field cannont be blank");}
			else { ui.sendMessage(input + " is not an integer"); }
		}	
    }
    
  }

  public static void doExperiments () {
    // EXERCISE 10
	  String[] commands = {
				"Constant Fib", //index: 0
				"Log Fib", //index: 1
				"Linear Fib", //index: 2
				"Exponential Fib", //index: 3
				"Mystery Fib", //index 4
		"Exit"}; //index: 5
	 while(true)
	 {
	  int c = ui.getCommand(commands);
		switch (c) {
		case -1:
			System.exit(0);
		case 0:
			doExperiments(new ConstantFib());
			break;
		case 1:
			doExperiments(new LogFib());
			break;
		case 2:
			doExperiments(new LinearFib());
			break;
		case 3:
			doExperiments(new ExponentialFib());
			break;
		case 4: 
			doExperiments(new MysteryFib());
			break;
		case 5:
			System.exit(0);
			return;
		
		}
	 }
  }

  static void labExperiments () {
    // Create (Exponential time) Fib object and test it.
    Fib efib = new ExponentialFib(); //new LinearFib();
    System.out.println(efib);
    for (int i = 0; i < 11; i++)
      System.out.println(i + " " + efib.fib(i));
    
    // Determine running time for n1 = 20 and print it out.
    int n1 = 20;
    double time1 = accurateTime(efib, n1);
    System.out.println("n1 " + n1 + " time1 " + time1);
    
    // Calculate constant:  time = constant times O(n).
    double c = time1 / efib.O(n1);
    System.out.println("c " + c);
    
    // Estimate running time for n2=30.
    int n2 = 30;
    double time2est = c * efib.O(n2);
    System.out.println("n2 " + n2 + " estimated time " + time2est);

    double time3est = c * efib.O(100);
    System.out.println("100 times" + 100 +" estimated time " + time3est);
    
    // Calculate actual running time for n2=30.
    double time2 = accurateTime(efib, n2);
    System.out.println("n2 " + n2 + " actual time " + time2);
  }

  /**
   * @param args the command line arguments
   */
  public static void main (String[] args) {
//    labExperiments();
//     doExperiments(new ExponentialFib());
     doExperiments();
  }
}

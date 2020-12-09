package prog05;

import java.util.Stack;
import prog02.UserInterface;
import prog02.GUI;

public class Tower {
  static UserInterface ui = new GUI("Towers of Hanoi");

  static public void main (String[] args) {
	//{{{
	int n = getInt("How many disks?");
    if (n <= 0)
      return;
    Tower tower = new Tower(n);

    String[] commands = { "Human plays.", "Computer plays." };
    int c = ui.getCommand(commands);
    if (c == -1)
      return;
    if (c == 0)
      tower.play();
    else
      tower.solve();
  }//}}}

  /** Get an integer from the user using prompt as the request.
   *  Return 0 if user cancels.  */
  static int getInt (String prompt) {
    while (true) {//{{{
      String number = ui.getInfo(prompt);
      if (number == null)
        return 0;
      try {
        return Integer.parseInt(number);
      } catch (Exception e) {
        ui.sendMessage(number + " is not a number.  Try again.");
      }
    }
  } //}}}  
  
  //Holds the number of disks
  int nDisks;
  
  //Creates the pegs. Each peg is an ArrayStack.
  StackInt<Integer>[] pegs = (StackInt<Integer>[]) new ArrayStack[3];

  //Constructor to create the tower of hanoi game
  Tower (int nDisks) {
//	 Set the # of disks to nDisks
    this.nDisks = nDisks;
//    Loop through eachh peg and initalize it as a new array stack
    for (int i = 0; i < pegs.length; i++)
      pegs[i] = new ArrayStack<Integer>();

    // EXERCISE: Initialize game with pile of nDisks disks on peg 'a' (pegs[0]).
    for(int i = nDisks; i > 0; i--) { pegs[0].push(i); 
    System.out.println(pegs[0].peek());
    }
    

  }

  void play () {
    String[] moves = { "ab", "ac", "ba", "bc", "ca", "cb" };
    boolean isFinished = (pegs[0].empty() && pegs[1].empty());
    

    /* EXERCISE:  player has not moved all the disks to 'c'. */ 
    
    while(!isFinished)
    {
      displayPegs();
      int imove = ui.getCommand(moves);
      if (imove == -1)
        return;
      String move = moves[imove];
      int from = move.charAt(0) - 'a';
      int to = move.charAt(1) - 'a';
      move(from, to);
      isFinished = (pegs[0].empty() && pegs[1].empty());
 
    } 
		  
    if(isFinished) { ui.sendMessage("You win!");}
	  
  }
  	

  String stackToString (StackInt<Integer> peg) 
  {
    StackInt<Integer> helper = new ArrayStack<Integer>();

    // String to append items to.
    String s, sReverse;
	s = sReverse = "";

    // EXERCISE:  append the items in peg to s from bottom to top.
	
	//if the peg stack isn't empty then pop  the values
	//and add to helper stack.
   while(!peg.empty()) 
   {
	   Integer num = peg.pop();
	   s += num.toString();
	   helper.push(num);
   }

   //while the helper stack isnt empty pop the helper stack
   //and add the value back to the peg stack.
   while(!helper.empty()) {
	  peg.push(helper.pop());   
   }
   
   //iterate through the string 's' from back to front and
   //append each value to the string 'sReverse'.
   for(int i = s.length(); i > 0; i--)
   {
	   sReverse += s.charAt(i-1);
   }
   

    return sReverse;
  }

  void displayPegs () {
    String s = "";
    for (int i = 0; i < pegs.length; i++) {
      char abc = (char) ('a' + i);
      s = s + abc + " " + stackToString(pegs[i]);
      if (i < pegs.length-1)
        s = s + "\n";
    }
    ui.sendMessage(s);
  }

  void move (int from, int to) {
    // EXERCISE:  move one disk from pegs[from] to pegs[to].
    // Don't allow illegal moves:  send a warning message instead.
    // For example "Cannot place disk 2 on top of disk 1."
    // Use ui.sendMessage() to send messages
	  if(!pegs[to].empty() && !pegs[from].empty())
	  {
		 if(pegs[from].peek() < pegs[to].peek())
		 {
			 pegs[to].push(pegs[from].pop());
		 }
		 else
		 {
			 ui.sendMessage("Cannont place disk: " + pegs[from].peek() + " on disk: " + pegs[to].peek());
		 }
	  }
	  else if(pegs[to].empty() && !pegs[from].empty())
	  {
		  pegs[to].push(pegs[from].pop());
	  }
	  else if(pegs[from].empty())
	  {
		  ui.sendMessage("peg " + (char)(from + 'a') + " is empty");
	  }
  }


  // EXERCISE:  create Goal class.
  class Goal 
  {

  
    // Data.
	int n_disk; //number of disks to move 
	int from; //'from' peg A(0),B(1),C(2) 
	int to; //'to' peg A(0),B(1),C(2)
	
    // Constructor.
	Goal(int n_disk, int from, int to){
		this.n_disk = n_disk;
		this.from = from;
		this.to = to;
	}





    public String toString () {
      String[] pegNames = { "a", "b", "c" };
      String s = "Swap" + goals.peek().n_disk + " disk(s) from " + (char) ('a' +
		goals.peek().from) + "to" + (char) ('a' + goals.peek().to) + "\n";
            return s;
    }
    
  } //End of Goal Class
  


  // EXERCISE:  display contents of a stack of goals
  void printGoals(StackInt<Goal> goals) {
	  String s = "";
	  StackInt<Goal> helper = goals;
	  if(!helper.empty())
	  {
		  ui.sendMessage(s + helper.peek().n_disk + (char)('a' + helper.peek().from) +  (char)('a' + helper.peek().to));
	  }
	   
  }



  
  void solve () {
    // EXERCISE
	
  StackInt<Goal> goals = new ArrayStack<Goal>();	

	goals.push(new Goal(nDisks, 0, 2));
		while(!goals.empty()){
		  int disks = goals.peek().n_disk;
		  int from = goals.peek().from;
		  int to = goals.peek().to;
		  int x = 3 - goals.peek().from - goals.peek().to; //take the difference of the 'from' & 'to' and subtract it by 
		  												   //total number of pegs

		  displayPegs();
		  printGoals(goals);
		  goals.pop();

		  if(disks == 1)
		  {
			move(from, to);
			displayPegs();
		  }
		  else
		  {
			goals.push(new Goal(disks - 1, x, to));
			goals.push(new Goal(1,from, to));
			goals.push(new Goal(disks - 1, from, x));
		  }
		}
		ui.sendMessage("Complete");
	
	  
  }        
  
  
}

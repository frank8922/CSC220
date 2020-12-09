package prog02;

/**
 * A program to query and modify the phone directory stored in csc220.txt.
 * @author vjm
 */
public class Main {

	/** Processes user's commands on a phone directory.
      @param fn The file containing the phone directory.
      @param ui The UserInterface object to use
      to talk to the user.
      @param pd The PhoneDirectory object to use
      to process the phone directory.
	 */
	public static void processCommands(String fn, UserInterface ui, PhoneDirectory pd) {
		pd.loadData(fn);

		String[] commands = {
				"Add/Change Entry", //index: 0
				"Look Up Entry", //index: 1
				"Remove Entry", //index: 2
				"Save Directory", //index: 3
		"Exit"}; //index: 4

		String name, number, oldNumber;

		while (true) {
			int c = ui.getCommand(commands);
			switch (c) {
			case -1:
				ui.sendMessage("You shut down the program, restarting.  Use Exit to exit.");
				break;
			case 0:
				name = ui.getInfo("Enter the name of contact you wish to add/change");
				if(name == null)
				{
					break;
				}
				else
				{
					while(name.isEmpty()) 
					{
						ui.sendMessage("Name cannot be blank");
						name = ui.getInfo("Enter the name of contact you wish to add/change");
						if(name == null)
						{
							break;
						}
					}
					if(name != null) 
					{
						oldNumber = pd.addOrChangeEntry(name,(number = ui.getInfo("Enter Number")));
						if(oldNumber != null)
						{
							ui.sendMessage("Name: " + name + "\n" +
											"Old Number: " + oldNumber + "\n" +
											"New Number: " + number + "\n");
						}
						else
						{
							ui.sendMessage(name + " was added\n" + 
											"Number : "+ number + "\n");
						}
					}
					break;
				}

			case 1:
				name = ui.getInfo("Enter the name to lookup");
				if(name == null)
				{
					break;
				}
				else
				{
					while(name.isEmpty()) 
					{
						ui.sendMessage("Name cannot be blank");
						name = ui.getInfo("Enter the name of contact you wish to look up");
						if(name == null)
						{
							break;
						}
					}
					if(name != null) 
					{
						number = pd.lookupEntry(name);
						if(number == null)
						{
							ui.sendMessage(name + " is not listed in directory");
						}
						else
						{
							ui.sendMessage(name + ": " + number);
						}
					}
					break;
				}
				
			case 2:
				name = ui.getInfo("Enter the name of the contact you wish to remove");
				if(name == null)
				{
					break;
				}
				else
				{
					while(name.isEmpty()) 
					{
						ui.sendMessage("Name cannot be blank");
						name = ui.getInfo("Enter the name of contact you wish to remove");
						if(name == null)
						{
							break;
						}
					}
					if(name != null) 
					{
						number = pd.removeEntry(name);
						if(number == null)
						{
							ui.sendMessage(name + " is not listed in directory");
						}
						else
						{
							ui.sendMessage(name + ": " + number + " was removed");
						}
					}
					break;
				}
			case 3:
				pd.save();
				break;
			case 4:
				return;
			}
		}
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		String fn = "csc220.txt"; //File name
		PhoneDirectory pd = new SortedPD(); //Phone Directory
		UserInterface ui = new GUI("Phone Directory"); //User Interface thats a GUI
		processCommands(fn, ui, pd); //calls process command with the filename(fn), a User interface(GUI), and a Phone Directory(pd)
	}
}

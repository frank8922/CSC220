package prog05;
import prog02.*;


public class Stack_Postfix {
	
	public static void main() {
		StackInt<String> stack = new ListStack();
		
		UserInterface ui = new GUI("Postfix Processor");
		
		
		String message = ui.getInfo("Enter an infix expression to process");

				String result = postFixProccess(message).pop();
				ui.sendMessage(result);

	}
	
	public static void postFixProcess(StackInt<E> temp,char[] elements)
	{
		StackInt<String> stack = temp;
		
		for(int i = 0; i < elements.length; i++)
				{
					int aIndex = (i + (elements.length - 2)) % elements.length;
					int bIndex = aIndex + 1; 
					int a;
					int b;
					if(elements[i] == '*')
					{
						a =  (int)elements[aIndex]-'0';
						b =  (int)elements[bIndex]-'0';
//						stack.push(String.valueOf(elements[aIndex]));
//						stack.push(String.valueOf(elements[bIndex]));
//						stack.push(String.valueOf(elements[i]));
						stack.push(String.valueOf(a*b));
					}
					else if(elements[i] == '/')
					{
						a =  (int)elements[aIndex]-'0';
						b =  (int)elements[bIndex]-'0';
//						stack.push(String.valueOf(elements[aIndex]));
//						stack.push(String.valueOf(elements[bIndex]));
//						stack.push(String.valueOf(elements[i]));
						stack.push(String.valueOf(a/b));
					}
					else if(elements[i] == '+')
					{
						a =  (int)elements[aIndex]-'0';
						b =  (int)elements[bIndex]-'0';
						stack.push(String.valueOf(elements[aIndex]));
						stack.push(String.valueOf(elements[bIndex]));
						stack.push(String.valueOf(elements[i]));
						stack.push(String.valueOf(a+b));
					}
				}
		
				
	}
	public static StackInt<String> postFixProccess(String message)
	{
		StackInt<String> stack = new ListStack();
		char [] elements = message.toCharArray();
	}
}

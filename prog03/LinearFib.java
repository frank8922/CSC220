package prog03;

public class LinearFib implements Fib{
	
	public double fib(int n) {
		
		if(n <= 2) {
			return n;
		}
		int a = 1, b = 1, c = 0;
		for(int i = 0; i < n-2; i++) {
			c = a+b;
			a = b;
			b = c;
		}
		
		return c;
	}

	@Override
	public double O(int n) {
		// TODO Auto-generated method stub
		return n;
	}

}

/**
Driver program to test the SQueue class   
@author John Ramirez
*/

// This program must work as is with your SQueue<T> class.  
// Look carefully at all of the method calls and make sure that
// you implement your SQueue<T> methods accordingly.  
// Your output should be identical to a sample output in A1out.txt, 
// with the exception of the result of the shuffle() method 
// -- since this should be random every time you run the program

public class Ass1Test {
	public static void main(String [] args)	{
		// Testing constructor and queue methods
		System.out.println("=================================");
		System.out.println("Testing constructor and queue methods");
		System.out.println("=================================");
		int capacity = 4;
		System.out.println("created an empty queue with 4 slots");
		SQueue<Integer> theQ = new SQueue<Integer>(capacity);

		// Testing enqueue and FullQueueException
		for (int i = 0; i < 10; i++) {
			Integer newItem = Integer.valueOf(2 * i);
			try {
				theQ.enqueue(newItem);
				System.out.println("Queue after adding element  "+ newItem);
				System.out.println(theQ.toString());
			} catch (FullQueueException fe) {
				System.out.println("ERROR: "+ "Cannot add element "
							+ newItem +" " + fe.getMessage());				
				break;
			} 
		}		

		// Testing dequeue		
		try {
			Integer oldItem = theQ.dequeue();
			System.out.println("Queue after dequeing element: " + oldItem);
			System.out.println(theQ.toString());
		}
		catch (EmptyQueueException ee) {
			System.out.println("ERROR: "+ ee.getMessage());
		}		
		System.out.println();
		
		// Testing circular array management
		System.out.println("=================================");
		System.out.println("Testing circular array management");
		System.out.println("=================================");
		System.out.println("Created an empty queue with 7 slots");
		SQueue<String> theQ2 = new SQueue<String>(7);
		int count = 1;
		
		String theItem = new String("Item " + count);
		try {
			theQ2.enqueue(theItem);
			System.out.println("Queue after adding element  "+ theItem);			
		} catch (FullQueueException fe) {
			System.out.println("ERROR: "+ fe.getMessage());
			System.out.println("Cannot add element "+ theItem);			
		} 	
		System.out.println(theQ2);
		
		count++;
		theItem = new String("Item " + count);
		try {
			theQ2.enqueue(theItem);
			System.out.println("Queue after adding element  "+ theItem);
			System.out.println(theQ2);
		} catch (FullQueueException fe) {
			System.out.println("ERROR: "+ fe.getMessage());
			System.out.println("Cannot add element "+ theItem);			
		} 		
		
		for (int i = 0; i < 10; i++)	{
			count++;
			theItem = new String("Item " + count);
			try {				
				theQ2.enqueue(theItem);				
				System.out.println("Queue after adding element  "+ theItem);				
			} catch (FullQueueException fe) {
				System.out.println("ERROR: "+ fe.getMessage());
				System.out.println("Cannot add element "+ theItem);										
			} 
			System.out.println(theQ2);	
			
			String copy = theItem + " Copy";
			try {				
				theQ2.enqueue(copy);
				System.out.println("Queue after adding element  "+ copy);				
			} catch (FullQueueException fe) {
				System.out.println("ERROR: "+ fe.getMessage());
				System.out.println("Cannot add element "+ copy);										
			}
			System.out.println(theQ2);	
			
			try {				
				theItem = theQ2.dequeue();
				System.out.println("Queue after dequeing element " + theItem);				
			} catch (EmptyQueueException ee) {
				System.out.println("ERROR: "+ ee.getMessage());										
			} 	
			System.out.println(theQ2);	
		}
		
		System.out.println(theQ2);
		//test getSize()
		System.out.println("There are " + theQ2.getSize() + " items in the queue");
		
		System.out.println();
		System.out.println("=================================");
		System.out.println("Testing Shufflable Interface");
		System.out.println("=================================");
		// This code will test  the Shufflable interface.
		System.out.println("About to test shuffle method...");
		
		SQueue<Integer> newQ = new SQueue<Integer>(10);
		for (int i = 0; i < 10; i++)	{
			try {
				newQ.enqueue(i);				
			} catch (FullQueueException fe) {
				System.out.println("ERROR: "+ fe.getMessage());
				System.out.println("Cannot add element "+ i);	
			} 		
			
		}
		System.out.println(newQ);
		
		System.out.println("Shuffling...");
		newQ.shuffle();
		System.out.println(newQ);
		
		System.out.println("Removing 2 elements and adding 1");
		try {				
			newQ.dequeue();	
			newQ.dequeue();	
		} catch (EmptyQueueException ee) {
			System.out.println("ERROR: "+ ee.getMessage());
			System.out.println(newQ);							
		} 		
		
		try {
			newQ.enqueue(22);				
		} catch (FullQueueException fe) {
			System.out.println("ERROR: "+ fe.getMessage());
			System.out.println(newQ);							
		} 		
		
		System.out.println(newQ);
		
		System.out.println("Shuffling again...");
		newQ.shuffle();
		System.out.println(newQ);
	}
}

public class EmptyQueueException extends RuntimeException {
	
	// default constructor
	public EmptyQueueException() {
		this("The queue is empty.");
	} 
	
	// constructor with user-defined message
	public EmptyQueueException(String message) {
	    super(message);
	} 	
} 
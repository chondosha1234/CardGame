public class FullQueueException extends RuntimeException {
	
	// default constructor
	public FullQueueException() {
		this("The queue is full.");
	} 
	
	// constructor with user-defined message
	public FullQueueException(String message) {
	    super(message);
	} 	
} 

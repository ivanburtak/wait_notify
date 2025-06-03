package waitNotify;

import java.util.LinkedList;
import java.util.Queue;

public class Pipeline<T> {
	private final Queue<T> queue = new LinkedList<>();
	private boolean stoppedReceiving = false;
	
	public synchronized void put(T item) {
		if (this.stoppedReceiving) {
			throw new IllegalStateException("Cannot put items into a closed pipeline."); 
		}
		
		queue.add(item);
		notify();
	}

	public synchronized T get() {
		while (queue.isEmpty()) {
			if (this.stoppedReceiving) {
				return null;
			}
			try {
				wait();
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
		return queue.remove();
	}

	public synchronized void close() {
		this.stoppedReceiving = true;
		notify();
	}
}
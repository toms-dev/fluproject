package Simulation;

import java.util.LinkedList;
import java.util.Queue;

public class LogManager {
    private static LogManager instance = new LogManager();
    private Queue<String> logQueue = new LinkedList<String>();
    
    
    private LogManager() {}
    
    /**
     * Returns the unique instance of LogManager.
     * @return The LogManager instance.
     */
    public static LogManager getInstance() {
        return instance;
    }
    
    /**
     * Logs a message.
     * @param msg
     *          The message to log.
     */
    public void log(String msg) {
        logQueue.add(msg);
    }
    
    /**
     * Get the first log in the queue.
     * Returns null if there is no log message.
     * @return The first log, or null if there is no log message
     */
    public String poll() {
        return logQueue.poll();
    }
    
    /**
     * Clear the log
     */
    public void clear() {
        logQueue.clear();
    }
}

/**
 * Created by Hanna on 18/04/2016.
 */
public class IO {

    private Statistics statistics;
    private Gui gui;
    private Queue ioQueue;
    private Process process;
    private long avgIOTime;


    public IO(Gui gui, Queue ioQueue, long avgIOTime, long queueTime, Statistics statistics) {
        this.gui = gui;
        this.ioQueue = ioQueue;
        this.avgIOTime = avgIOTime;
        this.statistics = statistics;
    }

    private Process popIoQueue(){
        return (Process) ioQueue.removeNext();
    }


}

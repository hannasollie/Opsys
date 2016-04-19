/**
 * Created by lmari on 14.04.2016.
 */
public class IO implements  Constants{

    private Queue ioQueue;
    private Statistics statistics;
    private Gui gui;
    private Process activeProcess;
    private long avgIoTime;


    public IO(Queue ioQueue, long avgIoTime, Statistics statistics, Gui gui){
        this.gui = gui;
        this.statistics = statistics;
        this.ioQueue = ioQueue;
        this.avgIoTime = avgIoTime;

    }



    public  Event addIorequest(Process activeProcess, long clock){
        //add process to I/0 queue

        ioQueue.insert(activeProcess);
        //activeProcess.calculateTimeToNextIoOperation();
        if(noActiveProcess()){
            return startIoOperation(clock);
        }

        return  null;
    }

    public Event startIoOperation(long clock){
    //check if a new I/O operations
        if(activeProcess == null){
            //device is free
        }
        if(!ioQueue.isEmpty()){

            activeProcess = popNextProcess();
            activeProcess.enteredIo(clock);
            gui.setIoActive(activeProcess);
            statistics.nofCompletedProcesses++;

            //start first  process in the queue start I/O


        }
        return new Event(END_IO, clock + avgIoTime);
    }
    public Process endIoOperation(long clock){
        this.ioQueue = null;
        Process process = this.activeProcess;
        process.leftIo(clock);
        gui.setIoActive(this.activeProcess);
        return process;
    }


    public boolean noActiveProcess(){
        return this.activeProcess instanceof Process;
    }

    public Process popNextProcess(){
        return (Process) ioQueue.removeNext();
    }

    public long getIoTime(){
        return (long) (Math.random() * avgIoTime *2);

    }


    public void calculateTimePassed(long timePassed) {
        statistics.ioQueueLengthTime += ioQueue.getQueueLength()*timePassed;
        if (ioQueue.getQueueLength() > statistics.ioQueueLargestLength)
            statistics.ioQueueLargestLength= ioQueue.getQueueLength();
    }

}


/**
 * Created by Hanna on 17/04/2016.
 */
public class CPU implements Constants {

    private int quantum; //tiden som tildeles hver prosess
    private Statistics statistics;
    private Process activeProcess;
    private Queue cpuQueue;
    private Gui gui;
    //implementer Round Robin
    
    public CPU(int quantum, Queue CPUqueue, Gui gui, Statistics statistics){
        this.quantum = quantum;
        this.cpuQueue = CPUqueue;
        this.gui = gui;
        this.statistics = statistics;
    }

    public void addProcessToQueue(Process process) { //setter inn prosess i cpu-køen
        cpuQueue.insert(process);
    }

    public Process removeProcessFromCpu(long clock) {
        // Fjerner aktiv prosess fra CPU
        Process process = activeProcess;
        activeProcess.leftCpu(clock); //oppdaterer statistikk
        activeProcess= null;
        gui.setCpuActive(null);
        return process;
    }

    public Process getNextProcessInQueue() {
        //popper cpu-køen og returnerer neste prosess i køen
        return (Process) cpuQueue.removeNext();
    }

    public boolean hasProcessInQueue() {
        // sjekker om det finnes flere prosesser i cpu-køen
        if(cpuQueue.isEmpty()) {
            return false;
        }
        else{
            return true;
        }
    }

    public Event addProcessToCpuFromQueue(long clock) {
        //henter neste prosess fra cpu-køen og legger den til i CPU
        activeProcess = getNextProcessInQueue();
        activeProcess.enteredCpu(clock);
        gui.setCpuActive(activeProcess);

        //Hvis tiden til neste IO-operasjon er lenger enn maks CPU tid, i tillegg til at prosessen ikke blir ferdig,
        //settes neste event til å bytte prosess
        if(activeProcess.timeToNextIO() > quantum && activeProcess.requiredCpuTime() > quantum) {
            return new Event(SWITCH_PROCESS, clock + quantum);

        }
        //hvis prosessen kjøres ferdig før den trenger å aksessere IO igjen, termineres prosessen
        else if(activeProcess.timeToNextIO() > activeProcess.requiredCpuTime()) {
            return new Event(END_PROCESS,clock + activeProcess.requiredCpuTime());
        }
        //hvis prosessen må aksessere IO før den kan avsluttes, blir neste event å aksessere IO
        else {
            return new Event(IO_REQUEST, clock + activeProcess.timeToNextIO());
        }
    }


    public boolean cpuIsAvailable() {
        if(activeProcess instanceof Process) {
            return false;
        } else {
            return true;
        }
    }

    public Event switchProcessInCpu(long clock) {
        // sjekker om det kjøres en prosess på CPU
        if(!cpuIsAvailable()) {
            //fjerner kjørende prosess fra cpu og legger den tilbake i cpu-køen
            Process currentProcess = removeProcessFromCpu(clock);
            //ToDo: oppdatere statistikk
            addProcessToQueue(currentProcess);
            //legger til ny prosess i Cpu fra cpu-køen
            return addProcessToCpuFromQueue(clock);
        } else if(hasProcessInQueue()) { //hvis CPU ikke kjører, legg til ny prosess i CPU.
            return addProcessToCpuFromQueue(clock);
        }else{ //hvis både CPU og CPU-køen er tom, vil det ikke returneres noe event.
            activeProcess = null;
            gui.setCpuActive(null);
            return null;
        }

    }

    public void calculateTimePassed(long timePassed) {
        statistics.cpuQueueLengthTime += cpuQueue.getQueueLength()*timePassed;
        if (cpuQueue.getQueueLength() > statistics.cpuQueueLargestLength)
            statistics.cpuQueueLargestLength = cpuQueue.getQueueLength();
    }


}

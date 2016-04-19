public class Statistics
{
	/** The number of processes that have exited the system */
	public long nofCompletedProcesses = 0;
	/** The number of processes that have entered the system */
	public long nofCreatedProcesses = 0;
	/** The total time that all completed processes have spent waiting for memory */
	public long totalTimeSpentWaitingForMemory = 0;
	/** The time-weighted length of the memory queue, divide this number by the total time to get average queue length */
	public long memoryQueueLengthTime = 0;
	/** The largest memory queue length that has occurred */
	public long memoryQueueLargestLength = 0;

	// Added fields
	public long totalTimeInIo = 0;
	public long totalTimeInCpu = 0;
	public long nofIoOperations = 0;
	public long nofForcedSwitches = 0;
	public long ioQueueLengthTime = 0;
	public long cpuQueueLengthTime = 0;
	public long ioQueueLargestLength = 0;
	public long totalTimeWaitingForIo = 0;
	public long cpuQueueLargestLength = 0;
	public long totalTimeWaitingForCpu = 0;
	public long nofProcessesPlacedInIoQueue = 0;
	public long nofProcessesPlacedInCpuQueue = 0;

	/**
	 * Prints out a report summarizing all collected data about the simulation.
	 * @param simulationLength	The number of milliseconds that the simulation covered.
	 */
	public void printReport(long simulationLength) {
		// Calculate statistics
		long totalTimeCpuIdle = simulationLength - totalTimeInCpu;
		float avgThroughput = (float) 1000 * nofCompletedProcesses / simulationLength;
		float fractionCpuProcessing = (float) 100 * totalTimeInCpu / simulationLength;
		float fractionCpuIdle = (float) 100 * totalTimeCpuIdle / simulationLength;
		float avgMemoryQueueLength = (float) memoryQueueLengthTime / simulationLength;
		float avgCpuQueueLength = (float) cpuQueueLengthTime / simulationLength;
		float avgIoQueueLength = (float) ioQueueLengthTime / simulationLength;
		long totalTimeInSystem = totalTimeInCpu + totalTimeWaitingForCpu +
				totalTimeInIo + totalTimeWaitingForIo +
				totalTimeSpentWaitingForMemory;

		System.out.println();
		System.out.println("Simulation statistics:");
		System.out.println();
		System.out.println("Number of completed processes:                                "+nofCompletedProcesses);
		System.out.println("Number of created processes:                                  "+nofCreatedProcesses);
		System.out.println("Number of (forced) process switches:                          "+nofForcedSwitches);
		System.out.println("Number of processed I/O operations:                           "+nofIoOperations);
		System.out.println("Average throughput (processes per second):                    "+avgThroughput);
		System.out.println();
		System.out.println("Total CPU time spent processing:                              "+totalTimeInCpu+" ms");
		System.out.println("Fraction of CPU time spent processing:                        "+fractionCpuProcessing+"%");
		System.out.println("Total CPU time spent waiting:                                 "+totalTimeCpuIdle+" ms");
		System.out.println("Fraction of CPU time spent waiting:                           "+fractionCpuIdle+"%");
		System.out.println();
		System.out.println("Largest occuring memory queue length:                         "+memoryQueueLargestLength);
		System.out.println("Average memory queue length:                                  "+avgMemoryQueueLength);
		System.out.println("Largest occuring cpu queue length:                            "+cpuQueueLargestLength);
		System.out.println("Average CPU queue length:                                     "+avgCpuQueueLength);
		System.out.println("Largest occuring I/O queue length:                            "+ioQueueLargestLength);
		System.out.println("Average I/O queue length:                                     "+avgIoQueueLength);
		if(nofCompletedProcesses > 0) {
			float avgTimesProcessInCpuQueue = nofProcessesPlacedInCpuQueue / nofCompletedProcesses;
			float avgTimesProcessInIoQueue = nofProcessesPlacedInIoQueue / nofCompletedProcesses;
			float avgTimeInSystemPerProcess = totalTimeInSystem / nofCompletedProcesses;
			float avgTimeWaitingForMemoryPerProcess = totalTimeSpentWaitingForMemory / nofCompletedProcesses;
			float avgTimeWaitingForCpuPerProcess = totalTimeWaitingForCpu / nofCompletedProcesses;
			float avgTimeProcessingPerProcess = totalTimeInCpu / nofCompletedProcesses;
			float avgTimeWaitingForIoPerProcess = totalTimeWaitingForIo / nofCompletedProcesses;
			float avgTimeInIoPerProcess = totalTimeInIo / nofCompletedProcesses;

			System.out.println("Average # of times a process has been placed in memory queue: "+1);
			System.out.println("Average # of times a process has been placed in CPU queue:    "+avgTimesProcessInCpuQueue);
			System.out.println("Average # of times a process has been placed in I/O queue:    "+avgTimesProcessInIoQueue);
			System.out.println();
			System.out.println("Average time spent in system per process:                     "+avgTimeInSystemPerProcess+" ms");
			System.out.println("Average time spent waiting for memory per process:            "+avgTimeWaitingForMemoryPerProcess+" ms");
			System.out.println("Average time spent waiting for CPU per process:               "+avgTimeWaitingForCpuPerProcess+" ms");
			System.out.println("Average time spent processing per process:                    "+avgTimeProcessingPerProcess+" ms");
			System.out.println("Average time spent waiting for I/O per process:               "+avgTimeWaitingForIoPerProcess+" ms");
			System.out.println("Average time spent in I/O per process:                        "+avgTimeInIoPerProcess+" ms");
		}
	}
}
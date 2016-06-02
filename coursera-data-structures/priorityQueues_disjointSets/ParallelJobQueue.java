package priorityQueues_disjointSets;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.StringTokenizer;

/**
 * Parallel processing job queue.
 * <p>
 * Give n threads and m jobs with duration. A
 * free thread immediately takes the next job
 * from the list. For each job the processing
 * time is the same for all the threads.
 * Determine for each job which thread will
 * process it and when will it start processing.
 * <p>
 * Constraints: 1 ≤ n ≤ 10^5;
 *              1 ≤ m ≤ 10^5;
 *              0 ≤ ti ≤ 10^9.
 * Example: 2 5, 0 2 0 4 5 ->
 *          0 0, 0 0, 1 0, 1 0, 0 2;
 *          the thread0 takes j0, but still not
 *          busy (t0=0), so it again takes j1
 */
public class ParallelJobQueue {
    // Number of threads, n
    private int numWorkers;
    // The processing time of each job i
    private int[] jobs;
    // The thread id of which each job is assigned to
    private int[] assignedWorker;
    // The thread ids of naive solution
    private int[] assignedWorker_naive;
    // The start_time of each job
    private long[] startTime, startTime_naive;

    private FastScanner in;
    private PrintWriter out;

    public static void main(String[] args) throws IOException {
        // new JobQueue().solve();
        new ParallelJobQueue().stressTest();
    }

    private void readData() throws IOException {
        numWorkers = in.nextInt();
        int m = in.nextInt();
        jobs = new int[m];
        for (int i = 0; i < m; ++i) {
            jobs[i] = in.nextInt();
        }
    }

    private void writeResponse() {
        for (int i = 0; i < jobs.length; ++i) {
            out.println(assignedWorker[i] + " " + startTime[i]);
        }
    }

    private void assignJobs_naive() {
        // TODO: replace this code with a faster algorithm.
        assignedWorker_naive = new int[jobs.length];
        startTime_naive = new long[jobs.length];
        long[] nextFreeTime = new long[numWorkers];
        for (int i = 0; i < jobs.length; i++) {
            int duration = jobs[i];
            int bestWorker = 0;
            // For each job i, select the first free thread;
            // that is, the `min` next free time
            for (int j = 0; j < numWorkers; ++j) {
                if (nextFreeTime[j] < nextFreeTime[bestWorker])
                    bestWorker = j;
            }
            assignedWorker_naive[i] = bestWorker;
            startTime_naive[i] = nextFreeTime[bestWorker];
            nextFreeTime[bestWorker] += duration;
        }
    }

    /**
     * Key Idea:
     * Process each job by the first free thread.
     * Maintain: a Priority Queue of threads,
     *           sorted by its next free time;
     *           if same free time, order by id.
     * Pop out the first available thread.
     * Record assigned worker and start_time for job i.
     * Update the thread's next free time and push back
     */
    private void assignJobs() {
        assignedWorker = new int[jobs.length];
        startTime = new long[jobs.length];
        // Build a PQ of m Workers sorted by free time
        PriorityQueue<Worker> pq = new PriorityQueue<Worker>(numWorkers,
                new Comparator<Worker>(){
            @Override
            public int compare (Worker w1, Worker w2) {
                return w1.nextFreeTime == w2.nextFreeTime ? w1.id - w2.id :
                    (int) (w1.nextFreeTime - w2.nextFreeTime);
            }
        });
        // Push all new threads with initial free time 0
        for (int i = 0; i < numWorkers; i++)
            pq.offer(new Worker(i));
        // Process each job by the 1st free thread
        for (int i = 0; i < jobs.length; i++) {
            Worker freeThread = pq.poll();
            // Record job i's assigned worker and start_time
            assignedWorker[i] = freeThread.id;
            startTime[i] = freeThread.nextFreeTime;
            // Update next free time and offer back
            freeThread.nextFreeTime += jobs[i];
            pq.offer(freeThread);
            // This thread will be sorted again according to
            // its next free time, by next job to be processed
        }
    }
    private static class Worker {
        int id;
        long nextFreeTime;
        public Worker (int id) {
            this.id = id;
            nextFreeTime = 0;
        }
    }
    public void stressTest() {
        // Generate random m threads and array of n jobs
        // Compare naive and fast algorithms
        Random random = new Random();
        int bound = 100000, job_bound = 1000000000;
        while (true) {
            this.numWorkers = random.nextInt(bound) + 1;
            int m = random.nextInt(bound) + 1;
            jobs = new int[m];
            for (int i = 0; i < m; i++) {
                jobs[i] = random.nextInt(job_bound + 1);
            }
            // Fast and naive algorithms share the same
            // jobs and numWorkers, but different output
            long start = System.currentTimeMillis();
            assignJobs();
            long end   = System.currentTimeMillis();
            assignJobs_naive();
            if (Arrays.equals(assignedWorker, assignedWorker_naive)
                    && Arrays.equals(startTime, startTime_naive)) {
                System.out.println("OK: n=" + numWorkers + "\t"
                    + (end - start) + "ms");
            }
            else System.out.println("Error: n=" + numWorkers);
        }
    }

    public void solve() throws IOException {
        in = new FastScanner();
        out = new PrintWriter(new BufferedOutputStream(System.out));
        readData();
        assignJobs();
        writeResponse();
        out.close();
    }

    static class FastScanner {
        private BufferedReader reader;
        private StringTokenizer tokenizer;

        public FastScanner() {
            reader = new BufferedReader(new InputStreamReader(System.in));
            tokenizer = null;
        }

        public String next() throws IOException {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                tokenizer = new StringTokenizer(reader.readLine());
            }
            return tokenizer.nextToken();
        }

        public int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }
}

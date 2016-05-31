package list_stack_tree;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

/**
 * Network packet processing simulation.
 * <p>
 * When packets arrive, they are stored
 * in the buffer before being processed.
 * If several packets arrive at the same
 * time, they are first all stored in
 * the buffer.
 * Note that a packet leaves the buffer
 * and frees the space as soon as the
 * computer finishes processing it.
 * <p>
 * Example: S=3, n=6, A=[0 1 2 3 4 5]
 *          P=[2 2 2 2 2 2]
 */
class Request {
    public Request(int arrival_time, int process_time) {
        this.arrival_time = arrival_time;
        this.process_time = process_time;
    }

    public int arrival_time;
    public int process_time;
}

class Response {
    public Response(boolean dropped, int start_time) {
        this.dropped = dropped;
        this.start_time = start_time;
    }

    public boolean dropped;
    public int start_time;
}

class Buffer {
    public Buffer(int size) {
        this.size_ = size;
        this.finish_time_ = new ArrayList<Integer>();
    }

    /**
     * Key Idea:
     * When each process i comes, decide:
     *     if it will be dropped or not?
     *     if enqueued, what is its start/finish time?
     * Pop all packets in buffer that have already finished;
     * If buffer is full, drop i and output -1;
     * Determine the start_time by queue:
     *     if queue is empty, process i as it arrives;
     *     else, enqueue i after last finish_time;
     *     (i.e. should wait in queue if not idle)
     *
     * @param request
     * @return
     */
    public Response Process(Request request) {
        // Free buffer only when the process finished
        while (!finish_time_.isEmpty() &&
                finish_time_.get(0) <= request.arrival_time) {
            // BZ: the process finishes at i's arrival? -> finished
            //     example: S=1, n=2, A=[0,0],P=[0,1|0]
            // 1st request finishes at 0 but 2nd also comes at 0...
            finish_time_.remove(0);
            /* @Note:
             * This finally popped request has nothing to do
             * with the current new request i; i either waits
             * in queue or gets processed if idle. */
        }
        // If still full, just drop it;
        if (finish_time_.size() == size_) {
            return new Response(true, -1);
        }
        // Compute start_time and enqueue finish_time.
        // If no buffer waiting, i.e. idle, start instantly;
        // else wait at the end...
        // BZ: start_time is `at least arrival time`...
        // BZ: start_time is the finish_time popped at last?
        //     or it waits at end of queue?
        int current_start_time = finish_time_.isEmpty() ? request.arrival_time :
            finish_time_.get(finish_time_.size() - 1);
        finish_time_.add(current_start_time + request.process_time);
        return new Response(false, current_start_time);
    }

    private int size_;
    private ArrayList<Integer> finish_time_;
}

class process_packages {
    private static ArrayList<Request> ReadQueries(Scanner scanner) throws IOException {
        int requests_count = scanner.nextInt();
        ArrayList<Request> requests = new ArrayList<Request>();
        for (int i = 0; i < requests_count; ++i) {
            int arrival_time = scanner.nextInt();
            int process_time = scanner.nextInt();
            requests.add(new Request(arrival_time, process_time));
        }
        return requests;
    }

    private static ArrayList<Response> ProcessRequests(ArrayList<Request> requests, Buffer buffer) {
        ArrayList<Response> responses = new ArrayList<Response>();
        for (int i = 0; i < requests.size(); ++i) {
            responses.add(buffer.Process(requests.get(i)));
        }
        return responses;
    }

    private static void PrintResponses(ArrayList<Response> responses) {
        for (int i = 0; i < responses.size(); ++i) {
            Response response = responses.get(i);
            if (response.dropped) {
                System.out.println(-1);
            } else {
                System.out.println(response.start_time);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        File folder = new File("/home/chongrui/Downloads/pa1/pa1-network");
        if (! folder.exists()) {
            Scanner scanner = new Scanner(System.in);
            int buffer_max_size = scanner.nextInt();
            Buffer buffer = new Buffer(buffer_max_size);
            ArrayList<Request> requests = ReadQueries(scanner);
            ArrayList<Response> responses = ProcessRequests(requests, buffer);
            PrintResponses(responses);
            scanner.close();
            return;
        }
        File[] files = folder.listFiles();
        Arrays.sort(files, new Comparator<File>(){
            @Override
            public int compare (File f1, File f2) {
                return f1.getName().compareTo(f2.getName());
            }
        });
        for (File test : files) {
            if (test.getName().endsWith("-report")) continue;
            System.out.print("Test file #" + test.getName() + ":\n");

            Scanner scanner = new Scanner(test);
            int buffer_max_size = scanner.nextInt();
            Buffer buffer = new Buffer(buffer_max_size);
            ArrayList<Request> requests = ReadQueries(scanner);
            ArrayList<Response> responses = ProcessRequests(requests, buffer);
            PrintResponses(responses);

            scanner.close();
            String path = test.getPath();
            FileWriter fw = new FileWriter(new File(path + "-report"), true);
            for (Response response : responses) {
                fw.write(response.start_time + "\n");
            }
            fw.close();
        }
    }
}

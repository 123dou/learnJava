package algorithm.testDemo;


import java.util.*;

public class Solution {
    private static int min = Integer.MAX_VALUE;

    public static void main(String[] args) {
    }

    public int makeConnected(int n, int[][] connections) {
        if (connections.length < n - 1) {
            return -1;
        }
        int countConn = 0;
        int[] UF = new int[n];
        for (int i = 0; i < UF.length; i++) {
            UF[i] = i;
        }
        for (int[] connection : connections) {
            if (!isConn(UF, connection[0], connection[1])) {
                conn(UF, connection[0], connection[1]);
            }
        }
        for (int i = 0; i < UF.length; i++) {
            if (UF[i] == i) {
                countConn++;
            }
        }
        return countConn - 1;
    }

    private boolean isConn(int[] arr, int i, int j) {
        return arr[i] == arr[j];
    }

    private void conn(int[] arr, int i, int j) {
        int leni = 0;
        int lenj = 0;
        while (i != arr[i]) {
            leni++;
            i = arr[i];
        }
        while (j != arr[j]) {
            lenj++;
            j = arr[j];
        }
        if (leni < lenj) {
            arr[i] = j;
        } else {
            arr[j] = i;
        }

    }

    public int maxEvents(int[][] events) {
        int minStart = Integer.MAX_VALUE;
        int maxEnd = -1;
        HashMap<Event, List<Event>> map = new HashMap<>();
        Event[] eventArr = new Event[events.length];
        int len = 0;
        for (int[] event : events) {
            Event e = new Event(event[0], event[1]);
            eventArr[len++] = e;
            minStart = Math.min(minStart, e.start);
            maxEnd = Math.max(maxEnd, e.end);
        }
        int max = maxEnd - minStart + 1;
        Arrays.sort(eventArr);
        int lo = 0;
        int hi = eventArr.length - 1;
        while (lo < hi) {
            Event t = eventArr[lo];
            eventArr[lo] = eventArr[hi];
            eventArr[hi] = t;
            lo++;
            hi--;
        }
        System.out.println(Arrays.toString(eventArr));
        boolean[] marked = new boolean[maxEnd + 1];
        int res = 0;
        for (int i = 0; i < eventArr.length; i++) {
            Event e = eventArr[i];
            for (int j = e.start; j <= e.end; j++) {
                if (!marked[j]) {
                    marked[j] = true;
                    res++;
                }
            }
        }
        for (int i = 0; i < marked.length; i++) {
            if (marked[i]) {
                System.out.print(i + "--" + marked[i] + ", ");
            }
        }
        return res;
    }

    class Event implements Comparable<Event> {
        int start;
        int end;

        Event(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        public int compareTo(Event o) {
            int first = this.start - o.start;
            if (first != 0) {
                return first;
            }
            return this.end - o.end;
        }

        @Override
        public String toString() {
            return "Event{" +
                    "start=" + start +
                    ", end=" + end +
                    '}';
        }
    }


}

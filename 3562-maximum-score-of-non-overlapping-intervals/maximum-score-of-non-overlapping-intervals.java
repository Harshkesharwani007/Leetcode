import java.util.*;

class Solution {

    static class Interval {
        int left, right, weight, index;

        Interval(int left, int right, int weight, int index) {
            this.left = left;
            this.right = right;
            this.weight = weight;
            this.index = index;
        }
    }

    static class State {
        long weight;
        List<Integer> indices;

        State(long weight, List<Integer> indices) {
            this.weight = weight;
            this.indices = indices;
        }
    }

    private State[][] dp;
    private List<Interval> arr;

    public int[] maximumWeight(List<List<Integer>> intervals) {

        int n = intervals.size();

        arr = new ArrayList<>();

        // Store original index also
        for (int i = 0; i < n; i++) {

            List<Integer> curr = intervals.get(i);

            arr.add(new Interval(
                    curr.get(0),
                    curr.get(1),
                    curr.get(2),
                    i
            ));
        }

        // Sort according to starting point
        arr.sort((a, b) -> {
            if (a.left != b.left)
                return Integer.compare(a.left, b.left);

            return Integer.compare(a.right, b.right);
        });

        dp = new State[n][5];

        State ans = solve(0, 4);

        int[] result = new int[ans.indices.size()];

        for (int i = 0; i < ans.indices.size(); i++) {
            result[i] = ans.indices.get(i);
        }

        return result;
    }


    private State solve(int i, int remaining) {

        if (i == arr.size() || remaining == 0) {
            return new State(0, new ArrayList<>());
        }

        if (dp[i][remaining] != null) {
            return dp[i][remaining];
        }

        // -------------------
        // OPTION 1: SKIP
        // -------------------

        State skip = solve(i + 1, remaining);


        // -------------------
        // OPTION 2: TAKE
        // -------------------

        Interval curr = arr.get(i);

        int nextIndex = findNext(i + 1, curr.right);

        State next = solve(nextIndex, remaining - 1);

        List<Integer> selected =
                new ArrayList<>(next.indices);

        selected.add(curr.index);

        // Answer must contain indices in sorted order
        Collections.sort(selected);

        State take = new State(
                (long) curr.weight + next.weight,
                selected
        );


        // -------------------
        // Compare
        // -------------------

        State answer;

        if (take.weight > skip.weight) {

            answer = take;

        } else if (take.weight < skip.weight) {

            answer = skip;

        } else {

            // Same weight
            // choose lexicographically smallest indices
            if (compare(take.indices, skip.indices) < 0)
                answer = take;
            else
                answer = skip;
        }

        return dp[i][remaining] = answer;
    }


    // Find first interval whose start > current end
    private int findNext(int start, int end) {

        int left = start;
        int right = arr.size();

        while (left < right) {

            int mid = left + (right - left) / 2;

            if (arr.get(mid).left > end) {

                right = mid;

            } else {

                left = mid + 1;
            }
        }

        return left;
    }


    // Lexicographical comparison
    private int compare(List<Integer> a,
                        List<Integer> b) {

        int n = Math.min(a.size(), b.size());

        for (int i = 0; i < n; i++) {

            if (!a.get(i).equals(b.get(i))) {
                return Integer.compare(
                        a.get(i),
                        b.get(i)
                );
            }
        }

        return Integer.compare(
                a.size(),
                b.size()
        );
    }
}
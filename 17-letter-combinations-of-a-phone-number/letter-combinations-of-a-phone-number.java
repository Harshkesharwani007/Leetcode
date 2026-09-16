import java.util.*;

class Solution {
    public List<String> letterCombinations(String digits) {
        List<String> result = new ArrayList<>();

        if (digits == null || digits.length() == 0) {
            return result;
        }

        String[] map = {
            "",     // 0
            "",     // 1
            "abc",  // 2
            "def",  // 3
            "ghi",  // 4
            "jkl",  // 5
            "mno",  // 6
            "pqrs", // 7
            "tuv",  // 8
            "wxyz"  // 9
        };

        backtrack(digits, 0, new StringBuilder(), map, result);

        return result;
    }

    private void backtrack(String digits, int index,
                           StringBuilder current,
                           String[] map,
                           List<String> result) {

        // Base case
        if (index == digits.length()) {
            result.add(current.toString());
            return;
        }

        int digit = digits.charAt(index) - '0';
        String letters = map[digit];

        for (char ch : letters.toCharArray()) {

            // Choose
            current.append(ch);

            // Explore
            backtrack(digits, index + 1, current, map, result);

            // Undo
            current.deleteCharAt(current.length() - 1);
        }
    }
}
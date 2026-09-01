
import java.util.*;

class Solution {
    public int minMoves(String[] classroom, int energy) {

        int m = classroom.length;
        int n = classroom[0].length();

        // litterId[i][j] = bit number of litter at this cell
        int[][] litterId = new int[m][n];

        int startR = 0;
        int startC = 0;
        int litterCount = 0;

        // Find S and number each L
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {

                char ch = classroom[i].charAt(j);

                if (ch == 'S') {
                    startR = i;
                    startC = j;
                } 
                else if (ch == 'L') {
                    litterId[i][j] = litterCount;
                    litterCount++;
                }
            }
        }

        // No litter
        if (litterCount == 0) {
            return 0;
        }

        // All litter initially needs to be collected
        int fullMask = (1 << litterCount) - 1;

        /*
         * State:
         * row
         * col
         * remaining energy
         * mask
         */
        boolean[][][][] visited =
            new boolean[m][n][energy + 1][1 << litterCount];

        Queue<int[]> queue = new LinkedList<>();

        queue.offer(new int[] {
            startR,
            startC,
            energy,
            fullMask
        });

        visited[startR][startC][energy][fullMask] = true;

        int moves = 0;

        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};

        while (!queue.isEmpty()) {

            int size = queue.size();

            // One BFS level = one move
            while (size-- > 0) {

                int[] state = queue.poll();

                int r = state[0];
                int c = state[1];
                int currEnergy = state[2];
                int mask = state[3];

                // All litter collected
                if (mask == 0) {
                    return moves;
                }

                // No energy -> cannot make another move
                if (currEnergy == 0) {
                    continue;
                }

                for (int d = 0; d < 4; d++) {

                    int nr = r + dr[d];
                    int nc = c + dc[d];

                    // Outside grid
                    if (nr < 0 || nr >= m ||
                        nc < 0 || nc >= n) {
                        continue;
                    }

                    // Cannot cross obstacle
                    if (classroom[nr].charAt(nc) == 'X') {
                        continue;
                    }

                    char next = classroom[nr].charAt(nc);

                    // Every move costs 1 energy
                    int nextEnergy = currEnergy - 1;

                    // R restores energy to maximum
                    if (next == 'R') {
                        nextEnergy = energy;
                    }

                    // Copy current mask
                    int nextMask = mask;

                    // If we reach litter, collect it
                    if (next == 'L') {

                        int id = litterId[nr][nc];

                        nextMask = mask & ~(1 << id);
                    }

                    // If this state wasn't visited
                    if (!visited[nr][nc][nextEnergy][nextMask]) {

                        visited[nr][nc][nextEnergy][nextMask] = true;

                        queue.offer(new int[] {
                            nr,
                            nc,
                            nextEnergy,
                            nextMask
                        });
                    }
                }
            }

            moves++;
        }

        return -1;
    }
}

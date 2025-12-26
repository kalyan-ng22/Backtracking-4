// Time Complexity : O(C(H*W) * H*W)  - C(H*W) is for the combinations possible and H*W time for bfs
// Space Complexity : O(H*W) - bfs queue space
// Did this code successfully run on Leetcode : Yes
// Approach : We need to explore all the combinations that are possible moving the n buildings in the grid matrix with the help of dfs and
// backtracking. While all the buildings are placed in the grid matrix, at each combination we calculate the minimum distance for all
// the empty parting lots by performing BFS and capture the minimum. After exploring all the possible combinations of placing the buildings,
// the minimum distance for the farthest parking lot will be returned.


public class OptimalPlacementOfBuildings {
    // "static void main" must be defined in a public class.
    public static void main(String[] args) {
        BuildingPlacement buildingPlacement = new BuildingPlacement();
        System.out.println(buildingPlacement.findMinDistance(5, 6, 3));
    }

    public static class BuildingPlacement {

        int H;
        int W;
        int result;

        public int findMinDistance(int h, int w, int n) {
            this.H = h;
            this.W = w;
            result = Integer.MAX_VALUE;
            int[][] grid = new int[h][w]; //grid to capture minimum distance
            for (int i = 0; i < h; i++) {
                for (int j = 0; j < w; j++) {
                    grid[i][j] = -1; //initially fill with -1
                }
            }
            backtrack(grid, 0, n);
            return result;
        }

        private void backtrack(int[][] grid, int idx, int n) { //to assign positions for n buildings
            if (n == 0) {
                bfs(grid); // bfs to capture the distance of the empty parking lots in grid matrix once all buildings are placed
                return;
            }
            for (int i = idx; i < H * W; i++) {
                int r = i / W;
                int c = i % W;

                grid[r][c] = 0;  // action
                backtrack(grid, i + 1, n - 1); // recurse
                grid[r][c] = -1; // backtrack
            }
        }

        private void bfs(int[][] grid) {
            int m = grid.length;
            int n = grid[0].length;
            int[][] dirs = new int[][]{{0, 1}, {0, -1}, {1, 0}, {-1, 0}};// all four directions
            boolean[][] visited = new boolean[H][W]; //visited matrix
            Queue<int[]> q = new LinkedList<>();
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (grid[i][j] == 0) { //building positions are added to queue to explore minimum distance for it's neighbors
                        q.add(new int[]{i, j});
                        visited[i][j] = true;
                    }
                }
            }
            int dist = 0;
            while (!q.isEmpty()) {
                int size = q.size();
                for (int i = 0; i < size; i++) {
                    int[] curr = q.poll();
                    for (int[] dir : dirs) {
                        int r = dir[0] + curr[0];
                        int c = dir[1] + curr[1];

                        if (r >= 0 && c >= 0 && r < H && c < W && grid[r][c] == -1 && !visited[r][c]) { //bounds and visited check
                            q.add(new int[]{r, c});
                            visited[r][c] = true;
                        }
                    }
                }
                dist++; //increment distance for each bfs level
            }
            result = Math.min(result, dist - 1);
        }
    }
}

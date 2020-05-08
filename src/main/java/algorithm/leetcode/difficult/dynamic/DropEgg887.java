package algorithm.leetcode.difficult.dynamic;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 你将获得 K 个鸡蛋，并可以使用一栋从 1 到 N  共有 N 层楼的建筑。
 * <p>
 * 每个蛋的功能都是一样的，如果一个蛋碎了，你就不能再把它掉下去。
 * <p>
 * 你知道存在楼层 F ，满足 0 <= F <= N 任何从高于 F 的楼层落下的鸡蛋都会碎，从 F 楼层或比它低的楼层落下的鸡蛋都不会破。
 * <p>
 * 每次移动，你可以取一个鸡蛋（如果你有完整的鸡蛋）并把它从任一楼层 X 扔下（满足 1 <= X <= N）。
 * <p>
 * 你的目标是确切地知道 F 的值是多少。
 * <p>
 * 无论 F 的初始值如何，你确定 F 的值的最小移动次数是多少？
 * <p>
 *  
 * <p>
 * 示例 1：
 * <p>
 * 输入：K = 1, N = 2
 * 输出：2
 * 解释：
 * 鸡蛋从 1 楼掉落。如果它碎了，我们肯定知道 F = 0 。
 * 否则，鸡蛋从 2 楼掉落。如果它碎了，我们肯定知道 F = 1 。
 * 如果它没碎，那么我们肯定知道 F = 2 。
 * 因此，在最坏的情况下我们需要移动 2 次以确定 F 是多少。
 * 示例 2：
 * <p>
 * 输入：K = 2, N = 6
 * 输出：3
 * 示例 3：
 * <p>
 * 输入：K = 3, N = 14
 * 输出：4
 *  
 * <p>
 * 提示：
 * <p>
 * 1 <= K <= 100
 * 1 <= N <= 10000
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/super-egg-drop
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class DropEgg887 {
    /**
     * f(K, N)表示K个鸡蛋,N层楼所需要的最小移动次数
     * 取X为任意一个楼层，则当一个鸡蛋从X楼层往下扔的时候，整栋楼就被分成了(0,X-1), X, (X+1, N)三部分,有两种情况：
     * 1.鸡蛋碎了，K-1, 并且目标楼层在0 -- X-1这一部分
     * 2.鸡蛋没有碎, K, 并且目标楼层在(X+1, N)共有N-X层
     * 所以,f(K, N) = max(f(K-1, X-1), f(K, N-X))  1 <= x <= N
     */

    @Test
    public int solution1(int K, int N) {
        return recusive(K, N);
    }

    public int recusive(int K, int N) {
        if (N == 0 || N == 1 || K == 1) return N;
        int minNum = Integer.MAX_VALUE;
        for (int i = 1; i < N; i++) {
            int temp = Math.max(recusive(K - 1, i - 1), recusive(K, N - i));
            minNum = Math.min(minNum, temp + 1);
        }
        return minNum;
    }


    /**
     * 使用动态规划
     * dp[k][n]表示k个鸡蛋,n层楼所需要的最少移动次数
     */
    public int solutino2(int K, int N) {
        int[][] dp = new int[K + 1][N + 1];
        //初始化K=1,的情况
        for (int i = 0; i <= N; i++) {
            dp[1][i] = i;
        }
        for (int k = 2; k <= K; k++) {
            for (int n = 1; n <= N; n++) {
                int minN = Integer.MAX_VALUE;
                for (int x = 1; x <= n; x++) { //这一个循环可以用二叉查找来实现, 如下solution3所示
                    minN = Math.min(minN, 1 + Math.max(dp[k - 1][x - 1], dp[k][n - x]));
                }
                dp[k][n] = minN;
            }
        }
        return dp[K][N];
    }

    /**
     * 动态规划加二叉搜索
     * 设t1 = f(K - 1, X - 1), t2 = f(K, N - X)
     * 则t1随着X递增, t2随着X递减
     * 对于一个X,如果t1<t2,则X太小了,如果t1 > t2 则X太大了
     */
    public int solution3(int K, int N) {
        return recursive2(K, N);
    }

    Map<Integer, Integer> cache = new HashMap<>();

    private int recursive2(int K, int N) {
        if (N == 0 || N == 1 || K == 1) return N;
        int key = N * 100 + K;
        if (cache.containsKey(key)) return cache.get(key);
        int lo = 1;
        int hi = N;
        while (lo + 1 < hi) {
            int mid = (lo + hi) / 2;
            int loV = recusive(K - 1, mid - 1);
            int hiV = recursive2(K, N - mid);
            if (loV < hiV) lo = mid;
            else if (loV > hiV) hi = mid;
            else lo = hi = mid;
        }

        int min = 1 + Math.min(
                Math.max(recursive2(K - 1, lo - 1), recursive2(K, N - lo)),
                Math.max(recursive2(K - 1, hi - 1), recursive2(K, N - hi))
        );
        cache.put(key, min);
        return min;
    }


    /**
     * dp[k][m] 表示用 k 个鸡蛋移动 m 步可以“保证求解”的最大楼层数。
     * <p>
     * 我们先解释一下定义中的几个概念：
     * <p>
     * 所谓“求解”，意思就是给定楼层 N，我们能否找到临界楼层 F(F <= N)，使得鸡蛋从 F 层掉落刚好不会被摔碎。所谓“保证求解”，意思就是即使每次丢鸡蛋的结果都很差，最终仍能求解。
     * <p>
     * 比如，给定 1 个鸡蛋移动 1 步，那么可以求解的最大楼层数为 1，即从 1 楼丢下，如果鸡蛋碎了，求得 F=0，如果鸡蛋没碎，求得 F=1。在这种情况下，假如我们给出一个 2 层的楼，就无法保证求解了，因为无论从哪一层丢出鸡蛋，都没有十足的把握能够一次求得 F，换句话说，虽然我们仍有一定的机会能够求解，但无法“保证求解”。
     * <p>
     * 下面回到正题：
     * <p>
     * 假设我们有 k 个鸡蛋可以移动 m 步，考虑某一步 t 应该在哪一层丢鸡蛋？一个正确的选择是在 dp[k-1][t-1] + 1 层丢鸡蛋，结果分两种情况：
     * <p>
     * 如果鸡蛋碎了，我们首先排除了该层以上的所有楼层（不管这个楼有多高），而对于剩下的 dp[k-1][t-1] 层楼，我们一定能用 k-1 个鸡蛋在 t-1 步内求解。因此这种情况下，我们总共可以求解无限高的楼层。可见，这是一种非常好的情况，但并不总是发生。
     * <p>
     * 如果鸡蛋没碎，我们首先排除了该层以下的 dp[k-1][t-1] 层楼，此时我们还有 k 个蛋和 t-1 步，那么我们去该层以上的楼层继续测得 dp[k][t-1] 层楼。因此这种情况下，我们总共可以求解 dp[k-1][t-1] + dp[k][t-1] + 1 层楼。
     * <p>
     * 容易想象，在所有 m 步中只要有一次出现了第一种情况，那么我们就可以求解无限高的楼层。但“保证求解”的定义要求我们排除一切运气成分，因此我们只得认为每次移动都遇到第二种情况。于是得到递推公式：
     * <p>
     * dp[k][t] = dp[k-1][t-1] + dp[k][t-1] + 1
     * <p>
     * 基本的问题已经解决了，但是我们还遗留了一个问题：为什么要选择在 dp[k-1][t-1] + 1 层丢鸡蛋？
     * <p>
     * 现在我们已经知道，如果我们每一步都在 dp[k-1][t-1] + 1 层丢鸡蛋，最终是一定能够求解的。但如果我们选择在更低的层或者更高的层丢鸡蛋会怎样呢？我们分两种情况讨论：
     * <p>
     * 在更低的楼层丢鸡蛋。同样能够“保证求解”，但最终得到的并不是“最大”楼层数，我们没有充分挖掘鸡蛋数和移动次数的潜力，最终求解时会剩余一定量的鸡蛋或移动次数。
     * <p>
     * 在更高的楼层丢鸡蛋。不妨假设高了一层，即在第 dp[k-1][t-1] + 2 层丢鸡蛋。如果鸡蛋碎掉了，我们仍然可以排除该层以上的所有楼层（不管这个楼有多高），但接下来就不好办了，因为我们剩下的 k-1 个鸡蛋在 t-1 步内只能“保证求解” dp[k-1][t-1] 的楼层，而现在剩余的楼层却是 dp[k-1][t-1] + 1，多了一层，因此无法“保证求解”！
     * <p>
     * 综上，我们用排除法证明了每一步都应该在第 dp[k-1][t-1] + 1 层丢鸡蛋。
     */
    public int superEggDrop(int K, int N) {
        int[][] dp = new int[K + 1][N + 1];
        for (int n = 1; n <= N; n++) {
            dp[0][n] = 0;
            for (int k = 1; k <= K; k++) {
                dp[k][n] = dp[k][n - 1] + dp[k - 1][n - 1] + 1;
                if (dp[k][n] >= N) return n;
            }

        }
        return N;
    }
}


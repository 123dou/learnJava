package algorithm.algs4.sorting;

/**
 * 归并排序:
 * 1.把长度为n的输入序列分成两个长度为n/2的子序列
 * 2.对这两个子序列分别采用归并排序
 * 3.将两个排序好的子序列合并成一个最终的排序序列
 *
 * @author dou
 */
public class MergeSorting {
    private static int[] aux;
    /**
     * 自顶向下的归并排序
     * @param array
     * @return
     */
    public static int[] mergeSorting(int[] array) {
        //如果array为null,或长度为1则返回
        if (SortingUtil.isNullOrOneElement(array)) return array;
        aux = new int[array.length];
        //数组长度
        int length = array.length;
        //将数组分成元素相同的两个数组
        int mid = length / 2;
        int[] left = new int[mid];
        System.arraycopy(array, 0, left, 0, mid);
        int[] right = new int[length - mid];
        System.arraycopy(array, mid, right, 0, length - mid);
        //递归调用
        return merge(mergeSorting(left), mergeSorting(right));
    }

    //将两个无序的数组合并成一个有序的数组
    private static int[] merge(int[] left, int[] right) {
        int[] result = new int[left.length + right.length]; //将要返回的数组,里面将要存放left,right排序后的元素
        int l = 0;
        int r = 0;
        int len = 0;
        while (l < left.length && r < right.length) {
            if (left[l] < right[r]) result[len++] = left[l++];
            else result[len++] = right[r++];
        }
        while (l < left.length) result[len++] = left[l++];
        while (r < right.length) result[len++] = right[r++];
        return result;
    }

   public static void mergeSorting3(int[] arr) {
        if (SortingUtil.isNullOrOneElement(arr)) return;
        aux = new int[arr.length];
        mergeSorting(arr, 0, arr.length - 1);
   }

    /**
     *
     * @param arr
     * @param lo
     * @param hi
     */
    private static void mergeSorting(int[] arr, int lo, int hi) {
       if (hi <= lo) return;
       int mid = lo + (hi - lo) / 2;
       mergeSorting(arr, lo, mid);
       mergeSorting(arr, mid + 1, hi);
       merge(arr, lo, mid, hi);
    }


    /**
     * arr[lo] - arr[mid] 是需要归并的左边的数组
     * arr[mid+1] - arr[hi] 是需要归并的右边的数组
     * @param arr
     * @param lo
     * @param mid
     * @param hi
     */
    private static void merge(int[] arr, int lo, int mid, int hi) {
        for(int i = lo; i <= hi; i++) {
            aux[i] = arr[i]; //辅助数组
        }
        int l = lo;
        int r = mid + 1;
        for (int k = lo; k <= hi; k++) {
            if (l > mid) arr[k] = aux[r++]; //左边的数组已经比较完了
            else if (r > hi) arr[k] = aux[l++]; //右边的数组已经比较完了
            else if (aux[l] < aux[r]) arr[k] = aux[l++];
            else arr[k] = aux[r++];
        }
    }


    public static void mergeSorting2(int[] arr) {
        if (SortingUtil.isNullOrOneElement(arr)) return;
        aux = new int[arr.length];
        //sz为子数组的大小
        for (int sz = 1; sz < arr.length - sz; sz += sz + sz) {
            for (int lo = 0; lo < arr.length - sz; lo += sz + sz) {
                merge(arr, lo, lo + sz, Math.min(lo + sz + sz - 1, arr.length - 1));
            }
        }

    }

}

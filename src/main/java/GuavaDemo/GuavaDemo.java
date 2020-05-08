package GuavaDemo;
import com.google.common.base.Strings;
import com.google.common.graph.GraphBuilder;
import com.google.common.graph.MutableGraph;
import com.google.common.math.IntMath;
import org.junit.Test;

public class GuavaDemo {
    public static void main(String[] args) {
        System.out.println(IntMath.checkedAdd(Integer.MAX_VALUE, 2));
    }

    @Test
    public void testStrings() {
        System.out.println(Strings.isNullOrEmpty(null));
        MutableGraph<Integer> unGraph = GraphBuilder.undirected().build();
        unGraph.addNode(1);
        unGraph.putEdge(1, 2);
        unGraph.putEdge(2, 1);
        System.out.println(unGraph.toString());
    }
}

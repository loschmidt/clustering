package loschmidt.clustering.api;

import java.util.ArrayList;
import java.util.List;

public class Clusters<T> {

    List<Cluster<T>> clusters = new ArrayList<>();

    public void add(Cluster<T> cluster) {
        clusters.add(cluster);
    }

    public List<Cluster<T>> get() {
        return clusters;
    }
    
    public void print() {
        for (Cluster<T> c : clusters) {
            for (T t : c.get()) {
                System.out.print(t + " ");
            }
            System.out.println();
        }
    }
}

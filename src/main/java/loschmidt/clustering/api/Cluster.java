package loschmidt.clustering.api;

import java.util.ArrayList;
import java.util.List;

public class Cluster<T> {

    List<T> instances = new ArrayList<>();

    public void add(T instance) {
        instances.add(instance);
    }
    
    public List<T> get() {
        return instances;
    }
}

package loschmidt.clustering.api.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import loschmidt.clustering.distance.DistanceProvider;

/**
 * Stores instances, defines their indices, computes their distances.
 */
public class EuclideanSpace implements DistanceProvider<VectorInstance> {
    
    private final List<VectorInstance> instances = new ArrayList<>();

    public final void add(VectorInstance instance) {
        instances.add(instance);
    }

    public EuclideanSpace(int elements, int dimensions) {        
        Random random = new Random(1);
        for (int i = 0; i < elements; i++) {
            double[] ds = new double[dimensions];
            for (int j = 0; j < dimensions; j++) {
                ds[j] = random.nextDouble();
            }
            VectorInstance vi = new VectorInstance(ds);
            add(vi);
        }
    }

    @Override
    public float getDistance(int x, int y) {
        double[] xv = instances.get(x).getArray();
        double[] yv = instances.get(y).getArray();
        double d = 0;
        for (int i = 0; i < xv.length; i++) {
            double dif = xv[i] - yv[i];
            d += dif * dif;
        }
        return (float) Math.sqrt(d);
    }

    @Override
    public int size() {
        return instances.size();
    }

    @Override
    public VectorInstance get(int x) {
        return instances.get(x);
    }

    @Override
    public int getElementSize(int x) {
        return 1;
    }
}

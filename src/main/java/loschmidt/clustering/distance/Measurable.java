package loschmidt.clustering.distance;

public interface Measurable<T> {

    /*
     * Override to specify what is the distance of objects x and y.
     * The funcion have to be be symetrical, i.e. 
     * x.getDistance(y) == y.getDistance(x)
     */
    public float getDistance(T m);
}

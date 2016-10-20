package loschmidt.clustering.api.example;

public class VectorInstance {

    private final double[] vector;

    public VectorInstance(double[] vector) {
        this.vector = vector;
    }

    public double[] getArray() {
        return vector;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (double d : vector) {
            sb.append(d).append(" ");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append("]");
        return sb.toString();
    }

}

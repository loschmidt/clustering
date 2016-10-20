package loschmidt.clustering.grain;

public class ValuedInteger implements Comparable<ValuedInteger> {

    public int i_;
    public double v_;

    public ValuedInteger(int i, double v) {
        i_ = i;
        v_ = v;
    }

    @Override
    public int compareTo(ValuedInteger vi) {
        return Double.compare(v_, vi.v_);
    }

    @Override
    public boolean equals(Object o) {
        ValuedInteger vi = (ValuedInteger) o;
        return i_ == vi.i_;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + this.i_;
        return hash;
    }
}

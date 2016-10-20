package loschmidt.clustering.hierarchical;

/**
 *
 * @author Antonin Pavelka
 */
public class Merge implements Comparable<Merge> {

	private int id_ = Integer.MIN_VALUE;
	private final int x_;
	private final int y_;
	private final float distance_;

	public Merge(int x, int y, float distance) {
		this.x_ = x;
		this.y_ = y;
		this.distance_ = distance;
	}

	public void setID(int id) {
		this.id_ = id;
	}

	public int getID() {
		return id_ == Integer.MIN_VALUE ? x_ : id_;
	}

    public float getDistance() {
		return distance_;
    }

    public int getX() {
        return x_;
    }

    public int getY() {
        return y_;
    }

    @Override
	public boolean equals(Object o) {
		if (!(o instanceof Merge)) {
			return false;
		}

        Merge m = (Merge) o;
        return x_ == m.x_ && y_ == m.y_;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + this.x_;
        hash = 41 * hash + this.y_;
        return hash;
    }

    @Override
    public int compareTo(Merge m) {
		int c = Float.compare(distance_, m.distance_);
        if (0 == c) {
            c = Integer.compare(x_, m.x_);
        }
        if (0 == c) {
            c = Integer.compare(y_, m.y_);
        }
        return c;
    }
}

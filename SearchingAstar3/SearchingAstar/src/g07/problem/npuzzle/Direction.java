package g07.problem.npuzzle;

public enum Direction {
	LEFT,
	RIGHT,
	UP,
	DOWN;
	
	public String toString() {
        return super.toString().substring(0, 1);
    }
}

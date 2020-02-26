package UTILS;

import java.util.HashSet;

public class TempContext {

	int index;
	int start;
	int end;
	HashSet<Integer> intersections;

	public TempContext(int index, int start, int end) {
		this.index = index;
		this.start = start;
		this.end = end;
		this.intersections = new HashSet<>();
	}

	public boolean isTempsIntersecting(TempContext t) {
		return (t.start >= start && t.start <= end) || (t.end >= start && t.end <= end)
				|| (start >= t.start && start <= t.end) || (end >= t.start && end <= t.end);
	}
}
package UTILS;

import java.util.*;

class GraphManager {

	private int verticesCount;
	private ArrayList<ArrayList<Integer>> adjacencies;
	private HashMap<Integer, Integer> tempIndexToRegister;

	GraphManager(int verticesCount) {
		this.verticesCount = verticesCount;
		adjacencies = new ArrayList<>();
		tempIndexToRegister = new HashMap<>();

		for (int i = 0; i < verticesCount; i++)
			adjacencies.add(new ArrayList<>());
	}

	HashMap<Integer, Integer> greedyColoring() {
		Integer result[] = new Integer[verticesCount];
		boolean available[] = new boolean[verticesCount];

		Arrays.fill(result, null);
		Arrays.fill(available, true);
		result[0] = 0;

		for (int vertex = 0; vertex < verticesCount; vertex++) {
			for (Integer adj : adjacencies.get(vertex)) {
				if (result[adj] != null) {
					available[result[adj]] = false;
				}
			}
			
			int registerNum;
			for (registerNum = 0; registerNum < verticesCount; registerNum++) {
				if (available[registerNum])
					break;
			}
			
			result[vertex] = registerNum;
			Arrays.fill(available, true);
		}
		
		for (int i = 0; i < verticesCount; i++) {
			tempIndexToRegister.put(i, result[i]);
		}
		
		return tempIndexToRegister;
	}

	void addEdge(int vertex1, int vertex2) {
		adjacencies.get(vertex1).add(vertex2);
		adjacencies.get(vertex2).add(vertex1);
	}

}
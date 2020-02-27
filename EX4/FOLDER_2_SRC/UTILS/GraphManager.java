package UTILS;

import java.util.*;

class GraphManager {

	private int verticesCount;
	private LinkedList<Integer> adjacencies[];
	private HashMap<Integer, Integer> tempIndexToRegister;

	@SuppressWarnings("unchecked")
	GraphManager(int verticesCount) {
		this.verticesCount = verticesCount;
		adjacencies = new LinkedList[verticesCount];
		tempIndexToRegister = new HashMap<>();

		for (int i = 0; i < verticesCount; ++i)
			adjacencies[i] = new LinkedList<Integer>();
	}

	// inspired by geeksforgeeks algorithm for greedy coloring
	HashMap<Integer, Integer> greedyColoring() {
		Integer result[] = new Integer[verticesCount];
		boolean available[] = new boolean[verticesCount];

		Arrays.fill(result, -1);
		result[0] = 0;
		Arrays.fill(available, true);

		for (int vertex = 1; vertex < verticesCount; vertex++) {
            Iterator<Integer> adjancyIterator = adjacencies[vertex].iterator() ; 
            while (adjancyIterator.hasNext()) 
            { 
                int currentAdj = adjancyIterator.next(); 
                if (result[currentAdj] != -1) 
                    available[result[currentAdj]] = false; 
            } 

            int colorIndex; 
            for (colorIndex = 0; colorIndex < verticesCount; colorIndex++){ 
                if (available[colorIndex]) 
                    break; 
            } 
  
            result[vertex] = colorIndex;
            
            Arrays.fill(available, true); 
		}
		
		for (int i = 0; i < verticesCount; i++) {
			tempIndexToRegister.put(i, result[i]);
		}
		
		return tempIndexToRegister;
	}

	void addEdge(int vertex1, int vertex2) {
		adjacencies[vertex1].add(vertex2);
		adjacencies[vertex2].add(vertex1);
	}

}
package ru.vsu.cs.gelem.task7;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Реализация графа на основе матрицы смежности
 */
public class AdjMatrixGraph implements Graph {

    private boolean[][] adjMatrix = null;
    private int vCount = 0;
    private int eCount = 0;

    /**
     * Конструктор
     * @param vertexCount Кол-во вершин графа (может увеличиваться при добавлении ребер)
     */
    public AdjMatrixGraph(int vertexCount) {
        adjMatrix = new boolean[vertexCount][vertexCount];
        vCount = vertexCount;
    }

    /**
     * Конструктор без парметров
     * (лучше не использовать, т.к. при добавлении вершин каждый раз пересоздается матрица)
     */
    public AdjMatrixGraph() {
        this(0);
    }

    @Override
    public int vertexCount() {
        return vCount;
    }

    @Override
    public int edgeCount() {
        return eCount;
    }

    private boolean isReachable(boolean[][] matrix, int v1, int v2) {
        boolean[][] matrixToCheck = copyArray(matrix);
        for (int i = 0; i < matrixToCheck[0].length; i++) {
            for (int j = 0; j < matrixToCheck.length; j++) {
                if (matrixToCheck[j][i]) {
                    sumRows(matrixToCheck[i], matrixToCheck[j]);
                }
            }
        }
        return matrixToCheck[v1][v2];
    }

    private boolean[][] copyArray(boolean[][] arr) {
        boolean[][] newArr = new boolean[arr.length][arr[0].length];
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                newArr[i][j] = arr[i][j];
            }
        }
        return newArr;
    }

    private void sumRows(boolean[] mainRow, boolean[] rowToSum) {
        for (int i = 0; i < rowToSum.length; i++) {
            if (mainRow[i]) {
                rowToSum[i] = true;
            }
        }
    }

    public HashMap<Integer, ArrayList<Integer>> getRemovableEdges(int v1, int v2, int k) {
        boolean[][] matrix = copyArray(adjMatrix);
        HashMap<Integer, ArrayList<Integer>> edgesToRemove = new HashMap<>();
        class Inner {
            private void findRemovableEdges(boolean[][] matrix, ArrayList<Integer> vertexes, int v1, int v2, int k) {
                if (k == 0) {
                    return;
                }
                for (int i = 0; i < matrix.length; i++) {
                    for (int j = i + 1; j < matrix[0].length; j++) {
                        if (matrix[i][j]) {
                            removeEdgeSub(matrix, i, j);
                            vertexes.add(i);
                            vertexes.add(j);
                            if (!isReachable(matrix, v1, v2)) {
                                ArrayList<Integer> arr1 = new ArrayList<>(vertexes);
                                ArrayList<ArrayList<Integer>> list = new ArrayList<>();
                                for (int l = 0; l <= edgesToRemove.size() - 1; l++) {
                                    list.add(edgesToRemove.get(l));
                                }
                                if (!isSame(arr1, list)) {
                                    edgesToRemove.put(edgesToRemove.size(), arr1);
                                }
                                if (!vertexes.isEmpty()) {
                                    vertexes.removeLast();
                                    vertexes.removeLast();
                                }
                            } else {
                                findRemovableEdges(matrix, vertexes, v1, v2, k - 1);
                                if (!vertexes.isEmpty()) {
                                    vertexes.removeLast();
                                    vertexes.removeLast();
                                }
                            }
                            addEdgeSub(matrix, i, j);
                        }
                    }
                }
            }
        }
        new Inner().findRemovableEdges(matrix, new ArrayList<>(), v1, v2, k);
        return edgesToRemove;
    }

    private boolean isSame(ArrayList<Integer> list, ArrayList<ArrayList<Integer>> lists) {
        for (int i = 0; i < lists.size(); i++) {
            if (list.size() == lists.get(i).size()) {
                ArrayList<ArrayList<Integer>> arr1 = new ArrayList<>();
                ArrayList<ArrayList<Integer>> arr2 = new ArrayList<>();
                for (int j = 0; j < list.size(); j += 2) {
                    ArrayList<Integer> a = new ArrayList<>();
                    ArrayList<Integer> b = new ArrayList<>();
                    a.add(list.get(j));
                    a.add(list.get(j + 1));
                    b.add(lists.get(i).get(j));
                    b.add(lists.get(i).get(j + 1));
                    arr1.add(a);
                    arr2.add(b);
                }
                int cnt = 0;
                for (int j = 0; j < arr1.size(); j++) {
                    for (int k = 0; k < arr2.size(); k++) {
                        if (arr1.get(j).equals(arr2.get(k))) {
                            cnt++;
                        }
                    }
                }
                if (cnt == arr1.size()) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void addAdge(int v1, int v2) {
        int maxV = Math.max(v1, v2);
        if (maxV >= vertexCount()) {
            adjMatrix = Arrays.copyOf(adjMatrix, maxV + 1);
            for (int i = 0; i <= maxV; i++) {
                adjMatrix[i] = i < vCount ? Arrays.copyOf(adjMatrix[i], maxV + 1) : new boolean[maxV + 1];
            }
            vCount = maxV + 1;
        }
        if (!adjMatrix[v1][v2]) {
            adjMatrix[v1][v2] = true;
            eCount++;
            // для наследников
            if (!(this instanceof Digraph)) {
                adjMatrix[v2][v1] = true;
            }
        }
    }

    private void addEdgeSub(boolean[][] matrix, int v1, int v2) {
        matrix[v1][v2] = true;
        // для наследников
        if (!(this instanceof Digraph)) {
            matrix[v2][v1] = true;
        }
    }

    @Override
    public void removeAdge(int v1, int v2) {
        if (adjMatrix[v1][v2]) {
            adjMatrix[v1][v2] = false;
            eCount--;
            // для наследников
            if (!(this instanceof Digraph)) {
                adjMatrix[v2][v1] = false;
            }
        }
    }

    private void removeEdgeSub(boolean[][] matrix, int v1, int v2) {
        if (matrix[v1][v2]) {
            matrix[v1][v2] = false;
            // для наследников
            if (!(this instanceof Digraph)) {
                matrix[v2][v1] = false;
            }
        }
    }

    @Override
    public Iterable<Integer> adges(int v) {
        return new Iterable<Integer>() {
            Integer nextAdj = null;

            @Override
            public Iterator<Integer> iterator() {
                for (int i = 0; i < vCount; i++) {
                    if (adjMatrix[v][i]) {
                        nextAdj = i;
                        break;
                    }
                }

                return new Iterator<Integer>() {
                    @Override
                    public boolean hasNext() {
                        return nextAdj != null;
                    }

                    @Override
                    public Integer next() {
                        Integer result = nextAdj;
                        nextAdj = null;
                        for (int i = result + 1; i < vCount; i++) {
                            if (adjMatrix[v][i]) {
                                nextAdj = i;
                                break;
                            }
                        }
                        return result;
                    }
                };
            }
        };
    }

    // Перегрузка для быстродействия
    @Override
    public boolean isAdj(int v1, int v2) {
        return adjMatrix[v1][v2];
    }
}

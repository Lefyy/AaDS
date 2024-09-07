package ru.vsu.cs.gelem.task3;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static java.lang.Math.abs;

public class Position {
    private int pos;
    private ArrayList<Integer> posToElim;
    private HashSet<Integer> eliminatedPoses;
    private ArrayList<Integer> usedVar;

    public Position(int pos, HashSet<Integer> elimedPoses) {
        this.pos = pos;
        eliminatedPoses = new HashSet<>();
        eliminatedPoses.addAll(checkPosesToElim(pos));
        eliminatedPoses.addAll(elimedPoses);
        posToElim = checkPosesToElim(pos);
        usedVar = new ArrayList<>();
    }

    private ArrayList<Integer> checkPosesToElim(int pos) {
        ArrayList<Integer> toEliminate = new ArrayList<>();
        int elemR = pos / 8;
        int elemC = pos % 8;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if(i == elemR || j == elemC || (abs(elemR-i) == abs(elemC-j))) {
                    toEliminate.add(8 * i + j);
                }
            }
        }
        return toEliminate;
    }

    public int getPos() {
        return pos;
    }

    public ArrayList<Integer> getPosToElim() {
        return posToElim;
    }

    public void addUsedVar(int pos) {
        usedVar.add(pos);
    }

    public ArrayList<Integer> getUsedVar() {
        return usedVar;
    }

    public HashSet<Integer> getElimedPoses() {
        return eliminatedPoses;
    }
}

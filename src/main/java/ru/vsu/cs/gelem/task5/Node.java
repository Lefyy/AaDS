package ru.vsu.cs.gelem.task5;

public class Node {
    public SimpleBinaryTree.SimpleTreeNode node;
    public Integer value;
    public Integer sum;
    public String road;

    public Node(SimpleBinaryTree.SimpleTreeNode node, String road) {
        this.node = node;
        this.value = (Integer) node.value;
        this.sum = node.getSum();
        this.road = road;
    }
}

package ru.vsu.cs.gelem.tree.bst.rb;

import ru.vsu.cs.gelem.tree.bst.BSTreeMap;
import ru.vsu.cs.gelem.tree.bst.BSTree;

/**
 * Реализация словаря на базе красно-черных деревьев
 *
 * @param <K>
 * @param <V>
 */
public class RBTreeMap<K extends Comparable<K>, V> implements BSTreeMap<K, V> {

    private final BSTree<MapTreeEntry<K, V>> tree = new RBTree<>();

    @Override
    public BSTree<MapTreeEntry<K, V>> getTree() {
        return tree;
    }
}

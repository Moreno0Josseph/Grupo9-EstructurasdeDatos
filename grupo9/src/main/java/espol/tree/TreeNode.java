/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package espol.tree;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Joseph
 */
public class TreeNode<E> {

    private E data;
    private List<TreeNode<E>> children; // lista de posibles hijos diferentes

    public TreeNode(E data) {
        this.data = data;
        this.children = new ArrayList<>();
    }

    public E getData() {
        return data;
    }

    public List<TreeNode<E>> getChildren() {
        return children;
    }

    public void addChild(TreeNode<E> child) {
        children.add(child);
    }

    public boolean isLeaf() {
        return children.isEmpty();
    }
}
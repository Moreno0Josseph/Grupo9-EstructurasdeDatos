/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package espol.tree;

/**
 *
 * @author Joseph
 */
public class Tree<E> {

    private TreeNode<E> root;

    public Tree(E data) {
        root = new TreeNode<>(data);
    }

    public TreeNode<E> getRoot() {
        return root;
    }

    public boolean isEmpty() {
        return root == null;
    }
}

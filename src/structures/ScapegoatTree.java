package structures;

import java.util.Iterator;

public class ScapegoatTree<T extends Comparable<T>> extends
		BinarySearchTree<T> {
	private int upperBound;


	@Override
	public void add(T t) {
		if(t == null){
			throw new NullPointerException();
		}
		upperBound++;
		BSTNode<T> newNode = new BSTNode<T>(t, null, null);
		root = addToSubtree(root, newNode);
		if(height() > Math.log(upperBound) / Math.log((double)3/2)){
			BSTNode<T> parentNewNode = newNode.parent;
			ScapegoatTree<T> subTree = new ScapegoatTree<T>();
			while((double)subtreeSize(newNode) / subtreeSize(parentNewNode) <= (double)2/3){
				parentNewNode = parentNewNode.parent;
				newNode = newNode.parent;
			}
			subTree.root = parentNewNode;
			subTree.balance();
			BSTNode<T> newParent = parentNewNode.parent;
			if (subTree.root.getData().compareTo(parentNewNode.getData()) <= 0){
				newParent.setLeft(subTree.root);
			}
			else{
				newParent.setRight(subTree.root);
			}
		}
	}

	@Override
	public boolean remove(T element) {
		if(element == null){
			throw new NullPointerException();
		}
		boolean bool = contains(element);
		if(bool){
			root = removeFromSubtree(root, element);
		}
		if(upperBound > 2 * size()){
			balance();
			upperBound = size();
		}
		return bool;
	}
}

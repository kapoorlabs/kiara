package com.kapoorlabs.kiara.domain;

import java.util.LinkedList;

import lombok.Data;

@Data
@Deprecated
public class SBTree<T extends Comparable<T>> {

	private SBTreeElement<T> root;

	private enum Operation {
		LT, LTE, GT, GTE, BT
	}

	public void insert(SBTreeElement<T> element) {

		root = insert(root, element);

	}

	private SBTreeElement<T> insert(SBTreeElement<T> currentNode, SBTreeElement<T> element) {

		if (currentNode == null) {
			return element;
		}

		if (element.getValue().compareTo(currentNode.getValue()) < 0) {
			currentNode.setLeftChild(insert(currentNode.getLeftChild(), element));
		} else if (element.getValue().compareTo(currentNode.getValue()) > 0) {
			currentNode.setRightChild(insert(currentNode.getRightChild(), element));
		} else {
			return currentNode;
		}

		currentNode.setHeight(1 + Math.max(height(currentNode.getLeftChild()), height(currentNode.getRightChild())));

		int balance = getBalance(currentNode);

		// If this node becomes unbalanced, then there
		// are 4 cases Left Left Case
		if (balance > 1 && element.getValue().compareTo(currentNode.getLeftChild().getValue()) < 0) {
			return rightRotate(currentNode);
		}

		// Right Right Case
		if (balance < -1 && element.getValue().compareTo(currentNode.getRightChild().getValue()) > 0) {
			return leftRotate(currentNode);
		}

		// Left Right Case
		if (balance > 1 && element.getValue().compareTo(currentNode.getLeftChild().getValue()) > 0) {
			currentNode.setLeftChild(leftRotate(currentNode.getLeftChild()));
			return rightRotate(currentNode);
		}

		// Right Left Case
		if (balance < -1 && element.getValue().compareTo(currentNode.getRightChild().getValue()) < 0) {
			currentNode.setRightChild(rightRotate(currentNode.getRightChild()));
			return leftRotate(currentNode);
		}

		return currentNode;

	}

	SBTreeElement<T> rightRotate(SBTreeElement<T> elementToRight) {
		SBTreeElement<T> newParent = elementToRight.getLeftChild();
		SBTreeElement<T> leftOfRightChild = newParent.getRightChild();

		// Perform rotation
		newParent.setRightChild(elementToRight);
		elementToRight.setLeftChild(leftOfRightChild);

		int elementToRightHeight = Math.max(height(elementToRight.getLeftChild()),
				height(elementToRight.getRightChild()));
		int newParentHeight = Math.max(height(newParent.getLeftChild()), height(newParent.getRightChild()));

		// Update heights
		elementToRight.setHeight(elementToRightHeight == 0 ? 0 : 1 + elementToRightHeight);
		newParent.setHeight(newParentHeight == 0 ? 0 : 1 + newParentHeight);

		// Return new root
		return newParent;
	}

	SBTreeElement<T> leftRotate(SBTreeElement<T> elementToLeft) {
		SBTreeElement<T> newParent = elementToLeft.getRightChild();
		SBTreeElement<T> leftOfRightChild = newParent.getLeftChild();

		// Perform rotation
		newParent.setLeftChild(elementToLeft);
		elementToLeft.setRightChild(leftOfRightChild);

		int elementToLeftHeight = Math.max(height(elementToLeft.getLeftChild()), height(elementToLeft.getRightChild()));
		int newParentHeight = Math.max(height(newParent.getLeftChild()), height(newParent.getRightChild()));
		// Update heights
		elementToLeft.setHeight(elementToLeftHeight == 0 ? 0 : 1 + elementToLeftHeight);
		newParent.setHeight(newParentHeight == 0 ? 0 : 1 + newParentHeight);

		// Return new root
		return newParent;
	}

	int getBalance(SBTreeElement<T> node) {
		if (node == null)
			return 0;

		return height(node.getLeftChild()) - height(node.getRightChild());
	}

	int height(SBTreeElement<T> node) {
		if (node == null)
			return 0;

		return node.getHeight();
	}

	public LinkedList<T> getAllLessThan(T upperRange) {

		LinkedList<T> list = new LinkedList<>();

		inOrderTraversal(root, list, Operation.LT, null, upperRange);

		return list;

	}

	public LinkedList<T> getAllLessThanEqual(T upperRange) {

		LinkedList<T> list = new LinkedList<>();

		inOrderTraversal(root, list, Operation.LTE, null, upperRange);

		return list;

	}

	public LinkedList<T> getAllGreaterThan(T lowerRange) {

		LinkedList<T> list = new LinkedList<>();

		reverseOrderTraversal(root, list, Operation.GT, lowerRange, null);

		return list;

	}

	public LinkedList<T> getAllGreaterThanEqual(T lowerRange) {

		LinkedList<T> list = new LinkedList<>();

		reverseOrderTraversal(root, list, Operation.GTE, lowerRange, null);

		return list;

	}
	
	
	public LinkedList<T> getAllBetween(T lowerRange, T upperRange) {

		LinkedList<T> list = new LinkedList<>();

		inOrderTraversalBetween(root, list, lowerRange, upperRange);

		return list;

	}

	private void inOrderTraversal(SBTreeElement<T> currentNode, LinkedList<T> list, Operation op, T lowerRange,
			T upperRange) {

		if (currentNode == null) {
			return;
		}

		inOrderTraversal(currentNode.getLeftChild(), list, op, lowerRange, upperRange);

		if (op == Operation.LT) {

			if (currentNode.getValue().compareTo(upperRange) < 0) {
				list.add(currentNode.getValue());
				inOrderTraversal(currentNode.getRightChild(), list, op, lowerRange, upperRange);
			}

		} else if (op == Operation.LTE) {
			if (currentNode.getValue().compareTo(upperRange) <= 0) {
				list.add(currentNode.getValue());
				inOrderTraversal(currentNode.getRightChild(), list, op, lowerRange, upperRange);
			}
		}

	}

	private void inOrderTraversalBetween(SBTreeElement<T> currentNode, LinkedList<T> list, T lowerRange, T upperRange) {

		if (currentNode == null) {
			return;
		}

		inOrderTraversalBetween(currentNode.getLeftChild(), list, lowerRange, upperRange);

		if (currentNode.getValue().compareTo(upperRange) <= 0) {
			if (currentNode.getValue().compareTo(lowerRange) >= 0) {
				list.add(currentNode.getValue());
			}
			inOrderTraversalBetween(currentNode.getRightChild(), list, lowerRange, upperRange);
		}

	}

	private void reverseOrderTraversal(SBTreeElement<T> currentNode, LinkedList<T> list, Operation op, T lowerRange,
			T upperRange) {

		if (currentNode == null) {
			return;
		}

		reverseOrderTraversal(currentNode.getRightChild(), list, op, lowerRange, upperRange);

		if (op == Operation.GT) {

			if (currentNode.getValue().compareTo(lowerRange) > 0) {
				list.add(currentNode.getValue());
				reverseOrderTraversal(currentNode.getLeftChild(), list, op, lowerRange, upperRange);
			}

		} else if (op == Operation.GTE) {
			if (currentNode.getValue().compareTo(lowerRange) >= 0) {
				list.add(currentNode.getValue());
				reverseOrderTraversal(currentNode.getLeftChild(), list, op, lowerRange, upperRange);
			}
		}

	}

}

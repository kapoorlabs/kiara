package com.kapoorlabs.kiara.domain;

import lombok.Data;

@Data
@Deprecated
public class SBTreeElement<T extends Comparable<T>> {
	
	
	private T value;
	
	private SBTreeElement<T> leftChild;
	
	private SBTreeElement<T> rightChild;
	
	private int height;

	
	public SBTreeElement(T value) {
		
		this.value = value;
		leftChild = null;
		rightChild = null;
		height = 0;		
	}
	
}

package com.kapoorlabs.kiara.domain;

import lombok.Data;

@Data
public class SpellCheckTrie {

    SpellCheckNode headNode;

    public SpellCheckTrie() {
        headNode = new SpellCheckNode();
    }

    public void insert(String inpStr) {

        SpellCheckNode currentNode = headNode;
        if (inpStr == null || inpStr.isEmpty()) {
            return;
        }

        for(char currentChar: inpStr.toCharArray()) {
            if (!currentNode.getChildren().containsKey(currentChar)) {
                SpellCheckNode childNode = new SpellCheckNode(currentChar);
                childNode.setParent(currentNode);
                currentNode.getChildren().put(currentChar, childNode);
            }
            currentNode = currentNode.getChildren().get(currentChar);
        }

        SpellCheckNode tailNode = new SpellCheckNode();
        tailNode.setParent(currentNode);
        currentNode.getChildren().put('*', tailNode);

    }

}

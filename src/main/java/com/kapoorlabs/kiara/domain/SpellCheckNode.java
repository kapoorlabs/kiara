package com.kapoorlabs.kiara.domain;

import lombok.Data;

import java.util.HashMap;
import java.util.TreeMap;

@Data
public class SpellCheckNode {

    private char currentChar;

    private TreeMap<Character, SpellCheckNode> children;

    private SpellCheckNode parent;

    public SpellCheckNode() {
        this.currentChar = '*';
        children =  new TreeMap<>();
    }

    public SpellCheckNode(char currentChar) {
        this.currentChar = currentChar;
        children =  new TreeMap<>();
    }
}

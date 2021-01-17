package com.kapoorlabs.kiara.domain;

import java.util.TreeMap;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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

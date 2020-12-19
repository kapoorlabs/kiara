package com.kapoorlabs.kiara.util;

import com.kapoorlabs.kiara.domain.SpellCheckNode;
import com.kapoorlabs.kiara.domain.SpellCheckTrie;
import lombok.AllArgsConstructor;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class SpellCheckUtil {

    @AllArgsConstructor
    private static class BfsNode {
        SpellCheckNode spellCheckNode;
        String strSoFar;
    }

    public static String removeStopWords(String inpStr) {
        return inpStr.replaceAll("[,.:;?+=\\-*&^%$#@!~'`\"|\\\\/]", " ")
                .replaceAll("[\\[\\]{}()]", "");
    }


    public static String getOneEditKeyword(String inpStr, SpellCheckTrie spellCheckTrie) {

        if (inpStr == null || inpStr.length() <= 3) {
            return null;
        }

        int editDistance = 0;
        int charPos = 0;

        SpellCheckNode currentNode = spellCheckTrie.getHeadNode();

        while (charPos < inpStr.length()) {

            if (editDistance > 1) {
                return null;
            }

            if (currentNode.getChildren().containsKey(inpStr.charAt(charPos))) {
                currentNode = currentNode.getChildren().get(inpStr.charAt(charPos));
                charPos++;
            } else if (charPos < inpStr.length() - 1 && currentNode.getChildren().containsKey(inpStr.charAt(charPos + 1))) {
                //Next input matches current trie level - Deletion needed in input
                editDistance++;
                currentNode = currentNode.getChildren().get(inpStr.charAt(charPos + 1));
                charPos += 2;
            } else {
                Map<Character, SpellCheckNode> nextLevelCharMap = new HashMap<>();
                for (char currentLevelKey : currentNode.getChildren().keySet()) {
                    nextLevelCharMap.putAll(currentNode.getChildren().get(currentLevelKey).getChildren());
                }

                if (nextLevelCharMap.containsKey(inpStr.charAt(charPos))) {
                    //Current input matches next trie level - Insertion needed in input
                    editDistance++;
                    currentNode = nextLevelCharMap.get(inpStr.charAt(charPos));
                    charPos++;
                } else if (charPos < inpStr.length() - 1 && nextLevelCharMap.containsKey(inpStr.charAt(charPos + 1))) {
                    //next input matches next trie level - update needed in input
                    editDistance++;
                    currentNode = nextLevelCharMap.get(inpStr.charAt(charPos + 1));
                    charPos += 2;
                } else if (charPos == inpStr.length() - 1) {
                    editDistance++;
                    
                    if (nextLevelCharMap.containsKey('*')) {
                    	currentNode = nextLevelCharMap.get('*').getParent();
                    }
                    
                    break;
                } else {
                    return null;
                }
            }
        }

        if (!currentNode.getChildren().containsKey('*')) {
            currentNode = currentNode.getChildren().firstEntry().getValue();
            editDistance++;
        }

        if (editDistance > 1) {
            return null;
        }

        StringBuilder result = new StringBuilder();

        while (currentNode != null) {
            if (currentNode.getCurrentChar() != '*') {
                result.insert(0, currentNode.getCurrentChar());
            }

            currentNode = currentNode.getParent();
        }


        return result.toString();
    }

    public static List<String> getTextPredictions(String inpStr, SpellCheckTrie spellCheckTrie) {

        if (inpStr == null || inpStr.trim().isEmpty()) {
            return null;
        }

        SpellCheckNode currentNode = spellCheckTrie.getHeadNode();

        for (char inpChar : inpStr.toCharArray()) {

            if (currentNode.getChildren().containsKey(inpChar)) {
                currentNode = currentNode.getChildren().get(inpChar);
            } else {
                return null;
            }
        }

        List<String> result = new LinkedList<>();

        Queue<BfsNode> bfsQueue = new LinkedList<>();
        bfsQueue.add(new BfsNode(currentNode, inpStr));

        while (!bfsQueue.isEmpty()) {

            BfsNode bfsNode = bfsQueue.poll();

            for (char key : bfsNode.spellCheckNode.getChildren().keySet()) {

                if (key == '*') {
                    result.add(bfsNode.strSoFar);
                    if (result.size() == 3) {
                        break;
                    }
                } else {
                    SpellCheckNode bfsSpellCheckNode = bfsNode.spellCheckNode.getChildren().get(key);
                    bfsQueue.add(new BfsNode(bfsSpellCheckNode, bfsNode.strSoFar + key));
                }

            }
        }

        return result;

    }


}

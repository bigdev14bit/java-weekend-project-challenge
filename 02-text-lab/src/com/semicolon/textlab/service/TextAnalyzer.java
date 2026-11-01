package com.semicolon.textlab.service;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextAnalyzer {
    private String originalText;
    private String editedText;

    public TextAnalyzer(String text) {
        this.originalText = (text == null) ? "" : text;
        this.editedText = this.originalText;
    }

    public String getOriginalText() { return originalText; }
    public String getEditedText() { return editedText; }

    public int getCharCountWithSpaces() {
        return editedText.length();
    }

    public int getCharCountWithoutSpaces() {
        return editedText.replaceAll("\\s", "").length();
    }

    public int getWordCount() {
        if (editedText.trim().isEmpty()) return 0;
        String[] words = editedText.trim().split("[^a-zA-Z0-9]+");
        return words.length;
    }

    public int getSentenceCount() {
        if (editedText.trim().isEmpty()) return 0;

        String normalized = editedText.trim().replaceAll("[.!?]+", ".");

        String[] parts = normalized.split("\\.");

        int count = 0;
        for (String part : parts) {
            if (!part.trim().isEmpty()) {
                count++;
            }
        }

        return count;
    }

    public void cleanWhitespace() {
        if (editedText.trim().isEmpty()) {
            editedText = "";
            return;
        }
        StringBuilder sb = new StringBuilder();
        boolean lastWasSpace = false;
        for (char c : editedText.trim().toCharArray()) {
            if (Character.isWhitespace(c)) {
                if (!lastWasSpace) {
                    sb.append(' ');
                    lastWasSpace = true;
                }
            } else {
                sb.append(c);
                lastWasSpace = false;
            }
        }
        editedText = sb.toString();
    }

    public int countWordFrequency(String targetWord) {
        if (targetWord == null || targetWord.trim().isEmpty()) return 0;

        Pattern pattern = Pattern.compile("\\b" + Pattern.quote(targetWord.trim()) + "\\b", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(editedText);

        int count = 0;
        while (matcher.find()) {
            count++;
        }
        return count;
    }

    public void replaceWord(String targetWord, String replacementWord) {
        if (targetWord == null || targetWord.trim().isEmpty()) return;

        Pattern pattern = Pattern.compile("\\b" + Pattern.quote(targetWord.trim()) + "\\b", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(editedText);
        editedText = matcher.replaceAll(Matcher.quoteReplacement(replacementWord));
    }

    public boolean isPalindrome() {
        String clean = editedText.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
        if (clean.isEmpty()) return false;

        int left = 0;
        int right = clean.length() - 1;
        while (left < right) {
            if (clean.charAt(left) != clean.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }

    public Set<String> getLongestWords() {
        Set<String> longestWords = new HashSet<>();
        if (editedText.trim().isEmpty()) return longestWords;

        String[] words = editedText.trim().split("[^a-zA-Z0-9]+");
        int maxLength = 0;

        for (String word : words) {
            if (word.length() > maxLength) {
                maxLength = word.length();
                longestWords.clear();
                longestWords.add(word);
            } else if (word.length() == maxLength) {
                longestWords.add(word);
            }
        }
        return longestWords;
    }
}

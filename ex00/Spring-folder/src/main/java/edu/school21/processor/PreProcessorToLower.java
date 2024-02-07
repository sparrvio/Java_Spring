package edu.school21.processor;

public class PreProcessorToLower implements PreProcessor {
    @Override
    public String modificationString(String string) {
        return string.toLowerCase();
    }
}

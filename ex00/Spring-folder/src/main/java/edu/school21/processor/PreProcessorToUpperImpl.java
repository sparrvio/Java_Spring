package edu.school21.processor;

public class PreProcessorToUpperImpl implements PreProcessor {
    @Override
    public String modificationString(String string) {
        return string.toUpperCase();
    }
}
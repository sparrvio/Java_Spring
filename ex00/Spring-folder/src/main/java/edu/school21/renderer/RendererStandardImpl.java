package edu.school21.renderer;

import edu.school21.processor.PreProcessor;

public class RendererStandardImpl implements Renderer{
    PreProcessor processor;
    String message = null;

    public RendererStandardImpl(PreProcessor processor){
        this.processor = processor;
    }

    @Override
    public void setMessage(String message) {
        this.message = processor.modificationString(message);
    }

    @Override
    public void printMessage(String message) {
        setMessage(message);
        System.out.println(this.message);
    }
}
package edu.school21.renderer;

import edu.school21.processor.PreProcessor;

public class RendererErrImpl implements Renderer {

    PreProcessor processor;
    String message = null;


    public RendererErrImpl(PreProcessor processor) {
        this.processor = processor;
    }

    public void setMessage(String message) {

        this.message = processor.modificationString(message);
    }

    @Override
    public void printMessage(String message) {
        setMessage(message);
        System.err.println(this.message);
    }


}

package edu.school21.printer;

import edu.school21.renderer.Renderer;

public class PrinterWithPrefixImpl implements Printer {
    String prefix = null;
    String message = null;
    Renderer renderer;

    public PrinterWithPrefixImpl(Renderer renderer) {
        this.renderer = renderer;
    }

    @Override
    public void print(String message) {
        this.message = prefix + message;
        renderer.printMessage(this.message);
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix + " ";
    }
}

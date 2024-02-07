package edu.school21.printer;

import edu.school21.renderer.Renderer;

import java.time.LocalDateTime;

public class PrinterWithDateTimeImpl implements Printer {
    LocalDateTime now = LocalDateTime.now();
    String message = null;

    Renderer renderer;

    public PrinterWithDateTimeImpl(Renderer renderer) {
        this.renderer = renderer;
    }

    @Override
    public void print(String message) {
        this.message = now.toString() + " " + message;
        renderer.printMessage(this.message);
    }
}

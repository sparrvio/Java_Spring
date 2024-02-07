package edu.school21;

import edu.school21.printer.Printer;
import edu.school21.printer.PrinterWithPrefixImpl;
import edu.school21.processor.PreProcessor;
import edu.school21.processor.PreProcessorToUpperImpl;
import edu.school21.renderer.Renderer;
import edu.school21.renderer.RendererErrImpl;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Program {
    public static void main(String[] args) {
        PreProcessor preProcessor = new PreProcessorToUpperImpl();
        Renderer renderer = new RendererErrImpl(preProcessor);
        PrinterWithPrefixImpl printer1 = new PrinterWithPrefixImpl(renderer);
        printer1.setPrefix("Prefix");
        printer1.print("Hello!");

        try (ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("context.xml")) {
            Printer printer = context.getBean("PrinterWithPrefixImpl", Printer.class);
            printer.print("Hello!");
        } catch (BeanCreationException e) {
            e.printStackTrace();
        }
    }
}

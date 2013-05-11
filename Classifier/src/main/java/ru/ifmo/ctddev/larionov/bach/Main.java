package ru.ifmo.ctddev.larionov.bach;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.ifmo.ctddev.larionov.bach.classifier.IClassifier;
import ru.ifmo.ctddev.larionov.bach.site.WeightedPair;

import java.util.List;

/**
 * User: Oleg Larionov
 * Date: 04.05.13
 * Time: 23:19
 */
public class Main {

    private static final String CONFIG_LOCATION = "spring-config.xml";
    private ApplicationContext context;

    private Main() {
        context = new ClassPathXmlApplicationContext(CONFIG_LOCATION);
    }

    public static void main(String[] args) {
        new Main().run();
    }

    private void run() {
        IClassifier classifier = (IClassifier) context.getBean("defaultClassifier");
        Iterable urls = (Iterable) context.getBean("defaultUrls");

        List<WeightedPair> pairs = classifier.classify(urls);
        System.out.println(pairs);
    }
}

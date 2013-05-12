package ru.ifmo.ctddev.larionov.bach.checker;

import org.springframework.beans.factory.annotation.Autowired;
import ru.ifmo.ctddev.larionov.bach.checker.linkstrategy.ILinkStrategy;
import ru.ifmo.ctddev.larionov.bach.checker.textchecker.ITextChecker;
import ru.ifmo.ctddev.larionov.bach.common.site.WeightedPair;

/**
 * User: Oleg Larionov
 * Date: 12.05.13
 * Time: 16:43
 */
public class PageChecker implements IPageChecker {

    private ILinkStrategy strategy;
    private ITextChecker textChecker;

    @Autowired
    public PageChecker(ILinkStrategy strategy, ITextChecker textChecker) {
        this.strategy = strategy;
        this.textChecker = textChecker;
    }

    @Override
    public double checkPair(WeightedPair pair) {
        return 0;
    }
}

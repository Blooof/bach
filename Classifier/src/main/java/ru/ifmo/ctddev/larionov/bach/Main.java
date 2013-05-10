package ru.ifmo.ctddev.larionov.bach;

import ru.ifmo.ctddev.larionov.bach.site.ISite;
import ru.ifmo.ctddev.larionov.bach.site.Site;

/**
 * User: Oleg Larionov
 * Date: 04.05.13
 * Time: 23:19
 */
public class Main {

    public static void main(String[] args) throws Exception {
        ISite googleCom = new Site("http://google.com");
        googleCom.getChildren();
    }
}

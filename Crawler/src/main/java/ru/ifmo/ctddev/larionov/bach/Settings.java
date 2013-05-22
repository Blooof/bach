package ru.ifmo.ctddev.larionov.bach;

import java.io.PrintWriter;
import java.util.Set;

/**
 * User: Oleg Larionov
 * Date: 22.05.13
 * Time: 23:07
 */
public class Settings {

    private final static Settings INSTANCE = new Settings();
    private Set<String> hosts;
    private PrintWriter pw;

    private Settings() {
    }

    public static Settings getInstance() {
        return INSTANCE;
    }

    public boolean checkHost(String host) {
        return hosts.contains(host);
    }

    public void setHosts(Set<String> hosts) {
        this.hosts = hosts;
    }

    public PrintWriter getWriter() {
        return pw;
    }

    public void setWriter(PrintWriter pw) {
        this.pw = pw;
    }
}
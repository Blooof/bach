package ru.ifmo.ctddev.larionov.bach.comparator;

import ru.ifmo.ctddev.larionov.bach.exception.ClassifierRuntimeException;
import ru.ifmo.ctddev.larionov.bach.site.Site;

import java.net.InetAddress;
import java.net.UnknownHostException;

import static java.net.InetAddress.getByName;

/**
 * User: Oleg Larionov
 * Date: 04.05.13
 * Time: 23:52
 */
public class IPAddressComparator implements Comparator {

    @Override
    public double compare(Site first, Site second) {
        try {
            InetAddress firstIp = getByName(first.getUrl());
            InetAddress secondIp = getByName(second.getUrl());
            if (firstIp.equals(secondIp)) {
                return 1;
            } else {
                return -1;
            }
        } catch (UnknownHostException e) {
            throw new ClassifierRuntimeException("Cannot resolve IP addresses", e);
        }
    }
}

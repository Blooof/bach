package ru.ifmo.ctddev.larionov.bach.comparator;

import ru.ifmo.ctddev.larionov.bach.exception.ClassifierRuntimeException;
import ru.ifmo.ctddev.larionov.bach.site.ISite;

import java.net.InetAddress;
import java.net.UnknownHostException;

import static java.net.InetAddress.getByName;

/**
 * User: Oleg Larionov
 * Date: 04.05.13
 * Time: 23:52
 */
public class IPAddressComparator implements IComparator {

    @Override
    public double compare(ISite first, ISite second) {
        try {
            InetAddress firstIp = getByName(first.getHost());
            InetAddress secondIp = getByName(second.getHost());

            return firstIp.equals(secondIp) ? 1 : 0;
        } catch (UnknownHostException e) {
            throw new ClassifierRuntimeException("Cannot resolve IP addresses", e);
        }
    }
}

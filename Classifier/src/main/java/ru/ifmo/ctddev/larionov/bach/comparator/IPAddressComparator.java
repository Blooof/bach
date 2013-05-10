package ru.ifmo.ctddev.larionov.bach.comparator;

import org.apache.log4j.Logger;
import ru.ifmo.ctddev.larionov.bach.site.ISite;
import ru.ifmo.ctddev.larionov.bach.site.WeightedPair;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * User: Oleg Larionov
 * Date: 04.05.13
 * Time: 23:52
 */
public class IPAddressComparator implements IComparator {

    public static final int DEFAULT_THRESHOLD = 10;
    private final Logger logger = Logger.getLogger(IPAddressComparator.class);
    private int threshold;
    private Map<String, Set<String>> ipToHosts = new HashMap<>();
    private Map<String, List<ISite>> hostToPages = new HashMap<>();

    public IPAddressComparator() {
        this(DEFAULT_THRESHOLD);
    }

    public IPAddressComparator(int threshold) {
        this.threshold = threshold;
    }

    @Override
    public List<WeightedPair> createPairs(Iterable<ISite> list) {
        resolveHosts(list);

        return getWeightedPairs();
    }

    private List<WeightedPair> getWeightedPairs() {
        List<WeightedPair> weightedList = new ArrayList<>();
        for (Map.Entry<String, Set<String>> entry : ipToHosts.entrySet()) {
            Set<String> currentHosts = entry.getValue();

            if (currentHosts.size() < threshold && currentHosts.size() > 1) {
                String[] hostsArray = currentHosts.toArray(new String[0]);
                for (int i = 0; i < hostsArray.length - 1; i++) {
                    for (int j = i + 1; j < hostsArray.length; j++) {
                        String firstHost = hostsArray[i];
                        String secondHost = hostsArray[j];

                        if (!firstHost.equals(secondHost)) {
                            List<ISite> firstHostPages = hostToPages.get(firstHost);
                            List<ISite> secondHostPages = hostToPages.get(secondHost);

                            // TODO weight
                            WeightedPair newPair = new WeightedPair(firstHostPages, secondHostPages, 1.0);
                            weightedList.add(newPair);
                        }
                    }
                }
            }
        }
        return weightedList;
    }

    private Map<String, Set<String>> resolveHosts(Iterable<ISite> list) {
        for (ISite site : list) {
            try {
                String host = site.getUrl().getHost();
                InetAddress inetAddress = InetAddress.getByName(host);
                String hostAddress = inetAddress.getHostAddress();
                hostAddress = getThreeOctets(hostAddress);

                addNewIpAddress(site, host, hostAddress);

                addNewPage(site, host);
            } catch (UnknownHostException e) {
                logger.warn("Cannot resolve url " + site.getUrl(), e);
            }
        }

        return ipToHosts;
    }

    private String getThreeOctets(String hostAddress) {
        hostAddress = hostAddress.substring(0, hostAddress.lastIndexOf("."));
        return hostAddress;
    }

    private void addNewIpAddress(ISite site, String host, String hostAddress) {
        if (!ipToHosts.containsKey(hostAddress)) {
            ipToHosts.put(hostAddress, new HashSet<String>());
        }

        Set<String> resolvedHosts = ipToHosts.get(hostAddress);
        if (!resolvedHosts.contains(host)) {
            logger.debug(String.format("Url %s resolved in %s", site.getUrl(), hostAddress));
            resolvedHosts.add(hostAddress);
        }
    }

    private void addNewPage(ISite site, String host) {
        if (!hostToPages.containsKey(host)) {
            hostToPages.put(host, new ArrayList<ISite>());
        }

        hostToPages.get(host).add(site);
    }
}

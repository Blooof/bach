package ru.ifmo.ctddev.larionov.bach.comparator;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import ru.ifmo.ctddev.larionov.bach.common.site.ISite;
import ru.ifmo.ctddev.larionov.bach.common.site.WeightedPair;

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
@Service("ipComparator")
public class IPAddressComparator implements IComparator {

    public static final int DEFAULT_THRESHOLD = 10;
    private static final Logger logger = Logger.getLogger(IPAddressComparator.class);
    private int threshold;

    public IPAddressComparator() {
        this(DEFAULT_THRESHOLD);
    }

    public IPAddressComparator(int threshold) {
        this.threshold = threshold;
    }

    @Override
    public List<WeightedPair> createPairs(Iterable<ISite> list) {
        Map<String, Set<ISite>> ipToHosts = resolveHosts(list);

        return getWeightedPairs(ipToHosts);
    }

    private Map<String, Set<ISite>> resolveHosts(Iterable<ISite> list) {
        Map<String, Set<ISite>> ipToHosts = new HashMap<>();

        for (ISite site : list) {
            String host = site.getHost();
            try {
                InetAddress inetAddress = InetAddress.getByName(host);
                String hostAddress = inetAddress.getHostAddress();
                logger.debug(String.format("Url %s resolved in %s", site.getHost(), hostAddress));

                addNewIpAddress(ipToHosts, site, hostAddress);
            } catch (UnknownHostException e) {
                logger.warn("Cannot resolve url " + host, e);
            }
        }

        return ipToHosts;
    }

    private List<WeightedPair> getWeightedPairs(Map<String, Set<ISite>> ipToHosts) {
        List<WeightedPair> weightedList = new ArrayList<>();

        for (Map.Entry<String, Set<ISite>> entry : ipToHosts.entrySet()) {
            Set<ISite> currentHosts = entry.getValue();

            if (currentHosts.size() < threshold && currentHosts.size() > 1) {
                ISite[] hostsArray = currentHosts.toArray(new ISite[0]);
                int length = hostsArray.length;

                for (int i = 0; i < length - 1; i++) {
                    for (int j = i + 1; j < length; j++) {
                        ISite firstHost = hostsArray[i];
                        ISite secondHost = hostsArray[j];

                        if (!firstHost.equals(secondHost)) {
                            WeightedPair newPair = new WeightedPair(firstHost, secondHost, 1.0 / length);
                            weightedList.add(newPair);
                        }
                    }
                }
            }
        }

        return weightedList;
    }

    private void addNewIpAddress(Map<String, Set<ISite>> ipToHosts, ISite site, String hostAddress) {
        if (!ipToHosts.containsKey(hostAddress)) {
            ipToHosts.put(hostAddress, new HashSet<ISite>());
        }

        Set<ISite> resolvedHosts = ipToHosts.get(hostAddress);
        resolvedHosts.add(site);
    }
}

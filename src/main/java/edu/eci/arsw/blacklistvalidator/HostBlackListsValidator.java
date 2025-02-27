/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blacklistvalidator;

import edu.eci.arsw.spamkeywordsdatasource.HostBlacklistsDataSourceFacade;

import javax.swing.*;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author hcadavid
 */
public class HostBlackListsValidator {

    private static final int BLACK_LIST_ALARM_COUNT = 5;

    public static int ocurrencesCount = 0;

    public static int checkedListsCount = 0;

    public static LinkedList<Integer> blackListOcurrences = new LinkedList<>();
    /**
     * Check the given host's IP address in all the available black lists,
     * and report it as NOT Trustworthy when such IP was reported in at least
     * BLACK_LIST_ALARM_COUNT lists, or as Trustworthy in any other case.
     * The search is not exhaustive: When the number of occurrences is equal to
     * BLACK_LIST_ALARM_COUNT, the search is finished, the host reported as
     * NOT Trustworthy, and the list of the five blacklists returned.
     *
     * @param ipaddress suspicious host's IP address.
     * @return Blacklists numbers where the given host's IP address was found.
     */
    public List<Integer> checkHost(String ipaddress, int N) {


        blackListOcurrences = new LinkedList<>();


        LinkedList<BlackListCheck> balcklists = new LinkedList<>();


        int cantidadListasArevisarPorHilo;
        HostBlacklistsDataSourceFacade skds = HostBlacklistsDataSourceFacade.getInstance();

        cantidadListasArevisarPorHilo = skds.getRegisteredServersCount() / N;

        for (int i = 0; i <= N; i++) {
            if (i == N &&!(N / 2 == 0) ) {
                int resta =  skds.getRegisteredServersCount() - cantidadListasArevisarPorHilo*N ;
                BlackListCheck black =  new BlackListCheck(ipaddress, (cantidadListasArevisarPorHilo * i) + resta);
                balcklists.add(black);
                black.start();
            } else {
                BlackListCheck black = (new BlackListCheck(ipaddress, cantidadListasArevisarPorHilo * i));
                balcklists.add(black);
                black.start();
            }
        }


        while (ocurrencesCount < BLACK_LIST_ALARM_COUNT){
            System.out.println();
        }



//        for (int i = 0; i < skds.getRegisteredServersCount() && ocurrencesCount < BLACK_LIST_ALARM_COUNT; i++) {
//            checkedListsCount++;
//
//
//            if (skds.isInBlackListServer(i, ipaddress)) {
//
//                System.out.println("i" + i);
//
//                blackListOcurrences.add(i);
//
//                ocurrencesCount++;
//            }
//        }

        if (ocurrencesCount >= BLACK_LIST_ALARM_COUNT) {
            skds.reportAsNotTrustworthy(ipaddress);
        } else {
            skds.reportAsTrustworthy(ipaddress);
        }

        LOG.log(Level.INFO, "Checked Black Lists:{0} of {1}", new Object[]{checkedListsCount, skds.getRegisteredServersCount()});

        return blackListOcurrences;
    }


    private static final Logger LOG = Logger.getLogger(HostBlackListsValidator.class.getName());


}

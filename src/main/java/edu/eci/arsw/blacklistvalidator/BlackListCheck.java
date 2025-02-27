package edu.eci.arsw.blacklistvalidator;

import java.util.LinkedList;

import edu.eci.arsw.spamkeywordsdatasource.HostBlacklistsDataSourceFacade;

import static edu.eci.arsw.blacklistvalidator.HostBlackListsValidator.ocurrencesCount;
import static edu.eci.arsw.blacklistvalidator.HostBlackListsValidator.blackListOcurrences;
import static edu.eci.arsw.blacklistvalidator.HostBlackListsValidator.checkedListsCount;

public class BlackListCheck extends Thread {

    private static final int BLACK_LIST_ALARM_COUNT = 5;


    private String ipaddress;
    private int start;

    private boolean isConfiable = true;


    public BlackListCheck(String ipaddress, int start) {

        this.ipaddress = ipaddress;

        this.start = start;

    }

    public boolean getisConfiable() {
        return isConfiable;
    }


    @Override
    public void run() {
        HostBlacklistsDataSourceFacade skds = HostBlacklistsDataSourceFacade.getInstance();


        for (int i = start; i < skds.getRegisteredServersCount() && ocurrencesCount < BLACK_LIST_ALARM_COUNT; i++) {
            checkedListsCount++;
//            System.out.println("holamundo estoy en un hilo ");


            if (skds.isInBlackListServer(i, ipaddress)) {

                blackListOcurrences.add(i);

                ocurrencesCount++;
            }

            if (ocurrencesCount < 5) {
                isConfiable = false;
            }
        }
//        if (ocurrencesCount >= BLACK_LIST_ALARM_COUNT) {
//            isConfiable = false;
//        } else {
//            isConfiable = true;
//        }
    }


}



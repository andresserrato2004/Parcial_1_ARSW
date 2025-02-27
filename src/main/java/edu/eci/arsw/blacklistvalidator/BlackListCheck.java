package edu.eci.arsw.blacklistvalidator;

import java.util.LinkedList;
import edu.eci.arsw.spamkeywordsdatasource.HostBlacklistsDataSourceFacade;

public class BlackListCheck extends Thread {

    private static final int BLACK_LIST_ALARM_COUNT=5;

    LinkedList<Integer> blackListOcurrences=new LinkedList<>();

    private String ipaddress;
    private int start;
    public BlackListCheck (String ipaddress , int start) {

        this.ipaddress = ipaddress;

        this.start = start;

    }



 @Override
    public void run() {
        HostBlacklistsDataSourceFacade skds = HostBlacklistsDataSourceFacade.getInstance();

        int ocurrencesCount = 0;

        for (int i=start;i<skds.getRegisteredServersCount() && ocurrencesCount<BLACK_LIST_ALARM_COUNT;i++) {

//            System.out.println("holamundo estoy en un hilo ");

                if (skds.isInBlackListServer(i, ipaddress)) {

                    System.out.println("i" + i);

                    blackListOcurrences.add(i);

                    ocurrencesCount++;
                }
            }
        }


    }



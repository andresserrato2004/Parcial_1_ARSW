/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blacklistvalidator;

import java.util.List;

/**
 *
 * @author hcadavid
 */
public class Main {
    
    public static void main(String a[]){
        HostBlackListsValidator hblv=new HostBlackListsValidator();
        int N = 10; // esta es la cantidad de hiloscon los que se va a realizar la busqueda
        List<Integer> blackListOcurrences=hblv.checkHost("202.24.34.55",N);
        System.out.println("The host was found in the following blacklists:"+blackListOcurrences);
        
    }
    
}

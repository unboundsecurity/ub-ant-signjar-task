package com.unboundtech;

import org.apache.tools.ant.Project;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.prefs.BackingStoreException;


public class Main {

    /**
     *
     * @param args
     * @throws GeneralSecurityException
     * @throws IOException
     */
    public static void main(String[] args) throws GeneralSecurityException, IOException, BackingStoreException {
        UbSignJar dySignJar = new UbSignJar();

        dySignJar.setJar(new File("test.jar"));
        dySignJar.setSignedjar(new File("test.jar"));
        dySignJar.setAlias("test");
        dySignJar.setProject(new Project());
        dySignJar.setPartition("p1");
        dySignJar.execute();

    }



}

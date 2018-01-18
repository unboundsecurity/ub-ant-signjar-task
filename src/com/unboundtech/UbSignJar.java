package com.unboundtech;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.taskdefs.ExecTask;
import org.apache.tools.ant.taskdefs.SignJar;

import java.io.File;
import java.nio.file.Paths;

public class UbSignJar extends SignJar {

    private static final String PROVIDER_JAR_NAME = "ekm-java-provider-2.0.jar";
    private static final String WIN_PROVIDER_FOLDER = "lib";
    private static final String WIN_DEFAULT_INSTALLATION_DIR = "C:\\Program Files\\DyadicSec";

    private static final String UNIX_INSTALLATION_DIR  = "/";
    private static final String UNIX_PROVIDER_FOLDER = "usr/lib";
    private static final String UNIX_64_PROVIDER_FOLDER = "usr/lib64";

    private String partition;
    private String providerPath;
    private String installationDir;

    /**
     * @throws BuildException
     */
    @Override
    public void execute() throws BuildException {

        setStorepass();
        setKeypass();
        setStoretype();

        super.execute();
    }

    /**
     * @return
     */
    @Override
    protected ExecTask createJarSigner() {

        ExecTask cmd = super.createJarSigner();

        setClasspath(cmd);
        setProviderName(cmd);
        setProviderClass(cmd);
        setPartition(cmd);

        return cmd;
    }

    /**
     *
     * @param installationDir
     */
    public void setInstallationDir(String installationDir) {
        this.installationDir = installationDir;
    }

    /**
     * @param providerPath
     */
    public void setProviderPath(String providerPath) {
        this.providerPath = providerPath;
    }

    /**
     * @param partition
     */
    public void setPartition(String partition) {
        this.partition = partition;
    }

    /**
     *
     */
    private void setStorepass() {
        if (storepass == null) {
            setStorepass("null");
        }
    }

    /**
     *
     */
    private void setKeypass() {
        if (keypass == null) {
            setKeypass("null");
        }
    }

    /**
     *
     */
    private void setStoretype() {
        setStoretype("PKCS11");
    }

    /**
     *
     */
    private void resolveProviderPathOnWindows() {
        if (installationDir == null) {
            installationDir = WIN_DEFAULT_INSTALLATION_DIR;
        }
        providerPath = Paths.get(installationDir).resolve(WIN_PROVIDER_FOLDER).toAbsolutePath().toString();
    }

    /**
     *
     */
    private void resolveProviderPathOnUnix() {
        if (installationDir == null) {
            installationDir = UNIX_INSTALLATION_DIR;
        }

        providerPath = Paths.get(installationDir).resolve(UNIX_PROVIDER_FOLDER).toAbsolutePath().toString();
        if(!new File(providerPath).exists())
            providerPath = Paths.get(installationDir).resolve(UNIX_64_PROVIDER_FOLDER).toAbsolutePath().toString();

    }

    /**
     * @param cmd
     */
    private void setClasspath(ExecTask cmd) {

        if (providerPath == null) {
            if (isWindows()) resolveProviderPathOnWindows();
            else resolveProviderPathOnUnix();
        }

        String providerFullPath = Paths.get(providerPath).resolve(PROVIDER_JAR_NAME).toAbsolutePath().toString();

        if(!new File(providerFullPath).exists())
            throw new BuildException(String.format("Provider jar file (%s) does not exist",providerFullPath));

        log(String.format("Using provider JAR: %s",providerFullPath));

        String classpath = System.getProperty("java.class.path");

        this.addValue(cmd, "-J-cp");
        this.addValue(cmd, String.format("-J%s;%s", classpath, providerFullPath));
    }

    /**
     * @param cmd
     */
    private void setProviderName(ExecTask cmd) {
        this.addValue(cmd, "-providerName");
        this.addValue(cmd, "DYADIC");
    }

    /**
     * @param cmd
     */
    private void setProviderClass(ExecTask cmd) {
        this.addValue(cmd, "-providerClass");
        this.addValue(cmd, "com.dyadicsec.provider.DYCryptoProvider");
    }

    /**
     * @param cmd
     */
    private void setPartition(ExecTask cmd) {
        if (partition != null) {
            this.addValue(cmd, "-providerArg");
            this.addValue(cmd, partition);
            log(String.format("Using partition: %s", partition));
        } else {
            log(String.format("Using default partition"));
        }
    }

    /**
     * @return
     */
    public static boolean isWindows() {
        return System.getProperty("os.name").startsWith("Windows");
    }
}

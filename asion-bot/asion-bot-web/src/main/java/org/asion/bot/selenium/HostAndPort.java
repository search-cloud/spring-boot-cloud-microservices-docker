package org.asion.bot.selenium;

/**
 * @author Asion.
 * @since 2018/5/17.
 */
public class HostAndPort {
    private String ip;
    private String port;
    private String local;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    @Override
    public String toString() {
        return "HostAndPort{" +
                "ip='" + ip + '\'' +
                ", port='" + port + '\'' +
//                ", local='" + local + '\'' +
                '}';
    }
}

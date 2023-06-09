package logic;

import java.net.InetAddress;

public class ClientModel {

    private InetAddress ip;
    private String host;
    private boolean isConnected;

    public ClientModel(String host, InetAddress ip, boolean isConnected) {
        this.host = host;
        this.ip = ip;
        this.isConnected = isConnected;
    }

    public String getHost() {
        return this.host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public InetAddress getIp() {
        return this.ip;
    }

    public void setIp(InetAddress ip) {
        this.ip = ip;
    }

    public boolean getIsConnected() {
        return this.isConnected;
    }

    public void setIsConnected(boolean isConnected) {
        this.isConnected = isConnected;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((ip == null) ? 0 : ip.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if ((obj == null) || (this.getClass() != obj.getClass()) )
            return false;
        if (this == obj)
            return true;
        ClientModel other = (ClientModel) obj;
        if (ip == null) {
            if (other.ip != null)
                return false;
        } else if (!ip.equals(other.ip))
            return false;
        return true;
    }
}

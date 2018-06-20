package Logic.Que;

import Logic.Messages.MessagePackage;

import java.io.Serializable;
import java.net.InetSocketAddress;

public class PackageBundle implements Serializable {

    private InetSocketAddress address;
    private MessagePackage msg;


    public PackageBundle(InetSocketAddress address, MessagePackage msg) {
        this.address = address;
        this.msg = msg;
    }

    public InetSocketAddress getAddress() {
        return address;
    }

    public void setAddress(InetSocketAddress address) {
        this.address = address;
    }

    public MessagePackage getMsg() {
        return msg;
    }

    public void setMsg(MessagePackage msg) {
        this.msg = msg;
    }
}

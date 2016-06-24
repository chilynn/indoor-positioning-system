package com.neu.wifilocalization.utils.xmpp;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

import com.neu.wifilocalization.application.Const;

/**
 * @author Sam.Io
 * @time 2011/11/18
 * @project AdXmpp
 */
public class XmppTool {

    private static XMPPConnection con = null;

    private static void openConnection() {
        try {
            ConnectionConfiguration connConfig = new ConnectionConfiguration(Const.OPEN_FIRE_SERVER_IP,
                    Const.OPEN_FIRE_SERVER_PORT);
            connConfig.setSendPresence(false);
            con = new XMPPConnection(connConfig);
            con.connect();
        } catch (XMPPException xe) {
            xe.printStackTrace();
        }
    }

    public static XMPPConnection getConnection() {
        if (con == null) {
            openConnection();
        }
        return con;
    }

    public static void closeConnection() {
        con.disconnect();
        con = null;
    }
}

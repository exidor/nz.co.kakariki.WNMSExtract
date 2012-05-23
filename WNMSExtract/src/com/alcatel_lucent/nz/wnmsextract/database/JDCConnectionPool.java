package com.alcatel_lucent.nz.wnmsextract.database;
/*
 * This file is part of wnmsextract.
 *
 * wnmsextract is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * wnmsextract is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Vector;

/**
 * ConnectionReaper. thread to intermittently reap dead connection
 * instances from the pool by calling pools own reapConnections()
 * @author jnramsay
 *
 */
class ConnectionReaper extends Thread {

    private JDCConnectionPool pool;
    private final long delay=300000;

    ConnectionReaper(JDCConnectionPool pool) {
        this.pool=pool;
    }

    @Override
	public void run() {
        while(true) {
           try {
              sleep(delay);
           } catch( InterruptedException e) { }
           pool.reapConnections();
        }
    }
}

/**
 * JDCConnection Pool. maintains/wraps a vector of connection instances
 * @author jnramsay
 *
 */
public class JDCConnectionPool {

   private Vector<JDCConnection> connections;
   private String url, user, pass;
   private ConnectionReaper reaper;
   final private long TIMEOUT = 21600000;//open for 6 hours, 1 hour isnt long enough to unzip a full DL set
   //3600000 = 1hr
   final private int POOL_SIZE = 10;

   public JDCConnectionPool(String url, String user, String pass) {
      this.url = url;
      this.user = user;
      this.pass = pass;
      connections = new Vector<JDCConnection>(POOL_SIZE);
      reaper = new ConnectionReaper(this);
      reaper.start();
   }

   public synchronized void reapConnections() {

      long stale = System.currentTimeMillis() - TIMEOUT;
      Enumeration<JDCConnection> connlist = connections.elements();

      while((connlist != null) && (connlist.hasMoreElements())) {
          JDCConnection conn = connlist.nextElement();

          if((conn.inUse())
        		  && (stale >conn.getLastUse())
        		  && (!conn.validate())) {
 	      removeConnection(conn);
         }
      }
   }

   public synchronized void closeConnections() {

      Enumeration<JDCConnection> connlist = connections.elements();

      while((connlist != null) && (connlist.hasMoreElements())) {
          JDCConnection conn = connlist.nextElement();
          removeConnection(conn);
      }
   }

   private synchronized void removeConnection(JDCConnection conn) {
       connections.removeElement(conn);
   }


   public synchronized Connection getConnection() throws SQLException {

       JDCConnection c;
       for(int i = 0; i < connections.size(); i++) {
           c = connections.elementAt(i);
           if (c.lease() && c!=null) {
              return c;
           }
       }

       Connection conn = DriverManager.getConnection(url, user, pass);
       c = new JDCConnection(conn, this);
       c.lease();
       connections.addElement(c);
       return c;
  }

   public synchronized void returnConnection(JDCConnection conn) {
      conn.expireLease();
   }
}

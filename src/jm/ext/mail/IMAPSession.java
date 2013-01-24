/**
 * 
 */
package jm.ext.mail;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Properties;

//import org.apache.commons.io.output.ByteArrayOutputStream;

import com.sun.mail.iap.Argument;
import com.sun.mail.iap.ProtocolException;
import com.sun.mail.imap.protocol.IMAPProtocol;

/**
 * @author schizophrenia
 *
 */
public class IMAPSession {
	private IMAPProtocol handler;
	private Properties prop;
	private ByteArrayOutputStream out = null;
	private PrintStream	ps = null;
	private String		name;
	private boolean		authenticated = false;
	private boolean		connected = false;
	
	public	int		osChunkSize = 8192;
	
	public synchronized byte[] connect(Properties p, String name, String host, int port, boolean debug, boolean inSSL)
				throws IOException, ProtocolException {
		out = new ByteArrayOutputStream(this.osChunkSize);
		ps = new PrintStream(out);
		out.reset();
		this.name = name;
		prop = p;
		this.handler = new IMAPProtocol(name, host, port, debug, ps, prop, inSSL);
		//this.handler.wr
		this.connected = true;
		return this.out.toByteArray();
	}
	
	public synchronized byte[] disconnect() throws IOException {
		out.reset();
		this.handler.disconnect();
		this.connected = false;
		this.out.close();
		return out.toByteArray();
	}
	
	public synchronized byte[] check() throws ProtocolException, IOException {
		out.reset();
		this.handler.check();
		return out.toByteArray();
	}
	
	public synchronized byte[] command(String command, Argument args) {
		out.reset();
		try {
		    this.handler.command(command, args);
		} catch (Exception e) {
		    
		}
		return out.toByteArray();
	}
	
	public synchronized byte[] login(String user, String password) {
		out.reset();
		
		try {
		    this.handler.login(user, password);
		    if(this.handler.isAuthenticated())	this.authenticated = true;
		} catch (Exception e) {
		
		}
		return out.toByteArray();
	}
	
	public synchronized byte[] logout() throws ProtocolException, IOException {
		out.reset();
		this.handler.logout();
		return out.toByteArray();
	}
	public synchronized byte[] listDelimeter() throws ProtocolException, IOException {
		out.reset();
		this.handler.list("", "");
		return out.toByteArray();
	}
	
	public synchronized byte[] list(String prefix, String mask) throws ProtocolException, IOException {
		out.reset();
		this.handler.list(prefix, mask);
		System.out.println(out.toString("windows-1251"));
		return out.toByteArray();
	}
	public synchronized byte[] select(String mbox) throws ProtocolException, IOException {
		out.reset();
		this.handler.select(mbox);
		return out.toByteArray();
	}
	
	public synchronized boolean isConnected() {
		return this.connected;
	}
	
	public synchronized boolean isAuthenticated() {
		return this.authenticated;
	}
	
	public synchronized String getName() {
		return this.name;
	}
}

/**
 * 
 */
package jm.ext.mail;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.jmeter.samplers.SampleResult;
import org.apache.jorphan.logging.LoggingManager;
import org.apache.log.Logger;

import com.sun.mail.iap.Argument;
import com.sun.mail.iap.ProtocolException;

/**
 * @author schizophrenia
 * TODO
 */
public class SessionManager {
	private final static Logger log = LoggingManager.getLoggerForClass();
	public final static SessionManager instance = new SessionManager();
	public Properties properties;
	
	private ConcurrentHashMap<String, IMAPSession> sessions;
	
	public SessionManager() {
		this.sessions = new ConcurrentHashMap<String, IMAPSession>();
	}
	
	public void setProperties(Properties p) {
		this.properties = p;
	}
	
	public SampleResult connect(String name, boolean debug, boolean inSSL) {
		SampleResult res = new SampleResult();
		IMAPSession session = new IMAPSession();
		try {
			res.setDataType(SampleResult.TEXT);
			res.sampleStart();
			res.setResponseData(session.connect(properties,
									name,
									properties.getProperty("mail.imap.host"),
									Integer.parseInt(properties.getProperty("mail.imap.port")),
									debug,
									inSSL));
			
			this.sessions.put(name, session);
		} catch (NumberFormatException e) {
			log.error("connect " + name, e);
		} catch (IOException e) {
			log.error("connect " + name + " ioexception ", e);
		} catch (ProtocolException e) {
			log.error("connect " + name + " protocol exception ", e);
		}
		res.sampleEnd();
		if(!session.isConnected()) log.error("connect " + name  + "not connected");
		else	res.setSuccessful(true);
		return res;
	}
	public SampleResult disconnect(String name) {
		SampleResult res = new SampleResult();
		res.setDataType(SampleResult.TEXT);
		IMAPSession p = this.sessions.get(name);
		if(p==null) res.setResponseMessage("disconnect not found session " + name);
		else {
			synchronized(p) {
				try {
					res.sampleStart();
					res.setResponseData(p.disconnect());
					res.setSuccessful(true);
				} catch (IOException e) {
					log.error("disconnect " + name + " ioexception", e);
				}
			}
			res.sampleEnd();
		}
		this.sessions.remove(name);
		return res;
	}

	// WARNING Use this with syncs!
	public synchronized IMAPSession getSession(String name) {
		return this.sessions.get(name);
	}

	public SampleResult login(String name, String user, String password) {
		SampleResult res = new SampleResult();
		res.setDataType(SampleResult.TEXT);
		res.setSuccessful(false);
		IMAPSession p = this.sessions.get(name);
		if(p == null)	res.setResponseMessage("Not found session " + name);
		else {
			synchronized(p) {
				res.sampleStart();
				res.setDataType(SampleResult.TEXT);
                res.setResponseData(p.login(user, password));
                res.setSuccessful(true);
			}
			res.sampleEnd();
		}
		return res;
	}
	
	public SampleResult logout(String name) {
		SampleResult res = new SampleResult();
		res.setDataType(SampleResult.TEXT);
		IMAPSession p = this.sessions.get(name);
		if(p == null)	res.setResponseMessage("not found session " + name);
		else {
			synchronized(p) {
				res.sampleStart();
				try {
						res.setResponseData(p.logout());
						res.setSuccessful(true);
				} catch (ProtocolException e) {
					log.error(name + "logout protocol error" , e);
				} catch (IOException e) {
					log.error(name +  "logout ioexception" , e);
				}
			}
			res.sampleEnd();
		}
		return res;
	}
	
	public SampleResult listDelimeter(String name) {
		SampleResult res = new SampleResult();
		res.setDataType(SampleResult.TEXT);
		res.setSuccessful(false);
		
		IMAPSession p = this.sessions.get(name);
		if(p == null)	res.setResponseMessage("not found session " + name); 
		else {
			synchronized(p) {
				try {
					res.sampleStart();
					res.setResponseData(p.listDelimeter());
					res.setSuccessful(true);
				} catch (ProtocolException e) {
					log.error(name + " protocol error", e);
				} catch (IOException e) {
					log.error(name + " ioexception", e);
				}
			}
			res.sampleEnd();
		}
		return res;
	}
	
	public SampleResult list(String name, String prefix, String mask) {
		SampleResult res = new SampleResult();
		res.setDataType(SampleResult.TEXT);
		res.setSuccessful(false);
		
		IMAPSession p = this.sessions.get(name);
		if(p == null)	res.setResponseMessage("not found session " + name);
		else {
			synchronized(p) {
				try {
					res.sampleStart();
					res.setResponseData(p.list(prefix, mask));
					res.setSuccessful(true);
				} catch (ProtocolException e) {
					log.error(name + " protocol error", e);
				} catch (IOException e) {
					log.error(name + " ioexception", e);
				}
			}
			res.sampleEnd();
		}
		return res;
	}
	public SampleResult select(String name, String mbox) {
		SampleResult res = new SampleResult();
		res.setDataType(SampleResult.TEXT);
		res.setSuccessful(false);
		IMAPSession p = this.sessions.get(name);
		if(p==null)	res.setResponseMessage("not found session " + name);
		else {
			synchronized(p) {
				res.sampleStart();
				try {
					res.setResponseData(this.sessions.get(name).select(mbox));
					res.setSuccessful(true);
				} catch (ProtocolException e) {
					log.error(name + " ioexception", e);
				} catch (IOException e) {
					log.error(name + " protocol error", e);
				}
			}
			res.sampleEnd();
		}
		return res;
	}
		
	public SampleResult command(String name, String command, Argument args) {
		SampleResult res = new SampleResult();
		res.setDataType(SampleResult.TEXT);
		
		IMAPSession p=this.sessions.get(name);
		if(p==null)	res.setResponseMessage(name + " session not found");
		else {
			synchronized(p) {
				res.sampleStart();
				res.setResponseData(p.command(command, args));
				res.setSuccessful(true);
			}
			res.sampleEnd();
		}
		
		return res;
	}
}
package jm.ext.mail.sampler;

//import java.nio.charset.Charset;

import jm.ext.mail.IMAPSession;
import jm.ext.mail.SessionManager;

import org.apache.jmeter.samplers.AbstractSampler;
import org.apache.jmeter.samplers.Entry;
import org.apache.jmeter.samplers.Interruptible;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.samplers.Sampler;
import org.apache.jorphan.logging.LoggingManager;
import org.apache.log.Logger;

public class IMAPCommandSampler extends AbstractSampler implements Sampler, Interruptible{

	/**
	 * 
	 */
	private static final Logger log = LoggingManager.getLoggerForClass();
	private static final long serialVersionUID = -9023582553867426543L;
	
	public static final String _sessionName = "SESSION_NAME";
	public static final String _command = "COMMAND";
	public static final String _label = "LABEL";
	
	
	public String getLabel(){
		return getPropertyAsString(_label);
	}
	public void setLabel(String label) {
		setProperty(_label, label);
	}
	public String getSessionName(){
		return getPropertyAsString(_sessionName);
	}
	
	public void setSessionName(String session) {
		setProperty(_sessionName, session);
	}
	
	public String getCommand(){
		return getPropertyAsString(_command);
	}
	
	public void setCommand(String command){
		setProperty(_command, command);
	}
	
	@Override
	public boolean interrupt() {
		Thread.currentThread().interrupt();
		return Thread.currentThread().isInterrupted();
	}

	@Override
	public SampleResult sample(Entry arg0) {
		SampleResult res = new SampleResult();
		res.setSampleLabel(getLabel());
		//res.setDataEncoding("windows-1251");
		res.setDataType(SampleResult.TEXT);
		
		IMAPSession p = SessionManager.instance.getSession(getSessionName());
		if(p == null) {
			log.error(getSessionName() + " session not found");
			res.setResponseMessage(getSessionName() + " session not found");
			return res;
		}
		synchronized(p) {
			log.debug(getSessionName() + " started");
			res.sampleStart();
			res.setResponseData(p.command(getCommand(), null));
			res.sampleEnd();
			res.setSuccessful(true);
		}
		return res;
	}

}

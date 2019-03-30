package co.edu.udistrital.common.util.sftp;

public class FTPProperties {

	/**
	 * Indicates the host that is trying to access through FTP platform. It should normally contain
	 * a structure similar to ftp.zyos.co
	 */
	private String hostName;

	/** Indicates the username for FTP server access */
	private String user;

	/** Indicates the password for FTP server access */
	private String password;

	/**
	 * Specifies the listening port of the FTP server. It is usually port 21, but another may be
	 * specified at the time of server configuration
	 */
	private String port;

	/**
	 * Specifies if you want to keep the connection open in order to obtain resources in a more
	 * agile way. The inconvenente to have the connection always open is the processing and
	 * maintenance of the open connection
	 */
	private boolean keepConnected;

	public FTPProperties() {
		// Empty Constructor
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @author mtorres 17/10/2018
	 * @param hostName @see {@link #hostName}
	 * @param user @see {@link #user}
	 * @param password @see {@link #password}
	 * @param port @see port @see {@link #port}
	 * @param keepConnected @see {@link #keepConnected}
	 */
	public FTPProperties(String hostName, String user, String password, String port, boolean keepConnected) {
		this.hostName = hostName;
		this.user = user;
		this.password = password;
		this.port = port;
		this.keepConnected = keepConnected;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public boolean isKeepConnected() {
		return keepConnected;
	}

	public void setKeepConnected(boolean keepConnected) {
		this.keepConnected = keepConnected;
	}



}

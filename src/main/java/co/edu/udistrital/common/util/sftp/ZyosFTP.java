package co.edu.udistrital.common.util.sftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.springframework.util.StringUtils;

import co.edu.udistrital.common.constant.IZyosConstantParameter;
import co.edu.udistrital.common.util.ZyosCDNFTP;
import it.sauronsoftware.ftp4j.FTPClient;
import it.sauronsoftware.ftp4j.FTPDataTransferListener;
import it.sauronsoftware.ftp4j.FTPException;
import it.sauronsoftware.ftp4j.FTPFile;
import it.sauronsoftware.ftp4j.FTPIllegalReplyException;

public class ZyosFTP {

	public static final String LOCAL_PATH;

	static {
		URL url = ZyosFTP.class.getClassLoader().getResource("../../");
		if (url != null)
			LOCAL_PATH = url.getPath().concat("/temporal") + File.separator;
		else
			LOCAL_PATH = IZyosConstantParameter.EMPTY_STRING;
	}

	/**
	 * Indicates the host that is trying to access through FTP platform. It should normally contain
	 * a structure similar to ftp.zyos.co
	 */
	private String hostName;

	/** Indicates the username for FTP server access */
	private String userId;

	/** Indicates the password for FTP server access */
	private String password;

	/**
	 * Specifies the listening port of the FTP server. It is usually port 21, but another may be
	 * specified at the time of server configuration
	 */
	private Integer port;

	/**
	 * Own object of the API that allows to contain the information of the connection of the FTP
	 * server to carry out the actions corresponding to connection, upload and download of files
	 */
	private FTPClient client;

	/**
	 * <p>
	 * Constructor that allows to initialize the connection to the FTP server. Take the data sent in
	 * the FTPProperties object and initialize the connection data
	 * </p>
	 * 
	 * @author mtorres 17/10/2018
	 * @param props Object that contains the connection data to the FTP server
	 */
	public ZyosFTP(FTPProperties props) {
		userId = props.getUser();
		password = props.getPassword();
		hostName = props.getHostName();
		port = Integer.parseInt(props.getPort());
	}

	/**
	 * <p>
	 * Change some characters that normally come in an url by understandable characters for the FTP
	 * server
	 * </p>
	 * 
	 * @author mtorres 17/10/2018
	 * @param url url specified by the user and intended to be modified to an understandable irl for
	 *        the FTP server
	 * @return Text string with character modification
	 */
	private String validateUri(String uri) {
		return uri.replace("\\", IZyosConstantParameter.SLASH).replace("//", IZyosConstantParameter.SLASH).replace("//",
			IZyosConstantParameter.SLASH);
	}

	/**
	 * <p>
	 * Establishes the connection to the FTP server prior to any transactional file operation
	 * </p>
	 * 
	 * @author mtorres 17/10/2018
	 */
	public void openConnection() {
		try {
			if (client == null) {
				client = new FTPClient();
				client.setPassive(true);
				client.setType(FTPClient.TYPE_BINARY);
			}
			if (!client.isConnected()) {
				client.connect(hostName, port);
			}
			if (!client.isAuthenticated()) {
				client.login(userId, password);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * <p>
	 * Closes the FTP server connection after downloading or uploading files.
	 * </p>
	 * 
	 * @author mtorres 17/10/2018
	 */
	public void closeConnection() {
		try {
			if (client == null) {
				return;
			}
			if (client.isConnected()) {
				client.disconnect(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * <p>
	 * Establishes the existence of a specific directory on the server. If the directory exists, it
	 * is located in said directory and in case the directory does not exist it creates the
	 * directory and it is located in the newly created directory.
	 * </p>
	 * 
	 * @author mtorres 17/10/2018
	 * @param fullPath Indicates the complete path of the directory. This route is divided into as
	 *        many parts as there are folders to validate the existence of the directory
	 */
	public void makeDirs(String fullPath) {
		String[] dirs = validateUri(fullPath).split(IZyosConstantParameter.SLASH);
		try {
			client.changeDirectory(IZyosConstantParameter.SLASH);
			for (String dir : dirs) {
				if (!dir.isEmpty()) {
					try {
						client.changeDirectory(dir);
					} catch (IllegalStateException | IOException | FTPIllegalReplyException | FTPException e) {
						client.createDirectory(dir);
						client.changeDirectory(dir);
					}
				}
			}
		} catch (IllegalStateException | IOException | FTPIllegalReplyException | FTPException e) {
			e.printStackTrace();
		}
	}


	/**
	 * <p>
	 * Upload a file to the CDN server
	 * </p>
	 * 
	 * @author mtorres 17/10/2018
	 * @param inputStream Specifies the input stream of bytes corresponding to the file that is
	 *        intended to upload to the server
	 * @param remoteDirectory Specifies the remote directory where the file is required to upload
	 * @param fileName Specifies the name to be assigned to the file that you want to upload
	 */
	public void uploadFile(InputStream inputStream, String remoteDirectory, String fileName) {
		try {
			makeDirs(validateUri(remoteDirectory));
			client.upload(fileName, inputStream, 0, 0, new ZyosFTPListener());
			inputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * <p>
	 * Upload a file to the CDN server
	 * </p>
	 * 
	 * @author mtorres 17/10/2018
	 * @param file Indicates the File object that contains the information of the file that is
	 *        intended to be uploaded to the FTP server
	 * @param remoteDirectory Specifies the remote directory where the file is required to upload
	 * @param fileName Specifies the name to be assigned to the file that you want to upload
	 */
	public void uploadFile(File file, String remoteDirectory, String fileName) throws FileNotFoundException {
		if (!file.isFile())
			throw new IllegalArgumentException("file could not be a directory");
		uploadFile(new FileInputStream(file), remoteDirectory, fileName);
	}

	/**
	 * <p>
	 * Upload a file to the CDN server
	 * </p>
	 * 
	 * @author mtorres 17/10/2018
	 * @param file Indicates the File object that contains the information of the file that is
	 *        intended to be uploaded to the FTP server
	 * @param remoteDirectory Specifies the remote directory where the file is required to upload
	 */
	public void uploadFile(File file, String remoteDirectory) throws FileNotFoundException {
		try {
			uploadFile(file, remoteDirectory, file.getName());
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * <p>
	 * Delete a file from the FTP server
	 * </p>
	 * 
	 * @author mtorres 17/10/2018
	 * @param filePath Specifies the remote directory where the file is required to upload
	 * @param fileName Specifies the name to be assigned to the file that you want to delete
	 */
	public void deleteFile(String filePath, String fileName) {
		try {
			if (existsFile(filePath, fileName)) {
				client.deleteFile(validateUri(filePath + IZyosConstantParameter.SLASH + fileName));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * <p>
	 * Validate the existence of a directory
	 * </p>
	 * 
	 * @author mtorres 17/10/2018
	 * @param dir Text string with the complete path of the directory that you want to validate
	 * @return true if the directory exists, false otherwise
	 */
	public boolean existsDir(String dir) {
		try {
			client.changeDirectory(validateUri(dir));
			return true;
		} catch (Exception e) {
			System.out.println("dir not found: " + e.getMessage());
		}
		return false;
	}

	/**
	 * <p>
	 * Validate the existence of a file
	 * </p>
	 * 
	 * @author mtorres 17/10/2018
	 * @param filePath Text string with the complete path of the directory that you want to validate
	 * @param fileName Specifies the name to be assigned to the file that you want to validate
	 * @return true if the file exists, false otherwise
	 */
	public boolean existsFile(String filePath, String fileName) {
		try {
			if (existsDir(filePath)) {
				FTPFile[] ftpFiles = client.list();
				for (FTPFile ftpFile : ftpFiles) {
					if (fileName.equals(ftpFile.getName()))
						return true;
				}
			}

		} catch (Exception e) {
			System.out.println("file not found: " + e.getMessage());
		}
		return false;
	}

	public File downloadFile(String remotePath) throws Exception {
		try {
			if (StringUtils.isEmpty(remotePath))
				return null;
			File localDir = new File(ZyosCDNFTP.LOCAL_PATH);
			if (!localDir.exists())
				localDir.mkdirs();
			String fileName = remotePath.substring(remotePath.lastIndexOf('/') + 1, remotePath.length());
			File localFile = new File(ZyosCDNFTP.LOCAL_PATH + fileName);
			client.download(remotePath, localFile);
			if (!localFile.exists())
				System.out.println(
					"WARN- An error occurred in the ZyosFTP downloadFile method when trying to drop a file. Validate the relative route or the destination route");
			return localFile;
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * <p>
	 * Class that listens for transaction events, opening and closing FTP server. Currently only
	 * informs about each of the events that occur, but it is possible to implement specific
	 * behaviors for each event
	 * </p>
	 * 
	 * @author mtorres 17/10/2018
	 */
	class ZyosFTPListener implements FTPDataTransferListener {

		/**
		 * <p>
		 * Method that triggers at the time of initiating a transfer
		 * </p>
		 * 
		 * @author mtorres 17/10/2018
		 */
		public void started() {
			// Transfer started
			System.out.println("TRANSFER-STATUS: File transfer started...");
		}

		/**
		 * <p>
		 * Method that is triggered at the time the transfer process was completed
		 * </p>
		 * 
		 * @author mtorres 17/10/2018
		 */
		public void transferred(int length) {
			// Yet other length bytes has been transferred since the last time
			// this method was called
		}

		/**
		 * <p>
		 * Method that is triggered at the time the transfer process was completed
		 * </p>
		 * 
		 * @author mtorres 17/10/2018
		 */
		public void completed() {
			// Transfer completed
			System.out.println("TRANSFER-STATUS: File transfer completed...");
		}

		/**
		 * <p>
		 * Method that is triggered at the moment the transfer process is canceled or aborted
		 * </p>
		 * 
		 * @author mtorres 17/10/2018
		 */
		public void aborted() {
			// Transfer aborted
			System.out.println("TRANSFER-STATUS: File transfer aborted...");
		}

		/**
		 * <p>
		 * Method that triggers when the transfer process fails
		 * </p>
		 * 
		 * @author mtorres 17/10/2018
		 */

		public void failed() {
			// Transfer failed
			System.out.println("TRANSFER-STATUS: File transfer failed...");
		}
	}



}

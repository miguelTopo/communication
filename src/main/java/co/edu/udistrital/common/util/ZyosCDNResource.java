package co.edu.udistrital.common.util;

import java.io.InputStream;

import co.edu.udistrital.common.util.sftp.FTPProperties;

public class ZyosCDNResource {

	/** Used to store the remote route of the resource for mobile devices */


	private String remoteMobilePath;

	/** Used to store the remote route of the resource for web devices */


	private String remoteWebPath;

	/** Used to store the remote route of the resource */


	private String remotePath;

	/** Contains the specific path of the CDN file that is being referenced */


	private String filePath;

	/**
	 * Indicates the specific name of the file. Next to @see filePath they build the complete path
	 * of the file to be captured
	 */


	private String fileName;

	/**
	 * Text field where the file that is intended to be removed from the CDN server is specified
	 */


	private String fileNameToDelete;

	/**
	 * Remote route where the CDN resource is located and which should normally be impacted in the
	 * database
	 */


	private String databasePath;

	/**
	 * Indicates the name of the functionality that is referenced. Example, for news the value of
	 * this variable should be "notice"
	 */


	private String functionality;

	/**
	 * Indicates the idEnterprise to which the resource is associated. is part of the file structure
	 */


	private Long idEnterprise;

	/**
	 * Indicates the width that you want to modify for the mobile file, applies for images
	 */


	private int customMobileWidth = 0;

	/**
	 * It allows to customize the width of the image for web resources, when it is not sent the
	 * default value is 0
	 */


	private int customWebWidth = 0;

	/**
	 * It allows to customize the height of the image for web resources, when it is not sent the
	 * default value is 0
	 */


	private int customWebHeight = 0;

	/**
	 * It allows to customize the image output quality for mobile resources, when it is not sent the
	 * default value is 0
	 */


	private Double customMobileQuality = 0D;

	/**
	 * It allows to customize the image output quality for web resources, when it is not sent the
	 * default value is 0
	 */


	private Double customWebQuality = 0D;

	/**
	 * Contains the inputStream of bytes associated with the file that is intended to be uploaded to
	 * the CDN server
	 */


	private InputStream inputStream;

	/**
	 * <p>
	 * contains the properties for connection to an FTP or SFTP server
	 * </p>
	 */
	private FTPProperties ftpPropertie;
	/**
	 * <p>
	 * contains the file extension to upload
	 * </p>
	 */
	private String fileExtension;
	/**
	 * identifies whether the resource that is being uploaded to the CDN is an image or a document
	 **/
	private boolean isDocument;

	public String getRemoteMobilePath() {
		return remoteMobilePath;
	}

	public void setRemoteMobilePath(String remoteMobilePath) {
		this.remoteMobilePath = remoteMobilePath;
	}

	public String getRemoteWebPath() {
		return remoteWebPath;
	}

	public void setRemoteWebPath(String remoteWebPath) {
		this.remoteWebPath = remoteWebPath;
	}

	public String getRemotePath() {
		return remotePath;
	}

	public void setRemotePath(String remotePath) {
		this.remotePath = remotePath;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileNameToDelete() {
		return fileNameToDelete;
	}

	public void setFileNameToDelete(String fileNameToDelete) {
		this.fileNameToDelete = fileNameToDelete;
	}

	public String getDatabasePath() {
		return databasePath;
	}

	public void setDatabasePath(String databasePath) {
		this.databasePath = databasePath;
	}

	public String getFunctionality() {
		return functionality;
	}

	public void setFunctionality(String functionality) {
		this.functionality = functionality;
	}

	public Long getIdEnterprise() {
		return idEnterprise;
	}

	public void setIdEnterprise(Long idEnterprise) {
		this.idEnterprise = idEnterprise;
	}

	public int getCustomMobileWidth() {
		return customMobileWidth;
	}

	public void setCustomMobileWidth(int customMobileWidth) {
		this.customMobileWidth = customMobileWidth;
	}

	public int getCustomWebWidth() {
		return customWebWidth;
	}

	public void setCustomWebWidth(int customWebWidth) {
		this.customWebWidth = customWebWidth;
	}

	public int getCustomWebHeight() {
		return customWebHeight;
	}

	public void setCustomWebHeight(int customWebHeight) {
		this.customWebHeight = customWebHeight;
	}

	public Double getCustomMobileQuality() {
		return customMobileQuality;
	}

	public void setCustomMobileQuality(Double customMobileQuality) {
		this.customMobileQuality = customMobileQuality;
	}

	public Double getCustomWebQuality() {
		return customWebQuality;
	}

	public void setCustomWebQuality(Double customWebQuality) {
		this.customWebQuality = customWebQuality;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public FTPProperties getFtpPropertie() {
		return ftpPropertie;
	}

	public void setFtpPropertie(FTPProperties ftpPropertie) {
		this.ftpPropertie = ftpPropertie;
	}

	public String getFileExtension() {
		return fileExtension;
	}

	public void setFileExtension(String fileExtension) {
		this.fileExtension = fileExtension;
	}

	public boolean isDocument() {
		return isDocument;
	}

	public void setDocument(boolean isDocument) {
		this.isDocument = isDocument;
	}
	
	public static ZyosCDNResource getDefaultCDNResource() {
		ZyosCDNResource resource = new ZyosCDNResource();
		resource.setIdEnterprise(10L);
		resource.setFunctionality("communication");
		resource.setDocument(true);
		return resource;
	}



}

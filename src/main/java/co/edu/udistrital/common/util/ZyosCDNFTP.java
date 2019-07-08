package co.edu.udistrital.common.util;

import java.io.File;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import org.springframework.util.StringUtils;

import co.edu.udistrital.common.constant.IZyosConstantParameter;
import co.edu.udistrital.common.util.sftp.FTPProperties;
import co.edu.udistrital.common.util.sftp.ZyosFTP;

public class ZyosCDNFTP {

	/**
	 * Instantiates a new zyosCDN.
	 */
	private ZyosCDNFTP() {}

	/**
	 * Path and file separator
	 * 
	 * @author mtorres 4/09/2018
	 */
	public static final String SEPARATOR = IZyosConstantParameter.SLASH;

	private static final String CORE_ISO_COUNTRYCODE = "co";
	private static final String FTP_CDN_HOSTNAME = "ftp.zyos.co";
	private static final String FTP_CDN_USER = "desarrollo@miedificio.co";
	private static final String FTP_CDN_PASSWORD = "Zyos123.";
	private static final String FTP_CDN_PORT = "21";
	public static final String LOCAL_PATH = "upload-dir/";
	public static final String URI_HOST_MAIN = "https://miedificio.co/cdn/dev/resources/web/";

	/**
	 * Path to refer to the location of resources or resources by enterprise
	 * 
	 * @author mtorres 4/09/2018
	 */
	public static final String ROOT_RESOURCES = "/resources";

	/**
	 * Word that allows to discriminate in some methods, if the file corresponds even resource for
	 * web or mobile environment. In this case it is for a web resource
	 * 
	 * @author mtorres 4/09/2018
	 */
	public static final String WEB = "web";

	/**
	 * Word that allows to discriminate in some methods, if the file corresponds even resource for
	 * web or mobile environment. In this case it is for a mobile resource
	 * 
	 * @author mtorres 4/09/2018
	 */
	public static final String MOBILE = "mobile";

	/**
	 * This method allows to validate if the parameters to upload a file to the SFTP CDN comply with
	 * the specific characteristics for folder structure
	 * 
	 * @author mtorres 07/06/2018
	 * @param cdnResource It contains the basic information with which to build the route and attach
	 *        the resource on the SFTP CDN server
	 * @param useInputStream
	 * @return True if the upload parameters of the file are valid and also if the referenced file
	 *         exists and is accessible, false if there is any problem with any parameter or the
	 *         file does not exist
	 */
	private static boolean validateUploadFile(ZyosCDNResource cdnResource, boolean useInputStream) {
		try {
			if (cdnResource.getIdEnterprise() == null) {
				System.out.println("WARN: It is not possible to add the resource, since it does not have idEnterprise");
				return false;
			}
			if (StringUtils.isEmpty(cdnResource.getFunctionality())) {
				System.out.println("WARN: It is not possible to add the resource, since it does not have a module or functionality to associate");
				return false;
			}
			if (!useInputStream && StringUtils.isEmpty(cdnResource.getFilePath())) {
				System.out.println("WARN: It is not possible to add the resource, since there is no path to access the file to be copied");
				return false;
			}
			if (!useInputStream && StringUtils.isEmpty(cdnResource.getFileName())) {
				System.out.println("WARN: It is not possible to add the resource, since there is no specific name of the file to be accessed");
				return false;
			}
			if (useInputStream) {
				if (cdnResource.getInputStream() == null) {
					System.out.println("WARN: Could not find an inputstream to write to the SFTP server");
					return false;
				}
			} else if (!cdnResource.isDocument()) {
				File file = new File(cdnResource.getFilePath() + cdnResource.getFileName());
				if (!file.exists()) {
					System.out.println("WARN: File not found");
					return false;
				}
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * <p>
	 * Upload a file to the CDN server. Requires that a configuration or connection of the FTP
	 * server be sent to use, with an already established connection
	 * </p>
	 * 
	 * @throws Exception Process the errors generated in the process
	 * @param cdnResource resources with specific parameters to upload file. File path, file name
	 *        and additional options
	 *
	 * @author KBaena 16/11/2018
	 **/
	public static void uploadFileResource(ZyosCDNResource cdnResource) throws Exception {
		try {
			if (cdnResource == null)
				return;
			ZyosFTP ftp = new ZyosFTP(getCDNFTPProperties());
			ftp.openConnection();
			uploadFileResource(cdnResource, ftp);
			ftp.closeConnection();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * <p>
	 * Upload a file to the CDN server. Requires that a configuration or connection of the FTP
	 * server be sent to use, with an already established connection
	 * </p>
	 * 
	 * @author mtorres 4/09/2018
	 * @param cdnResource resources with specific parameters to upload file. File path, file name
	 *        and additional options
	 * @param ftp This object contains all the connection information and a connection established
	 *        with the FTP server to be used. In this method the connection is not closed to allow a
	 *        massive upload of files
	 */
	private static void uploadFileResource(ZyosCDNResource cdnResource, ZyosFTP ftp) throws Exception {
		try {
			if (!StringUtils.isEmpty(cdnResource.getFileNameToDelete()))
				removeResourceFromCDN(cdnResource, cdnResource.getFileNameToDelete(), ftp);
			copyResourceDocumentFromLocalToCDN(cdnResource, ftp);
			System.out.println("SUBIENDO EL ARCHIVO");
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * <p>
	 * Valid if the name of a file contains the specific format for CDN server. Remember that the
	 * format has the structure yyyy-mm-UUID.extension
	 * </p>
	 * 
	 * @author mtorres 4/09/2018
	 * @param fileName String with the name of the file you want to validate
	 * @return If the file name contains the CDN structure yyyy-mm-UUID.extension returns true,
	 *         otherwise false
	 */
	private static boolean fileContainCDNFormat(String fileName) {
		try {
			if (fileName == null || fileName.trim().isEmpty() || fileName.length() < 7)
				return false;
			fileName = fileName.substring(0, 7);
			if (!fileName.contains(IZyosConstantParameter.HYPHEN))
				return false;

			String year = fileName.substring(0, 4);
			String month = fileName.substring(5, 7);
			if (!ZyosFieldValidator.isInteger(year) || Integer.valueOf(year) < 1990)
				return false;

			return ZyosFieldValidator.isInteger(month) && Integer.valueOf(month) >= 1 && Integer.valueOf(month) <= 12;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * <p>
	 * Allows you to delete a list of resources from the CDN while maintaining a single connection
	 * for the operation
	 * </p>
	 * 
	 * @author mtorres 4/09/2018
	 * @param cdnResourceList List of CDN resources that you want to delete, to eliminate a
	 *        resource, it is enough to send the specific name of the file plus the data of the
	 *        module name and the company id for each resource that you want to delete
	 */
	public static void removeResourceListFromCDN(List<ZyosCDNResource> cdnResourceList) throws Exception {
		try {
			if (cdnResourceList == null || cdnResourceList.isEmpty())
				return;
			ZyosFTP ftp = new ZyosFTP(getCDNFTPProperties());
			ftp.openConnection();
			for (ZyosCDNResource dataResource : cdnResourceList)
				removeResourceFromCDN(dataResource, dataResource.getFileNameToDelete(), ftp);
			ftp.closeConnection();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * <p>
	 * Allows you to remove a resource from the CDN server
	 * </p>
	 * 
	 * @author mtorres 4/09/2018
	 * @param cdnResource CDN resource that you want to delete, to eliminate a resource, it is
	 *        enough to send the specific name of the file plus the data of the module name and the
	 *        company id for each resource that you want to delete
	 */
	public static void removeResourceFromCDN(ZyosCDNResource cdnResource) {
		try {
			removeResourceListFromCDN(Arrays.asList(cdnResource));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * <p>
	 * Take the FTP connection sent and delete a resource from the CDN server. This method does not
	 * close the server connection to allow files to be deleted in bulk
	 * </p>
	 * 
	 * @author mtorres 4/09/2018
	 * @param cdnResource CDN resource that you want to delete, to eliminate a resource, it is
	 *        enough to send the specific name of the file plus the data of the module name and the
	 *        company id for each resource that you want to delete
	 * @param fileName Name of the file that you want to delete from the CDN server.
	 * @param ftp This object contains all the connection information and a connection established
	 *        with the FTP server to be used. In this method the connection is not closed to allow a
	 *        massive upload of files
	 */
	private static void removeResourceFromCDN(ZyosCDNResource cdnResource, String fileName, ZyosFTP ftp) {
		try {
			// Get the remote URLs for web, mobile and current location of the
			// file that you want to
			// migrate or copy to the SFTP server
			if (StringUtils.isEmpty(fileName) || !fileContainCDNFormat(fileName))
				return;
			getCDNPathFromResourcePathWebAndMobile(cdnResource, fileName);
			ftp.deleteFile(cdnResource.getRemoteMobilePath(), fileName);
			ftp.deleteFile(cdnResource.getRemoteWebPath(), fileName);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}


	/**
	 * Allows you to add or upload a new file to SFTP destined to work as CDN of Zyos resources
	 * 
	 * @author mtorres 07/06/2018
	 * @param cdnResource It contains the basic information with which to build the route and attach
	 *        the resource on the SFTP CDN server
	 * @param ftp This object contains all the connection information and a connection established
	 *        with the FTP server to be used. In this method the connection is not closed to allow a
	 *        massive upload of files
	 */
	public static void copyResourceDocumentFromLocalToCDN(ZyosCDNResource cdnResource, ZyosFTP ftp) throws Exception {
		try {
			if (!validateUploadFile(cdnResource, true))
				return;
			// Get the remote URLs for web, mobile and current location of the
			// file that you want to
			// migrate or copy to the SFTP server
			getCDNPathWebAndMobile(cdnResource);
			// Adjust image for small resolutions and upload file to mobile
			// location
			copyDocumentWebFromLocalToCDN(cdnResource, ftp);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * <p>
	 * Build a file name for an upcoming CDN resource. The file structure is yyyy-mm-UUID.extension
	 * </p>
	 * 
	 * @author mtorres 4/09/2018
	 * @param extension File extension. It must come with the format .extension (Including point)
	 * @return Name of the file with the CDN structure concatenating the file extension
	 */
	public static String getFileName(String extension) {
		try {
			Calendar now = Calendar.getInstance();
			int year = now.get(Calendar.YEAR);
			int month = now.get(Calendar.MONTH) + 1;
			return year + IZyosConstantParameter.HYPHEN + (month < 10 ? IZyosConstantParameter.ZERO + month : month) + IZyosConstantParameter.HYPHEN
				+ UUID.randomUUID().toString().substring(0, 8) + extension;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}


	/**
	 * This method allows to modify the weight of an document, in order to convert it into a heavy
	 * image for web devices, additionally makes a copy of the original image with this modification
	 * in a separate location
	 * 
	 * @author mtorres 07/06/2018
	 * @param cdnResource This object contains all the information of the file that you want to be
	 *        copied, as well as: the SFTP route where you want to store (in this case for web
	 *        devices); as well as extra data such as width
	 * @param ftp This object contains the information and the connection to the SFTP server, with
	 *        this instance it is allowed to upload the file to the SFTP server
	 * @throws Exception the exception
	 */
	private static void copyDocumentWebFromLocalToCDN(ZyosCDNResource cdnResource, ZyosFTP ftp) throws Exception {
		try {
			ftp.uploadFile(cdnResource.getInputStream(), cdnResource.getRemoteWebPath(), cdnResource.getFileName());
			cdnResource.setDatabasePath(cdnResource.getRemoteWebPath().replace("\\", IZyosConstantParameter.SLASH).replace("/resources/web/",
				IZyosConstantParameter.EMPTY_STRING) + cdnResource.getFileName());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("FATAL: FILE UPLOAD TO EXTERNAL FTP ### ha ocurrido un error subiendo el archivo");
			throw e;
		}
	}

	/**
	 * Allows you to obtain the connection properties for the SFTP CDN server
	 * 
	 * @author mtorres 07/06/2018
	 * @return SFTPProperties object with host, user, password, port and connection type data for
	 *         the SFTP server
	 */
	private static FTPProperties getCDNFTPProperties() {
		try {
			return new FTPProperties(FTP_CDN_HOSTNAME, FTP_CDN_USER, FTP_CDN_PASSWORD, FTP_CDN_PORT, true);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * It allows to build and assign the variables remoteWebPath and remoteMobilePath, which are the
	 * Path that will be assigned to the file in the SFTP server
	 * 
	 * @author mtorres 07/06/2018
	 * @param cdnResource This object contains the information needed to build location routes for
	 *        web and mobile files in the SFTP
	 */
	public static void getCDNPathWebAndMobile(ZyosCDNResource cdnResource) {
		try {
			if (cdnResource != null) {
				cdnResource.setRemotePath(getCDNPath(CORE_ISO_COUNTRYCODE, cdnResource.getIdEnterprise(), cdnResource.getFunctionality()));
				cdnResource.setRemoteMobilePath(ROOT_RESOURCES + SEPARATOR + MOBILE + cdnResource.getRemotePath());
				cdnResource.setRemoteWebPath(ROOT_RESOURCES + SEPARATOR + WEB + cdnResource.getRemotePath());
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * <p>
	 * It allows to build the routes where the specific CDN resources are located by enterprise. The
	 * format for the routes must contain the structure:
	 * countryCode/idEnterprise/functionality/year/month/web/fileName.ext. This method assigns the
	 * corresponding value to RemoteWebPath and RemoteMobilePath
	 * </p>
	 * 
	 * @author mtorres 4/09/2018
	 * @param cdnResource Contains idEnterprise-specific information and module name to build the
	 *        specific route of the resource
	 * @param fileName Name to be assigned to the resource that you want to upload to the CDN server
	 */
	private static void getCDNPathFromResourcePathWebAndMobile(ZyosCDNResource cdnResource, String fileName) {
		try {
			if (cdnResource == null)
				return;
			if (!StringUtils.isEmpty(fileName)) {
				cdnResource.setRemoteWebPath(
					getCDNPathFromImage(fileName, CORE_ISO_COUNTRYCODE, cdnResource.getIdEnterprise(), cdnResource.getFunctionality(), true));
				cdnResource.setRemoteMobilePath(
					getCDNPathFromImage(fileName, CORE_ISO_COUNTRYCODE, cdnResource.getIdEnterprise(), cdnResource.getFunctionality(), false));
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * <p>
	 * Build the corresponding path for the CDN resources with the required structure
	 * idEnterprise/functionality/year/month/web/fileName.ext
	 * </p>
	 * 
	 * @author mtorres 4/09/2018
	 * @param fileName Name to be assigned to the resource that you want to upload to the CDN server
	 * @param countryCode 2-character ISO code that unequivocally represents the country. Example:
	 *        for colombia are the characters co
	 * @param idEnterprise Id of the enterprise to which the resource is associated
	 * @param functionality Name of the functionality to which the resource belongs @see IModuleName
	 * @param web Indicates whether the path to be built corresponds to a web or mobile path
	 * @return Complete path, web or mobile where the CDN resource is located
	 */
	private static String getCDNPathFromImage(String fileName, String countryCode, Long idEnterprise, String functionality, boolean web) {
		try {
			int startIndex = 0;
			int endIndex = fileName.indexOf('-');
			int year = Integer.parseInt(fileName.substring(startIndex, endIndex));
			startIndex = endIndex + 1;
			int month = Integer.parseInt(fileName.substring(startIndex, fileName.lastIndexOf('-')));
			return ROOT_RESOURCES + File.separator + (web ? WEB : MOBILE) + File.separator + countryCode + File.separator + idEnterprise.intValue()
				+ File.separator + functionality + File.separator + year + File.separator + month + File.separator;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * Allows you to build routes for web and mobile resources on the CDN SFTP server
	 *
	 * @param idEnterprise Corresponds to the Id of the Enterprise to which the resource is
	 *        associated
	 * @param functionality It is a string where the name of the functionality to which the resource
	 *        is associated is stored
	 * @return String with the path to be assigned in the SFTP server, corresponding to the
	 *         requested web or mobile
	 */
	private static String getCDNPath(String countryCode, Long idEnterprise, String functionality) {
		try {
			Calendar now = Calendar.getInstance();
			int year = now.get(Calendar.YEAR);
			int month = now.get(Calendar.MONTH) + 1;
			return SEPARATOR + countryCode + SEPARATOR + idEnterprise.intValue() + SEPARATOR + functionality + SEPARATOR + year + SEPARATOR + month
				+ SEPARATOR;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	public static File downloadResource(String remotePath) throws Exception {
		try {
			if (StringUtils.isEmpty(remotePath))
				return null;
			String fileName = remotePath.substring(remotePath.lastIndexOf('/') + 1, remotePath.length());
			File localFile = new File(ZyosCDNFTP.LOCAL_PATH + fileName);
			if (localFile.exists() && localFile.canRead())
				return localFile;
			ZyosFTP ftp = new ZyosFTP(getCDNFTPProperties());
			ftp.openConnection();
			return ftp.downloadFile(ROOT_RESOURCES + SEPARATOR + WEB + SEPARATOR + remotePath);
		} catch (Exception e) {
			throw e;
		}
	}
}

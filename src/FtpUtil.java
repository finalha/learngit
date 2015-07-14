import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.net.ftp.*;

public class FtpUtil {

	private FTPClient ftpClient = new FTPClient();

	/**
	 * 连接到FTP服务器
	 * 
	 * @param host
	 *            FTP地址
	 * @param port
	 *            端口号
	 * @param username
	 *            用户名
	 * @param password
	 *            密码
	 */
	public boolean ftpConnect(String host, int port, String username,
			String password) {
		try {
			ftpClient.connect(host, port);
			ftpClient.setControlEncoding("GBK");
			int reply = ftpClient.getReplyCode();
			if (FTPReply.isPositiveCompletion(reply)) {
				if (ftpClient.login(username, password)) {
					System.out.println("Successful login!");
					return true;
				} else {
					System.out.println("fail to login!");
				}
			}
		} catch (Exception e) {
			System.out.println("Failure connection!");
			e.printStackTrace();
		}
		return false;
	}
    //从本地上传单个文件，localName：本地文件路径包含文件名，ftpFile：所要放于ftp的文件夹，newName：重命名
	public void ftpUpload(String localName, String ftpFile, String newName) {
		File srcFile = new File(localName);
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(srcFile);
			//改变工作目录到所需要的路径下
			ftpClient.changeWorkingDirectory(ftpFile);  
			ftpClient.setBufferSize(1024);
			ftpClient.setControlEncoding("GBK");
			ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
			ftpClient.storeFile(newName, fis);
		} catch (Exception e) {
			System.out.println("Failed to upload!");
			e.printStackTrace();
		} finally {
			try {
				fis.close();
				ftpDisConnect();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
    //从本地上传一个文件夹，localPath：本地文件夹，ftpPath：上传的资源所要存放的文件夹
	public void ftpUpload2(String localPath, String ftpPath) {
		File uploadFile = new File(localPath);
		File[] fileList = uploadFile.listFiles();
		FileInputStream fis = null;
		if (fileList == null) {
			return;
		}
		for (int i = 0; i < fileList.length; i++) {
			try {
				fis = new FileInputStream(fileList[i]);
				String ftpFileName = fileList[i].getName();
				ftpClient.changeWorkingDirectory(ftpPath);
				ftpClient.setBufferSize(1024);
				ftpClient.setControlEncoding("GBK");
				ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
				ftpClient.storeFile(ftpFileName, fis);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		try {
			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		ftpDisConnect();

	}
    //从FTP下载单个文件
	public void ftpDownload(String ftpFile, String localName) {
//		File outfile = new File(localName + "/" + ftpFile);
		File outfile = new File(localName);
		OutputStream fos = null;
		try {
//			String remotePath = ftpFile.substring(1, ftpFile.lastIndexOf("/"));
//			ftpClient.changeWorkingDirectory(remotePath);
//			ftpClient.enterLocalPassiveMode();
			ftpClient.setControlEncoding("GB2312");
			
			fos = new FileOutputStream(outfile);
			ftpClient.setBufferSize(1024);
			ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
//			ftpClient.retrieveFile(ftpFile, fos);
			ftpClient.retrieveFile(new String(ftpFile.getBytes("GB2312"),"ISO-8859-1"), fos);
			System.out.println("download success");
		} catch (Exception e) {
			System.out.println("Failed to download!");
			e.printStackTrace();
		} finally {
			try {
				fos.close();
				ftpClient.disconnect();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ftpDisConnect();
		}
	}
    //从FTP下载一个文件夹
	public void ftpDownload2(String ftpPath, String localPath) {
		OutputStream fos = null;
		File localFile = null;
		try {
			ftpClient.changeWorkingDirectory(ftpPath);
			FTPFile[] fileList = ftpClient.listFiles();
			for (int i = 0; i < fileList.length; i++) {
				String localname = fileList[i].getName();
				localFile = new File(localPath + "/" + localname);
				fos = new FileOutputStream(localFile);
				ftpClient.setBufferSize(1024);
				ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		ftpDisConnect();

	}

	public void ftpDisConnect() {
		try {
			if (ftpClient.isConnected()) {
				ftpClient.disconnect();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    //测试
	public static void main(String[] args) {
		FtpUtil ftp = new FtpUtil();
//		ftp.ftpConnect("192.168.0.161", 21, "NSTC", "NSTC04");
		ftp.ftpConnect("192.168.0.15", 21, "liuyang2", "Hanyu130061");
		//上传单个文件
//	    ftp.ftpUpload("D://1.jpg", "/s", "6.jpg");
		//上传文件夹
//	    ftp.ftpUpload2("D://桌面", "/s");
		//下载单个文件
//	    ftp.ftpDownload("TB盘/N6/N62014R2.0CEO.rar", "F:/N62014R2.0CEO.rar");
		 ftp.ftpDownload("其他/公司VI应用模板/Foxmail-信纸格式(北京).rar", "F:/Foxmail-信纸格式(北京).rar");
		//下载文件夹
//	    ftp.ftpDownload2("/s","e://s");
	}
}


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
	 * ���ӵ�FTP������
	 * 
	 * @param host
	 *            FTP��ַ
	 * @param port
	 *            �˿ں�
	 * @param username
	 *            �û���
	 * @param password
	 *            ����
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
    //�ӱ����ϴ������ļ���localName�������ļ�·�������ļ�����ftpFile����Ҫ����ftp���ļ��У�newName��������
	public void ftpUpload(String localName, String ftpFile, String newName) {
		File srcFile = new File(localName);
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(srcFile);
			//�ı乤��Ŀ¼������Ҫ��·����
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
    //�ӱ����ϴ�һ���ļ��У�localPath�������ļ��У�ftpPath���ϴ�����Դ��Ҫ��ŵ��ļ���
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
    //��FTP���ص����ļ�
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
    //��FTP����һ���ļ���
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
    //����
	public static void main(String[] args) {
		FtpUtil ftp = new FtpUtil();
//		ftp.ftpConnect("192.168.0.161", 21, "NSTC", "NSTC04");
		ftp.ftpConnect("192.168.0.15", 21, "liuyang2", "Hanyu130061");
		//�ϴ������ļ�
//	    ftp.ftpUpload("D://1.jpg", "/s", "6.jpg");
		//�ϴ��ļ���
//	    ftp.ftpUpload2("D://����", "/s");
		//���ص����ļ�
//	    ftp.ftpDownload("TB��/N6/N62014R2.0CEO.rar", "F:/N62014R2.0CEO.rar");
		 ftp.ftpDownload("����/��˾VIӦ��ģ��/Foxmail-��ֽ��ʽ(����).rar", "F:/Foxmail-��ֽ��ʽ(����).rar");
		//�����ļ���
//	    ftp.ftpDownload2("/s","e://s");
	}
}


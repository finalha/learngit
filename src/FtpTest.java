import org.apache.commons.io.IOUtils; 
import org.apache.commons.net.ftp.FTPClient; 

import java.io.File; 
import java.io.FileInputStream; 
import java.io.IOException; 
import java.io.FileOutputStream; 

/** 
* Apache commons-net ����һ�ѣ�����FTP�ͻ��˹������ĺ��ò� 
* 
* @author : leizhimin��2008-8-20 14:00:38��<p> 
*/ 
public class FtpTest { 
    public static void main(String[] args) { 
//        testUpload(); 
        testDownload(); 
    } 

    /** 
     * FTP�ϴ������ļ����� 
     */ 
    public static void testUpload() { 
        FTPClient ftpClient = new FTPClient(); 
        FileInputStream fis = null; 

        try { 
            ftpClient.connect("192.168.14.117"); 
            ftpClient.login("admin", "123"); 

            File srcFile = new File("C:\\new.gif"); 
            fis = new FileInputStream(srcFile); 
            //�����ϴ�Ŀ¼ 
            ftpClient.changeWorkingDirectory("/admin/pic"); 
            ftpClient.setBufferSize(1024); 
            ftpClient.setControlEncoding("GBK"); 
            //�����ļ����ͣ������ƣ� 
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE); 
            ftpClient.storeFile("3.gif", fis); 
        } catch (IOException e) { 
            e.printStackTrace(); 
            throw new RuntimeException("FTP�ͻ��˳���", e); 
        } finally { 
            IOUtils.closeQuietly(fis); 
            try { 
                ftpClient.disconnect(); 
            } catch (IOException e) { 
                e.printStackTrace(); 
                throw new RuntimeException("�ر�FTP���ӷ����쳣��", e); 
            } 
        } 
    } 

    /** 
     * FTP���ص����ļ����� 
     */ 
    public static void testDownload() { 
        FTPClient ftpClient = new FTPClient(); 
        FileOutputStream fos = null; 

        try { 
            ftpClient.connect("192.168.0.161",21); 
            ftpClient.login("NSTC", "NSTC04"); 
            String remoteFileName = "TB��/N6/N62014R2.0CEO.rar";
//          ftpClient.connect("192.168.0.15",21); 
//          ftpClient.login("liuyang2", "Hanyu130061"); 
//          String remoteFileName = "/ͨ�����/-=�������=-/javacn2005.rar";
        	
            fos = new FileOutputStream("F:/download.rar"); 
            ftpClient.setControlEncoding("GB2312");
            ftpClient.setBufferSize(1024); 
            //�����ļ����ͣ������ƣ� 
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE); 
//            ftpClient.retrieveFile(remoteFileName, fos);
            ftpClient.retrieveFile(new String(remoteFileName.getBytes("GB2312"),"ISO-8859-1"), fos);
            System.out.println("download success");
        } catch (IOException e) { 
            e.printStackTrace(); 
            throw new RuntimeException("FTP�ͻ��˳���", e); 
        } finally { 
            IOUtils.closeQuietly(fos); 
            try { 
                ftpClient.disconnect(); 
            } catch (IOException e) { 
                e.printStackTrace(); 
                throw new RuntimeException("�ر�FTP���ӷ����쳣��", e); 
            } 
        } 
    } 
} 

import org.apache.commons.io.IOUtils; 
import org.apache.commons.net.ftp.FTPClient; 

import java.io.File; 
import java.io.FileInputStream; 
import java.io.IOException; 
import java.io.FileOutputStream; 

/** 
* Apache commons-net 试用一把，看看FTP客户端工具做的好用不 
* 
* @author : leizhimin，2008-8-20 14:00:38。<p> 
*/ 
public class FtpTest { 
    public static void main(String[] args) { 
//        testUpload(); 
        testDownload(); 
    } 

    /** 
     * FTP上传单个文件测试 
     */ 
    public static void testUpload() { 
        FTPClient ftpClient = new FTPClient(); 
        FileInputStream fis = null; 

        try { 
            ftpClient.connect("192.168.14.117"); 
            ftpClient.login("admin", "123"); 

            File srcFile = new File("C:\\new.gif"); 
            fis = new FileInputStream(srcFile); 
            //设置上传目录 
            ftpClient.changeWorkingDirectory("/admin/pic"); 
            ftpClient.setBufferSize(1024); 
            ftpClient.setControlEncoding("GBK"); 
            //设置文件类型（二进制） 
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE); 
            ftpClient.storeFile("3.gif", fis); 
        } catch (IOException e) { 
            e.printStackTrace(); 
            throw new RuntimeException("FTP客户端出错！", e); 
        } finally { 
            IOUtils.closeQuietly(fis); 
            try { 
                ftpClient.disconnect(); 
            } catch (IOException e) { 
                e.printStackTrace(); 
                throw new RuntimeException("关闭FTP连接发生异常！", e); 
            } 
        } 
    } 

    /** 
     * FTP下载单个文件测试 
     */ 
    public static void testDownload() { 
        FTPClient ftpClient = new FTPClient(); 
        FileOutputStream fos = null; 

        try { 
            ftpClient.connect("192.168.0.161",21); 
            ftpClient.login("NSTC", "NSTC04"); 
            String remoteFileName = "TB盘/N6/N62014R2.0CEO.rar";
//          ftpClient.connect("192.168.0.15",21); 
//          ftpClient.login("liuyang2", "Hanyu130061"); 
//          String remoteFileName = "/通用软件/-=开发相关=-/javacn2005.rar";
        	
            fos = new FileOutputStream("F:/download.rar"); 
            ftpClient.setControlEncoding("GB2312");
            ftpClient.setBufferSize(1024); 
            //设置文件类型（二进制） 
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE); 
//            ftpClient.retrieveFile(remoteFileName, fos);
            ftpClient.retrieveFile(new String(remoteFileName.getBytes("GB2312"),"ISO-8859-1"), fos);
            System.out.println("download success");
        } catch (IOException e) { 
            e.printStackTrace(); 
            throw new RuntimeException("FTP客户端出错！", e); 
        } finally { 
            IOUtils.closeQuietly(fos); 
            try { 
                ftpClient.disconnect(); 
            } catch (IOException e) { 
                e.printStackTrace(); 
                throw new RuntimeException("关闭FTP连接发生异常！", e); 
            } 
        } 
    } 
} 

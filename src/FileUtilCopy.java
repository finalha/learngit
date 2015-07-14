import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class FileUtilCopy {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
//       String src = "E:/1/BRS-Service.jar";
//       String dest = "E:/2/BRS-Service.jar";
//       try
//       {
//       File srcfile = new File(src);
//       File destfile = new File(dest);
//       FileUtils.copyFile(srcfile, destfile);
//       System.out.println("OK");
//       }
//       catch(Exception e)
//       {
//    	   e.printStackTrace();
//       }
//		String[] updateFiles = new String[]{};
//		if(updateFiles == null)
//		{
//			System.out.println("yes");
//		}
//		else
//		{
//			System.out.println("no");
//		}
		String bsOrCs = "cs/fss";
		System.out.println(bsOrCs.substring(0, 2));
	}

}

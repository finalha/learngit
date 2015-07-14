import java.util.Arrays;
import java.util.Comparator;

public class ComparatorTest implements Comparator {

  public int compare(Object o1, Object o2) {
//    return toInt(o1) - toInt(o2);
	  return toInt(o2) - toInt(o1);
  }

  private int toInt(Object o) {
    String str = (String) o;
    str = str.replaceAll("一", "1");
    str = str.replaceAll("二", "2");
    str = str.replaceAll("三", "3");
    // 
    return Integer.parseInt(str);
  }

  /**
   * 测试方法:测试git:2015-07-14
   */
  public static void main(String[] args) {
	  /*
    String[] array = new String[] { "一二", "二", "三" };
    Arrays.sort(array, new ComparatorTest());
    for (int i = 0; i < array.length; i++) {
      System.out.println(array[i]);
    }
    */
	//System.out.println("svn info "+"\""+"http://192.168.0.2/svn/repo/CusT/000024东风财务/N0160.东风财务支付接口项目/02施工/2.5现场交付/[MB101]现场实施工作报告_2014.12.27-2015.01.23.docx"+"\"");
//	  String date = "2015-04-02";
//	 if(date.contains("-"))
//	 {
//		 System.out.println("yes");
//	 }
//	 else
//	 {
//		 System.out.println("no");
//	 }
//	  String ftpPath = "ftp://192.168.0.15/其他/公司VI应用模板/Foxmail-信纸格式(北京).rar";
//	  String s1 = ftpPath.substring(ftpPath.indexOf("//")+2);
//	  String s2 = s1.substring(s1.indexOf("/")+1);
//	  String s3 = s1.substring(0, s1.indexOf("/"));
//	  String s4 = s1.substring(s1.lastIndexOf("/")+1);
//	  System.out.println(s1);
//	  System.out.println(s2);
//	  System.out.println(s3);
//	  System.out.println(s4);
	  String regex = "[-a-z:A-Z0-9_\\s\\\\/]*";
//	  String install = "D:\\Program Files\\NSTC\\G6";
	  String install = "/weblogic/user_projects/domains/2015G6-first";
	  if(install.matches(regex))
	  {
		  System.out.println("true");
	  }
	  else
	  {
		  System.out.println("false");
	  }
  }

}
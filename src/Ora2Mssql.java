import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Ora2Mssql {

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		File root = new File("F:/新菜单维护/LMS");
		OraScripts2Mssql(root);
		System.out.println("It is OK!");
//		String str = "INSERT INTO WFPAGE (PAGENO, PAGENAME, APPNO, RSPMODE, URL, CAPTION) SELECT '12B5141A-87DF-FCAF-3337-F5273BA59878', 'Ͷ�����ϵͳ/�������/¼���������', 'BIFS-Web', 0, 'exec.do?c=bifsApply'|| chr(38)||'flag=101'|| chr(38)||'t=page/showBifsApplySet.jsp', '¼���������' FROM DUAL WHERE NOT EXISTS(SELECT 1 FROM WFPAGE WHERE PAGENO='12B5141A-87DF-FCAF-3337-F5273BA59878')";
//		if(str.toLowerCase().contains("'||'&'||'"))
//		{
//		 System.out.println(str.replace("'||'&'||'", "&")+'\r'+'\n');
//		}
//		if(str.toLowerCase().contains("'|| chr(38)||'"))
//		{
//	     System.out.println(str.replace("'|| chr(38)||'", "&")+'\r'+'\n');
//		}
	}

	public static void OraScripts2Mssql(File dir) throws Exception {
		File[] fs = dir.listFiles();
		for (int i = 0; i < fs.length; i++) {
//			System.out.println(fs[i].getAbsolutePath());			
				try {
					if (fs[i].isDirectory()) {
					OraScripts2Mssql(fs[i]);
					}
					else
					{
					  if(fs[i].isFile() && (fs[i].getName().toLowerCase().endsWith("sql")||fs[i].getName().toLowerCase().endsWith("txt")))
					  {
					//������дOracleתMssql���߼�
						File file = new File(fs[i].getAbsolutePath());
						FileInputStream fis = new FileInputStream(file);
						BufferedReader in = new BufferedReader(new InputStreamReader(fis,"GB2312"));
                        File outFile = null;
                        if(fs[i].getName().toLowerCase().endsWith("sql"))
                        {
						outFile = new File(fs[i].getAbsolutePath().toLowerCase().replace(".sql", "_2Mssql.sql"));
                        }
                        else
                        {
                        outFile = new File(fs[i].getAbsolutePath().toLowerCase().replace(".txt", "_2Mssql.txt"));	
                        }
						FileOutputStream fout = new FileOutputStream(outFile);
						BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fout,"GB2312"));
//						int path = file.getParent().lastIndexOf("\\");
//						String ModuleName = file.getParent().substring(path+1);
						//ȡ��sql�ļ����ϼ�Ŀ¼��Ϊģ����
						File file1 = new File(file.getPath());
						File parentFile = file1.getParentFile();
						String ModuleName = parentFile != null ? parentFile.getName() : "";
						System.out.println(ModuleName);		
						if(file.getName().toLowerCase().contains("wfpage"))
						{
							out.write("select * from WFPAGE where APPNO = '"+ModuleName+"'"+'\r'+'\n');	
							out.write("GO"+'\r'+'\n');
							out.write("delete from WFPAGE where APPNO = '"+ModuleName+"'"+'\r'+'\n');
							out.write("GO"+'\r'+'\n');
						}
						else if (file.getName().toLowerCase().contains("wf_menu_tree"))
						{
							out.write("select * from WF_MENU_TREE  where MENUID like '"+ModuleName+"%'"+'\r'+'\n');	
							out.write("GO"+'\r'+'\n');
							out.write("delete from WF_MENU_TREE  where MENUID like '"+ModuleName+"%'"+'\r'+'\n');
							out.write("GO"+'\r'+'\n');
						}	
						while (true) {
							String str = in.readLine();
							if (str == null)
							{	
								break;
							}
							else if(str.trim().equals("/"))
							{
							 out.write("GO"+'\r'+'\n'); 	
							 //out.flush();
							}
							/*
							else if(str.toLowerCase().contains("'|| chr(38)||'"))
							{
							 System.out.println("find chr(38)");
						     out.write(str.replace("'|| chr(38)||'", "&")+'\r'+'\n');		
						     //out.flush();
							}
							else if(str.toLowerCase().contains("'||'&'||'"))
							{
							 System.out.println("find &");
						     out.write(str.replace("'||'&'||'", "&")+'\r'+'\n');	
						     //out.flush();
							}
							else if(str.toLowerCase().contains("''"))
							{
							 out.write(str.replace("''", "NULL")+'\r'+'\n');	
						     //out.flush();
							}
							else if(str.toUpperCase().contains("FROM DUAL"))
							{
							 System.out.println("dual");
							 out.write(str.replace("FROM DUAL", "")+'\r'+'\n');
							 //out.flush();
							}
							*/
							else
							{
								//System.out.println(str);
								out.write(str.trim().replace("FROM DUAL", "").replace("''", "NULL").replace("'||chr(38)||'", "&").replace("'|| chr(38)||'", "&").replace("' || chr(38) || '", "&").replace("'||'&'||'", "&").replace("' ||'&'|| '", "&").replace("&'||'","&")+'\r'+'\n');
								//out.flush();
							}
						}
						out.flush();
					  }
					}
				} catch (Exception e) {
                   System.err.println(e.getMessage());
				}
			}
		}
	}

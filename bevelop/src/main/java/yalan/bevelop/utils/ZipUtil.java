/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yalan.bevelop.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

/**
 *
 * @author user
 */
public class ZipUtil {

    /**
     * 解壓縮檔案
     *
     * @param form 壓縮檔
     * @param destination 目的地
     */
    public static void extractAllFiles(String form, String destination) {

        try {
            ZipFile zipFile = new ZipFile(form);
            zipFile.extractAll(destination);

        } catch (ZipException e) {
            e.printStackTrace();
        }

    }

    /**
     * 解壓縮密碼壓縮檔
     *
     * @param form 壓縮檔
     * @param destination 目的地
     * @param password 密碼
     */
    public static void extractAllFiles(String form, String destination, String password) {

        try {
            ZipFile zipFile = new ZipFile(form);
            if (zipFile.isEncrypted()) {
                zipFile.setPassword(password);
            }
            zipFile.extractAll(destination);

        } catch (ZipException e) {
            e.printStackTrace();
        }

    }

    /**
     * 建立壓縮檔，並將檔案加入壓縮檔（未設置加密） 該類主要用於創建普通壓縮包。 如果壓縮包不存在則會自動創建一個 ZIP 包；
     * 如果已經存在一個不為空的同名 ZIP 壓縮包會將內容添加到該同名壓縮包中。
     *
     * @param destination 目的路徑
     * @param form 被壓縮檔路徑
     * @throws IOException
     */
    public static void addFilesDeflateComp(String destination, ArrayList<String> form) throws IOException {
        try {

            ZipFile zipFile = new ZipFile(destination);
            ArrayList<File> filesToAdd = new ArrayList<File>();
            for (int i = 0; i < form.size(); i++) {
                File file = new File(form.get(i));
                filesToAdd.add(file);
            }
            ZipParameters parameters = new ZipParameters();
            parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE); // set compression method to deflate compression
            parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
            zipFile.addFiles(filesToAdd, parameters);

        } catch (ZipException e) {
            Logger.getLogger(ZipUtil.class.getName()).log(Level.SEVERE, "", e);
        }
    }

    public static void addFolderToZip(String destination, File file) {
        try {
            ZipFile zipFile = new ZipFile(destination);
            ZipParameters parameters = new ZipParameters();
            parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE); // set compression method to deflate compression
            parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_FASTEST);
            zipFile.addFolder(file, parameters);
        } catch (ZipException e) {
            Logger.getLogger(ZipUtil.class.getName()).log(Level.SEVERE, "", e);
        }
    }

    /**
     * 建立壓縮檔，並將檔案加入壓縮檔指定資料夾（未設置加密）。 該程序執行後，會將文件添加到指定文件夾中並生成壓縮包，如果已經存在，
     * 則會將需要打包的內容添加到已經存在的壓縮包去。
     *
     * @param destination 目的路徑
     * @param form 被壓縮檔路徑
     * @param folder 資料夾
     */
    public static void addFilesToFolderInZip(String destination, ArrayList<String> form, String folder) {
        try {
            ZipFile zipFile = new ZipFile(destination);
            ArrayList<File> filesToAdd = new ArrayList<File>();
            for (int i = 0; i < form.size(); i++) {
                filesToAdd.add(new File(form.get(i)));
            }
            ZipParameters parameters = new ZipParameters();
            parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
            parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
            parameters.setRootFolderInZip(folder);
            zipFile.addFiles(filesToAdd, parameters);
        } catch (ZipException e) {
            e.printStackTrace();
        }

    }

    /**
     * 建立加密壓縮檔
     *
     * @param destination
     * @param form
     * @param password
     */
    public static void addFilesWithAESEncryption(String destination, ArrayList<String> form, String password) {

        try {
            ZipFile zipFile = new ZipFile(destination);

            ArrayList<File> filesToAdd = new ArrayList<File>();
            for (int i = 0; i < form.size(); i++) {
                filesToAdd.add(new File(form.get(i)));
            }
            ZipParameters parameters = new ZipParameters();
            parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);

            parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
            parameters.setEncryptFiles(true);

            parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_AES);

            parameters.setAesKeyStrength(Zip4jConstants.AES_STRENGTH_256);
            parameters.setPassword(password);

            zipFile.addFiles(filesToAdd, parameters);
        } catch (ZipException e) {
            e.printStackTrace();
        }
    }

    /**
     * 輸入資料夾的路徑, 顯示得該資料夾下的所有檔案路徑
     *
     * @author Lupin
     *
     */
    public static ArrayList<String> getFileList(String folderString) {
        //String folderPath = "C:\\";// 資料夾路徑
        ArrayList<String> fileList = new ArrayList<String>();
        try {
            File folder = new File(folderString);
            String[] list = folder.list();
            for (int j = 0; j < list.length; j++) {
                fileList.add(folderString + "/" + list[j]);
            }

        } catch (Exception e) {
            return new ArrayList<String>();
        }
        return fileList;
    }
}

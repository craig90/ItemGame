package com.craig.itemgame.model;

import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files
import java.util.ArrayList;


public class MyFileReader {

    Scanner scanner;
    // String fileName;

    MyFileReader() {
        this.scanner = null;
        // this.fileName = "";
    }

    public void openFile(String fileName) {

        try {
            // System.out.println(fileName);
            File file = new File(fileName);
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred locating the file");
            e.printStackTrace();
            System.exit(0);
        }

    }

    public String getLine() {
        
        if (scanner.hasNextLine()) {
            return scanner.nextLine();
        } else {
            scanner.close();
            return FileKeywords.END_OF_FILE;
        }
    }

    public void closeFile() {
        scanner.close();
    }

    /**
     * returns the names of the files in the specified directory as an ArrayList of Strings
     * @return false if no files are found
     */
    public static ArrayList<String> readFileNames(String extensionMatch) throws FileNotFoundException {
        
        File directory = new File(FileKeywords.DIRECTORY_PATH);
        File[] files = directory.listFiles();
        ArrayList<String> fileNames = new ArrayList<String>();

        System.out.println(FileKeywords.DIRECTORY_PATH);

        if (files != null) {
            for (File file : files) {
                // System.out.println(file.getName());
                String extension = "";
                String fileName = file.getName().toString();
                int dotIndex = fileName.lastIndexOf(".");

                if (dotIndex == -1 || dotIndex == fileName.length()) {
                    extension = "";
                } else {
                    extension = fileName.substring(dotIndex+1, fileName.length());
                }

                System.out.println(extension);

                if (extension.equals(extensionMatch)) {
                    System.out.println("Adding " + FileKeywords.DIRECTORY_PATH + file.getName());
                    fileNames.add(FileKeywords.DIRECTORY_PATH + file.getName());
                }


                if (extension.equals("json")) {
                    System.out.println("Found a json file");
                } else if (extension.equals("txt")) {
                    System.out.println("Found a txt file");
                } else {
                    System.out.println("No files with proper extension name");
                }
                
            }
            return fileNames;
        } else {
            throw new FileNotFoundException("No files found in " + FileKeywords.DIRECTORY_PATH);
        }

    }


     /**
     * this main method is purely for testing file IO class
     * @param args
     */
    public static void main(String args[]){

        ArrayList<String> fileNames = new ArrayList<String>();
        //FileReader fileReader = new FileReader();
        try {
            fileNames = readFileNames(FileKeywords.TEXT_FILE_EXTENSTION);
        } catch (FileNotFoundException e) {
            System.out.println("Ahhh, No files found");
        }

        for (int i = 0; i < fileNames.size(); i++) {
            System.out.println(fileNames.get(i));
        }

    }    


}

package com.craig.itemgame.model;

import java.io.FileWriter;
import java.io.IOException;

public class FileWrite {

    FileWriter fileWriter;

    public FileWrite() {

    }
    
    public void openFile(String fileName) throws IOException {
        try {
            this.fileWriter = new FileWriter(FileKeywords.DIRECTORY_PATH + fileName);
        } catch (java.io.IOException e) {
            throw new IOException("Error opening file " + e.getMessage());
        }
    }

    
    public void writeLine(String line) throws IOException {
        try {
            this.fileWriter.write(line + "\n");
        }
        catch (java.io.IOException e) {
            throw new IOException("Error writing to file " + e.getMessage());
        }
    }
    
    public void closeFile() throws IOException {
        try {
            this.fileWriter.close();
        } catch (java.io.IOException e) {
            throw new IOException ("Error closing file" + e.getMessage());
        }
    }

    /**
     * this main method is purely for testing file IO class
     * @param args
     */
    public static void main(String args[]){

        FileWrite fileWrite = new FileWrite();
        try {
            fileWrite.openFile("output.txt");
            fileWrite.writeLine("Test Line 1");
            fileWrite.writeLine("Test line 2");
            fileWrite.closeFile();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }    



}

package com.craig.itemgame.model;

import java.io.FileWriter;

public class FileWrite {

    FileWriter fileWriter;

    public FileWrite() {

    }
    
    public void openFile(String fileName) {
        try {
            this.fileWriter = new FileWriter(FileReader.directoryPath + fileName);
        } catch (java.io.IOException e) {
            System.err.println("Error creating file: " + e.getMessage());
        }
    }

    /** TODO: last line doesn't need \n -- but it's not really important - not worth implementing a  workaround */
    public void writeLine(String line) {
        try {
            this.fileWriter.write(line + "\n");
        }
        catch (java.io.IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }

    }
    
    public void closeFile() {
        try {
            this.fileWriter.close();
        } catch (java.io.IOException e) {
            System.err.println("Error closing file: " + e.getMessage());
        }
    }

    /**
     * this main method is purely for testing file IO class
     * @param args
     */
    public static void main(String args[]){

        FileWrite fileWrite = new FileWrite();
        fileWrite.openFile("output.txt");
        fileWrite.writeLine("Test Line 1");
        fileWrite.writeLine("Test line 2");
        fileWrite.closeFile();

    }    



}

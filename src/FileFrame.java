
import java.io.File;

import javax.swing.*;


public class FileFrame extends JInternalFrame  {

    GUI topGUI;
    JSplitPane splitpane;
    String currentTitle;
    DirectoryPanel dirpanel;
    FilePanel filePanel;
    GetDrives drives = new GetDrives();
    String selectedFile = null;
    String fileFrameTitle;

    public FileFrame(GUI givenGUI){
        topGUI = givenGUI;
        filePanel = new  FilePanel(givenGUI);
        topGUI.setOurFilePanel(filePanel);
        dirpanel = new DirectoryPanel();
        currentTitle = drives.getDefaultRootName();
        filePanel.setFileFrameAtFilePanel(this);
        dirpanel.setFilePanel(filePanel);
        dirpanel.setFileFrame(this);
        dirpanel.setFileFrameTitle(currentTitle);
        splitpane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, dirpanel, filePanel );
        this.getContentPane().add(splitpane);     


        this.setTitle(drives.getDefaultRootName()+ "");
        
        this.setMaximizable(true);
        this.setClosable(true);
        this.setIconifiable(true);
        this.setSize(850,550);
        setVisible(true);
    }

    public FileFrame(File currentDirectory, GUI givenGUI){
        
        topGUI = givenGUI;
        System.out.println(currentDirectory.toString());
        filePanel = new  FilePanel(givenGUI);
        dirpanel = new DirectoryPanel(currentDirectory);
        dirpanel.setTreeRoot(currentDirectory);
        currentTitle = currentDirectory.toString();
        filePanel.setFileFrameAtFilePanel(this);
        dirpanel.setFilePanel(filePanel);
        dirpanel.setFileFrame(this);
        //dirpanel.setFileFrameTitle(currentDirectory.toString()+ "Hello there");
        splitpane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, dirpanel, filePanel );
        this.getContentPane().add(splitpane);
                
        this.setTitle(currentDirectory.toString());
        
        this.setMaximizable(true);
        this.setClosable(true);
        this.setIconifiable(true);
        this.setSize(850,550);
        setVisible(true);
    
    }  






    public void setNewTitle(String newTitle){

        fileFrameTitle = newTitle;
        this.setTitle(fileFrameTitle);

    }

    public String getFrameTitle(){

        return fileFrameTitle;

    }

    public void setSelectedFileLocation(String selectedFile){

        this.selectedFile = selectedFile;

    }


    public String getSelectedFileName(){

        return selectedFile;

    }


 
    
}

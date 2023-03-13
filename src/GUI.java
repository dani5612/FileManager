import javax.swing.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.awt.*;



public class GUI extends JFrame{
    JPanel panel, topPanel;
    //JButton ok, cancel;
    JMenuBar menubar, statusbar;
    public static JDesktopPane desktoppane;
    GetDrives driveList;
    JTree ourTree;
    JList ourJList;
    FileFrame ourFileFrame;
    JToolBar toolbar;
    GetDrives GUIdrives;
    JButton details, simple;
    JComboBox select_drive;
    Integer FrameCounter = 0;
    File GUISelectedFile;
    GUI ourInterface = this;
    FilePanel ourFilePanel;
    Integer SelectedListInteger;
    File currentPath;
    

    public GUI(){

        driveList = new GetDrives();
        desktoppane = new JDesktopPane();
        panel = new JPanel();
        topPanel = new JPanel();
        menubar = new JMenuBar();
        toolbar = new JToolBar();
        GUIdrives = new GetDrives();
        statusbar = new JMenuBar();
        panel.setLayout(new BorderLayout());
        topPanel.setLayout(new BorderLayout());
        
        
        
        //panel.setLayout(new BorderLayout());
        //ok = new JButton("Okay");
        //cancel = new JButton("Cancel");
        //ok.addActionListener(new okActionListener());    
        //cancel.addActionListener(new okActionListener());
        
    }  



    public void setOurFilePanel(FilePanel givenFilePanel){

        ourFilePanel =  givenFilePanel;
    }

    public void setGUISelectedFile(File fileToAssign){

        GUISelectedFile = fileToAssign;

    }

    public void go() {
        //panel.add(ok, BorderLayout.NORTH);
        //panel.add(cancel, BorderLayout.SOUTH);
        
        buildMenu();
        buildFileFrame();
        buildStatusBar(GUIdrives.getDefaultRoot());
        buildToolBar();
        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(desktoppane, BorderLayout.CENTER);
        this.add(panel);
        
        this.setSize(1000,800);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //this.add(menubar);
        this.setTitle("-File Explorer-");
        this.setVisible(true);
        
        
        

    }

    public void cascada(){

        this.setLocationByPlatform(true);

    }

    public void setGUIRoot(File newRoot){

        GUIdrives.setDefaultRoot(newRoot);

    }

    private void buildMenu(){

        JMenu file, window, tree, help;
        JMenuItem rename = new JMenuItem("Rename");
        JMenuItem copy = new JMenuItem("Copy");
        JMenuItem delete = new JMenuItem("Delete");
        JMenuItem run = new JMenuItem("Run");
        JMenuItem exit = new JMenuItem("Exit");
        JMenuItem explandBranch = new JMenuItem("Expand Branch");
        JMenuItem collapseBranch = new JMenuItem("Collapse Branch");
        JMenuItem menuItemNew = new JMenuItem("New");
        JMenuItem cascade = new JMenuItem("Cascade");
        JMenuItem menuItemHelp = new JMenuItem("Help");
        JMenuItem about = new JMenuItem("About");

        file = new JMenu("File");
        tree = new JMenu("Tree");
        window = new JMenu("Window");
        help = new JMenu("Help");

        rename.addActionListener(new itemActionListener());
        copy.addActionListener(new itemActionListener());
        delete.addActionListener(new itemActionListener());
        run.addActionListener(new itemActionListener());
        exit.addActionListener(new itemActionListener());
        explandBranch.addActionListener(new itemActionListener());
        collapseBranch.addActionListener(new itemActionListener());
        menuItemHelp.addActionListener(new itemActionListener());
        cascade.addActionListener(new itemActionListener());
        about.addActionListener(new itemActionListener());
        menuItemNew.addActionListener(new itemActionListener());

        menubar.add(file);
        menubar.add(tree);
        menubar.add(window);
        menubar.add(help);

        file.add(rename);
        file.add(copy);
        file.add(delete);
        file.add(run);
        file.add(exit);

        tree.add(explandBranch);
        tree.add(collapseBranch);

        window.add(menuItemNew);
        window.add(cascade);

        help.add(menuItemHelp);
        help.add(about);

        topPanel.add(menubar, BorderLayout.NORTH);


    }



    private File[] getDrives() {
        File [] paths = File.listRoots();
        return paths;
    }


    private String bytesToInfo(long bytes) {
        long kb = 1024;
        long mb = kb * 1024;
        long gb = mb * 1024;
        long tb = gb * 1024;
        if ((bytes >= kb) && (bytes < mb))
            return (bytes/kb) + "KB";
        else if ((bytes >= mb) && (bytes < gb))
            return (bytes/mb) + "MB";
        else if ((bytes >= gb) && (bytes < tb))
            return (bytes/gb) + "GB";
        else if (bytes >= tb)
            return (bytes/tb) + "TB";
        else
            return bytes + "Bytes";
    }

    private void buildStatusBar(File currentRoot){
        
        String freeSpace = bytesToInfo(currentRoot.getFreeSpace());
        String usedSpace = bytesToInfo(currentRoot.getTotalSpace()- currentRoot.getFreeSpace());
        String totalSpace = bytesToInfo(currentRoot.getTotalSpace());
        JLabel cd = new JLabel("Current Drive: " + currentRoot + "   ");
        JLabel fs = new JLabel("Free Space: " + freeSpace + "   ");
        JLabel us = new JLabel("Used Space: " + usedSpace + "   ");
        JLabel ts = new JLabel("Total Space: " + totalSpace + "   ");
        
        //panel.setLayout(new BorderLayout());
        statusbar.removeAll();
        statusbar.add(cd);
        statusbar.add(fs);
        statusbar.add(us);
        statusbar.add(ts);
        panel.add(statusbar, BorderLayout.SOUTH);
    }

/*  THIS ONE WORKS
    private void buildStatusBar(){
        JLabel size = new JLabel("Size in GB: ");
        statusbar.add(size);
        panel.add(statusbar, BorderLayout.SOUTH);


    }
*/

public void buildToolBar(){

    File [] drives = getDrives();
    drives = File.listRoots();
    
    select_drive = new JComboBox(drives); 
    select_drive.addActionListener(new StatusActionListener());
    details = new JButton("Details");
    simple = new JButton("Simple");
    select_drive.setSize(20, 20);
  
    
    
    toolbar.add(select_drive);
    toolbar.add(details);
    toolbar.add(simple);
    
    
    topPanel.add(toolbar, BorderLayout.SOUTH);
}


    public void buildcascade(){
        
        JInternalFrame[] cascade = desktoppane.getAllFrames();
        int i = 10;
        int j = 10;
        for ( JInternalFrame k : cascade ){
            k.setLocation(i, j);
            i += 25;
            j += 25;
            k.moveToFront();
            System.out.println(k);

        }
           
    }


    //MAY SWITCH TO PUBLIC LATER
    private void buildFileFrame(){

        ourFileFrame = new FileFrame(this);
        desktoppane.add(ourFileFrame);


    }


    public JInternalFrame  getOurSelectedFrame(){

        return this.desktoppane.getSelectedFrame();

    }


    private class StatusActionListener implements ActionListener{

        
        @Override
        public void actionPerformed(ActionEvent e) {

            
            System.out.println(select_drive.getSelectedItem());
            File SelectedRoot = (File) select_drive.getSelectedItem();
            FileFrame newFileFrame = new FileFrame(SelectedRoot, ourInterface);
            //FileFrame ff_new = new FileFrame((File) select_drive.getSelectedItem());
            FrameCounter++;
            Integer locationX;
            Integer locationY;
            if(FrameCounter < 20){
                locationX = FrameCounter * 25;
                locationY = FrameCounter * 25;
                
            }
            else{
                locationX = ((FrameCounter - 19 )  * 25) + 25;
                locationY = ((FrameCounter - 19 )  * 25);
            }

            newFileFrame.setLocation(locationX,locationY);
            buildStatusBar(SelectedRoot);
            desktoppane.add(newFileFrame);
            desktoppane.moveToFront(newFileFrame);
//           buildStatusBar((File) sel_drive.getSelectedItem());
        }

    }


    public class itemActionListener implements ActionListener  {

        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equals("About")){
                System.out.println("You pressed About");
                AboutDig dig = new AboutDig(null,true );
                dig.setVisible(true); 
            }
            else if (e.getActionCommand().equals("Help")){
                System.out.println("You pressed Help");
                System.out.println("MESSAGE FROM GUI! OUR SELECTED FILE HERE IS NOW " + GUISelectedFile.toString());
            }
            else if (e.getActionCommand().equals("Cascade")){
                System.out.println("You pressed Cascade");
                buildcascade();
                
            }
            else if (e.getActionCommand().equals("New")){
                System.out.println("You pressed New");
                buildFileFrame();
            }
            else if (e.getActionCommand().equals("Collapse Branch")){
                System.out.println("You pressed Collapse Branch");
                FileFrame activeFrame = (FileFrame) desktoppane.getSelectedFrame();
                if(activeFrame == null) return;
                JTree temporalTree = activeFrame.dirpanel.getTree();
                int row = temporalTree.getMinSelectionRow();
                if(temporalTree.isExpanded(row)){
                    temporalTree.collapseRow(row);
                }



            }
            else if (e.getActionCommand().equals("Expand Branch")){
                System.out.println("You pressed Expand Branch");
                FileFrame activeFrame = (FileFrame) desktoppane.getSelectedFrame();
                if(activeFrame == null) return;
                JTree temporalTree = activeFrame.dirpanel.getTree();
                int row = temporalTree.getMinSelectionRow();
                if(temporalTree.isCollapsed(row)){
                    temporalTree.expandRow(row);
                }
            



            
            }
            else if(e.getActionCommand().equals("Rename")){
                System.out.println("You pressed Rename");
                Renaming renameWindow = new Renaming(null, true);
                renameWindow.setLocalGUI(ourInterface);
                
                renameWindow.setLocalGUI(ourInterface);
                renameWindow.setFromTextField(GUISelectedFile.toString());
                renameWindow.setcurrentDirectoryRename(GUISelectedFile.toString());
                renameWindow.setVisible(true);
                
                String to = renameWindow.getToField();

                if (to.length()>0){

                    Path source =  Paths.get(GUISelectedFile.getAbsolutePath());
                    try {
                        Files.move(source, source.resolveSibling(to));
                        
                    } catch (IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                    

                }
                currentPath = ourFileFrame.dirpanel.getCurrentPath();
                MyFileNode nfn = new MyFileNode(currentPath.getName(), currentPath);
                ourFilePanel.fillList(nfn);

                System.out.println("You pressed Rename");
            }
            else if(e.getActionCommand().equals("Copy")){
                System.out.println("You pressed Copy");
                
                Copying copyWindow = new Copying(null, true);
                copyWindow.setLocalGUI(ourInterface);
                copyWindow.setFromTextField(GUISelectedFile.toString());
                copyWindow.setCurrentDirecroryLabel(GUISelectedFile.toString());
                copyWindow.setVisible(true); 
                String to = copyWindow.getToField();
                if (to.length()>0){
                    MyFileNode newfilenode = new MyFileNode(to);
                    String toPath = newfilenode.getFile().getAbsolutePath();
                    Path source = Paths.get(GUISelectedFile.getAbsolutePath());
                    Path destination = Paths.get(toPath);

                    try {
                        Files.copy(source, destination);
                    } catch (IOException e1) {
                        
                        e1.printStackTrace();
                    }


                }      
                
            }
            else if(e.getActionCommand().equals("Delete")){
                System.out.println("You pressed Delete");
                ourJList = ourFilePanel.fileList;
                File filename = (File) ourFilePanel.fileList.getSelectedValue();
                int indexFile = ourJList.getSelectedIndex();

                ourFilePanel.model.removeElement(ourJList.getSelectedValue());
                //Integer x =ourFilePanel.getIndexToDelete();
                //System.out.println(x);
                //ourJList.remove(SelectedListInteger);
                
            }
            else if(e.getActionCommand().equals("Run")){
                
                    //fileList.getSelectedValue().;
                    Desktop desktop = Desktop.getDesktop();
                    try{  
                    desktop.open(GUISelectedFile);}
                   catch( IOException    ex){ System.out.println(ex.toString());}
                System.out.println("You pressed Run");
            }
            else if(e.getActionCommand().equals("Exit")){
                System.out.println("You pressed Exit");
                System.exit(0);
            }
        }
    }


}


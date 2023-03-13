import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.dnd.*;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.*;
import java.util.List;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

//import jdk.internal.org.jline.terminal.MouseEvent;

import java.awt.event.*;
import java.text.SimpleDateFormat;





public class FilePanel extends JPanel{
    GUI GUIAccessFromFilePanel;
    private JScrollPane scrollpane = new JScrollPane();
    JList fileList;
    DefaultListModel model = new DefaultListModel();
    String  selectedFile;
    FileFrame fileFrame;
    Integer selectedItemIndex;

    
    //listActionListener listener = new listActionListener(); 


    //String[] alist = {"hola ", "hola"};
    public void setselectedItemIndex(Integer index){


        selectedItemIndex = index;
        
    }


    



    public FilePanel(GUI GivenGuy){
        GUIAccessFromFilePanel = GivenGuy;
        selectedFile = null;
        fileList = new JList();
        this.setDropTarget(new MyDropTarget());
        //fileList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        //fileList.addListSelectionListener(listener);
        MouseListener mouseListener = new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1){

                    getActiveFilePanel();
                    selectedFile = (String) fileList.getSelectedValue();
                    setselectedItemIndex((Integer) fileList.getSelectedIndex());
                    GUIAccessFromFilePanel.SelectedListInteger = (Integer) fileList.getSelectedIndex();
                    
                    System.out.println(selectedFile + selectedItemIndex);
                    System.out.println("SELECTED FILE IS NOW "+ selectedFile.toString());

                    String[] splited = selectedFile.split("    ");
                    GUIAccessFromFilePanel.setGUISelectedFile(new File( splited[0]  )  );
                    System.out.println("MESSAGE FROM FILE PANEL! OUR SELECTED FILE IS " + splited[0] );
                    
                }
                if (e.getClickCount() == 2) {
    
                    
                   String selectedItem = (String) fileList.getSelectedValue();
                   String[] splited = selectedItem.split("     ");

                    
                   System.out.println("AQUI STAAA" + splited[0]);

                   // add selectedItem to your second list.
                   System.out.println(selectedItem);
                   //JOptionPane.showMessageDialog(null ,fileList.getSelectedValue());
                   Desktop desktop = Desktop.getDesktop();
                   try{
                    //fileList.getSelectedValue().;  
                    desktop.open(new File( splited[0]  ) );}
                   catch(IOException    ex){ System.out.println(ex.toString());}


        
                }
                if (e.getButton() == MouseEvent.BUTTON3){

                    popMenu ourPopMenu;

                    try{

                        ourPopMenu = new popMenu();
                        ourPopMenu.show(e.getComponent(), e.getX(), e.getY());


                    }
                    catch (Exception ex){}

                }
            }
        };
        fileList.addMouseListener(mouseListener);
        //fileList.setPreferredSize(new Dimension(200,200));
        //this.setDropTarget(new MyDropTarget());
        fileList.setDragEnabled(true);
        fileList.setModel(model);  

        scrollpane.setViewportView(fileList);
        
        add(scrollpane);


        GroupLayout gL = new GroupLayout(this);
        this.setLayout(gL);
        gL.setHorizontalGroup(
            gL.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(scrollpane, GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
            );
            gL.setVerticalGroup(
                gL.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(scrollpane, GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                );
        

    
    }



    public Integer getIndexToDelete(){

        return selectedItemIndex;

    }

    public JList getJlist(){

        return fileList;
    }

    public static FilePanel getActiveFilePanel() {
        if (GUI.desktoppane == null) {
          return new FilePanel(new GUI());
        }
        FileFrame activeFrame = (FileFrame) GUI.desktoppane.getSelectedFrame();
        if (activeFrame == null) {
          return new FilePanel(new GUI());
        }
        return activeFrame.filePanel;
    }

    public Integer getourSelectedRow(){

        return fileList.getSelectedIndex();

    }


    public void updateFilePanel(){




        
    }

    public void fillList(MyFileNode dir){
        File[] files;
        File[] cleanedFiles;
        
        

        FileFilter directoryFilter = new FileFilter(){

            @Override
            public boolean accept(File file) {
                // TODO Auto-generated method stub
                return file.isDirectory();
            }
                
            };
        

            

        files = dir.getFile().listFiles();
        cleanedFiles = dir.getFile().listFiles(directoryFilter);
        model.clear();
        fileList.removeAll();
        fileList.clearSelection();
        for(int i = 0; i < cleanedFiles.length; i++){


            if(!cleanedFiles[i].isHidden()){
                File file = files[i];
                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
                
                
                model.addElement(files[i].toString());
            } 

        }
        for(int i = 0; i < files.length; i++){

            if(files[i].isDirectory()){
                continue;
            }
            if(!files[i].isHidden()){

                File file = files[i];
                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

                
        
                /*for(int k = 0 ; k < Integer2 ; k++){

                    string1 = string1 +" ";

                }*/

                //string2 = string1 +"     "+"Size: " + files[i].length()+ "bytes" + "     Last modified date " + sdf.format(file.lastModified());
                //String[] splited = string1.split("     ");
                
                //System.out.println(string1 +"---------------------------------------------------------------------------------------------------------");
                //System.out.println("---------"+string1+"-------------");
                //System.out.println("---------"+string1.trim()+"-------------");

                model.addElement(files[i].toString()+"     "+"Size: " + files[i].length()+ "bytes" + "     Last modified date " + sdf.format(file.lastModified()));
                //model.addElement(files[i].toString());
            } 

        }
        fileList.setModel(model);
        
    }


    class MyDropTarget extends DropTarget {
        /**************************************************************************
         * 
         * @param evt the event that caused this drop operation to be invoked
         */    
            public void drop(DropTargetDropEvent evt){
                try {
                    //types of events accepted
                    evt.acceptDrop(DnDConstants.ACTION_COPY);
                    //storage to hold the drop data for processing
                    List result = new ArrayList();
                    //what is being dropped? First, Strings are processed
                    if(evt.getTransferable().isDataFlavorSupported(DataFlavor.stringFlavor)){     
                        String temp = (String)evt.getTransferable().getTransferData(DataFlavor.stringFlavor);
                        //String events are concatenated if more than one list item 
                        //selected in the source. The strings are separated by
                        //newline characters. Use split to break the string into
                        //individual file names and store in String[]
                        String[] next = temp.split("\\n");
                        //add the strings to the listmodel
                        for(int i=0; i<next.length;i++)
                            model.addElement(next[i]); 
                        }
                    else{ //then if not String, Files are assumed
                        result =(List)evt.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
                        //process the input
                        for(Object o : result){
                            System.out.println(o.toString());
                            model.addElement(o.toString());
                        }
                    }
                }
                catch(Exception ex){
                    ex.printStackTrace();
            }
        }
            
    }
    
    

    private class popMenu extends JPopupMenu{

        JMenuItem rename  = new JMenuItem("Rename");
        JMenuItem copy  = new JMenuItem("Copy");
        JMenuItem delete = new JMenuItem("Delete");

        public popMenu(){

            add(rename);
            add(copy);

            this.addSeparator();
            add(delete);

        }



    }

    public void setFileFrameAtFilePanel(FileFrame recievedFileFrame){
        
    }

    private class listActionListener implements ListSelectionListener  {

        String itemSelected;
        @Override
        public void valueChanged(ListSelectionEvent e) {

            int index = fileList.getSelectedIndex();
            //String filepath = fileList.getSelectedValue().toString();
            JList list = (JList)e.getSource();			
	        JOptionPane.showMessageDialog(null,list.getSelectedValue());
            if(index != -1){

                System.out.println("You selected " + list.getSelectedValue().toString() );

            } 
            list.removeAll();
            index = -1;
        }
    }
    /*public void setDisplayList(MyFileNode[] listFiles){


        alist = listFiles;


    }*/


}

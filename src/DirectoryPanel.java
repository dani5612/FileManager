import java.awt.*;
import java.io.*;


import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.xml.crypto.dsig.keyinfo.RetrievalMethod;

public class DirectoryPanel extends JPanel {
    
    private JScrollPane scrollpane = new JScrollPane();
    private JTree directoryTree = new JTree();
    private DefaultTreeModel treemodel;
    //DirectoryRead reader = new DirectoryRead();  //reads stuff
    GetDrives drives = new GetDrives();          //list of drives with a default drive
    
    //This is the top node of the tree
    DefaultMutableTreeNode root= null;  //= new DefaultMutableTreeNode(rootDrive);
    File[] RootFiles;
    DefaultMutableTreeNode dir = null;
    DefaultMutableTreeNode treeSubnode = null;
    DefaultMutableTreeNode deepSubnode = null;
    DefaultMutableTreeNode deepDir = null;
    File[] ACTIONdirs;
    File[] ACTIONfilteredDirs;
    FilePanel filePanel;
    FileFrame fileFrame;
    String fileFrameName; 
    MyFileNode fileNodeObject = new MyFileNode("");
    File currentPath;
    public void setTreeRoot(File newRoot){

        root = new DefaultMutableTreeNode(newRoot);

    }

    public DirectoryPanel(){
        File rootDrive = drives.getDefaultRoot();
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

        scrollpane.setViewportView(directoryTree);
        //directoryTree.setPreferredSize(new Dimension(200,50000));
        add(scrollpane);
        buildTree(rootDrive);

    }

    public JTree getTree(){

        return directoryTree;

    }
    public DirectoryPanel(File GivenDirectory){
        File rootDrive = GivenDirectory;
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

        scrollpane.setViewportView(directoryTree);
        //directoryTree.setPreferredSize(new Dimension(200,50000));
        add(scrollpane);
        buildTree(rootDrive);

    }
    
    public void setFilePanel(FilePanel givenfilePanel){

        this.filePanel = givenfilePanel;

    }

    public void setFileFrame(FileFrame givenFileFrame){

        this.fileFrame = givenFileFrame;
    }

    public void setFileFrameTitle(String givenFileFrameTitle){

        this.fileFrameName = givenFileFrameTitle;

    }

    public File getCurrentPath(){

        return currentPath;

    }

    private void buildTree(File TreeRoot){
        //Here you can put anything you want, not just strings
        DefaultTreeCellRenderer renderer = (DefaultTreeCellRenderer) directoryTree.getCellRenderer();
        renderer.setLeafIcon(renderer.getClosedIcon());
        if(root == null){root = new DefaultMutableTreeNode(TreeRoot);}
        treemodel = new DefaultTreeModel(root); 
        directoryTree.addTreeSelectionListener(new treeSelectionListener());
    
        RootFiles = TreeRoot.listFiles();

        for(int i = 0; i<RootFiles.length;i++){

            System.out.println(RootFiles[i].getName()+"IS AT INDEX" + i);
            System.out.println(RootFiles[i].getName());
            MyFileNode aFileNode = new MyFileNode(RootFiles[i].getName(), RootFiles[i]);
            dir = new DefaultMutableTreeNode(aFileNode);
            root.add(dir)
            ;
            if(RootFiles[i].isDirectory() && RootFiles[i].canRead() && RootFiles[i] != null ){
                File innerDirectoryFiles [];
                if (RootFiles[i] != null ){
                    innerDirectoryFiles = RootFiles[i].listFiles();


                    System.out.println("This is a drirectorty"+RootFiles[i].getName());
                }

            }
            else{System.out.println("This is a file"+ RootFiles[i].getName());}
                

        }
        directoryTree.setModel(treemodel);

        /*for(int i = 0; i < Rootfiles.length; i++){
            //System.out.println(files[i].getName());
            MyFileNode myFileNode = new MyFileNode(Rootfiles[i].getName(), Rootfiles[i]);
            if (myFileNode.isDirectory()){
                File directoryFiles [] = reader.lookForDirectories(myFileNode.getFile());
                DefaultMutableTreeNode node = new DefaultMutableTreeNode(myFileNode.getFile());
                for(int k = 0; k < Rootfiles.length-1; ){
                    MyFileNode file = new MyFileNode(directoryFiles[k].getName(), directoryFiles[k]);
                    DefaultMutableTreeNode subnode = new DefaultMutableTreeNode(file.getFile());
                    node.add(subnode);
                System.out.println("is Directory"+ myFileNode.toString());
                //buildBranch(myFileNode);
                k += 1;
                }
            }
            else{System.out.println("Not directorty"+ myFileNode.toString());}
            DefaultMutableTreeNode node = new DefaultMutableTreeNode(myFileNode);
            root.add(node);
        }
        //DefaultMutableTreeNode node = new DefaultMutableTreeNode(owo);
        //root.add(node);
        //node = new DefaultMutableTreeNode(owo);
        //root.add(node);
        //for(int i = 0; i < 10; i++ ){
             //We could probably put thefile.getAbsolutePath();//returns a string 
            //MyFileNode myFileNode = new MyFileNode("subnode"+i);
            //DefaultMutableTreeNode subnode = new DefaultMutableTreeNode(myFileNode);
            //node.add(subnode);
         //}
            
        */
        
        
    }

    //public String set







    public void buildBranch(MyFileNode aDirectory){
        File [] branchFiles;
        //MyFileNode directory = new MyFileNode(aDirectory.getName(), aDirectory);
        //DefaultMutableTreeNode branch = new DefaultMutableTreeNode(aDirectory);
        DirectoryRead aReader = new DirectoryRead(); 

        branchFiles = aReader.lookForDirectories(aDirectory.getFile());
        System.out.println(branchFiles); 
        

        
        //aReader.cleanContainer();
        

    }



    class treeSelectionListener implements TreeSelectionListener{

        @Override
        public void valueChanged(TreeSelectionEvent e) {
            
            
            System.out.println(directoryTree.getMinSelectionRow());
            System.out.println(directoryTree.getSelectionPath());//C:\\Node2\\SubNode0
            // TODO Auto-generated method stub
            DefaultMutableTreeNode Emptynode = null;                    //We are gonna be adding this guy to the selected node

            DefaultMutableTreeNode node = (DefaultMutableTreeNode)  directoryTree.getLastSelectedPathComponent();   //Node that we clicked on Type TreeNode
            

            //PLEASE UNCOMENT THIS
            MyFileNode nfn = (MyFileNode) node.getUserObject();                                                     //We convert the tree node to MyFileNode
            
            System.out.println(nfn.toString());
            //JOptionPane.showMessageDialog(scrollpane, nfn.getFile().getName());
            


            ACTIONdirs = nfn.getFile().listFiles();                                                       //We get the files inside the MyFileNode we created & store them in a container
            ACTIONfilteredDirs = nfn.getFile().listFiles();
            currentPath = nfn.getFile();
            if(nfn.isDirectory()){

                 
                try{                //to avoid crashes
                    filePanel.fillList(nfn);
                    fileFrame.setNewTitle(nfn.getFile().getAbsolutePath());
                    for(int k = 0; k <ACTIONdirs.length; k++){                              //We check the files inside the container we created
                        if(ACTIONdirs[k].isDirectory()){
                            
                            MyFileNode aNewFileNode = new MyFileNode(ACTIONdirs[k].getName(),ACTIONdirs[k]);   //we grab one of the files in the container and turn it into MyFiLeNode type


                            Emptynode = new DefaultMutableTreeNode(aNewFileNode);                           //We turn that MyFileNode into a tree node
                            node.add(Emptynode);                                                            //We stick the new tree node into the very old tree node

                        }
                        
                        /*else{
                            MyFileNode aNewFileNode = new MyFileNode(ACTIONdirs[k].getName(),ACTIONdirs[k]);
                            
                            //Emptynode = new DefaultMutableTreeNode(aNewFileNode);
                            node.add(Emptynode);
                        }*/
  
                    }
                    
                }  
            catch(Exception exc){System.out.println("Permisions needed to open this file");JOptionPane.showMessageDialog(scrollpane,exc +" \nFile Protected by System");}

            }
            

            

            //System.out.println(node);
            //File file = new File("D:");

            //file.getAbsolutePath();//returns a string
        }


    }



}

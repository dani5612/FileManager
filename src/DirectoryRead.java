import java.io.File;

public class DirectoryRead {
    

    File[] files;
    GetDrives drives = new GetDrives();
    

    public DirectoryRead(){


    }

    public File[] lookForDirectories(File fileToRead){

        //files = drives.defaultRoot.listFiles();
        files = fileToRead.listFiles(); 
        for(int i = 0; i < files.length; i++){
            //System.out.println("Contador"+ i);
            //System.out.println("inside"+fileToRead.getName()+" -- "+files[i].getName());

        }
        //System.out.println("-------------------------");
        
        return files;
        //cleanContainer();
    }

    public void cleanContainer(){

        files = null;

    }

}

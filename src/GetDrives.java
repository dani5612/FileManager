import java.io.File;


public class GetDrives {

    File[] paths;
    File defaultRoot;

    public GetDrives(){
    
        
        paths = File.listRoots();
        defaultRoot = paths[0];


    }

    public void setDefaultRoot(File customRoot){

        defaultRoot = customRoot;

    }

    public void printDrives(){

        for(File path: paths){

            System.out.println(path);

        }

    }

    public String getDefaultRootName(){
        //System.out.println(defaultRoot.getName()+"------------------------------");
        String name = defaultRoot.toString();
        return name ;
        

    }

    public File getDefaultRoot(){

        return defaultRoot;

    }

    public void setDefaultRootFromDriveList(int driveNumber ){

        defaultRoot = paths[driveNumber];

    }

    
}
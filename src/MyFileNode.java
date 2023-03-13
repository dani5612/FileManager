import java.io.File;

public class MyFileNode{
    File file; 
    String fileName;

    public  MyFileNode(String fileName){

        file = new File(fileName);

    }

    public  MyFileNode(String fileName, File file){

        this.fileName = fileName;
        this.file = file;
         
    }

    public File getFile(){

        return this.file;    

    }

    public String toString(){

        if(this.file.getName().equals("")){

            return file.getPath();
        }
        return file.getName();

    }

    public String getFileName(){

        return fileName;

    }

    public boolean isDirectory(){

        return this.file.isDirectory();

    }


}

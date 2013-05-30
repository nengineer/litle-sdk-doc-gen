package mainApp;

import java.io.File;

public class DocGenerator {

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        
        // XML file address
        // type of package :: java :: ruby :: dotnet
        // Package address
        // version number
            
        if(args.length != 4){
            System.out.println("Please enter following 4 arguments  ::\n " +
            		"1. XML file address\n " +
            		"2. Type of sdk(type java for Java-sdk, ruby for Ruby-sdk, dotnet for Dotnet-sdk)\n " +
            		"3. Package Address\n" +
            		"4. Version Number");
        }else if(!new File(args[0]).canRead()){
            System.out.println("Cannot read the XML File. Please check the permissions to continue...\n");
        }
        else if(!new File(args[2]).isDirectory()){
            System.out.println("Package is not valid");
        }
        else if(!new File(args[2]).canWrite()){
            System.out.println("Permission Denied for the Package : " + args[2]);
        }
        else if(!(args[1].toLowerCase().equals("java")
                ||args[1].toLowerCase().equals("ruby")
                ||args[1].toLowerCase().equals("dotnet"))){
            System.out.println("Please enter the valid type of package" +
            		"(type java for Java-sdk, ruby for Ruby-sdk, dotnet for Dotnet-sdk)");
        }else{
            try{
                Float f = Float.valueOf(args[3]);
                if(f < 0) throw new Exception(" It cannot be negative");
            }catch(Exception e){
                System.out.println("Version number is not valid.\n");
                System.out.println(e.getMessage());
            }
            if(args[1].toLowerCase().equals("java"))
                new DocGeneratorForJava().run(args[0],args[2],args[3]);
            else if(args[1].toLowerCase().equals("dotnet"))
                new DocGeneratorForDotNet().run(args[0], args[2],args[3]);
            else 
                new DocGeneretorForRuby().run(args[0], args[2], args[3]);
            
        }
        
    }

}

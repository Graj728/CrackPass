import net.lingala.zip4j.core.*;
import net.lingala.zip4j.exception.*;

import java.util.ArrayList;

public class three{
    public static String password="";
    public static boolean pfound=false;

    public three (String fileName,ArrayList<String> pass){
       
        for (String string : pass) {
            try {
			ZipFile zipFile = new ZipFile(fileName);
			zipFile.setPassword(string);
			zipFile.extractAll("contents");
            pfound=true;
            password=string;
			System.out.println("Successfully cracked!"+string);
            
            break;
            
		} catch (ZipException ze) {
			// System.out.println("Incorrect password :(");
		} catch (Exception e){
			e.printStackTrace();
		}
    }
}
public static String getPassword(){
    String password3="";
if (pfound) {
    password3=password;
}
return password3;
}
    
    public static ArrayList<String> passwordMaker(ArrayList<String> arr){
        String password="";
        for(char ind1='a';ind1<='z';ind1++){
            for(char ind2='a';ind2<='z';ind2++){
                for(char ind3='a';ind3<='z';ind3++){
                    password=""+ind1+ind2+ind3;
                    arr.add(password);
                    
                    
                    
                }
            }
        }
        
        return arr;
    }
}

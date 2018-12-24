package schedulling.abstractions;

public class Identifier {
   public String gen(String Id, String pseudo){
      return Id+"%"+pseudo;
   }
   public String getPseudo(String input){
     return input.substring(input.indexOf("%")+1);
   };
    public String getId(String input){
        return input.substring(0,input.indexOf("%"));
    };


}

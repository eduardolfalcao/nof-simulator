import java.util.TreeMap;
 
public class TreeMapExample {
	
	
	
	
	
    public static void main(String[] args) {
        TreeMap<String, Integer> students = new TreeMap<String, Integer>();
        //Add Key/Value pairs
        students.put("Ed", 47);
        students.put("Alan", 34);
        students.put("Sheila", 65);
        students.put("Becca", 44);
 
        //Iterate over HashMap
        for(String key: students.keySet()){
            System.out.println(key  +" :: "+ students.get(key));
        }
        
        
        
        Integer valueEd = students.get("Ed");
        Integer valueTop = students.get(students.lastKey());
        
        System.out.println("###################");
        System.out.println(valueEd);
        System.out.println(valueTop);
    }
}
class Solution {
    public boolean wordPattern(String pattern, String s) {
        HashMap<Character,String> map1 = new HashMap<>();
        HashMap<String,Character> map2 = new HashMap<>();

        String[] words = s.split(" ");


        if(pattern.length() != words.length){
            return false;
        }

        for(int i =0; i < pattern.length();i++){
            char character = pattern.charAt(i);
            String word = words[i];

        

    if(map1.containsKey(character)){

        if(!map1.get(character).equals(word))
           return false;
        

    }  else {

         if(map2.containsKey(word)) {
                return false;
            }
    }

            map1.put(character,word);
            map2.put(word,character);
        }

        return true;
    

    

    
    }
        
    }  

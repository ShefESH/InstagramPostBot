package instagram.post.creator;

public class Starters {
    public enum Starter {
        EMAIL ("Dear "),
        DISGRAM ("Hi ");
    
        private final String name;       
    
        private Starter(String s) {
            name = s;
        }

        public boolean equalsName(String otherName) {
            return name.equals(otherName);
        }
    
        public String toString() {
           return this.name;
        }
    }
    
}

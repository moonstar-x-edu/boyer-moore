import java.util.HashMap;

public class StringSearch {

  public static void main(String[] args) {
    HashMap<Character, String> parsedArgs = parseArgs(args);
    validateArgs(parsedArgs);

    System.out.println(Naive.search(parsedArgs.get('p'), parsedArgs.get('t')));
    System.out.println(BoyerMoore.BadCharacter.search(parsedArgs.get('p'), parsedArgs.get('t')));
    System.out.println(BoyerMoore.GoodSuffix.search(parsedArgs.get('p'), parsedArgs.get('t')));
    System.out.println(BoyerMoore.Combined.search(parsedArgs.get('p'), parsedArgs.get('t')));
  }

  private static HashMap<Character, String> parseArgs(String[] args) {
    HashMap<Character, String> parsed = new HashMap<>();

    for (int i = 0; i < args.length; i++) {
      if (args[i].charAt(0) == '-') {
        parsed.put(args[i].charAt(1), args[++i]);
      }
    }

    return parsed;
  }

  private static void validateArgs(HashMap<Character, String> parsedArgs) {
    char[] keys = { 't', 'p' };

    for (char key : keys) {
      if (!parsedArgs.containsKey(key)) {
        throw new IllegalArgumentException("Argumento -" + key + " es requerido!");
      }
    }
  }
}

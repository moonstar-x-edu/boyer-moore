public class Naive {
  public static Result search(String pattern, String text) {
    Result result = new Result("Naive");
    result.initTimeOfStart();

    int m = pattern.length();
    int n = text.length();

    for (int i = 0; i < n; i++) {
      int j;
      for (j = 0; j < m; j++) {
        result.bumpComparisons();
        if (pattern.charAt(j) != text.charAt(i + j)) {
          break;
        }
      }

      if (j == m) {
        result.setIndexOf(i);
        break;
      }
    }

    return result;
  }
}

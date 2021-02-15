import java.util.HashMap;

public class BoyerMoore {

  // Código tomado de
  // https://algs4.cs.princeton.edu/53substring/BoyerMoore.java.html y adaptado
  // por mi para usar HashMap en lugar de un arreglo de 256 elementos.
  public static class BadCharacter {
    protected static void printTable(HashMap<Character, Integer> table) {
      StringBuilder builder = new StringBuilder();
      builder.append("{ ");
      table.forEach((k, v) -> {
        builder.append(k).append(":").append(v).append(" ");
      });
      builder.append("}");
      System.out.println(builder.toString());
    }

    protected static HashMap<Character, Integer> createShiftsTable(String pattern, int m) {
      HashMap<Character, Integer> table = new HashMap<>();

      for (int i = 0; i < m; i++) {
        table.put(pattern.charAt(i), i);
      }

      return table;
    }

    public static Result search(String pattern, String text) {
      Result result = new Result("Boyer-Moore: Bad Character");
      result.initTimeOfStart();

      int m = pattern.length();
      int n = text.length();
      HashMap<Character, Integer> shifts = createShiftsTable(pattern, m);
      int skip = 0;

      System.out.print("Tabla BC: ");
      printTable(shifts);

      for (int i = 0; i <= n - m; i += skip) {
        skip = 0;

        for (int j = m - 1; j >= 0; j--) {
          result.bumpComparisons();

          if (pattern.charAt(j) != text.charAt(i + j)) {
            skip = Math.max(1, j - shifts.getOrDefault(text.charAt(i + j), -1));
            break;
          }
        }

        if (skip == 0) {
          result.setIndexOf(i);
          break;
        }
      }

      return result;
    }
  }

  // Código tomado de
  // https://www.geeksforgeeks.org/boyer-moore-algorithm-good-suffix-heuristic/
  public static class GoodSuffix {
    protected static void printTable(int[] shift, String pattern) {
      StringBuilder builder = new StringBuilder();
      builder.append("{ ");
      for (int i = 0; i < pattern.length(); i++) {
        builder.append(pattern.charAt(i)).append(":").append(shift[i]).append(" ");
      }
      builder.append("*:").append(shift[shift.length - 1]).append(" }");
      System.out.println(builder.toString());
    }

    private static void strongSuffixCase1(int[] shift, int[] bpos, String pattern, int m) {
      int i = m;
      int j = m + 1;
      bpos[i] = j;

      while (i > 0) {
        while (j <= m && pattern.charAt(i - 1) != pattern.charAt(j - 1)) {
          if (shift[j] == 0) {
            shift[j] = j - i;
          }

          j = bpos[j];
        }

        i--;
        j--;
        bpos[i] = j;
      }
    }

    private static void strongSuffixCase2(int[] shift, int[] bpos, String pattern, int m) {
      int i;
      int j = bpos[0];

      for (i = 0; i <= m; i++) {
        if (shift[i] == 0) {
          shift[i] = j;
        }

        if (i == j) {
          j = bpos[j];
        }
      }
    }

    protected static int[] createShiftsTable(String pattern, int m) {
      int[] bpos = new int[m + 1];
      int[] shift = new int[m + 1];

      for (int i = 0; i < m + 1; i++) {
        bpos[i] = 0;
        shift[i] = 0;
      }

      strongSuffixCase1(shift, bpos, pattern, m);
      strongSuffixCase2(shift, bpos, pattern, m);

      return shift;
    }

    public static Result search(String pattern, String text) {
      Result result = new Result("Boyer-Moore: Good Suffix");
      result.initTimeOfStart();

      int n = text.length();
      int m = pattern.length();
      int[] shifts = createShiftsTable(pattern, m);
      int skip = 0;

      System.out.print("Tabla GS: ");
      printTable(shifts, pattern);

      for (int i = 0; i < n - m; i++) {
        skip = 0;

        for (int j = m - 1; j >= 0; j--) {
          result.bumpComparisons();

          if (pattern.charAt(j) != text.charAt(i + j)) {
            skip = shifts[j + 1];
            break;
          }
        }

        if (skip == 0) {
          result.setIndexOf(i);
          break;
        }
      }

      return result;
    }
  }

  public static class Combined {
    public static Result search(String pattern, String text) {
      Result result = new Result("Boyer-Moore: Combined");
      result.initTimeOfStart();

      int m = pattern.length();
      int n = text.length();
      HashMap<Character, Integer> badCharacterShifts = BadCharacter.createShiftsTable(pattern, m);
      int[] goodSuffixShifts = GoodSuffix.createShiftsTable(pattern, m);
      int skip = 0;

      for (int i = 0; i <= n - m; i += skip) {
        skip = 0;

        for (int j = m - 1; j >= 0; j--) {
          result.bumpComparisons();

          if (pattern.charAt(j) != text.charAt(i + j)) {
            skip = Math.max(Math.max(1, j - badCharacterShifts.getOrDefault(text.charAt(i + j), -1)),
                goodSuffixShifts[j + 1]);
            break;
          }
        }

        if (skip == 0) {
          result.setIndexOf(i);
          break;
        }
      }

      return result;
    }
  }
}

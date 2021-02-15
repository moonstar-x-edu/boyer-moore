public class Result {
  private String name;
  private int comparisons;
  private int indexOf;

  private long timeOfStart;

  public Result(String name, int comparisons, int indexOf) {
    this.name = name;
    this.comparisons = comparisons;
    this.indexOf = indexOf;
    this.timeOfStart = 0;
  }

  public Result(String name) {
    this.name = name;
    this.comparisons = 0;
    this.indexOf = -1;
  }

  public void initTimeOfStart() {
    timeOfStart = System.currentTimeMillis();
  }

  public void bumpComparisons() {
    comparisons++;
  }

  public String getName() {
    return name;
  }

  public int getComparisons() {
    return comparisons;
  }

  public int getIndexOf() {
    return indexOf;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setComparisons(int comparisons) {
    this.comparisons = comparisons;
  }

  public void setIndexOf(int indexOf) {
    this.indexOf = indexOf;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append(name).append(": ").append("{ Comparaciones: ").append(comparisons).append(", Tiempo de ejecución: ")
        .append(System.currentTimeMillis() - timeOfStart).append("ms").append(", Patrón encontrado: ");

    if (indexOf < 0) {
      builder.append("No");
    } else {
      builder.append("Si");
      builder.append(", Índice del match: ");
      builder.append(indexOf);
    }

    builder.append(" }");

    return builder.toString();
  }
}

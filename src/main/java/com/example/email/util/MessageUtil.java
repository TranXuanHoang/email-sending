package com.example.email.util;

/**
 * Provides supporting methods in formating String messages.
 * 
 * @author hoang.tran
 */
public class MessageUtil {
  /**
   * Formats a given input pattern including variable(s) into a new String that
   * each variable's value is substituted. For example:
   * <code>
   *   String arg1 = "I bought";
   *   int arg2 = 3;
   *   
   *   format("%arg1% %arg2% apples.", arg1, arg2);
   * </code>
   * will return <code>"I bought 3 apples."</code>.
   * 
   * @param pattern
   *          the pattern to be formatted.
   * @param args
   *          value(s) of variable(s) to be substituted into the pattern.
   * @return a String that is the result of substituting variable(s) in the
   *         pattern with it(their) value(s).
   */
  public static String format(String pattern, Object... args) {
    int occurence = frequency(pattern);

    if (occurence % 2 != 0) {
      return String.format("Format of the pattern '%s' is not correct.",
          pattern);
    }

    String[] tokens = pattern.split("%");
    String result = "";

    for (int i = 0, argsIndex = 0; i < tokens.length; i++) {
      result += (i % 2 == 0) ? tokens[i] : args[argsIndex++];
    }

    return result;
  }

  /**
   * Counts the frequency of the delimiter (% percent character) appearing in
   * the pattern.
   * 
   * @param pattern
   *          the pattern that will be used to count the frequency of the
   *          delimiter.
   * @return frequency of the delimiter (here, the delimiter is %)
   */
  private static int frequency(String pattern) {
    byte[] chars = pattern.getBytes();
    byte delimiter = '%';

    int frequency = 0;

    for (int i = 0; i < chars.length; i++) {
      if (delimiter == chars[i]) {
        frequency++;
      }
    }

    return frequency;
  }
}
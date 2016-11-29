
import java.util.Scanner;
import java.util.regex.*;

/**
 *
 * @author zacke
 */
public class PuzzleTranslater {

    public static final Pattern match = Pattern.compile("value=\"(\\d)\"");

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        while (input.hasNext()) {
            String next = input.nextLine().trim();
            if (next.equals("</tr>")) {
                System.out.println();
            } else if (next.contains("input")) {
                Matcher m = match.matcher(next);
                if (m.find()) {
                    System.out.print(m.group(1));
                } else {
                    System.out.print("0");
                }
                System.out.print(" ");
            }
        }

    }
}

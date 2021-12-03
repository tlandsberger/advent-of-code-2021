import java.util.Map;
import java.util.function.BiConsumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @see <a href=https://adventofcode.com/2021/day/2>Advent of Code 2021 - Day 2</a>
 * @author Tim Landsberger
 */
public class Day2Dive {

  private static final Map<String, BiConsumer<Navigator, Integer>> ACTIONS =
      Map.of(
          "forward", Navigator::moveForward,
          "down", Navigator::moveDown,
          "up", Navigator::moveUp);

  private static final Pattern COMMAND_REGEX =
      Pattern.compile("(" + String.join("|", ACTIONS.keySet()) + ") (\\d)");

  private static final String[] commands_example = {"forward 5", "down 5", "forward 8", "up 3", "down 8", "forward 2"};
  private static final String[] commands = {"forward 8", "down 9", "forward 2", "down 1", "forward 9", "forward 7", "forward 5", "up 3", "up 3", "down 5", "forward 2", "down 8", "down 3", "forward 6", "down 2", "down 4", "down 7", "down 7", "forward 2", "down 6", "down 8", "down 2", "down 8", "up 9", "down 8", "forward 8", "down 5", "up 4", "forward 4", "forward 4", "forward 3", "down 9", "forward 8", "up 3", "forward 2", "forward 3", "forward 6", "down 7", "down 2", "forward 4", "forward 7", "forward 6", "up 5", "up 1", "forward 4", "down 9", "up 6", "forward 6", "up 9", "forward 1", "down 9", "forward 4", "down 3", "forward 7", "forward 5", "down 1", "up 9", "down 9", "forward 6", "forward 1", "down 5", "down 6", "forward 6", "forward 3", "up 4", "up 9", "down 3", "forward 6", "up 4", "up 6", "forward 4", "down 1", "down 2", "up 9", "forward 8", "down 2", "down 3", "down 4", "up 3", "forward 3", "forward 4", "down 4", "forward 7", "forward 9", "down 7", "forward 6", "forward 2", "up 6", "forward 7", "forward 9", "down 5", "forward 6", "up 9", "forward 6", "forward 2", "forward 6", "up 3", "down 1", "forward 5", "down 3", "forward 7", "down 4", "forward 1", "forward 7", "down 1", "up 2", "down 7", "down 6", "forward 8", "forward 2", "forward 1", "forward 9", "down 3", "forward 3", "down 6", "up 8", "up 3", "forward 1", "forward 3", "forward 7", "down 9", "forward 7", "forward 3", "up 6", "forward 4", "down 9", "forward 2", "down 4", "up 2", "down 1", "up 1", "down 6", "forward 1", "up 6", "up 7", "forward 3", "forward 3", "forward 2", "forward 1", "down 7", "forward 9", "down 5", "down 9", "up 9", "forward 3", "forward 8", "down 3", "forward 9", "forward 4", "down 3", "up 4", "up 8", "up 4", "down 8", "down 6", "down 5", "forward 2", "up 6", "up 1", "up 9", "down 4", "up 8", "forward 6", "down 1", "forward 7", "up 2", "forward 6", "up 2", "down 6", "down 5", "forward 2", "down 2", "down 1", "forward 8", "forward 1", "up 9", "forward 3", "down 6", "forward 2", "forward 8", "down 3", "forward 3", "forward 4", "forward 7", "forward 2", "up 4", "forward 8", "forward 1", "forward 9", "down 3", "down 1", "forward 8", "down 5", "down 3", "forward 5", "down 7", "down 1", "forward 8", "forward 2", "forward 4", "forward 8", "forward 6", "down 1", "forward 5", "forward 9", "forward 9", "up 9", "forward 9", "forward 4", "down 5", "down 2", "down 3", "forward 8", "forward 9", "up 8", "up 1", "up 6", "forward 7", "up 9", "forward 2", "forward 6", "up 6", "forward 3", "up 4", "forward 1", "down 4", "up 6", "down 5", "forward 7", "forward 6", "down 3", "down 4", "forward 3", "down 6", "down 1", "forward 5", "forward 7", "up 8", "forward 4", "up 7", "down 4", "forward 3", "down 7", "forward 7", "forward 4", "forward 1", "forward 8", "up 5", "up 6", "forward 5", "forward 3", "down 6", "forward 8", "forward 2", "forward 7", "down 7", "down 8", "down 3", "up 3", "down 1", "down 1", "forward 6", "forward 9", "forward 4", "forward 9", "forward 6", "down 1", "forward 9", "down 6", "down 8", "up 5", "down 8", "forward 4", "forward 2", "up 6", "down 9", "forward 6", "down 9", "down 6", "down 6", "forward 2", "up 8", "down 7", "down 2", "forward 2", "forward 2", "down 1", "up 8", "down 5", "forward 9", "forward 5", "forward 8", "forward 8", "forward 1", "down 2", "down 7", "up 5", "forward 9", "forward 4", "forward 4", "forward 6", "down 7", "up 5", "forward 5", "up 9", "down 7", "down 4", "down 9", "forward 7", "up 4", "down 1", "down 6", "up 2", "up 6", "down 2", "forward 9", "down 3", "forward 3", "forward 4", "forward 1", "up 2", "forward 6", "down 3", "forward 2", "down 9", "forward 8", "forward 3", "forward 2", "up 5", "forward 3", "forward 1", "down 8", "up 2", "up 4", "up 5", "down 3", "down 6", "down 1", "forward 4", "up 3", "down 1", "down 4", "up 6", "forward 8", "down 5", "down 7", "down 7", "forward 9", "forward 9", "down 2", "up 2", "down 5", "forward 5", "forward 4", "down 7", "forward 4", "down 2", "forward 2", "forward 4", "up 8", "down 8", "up 4", "forward 2", "forward 2", "up 8", "forward 2", "down 3", "down 7", "down 9", "up 6", "up 3", "forward 2", "forward 3", "up 8", "forward 6", "up 8", "up 1", "down 6", "down 8", "up 9", "down 1", "up 8", "forward 9", "forward 4", "forward 9", "forward 8", "forward 1", "down 6", "down 7", "up 5", "down 1", "forward 9", "down 9", "forward 7", "down 5", "forward 7", "down 1", "down 4", "down 4", "forward 6", "forward 1", "up 4", "up 2", "forward 7", "down 6", "down 2", "down 3", "up 7", "up 1", "down 6", "down 6", "down 8", "down 8", "forward 5", "forward 1", "forward 5", "up 8", "forward 8", "up 8", "forward 1", "down 9", "forward 1", "up 7", "up 3", "down 1", "down 9", "up 2", "down 3", "down 2", "forward 9", "up 9", "up 1", "up 5", "forward 8", "down 3", "down 7", "forward 7", "down 8", "down 5", "down 5", "down 4", "down 7", "forward 6", "forward 6", "forward 4", "forward 6", "forward 3", "up 5", "forward 2", "down 7", "forward 1", "down 4", "down 7", "down 7", "forward 4", "down 2", "up 4", "forward 2", "up 2", "up 3", "down 5", "forward 3", "down 2", "forward 5", "down 2", "down 1", "up 4", "up 5", "up 9", "forward 1", "forward 9", "down 9", "up 8", "forward 9", "forward 7", "down 9", "down 2", "down 9", "forward 9", "forward 7", "up 7", "forward 6", "up 6", "forward 5", "forward 6", "down 4", "forward 8", "forward 5", "forward 2", "up 4", "down 4", "forward 1", "down 2", "up 9", "up 7", "up 2", "up 3", "down 9", "forward 4", "up 6", "forward 5", "forward 5", "forward 9", "forward 1", "down 6", "forward 8", "down 5", "up 3", "up 1", "down 2", "up 4", "down 1", "down 9", "forward 8", "down 2", "forward 7", "down 7", "up 5", "down 7", "down 3", "forward 2", "forward 2", "forward 4", "down 9", "down 6", "down 9", "forward 6", "down 6", "down 9", "forward 1", "down 1", "forward 8", "down 4", "up 6", "down 2", "forward 9", "down 3", "up 3", "down 2", "up 2", "up 5", "down 8", "forward 1", "forward 3", "down 8", "up 3", "up 3", "up 3", "forward 2", "up 3", "down 4", "down 1", "down 7", "forward 3", "down 3", "down 8", "down 9", "forward 9", "forward 3", "up 4", "forward 8", "forward 8", "up 7", "up 3", "forward 6", "down 9", "up 1", "forward 2", "down 6", "up 6", "forward 2", "down 6", "down 3", "up 7", "forward 6", "down 6", "down 1", "down 5", "forward 6", "up 2", "down 2", "down 3", "down 1", "up 9", "forward 6", "up 2", "forward 2", "down 6", "up 3", "up 4", "forward 8", "up 8", "up 4", "down 7", "down 4", "up 9", "forward 9", "up 6", "forward 5", "forward 7", "forward 2", "forward 8", "down 7", "down 5", "down 4", "up 3", "forward 7", "down 2", "forward 5", "forward 9", "forward 4", "forward 7", "forward 8", "up 6", "down 1", "forward 3", "forward 9", "forward 1", "down 8", "down 1", "down 3", "down 1", "down 1", "up 3", "down 5", "up 1", "down 8", "down 2", "down 8", "down 3", "forward 2", "forward 8", "forward 4", "down 8", "down 6", "forward 8", "down 7", "forward 8", "forward 2", "forward 6", "forward 6", "forward 4", "up 2", "forward 3", "up 8", "forward 7", "forward 4", "down 8", "down 3", "down 4", "up 8", "forward 5", "forward 3", "up 4", "up 2", "down 6", "forward 4", "up 8", "up 3", "up 8", "down 3", "up 1", "forward 2", "down 4", "down 4", "down 9", "down 5", "forward 9", "up 6", "up 5", "down 8", "down 6", "down 7", "forward 8", "forward 4", "up 4", "forward 1", "down 4", "up 4", "down 9", "up 6", "down 9", "up 3", "forward 4", "down 1", "down 5", "down 5", "up 1", "down 8", "down 9", "down 1", "forward 4", "down 8", "down 6", "down 1", "forward 2", "down 5", "up 1", "up 1", "down 1", "down 3", "down 3", "down 8", "down 6", "down 5", "down 3", "up 3", "forward 5", "down 7", "down 7", "forward 2", "forward 6", "forward 1", "down 8", "forward 2", "up 2", "forward 2", "down 2", "forward 7", "down 7", "down 9", "up 2", "up 2", "down 3", "forward 1", "down 1", "forward 5", "forward 4", "down 4", "forward 6", "forward 2", "forward 7", "forward 2", "forward 8", "down 4", "up 3", "down 3", "up 9", "down 7", "up 8", "down 1", "down 8", "up 9", "down 6", "up 5", "up 8", "down 2", "down 3", "forward 1", "down 5", "down 5", "forward 8", "down 9", "forward 7", "forward 8", "down 1", "down 2", "up 8", "down 2", "up 5", "down 3", "forward 5", "forward 6", "up 4", "up 3", "forward 5", "forward 1", "down 2", "forward 2", "down 9", "up 7", "down 2", "up 8", "forward 2", "forward 2", "up 4", "forward 5", "down 4", "up 6", "up 8", "forward 9", "up 1", "forward 9", "forward 7", "forward 3", "forward 1", "down 1", "forward 5", "forward 2", "forward 6", "forward 6", "forward 3", "forward 7", "up 7", "down 6", "down 2", "up 5", "down 5", "up 2", "forward 7", "forward 2", "down 9", "up 4", "down 5", "down 3", "forward 7", "down 2", "forward 9", "forward 6", "down 3", "down 3", "forward 6", "down 9", "forward 7", "up 5", "up 6", "down 3", "up 4", "forward 4", "up 3", "down 8", "down 9", "down 9", "down 4", "up 1", "up 2", "down 3", "forward 4", "forward 5", "down 1", "up 3", "down 8", "down 7", "forward 2", "up 4", "down 8", "forward 1", "forward 6", "up 8", "down 2", "down 6", "forward 5", "up 8", "forward 7", "down 9", "forward 9", "down 3", "forward 9", "forward 1", "forward 6", "up 7", "forward 1", "down 5", "down 8", "down 3", "forward 5", "forward 6", "down 7", "forward 4", "down 6", "forward 4", "up 4", "down 4", "forward 4", "down 9", "forward 3", "down 6", "forward 3", "down 3", "up 3", "forward 3", "down 4", "down 4", "down 4", "down 5", "forward 5", "down 9", "down 7", "down 1", "forward 5", "forward 3", "forward 8", "down 5", "forward 4", "forward 3", "down 8", "forward 1", "forward 1", "down 6", "forward 4", "down 5", "forward 8", "down 7", "forward 6", "down 7", "up 2", "forward 6", "down 7", "down 3", "down 6", "up 2", "forward 8", "forward 5", "down 6", "forward 5", "up 7", "forward 8", "down 1", "forward 2", "up 7", "down 4", "up 5", "forward 5", "forward 4", "forward 3", "down 1", "forward 8", "up 9", "down 2", "up 4", "forward 7", "up 6", "forward 3", "down 8", "forward 4", "down 2", "down 9", "forward 8", "down 5", "forward 3", "down 9", "up 1", "forward 6", "up 9", "down 7", "forward 5", "forward 3", "down 8", "down 6", "up 3", "down 5", "forward 7", "up 2", "forward 4", "up 1", "down 8", "down 7", "forward 3", "forward 1", "down 3", "down 8", "forward 7", "up 2", "down 7", "up 6", "down 7", "down 1", "down 3", "up 6", "forward 2", "down 5", "forward 1", "forward 7", "forward 2", "down 8", "down 8", "down 7", "forward 7", "forward 5", "forward 1", "forward 7", "forward 3", "down 2", "up 8", "up 4", "up 6", "down 2", "down 1", "down 5", "down 3", "forward 2", "up 1", "forward 8", "up 8", "forward 7", "up 1", "down 7", "forward 7", "forward 5", "forward 3", "up 4", "down 5", "down 5", "forward 3", "forward 9", "down 9", "up 6", "up 7", "down 7", "forward 7"};

  public static void main(String[] args) {
    System.out.println(calcPosition(commands, new Navigator1()));
    System.out.println(calcPosition(commands, new Navigator2()));
  }

  private static int calcPosition(String[] commands, Navigator navigator) {
    for (String command : commands) {
      Matcher m = COMMAND_REGEX.matcher(command);
      if (!m.matches()) throw new IllegalArgumentException();
      ACTIONS.get(m.group(1)).accept(navigator, Integer.parseInt(m.group(2)));
    }
    return navigator.getResult();
  }

  private interface Navigator {
    void moveForward(int value);

    void moveDown(int value);

    void moveUp(int value);

    int getResult();
  }

  private static class Navigator1 implements Navigator {
    int horizontal = 0;
    int depth = 0;

    @Override
    public void moveForward(int value) {
      horizontal += value;
    }

    @Override
    public void moveDown(int value) {
      depth += value;
    }

    @Override
    public void moveUp(int value) {
      depth -= value;
    }

    @Override
    public int getResult() {
      return horizontal * depth;
    }
  }

  private static class Navigator2 extends Navigator1 {
    int aim = 0;

    @Override
    public void moveForward(int value) {
      super.moveForward(value);
      depth += aim * value;
    }

    @Override
    public void moveDown(int value) {
      aim += value;
    }

    @Override
    public void moveUp(int value) {
      aim -= value;
    }
  }
}

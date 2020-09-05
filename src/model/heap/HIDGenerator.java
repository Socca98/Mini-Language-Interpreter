package model.heap;

public class HIDGenerator {
    private static int i = 1;
    public static int getID()
    {
        return i++;
    }
}

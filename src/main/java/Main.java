public class Main {

    public static void main(String[] args) {
        RangeSet set = new RangeSet();

        set.add(2);
        set.addRange(10, 100_000);
        set.addRange(1_000_000, 2_000_000);
        set.remove(1_500_000);

        set.contains(10); //true
        set.contains(500); //true
        set.contains(99_000); //true
        set.contains(1_500_001); //true
        set.contains(2); //true

        set.contains(3); //false
        set.contains(1_500_000); //false

        for (Integer value : set) {
            //do something with value
        }
    }
}

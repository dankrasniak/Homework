# Homework

Create a class RangeSet which implements java.util.Set<Integer>
This class should be optimized to keep ranges of integer values, not individual 
in order to efficiently handle long ranges (e.g. keep milion integers).
We would like to be able to use it like this:


```
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
```
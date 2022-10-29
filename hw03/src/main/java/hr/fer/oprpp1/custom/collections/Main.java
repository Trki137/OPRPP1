package hr.fer.oprpp1.custom.collections;



public class Main {
    public static void main(String[] args) {
        SimpleHashtable<String,Integer> examMarks = new SimpleHashtable<>(2);
        examMarks.put("Ivana", 2);
        examMarks.put("Ante", 2);
        examMarks.put("Jasna", 2);
        examMarks.put("Kristina", 5);
        examMarks.put("Ivana", 5); // overwrites old grade for Ivana
        //System.out.println(examMarks);
        /*for(SimpleHashtable.TableEntry<String,Integer> pair : examMarks) {
            System.out.printf("%s => %d%n", pair.getKey(), pair.getValue());
        }*/

        for(SimpleHashtable.TableEntry<String,Integer> pair1 : examMarks) {
            for(SimpleHashtable.TableEntry<String,Integer> pair2 : examMarks) {
                System.out.printf(
                        "(%s => %d) - (%s => %d)%n",
                        pair1.getKey(), pair1.getValue(),
                        pair2.getKey(), pair2.getValue()
                        );
            }
        }
        /*Iterator<SimpleHashtable.TableEntry<String,Integer>> iter = examMarks.iterator();
        while(iter.hasNext()) {
            SimpleHashtable.TableEntry<String,Integer> pair = iter.next();
            if(pair.getKey().equals("Ivana")) {
                iter.remove(); // sam iterator kontrolirano uklanja trenutni element
            }
        }
        System.out.println(examMarks);*/
/*
       Iterator<SimpleHashtable.TableEntry<String,Integer>> iter = examMarks.iterator();
        while(iter.hasNext()) {
            SimpleHashtable.TableEntry<String,Integer> pair = iter.next();
            if(pair.getKey().equals("Ivana")) {
                iter.remove();
                iter.remove();
            }
        }*/

        /*
        Iterator<SimpleHashtable.TableEntry<String,Integer>> iter = examMarks.iterator();
        while(iter.hasNext()) {
            SimpleHashtable.TableEntry<String,Integer> pair = iter.next();
            System.out.printf("%s => %d%n", pair.getKey(), pair.getValue());
            iter.remove();
        }

        System.out.println(examMarks);
        System.out.printf("Veličina: %d%n", examMarks.size());*/

    }


}

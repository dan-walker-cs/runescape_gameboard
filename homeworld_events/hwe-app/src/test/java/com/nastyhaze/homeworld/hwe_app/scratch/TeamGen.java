package com.nastyhaze.homeworld.hwe_app.scratch;

import java.util.*;
import java.util.stream.IntStream;

public class TeamGen {

    public static record Gamer(
        String name,
        double weight
    ){}

    public static void main(String[] args) {
        List<Gamer> gamers = new ArrayList<>(
            List.of(
                new Gamer("Tenzo", 5),
                new Gamer("Kwanzilla", 5),
                new Gamer("nasty_haze", 4),
                new Gamer("Osmium Oats", 4),
                new Gamer("Taxol", 3),
                new Gamer("DoubleUQ", 3),
                new Gamer("TheATFisgay", 2),
                new Gamer("Runite Egg", 1.5),
                new Gamer("Slorko", 1),
                new Gamer("AddyAddicted", 1)
            )
        );

        List<SplitResult> results = generateConstrainedSplits(gamers)
            .stream()
            .filter(r -> r.diff < 1)
            .toList();

//        List<SplitResult> results = generateSplits(gamers)
//            .stream()
//            .filter(r -> r.diff == 1)
//            .toList();

        for (int i = 0; i < results.size(); i++) {
            SplitResult r = results.get(i);
            System.out.printf(
                "Option %d: \nSet1: %s (w=%f), \nSet2: %s (w=%f) → diff=%f%n",
                i, names(r.set1()), r.weight1(), names(r.set2()), r.weight2(), r.diff()
            );
        }
    }

    public static record SplitResult(List<Gamer> set1, List<Gamer> set2, double weight1, double weight2, double diff) {}

    private static String names(List<Gamer> Gamers) {
        return Gamers.stream().map(Gamer::name).toList().toString();
    }

    private static List<SplitResult> generateConstrainedSplits(List<Gamer> Gamers) {
        if (Gamers.size() != 10) throw new IllegalArgumentException("Expected exactly 10 Gamerects.");
        List<Integer> fives = new ArrayList<>(2);
        for (int i = 0; i < Gamers.size(); i++) if (Gamers.get(i).weight() == 5) fives.add(i);
        if (fives.size() != 2) throw new IllegalArgumentException("Expected exactly two Gamerects with weight == 5.");

        int fiveA = Math.min(fives.get(0), fives.get(1));
        int fiveB = Math.max(fives.get(0), fives.get(1));

        int[] rem = IntStream.range(0, Gamers.size()).filter(i -> i != fiveA && i != fiveB).toArray();

        int k = 4, m = rem.length; // m == 8
        int[] comb = IntStream.range(0, k).toArray();

        List<SplitResult> out = new ArrayList<>(70);
        while (true) {
            boolean[] used = new boolean[Gamers.size()];
            List<Gamer> set1 = new ArrayList<>(5);
            List<Gamer> set2 = new ArrayList<>(5);

            set1.add(Gamers.get(fiveA));
            used[fiveA] = true;
            for (int idx : comb) {
                int GamerIdx = rem[idx];
                set1.add(Gamers.get(GamerIdx));
                used[GamerIdx] = true;
            }
            set2.add(Gamers.get(fiveB));
            used[fiveB] = true;
            for (int i = 0; i < Gamers.size(); i++) if (!used[i]) set2.add(Gamers.get(i));

            double w1 = set1.stream().mapToDouble(Gamer::weight).sum();
            double w2 = set2.stream().mapToDouble(Gamer::weight).sum();
            out.add(new SplitResult(set1, set2, w1, w2, Math.abs(w1 - w2)));

            int i = k - 1;
            while (i >= 0 && comb[i] == i + m - k) i--;
            if (i < 0) break;
            comb[i]++;
            for (int j = i + 1; j < k; j++) comb[j] = comb[j - 1] + 1;
        }
        return out;
    }

    private static List<SplitResult> generateSplits(List<Gamer> Gamers) {
        List<SplitResult> results = new ArrayList<>();
        int n = Gamers.size();
        int k = n / 2;
        int[] indices = IntStream.range(0, k).toArray();

        while (true) {
            List<Gamer> set1 = new ArrayList<>();
            List<Gamer> set2 = new ArrayList<>(Gamers);
            for (int i = 0; i < k; i++) {
                set1.add(Gamers.get(indices[i]));
            }
            set2.removeAll(set1);

            double w1 = set1.stream().mapToDouble(Gamer::weight).sum();
            double w2 = set2.stream().mapToDouble(Gamer::weight).sum();
            results.add(new SplitResult(set1, set2, w1, w2, Math.abs(w1 - w2)));

            int i = k - 1;
            while (i >= 0 && indices[i] == i + n - k) i--;
            if (i < 0) break;
            indices[i]++;
            for (int j = i + 1; j < k; j++) indices[j] = indices[j - 1] + 1;
        }
        return results;
    }
}
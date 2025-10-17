package com.nastyhaze.homeworld.hwe_app.scratch;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TeamGen {

    public static record Gamer(String name, double weight) {}

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
                new Gamer("Runite Egg", 1),
                new Gamer("Slorko", 1),
                new Gamer("AddyAddicted", 1)
            )
        );

        List<SplitResult> results = generateConstrainedSplits(gamers)
            .stream()
            .filter(r -> r.diff <= 1)
            .toList();

        for (int i = 0; i < results.size(); i++) {
            SplitResult r = results.get(i);
            System.out.printf(
                "Option %d: \nSet1: %s (w=%f), \nSet2: %s (w=%f) → diff=%f%n",
                i, names(r.set1()), r.weight1(), names(r.set2()), r.weight2(), r.diff()
            );
        }
    }

    public static record SplitResult(List<Gamer> set1, List<Gamer> set2, double weight1, double weight2, double diff) {}

    private static String names(List<Gamer> gamers) {
        return gamers.stream().map(Gamer::name).toList().toString();
    }

    /**
     * Constraints:
     * 1) Two weight-5 gamers are split across teams.
     * 2) "Tenzo" and "DoubleUQ" are on the SAME team.
     * 3) "Kwanzilla", "nasty_haze", and "Osmium Oats" are NOT all on the same team.
     */
    private static List<SplitResult> generateConstrainedSplits(List<Gamer> gamers) {
        if (gamers.size() != 10) throw new IllegalArgumentException("Expected exactly 10 gamers.");

        // Two 5-weights
        List<Integer> fives = new ArrayList<>(2);
        for (int i = 0; i < gamers.size(); i++) if (gamers.get(i).weight() == 5) fives.add(i);
        if (fives.size() != 2) throw new IllegalArgumentException("Expected exactly two gamers with weight == 5.");

        // Indices for required names
        int tenzoIdx = indexOfName(gamers, "Tenzo");
        int doubleUqIdx = indexOfName(gamers, "DoubleUQ");
        int kwanzillaIdx = indexOfName(gamers, "Kwanzilla");
        int nastyIdx = indexOfName(gamers, "nasty_haze");
        int oatsIdx = indexOfName(gamers, "Osmium Oats");

        if (tenzoIdx < 0 || doubleUqIdx < 0 || kwanzillaIdx < 0 || nastyIdx < 0 || oatsIdx < 0)
            throw new IllegalArgumentException("One or more required names not found.");

        // Other 5-weight must oppose Tenzo (with current data, that's Kwanzilla)
        Integer otherFiveIdx = fives.get(0).equals(tenzoIdx) ? fives.get(1) : fives.get(0);

        // Remaining pool EXCLUDING: Tenzo, DoubleUQ, other 5-weight
        int[] rem = IntStream.range(0, gamers.size())
            .filter(i -> i != tenzoIdx && i != doubleUqIdx && i != otherFiveIdx)
            .toArray();

        // We fixed 2 in Set1 (Tenzo + DoubleUQ) and 1 in Set2 (other 5-weight).
        // Choose 3 of the remaining for Set1.
        int k = 3, m = rem.length; // m == 7
        int[] comb = IntStream.range(0, k).toArray();

        List<SplitResult> out = new ArrayList<>();
        while (true) {
            boolean[] used = new boolean[gamers.size()];
            List<Gamer> set1 = new ArrayList<>(5);
            List<Gamer> set2 = new ArrayList<>(5);

            // Force Tenzo and DoubleUQ together (Set1)
            set1.add(gamers.get(tenzoIdx));     used[tenzoIdx] = true;
            set1.add(gamers.get(doubleUqIdx));  used[doubleUqIdx] = true;

            // Choose remaining 3 for Set1
            for (int idx : comb) {
                int gi = rem[idx];
                set1.add(gamers.get(gi));
                used[gi] = true;
            }

            // Force the other 5-weight to Set2
            set2.add(gamers.get(otherFiveIdx)); used[otherFiveIdx] = true;

            // Fill rest of Set2
            for (int i = 0; i < gamers.size(); i++) if (!used[i]) set2.add(gamers.get(i));

            // ----- NEW CONSTRAINT CHECK -----
            // "Kwanzilla", "nasty_haze", and "Osmium Oats" cannot all be on the same team
            if (teamHasAll(set1, "Kwanzilla", "nasty_haze", "Osmium Oats")
                || teamHasAll(set2, "Kwanzilla", "nasty_haze", "Osmium Oats")) {
                // Skip this split
            } else {
                double w1 = set1.stream().mapToDouble(Gamer::weight).sum();
                double w2 = set2.stream().mapToDouble(Gamer::weight).sum();
                out.add(new SplitResult(set1, set2, w1, w2, Math.abs(w1 - w2)));
            }
            // --------------------------------

            // Next combination (3-of-7)
            int i = k - 1;
            while (i >= 0 && comb[i] == i + m - k) i--;
            if (i < 0) break;
            comb[i]++;
            for (int j = i + 1; j < k; j++) comb[j] = comb[j - 1] + 1;
        }
        return out;
    }

    private static boolean teamHasAll(List<Gamer> team, String... names) {
        Set<String> present = team.stream().map(Gamer::name).collect(Collectors.toSet());
        for (String n : names) if (!present.contains(n)) return false;
        return true;
    }

    private static int indexOfName(List<Gamer> gamers, String name) {
        for (int i = 0; i < gamers.size(); i++) {
            if (gamers.get(i).name().equals(name)) return i;
        }
        return -1;
    }

    // (Unchanged utility if you keep it)
    private static List<SplitResult> generateSplits(List<Gamer> gamers) {
        List<SplitResult> results = new ArrayList<>();
        int n = gamers.size();
        int k = n / 2;
        int[] indices = IntStream.range(0, k).toArray();

        while (true) {
            List<Gamer> set1 = new ArrayList<>();
            List<Gamer> set2 = new ArrayList<>(gamers);
            for (int i = 0; i < k; i++) set1.add(gamers.get(indices[i]));
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
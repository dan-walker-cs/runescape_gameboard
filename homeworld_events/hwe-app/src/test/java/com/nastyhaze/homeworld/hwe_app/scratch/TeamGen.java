package com.nastyhaze.homeworld.hwe_app.scratch;

import java.util.*;
import java.util.stream.Collectors;

public class TeamGen {

    public record Gamer(String name, double weight) {}

    // Constraint names
    private static final String TENZO = "Tenzo";
    private static final String DOUBLE_UQ = "DoubleUQ";
    private static final String KWANZILLA = "Kwanzilla";
    private static final String NASTY_HAZE = "nasty_haze";
    private static final String OSMIUM_OATS = "Osmium Oats";
    private static final String SLORKO = "Slorko";
    private static final String RUNITE_EGG = "Runite Egg";

    private static final int TEAM_SIZE = 5;     // Each team of 5
    private static final int TARGET_SUM = 15;   // Total weight 30 ⇒ 15 each

    public static void main(String[] args) {
        List<Gamer> gamers = new ArrayList<>(
            List.of(
                new Gamer("Tenzo", 5),
                new Gamer("Kwanzilla", 5),
                new Gamer("nasty_haze", 5),
                new Gamer("Osmium Oats", 4),
                new Gamer("Taxol", 3),
                new Gamer("DoubleUQ", 3),
                new Gamer("TheATFisgay", 2),
                new Gamer("Runite Egg", 1),
                new Gamer("Slorko", 1),
                new Gamer("AddyAddicted", 1)
            )
        );

        // Sort heavy-first to prune faster
        gamers.sort(Comparator.comparingDouble(Gamer::weight).reversed());

        // Fix Tenzo to Team 1 to avoid mirrored duplicates
        Gamer tenzo = gamers.stream()
            .filter(g -> g.name().equals(TENZO))
            .findFirst().orElseThrow();
        Set<Gamer> team1 = new HashSet<>();
        Set<Gamer> team2 = new HashSet<>();
        team1.add(tenzo);

        List<Gamer> order = new ArrayList<>(gamers);
        order.remove(tenzo);

        List<TeamSplit> solutions = new ArrayList<>();
        backtrack(order, 0, team1, team2,
            (int) tenzo.weight(), 0,
            solutions);

        System.out.println("Total solutions: " + solutions.size());
        for (int i = 0; i < solutions.size(); i++) {
            TeamSplit s = solutions.get(i);
            System.out.println("\nOption " + (i + 1) + ":");
            printTeam("Team 1", s.team1);
            printTeam("Team 2", s.team2);
            int sum1 = sum(s.team1);
            int sum2 = sum(s.team2);
            System.out.println("Weight difference: " + Math.abs(sum1 - sum2));
        }
    }

    private static void backtrack(
        List<Gamer> gamers, int idx,
        Set<Gamer> t1, Set<Gamer> t2,
        int sum1, int sum2,
        List<TeamSplit> out
    ) {
        // Size pruning
        if (t1.size() > TEAM_SIZE || t2.size() > TEAM_SIZE) return;
        int remaining = gamers.size() - idx;
        if (t1.size() + remaining < TEAM_SIZE) return;
        if (t2.size() + remaining < TEAM_SIZE) return;

        // Weight pruning
        if (sum1 > TARGET_SUM || sum2 > TARGET_SUM) return;

        if (idx == gamers.size()) {
            if (t1.size() == TEAM_SIZE && t2.size() == TEAM_SIZE &&
                sum1 == TARGET_SUM && sum2 == TARGET_SUM &&
                constraintsHold(t1, t2)) {
                out.add(new TeamSplit(new ArrayList<>(t1), new ArrayList<>(t2)));
            }
            return;
        }

        Gamer g = gamers.get(idx);
        int w = (int) g.weight();

        // Try Team 1
        if (t1.size() < TEAM_SIZE) {
            t1.add(g);
            if (partialConstraintsHold(t1, t2)) {
                backtrack(gamers, idx + 1, t1, t2, sum1 + w, sum2, out);
            }
            t1.remove(g);
        }

        // Try Team 2
        if (t2.size() < TEAM_SIZE) {
            t2.add(g);
            if (partialConstraintsHold(t1, t2)) {
                backtrack(gamers, idx + 1, t1, t2, sum1, sum2 + w, out);
            }
            t2.remove(g);
        }
    }

    // ===== Constraints =====
    private static boolean constraintsHold(Set<Gamer> t1, Set<Gamer> t2) {
        return partialConstraintsHold(t1, t2);
    }

    private static boolean partialConstraintsHold(Set<Gamer> t1, Set<Gamer> t2) {
        // Tenzo & DoubleUQ must be together
        if (!sameTeamIfBothAssigned(t1, t2, TENZO, DOUBLE_UQ)) return false;
        // Slorko & Runite Egg cannot be together
        if (!notBothOnSameTeam(t1, t2, SLORKO, RUNITE_EGG)) return false;
        // Trio restriction
        if (!notAllThreeOnSameTeam(t1, KWANZILLA, NASTY_HAZE, OSMIUM_OATS)) return false;
        if (!notAllThreeOnSameTeam(t2, KWANZILLA, NASTY_HAZE, OSMIUM_OATS)) return false;
        return true;
    }

    private static boolean sameTeamIfBothAssigned(Set<Gamer> t1, Set<Gamer> t2, String a, String b) {
        boolean a1 = in(t1, a), a2 = in(t2, a);
        boolean b1 = in(t1, b), b2 = in(t2, b);
        if ((a1 || a2) && (b1 || b2))
            return (a1 && b1) || (a2 && b2);
        return true;
    }

    private static boolean notBothOnSameTeam(Set<Gamer> t1, Set<Gamer> t2, String a, String b) {
        boolean a1 = in(t1, a), a2 = in(t2, a);
        boolean b1 = in(t1, b), b2 = in(t2, b);
        return !((a1 && b1) || (a2 && b2));
    }

    private static boolean notAllThreeOnSameTeam(Set<Gamer> team, String n1, String n2, String n3) {
        int c = 0;
        if (in(team, n1)) c++;
        if (in(team, n2)) c++;
        if (in(team, n3)) c++;
        return c < 3;
    }

    private static boolean in(Set<Gamer> team, String name) {
        for (Gamer g : team)
            if (g.name().equals(name))
                return true;
        return false;
    }

    // ===== Output Helpers =====
    private static int sum(Collection<Gamer> team) {
        return (int) Math.round(team.stream().mapToDouble(Gamer::weight).sum());
    }

    private static void printTeam(String label, Collection<Gamer> team) {
        int s = sum(team);
        // Sort by weight DESC, then name ASC
        String line = team.stream()
            .sorted(Comparator.comparingDouble(Gamer::weight).reversed()
                .thenComparing(Gamer::name))
            .map(g -> g.name() + " [" + (int) g.weight() + "]")
            .collect(Collectors.joining(", "));
        System.out.println(label + " (" + s + "): " + line);
    }

    private static final class TeamSplit {
        final List<Gamer> team1;
        final List<Gamer> team2;

        TeamSplit(List<Gamer> t1, List<Gamer> t2) {
            this.team1 = t1;
            this.team2 = t2;
        }
    }
}
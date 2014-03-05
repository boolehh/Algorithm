import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;


public class BaseballElimination {
    
    private final int teamNum;               //number of teams
    private int teamWinMaxNum;              //the maximum score of the team that wins the most
    private final ArrayList<String> teams;  //team iterator
    private final int[][] gameState;        //current competition situation
    private boolean[] eliminated;           //the teams eliminated state
    private FordFulkerson ff;               //FF method instance
    
    // create a baseball division from given filename in format specified below
    public BaseballElimination(String filename) throws FileNotFoundException {
        Scanner file = new Scanner(new File(filename));
        teamNum = file.nextInt();
        teams = new ArrayList<String>();
        gameState = new int[teamNum][3+teamNum];
        eliminated = new boolean[teamNum];
        int win = 0;
        for (int i = 0; i < teamNum; i++) {
            if(file.hasNextLine()) {
                teams.add(file.next());
                for(int j = 0; j < teamNum+3; j++)
                    gameState[i][j] = file.nextInt();
                if (win < gameState[i][0]) {
                    teamWinMaxNum = i;
                    win = gameState[i][0];
                }
            }
        }
    }
    
    // number of teams
    public int numberOfTeams() {
        return teamNum;
    }
    
    // all teams
    public Iterable<String> teams() {
        return teams;
    }
    
    // number of wins for given team
    public int wins(String team) {
        if (team == null)
            throw new IllegalArgumentException();
        return gameState[teams.indexOf(team)][0];
    }
    
    // number of losses for given team
    public int losses(String team) {
        if (team == null)
            throw new IllegalArgumentException();
        return gameState[teams.indexOf(team)][1];
    }
    
    // number of remaining games for given team
    public int remaining(String team) {
        if (team == null)
            throw new IllegalArgumentException();
        return gameState[teams.indexOf(team)][2];
    }
    
    // number of remaining games between team1 and team2
    public int against(String team1, String team2) {
        if (team1 == null || team2 == null)
            throw new IllegalArgumentException();
        return gameState[teams.indexOf(team1)][3+teams.indexOf(team2)];
    }
    
    // is given team eliminated?
    //original teams are distributed from 0 to number-1
    //versus are distributed from number to number+comb-1
    //source is number+comb, sink is number+comb+1
    public boolean isEliminated(String team) {
        int teamNumb = teams.indexOf(team);
        int teamMaxWin = wins(team)+remaining(team);
        if (teamMaxWin < wins(teams.get(teamWinMaxNum))) {
            eliminated[teamNumb] = true;
            return true;
        }
        int temp = teamNum;
        int source = teamNum + teamNum*(teamNum-1)/2;
        int sink = source +1;
        FlowNetwork fw = new FlowNetwork(sink+1);
        //sourse --- competition --- team
        for(int i = 0; i < teamNum; i++) {
            for(int j = i + 1; j < teamNum; j++) {
                FlowEdge es = new FlowEdge(source, temp, gameState[i][j+3]);
                fw.addEdge(es);
                FlowEdge ej = new FlowEdge(temp, j, Double.MAX_VALUE);
                fw.addEdge(ej);
                FlowEdge ei = new FlowEdge(temp, i, Double.MAX_VALUE);
                fw.addEdge(ei);
                temp++;
            }
        }
        
        for (int i = 0; i < teamNum; i++) {
            if(i != teamNumb) {
                FlowEdge et = new FlowEdge(i, sink, teamMaxWin-gameState[i][0]);
                fw.addEdge(et);
            }
            else {
                FlowEdge et = new FlowEdge(i, sink, gameState[teamNumb][2]);
                fw.addEdge(et);
            }
        }
        int totalGameToPlay = 0;
        for (int i = 0; i < teamNum; i++) {
            totalGameToPlay += gameState[i][2];
        }
        totalGameToPlay /= 2 ;
        ff = new FordFulkerson(fw, source, sink);
        if(ff.value() < totalGameToPlay) {
            eliminated[teamNumb] = true;
            return true;
        }
        return false;
        
    }
    public Iterable<String> certificateOfElimination(String team) {
        // subset R of teams that eliminates given team; null if not eliminated
        ArrayList<String> certificate = new ArrayList<String>();
        for(int i = 0; i < teamNum; i++) {
            if (!eliminated[i])
                certificate.add(teams.get(i));
        }
        return certificate;
    }
    public static void main(String[] args) throws FileNotFoundException {
        //BaseballElimination division = new BaseballElimination(args[0]);
        String filename = "/home/boolee/workspace/assignment3/src/teams4";
        BaseballElimination division = new BaseballElimination(filename);
        for (String team : division.teams()) {
            if (division.isEliminated(team)) {
                StdOut.print(team + " is eliminated by the subset R = { ");
                for (String t : division.certificateOfElimination(team))
                    StdOut.print(t + " ");
                StdOut.println("}");
            }
            else {
                StdOut.println(team + " is not eliminated");
            }
        }
    }
}

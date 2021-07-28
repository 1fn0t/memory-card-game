import java.io.*;

public class TopScores {
    private int[] bestScores;
    private int[] bestTimes;

    public TopScores() {
        bestScores = new int[5];
        bestTimes = new int[5];
    }

    public void checkForBest(int time, int score) {
        readFromFile();
        for (int i = 0; i < 5; i++) {
            if (bestTimes[i] > time) {
                bestTimes[i] = time;
                bestScores[i] = score;
                continue;
            }
            if (i == 4) return;
        }
        int i;
        int key;
        int j;
        for (i = 1; i < 5; i++) {
            key = bestTimes[i];
            j = i - 1;
            while (j >= 0 && bestTimes[i] > key) {
                bestTimes[j + 1] = bestTimes[j];
                bestScores[j + 1] = bestScores[j];
                j--;
            }
            bestTimes[j + 1] = key;
        }
        writeToFile();
    }

    public void writeToFile() {
//        try {
//            BufferedWriter reader = new BufferedWriter(
//                    new FileReader("C:\\Users\\isaac\\IdeaProjects\\GUI practice 4\\TopScores.txt"));
//            String str;
        try {
            File outputFile = new File("TopScores.txt");
            PrintWriter writer = new PrintWriter(outputFile);
            for (int i = 0; i < 5; i++) {
                writer.println(bestScores[i] + ", " + bestTimes[i]);
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readFromFile() {
        try {
            BufferedReader reader = new BufferedReader(
                    new FileReader("C:\\Users\\isaac\\IdeaProjects\\GUI practice 4\\TopScores.txt"));
            String str;
            int i = 0;
            while ((str = reader.readLine()) != null) {
                String[] temp = str.split(", ");
                bestScores[i] = Integer.parseInt(temp[0]);
                bestTimes[i] = Integer.parseInt(temp[1]);
                i++;
            }
            reader.close();
//            File inputFile = new File("TopScores.txt");
//            Scanner scan = new Scanner(inputFile);
//            if (scan.hasNext()) {
//                for (int i = 0; i < 5; i++) {
//                    if (scan.hasNext()) {
//                        String[] temp = scan.nextLine().split(", ");
//                        bestScores[i] = Integer.parseInt(temp[0]);
//                        bestTimes[i] = Integer.parseInt(temp[1]);
//                    }
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

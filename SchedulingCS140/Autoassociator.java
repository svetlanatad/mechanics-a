import java.util.Random;

public class Autoassociator {
    private int[][] weights;
    private int numNeurons;
    private Random random;
    public Autoassociator(CourseArray courses) {
        this.numNeurons = courses.length();
        this.weights = new int[numNeurons][numNeurons];
        this.random = new Random();
    }

    public int getTrainingCapacity() {
        return (int)(0.139 * numNeurons);
    }

    public void training(int[] pattern) {
        for (int i = 0; i < numNeurons; i++) {
            for (int j = 0; j < numNeurons; j++) {
                if (i != j) {
                    weights[i][j] += pattern[i] * pattern[j];
                }
            }
        }
    }

    public int unitUpdate(int[] neurons) {
        int index = random.nextInt(numNeurons);
        int sum = 0;
        for (int i = 0; i < numNeurons; i++) {
            if (i != index) {
                sum += weights[index][i] * neurons[i];
            }
        }

        neurons[index] = sum >= 0 ? 1 : -1;

        return index;
    }

    public void unitUpdate(int[] neurons, int index) {
        int sum = 0;

        for (int i = 0; i < numNeurons; i++) {
            if (i != index) {
                sum += weights[index][i] * neurons[i];
            }
        }

        neurons[index] = sum >= 0 ? 1 : -1;
    }

    public void chainUpdate(int[] neurons, int steps) {
        for (int i = 0; i < steps; i++) {
            unitUpdate(neurons);
        }
    }

    public void fullUpdate(int[] neurons) {
        boolean stable;
        do {
            stable = true;
            for (int i = 0; i < numNeurons; i++) {
                int previousState = neurons[i];
                unitUpdate(neurons, i);

                if (neurons[i] != previousState) {
                    stable = false;
                }
            }
        } while (!stable);
    }
}


import java.util.*;

public class Autoassociator {
    private int[][] weights;
   // private int numNeurons;
    private int trainingCapacity;
    private Random random = new Random();
    public Autoassociator(CourseArray courses) {
        //this.numNeurons = courses.length();
        //this.weights = new int[numNeurons][numNeurons];
        //this.random = new Random();
	weights = new int[courses.length()][courses.length()];
	trainingCapacity = (int) (0.139 * weights.length);
    }

    public int getTrainingCapacity() {
        return trainingCapacity;

    }

    public void training(int[] pattern) {
       // for (int i = 0; i < numNeurons; i++) {
         //   for (int j = 0; j < numNeurons; j++) {
           //     if (i != j) {
             //       weights[i][j] += pattern[i] * pattern[j];
              //  }
         //   }
       // }


	if(pattern.length == weights.length && trainingCapacity > 0){
		int prod;
		for(int i = 0; i < pattern.length - 1; i++)
			for(int j = i + 1; j < pattern.length; j++){
				prod = pattern[i] * pattern[j];
				weights[i][j] += prod;
				weights[j][i] += prod;
}
trainingCapacity--;
}
    }

    public void unitUpdate(int[] neurons, int index) {
        int sum = 0;
        for (int i = 0; i < neurons.length; i++) {
                sum += weights[index][i] * neurons[i];
		
        }

        neurons[index] = sum >= 0 ? 1 : -1;

    }

    public int unitUpdate(int[] neurons) {
	int index = random.nextInt(neurons.length);
	unitUpdate(neurons, index);
	return index;
    }

    public void chainUpdate(int[] neurons, int steps) {
       // for (int i = 0; i < steps; i++) {
         //   unitUpdate(neurons);
       // }
	  for(; steps > 0; steps--)
		unitUpdate(neurons);
    }

    public void fullUpdate(int[] neurons) {
        boolean stable;
        do {
            stable = true;
            for (int i = 0; i < weights.length; i++) {
                int previousState = neurons[i];
                unitUpdate(neurons, i);

                if (neurons[i] != previousState) {
                    stable = false;
                }
            }
        } while (!stable);
    }
}


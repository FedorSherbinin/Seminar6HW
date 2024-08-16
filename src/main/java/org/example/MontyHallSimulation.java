package org.example;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Getter
@Setter
public class MontyHallSimulation {
    private int numTrials;              // Количество итераций
    private int stayWins;               // Победы при сохранении выбора
    private int switchWins;             // Победы при смене выбора
    private Map<Integer, String> results; // Хранение результатов в формате <номер шага, результат>

    public MontyHallSimulation(int numTrials) {
        this.numTrials = numTrials;
        this.stayWins = 0;
        this.switchWins = 0;
        this.results = new HashMap<>();
    }

    public void runSimulation() {
        Random rand = new Random();

        for (int i = 0; i < numTrials; i++) {
            boolean[] doors = {false, false, false};
            int carPosition = rand.nextInt(3); // Позиция машины
            doors[carPosition] = true;

            int initialChoice = rand.nextInt(3); // Первоначальный выбор игрока

            int montyChoice;
            do {
                montyChoice = rand.nextInt(3); // Ведущий выбирает дверь с козой
            } while (montyChoice == initialChoice || doors[montyChoice]);

            int remainingDoor = 3 - initialChoice - montyChoice; // Оставшаяся дверь

            // Проверка результата при сохранении выбора
            if (doors[initialChoice]) {
                stayWins++;
                results.put(i + 1, "Сохранение выбора - Победа");
            } else {
                results.put(i + 1, "Сохранение выбора - Поражение");
            }

            // Проверка результата при смене выбора
            if (doors[remainingDoor]) {
                switchWins++;
                results.put(i + 1, results.get(i + 1) + "; Смена выбора - Победа");
            } else {
                results.put(i + 1, results.get(i + 1) + "; Смена выбора - Поражение");
            }
        }
    }

    public void printResults() {
        System.out.println("Количество выигрышей при сохранении выбора: " + stayWins);
        System.out.println("Количество выигрышей при смене выбора: " + switchWins);

        DescriptiveStatistics stats = new DescriptiveStatistics();
        stats.addValue(stayWins);
        stats.addValue(switchWins);

        System.out.println("Среднее значение: " + stats.getMean());
        System.out.println("Стандартное отклонение: " + stats.getStandardDeviation());

        System.out.println("\nРезультаты каждого шага:");
        for (Map.Entry<Integer, String> entry : results.entrySet()) {
            System.out.println("Шаг " + entry.getKey() + ": " + entry.getValue());
        }
    }

    public static void main(String[] args) {
        MontyHallSimulation simulation = new MontyHallSimulation(1000); // 1000 итераций
        simulation.runSimulation();
        simulation.printResults();
    }
}

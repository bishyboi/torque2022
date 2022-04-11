package frc.robot.game2022.modules;

public class AutoTaskLoopTest {
    public static int[] testLoop(int distance, int phase, int count, int shootingDistance, int exitDistance) {
        int error = 0;
        switch (phase) {
            case 1: // phase 1: move to shooting location
                // combine.intakeMove(intakePower);
                error = shootingDistance - distance;
                distance = distance + (error / Math.abs(error));// this.centerAlign(shootingDistance, distanceError);
                if (distance - shootingDistance == 0) {
                    phase++;
                }
                break;
            case 2: // phase 2: shoot
                count++;
                // combine.intakeMove(-intakePower);
                if (count >= 100) {
                    phase++;
                }
                break;
            case 3: // phase 3: move out
                // combine.intakeMove(intakePower);
                error = exitDistance - distance;
                distance = distance + (error != 0 ? (error / Math.abs(error)) : 0);// this.centerAlign(exitDistance,
                                                                                   // errorMargin);
                break;
        }
        int[] result = { distance, phase, count, error };
        return result;
    }

    public static void main(String[] args) throws InterruptedException {
        int[] data = { 100, 1, 0, 0 };
        boolean done = false;
        while (!done) {
            data = testLoop(data[0], data[1], data[2], 36, 130);
            System.out.println(data[0] + "\t" + data[1] + "\t" + data[2] + "\t" + data[3]);
            if (data[1] == 3 && data[3] == 0) {
                done = true;
            }
            Thread.sleep(20);
        }
    }
}
package gusto.fatec.exercicios.semaforos.ex2.view;

import java.util.concurrent.Semaphore;

import gusto.fatec.exercicios.semaforos.ex2.controller.CarThread;

public class Main {

	public static void main(String[] args) {
		int permissoes = 2;

		Semaphore semaforo = new Semaphore(permissoes);
				
		for (int idCar = 0; idCar < 4; idCar++) {
			Thread tCar = new CarThread(idCar + 1, semaforo, idCar);
			tCar.start();
		}
	}

}

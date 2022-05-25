package gusto.fatec.exercicios.semaforos.ex3;

import gusto.fatec.exercicios.semaforos.ex3.controller.TreinoController;

import java.util.concurrent.Semaphore;

public class Principal {
	public static void main(String[] args) {
		
		Semaphore semaforo = new Semaphore(5);

		for (int i = 0; i < 7; i++) {
			Thread treino = new TreinoController(i + 1, semaforo);
			treino.start();

		}
	}

}
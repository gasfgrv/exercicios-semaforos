package gusto.fatec.exercicios.semaforos.ex3.view;

import java.util.concurrent.Semaphore;

import gusto.fatec.exercicios.semaforos.ex3.controller.TreinoController;

public class Principal {
	public static void main(String[] args) {
		
		Semaphore semaforo = new Semaphore(5);
		
		int aux[] = new int[3];
		
		for (int i = 0; i < 7; i++) {
			Thread treino = new TreinoController(i + 1, semaforo);
			treino.start();

		}
	}

}
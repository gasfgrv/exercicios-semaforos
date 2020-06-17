package gusto.fatec.exercicios.semaforos.ex1.view;

import java.util.concurrent.Semaphore;

import gusto.fatec.exercicios.semaforos.ex1.controller.PessoaThread;

public class Main {

	public static void main(String[] args) {
		Semaphore semaforo = new Semaphore(1);

		for (int pessoa = 0; pessoa < 4; pessoa++) {
			Thread thread = new PessoaThread(pessoa + 1, semaforo);
			thread.start();
		}

	}

}

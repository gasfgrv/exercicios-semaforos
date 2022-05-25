package gusto.fatec.exercicios.semaforos.ex1;

import gusto.fatec.exercicios.semaforos.ex1.controller.PessoaThread;

import java.util.concurrent.Semaphore;

public class Main {

	public static void main(String[] args) {
		Semaphore semaforo = new Semaphore(1);

		for (int pessoa = 0; pessoa < 4; pessoa++) {
			Thread thread = new PessoaThread(semaforo);
			thread.setName("Pessoa " + (pessoa + 1));
			thread.start();
		}

	}

}

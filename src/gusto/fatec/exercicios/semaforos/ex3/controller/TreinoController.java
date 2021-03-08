package gusto.fatec.exercicios.semaforos.ex3.controller;

import java.util.concurrent.Semaphore;

public class TreinoController extends Thread {
	private final Semaphore carrosNaPista;

	private final int idEquipe;
	private int melhorVolta = 0;

	private final int[] voltas = new int[3];

	public final int[][] grid = new int[14][3];

	private static int treinoConcluido = -1;

	public TreinoController(int idEquipe, Semaphore carrosNaPista) {
		this.idEquipe = idEquipe;
		this.carrosNaPista = carrosNaPista;
	}

	@Override
	public void run() {
		try {
			carrosNaPista.acquire();
			equipeAndando();
		} catch (InterruptedException e) {
			e.printStackTrace();
			Thread.currentThread().interrupt();
		} finally {
			carrosNaPista.release();
		}
	}

	private void equipeAndando() {
		Semaphore carrosPorEquipe = new Semaphore(1);

		for (int i = 0; i < 2; i++) {
			try {
				carrosPorEquipe.acquire();
				melhorVolta(i + 1);
			} catch (InterruptedException e) {
				e.printStackTrace();
				Thread.currentThread().interrupt();
			} finally {
				carrosPorEquipe.release();
			}
		}
	}

	private void melhorVolta(int idCarro) {
		for (int i = 0; i < 3; i++) {
			try {
				Thread.sleep(100);

				int tempoVolta = 50 + (int) (Math.random() * (80 - 50));
				voltas[i] = tempoVolta;

				if (melhorVolta == 0 || voltas[i] < melhorVolta) {
					melhorVolta = voltas[i];
				}

				System.out.println("Equipe: #" + idEquipe + " carro #" + idCarro + " fez a volta " + (i + 1) + " em "
						+ tempoVolta + " segundos.");
			} catch (InterruptedException e) {
				e.printStackTrace();
				Thread.currentThread().interrupt();
			}
		}
		setTreinoConcluido();

		grid[treinoConcluido][0] = idEquipe;
		grid[treinoConcluido][1] = idCarro;
		grid[treinoConcluido][2] = melhorVolta;

		if (treinoConcluido > 12) {
			organizarGrid();
		}
	}

	public void organizarGrid() {
		int[] aux;

		System.out.println();

		for (int i = 0; i < 14; i++) {
			for (int j = i + 1; j < 14; j++) {
				if (grid[i][2] > grid[j][2]) {
					aux = grid[i];
					grid[i] = grid[j];
					grid[j] = aux;
				}
			}
		}

		for (int i = 0; i < 14; i++) {
			System.out.println((i + 1) + "º: Equipe: #" + grid[i][0] + " - Carro: #" + grid[i][1]);
		}
	}

	public static void setTreinoConcluido() {
		TreinoController.treinoConcluido = treinoConcluido + 1;
	}
}

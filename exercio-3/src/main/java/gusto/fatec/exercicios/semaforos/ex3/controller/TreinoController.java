package gusto.fatec.exercicios.semaforos.ex3.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class TreinoController extends Thread {

    private static final Logger LOGGER = LoggerFactory.getLogger(Thread.currentThread().getName());
    private final Semaphore carrosNaPista;
    private final int idEquipe;
    private int melhorVolta = 0;
    private final int[] voltas = new int[3];
    public final int[][] grid = new int[14][3];
    private static int treinoConcluido = -1;
	private final Random random;

    public TreinoController(int idEquipe, Semaphore carrosNaPista) {
        this.idEquipe = idEquipe;
        this.carrosNaPista = carrosNaPista;
		random = new Random();
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
            carroNaPista(carrosPorEquipe, i);
        }
    }

    private void carroNaPista(Semaphore carrosPorEquipe, int i) {
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

    private void melhorVolta(int idCarro) {
		darVolta(idCarro);

		setTreinoConcluido();

		definirPolePosition(idCarro);

		if (treinoConcluido > 12) {
            organizarGrid();
        }
    }

	private void darVolta(int idCarro) {
		for (int i = 0; i < 3; i++) {
			participarDaClassificacao(idCarro, i);
		}
	}

	private void definirPolePosition(int idCarro) {
		grid[treinoConcluido][0] = idEquipe;
		grid[treinoConcluido][1] = idCarro;
		grid[treinoConcluido][2] = melhorVolta;
	}

	private void participarDaClassificacao(int idCarro, int i) {
		try {
			Thread.sleep(100);

			int tempoVolta = fazAVolta(i);
			isMelhorVolta(i);

			String mensagem = "Equipe: #" + idEquipe + " carro #" + idCarro + " fez a volta " + (i + 1) + " em " + tempoVolta + " segundos.";
			LOGGER.info(mensagem);
		} catch (InterruptedException e) {
			e.printStackTrace();
			Thread.currentThread().interrupt();
		}
	}

	private int fazAVolta(int volta) {
		int tempoVolta = random.nextInt(30) + 50;
		voltas[volta] = tempoVolta;
		return tempoVolta;
	}

	private void isMelhorVolta(int tempoVolta) {
		melhorVolta = Integer.MAX_VALUE;

		if (voltas[tempoVolta] < melhorVolta) {
            melhorVolta = voltas[tempoVolta];
        }
    }

    public void organizarGrid() {
		ordenarMatriz();

		apresentarGrid();
	}

	private void ordenarMatriz() {
		for (int i = 0; i < 14; i++) {
			percorrerColunasDaLinha(i);
		}
	}

	private void percorrerColunasDaLinha(int i) {
		for (int j = i + 1; j < 14; j++) {
			ordenarGrid(i, j);
		}
	}

	private void ordenarGrid(int i, int j) {
		int[] aux;
		if (grid[i][2] > grid[j][2]) {
			aux = grid[i];
			grid[i] = grid[j];
			grid[j] = aux;
		}
	}

	private void apresentarGrid() {
		for (int i = 0; i < 14; i++) {
			String mensagem = (i + 1) + "º: Equipe: #" + grid[i][0] + " - Carro: #" + grid[i][1];
			LOGGER.info(mensagem);
		}
	}

	public static void setTreinoConcluido() {
        TreinoController.treinoConcluido = treinoConcluido + 1;
    }
}

package gusto.fatec.exercicios.semaforos.ex2.controller;

import java.util.concurrent.Semaphore;

public class CarThread extends Thread {

	private static int posChegada;
	private static int posSaida;
	private final int idCar;
	private final Semaphore semaforo;
	private static final String[] SENTIDOS = new String[] { "norte", "leste", "sul", "oeste" };
	private final String sentido;

	public CarThread(int idCar, Semaphore semaforo, int sentido) {
		this.idCar = idCar;
		this.semaforo = semaforo;
		this.sentido = SENTIDOS[sentido];
	}

	@Override
	public void run() {
		carroAndando();

		try {
			semaforo.acquire();
			carroEsperando();
			carroSaindo();
		} catch (InterruptedException e) {
			e.printStackTrace();
			Thread.currentThread().interrupt();
		} finally {
			semaforo.release();
		}
	}

	private void carroAndando() {
		int distTotal = (int) ((Math.random() * 1001) + 2000);
		int distPecorrida = 0;
		int delocamento = 30;
		int tempo = 50;

		while (distPecorrida < distTotal) {
			distPecorrida += delocamento;
			try {
				Thread.sleep(tempo);
			} catch (InterruptedException e) {
				e.printStackTrace();
				Thread.currentThread().interrupt();
			}
			System.out.println("Carro: #" + idCar + " andou " + distPecorrida + "m");
		}

		setPosSaida();

		System.out.println("Carro: #" + idCar + " foi o " + posChegada + "� a chegar");
	}

	private void carroEsperando() {
		System.out.println("Carro: #" + idCar + " est� espenado");

		int tempoParado = (int) ((Math.random() * 2000) + 1000);

		try {
			Thread.sleep(tempoParado);
		} catch (InterruptedException e) {
			e.printStackTrace();
			Thread.currentThread().interrupt();
		}
	}

	private void carroSaindo() {
		setPosSaida();

		System.out.println("Carro: #" + idCar + " passou pelo semaforo, indo no sentido, " + sentido);
	}

	public static void setPosChegada() {
		CarThread.posChegada = posChegada +1;
	}

	public static void setPosSaida() {
		CarThread.posSaida = posSaida + 1;
	}
}

package gusto.fatec.exercicios.semaforos.ex2.controller;

import java.util.concurrent.Semaphore;

public class CarThread extends Thread {

	private static int posChegada;
	private static int posSaida;
	private int idCar;
	private Semaphore semaforo;
	private static String[] sentidos = new String[] { "norte", "leste", "sul", "oeste" };
	private String sentido;

	public CarThread(int idCar, Semaphore semaforo, int sentido) {
		this.idCar = idCar;
		this.semaforo = semaforo;
		this.sentido = this.sentidos[sentido];
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
			}
			System.out.println("Carro: #" + idCar + " andou " + distPecorrida + "m");
		}

		posChegada++;

		System.out.println("Carro: #" + idCar + " foi o " + posChegada + "� a chegar");
	}

	private void carroEsperando() {
		System.out.println("Carro: #" + idCar + " est� espenado");

		int tempoParado = (int) ((Math.random() * 2000) + 1000);

		try {
			Thread.sleep(tempoParado);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void carroSaindo() {
		posSaida++;

		System.out.println("Carro: #" + idCar + " passou pelo semaforo, indo no sentido, " + sentido);
	}

}

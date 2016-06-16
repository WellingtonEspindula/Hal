package hal.esy.es.utils;

public class CalculadoraPontuacao {
	
	public static int receberPontuacao(int arrayLength){
		int calculo = 0;
		for (int i = 0; i < arrayLength; i++)
			calculo += i;
		return calculo;
	}
}

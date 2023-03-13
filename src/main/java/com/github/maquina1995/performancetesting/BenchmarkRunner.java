package com.github.maquina1995.performancetesting;

import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.Main;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Warmup;

@Fork(value = 2)
@Warmup(iterations = 2)
@Measurement(iterations = 3)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@BenchmarkMode(Mode.AverageTime)
public class BenchmarkRunner {

	public static void main(String[] args) throws Exception {
		Main.main(args);
	}

	@Benchmark
	public void concatenar() {
		String cadena = "fragmento1" + " fragmento2" + " fragmento3";
	}

	@Benchmark
	public void stringBuilder() {
		String cadena = new StringBuilder("fragmento1").append(" fragmento2")
				.append(" fragmento3")
				.toString();
	}

	@Benchmark
	public void stringFormat() {
		String cadena = String.format("%s %s %s", "fragmento1", " fragmento2", " fragmento3");
	}

	@Benchmark
	public void stringJoin() {
		String cadena = String.join(" ", "fragmento1" + "fragmento2" + "fragmento3");
	}

}

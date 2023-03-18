# Configuración del proyecto

Para poder hacer un análisis de rendimiento tendremos que añadir las siguientes dependencias:
```
<dependency>
	<groupId>org.openjdk.jmh</groupId>
	<artifactId>jmh-core</artifactId>
	<version>1.36</version> <!-- revisar ultima versión -->
</dependency>
<dependency>
	<groupId>org.openjdk.jmh</groupId>
	<artifactId>jmh-generator-annprocess</artifactId>
	<version>1.36</version> <!-- revisar ultima versión -->
</dependency>
```
Añadir a la clase donde queramos analizar 1 o mas métodos publicos:
```
// Ejecuciones que se descartarán (unido normalmente al warmup)
@Fork(value = 2)
// Se usa normalmente para "calentar" haciendo X interacciones antes de que empiece el análisis
@Warmup(iterations = 2)
// Número de interacciones a ejecutar del método o métodos
@Measurement(iterations = 3)
// Unidad de tiempo en la que medir
@OutputTimeUnit(TimeUnit.NANOSECONDS)
// Tipo de prueba que queremos ejecutar tabla de abajo
@BenchmarkMode(Mode.AverageTime)
```
Tipos de prueba a ejecutar
| Tipo | Descripción | 
|:---:|:---:|
| Throughput("thrpt", "Throughput, ops/time") |	Calculará el número de veces que tu método podrá ser ejecutado en 1 segundo |
| AverageTime("avgt", "Tiempo medio, time/op") |	Calculará el tiempo promedio que se tarda en ejecutar el método |
| SampleTime("sample", "Sampling time") | Coge al azar entre muchas ejecuciones el tiempo que se tarda en ejecutar tu método |
| SingleShotTime("ss", "Single shot invocation time") |	Solo se ejecutará 1 vez, normalmente usado para testeo de "rendimiento frío" sin que entre demasiado | las optimizaciones del compilador |
| All("all", "All benchmark modes") |	Calcula todos los anteriores |

Añadir un método Main donde queramos ejeecutar los análisis:
```
public static void main(String[] args) throws Exception {
	Main.main(args);
}
```

Añadir al método del que queremos analizar los tiempos la siguiente anotación:
```
@Benchmark
public void concatenar() {
	String cadena = "fragmento1" + " fragmento2" + " fragmento3";
}
```
Cada vez que cambiemos algo de la configuración de fork iteraciones o adiciones de @Benchmark en metodo tendremos que compilar nuestro proyecto con `mvn clean package` o similares ya sea skipeando test etc

# Ejemplo de análisis sobre 6 métodos de concatenación de Strings:

## Concatenación simple con +

```
String cadena = "fragmento1" + " fragmento2" + " fragmento3";
```

## Concatenación con Concat
```
String cadena = "fragmento1"
		.concat(" fragmento2")
		.concat(" fragmento3");
```

## Concatenación con StringBuilder

```
String cadena = new StringBuilder("fragmento1")
          .append(" fragmento2")
          .append(" fragmento3")
          .toString();
```

## Concatenación con stringFormat

```
String cadena = String.format("%s %s %s", "fragmento1" , " fragmento2" , " fragmento3");
```

## Concatenación con String.Join

```
String cadena = String.join(" ", "fragmento1" + "fragmento2" + "fragmento3");
```

## Concatenación con Stringjoiner

```
StringJoiner cadena = new StringJoiner(" ")
		.add("fragmento1")
		.add("fragmento2")
		.add("fragmento3");
```

## Resultados:

| Benchmark | Mode | Cnt | Score | Error | Units |
|:---:|:---:|:---:|:---:|:---:|:---:|
| BenchmarkRunner.concatenar | avgt | 6 | 0,343 | ± 0,007 | ns/op |
| BenchmarkRunner.stringBuilder | avgt | 6 | 10,913 | ± 1,864 | ns/op |
| BenchmarkRunner.stringConcat | avgt | 6 | 16,677 | ± 1,765 | ns/op |
| BenchmarkRunner.stringFormat | avgt | 6 | 219,744 | ± 95,807 | ns/op |
| BenchmarkRunner.stringJoin | avgt | 6 | 5,596 | ± 0,479 | ns/op |
| BenchmarkRunner.stringJoiner | avgt | 6 | 5,076 | ± 0,261 | ns/op |

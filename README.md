![POD](https://img.shields.io/badge/version-v1.0.0-blue.svg) ![POD](https://img.shields.io/badge/language-Scala-black.svg) ![POD](https://img.shields.io/badge/platform-Spark-red.svg) ![POD](https://img.shields.io/badge/license-gnu-lightgrey.svg) ![SBT compatible](https://img.shields.io/badge/SBT-compatible-4BC51D.svg?style=flat)

# Teste HTTP requests to the NASA Kennedy  Space Center WWW server

Fonte oficial do dateset : http://ita.ee.lbl.gov/html/contrib/NASA-HTTP.html

Dados :

[● Jul 01 to Jul 31, ASCII format, 20.7 MB gzip compressed , 205.2 MB.](ftp://ita.ee.lbl.gov/traces/NASA_access_log_Jul95.gz)
[● Aug 04 to Aug 31, ASCII format, 21.8 MB gzip compressed , 167.8 MB.](ftp://ita.ee.lbl.gov/traces/NASA_access_log_Aug95.gz)

Sobre o dataset : Esses dois conjuntos de dados possuem todas as requisições HTTP para o servidor da NASA Kennedy

Space Center WWW na Flórida para um período específico.

Os logs estão em arquivos ASCII com uma linha por requisição com as seguintes colunas:

● **Host fazendo a requisição** . Um hostname quando possível, caso contrário o endereço de internet se o nome não puder ser identificado.
● **Timestamp** no formato "DIA/MÊS/ANO:HH:MM:SS TIMEZONE"
● **Requisição (entre aspas)**
● **Código do retorno HTTP**
● **Total de bytes retornados**

**Questões**
 Responda as seguintes questões devem ser desenvolvidas em Spark utilizando a sua linguagem de preferência.

1. Número de hosts únicos.
**137978**

2. O total de erros 404.
**20901**

3. Os 5 URLs que mais causaram erro 404.
    ```
	URL: hoohoo.ncsa.uiuc.edu  QTDE: 251
	URL: piweba3y.prodigy.com  QTDE: 157
	URL: jbiagioni.npt.nuwc.navy.mil  QTDE: 132
	URL: piweba1y.prodigy.com  QTDE: 114
	URL: www-d4.proxy.aol.com  QTDE: 91
	```

4. Quantidade de erros 404 por dia.
	```
    DIA: 01/Aug/1995   QTDE: 243
    DIA: 01/Jul/1995      QTDE: 316
    DIA: 02/Jul/1995      QTDE: 291
    DIA: 03/Aug/1995    QTDE: 304
    DIA: 03/Jul/1995      QTDE: 474
    DIA: 04/Aug/1995   QTDE: 346
    DIA: 04/Jul/1995      QTDE: 359
    DIA: 05/Aug/1995   QTDE: 236
    DIA: 05/Jul/1995      QTDE: 497
    DIA: 06/Aug/1995   QTDE: 373
    DIA: 06/Jul/1995      QTDE: 640
    DIA: 07/Aug/1995   QTDE: 537
    DIA: 07/Jul/1995      QTDE: 570
    DIA: 08/Aug/1995   QTDE: 391
    DIA: 08/Jul/1995      QTDE: 302
    DIA: 09/Aug/1995   QTDE: 279
    DIA: 09/Jul/1995      QTDE: 348
    DIA: 10/Aug/1995   QTDE: 315
    DIA: 10/Jul/1995     QTDE: 398
    DIA: 11/Aug/1995   QTDE: 263
    DIA: 11/Jul/1995     QTDE: 471
    DIA: 12/Aug/1995   QTDE: 196
    DIA: 12/Jul/1995     QTDE: 471
    DIA: 13/Aug/1995   QTDE: 216
    DIA: 13/Jul/1995     QTDE: 532
    DIA: 14/Aug/1995   QTDE: 287
    DIA: 14/Jul/1995     QTDE: 413
    DIA: 15/Aug/1995   QTDE: 327
    DIA: 15/Jul/1995     QTDE: 254
    DIA: 16/Aug/1995   QTDE: 259
    DIA: 16/Jul/1995     QTDE: 257
    DIA: 17/Aug/1995   QTDE: 271
    DIA: 17/Jul/1995     QTDE: 406
    DIA: 18/Aug/1995   QTDE: 256
    DIA: 18/Jul/1995     QTDE: 465
    DIA: 19/Aug/1995   QTDE: 209
    DIA: 19/Jul/1995     QTDE: 639
    DIA: 20/Aug/1995   QTDE: 312
    DIA: 20/Jul/1995     QTDE: 428
    DIA: 21/Aug/1995   QTDE: 305
    DIA: 21/Jul/1995     QTDE: 334
    DIA: 22/Aug/1995   QTDE: 288
    DIA: 22/Jul/1995     QTDE: 192
    DIA: 23/Aug/1995   QTDE: 345
    DIA: 23/Jul/1995     QTDE: 233
    DIA: 24/Aug/1995   QTDE: 420
    DIA: 24/Jul/1995     QTDE: 328
    DIA: 25/Aug/1995   QTDE: 415
    DIA: 25/Jul/1995     QTDE: 461
    DIA: 26/Aug/1995   QTDE: 366
    DIA: 26/Jul/1995     QTDE: 336
    DIA: 27/Aug/1995   QTDE: 370
    DIA: 27/Jul/1995     QTDE: 336
    DIA: 28/Aug/1995   QTDE: 410
    DIA: 28/Jul/1995     QTDE: 94
    DIA: 29/Aug/1995    QTDE: 420
    DIA: 30/Aug/1995    QTDE: 571
    DIA: 31/Aug/1995    QTDE: 526
   ```

5. O total de bytes retornados.
	**65524314915 Bytes**

**Explique o que o código Scala abaixo faz.**

```
val textFile = sc.textFile("hdfs://...")
val counts = textFile.flatMap(line => line.split(" "))
	.map(word => (word,1))
	.reduceByKey(_+_)
counts.saveAsTextFile("hdfs://...")
```

**linha1 = Carrega um arquivo no spark
linha2 = Quebrar o texto quando encontrar espaço vazio, fazendo assim a separação das palavras
linha3 = Faz o mapeamento atribuindo 1 ponto para cada palavra
linha4 = Aplica o reduce agrupando e somando quantas vezes a mesma palavra aparece
linha5 = Salva o arquivo enriquecido com o agrupamento com contagem**

### QUESTÕES:

Gostaríamos de fazer um teste que será usado para sabermos a sua proficiência nas habilidades para a vaga. O teste consiste em algumas perguntas e exercícios práticos sobre Spark e as respostas e códigos implementados devem ser armazenados no GitHub. 

O link do seu repositório deve ser compartilhado conosco ao final do teste.


Quando usar alguma referência ou biblioteca externa, informe no arquivo README do seu projeto. Se tiver alguma dúvida, use o  bom senso e se precisar deixe isso registrado na documentação do projeto.

#### 1 - Qual o objetivo do comando cache em Spark?

Resp: È utilizado o conceito de Lazy load para as variáveis, assim os recursos serão utilizados somente de forma repentina e sob demanda, otimizando os processos. 

#### 2 - O mesmo código implementado em Spark é normalmente mais rápido que a implementação equivalente em MapReduce. Por quê?

Resp: Além de possuir diversos algoritmos super otimizados, o Spark faz no geral o uso de "Memória RAM" para processamento, já o MapReduce grava os dados em disco normalmente mais lentos que a RAM.

#### 3 - Qual é a função do SparkContext ?

Resp: SparkContext pode ser único ou múltiplo e sua função é conectar uma linguagem ou recurso a uma sessão de Spark, compartilhando recursos e execução de Jobs em Stages.

#### 4 - Explique com suas palavras o que é Resilient Distributed Datasets (RDD).

Resp: RRD implementa um conceito de nós em cluster, paralelismo e dados distribuidos. Com isso além de termos maior otimização no processamento  simultâneo, conseguimos também obter "resiliência" e garantias no caso de falhas em algum nó, já que a execução é realizada em vários nós que podem de replicar e se recuperar.

#### 5 - GroupByKey é menos eficiente que reduceByKey em grandes dataset.  Por quê?

Resp: GroupByKey faz a combinação depois de calcular todos as combinações de registros fazendo um uso muito maior da memória. O ReduceByKey a combinação e a soma dos dados são feitas de forma parcial, conseguindo uma otimização muito maior, como o conceito de Streams. 


## Requisitos mínimos

Tecnologia | Versão 
------- | --------
Java | 8 
Scala | 2.11.x 
Spark | 2.4.4 
SBT | 1.2.8 
Docker   | corrente 


# Instruções de como testar a solução via Docker

```
$ docker pull jeffersonmf/nasalogs

$ docker run -t -d -p 9000:9000 nasalogs

$ teste no browser a seguinte url:   http://localhost:9000/monitoring
Aguarde aparecer a mensagem:  Nasa Log Analyzer is up!!! e então siga com os testes....

```

# Instruções de como testar a solução localmente

```
[ssh]
$ git clone git@github.com:Jeffersonmf/teste-nasa-logs.git

[https]
$ git clone https://github.com/Jeffersonmf/teste-nasa-logs.git

$ sbt clean package

$ sbt run

```

## Como utilizar as API's

**Testando se a solução está disponível**
```
Method: Get
http://localhost:9000/monitoring
```

**Realiza o processamento dos logs com a necessidade copiar os arquivos de Logs na pasta de entrada. "É necessário COPIAR os arquivos para a pasta de entrada. A Pasta pode ser configurada no arquivo application.conf [SourceLocalFolder]", tendo o valor default ./inputdata**
```
Method: Get
http://localhost:9000/parser/nasa/logs
```

**Realiza o processamento dos logs sem a necessidade copiar os arquivos de Logs na pasta de entrada. O Download é resolvido e mesclado internamente pela solução**
```
Method: Get
http://localhost:9000/parser/nasa/logs/remote
```

## Resultado da execução

```
RESULTADOS COLETADOS DA ANALISE:

1 - NUMERO DE HOSTS UNICOS: 137978

2 - O TOTAL DE ERROS 404: 20901

3 - AS 5 URLS QUE MAIS CAUSARAM ERRO 404

URL: hoohoo.ncsa.uiuc.edu  QTDE: 251
URL: piweba3y.prodigy.com  QTDE: 157
URL: jbiagioni.npt.nuwc.navy.mil  QTDE: 132
URL: piweba1y.prodigy.com  QTDE: 114
URL: www-d4.proxy.aol.com  QTDE: 91

4 - QUANTIDADE DE ERROS 404 POR DIA.
DIA: 01/Aug/1995 QTDE: 243
DIA: 01/Jul/1995 QTDE: 316
DIA: 02/Jul/1995 QTDE: 291
DIA: 03/Aug/1995 QTDE: 304
DIA: 03/Jul/1995 QTDE: 474
DIA: 04/Aug/1995 QTDE: 346
DIA: 04/Jul/1995 QTDE: 359
DIA: 05/Aug/1995 QTDE: 236
DIA: 05/Jul/1995 QTDE: 497
DIA: 06/Aug/1995 QTDE: 373
DIA: 06/Jul/1995 QTDE: 640
DIA: 07/Aug/1995 QTDE: 537
DIA: 07/Jul/1995 QTDE: 570
DIA: 08/Aug/1995 QTDE: 391
DIA: 08/Jul/1995 QTDE: 302
DIA: 09/Aug/1995 QTDE: 279
DIA: 09/Jul/1995 QTDE: 348
DIA: 10/Aug/1995 QTDE: 315
DIA: 10/Jul/1995 QTDE: 398
DIA: 11/Aug/1995 QTDE: 263
DIA: 11/Jul/1995 QTDE: 471
DIA: 12/Aug/1995 QTDE: 196
DIA: 12/Jul/1995 QTDE: 471
DIA: 13/Aug/1995 QTDE: 216
DIA: 13/Jul/1995 QTDE: 532
DIA: 14/Aug/1995 QTDE: 287
DIA: 14/Jul/1995 QTDE: 413
DIA: 15/Aug/1995 QTDE: 327
DIA: 15/Jul/1995 QTDE: 254
DIA: 16/Aug/1995 QTDE: 259
DIA: 16/Jul/1995 QTDE: 257
DIA: 17/Aug/1995 QTDE: 271
DIA: 17/Jul/1995 QTDE: 406
DIA: 18/Aug/1995 QTDE: 256
DIA: 18/Jul/1995 QTDE: 465
DIA: 19/Aug/1995 QTDE: 209
DIA: 19/Jul/1995 QTDE: 639
DIA: 20/Aug/1995 QTDE: 312
DIA: 20/Jul/1995 QTDE: 428
DIA: 21/Aug/1995 QTDE: 305
DIA: 21/Jul/1995 QTDE: 334
DIA: 22/Aug/1995 QTDE: 288
DIA: 22/Jul/1995 QTDE: 192
DIA: 23/Aug/1995 QTDE: 345
DIA: 23/Jul/1995 QTDE: 233
DIA: 24/Aug/1995 QTDE: 420
DIA: 24/Jul/1995 QTDE: 328
DIA: 25/Aug/1995 QTDE: 415
DIA: 25/Jul/1995 QTDE: 461
DIA: 26/Aug/1995 QTDE: 366
DIA: 26/Jul/1995 QTDE: 336
DIA: 27/Aug/1995 QTDE: 370
DIA: 27/Jul/1995 QTDE: 336
DIA: 28/Aug/1995 QTDE: 410
DIA: 28/Jul/1995 QTDE: 94
DIA: 29/Aug/1995 QTDE: 420
DIA: 30/Aug/1995 QTDE: 571
DIA: 31/Aug/1995 QTDE: 526

5 - O TOTAL DE BYTES RETORNADOS: 65524314915 Bytes
```

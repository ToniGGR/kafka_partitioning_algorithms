# README: Vergleich von Partitionierungsalgorithmen in Apache Kafka mit Spring Boot

## Einleitung
Dieses Java Spring Boot Projekt vergleicht die Partitionierungsalgorithmen **Round Robin**, **Key Hash** und **Sticky** für Apache Kafka. Die Implementierung testet die Latenz und die Lastverteilung der Algorithmen anhand von künstlich generierten und Live-Wetterdaten.

## Voraussetzungen
Bevor die Spring Boot Anwendung gestartet werden kann, muss Apache Kafka installiert und konfiguriert sein. Die folgenden Schritte sind erforderlich:

### 1. Apache Kafka herunterladen und starten
1. [Apache Kafka herunterladen](https://kafka.apache.org/downloads) (Hier wurde die Binary Version von 3.9 heruntergeladen)
2. In das Kafka-Verzeichnis wechseln
3. Zookeeper starten:
   ```bash
   sh bin/zookeeper-server-start.sh config/zookeeper.properties
   ```
4. Kafka-Server starten:
   ```bash
   sh bin/kafka-server-start.sh config/server.properties
   ```

## API-Dokumentation

### 1. API zur Latenzmessung der Strategien
**Base URL:**  
`localhost:8080/start-test-`

**Verfügbare Optionen (an Base URL anhängen):**  
- `key-hash`
- `sticky`
- `round-robin`

| Parameter Name  | Typ   | Beschreibung  |
|---------------|------|---------------|
| message      | Query | Nachricht, die an die Partitionen gesendet werden soll |
| message_count | Query | Anzahl der zu sendenden Nachrichten |

### 2. API zur Lastverteilungsmessung mit Live-Wetterdaten
**Base URL:**  
`localhost:8080/start-weather-test-`

**Verfügbare Optionen (an Base URL anhängen):**  
- `key-hash`
- `sticky`
- `round-robin`

| Parameter Name  | Typ   | Beschreibung  |
|---------------|------|-----------------------------------------------|
| message_count | Query | Anzahl der Daten, die von der DWD API gesendet werden sollen |


### 3. Durchsatz Messung
**API Call**
`http://localhost:8080/init-bm`
``` bash
sh bin/kafka-producer-perf-test.sh --topic topic-one-partition --num-records 50000 --record-size 1000 --throughput 5000 --producer-props acks=1 bootstrap.servers=localhost:9092 buffer.memory=67108864 compresstion.type=none
```


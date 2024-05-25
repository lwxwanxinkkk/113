package lwxsubway;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;


class Station {
    String name;
    Set<String> lines;

    public Station(String name) {
        this.name = name;
        this.lines = new HashSet<>();
    }

    public void addLine(String line) {
        lines.add(line);
    }

    @Override
    public String toString() {
        return name + " " + lines;
    }
}

class LineSegment {
    String line;
    Station from;
    Station to;
    double distance;

    public LineSegment(String line, Station from, Station to, double distance) {
        this.line = line;
        this.from = from;
        this.to = to;
        this.distance = distance;
    }

    @Override
    public String toString() {
        return line + ": " + from.name + " -> " + to.name + " (" + distance + " km)";
    }
}
class SubwaySystem {
    Map<String, Station> stations;
    List<LineSegment> lineSegments;

    public SubwaySystem(String filePath) throws IOException {
        stations = new HashMap<>();
        lineSegments = new ArrayList<>();
        loadSubwayData(filePath);
    }

    private void loadSubwayData(String filePath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        String currentLine = "";

        while ((line = reader.readLine()) != null) {
            if (line.contains("号线站点间距")) {
                currentLine = line.split("号线")[0];
            } else if (line.contains("---")) {
                String[] parts = line.split("\t");
                String[] stationNames = parts[0].split("---");
                double distance = Double.parseDouble(parts[1]);

                addLineSegment(currentLine, stationNames[0], stationNames[1], distance);
            }
        }
        reader.close();
    }

    private void addLineSegment(String line, String from, String to, double distance) {
        Station fromStation = stations.computeIfAbsent(from, Station::new);
        Station toStation = stations.computeIfAbsent(to, Station::new);

        fromStation.addLine(line);
        toStation.addLine(line);

        lineSegments.add(new LineSegment(line, fromStation, toStation, distance));
        lineSegments.add(new LineSegment(line, toStation, fromStation, distance));
    }

    public Set<Station> getTransferStations() {
        Set<Station> transferStations = new HashSet<>();
        for (Station station : stations.values()) {
            if (station.lines.size() > 1) {
                transferStations.add(station);
            }
        }
        return transferStations;
    }

    public Map<Station, Double> getNearbyStations(String stationName, double maxDistance) {
        Station start = stations.get(stationName);
        if (start == null) {
            throw new IllegalArgumentException("站点不存在: " + stationName);
        }
    }
}
       


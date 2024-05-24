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


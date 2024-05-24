package lwxsubway;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
public class SubwaySystemGUI {
    private SubwaySystem subwaySystem;

    public SubwaySystemGUI(String filePath) throws IOException {
        subwaySystem = new SubwaySystem(filePath);
        createAndShowGUI();
    }

    private void createAndShowGUI() {
        JFrame frame = new JFrame("Subway System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        JPanel panel = new JPanel();

        JButton transferStationsButton = new JButton("识别地铁中转站");
        transferStationsButton.addActionListener(e -> showTransferStations());
        panel.add(transferStationsButton);

        JButton nearbyStationsButton = new JButton("查找附近站点");
        nearbyStationsButton.addActionListener(e -> showNearbyStations());
        panel.add(nearbyStationsButton);

        JButton allPathsButton = new JButton("查找所有路径");
        allPathsButton.addActionListener(e -> showAllPaths());
        panel.add(allPathsButton);

        JButton shortestPathButton = new JButton("查找最短路径");
        shortestPathButton.addActionListener(e -> showShortestPath());
        panel.add(shortestPathButton);

        JButton calculateFareButton = new JButton("计算乘车费用");
        calculateFareButton.addActionListener(e -> calculateFare());
        panel.add(calculateFareButton);

        JButton discountedFareButton = new JButton("计算不同票种的票价");
        discountedFareButton.addActionListener(e -> calculateDiscountedFare());
        panel.add(discountedFareButton);

        JButton exitButton = new JButton("退出");
        exitButton.addActionListener(e -> System.exit(0));
        panel.add(exitButton);

        frame.getContentPane().add(panel);
        frame.setVisible(true);
    }

    private void showTransferStations() {
        Set<Station> transferStations = subwaySystem.getTransferStations();
        JOptionPane.showMessageDialog(null, "Transfer Stations: " + transferStations);
    }

    private void showNearbyStations() {
        String stationName = JOptionPane.showInputDialog("输入站点名称:");
        String distanceStr = JOptionPane.showInputDialog("输入距离:");
        double distance = Double.parseDouble(distanceStr);

        try {
            Map<Station, Double> nearbyStations = subwaySystem.getNearbyStations(stationName, distance);
            JOptionPane.showMessageDialog(null, "Nearby Stations: " + nearbyStations);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    private void showAllPaths() {
        String startStation = JOptionPane.showInputDialog("输入起点站:");
        String endStation = JOptionPane.showInputDialog("输入终点站:");

  try {
            List<List<Station>> allPaths = subwaySystem.getAllPaths(startStation, endStation);
            JOptionPane.showMessageDialog(null, "All Paths: " + allPaths);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    private void showShortestPath() {
        String startStation = JOptionPane.showInputDialog("输入起点站:");
        String endStation = JOptionPane.showInputDialog("输入终点站:");

        try {
            List<Station> shortestPath = subwaySystem.getShortestPath(startStation, endStation);
            JOptionPane.showMessageDialog(null, "Shortest Path: " + shortestPath);
            subwaySystem.printPath(shortestPath);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

  private void calculateFare() {
        String startStation = JOptionPane.showInputDialog("输入起点站:");
        String endStation = JOptionPane.showInputDialog("输入终点站:");

        try {
            List<Station> path = subwaySystem.getShortestPath(startStation, endStation);
            double fare = subwaySystem.calculateFare(path);
            JOptionPane.showMessageDialog(null, "Fare: " + fare);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

  private void calculateDiscountedFare() {
        String startStation = JOptionPane.showInputDialog("输入起点站:");
        String endStation = JOptionPane.showInputDialog("输入终点站:");
        String ticketType = JOptionPane.showInputDialog("输入票种 (普通, 武汉通, 日票):");

        try {
            List<Station> path = subwaySystem.getShortestPath(startStation, endStation);
            double discountedFare = subwaySystem.calculateFareWithDiscount(path, ticketType);
            JOptionPane.showMessageDialog(null, "Discounted Fare (" + ticketType + "): " + discountedFare);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public static void main(String[] args) {
        try {
            new SubwaySystemGUI("C:\\Users\\33600\\Desktop\\实验一\\WuHanSubway\\subway.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

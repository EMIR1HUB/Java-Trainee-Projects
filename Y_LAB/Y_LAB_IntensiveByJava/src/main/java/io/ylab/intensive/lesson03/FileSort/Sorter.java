package io.ylab.intensive.lesson03.FileSort;

import java.io.*;
import java.util.*;

public class Sorter {
    private static final int MAX_SIZE = 10_000;

    public File sortFile(File dataFile) throws IOException {
        long currentTime = System.currentTimeMillis();
        List<File> sortedChunks = createSortedPieces(dataFile);
        mergeSortedPieces(dataFile, sortedChunks);
        System.out.println("Время сортировки: " + (System.currentTimeMillis() - currentTime));
        return dataFile;
    }

    private List<File> createSortedPieces(File dataFile) throws IOException {
        List<Long> piece = new ArrayList<>(MAX_SIZE);
        List<File> sortedPiece = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(dataFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                piece.add(Long.parseLong(line));
                if (piece.size() == MAX_SIZE) {
                    Collections.sort(piece);
                    sortedPiece.add(createSortedPieceFile(piece));
                    piece.clear();
                }
            }
        }
        if (!piece.isEmpty()) {
            Collections.sort(piece);
            sortedPiece.add(createSortedPieceFile(piece));
        }
        return sortedPiece;
    }

    private File createSortedPieceFile(List<Long> piece) throws IOException {
        File file = File.createTempFile("temp", ".txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (Long number : piece) {
                writer.write(number.toString());
                writer.newLine();
            }
        }
        return file;
    }

    private void mergeSortedPieces(File dataFile, List<File> sortedChunks) throws IOException {
        Queue<BufferedReader> readers = new LinkedList<>();
        for (File pieceFile : sortedChunks) {
            readers.offer(new BufferedReader(new FileReader(pieceFile)));
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(dataFile))) {
            PriorityQueue<NumberWithReader> heap = new PriorityQueue<>();
            for (BufferedReader reader : readers) {
                String line = reader.readLine();
                if (line != null) {
                    heap.offer(new NumberWithReader(Long.parseLong(line), reader));
                }
            }
            while (!heap.isEmpty()) {
                NumberWithReader nwr = heap.poll();
                writer.write(nwr.number.toString());
                writer.newLine();
                BufferedReader reader = nwr.reader;
                String line = reader.readLine();
                if (line != null) {
                    heap.offer(new NumberWithReader(Long.parseLong(line), reader));
                } else {
                    reader.close();
                }
            }
        }
    }

    private record NumberWithReader(Long number, BufferedReader reader) implements Comparable<NumberWithReader> {
        public int compareTo(NumberWithReader other) {
            return number.compareTo(other.number);
        }
    }
}

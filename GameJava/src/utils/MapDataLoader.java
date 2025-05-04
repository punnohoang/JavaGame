package utils;

import java.io.*;
import java.util.*;

public class MapDataLoader {

	public static List<int[]> readColumn(String filePath) {
		List<int[]> positions = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			String line;
			while ((line = br.readLine()) != null) {
				String[] parts = line.trim().split("\\s+"); // Tách bằng khoảng trắng
				if (parts.length == 2) {
					int x = Integer.parseInt(parts[0].trim());
					int height = Integer.parseInt(parts[1].trim());
					positions.add(new int[] { x, height });
				}
			}
		} catch (IOException | NumberFormatException e) {
			System.err.println("Lỗi đọc file");
		}
		return positions;
	}

}
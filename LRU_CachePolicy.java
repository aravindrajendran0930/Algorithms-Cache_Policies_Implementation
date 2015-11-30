/**
 * Implementation of LRU (Least Recently Used) Cache policy using ArrayList
 * Author : Aravind Rajendran
 * 
 */
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;

public class LRU_CachePolicy {

	static int cache_size = 0;
	static int iterator = 0;
	static int hit_count = 0, miss_count = 0;

	static String s;
	static ArrayList<Integer> cache = new ArrayList<Integer>();

	public static void main(String args[]) {
		String file_name = null;
		float hit_ratio = 0.0f;

		LinkedList<Integer> file_elements = new LinkedList<Integer>();
		LinkedList<Integer> sizes = new LinkedList<Integer>();

		Scanner in = new Scanner(System.in);
		System.out.println("Please Enter the FileName (e.g Filename.lis): ");
		file_name = in.nextLine();
		System.out.println("Please Enter the Cache Size (e.g 1024, 2048) : ");
		cache_size = in.nextInt();
		String[] ch;

		BufferedReader file_br = null;

		try {
			file_br = new BufferedReader(new FileReader(file_name));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		try {
			while (true) {
				s = file_br.readLine();
				if (s == null)
					break;
				else {
					ch = s.split(" ");
					file_elements.add(Integer.parseInt(ch[0]));
					sizes.add(Integer.parseInt(ch[1]));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		int temp, temp_size;
		int num_of_times = file_elements.size();
		System.out.println("Total number of elements : " + num_of_times);
		try {
			for (int i = 0; i < num_of_times; i++) {
				temp = file_elements.removeFirst();
				temp_size = sizes.removeFirst();
				insert_element(temp, temp_size);
			}
			System.out.println("Reslts");
			System.out.println("-------");
			System.out.println("1. Hit count : " + hit_count);
			System.out.println("2. Miss count : " + miss_count);
			System.out.println("3. Total count : " + miss_count + hit_count);

			hit_ratio = ((float) hit_count)
					/ ((float) (hit_count + miss_count)) * 100;

			System.out.println(hit_ratio);
			System.out.println("4. Hit ratio (%) : "
					+ (float) round_the_number(hit_ratio));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void insert_element(int temp, int temp_size) {

		int numberToBeInserted = 0;
		iterator++;
		for (int i = 0; i < temp_size; i++) {
			numberToBeInserted = temp + i;
				// case 1 : (item is not in the cache)
			if (!cache.contains(numberToBeInserted)) {
				miss_count++;
				// if cache is full : delete the lru position and paste the item
				// at MRU
				if (cache.size() == cache_size) {
					cache.remove(cache_size - 1);
					cache.add(0, numberToBeInserted);
				}
				// if cache is not full : paste Item at MRU
				else {
					cache.add(0, numberToBeInserted);
				}
			}
				// case 2 : item is in the cache bring item to the beginning)
			else {
				hit_count++;
				cache.remove(cache.indexOf(numberToBeInserted));
				cache.add(1, numberToBeInserted);
			}
		}
	}

	public static float round_the_number(float hit_ratio) {

		if (Math.round((hit_ratio * 1000.0)) % 10.0 > 6.0) {
			return (float) (Math.round((hit_ratio * 100.0) + 1.0) / 100.0);
		} else
			return (float) (Math.round((hit_ratio * 100.0)) / 100.0);
	}
}
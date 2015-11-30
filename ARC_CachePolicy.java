/**
 * Implementation of LRU (Least Recently Used) Cache policy using ArrayList
 * Author : Aravind Rajendran
 * 
 */
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class ARC_CachePolicy {

	static int cache_size = 0;
	static int iterator = 0;
	static int hit_count = 0, miss_count = 0;
	static String s;
	static LinkedList<Integer> T1 = new LinkedList<Integer>();
	static LinkedList<Integer> B1 = new LinkedList<Integer>();
	static LinkedList<Integer> T2 = new LinkedList<Integer>();
	static LinkedList<Integer> B2 = new LinkedList<Integer>();
	static int p;

	public static void main(String args[]) {
		String file_name = null;
		float hit_ratio = 0.0f;
		LinkedList<Integer> file_elements = new LinkedList<Integer>();
		LinkedList<Integer> sizes = new LinkedList<Integer>();
		BufferedReader br = null;
		String[] ch;
		int elementToBeInserted, sizeOfTheElement, num_of_times;

		Scanner in = new Scanner(System.in);
		file_name = in.nextLine();
		cache_size = in.nextInt();

		try {
			br = new BufferedReader(new FileReader(file_name));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		try {
			while (true) {
				s = br.readLine();
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

		num_of_times = file_elements.size();
		@SuppressWarnings("unused")
		int flag_IfItemInserted = 0;
		try {
			for (int i = 0; i < num_of_times; i++) {
				elementToBeInserted = file_elements.removeFirst();
				sizeOfTheElement = sizes.removeFirst();

				flag_IfItemInserted = insert_element(elementToBeInserted,
						sizeOfTheElement);
				flag_IfItemInserted = 0;
			}

			System.out.println("1. hit count = " + hit_count);
			System.out.println("2. miss count = " + miss_count);
			System.out.println("3. total count = " + miss_count + hit_count);

			hit_ratio = ((float) hit_count)
					/ ((float) (hit_count + miss_count)) * 100;

			System.out.println("4. hit ratio = "
					+ (float) round_the_number(hit_ratio));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static int insert_element(int elementToBeInserted,
			int sizeOfTheElement) {
		int item_inserted = 0, item_to_be_inserted = 0;
		int temp_size = sizeOfTheElement;
		int temp = elementToBeInserted;
		for (int i = 0; i < temp_size; ++i) {
			item_inserted = 0;
			item_to_be_inserted = temp + i;
			if (check_t1_t2(item_to_be_inserted))
				;
			else if (check_b1(item_to_be_inserted))
				;
			else if (check_b2(item_to_be_inserted))
				;
			else
				check_all(item_to_be_inserted);
		}
		return 1;
	}

	public static boolean check_t1_t2(int number) {
		int temp_position = 0;

		boolean a = T1.contains(number);
		boolean b = false;
		if (!a) {
			b = T2.contains(number);
		}
		if (a || b) {
			int temp = 0;
			hit_count++;

			T1.removeFirstOccurrence((Integer) number);
			T2.removeFirstOccurrence((Integer) number);
			T2.addFirst(number);
			return true;
		}
		return false;
	}

	public static boolean check_b1(int number) {
		int delta = 0;
		if (B1.contains(number)) {
			miss_count++;
			if (B1.size() >= B2.size())
				delta = 1;
			else
				delta = B2.size() / B1.size();
			p = Math.min(p + delta, cache_size);
			replace(number, p);
			B1.removeFirstOccurrence((Integer) number);
			T2.addFirst(number);
			return true;
		}
		return false;
	}

	public static boolean check_b2(int number) {
		int delta = 0;
		if (B2.contains(number)) {
			miss_count++;
			if (B2.size() >= B1.size()) {
				delta = 1;
			} else {
				delta = B1.size() / B2.size();
			}
			p = Math.max(p - delta, 0);
			replace(number, p);
			B2.removeFirstOccurrence((Integer) number);
			T2.addFirst(number);
			return true;
		}
		return false;
	}

	public static boolean check_all(int number) {
		int L1 = 0, T1_size = 0, B1_size = 0, T2_size = 0, B2_size = 0;
		miss_count++;
		T1_size = T1.size();
		B1_size = B1.size();
		T2_size = T2.size();
		B2_size = B2.size();
		L1 = T1_size + B1_size;
		if (L1 == cache_size) {
			if (T1_size < cache_size) {
				B1.removeLast(); // Deleting LRU page in B1
				replace(number, p);
			} else
				T1.removeLast(); // Deleting LRU page in T1
		} else if (L1 < cache_size) {
			if (T1_size + T2_size + B1_size + B2_size >= cache_size) {
				if (T1_size + T2_size + B1_size + B2_size == 2 * cache_size) {
					B2.removeLast();
				}
				replace(number, p);
			}
		}
		T1.addFirst(number);
		return true;
	}

	public static void replace(int number, int p) {
		int temp = 0;
		boolean T1_not_empty = T1.size() != 0;
		boolean T1_gt_p = T1.size() > p;
		boolean T1_eq_p = T1.size() == p;

		if (T1_not_empty) {
			if (T1_gt_p) {
				temp = T1.removeLast();
				B1.removeFirstOccurrence((Integer) temp);
				B1.addFirst(temp);
			} else {
				if (T1_eq_p && B2.contains(number)) {
					temp = T1.removeLast();
					B1.removeFirstOccurrence((Integer) temp);
					B1.addFirst(temp);
				} else {
					temp = T2.removeLast();
					B2.addFirst(temp);
					return;
				}
			}
		} else {
			temp = T2.removeLast();
			B2.addFirst(temp);
			return;
		}
	}

	public static float round_the_number(float hit_ratio) {

		if (Math.round((hit_ratio * 1000.0)) % 10.0 > 6.0) {
			return (float) (Math.round((hit_ratio * 100.0) + 1.0) / 100.0);
		} else
			return (float) (Math.round((hit_ratio * 100.0)) / 100.0);

	}
}
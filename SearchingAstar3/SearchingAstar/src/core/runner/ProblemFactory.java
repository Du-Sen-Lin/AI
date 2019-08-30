package core.runner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import core.problem.Problem;
import g07.problem.blocks.BlocksProblem;
import g07.problem.npuzzle.NPuzzle;


//����಻���޸�
public class ProblemFactory {

	public ProblemFactory() {
		// TODO Auto-generated constructor stub
	}

	// ��ȡĳ�����������ʵ��
	// type���������ͣ�1: n���룻2: n������ľ��
	public static ArrayList<Problem> getProblems(int type) {
		if (type == 1)
			return readProblemFromFile("problems2.txt");
		else {
			ArrayList<Problem> problems = new ArrayList<>();
			//problems.add(new BlocksProblem(3, false));
		//	problems.add(new BlocksProblem(27, true));
		//	problems.add(new BlocksProblem(28, true));
		//	problems.add(new BlocksProblem(29, true));
			problems.add(new BlocksProblem(6, true));
			problems.add(new BlocksProblem(6, false));
//			problems.add(new BlocksProblem(23, false));
//			problems.add(new BlocksProblem(24, false));
//			problems.add(new BlocksProblem(25, false));
//			problems.add(new BlocksProblem(26, false));
			//problems.add(new BlocksProblem(10, true));
	//		problems.add(new BlocksProblem(22, false));
//			problems.add(new BlocksProblem(12, true));
//			problems.add(new BlocksProblem(12, false));
			return problems;
		}
	}

	// ���ļ��е�һ�У�����Ϊn��������
	private static Problem parseString(String problem) {

		String[] cells = problem.split(",");
		int side = Integer.valueOf(cells[0].trim()).intValue();

		int[] status = new int[cells.length - 1];
		for (int i = 0; i < status.length; i++) {
			status[i] = Byte.valueOf(cells[i + 1].trim()).byteValue();
		}

		Problem npuzzle = new NPuzzle(side, status);

		return npuzzle;
	}

	//��ȡ�ļ��е���������ʵ��
	private static ArrayList<Problem> readProblemFromFile(String fileName) {
		ArrayList<Problem> problems = new ArrayList<>();
		File file = new File(fileName);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			while ((tempString = reader.readLine()) != null) {
				problems.add(parseString(tempString));
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
		return problems;
	}
}

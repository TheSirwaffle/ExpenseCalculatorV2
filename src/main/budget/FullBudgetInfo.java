package main.budget;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class FullBudgetInfo implements Serializable {

	private static final long serialVersionUID = -5106985026344732011L;

	private ArrayList<BudgetItem> budgetItems;
	
	public FullBudgetInfo(ArrayList<BudgetItem> items) {
		this.budgetItems = items;
	}
	
	public ArrayList<BudgetItem> getIncomes() {
		return budgetItems;
	}
	
	public static void save(FullBudgetInfo info) {
		File file = getFile();
		try {
			file.createNewFile();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))) {
			out.writeObject(info);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static FullBudgetInfo load() {
		FullBudgetInfo info = new FullBudgetInfo(new ArrayList<>());
		File file = getFile();
		if(file.exists()) {
			try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));) {
				info = (FullBudgetInfo) in.readObject();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		return info;
	}
	
	private static File getFile() {
		String path = FullBudgetInfo.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		if(path.contains(":")) {
			path = path.substring(1);
		}
		path = path.replaceAll("%20", " ");
		Path fullPath = Paths.get(path, "/saveFile.bin");
		if(path.endsWith(".jar")) {
			int index = path.lastIndexOf("/");
			path = path.substring(0, index);
			fullPath = Paths.get(path, "/saveFile.bin");
		}
		File file = fullPath.toFile();
		return file;
	}

}

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class FileCompare {

	void compareFile(File firFile, File secFile) {
		try {
			BufferedInputStream fir = new BufferedInputStream(new FileInputStream(firFile));
			BufferedInputStream sec = new BufferedInputStream(new FileInputStream(secFile));
			// 比较文件的长度是否一样
			while (true) {
				int firRead = fir.read();
				int secRead = sec.read();
				while(firRead == 13 || firRead == 10) {
					firRead = fir.read();
				}
				while(secRead == 13 || secRead == 10) {
					secRead = sec.read();
				}
				if (firRead == -1 || secRead == -1) {
					System.out.println("two files are same!");
					break;
				} else if (firRead != secRead) {
					System.out.println("Files not same!");
					break;
				}
			}
			fir.close();
			sec.close();
			return;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

package quan.AccessController;

import java.awt.AWTPermission;
import java.io.FilePermission;
import java.security.AccessController;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		AWTPermission awtPermission = new AWTPermission("accessClipboard");
		FilePermission filePermission = new FilePermission("D://textpermission.txt", "read");

	}

}

package utilities;

import java.awt.Robot;
import java.awt.event.KeyEvent;

public class UserInterfaceUtil {

	public static void scrollPageDown() throws Exception {
		Robot robot1 = new Robot();
		robot1.keyPress(KeyEvent.VK_PAGE_DOWN);
		robot1.keyRelease(KeyEvent.VK_PAGE_DOWN);
	}
}

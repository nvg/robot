package test;

import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OptOutClickerTest {

	private MacOptOutClickerRobot robot;

	@BeforeEach
	void init() {
		robot = new MacOptOutClickerRobot();
		robot.setUser("MAA3");
		robot.setPass("test123");
	}

	@Test
	void test() {
		try {
			robot.doWork();
		} catch (Exception e) {
			fail(e);
		}
	}

	@AfterEach
	void close() throws IOException {
		robot.close();
	}

}

package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RobotTest {

	private EpamAddressScrapingRobot robot;

	@BeforeEach
	void init() {
		robot = new EpamAddressScrapingRobot();
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
